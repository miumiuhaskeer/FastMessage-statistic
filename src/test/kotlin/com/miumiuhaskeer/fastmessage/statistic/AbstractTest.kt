package com.miumiuhaskeer.fastmessage.statistic

import com.github.dockerjava.api.model.ExposedPort
import com.github.dockerjava.api.model.HostConfig
import com.github.dockerjava.api.model.PortBinding
import com.github.dockerjava.api.model.Ports
import com.miumiuhaskeer.fastmessage.statistic.config.TestPersistenceConfig
import com.miumiuhaskeer.fastmessage.statistic.properties.config.JWTokenProp
import com.miumiuhaskeer.fastmessage.statistic.repository.MessageSearchRepository
import com.miumiuhaskeer.fastmessage.statistic.repository.UserInfoRepository
import com.miumiuhaskeer.fastmessage.statistic.repository.UserRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.bind.annotation.RequestMethod
import org.testcontainers.elasticsearch.ElasticsearchContainer
import java.util.*


@SpringBootTest
@AutoConfigureMockMvc
@Import(TestPersistenceConfig::class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@EmbeddedKafka(
    partitions = 1,
    brokerProperties = [
        "listeners=PLAINTEXT://localhost:9092",
        "port=9092"
    ]
)
abstract class AbstractTest {

    companion object {
        val elasticsearchContainer = ElasticsearchContainer("docker.elastic.co/elasticsearch/elasticsearch:7.17.4").apply {
            withEnv("bootstrap.memory_lock", "true")
            withEnv("discovery.type", "single-node")
            withExposedPorts(9200)
            withCreateContainerCmdModifier { cmd ->
                cmd.withHostConfig(HostConfig().withPortBindings(
                    PortBinding(Ports.Binding.bindPort(9292), ExposedPort(9200))
                ))
            }
            start()
        }
    }

    @Autowired
    protected lateinit var mockMvc: MockMvc

    @Autowired
    protected lateinit var jsonConverter: JsonConverter

    @Autowired
    protected lateinit var userInfoRepository: UserInfoRepository

    @Autowired
    protected lateinit var messageSearchRepository: MessageSearchRepository

    @Autowired
    private lateinit var jwTokenProp: JWTokenProp

    protected lateinit var adminHeader: String

    @BeforeEach
    fun clearElasticsearchBD() {
        userInfoRepository.deleteAll()
        messageSearchRepository.deleteAll()
    }

    @BeforeEach
    fun initUser(@Autowired userRepository: UserRepository) {
        val current = Date()
        val user = userRepository.findById(1).orElseThrow(::AssertionError)

        adminHeader = "Bearer " + Jwts.builder()
            .setSubject(user.email)
            .setIssuedAt(current)
            .setExpiration(Date(current.time + jwTokenProp.fmsExpirationSeconds * 1000))
            .signWith(SignatureAlgorithm.HS512, jwTokenProp.fmsSecret)
            .compact();
    }

    /**
     * Perform ok request with returning result
     *
     * @param query servlet request parameters. Cannot be null
     * @param request parameter for servlet request. Can be null
     * @param returnType returning class type (result of request)
     * @param <T> type for request
     * @param <U> type for returnType
     * @return result of request
     * @throws Exception throwing by mockMvc
     */
    protected fun <T, U> performOkRequest(query: MockMvcQuery, request: T, returnType: Class<U>): U {
        val mvcResult = mockMvc.perform(createRequest(query, request))
            .andExpect(status().isOk)
            .andReturn()
        val content = mvcResult.response.contentAsString

        return jsonConverter.fromJson(content, returnType)
    }

    /**
     * Perform ok request without returning result
     *
     * @param query servlet request parameters. Cannot be null
     * @param request parameter for servlet request. Can be null
     * @param <T> type for request
     * @throws Exception throwing by mockMvc
     */
    protected fun <T> performOkRequest(query: MockMvcQuery, request: T) {
        mockMvc.perform(createRequest(query, request))
            .andExpect(status().isOk)
    }

    /**
     * Perform bad request with returning result
     *
     * @param query servlet request parameters. Cannot be null
     * @param request parameter for servlet request. Can be null
     * @param returnType returning class type (result of request)
     * @param <T> type for request
     * @param <U> type for returnType
     * @return result of request
     * @throws Exception throwing by mockMvc
     */
    protected fun <T, U> performBadRequest(query: MockMvcQuery, request: T, returnType: Class<U>): U {
        val mvcResult = mockMvc.perform(createRequest(query, request))
            .andExpect(status().isBadRequest)
            .andReturn()
        val content = mvcResult.response.contentAsString

        return jsonConverter.fromJson(content, returnType)
    }

    /**
     * Perform bad request without returning result
     *
     * @param query servlet request parameters. Cannot be null
     * @param request parameter for servlet request. Can be null
     * @param <T> type for request
     * @throws Exception throwing by mockMvc
     */
    protected fun <T> performBadRequest(query: MockMvcQuery, request: T) {
        mockMvc.perform(createRequest(query, request))
            .andExpect(status().isBadRequest)
    }

    /**
     * Perform bad request without returning result
     *
     * @param query servlet request parameters. Cannot be null
     * @param request parameter for servlet request. Can be null
     * @param <T> type for request
     * @throws Exception throwing by mockMvc
     */
    protected fun <T> performUnauthorizedRequest(query: MockMvcQuery, request: T) {
        mockMvc.perform(createRequest(query, request))
            .andExpect(status().isUnauthorized)
    }

    /**
     * Create servlet request builder with parameters that are provided by query param.
     *
     * @param query servlet request parameters
     * @param request parameter for servlet request
     * @param <T> type for request
     * @return servlet request builder
     */
    protected fun <T> createRequest(query: MockMvcQuery, request: T): MockHttpServletRequestBuilder {
        val builder = if (query.method == RequestMethod.POST) {
            post(query.path)
        } else {
            get(query.path)
        }

        if (query.authToken != null) {
            builder.header(HttpHeaders.AUTHORIZATION, query.authToken)
        }

        if (request != null) {
            builder.contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.toJsonSafe(request))
        }

        return builder
    }
}