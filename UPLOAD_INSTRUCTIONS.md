# GitHub Upload Instructions for Customer API

This document provides step-by-step instructions for uploading your project files to GitHub.

## Option 1: Manual Upload Through GitHub Web Interface

1. Navigate to your repository (https://github.com/Aashrithagoulla/customer-management-api)
2. For each directory structure, create it first:
   - Click "Add file" > "Create new file"
   - Type the path with a trailing slash (e.g., `src/main/java/com/example/customerapi/`)
   - Add placeholder content
   - Click "Commit new file"
3. Then upload files to each directory:
   - Navigate to the directory
   - Click "Add file" > "Upload files" 
   - Select the appropriate files
   - Click "Commit changes"

## Option 2: Using the Prepared Files

1. Run the script `./prepare_upload.sh` which creates a `github_upload` folder
2. This folder contains only the important files with their directory structure preserved
3. You can upload the contents of this folder directly to GitHub:
   - Go to your repository root
   - Upload the contents (not the folder itself) of `github_upload`
   - Make sure to maintain the directory structure

## Key Files to Upload (In Order of Priority)

1. Base Project Files:
   - README.md
   - pom.xml
   - Dockerfile

2. Core Application:
   - src/main/java/com/example/customerapi/CustomerApiApplication.java
   - src/main/java/com/example/customerapi/model/Customer.java
   - src/main/java/com/example/customerapi/dto/CustomerDto.java

3. Repository and Service:
   - src/main/java/com/example/customerapi/repository/CustomerRepository.java
   - src/main/java/com/example/customerapi/service/CustomerService.java

4. Controller and Exceptions:
   - src/main/java/com/example/customerapi/controller/CustomerController.java
   - All exception files

5. Configuration and Tests:
   - src/main/resources/application.properties
   - All test files

6. Client and Kubernetes:
   - Client files
   - Kubernetes configuration files

## Important Tips

- Commit messages should be descriptive (e.g., "Add core application files")
- You can upload multiple files at once to the same directory
- Make sure to create directories before uploading files to them
