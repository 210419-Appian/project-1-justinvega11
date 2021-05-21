# Banking API
The Banking API will manage the bank accounts of its users. It will be managed by the Bank's employees and admins. Employees and Admins count as Standard users with additional abilities. 
# Technologies Used

- Java 8
- Maven
- Servlets
- PostgreSQL
- JDBC
- Jackson Databind
- Postman
- Apache Tomcat 9
- AWS RDS

# Features
* All Users can create and register an account.
* Employees and admins can view all users information, while only admins can manipulate user information.
* Standard Users can withdraw and deposit to their own account as well as transfer funds to other accounts.
* Every user is allowed to change their own user information.
* Users are unable to withdraw more money than available.
* Users can have a checkings account and a savings account.


# To-do list:
* Password Hashing
* Paging and Sorting endpoints
* Use JSON Web Tokens (JWTs) instead of Session Storage
* Support DELETE requests for Users and Accounts
* Simulate Savings account interest rates.


# Getting Started

1. Install Apache Tom Cat 9
2. Create or use an existing PostGreSQL database and map the location ConnectionUtil.java
3. Install Postman to interact with the API

- git clone https://github.com/210419-Appian/project-1-justinvega11

# Usage

### **Login**
* **URL:** `/login`

* **Method:** `POST`

* **Request:**
  ```json
  {
    "username": String,
    "password": String
  }
  ```

* **Response:**
  ```json
  User
  ```

* **Error Response:**
  * **Status Code:** `400 BAD REQUEST`

  ```json
  {
    "message": "Invalid Credentials"
  }
  ```

### **Logout**
* **URL:** `/logout`

* **Method:** `POST`

* **Response:**
  ```json
  {
    "message": "You have successfully logged out {username}"
  }
  ```
* **Error Response:**
  * **Status Code:** `400 BAD REQUEST`

  ```json
  {
    "message": "There was no user logged into the session"
  }
  ```

### **Register**
* **URL:** `/register`

* **Method:** `POST`

* **Allowed Roles:** `Admin`

* **Request:**
  Note: All fields must be included and the userId should be zero
  ```json
  User
  ```

* **Response:**
  Note: The userId should be updated
  * **Status Code:** `201 CREATED`
  ```json
  User
  ```

* **Error Response:**
  Note: In case username or email is already used
  * **Status Code:** `400 BAD REQUEST`
  ```json
  {
    "message": "Invalid fields"
  }
  ```

### **Withdraw**
* **URL:** `/accounts/withdraw`

* **Method:** `POST`

* **Allowed Roles:** `Admin` or if the account belongs to the current user

* **Request:**
  ```json
  {
    "accountId": int,
    "amount": double
  }
  ```

* **Response:**
  ```json
  {
    "message": "${amount} has been withdrawn from Account #{accountId}"
  }
  ```

### **Deposit**
* **URL:** `/accounts/deposit`

* **Method:** `POST`

* **Allowed Roles:** `Admin` or if the account belongs to the current user

* **Request:**
  ```json
  {
    "accountId": int,
    "amount": double
  }
  ```

* **Response:**
  ```json
  {
    "message": "${amount} has been deposited to Account #{accountId}"
  }
  ```

### **Transfer**
* **URL:** `/accounts/transfer`

* **Method:** `POST`

* **Allowed Roles:** `Admin` or if the source account belongs to the current user

* **Request:**
  ```json
  {
    "sourceAccountId": int,
    "targetAccountId": int,
    "amount": double
  }
  ```

* **Response:**
  ```json
  {
    "message": "${amount} has been transferred from Account #{sourceAccountId} to Account #{targetAccountId}"
  }
  ```

## RESTful Endpoints
These endpoints *are* RESTful, and generally provide basic CRUD operations for Employees/Admins

### **Find Users**
* **URL:** `/users`

* **Method:** `GET`

* **Allowed Roles:** `Employee` or `Admin`

* **Response:**
  ```json
  [
    User
  ]
  ```

### **Find Users By Id**
* **URL:** `/users/:id`

* **Method:** `GET`

* **Allowed Roles:** `Employee` or `Admin` or if the id provided matches the id of the current user

* **Response:**
  ```json
  User
  ```

### **Update User**
* **URL:** `/users`

* **Method:** `PUT`

* **Allowed Roles:** `Admin` or if the id provided matches the id of the current user

* **Request:**
  Note: All fields must be included
  ```json
  User
  ```

* **Response:**
  ```json
  User
  ```

### **Find Accounts**
* **URL:** `/accounts`

* **Method:** `GET`

* **Allowed Roles:** `Employee` or `Admin`

* **Response:**
  ```json
  [
    Account
  ]
  ```

### **Find Accounts By Id**
* **URL:** `/accounts/:id`

* **Method:** `GET`

* **Allowed Roles:** `Employee` or `Admin` or if the account belongs to the current user

* **Response:**
  ```json
  Account
  ```

### **Find Accounts By Status**
* **URL:** `/accounts/status/:statusId`

* **Method:** `GET`

* **Allowed Roles:** `Employee` or `Admin`

* **Response:**
  ```json
  [
    Account
  ]
    ```

### **Find Accounts By User**
* **URL:** `/accounts/owner/:userId`
  For a challenge you could do this instead: `/accounts/owner/:userId?accountType=type`

* **Method:** `GET`

* **Allowed Roles:** `Employee` or `Admin` or if the id provided matches the id of the current user

* **Response:**
  ```json
  [
    Account
  ]
  ```

### **Submit Account**
* **URL:** `/accounts`

* **Method:** `POST`

* **Allowed Roles:** `Employee` or `Admin` or if the account belongs to the current user

* **Request:**
  The accountId should be 0
  ```json
  Account
  ```

* **Response:**
  * **Status Code:** `201 CREATED`

  ```json
  Account
  ```


### **Update Account**
* **URL:** `/accounts`

* **Method:** `PUT`

* **Allowed Roles:** `Admin`

* **Request:**
  Note: All fields must be included
  ```json
  Account
  ```

* **Response:**
  ```json
  Account
# Contributors

Justin Vega
