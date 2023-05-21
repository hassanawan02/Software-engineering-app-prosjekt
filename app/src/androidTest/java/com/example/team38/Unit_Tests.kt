package com.example.team38
import Datasource
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Test

class Unit_Tests {
/*
    private val client = HttpClient(MockEngine) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
        engine {
            // Setup Mock Engine to respond to fetchStromprisData request
            addHandler { request ->
                when (request.url.fullPath) {
                    "/path-to-strom" -> {
                        val responseHeaders = headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
                        respond(
                            content = "[{\"some_key\": \"some_value\"}]", // your test JSON response
                            headers = responseHeaders
                        )
                    }
                    else -> error("Unhandled ${request.url.fullPath}")
                }
            }
        }
    }

    private val datasource = Datasource(
        path = "https://www.hvakosterstrommen.no/api/v1/prices/2023/03-27_NO5.json",

    )

    @Test
    fun testFetchStromprisData() = runBlocking {
        val data = datasource.fetchStromprisData()
        assertEquals(1, data.size)  // Assuming your JSON response has 1 item
        assertEquals("some_value", data[0].some_key)  // Substitute with actual StromprisData fields
    }

    // Add more test functions for fetchForecastData and fetchFrostData




 */




}