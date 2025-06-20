package com.example.customerapi.service;

import com.example.customerapi.dto.CustomerDto;
import com.example.customerapi.model.Customer;
import com.example.customerapi.repository.CustomerRepository;
import com.example.customerapi.exception.CustomerNotFoundException;
import com.example.customerapi.exception.DuplicateEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class CustomerService {
    // TODO: Replace with proper logging framework
    private static final Logger logger = Logger.getLogger(CustomerService.class.getName());
    
    private final CustomerRepository customerRepository;

    // Get all customer records from the database
    public List<CustomerDto> getAllCustomers() {
        logger.info("Fetching all customers");
        // Using Java 8 streams to transform entities to DTOs
        return customerRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Find a specific customer by their UUID
    public CustomerDto getCustomerById(UUID id) {
        logger.info("Looking up customer with id: " + id);
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
        return mapToDto(customer);
    }

    @Transactional
    public CustomerDto createCustomer(CustomerDto customerDto) {
        // Generate random UUID if not provided
        if (customerDto.getId() == null) {
            customerDto.setId(UUID.randomUUID());
            // helpful log for debugging
            logger.fine("Generated UUID for new customer: " + customerDto.getId());
        }
        
        // Check if email already exists to avoid duplicates
        var emailAddress = customerDto.getEmailAddress();
        if (customerRepository.existsByEmailAddress(emailAddress)) {
            logger.warning("Attempted to create customer with duplicate email: " + emailAddress);
            throw new DuplicateEmailException("Email address already exists: " + emailAddress);
        }
        
        // Convert, save, and return
        var customer = mapToEntity(customerDto);
        var savedCustomer = customerRepository.save(customer);
        logger.info("Created new customer with ID: " + savedCustomer.getId());
        return mapToDto(savedCustomer);
    }

    /**
     * Update existing customer
     * 
     * @param id customer ID
     * @param customerDto customer data
     * @return updated CustomerDto
     * @throws CustomerNotFoundException if customer not found
     * @throws DuplicateEmailException if email already exists for another customer
     */
    @Transactional
    public CustomerDto updateCustomer(UUID id, CustomerDto customerDto) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
        
        // Check if email is being changed and if new email already exists
        if (!existingCustomer.getEmailAddress().equals(customerDto.getEmailAddress()) &&
                customerRepository.existsByEmailAddress(customerDto.getEmailAddress())) {
            throw new DuplicateEmailException("Email address already exists: " + customerDto.getEmailAddress());
        }
        
        // Update customer data
        Customer customer = mapToEntity(customerDto);
        customer.setId(id); // Ensure ID doesn't change
        Customer updatedCustomer = customerRepository.save(customer);
        return mapToDto(updatedCustomer);
    }

    /**
     * Delete customer by ID
     * 
     * @param id customer ID
     * @throws CustomerNotFoundException if customer not found
     */
    @Transactional
    public void deleteCustomer(UUID id) {
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }

    // Helper method to convert entity to DTO
    private CustomerDto mapToDto(Customer customer) {
        // Using builder pattern from Lombok
        CustomerDto dto = CustomerDto.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .middleName(customer.getMiddleName())
                .lastName(customer.getLastName())
                .emailAddress(customer.getEmailAddress())
                .phoneNumber(customer.getPhoneNumber())
                .build();
        
        return dto; // explicit return for clarity
    }

    // Helper method to convert DTO to entity
    private Customer mapToEntity(CustomerDto dto) {
        // Could use a mapper library like MapStruct in a real project
        return Customer.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .middleName(dto.getMiddleName())
                .lastName(dto.getLastName())
                .emailAddress(dto.getEmailAddress())
                .phoneNumber(dto.getPhoneNumber())
                .build();
    }
}
