package com.example.customerapi.controller;

import com.example.customerapi.dto.CustomerDto;
import com.example.customerapi.service.CustomerService;
import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;
// TODO: Add swagger documentation

@RestController
@RequestMapping("/api/v1/customers") // RESTful convention - use plural nouns
@RequiredArgsConstructor
public class CustomerController {
    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
    
    private final CustomerService customerService;

    // Get all customers endpoint
    @GetMapping
    @Timed(value = "customers.get.all", description = "Time taken to get all customers")
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        log.debug("REST request to get all customers");
        var customers = customerService.getAllCustomers(); // Using var for brevity
        return ResponseEntity.ok(customers);
    }

    /**
     * Get customer by ID
     * 
     * @param id customer ID
     * @return CustomerDto
     */
    @GetMapping("/{id}")
    @Timed(value = "customers.get.one", description = "Time taken to get a customer by ID")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable UUID id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    /**
     * Create a new customer
     * 
     * @param customerDto customer data
     * @return created CustomerDto
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Timed(value = "customers.create", description = "Time taken to create a customer")
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
        CustomerDto createdCustomer = customerService.createCustomer(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    /**
     * Update an existing customer
     * 
     * @param id customer ID
     * @param customerDto customer data
     * @return updated CustomerDto
     */
    @PutMapping("/{id}")
    @Timed(value = "customers.update", description = "Time taken to update a customer")
    public ResponseEntity<CustomerDto> updateCustomer(
            @PathVariable UUID id, 
            @Valid @RequestBody CustomerDto customerDto) {
        // FIXME: Validate path ID matches body ID or ignore body ID entirely
        log.info("Request to update customer: {}", id);
        
        // I prefer using var here - makes refactoring easier later
        var updatedCustomer = customerService.updateCustomer(id, customerDto);
        return ResponseEntity.ok(updatedCustomer);
    }

    // Delete customer endpoint - returns 204 No Content
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Timed(value = "customers.delete", description = "Time taken to delete a customer")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) {
        log.info("REST request to delete Customer : {}", id);
        // Could add deletion history logging here for audit purposes
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
