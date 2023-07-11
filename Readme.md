
# Bloomberg FX Data Warehouse

The Bloomberg FX Data Warehouse is a Java-based application built with Spring Boot that allows you to analyze FX deals by persisting and analyzing deal details in a data warehouse.

## Features

- Accepts and persists FX deal details into a database.
- Validates the structure and fields of incoming deal requests.
- Prevents duplicate deal imports.
- Provides error/exception handling and proper logging.
- Supports unit testing with test coverage.
- Documentation for easy understanding and setup.

## Technologies Used

- Java
- Spring Boot
- Maven
- MySQL (or your preferred database)
- Docker

## Getting Started

### Prerequisites

- Java 11 or higher installed on your machine
- Docker installed on your machine (if you want to run the application in a Docker container)
- MySQL (or your preferred database) installed and running (if not using Docker)

### Installation

1. Clone the repository:

   ```shell
   git clone https://github.com/victor-onu/bloomberg-fx-data-warehouse.git


### Navigate to the project directory:

* cd bloomberg-fx-data-warehouse

### Build the application using Maven:

```sh
 mvn clean install
```

### Configuration
* Open the application.yml file in the src/main/resources directory.
* Modify the spring.datasource.url, spring.datasource.username, and spring.datasource.password properties to match your database configuration.


## Running the Application

### Option 1: Using Docker

* Make sure Docker is running on your machine.
* Run the following command to start the application using Docker Compose:
```sh
 docker-compose up
```
* The application will be accessible at http://localhost:8015/api

### Option 2: Without Docker
* Make sure MySQL (or your preferred database) is installed and running with the configured credentials.

* Run the following command to start the application:
```sh
 ./mvnw clean spring-boot:run or mvn clean spring-boot:run 
```
The application will be accessible at http://localhost:8015/api.

## **API Documentation**

The Bloomberg FX Data Warehouse API allows you to manage FX deals by
creating, retrieving, and analyzing deal details.

### **Base URL**

The base URL for all API endpoints is http://localhost:8015/api.

### **Available Endpoints**

#### Create Deal

Endpoint: POST /deals

Create a new FX deal.

Sample Request:

POST /deals

Content-Type: application/json

{

\"dealUniqueId\": \"123\",

\"fromCurrencyISOCode\": \"USD\",

\"toCurrencyISOCode\": \"EUR\",

\"dealTimestamp\": \"2023-07-09T10:00:00\",

\"dealAmount\": 100.0

}

Sample Response:

HTTP/1.1 201 Created

Content-Type: application/json

{

\"status\": \"CREATED\",

\"message\": \"Deal saved successfully.\",

\"data\": \"Deal saved successfully.\"

}

#### Get Deal by Unique ID

Endpoint: GET /deals/{dealUniqueId}

Retrieve an FX deal by its unique ID.

Sample Request:

GET /deals/123

Sample Response:

HTTP/1.1 200 OK

Content-Type: application/json

{

\"status\": \"OK\",

\"message\": \"Deal retrieved successfully.\",

\"data\": {

\"dealUniqueId\": \"123\",

\"fromCurrencyISOCode\": \"USD\",

\"toCurrencyISOCode\": \"EUR\",

\"dealTimestamp\": \"2023-07-09T10:00:00\",

\"dealAmount\": 100.0

}

}

#### Get All Deals

Endpoint: GET /deals

Retrieve all FX deals.

Sample Request:

GET /deals

Sample Response:

HTTP/1.1 200 OK

Content-Type: application/json

{

\"status\": \"OK\",

\"message\": \"Deals retrieved successfully.\",

\"data\": \[

{

\"dealUniqueId\": \"123\",

\"fromCurrencyISOCode\": \"USD\",

\"toCurrencyISOCode\": \"EUR\",

\"dealTimestamp\": \"2023-07-09T10:00:00\",

\"dealAmount\": 100.0

},

{

\"dealUniqueId\": \"456\",

\"fromCurrencyISOCode\": \"GBP\",

\"toCurrencyISOCode\": \"USD\",

\"dealTimestamp\": \"2023-07-09T11:00:00\",

\"dealAmount\": 200.0

}

\]

}

### **Error Handling**

If an error occurs during API requests, you will receive an error
response with the corresponding status code and error message.

Sample Error Response:

HTTP/1.1 400 Bad Request

Content-Type: application/json

{

\"status\": \"BAD_REQUEST\",

\"message\": \"Validation Failed\",

\"error\": \"Validation Failed\",

\"timestamp\": \"2023-07-09T12:00:00\",

\"debugMessage\": null,

\"subErrors\": \[

{

\"object\": \"dealDTO\",

\"field\": \"toCurrencyISOCode\",

\"rejectedValue\": \"\",

\"message\": \"To currency ISO code is required\"

},

{

\"object\": \"dealDTO\",

\"field\": \"fromCurrencyISOCode\",

\"rejectedValue\": \"\",

\"message\": \"From currency ISO code is required\"

}

\],

\"data\": null

}



## Testing
* To run the unit tests and check the test coverage, run the following command:
```sh
 mvn test
```
