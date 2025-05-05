# Barky Barbers

Full-stack dog grooming appointment management system built with Spring Boot and JavaScript.

## Features

- User management
- Pet management
- Appointment Booking
- Admin Panel

## Technology

- Spring Boot
- MySQL
- JavaScript
- HTML/CSS
- Bootstrap

### Setup

1. Clone the repository.
2. Configure the MySQL database connection in `application.properties`.
3. Initialize the database with the provided SQL script ('schema.sql'). Going to implement table auto generation ASAP.
4. Run the application using `./mvnw spring-boot:run` (uses included Maven wrapper).
5. Access the application at `http://localhost:8080`.

### Future Enhancements

 - Auto-create tables
 - Time-slot based booking
 - Admin verification
 - Selenium tests
 - Refactor; ended up very frontend heavy.
