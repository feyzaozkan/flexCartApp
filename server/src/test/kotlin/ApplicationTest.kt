package com.aselsan
import com.aselsan.domain.model.CampaignDetails
import com.aselsan.domain.model.CampaignRequest
import com.aselsan.domain.model.CampaignResponse
import com.aselsan.domain.model.CartItemRequest
import com.aselsan.domain.model.CompletePaymentRequest
import com.aselsan.domain.model.CustomerResponse
import com.aselsan.domain.model.ProductResponse
import com.aselsan.domain.product.ProductCategoryType
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

    @Test
    fun testGetProducts() = testApplication {
        application {
            module()
        }
        val response = client.get("/products")

        assertEquals(HttpStatusCode.OK, response.status)

        val responseBody = response.bodyAsText()

        val products = Json.decodeFromString<List<ProductResponse>>(responseBody)

        assertTrue(products.isNotEmpty())
        assertTrue(products.any { it.name == "Smartphone" })
        assertTrue(products.any { it.category == ProductCategoryType.CLOTHING })

    }

    @Test
    fun testApplyDiscount() = testApplication {

        suspend fun applyDiscountAndVerify(requestBody: CampaignRequest, expectedResponse: CampaignResponse) {
            val response = client.post("/shopping/applyDiscount") {
                contentType(ContentType.Application.Json)
                setBody(Json.encodeToString(requestBody))
            }
            val responseBody = response.bodyAsText()
            val actualResponse = Json.decodeFromString<CampaignResponse>(responseBody)

            val actualCampaignDetails = actualResponse.campaignDetails.sortedBy { it.name }
            val expectedCampaignDetails = expectedResponse.campaignDetails.sortedBy { it.name }

            assertEquals(expectedCampaignDetails, actualCampaignDetails)
            assertEquals(expectedResponse.shippingFee, actualResponse.shippingFee)
        }

        application {module()}

        // PREMIUM USER, ELECTRONICS * 220.0 * 10, CLOTHING * 96.0 * 10
        val requestBody1 = CampaignRequest(
            customerId = "c7882b51-39b9-4f67-809f-56deb771da5a",

            items = listOf(
                CartItemRequest(productId = "04a16149-e833-4988-8e19-62a58ef87708", quantity = 10),
                CartItemRequest(productId = "b0942e1d-b241-44f6-83ff-9d4f225d372d", quantity = 10)
            )
        )
        val expectedResponse1 = CampaignResponse(
            campaignDetails = listOf(
                CampaignDetails(name = "Bundle-Multi Buy Campaign", discount = 632.0),
                CampaignDetails(name = "Percentage Discount", discount = 474.0),
                CampaignDetails(name = "Fixed Amount Discount", discount = 0.0),
                CampaignDetails(name = "Date-Based Discounts", discount = 0.0),
                CampaignDetails(name = "First Purchase Campaign", discount = 0.0)
            ),
            shippingFee = 0.0
        )
        applyDiscountAndVerify(requestBody1, expectedResponse1)


        // NEW USER, ELECTRONICS * 89.5 * 3, BOOKS * 266 * 2
        val requestBody2 = CampaignRequest(
            customerId = "7c0896a1-2ae3-407a-b255-8c50e8d99022",
            items = listOf(
                CartItemRequest(productId =  "a7a51d79-d92f-4142-9a6a-fe9956dff69c", quantity = 3),
                CartItemRequest(productId = "7a1f93f8-b4c4-4a7c-ae6a-0c0f8a24b68c", quantity = 2)
            )
        )
        val expectedResponse2 = CampaignResponse(
            campaignDetails = listOf(
                CampaignDetails(name = "Bundle-Multi Buy Campaign", discount = 160.10000000000002),
                CampaignDetails(name = "Percentage Discount", discount = 0.0),
                CampaignDetails(name = "Fixed Amount Discount", discount =100.0),
                CampaignDetails(name = "Date-Based Discounts", discount =  53.2),
                CampaignDetails(name = "First Purchase Campaign", discount =  200.125)
            ),
            shippingFee = 25.0
        )
        applyDiscountAndVerify(requestBody2, expectedResponse2)

        // STANDARD USER, HOME_LIVING * 459 * 1, COSMETICS * 423.6 * 1
        val requestBody3 = CampaignRequest(
            customerId = "b1326ce0-737a-47c3-92b0-d1bcb8b39209",
            items = listOf(
                CartItemRequest(productId =  "966ef3ff-70ee-4f5c-8ecb-8a93d1085668", quantity = 1),
                CartItemRequest(productId = "1390a6a7-cb02-414c-a4ce-8f85bf725525", quantity = 1)
            )
        )
        val expectedResponse3 = CampaignResponse(
            campaignDetails = listOf(
                CampaignDetails(name = "Bundle-Multi Buy Campaign", discount = 423.6),
                CampaignDetails(name = "Percentage Discount", discount = 0.0),
                CampaignDetails(name = "Fixed Amount Discount", discount = 0.0),
                CampaignDetails(name = "Date-Based Discounts", discount =  0.0),
                CampaignDetails(name = "First Purchase Campaign", discount = 0.0)
            ),
            shippingFee = 25.0
        )
        applyDiscountAndVerify(requestBody3, expectedResponse3)
    }


    @Test
    fun testApplyDiscountFailure() = testApplication {
        application { module() }

        val testCases = listOf(
            // case: customer not found
            """{ "customerId": "b1326ce0-737a-47c3-92b0-d1bcb8b3920","items": [{ "productId": "966ef3ff-70ee-4f5c-8ecb-8a93d1085668","quantity": 1}] }""" to HttpStatusCode.NotFound,

            // case: zero or negative quantity
            """{ "customerId": "c7882b51-39b9-4f67-809f-56deb771da5a","items": [{"productId": "966ef3ff-70ee-4f5c-8ecb-8a93d1085668","quantity": -1}]}""" to HttpStatusCode.BadRequest,

        )

        for ((requestBody, expectedStatus) in testCases) {
            val response = client.post("/shopping/applyDiscount") {
                contentType(ContentType.Application.Json)
                setBody(requestBody)
            }
            assertEquals(expectedStatus, response.status)
        }
    }


    @Test
    fun testCompletePaymentSuccess() = testApplication {
        application {
            module()
        }
        val requestBody = CompletePaymentRequest(
            customerId = "7c0896a1-2ae3-407a-b255-8c50e8d99022",
            amountToPay = 100.0
        )

        // STEP 1:  Check customer's initial totalSpent
        val initialCustomerResponse = client.get("/customers/${requestBody.customerId}")
        val initialCustomerBody = initialCustomerResponse.bodyAsText()
        val initialCustomer = Json.decodeFromString<CustomerResponse>(initialCustomerBody)
        val initialTotalSpent = initialCustomer.totalSpent

        // STEP 2: Perform payment
        val response = client.patch("/shopping/completePayment") {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(requestBody))
        }

        assertEquals(HttpStatusCode.OK, response.status)

        // STEP 3: Check customer's updated totalSpent
        val updatedCustomerResponse = client.get("/customers/${requestBody.customerId}")
        val updatedCustomerBody = updatedCustomerResponse.bodyAsText()
        val updatedCustomer = Json.decodeFromString<CustomerResponse>(updatedCustomerBody)
        val updatedTotalSpent = updatedCustomer.totalSpent

        assertEquals(initialTotalSpent + requestBody.amountToPay, updatedTotalSpent)


    }


    @Test
    fun testCompletePaymentFailure() = testApplication {
        application { module() }

        val testCases = listOf(
            // case: missing customerId
            """{ "amountToPay": 100.0 }""" to HttpStatusCode.BadRequest,

            // case: zero amount
            """{ "customerId": "c7882b51-39b9-4f67-809f-56deb771da5a", "amountToPay": 0.0 }""" to HttpStatusCode.BadRequest,

            // case: negative amount
            """{ "customerId": "c7882b51-39b9-4f67-809f-56deb771da5a", "amountToPay": -50.0 }""" to HttpStatusCode.BadRequest,

            // case: empty body
            "" to HttpStatusCode.InternalServerError
        )

        for ((requestBody, expectedStatus) in testCases) {
            val response = client.patch("/shopping/completePayment") {
                contentType(ContentType.Application.Json)
                setBody(requestBody)
            }
            assertEquals(expectedStatus, response.status)
        }
    }

}
