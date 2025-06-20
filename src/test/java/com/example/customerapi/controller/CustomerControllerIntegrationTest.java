package com.example.customerapi.controller;

import com.example.customerapi.dto.CustomerDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createCustomer_WhenValidInput_ShouldReturnCreatedCustomer() throws Exception {
        // Given - prepare test data with builder pattern
        CustomerDto customerDto = CustomerDto.builder()
                .firstName("John")
                .lastName("Doe")
                .emailAddress("john.doe@example.com")
                .phoneNumber("1234567890")
                .build();
                
        // Could use some fake data generator library for more variation

        // When & Then - test API call and validate response
        MvcResult result = mockMvc.perform(post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDto)))
                // Inline comments on expectations
                .andExpect(status().isCreated()) // 201 status
                .andExpect(jsonPath("$.id").isNotEmpty()) // UUID generated
                .andExpect(jsonPath("$.firstName").value("John")) 
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.emailAddress").value("john.doe@example.com"))
                .andExpect(jsonPath("$.phoneNumber").value("1234567890"))
                // Could add more validations here
                .andReturn();
                
        // Extract the ID for later tests
        String responseContent = result.getResponse().getContentAsString();
        CustomerDto createdCustomer = objectMapper.readValue(responseContent, CustomerDto.class);
        UUID customerId = createdCustomer.getId();

        // Test get by ID
        mockMvc.perform(get("/api/v1/customers/{id}", customerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customerId.toString()))
                .andExpect(jsonPath("$.firstName").value("John"));

        // Test update
        customerDto.setId(customerId);
        customerDto.setFirstName("Johnny");
        mockMvc.perform(put("/api/v1/customers/{id}", customerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Johnny"));

        // Test get all
        mockMvc.perform(get("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].id").isNotEmpty());

        // Test delete
        mockMvc.perform(delete("/api/v1/customers/{id}", customerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Verify delete was successful
        mockMvc.perform(get("/api/v1/customers/{id}", customerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createCustomer_WhenInvalidInput_ShouldReturnBadRequest() throws Exception {
        // Given - missing required fields
        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName(""); // Invalid: empty first name
        customerDto.setLastName("Doe");
        customerDto.setEmailAddress("invalid-email"); // Invalid: not a valid email
        customerDto.setPhoneNumber("");  // Invalid: empty phone number

        // When & Then
        mockMvc.perform(post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isNotEmpty());
    }

    @Test
    void createCustomer_WhenDuplicateEmail_ShouldReturnConflict() throws Exception {
        // Given
        CustomerDto customer1 = new CustomerDto();
        customer1.setFirstName("Test");
        customer1.setLastName("User");
        customer1.setEmailAddress("test.duplicate@example.com");
        customer1.setPhoneNumber("9876543210");

        // Create first customer
        mockMvc.perform(post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer1)))
                .andExpect(status().isCreated());

        // Try to create another customer with same email
        CustomerDto customer2 = new CustomerDto();
        customer2.setFirstName("Another");
        customer2.setLastName("User");
        customer2.setEmailAddress("test.duplicate@example.com");
        customer2.setPhoneNumber("1234567890");

        // When & Then
        mockMvc.perform(post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer2)))
                .andExpect(status().isConflict());
    }
}
