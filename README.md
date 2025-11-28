# Library Application

## What is required to install to run application:

 - Java 17 JDK
 - Maven (or use ./mvnw spring-boot:run)
 - MySQL
 - Git

**Script to install:**

    chmod +x scripts/install-dependencies.sh
    ./scripts/install-dependencies.sh

  
## 1) Setup MySQL:

    mysql -u root -p
    CREATE DATABASE IF NOT EXISTS library_db;
    CREATE USER IF NOT EXISTS 'library_user'@'localhost' IDENTIFIED BY 'YOUR_PASSWORD';
    GRANT ALL PRIVILEGES ON library_db.* TO 'library_user'@'localhost';
    FLUSH PRIVILEGES;

> **Make sure you replace 'YOUR_PASSWORD' with a different password**

  

## 2) Setup credentials:

    export SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/library_db"
    export SPRING_DATASOURCE_USERNAME="library_user"
    export SPRING_DATASOURCE_PASSWORD="YOUR_PASSWORD"

> ****Make sure you replace 'YOUR_PASSWORD' with the same password you used before****


**NOTE: this needs to be done in the same terminal session where you run the application**

    chmod +x scripts/check-env.sh
    ./scripts/check-env.sh

## 3) Create and populate database *(can also be used to reset database)*

    chmod +x scripts/reset-db.sh
    ./scripts/reset-db.sh

  
## 4) Run the app

    ./mvnw spring-boot:run

or

    mvn spring-boot:run

  
  

## 5) Go to browser

http://localhost:8080

  
**URLs to check database:**

http://localhost:8080/db/tables

http://localhost:8080/db/schema

http://localhost:8080/books

http://localhost:8080/members

http://localhost:8080/staff

http://localhost:8080/books_borrowed