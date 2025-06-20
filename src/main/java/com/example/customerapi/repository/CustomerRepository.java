package com.example.customerapi.repository;

import com.example.customerapi.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    
    /**
     * Find a customer by email address
     * 
     * @param emailAddress email address to search for
     * @return Optional containing customer if found
     */
    Optional<Customer> findByEmailAddress(String emailAddress);
    
    /**
     * Check if customer exists with the given email address
     * 
     * @param emailAddress email address to check
     * @return true if customer exists, false otherwise
     */
    boolean existsByEmailAddress(String emailAddress);
}
