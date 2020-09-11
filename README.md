# JDBC Code
This program connects to a local MySQL server and performs insert, update and view operations on the data that is found in the db.

## MySQL
The database needs to have a table with two columns - id (int, auto incremented) and data (longtext).
The data columns contains json strings.

## Setup
1. Clone the project.
2. Import all the dependencies in the 'build.gradle' file
3. Create a local MySQL server with a username and password. Create a database (default is 'test') and a table (default is 'deliveries') as mentioned above.
4. Once created, adjust the url, username and password strings in the 'run' method to your chosen values.

## Running
* The entry point for the project is the DeliveryService class. It connects to the database and presents the options to the user at the beginning and after each operation. 
* Performs validation on the inputs entered.  

## Using
* Java 8
* Jackson 2.10.0
* MySQL connector java 8.0.20   