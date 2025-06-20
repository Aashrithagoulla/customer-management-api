package com.example.customerapi.service;

import com.example.customerapi.dto.CustomerDto;
import com.example.customerapi.exception.CustomerNotFoundException;
import com.example.customerapi.exception.DuplicateEmailException;
import com.example.customerapi.model.Customer;
import com.example.customerapi.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private UUID customerId;
    private Customer customer;
    private CustomerDto customerDto;

    @BeforeEach
    void setUp() {
        customerId = UUID.randomUUID();
        
        customer = new Customer();
        customer.setId(customerId);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmailAddress("john.doe@example.com");
        customer.setPhoneNumber("1234567890");
        
        customerDto = new CustomerDto();
        customerDto.setId(customerId);
        customerDto.setFirstName("John");
        customerDto.setLastName("Doe");
        customerDto.setEmailAddress("john.doe@example.com");
        customerDto.setPhoneNumber("1234567890");
    }

    @Test
    void getAllCustomers_ShouldReturnListOfCustomers() {
        // Given
        Customer customer2 = new Customer();
        customer2.setId(UUID.randomUUID());
        customer2.setFirstName("Jane");
        customer2.setLastName("Smith");
        customer2.setEmailAddress("jane.smith@example.com");
        customer2.setPhoneNumber("0987654321");
        
        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer, customer2));

        // When
        List<CustomerDto> result = customerService.getAllCustomers();

        // Then
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Jane", result.get(1).getFirstName());
    }

    @Test
    void getCustomerById_WhenCustomerExists_ShouldReturnCustomer() {
        // Given
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        // When
        CustomerDto result = customerService.getCustomerById(customerId);

        // Then
        assertEquals(customerId, result.getId());
        assertEquals("John", result.getFirstName());
    }

    @Test
    void getCustomerById_WhenCustomerDoesNotExist_ShouldThrowException() {
        // Given
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(CustomerNotFoundException.class, () -> {
            customerService.getCustomerById(customerId);
        });
    }

    @Test
    void createCustomer_WhenEmailNotExists_ShouldCreateCustomer() {
        // Given - setup test conditions
        String email = "john.doe@example.com";
        when(customerRepository.existsByEmailAddress(email)).thenReturn(false);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        // Need to use ArgumentCaptor if we want to verify exact values
        // ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
        
        // When - execute the method under test
        CustomerDto result = customerService.createCustomer(customerDto);

        // Then - verify the results and interactions
        assertEquals(customerId, result.getId(), "ID should match");
        assertEquals("John", result.getFirstName(), "First name should match");
        assertEquals("Doe", result.getLastName(), "Last name should match"); // Added additional validation
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void createCustomer_WhenEmailExists_ShouldThrowException() {
        // Given
        when(customerRepository.existsByEmailAddress(customerDto.getEmailAddress())).thenReturn(true);

        // When & Then
        assertThrows(DuplicateEmailException.class, () -> {
            customerService.createCustomer(customerDto);
        });
        
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void updateCustomer_WhenCustomerExistsAndEmailNotChanged_ShouldUpdateCustomer() {
        // Given
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        // When
        CustomerDto result = customerService.updateCustomer(customerId, customerDto);

        // Then
        assertEquals(customerId, result.getId());
        assertEquals("John", result.getFirstName());
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void updateCustomer_WhenCustomerExistsAndEmailChangedAndNewEmailNotExists_ShouldUpdateCustomer() {
        // Given
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        
        CustomerDto updatedDto = new CustomerDto();
        updatedDto.setId(customerId);
        updatedDto.setFirstName("John");
        updatedDto.setLastName("Doe");
        updatedDto.setEmailAddress("john.updated@example.com");
        updatedDto.setPhoneNumber("1234567890");
        
        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(customerId);
        updatedCustomer.setFirstName("John");
        updatedCustomer.setLastName("Doe");
        updatedCustomer.setEmailAddress("john.updated@example.com");
        updatedCustomer.setPhoneNumber("1234567890");
        
        when(customerRepository.existsByEmailAddress("john.updated@example.com")).thenReturn(false);
        when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);

        // When
        CustomerDto result = customerService.updateCustomer(customerId, updatedDto);

        // Then
        assertEquals(customerId, result.getId());
        assertEquals("john.updated@example.com", result.getEmailAddress());
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void updateCustomer_WhenCustomerExistsAndEmailChangedAndNewEmailExists_ShouldThrowException() {
        // Given
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        
        CustomerDto updatedDto = new CustomerDto();
        updatedDto.setId(customerId);
        updatedDto.setFirstName("John");
        updatedDto.setLastName("Doe");
        updatedDto.setEmailAddress("john.updated@example.com");
        updatedDto.setPhoneNumber("1234567890");
        
        when(customerRepository.existsByEmailAddress("john.updated@example.com")).thenReturn(true);

        // When & Then
        assertThrows(DuplicateEmailException.class, () -> {
            customerService.updateCustomer(customerId, updatedDto);
        });
        
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void updateCustomer_WhenCustomerDoesNotExist_ShouldThrowException() {
        // Given
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(CustomerNotFoundException.class, () -> {
            customerService.updateCustomer(customerId, customerDto);
        });
        
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void deleteCustomer_WhenCustomerExists_ShouldDeleteCustomer() {
        // Given
        when(customerRepository.existsById(customerId)).thenReturn(true);

        // When
        customerService.deleteCustomer(customerId);

        // Then
        verify(customerRepository).deleteById(customerId);
    }

    @Test
    void deleteCustomer_WhenCustomerDoesNotExist_ShouldThrowException() {
        // Given
        when(customerRepository.existsById(customerId)).thenReturn(false);

        // When & Then
        assertThrows(CustomerNotFoundException.class, () -> {
            customerService.deleteCustomer(customerId);
        });
        
        verify(customerRepository, never()).deleteById(any(UUID.class));
    }
}
