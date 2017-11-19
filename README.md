# app4

Steps to use 
1.	checkout both code bases app1 and app4 

2.  import them to eclipse as existing maven projects.

3.	Sample CSV file is already present in the resources folder of application 1. This can be edited or replaced with any other csv file in same format.

4.	Run application 4 as a spring boot application using Application4.java. Application 4 comes up on port 8090.

5.	Now start application 1 as a spring boot application using CsvJsonApplication.java. Application1 will come up on port 8080. It will parse the csv file and post the json-formated data to application 4's rest endpoint. 

6. 	Application 4 will receive the data and save the data in DB. Here we have used in memory database (H2).

7.	In the absence of a UI, interaction with application 4 is to be done through postman application.


Below are the various rest APIs and its usage for application 4.

•	POST /rest/addallcustomer --> used by app1 to send data. Also, can be used from postman to send more customers data in bulk as a json string.

•	GET /rest/showallcustomers --> used to list all customers stored in database.

•	POST /rest/addacustomer -> add a new customer data.

•	POST /rest/searchacustomerbyID --> search a customer by id.

•	POST /rest/searchacustomerbyfirstname --> search a customer by first name.


