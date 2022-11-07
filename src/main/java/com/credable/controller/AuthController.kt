package com.credable.controller

import com.credable.config.JwtHelper
import com.credable.dto.*
import com.credable.repository.CustomerRepository
import com.credable.service.BankClient
import io.credable.cbs.customer.Customer
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("auth")
@Api(value = "Authentication", description = "API authentications")
class AuthController(
    private val jwtUtil: JwtHelper,
    private val customerRepository: CustomerRepository,
    private val bankClient: BankClient
) {


    @ApiOperation(value = "customer subscription")
    @PostMapping("/subscribe/customer")
    fun subscribeUser(
        @RequestBody request: SubscribeRequest
    ): ResponseEntity<CredableResponse> {
        var credable_response = CredableResponse()
        try {
            val retrievedCustomer: Customer = bankClient.getCustomer(request.customerNumber)
            var newCustomer: com.credable.model.Customer = com.credable.model.Customer()
            newCustomer.customerNumber=retrievedCustomer.customerNumber
            newCustomer.username=request.username
            newCustomer.password=request.password
            credable_response.data = newCustomer
            customerRepository.save(newCustomer);
            credable_response.title = "success"
            credable_response.message ="subscription added successfully!"
            return ResponseEntity(credable_response, HttpStatus.OK)

        } catch (e: Exception) {
            e.printStackTrace()
            credable_response.title = "error"
            credable_response.message =
                "system busy, please try again later!"
            credable_response.detail = e.toString()
            return ResponseEntity(credable_response, HttpStatus.OK)
        }
    }


}

