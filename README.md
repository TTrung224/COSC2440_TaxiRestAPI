# COSC2440_A2
A system that automates business processes of a taxi company. It uses Spring Boot to create APIs that perform CRUD operations on a ProstgreSQL database.

The system simulates a platform where users can book a taxi based on their preferences (location and time). When customer creates a booking, a new Booking entity will be made with relation to an invoice that contains the driver's and customer's id as well as the price calculated based on the rate of the car used and the distance. A driver will be assigned a car on a first-come-first-serve basis.

Team 6:
    Tran Quoc Trung - s3891724
    Ho Tran Minh Khoi - s3877653
    Huynh Ky Thanh - s3884734
    Tran Minh Khoi - s3916827

GitHub URL: https://github.com/TTrung224/COSC2440_A2.git

Unit test class: src/test/java/com/assignment/taxiCom/TaxiComApplicationTests.java

Password of database is at code line 57 in src/main/java/com/assignment/taxiCom/config/AppConfig.java
