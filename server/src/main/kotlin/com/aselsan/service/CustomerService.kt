package com.aselsan.service
import com.aselsan.domain.model.CustomerResponseDto
import com.aselsan.utils.toCustomerResponseDto
import com.aselsan.repository.CustomerRepository




class CustomerService() {

    fun getAllCustomers(): List<CustomerResponseDto> {
        val customers = CustomerRepository.allCustomers()
        return customers.map { it.toCustomerResponseDto() }
    }

    fun findById(id: String): CustomerResponseDto? {
        val customer = CustomerRepository.customerById(id)
        return customer?.toCustomerResponseDto()
    }

}
