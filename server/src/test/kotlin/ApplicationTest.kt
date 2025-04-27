package com.aselsan

import com.aselsan.domain.customer.Customer
import com.aselsan.domain.model.CustomerResponse
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*
import io.ktor.server.testing.*
import junit.framework.TestCase.assertTrue
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.assertNotNull
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {

    @Test
    fun testGetCustomers() = testApplication {
        application {
            module()
        }
        val response = client.get("/customers")

        assertEquals(HttpStatusCode.OK, response.status)

        val responseBody = response.bodyAsText()

        val customers = Json.decodeFromString<List<CustomerResponse>>(responseBody)

        assertTrue(customers.isNotEmpty())
        assertTrue(customers.any { it.name == "Alice" })
    }

    @Test
    fun testGetCustomerById() = testApplication {

        application {
            module()
        }
        val existingCustomerId = "7c0896a1-2ae3-407a-b255-8c50e8d99022"
        val nonExistingCustomerId = "04a16149-e833-4988-8e19-62a58ef87708"

        val responseExisting = client.get("/customers/$existingCustomerId")
        assertEquals(HttpStatusCode.OK, responseExisting.status)

        val responseBodyExisting = responseExisting.bodyAsText()
        val customer = Json.decodeFromString<CustomerResponse>(responseBodyExisting)

        assertNotNull(customer)
        assertEquals(existingCustomerId, customer.id)

        val responseNonExisting = client.get("/customers/$nonExistingCustomerId")
        assertEquals(HttpStatusCode.NotFound, responseNonExisting.status)

    }


}
