# data-collection-service

## Endpoints : 

1.  Store employee data in XML or CSV format.
    POST http://localhost:8080/data-collection/employee/store?fileType=XML/CSV

    > Default fileType : CSV

    ```
    Request Body:
    {
        "name" : "employee",
        "dateOfBirth" : "2020-08-29",
        "salary" : 3000.00,
        "age" : 100
    }
    ```

    This endpoint will serialize the employee data to protobuf and encrypt it before publishing it to employeeQueue via Direct Exchange.

2.  Update employee data in XML or CSV format.

	PUT http://localhost:8080/data-collection/employee/update?fileType=XML

	> Default fileType : CSV

	```
	Request Body:
	{
		"name" : "employee",
		"dateOfBirth" : "2022-08-29", 
		"salary" : 3000.00,
		"age" : 1000
	}
	```

	the name field must match the existing file, if not found, this PUT will be ignored.

3.  GET employee data

	http://localhost:8080/data-collection/employee/read/employee?fileType=XML/CSV

	This endpoint will invoke get endpoint of dataStorage service to fetch the employee data in XML or CSV format, it will deserialize the protobuf before responding.

## Pre-requisite:
	RMQ must be up and running at below mentioned port.
	http://localhost:15672

## Steps to run in local:
	1. git clone git@github.com:RakshithVP/data-collection-service.git - Clone this repo.
	2. mvn clean install - to generate jar files in target repository.
	3. java -jar dataCollectionService-0.0.1-SNAPSHOT.jar - run the spring boot application.
