package com.miumiuhaskeer.fastmessage.statistic

import org.springframework.web.bind.annotation.RequestMethod

/**
 * Class was created for specify test queries for MockMvc.
 * All test classes that use MockMvcQuery must use method with @BeforeEach annotation
 * to clear old query object
 */
class MockMvcQuery private constructor(
    val method: RequestMethod,
    val path: String,
    var authToken: String?
) {

    companion object {
        fun createPostQuery(path: String, authToken: String?) = MockMvcQuery(
            RequestMethod.POST,
            path,
            authToken
        )

        fun createGetQuery(path: String, authToken: String?) = MockMvcQuery(
            RequestMethod.GET,
            path,
            authToken
        )
    }
}