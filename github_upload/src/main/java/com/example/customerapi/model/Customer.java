package com.example.customerapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Customer entity representing customer information stored in the database
 * 
 * @author John Smith
 * @since 1.0
 */
@Entity
@Table(name = "customers") // explicitly naming the table
@Data // Lombok to generate getters, setters, equals, hashCode, toString
@Builder // builder pattern
@NoArgsConstructor // required for JPA
@AllArgsConstructor // useful for builder
public class Customer {

    @Id
    private UUID id; // using UUID instead of auto-increment for distributed systems

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "middle_name", length = 50)
    private String middleName; // optional field

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "email_address", unique = true, nullable = false, length = 100)
    private String emailAddress; // unique constraint

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;
    
    // Could add audit fields like created_at, updated_at here
}
