# IronBnk 🏦

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

## _Project Description_

This project is a banking system developed using Java and Spring Boot. It is designed to meet all the given requirements such as having 4 types of accounts, 3 types of users, and specific attributes for each account type. The project is intended to showcase the skills and knowledge of the developer in implementing a backend system, utilizing MySQL database tables, and applying authentication with Spring Security. The main objective of this project is to provide a practical learning experience in developing a complex system while demonstrating proficiency in programming concepts such as error handling, unit testing, and integration testing. The project also includes extra features such as fraud detection and deployment of the application to an external server. By completing this project, the developer aims to solidify their knowledge of Java Spring Boot and showcase their abilities in developing a robust and scalable system.

## _Requirements_
To run the application, the following software, tools, and technologies are required:

- Java version 17
- Maven version 3.0.2
- Spring Boot version 2.6.3
- Spring Boot Starter Data JPA
- Spring Boot Starter Validation
- Spring Boot Starter Web
- Project Lombok
- Spring Boot DevTools
- MySQL Connector/J
- Spring Boot Starter Test
- Spring Security Test
- Java JWT version 3.18.1
- Spring Boot Starter Security

Note that these are the versions specified in the provided pom.xml file. Other versions may be compatible, but these are the ones that were used during development and testing of the application.

## _Installation_

Here's a step-by-step guide to use the IronBnk app by cloning the repository:

- Open a web browser and go to the GitHub repository for IronBnk: https://github.com/0xNordian/ironBnk
- Click on the green "Code" button and select "Download ZIP". This will download the repository to your local machine.
- Extract the ZIP file to a folder on your local machine.
- Open a command prompt or terminal window and navigate to the extracted folder.
- Ensure that you have Java and Maven installed on your machine. If not, follow the installation instructions for your operating system.
- In the command prompt or terminal window, enter the following command to build the project:
```sh
mvn clean install
```
- Once the build is complete, enter the following command to run the application:
```sh
mvn spring-boot:run
```
- The application should now be running on your local machine. Open a web browser and navigate to http://localhost:8080 to access the application.
- You can use the application to create accounts, transfer funds, and perform other banking-related tasks.
- To stop the application, go back to the command prompt or terminal window and press CTRL + C.

That's it! You should now be able to use the IronBnk app by cloning the repository and following these steps.

## _Default Users and Accounts_

Upon running the application, a number of default users and accounts are generated. The following is a list of the default users and their corresponding roles:

| Username | ROLE |
| --- | --- |
| jose | ROLE_ADMIN |
| nestor | ROLE_ACCOUNT_HOLDER |
| john | ROLE_ACCOUNT_HOLDER |
| jane | ROLE_ACCOUNT_HOLDER |
| james | ROLE_ADMIN |
| chris | ROLE_ADMIN, ROLE_ACCOUNT_HOLDER |

In addition to the default users, several default accounts are also generated, as follows:

### Savings Accounts
| Balance | Primary Owner ID | Minimum Balance | Secret Key | Interest Rate |
| --- | --- | --- | --- | --- |
| $6,000 | 1 | $2,000 | "4321" | null |
| $2,000 | 2 | $7,000 | "789" | 0.005 |


### Credit Card Accounts
| Balance | Primary Owner ID | Credit Limit | Secret Key | Interest Rate |
| --- | --- | --- | --- | --- |
| $3,000 | 3 | $1,250 | "RE45Bh67" | 0.1 |


### Checking Accounts
| Balance | Primary Owner ID | Secret Key | Account Type |
| --- | --- | --- |----------|
| $2,000 | 1 | "N90ZF87b" | Checking |
| $1,500 | 2 | "hdfEWRT4452" | Checking |
| $3,456 | 3 | "eytudgh&%/dfgh" | Student Checking |


Feel free to modify or delete these default users and accounts as needed.
## _Usage_
In order to use the ironBnk application, you will need to use an API development tool such as Postman, Insomnia or a similar application. These tools allow you to make HTTP requests to the application's endpoints, which in turn allows you to interact with the various features of the application. With these tools, you can test the functionality of the application, make requests to create, retrieve, update, and delete resources in the database, and ensure that the application is working as expected. In this way, you can use the API development tool to interact with the ironBnk application and make the most of its features.

| TITLE | METHOD | PATH | PARAMS | BODY | AUTH | ACCESS |
| --- | --- | --- | --- | --- | --- | --- |
| Login | GET | http://localhost:8080/api/login?username=XYZ&password=1234 | Username, Password | None | Required (Bearer Token) | ALL |
| Users List | GET | http://localhost:8080/api/users | None | None | Required (Bearer Token) | ADMIN |
| Create Savings Account | POST | http://localhost:8080/api/admin/savings | None | {"balance": "44000", "primaryOwnerId": 2, "secondaryOwnerId": null, "minimunBalance": null, "secretKey": "FGvb56jhkg", "monthlyMaintenanceFee": null, "interestRate": null} | Required (Bearer Token) | ADMIN |
| Create Checkings Account | POST | http://localhost:8080/api/admin/checkings | None | {"balance": 6000, "secretKey": "mySecretKey2", "primaryOwnerId": 2, "secondaryOwnerId": null} | Required (Bearer Token) | ADMIN |
| Update Balance | PUT | http://localhost:8080/api/admin/update/3 | id | {"amount": 900, "currency": "USD"} | Required (Bearer Token) | ADMIN |
| Delete Account | DELETE | http://localhost:8080/api/admin/delete/1 | id | None | Required (Bearer Token) | ADMIN |
| Access Account Balance | GET | http://localhost:8080/api/account_holder/accessAccount/2 | id | None | Required (Bearer Token) | ACCOUNT_HOLDER |
| Create Credit Card | POST | http://localhost:8080/api/admin/creditcard | None | {"balance": "85050", "secretKey": "DDDdddDDDdd", "primaryOwnerId": 2, "secondaryOwnerId": null, "creditLimit": 10000, "interestRate": null} | Required (Bearer Token) | ADMIN |
| Transfer from Checking Account | POST | http://localhost:8080/api/admin/transferFromChecking | None | {"sourceAccountId": 5, "targetAccountId": 4, "amount": 1100, "transferRequest": {"ownerName": "Nestor"}} | Required (Bearer Token) | ADMIN |
| Transfer from Saving Account | POST | http://localhost:8080/api/admin/transferFromSaving | None | {"sourceAccountId": 2, "targetAccountId": 1, "amount": 1100, "transferRequest": {"ownerName": "Nestor"}} | Required (Bearer Token) | ADMIN |


## _Testing_
The testing class for AdminServices uses several tools to perform tests on the endpoints. The annotations used in the class are @SpringBootTest, which is used to load the Spring Boot application context, and @Autowired, which is used for dependency injection of the necessary services and repositories.

The class also uses MockMvc to perform HTTP requests and to check the response of the server. The tests performed in this class include:

create_new_CheckingAccount: tests the creation of a new checking account and checks if the returned response contains the expected data.
create_new_savings: tests the creation of a new savings account and checks if the returned response contains the expected data.
delete_account: tests the deletion of an account and checks if the account is no longer present in the database.
testCreateSavingsAcc: tests the creation of a new savings account using an object of type SavingsDTO. This test checks if the actual savings account object is the same as the expected savings account object.
testCreateCreditCardAcc: tests the creation of a new credit card account using an object of type AccountDTO. This test checks if the actual credit card account object is the same as the expected credit card account object.
Overall, the testing class covers the different types of accounts that can be created in the banking application, and it ensures that the accounts can be created and deleted successfully. The tests also check if the returned response from the server contains the expected data.

Certainly. Testing is an essential aspect of software development that I need to improve on. As the current implementation is very basic, I'm aware that there is much room for improvement in terms of coverage, test cases, and methodologies employed. As such, I will make it a priority to learn more about testing techniques and frameworks so that I can improve my skills in this area and create more comprehensive and effective tests for future projects.

## _License_
MIT

**Free Software, Hell Yeah!**
## _Contact_
Developer: Néstor Torres
LinkedIn: https://www.linkedin.com/in/torres-nestor/

[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)

[dill]: <https://github.com/joemccann/dillinger>

