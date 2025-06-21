package com.example.client;

// TODO: Add proper package structure in future versions
// TODO: Add proper error handling and retries

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CustomerApiClient {

    // Default URL - can be overridden with environment variables
    private static final String BASE_URL = System.getenv("API_URL") != null ? 
            System.getenv("API_URL") : "http://localhost:8080/api/v1/customers";
            
    // Configure JSON mapper with pretty printing
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);
            
    // Configure HTTP client with timeouts
    private static final CloseableHttpClient httpClient = HttpClients.custom()
            .setDefaultRequestConfig(RequestConfig.custom()
                .setConnectTimeout(5000)
                .setSocketTimeout(30000)
                .build())
            .build();
            
    // For reading user input
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        try {
            boolean exit = false;
            while (!exit) {
                printMenu();
                String choice = reader.readLine();
                
                switch (choice) {
                    case "1":
                        getAllCustomers();
                        break;
                    case "2":
                        getCustomerById();
                        break;
                    case "3":
                        createCustomer();
                        break;
                    case "4":
                        updateCustomer();
                        break;
                    case "5":
                        deleteCustomer();
                        break;
                    case "0":
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
                
                if (!exit) {
                    System.out.println("\nPress Enter to continue...");
                    reader.readLine();
                }
            }
            
            System.out.println("Exiting the application. Goodbye!");
            
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                System.err.println("Error closing HTTP client: " + e.getMessage());
            }
        }
    }

    /**
     * Displays the main menu options
     */
    private static void printMenu() {
        // ASCII art logo - just for fun
        System.out.println("\n" + 
                "   ____          _                            _    ____ ___ \n" +
                "  / ___|   _ ___| |_ ___  _ __ ___   ___ _ __| |  / ___|_ _|\n" +
                " | |  | | | / __| __/ _ \\| '_ ` _ \\ / _ \\ '__| | | |    | | \n" +
                " | |__| |_| \\__ \\ || (_) | | | | | |  __/ |  | | | |___ | | \n" +
                "  \\____\\__,_|___/\\__\\___/|_| |_| |_|\\___|_|  |_|  \\____|___|\n");
                
        System.out.println("\n==== Customer API Client v1.0 ====");
        System.out.println("Available operations:");
        System.out.println("1. List All Customers");
        System.out.println("2. Get Customer by ID");
        System.out.println("3. Create Customer");
        System.out.println("4. Update Customer");
        System.out.println("5. Delete Customer");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void getAllCustomers() throws IOException {
        System.out.println("\n--- List All Customers ---");
        
        HttpGet request = new HttpGet(BASE_URL);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            printResponse(response);
            
            if (response.getStatusLine().getStatusCode() == 200) {
                String json = EntityUtils.toString(response.getEntity());
                Customer[] customers = objectMapper.readValue(json, Customer[].class);
                
                if (customers.length == 0) {
                    System.out.println("No customers found.");
                } else {
                    System.out.println("\nCustomers:");
                    for (Customer customer : customers) {
                        System.out.println(customer);
                    }
                }
            }
        }
    }

    private static void getCustomerById() throws IOException {
        System.out.println("\n--- Get Customer by ID ---");
        System.out.print("Enter customer ID: ");
        String id = reader.readLine();
        
        try {
            UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid UUID format.");
            return;
        }
        
        HttpGet request = new HttpGet(BASE_URL + "/" + id);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            printResponse(response);
            
            if (response.getStatusLine().getStatusCode() == 200) {
                String json = EntityUtils.toString(response.getEntity());
                Customer customer = objectMapper.readValue(json, Customer.class);
                System.out.println("\nCustomer details:");
                System.out.println(customer);
            }
        }
    }

    private static void createCustomer() throws IOException {
        System.out.println("\n--- Create New Customer ---");
        Customer customer = new Customer();
        
        System.out.print("First name: ");
        customer.setFirstName(reader.readLine());
        
        System.out.print("Middle name (optional, press Enter to skip): ");
        String middleName = reader.readLine();
        if (!middleName.isEmpty()) {
            customer.setMiddleName(middleName);
        }
        
        System.out.print("Last name: ");
        customer.setLastName(reader.readLine());
        
        System.out.print("Email address: ");
        customer.setEmailAddress(reader.readLine());
        
        System.out.print("Phone number: ");
        customer.setPhoneNumber(reader.readLine());
        
        String json = objectMapper.writeValueAsString(customer);
        
        HttpPost request = new HttpPost(BASE_URL);
        StringEntity entity = new StringEntity(json);
        request.setEntity(entity);
        request.setHeader("Content-Type", "application/json");
        
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            printResponse(response);
            
            if (response.getStatusLine().getStatusCode() == 201) {
                String responseJson = EntityUtils.toString(response.getEntity());
                Customer createdCustomer = objectMapper.readValue(responseJson, Customer.class);
                System.out.println("\nCustomer created successfully:");
                System.out.println(createdCustomer);
            }
        }
    }

    private static void updateCustomer() throws IOException {
        System.out.println("\n--- Update Customer ---");
        System.out.print("Enter customer ID to update: ");
        String id = reader.readLine();
        
        try {
            UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid UUID format.");
            return;
        }
        
        // First, get the current customer data
        HttpGet getRequest = new HttpGet(BASE_URL + "/" + id);
        try (CloseableHttpResponse getResponse = httpClient.execute(getRequest)) {
            if (getResponse.getStatusLine().getStatusCode() != 200) {
                printResponse(getResponse);
                return;
            }
            
            String json = EntityUtils.toString(getResponse.getEntity());
            Customer customer = objectMapper.readValue(json, Customer.class);
            
            System.out.println("\nCurrent customer details:");
            System.out.println(customer);
            System.out.println("\nEnter new details (press Enter to keep current value):");
            
            System.out.print("First name [" + customer.getFirstName() + "]: ");
            String input = reader.readLine();
            if (!input.isEmpty()) {
                customer.setFirstName(input);
            }
            
            System.out.print("Middle name [" + (customer.getMiddleName() != null ? customer.getMiddleName() : "None") + "]: ");
            input = reader.readLine();
            if (!input.isEmpty()) {
                customer.setMiddleName(input);
            }
            
            System.out.print("Last name [" + customer.getLastName() + "]: ");
            input = reader.readLine();
            if (!input.isEmpty()) {
                customer.setLastName(input);
            }
            
            System.out.print("Email address [" + customer.getEmailAddress() + "]: ");
            input = reader.readLine();
            if (!input.isEmpty()) {
                customer.setEmailAddress(input);
            }
            
            System.out.print("Phone number [" + customer.getPhoneNumber() + "]: ");
            input = reader.readLine();
            if (!input.isEmpty()) {
                customer.setPhoneNumber(input);
            }
            
            // Update the customer
            String updatedJson = objectMapper.writeValueAsString(customer);
            
            HttpPut putRequest = new HttpPut(BASE_URL + "/" + id);
            StringEntity entity = new StringEntity(updatedJson);
            putRequest.setEntity(entity);
            putRequest.setHeader("Content-Type", "application/json");
            
            try (CloseableHttpResponse putResponse = httpClient.execute(putRequest)) {
                printResponse(putResponse);
                
                if (putResponse.getStatusLine().getStatusCode() == 200) {
                    String responseJson = EntityUtils.toString(putResponse.getEntity());
                    Customer updatedCustomer = objectMapper.readValue(responseJson, Customer.class);
                    System.out.println("\nCustomer updated successfully:");
                    System.out.println(updatedCustomer);
                }
            }
        }
    }

    private static void deleteCustomer() throws IOException {
        System.out.println("\n--- Delete Customer ---");
        System.out.print("Enter customer ID to delete: ");
        String id = reader.readLine();
        
        try {
            UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid UUID format.");
            return;
        }
        
        System.out.print("Are you sure you want to delete this customer? (y/n): ");
        String confirmation = reader.readLine();
        
        if (!confirmation.equalsIgnoreCase("y")) {
            System.out.println("Customer deletion cancelled.");
            return;
        }
        
        HttpDelete request = new HttpDelete(BASE_URL + "/" + id);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            printResponse(response);
            
            if (response.getStatusLine().getStatusCode() == 204) {
                System.out.println("Customer deleted successfully.");
            }
        }
    }

    private static void printResponse(CloseableHttpResponse response) throws IOException {
        System.out.println("\nResponse Status: " + response.getStatusLine());
        
        HttpEntity entity = response.getEntity();
        if (entity != null && entity.getContent().available() > 0) {
            String content = EntityUtils.toString(entity);
            try {
                Object json = objectMapper.readValue(content, Object.class);
                System.out.println("Response Body: ");
                System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json));
            } catch (Exception e) {
                System.out.println("Response Body: " + content);
            }
        }
    }
    
    public static class Customer {
        private UUID id;
        private String firstName;
        private String middleName;
        private String lastName;
        private String emailAddress;
        private String phoneNumber;
        
        // Getters and setters
        public UUID getId() { return id; }
        public void setId(UUID id) { this.id = id; }
        
        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        
        public String getMiddleName() { return middleName; }
        public void setMiddleName(String middleName) { this.middleName = middleName; }
        
        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }
        
        public String getEmailAddress() { return emailAddress; }
        public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }
        
        public String getPhoneNumber() { return phoneNumber; }
        public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("ID: ").append(id).append("\n");
            sb.append("Name: ").append(firstName);
            if (middleName != null && !middleName.isEmpty()) {
                sb.append(" ").append(middleName);
            }
            sb.append(" ").append(lastName).append("\n");
            sb.append("Email: ").append(emailAddress).append("\n");
            sb.append("Phone: ").append(phoneNumber);
            return sb.toString();
        }
    }
}
