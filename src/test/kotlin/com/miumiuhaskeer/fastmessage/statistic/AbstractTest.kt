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
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
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
}