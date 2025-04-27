package com.aselsan.service
import com.aselsan.domain.model.CustomerResponse
import com.aselsan.utils.toCustomerResponseDto
import com.aselsan.repository.CustomerRepository




class CustomerService() {

    fun getAllCustomers(): List<CustomerResponse> {
        val customers = CustomerRepository.allCustomers()
        return customers.map { it.toCustomerResponseDto() }
    }

    fun findById(id: String): CustomerResponse? {
        val customer = CustomerRepository.customerById(id)
        return customer?.toCustomerResponseDto()
    }

}
