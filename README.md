# Hotel management system
## 📝About HMS
REST API application, this Hotel Management System aims to simplify and automate various aspects of hotel operations. Leveraging Java, the Spring Framework, and MYSQL,
the system offers a platform with controllers specifically designed for managing guests and rooms.  Some of the features that have been implemented:
- JWT Authentication,
- Logging in/registering accounts,
- Management of the users and room databases,
- Booking available rooms

# 🛠️ Technologies
- Java
- Spring Boot
- MySql
- Thymeleaf
- JWT Authentication

# ▶ Run
1. Setup MySQL database and update variables in application.properties
2. Open the project in your IDE and run HotelApplication.java
3. Go to the localhost:{your_port}/signup to create user account or log in with your admin account (L:admin, P:admin

# 📄Endpoints
## Login Controller:
### Sign Up:
  - Endpoint: /signup
  - Users can sign up for the system by providing necessary details.
### Sign In:
  - Endpoint: /signin
  - Users can sign in using their credentials, JWT is used for authentication.
### Logout:
  - Endpoint: /logout
  - Allows users to securely log out.

## Panel Controller
### Show Choose Panel Page:
  - Endpoint: /choose-panel
  - Displays the page allowing users to choose a panel
  - Method: GET
### Choose Panel:
  - Endpoint: /choose-panel
  - Method: POST
  - Parameters: panel (String) - Represents the selected panel (e.g., "admin" or "user").
  - Redirects users to the selected panel based on their choice
### Show User Panel:
  - Endpoint: /user-panel
  - Method: GET
  - Displays the user panel
### Show Admin Panel:
  - Endpoint: /admin-panel
  - Method: GET
  - Displays the admin panel

## Room Controller
### Show All Rooms:
  - Endpoint: /room/list
  - Method: GET
  - Displays a list of all rooms
### Add New Room:
  - Endpoint: /room/new
  - Method: GET
  - Displays a form for adding a new room
### Save New Room:
  - Endpoint: /room/add
  - Method: POST
  - Saves a new room to the system
### Edit Room:
  - Endpoint: /room/edit/{id}
  - Method: GET
  - Displays a form to edit an existing room
### Update Room:
  - Endpoint: /room/update/{id}
  - Method: POST
  - Updates an existing room with new information
### Delete Room:
  - Endpoint: /room/delete/{id}
  - Method: GET
  - Deletes a room from the system
### Show Available Rooms:
  - Endpoint: /room/available
  - Method: GET
  - Displays a list of available rooms
### Reserve Room:
  - Endpoint: /room/reserve
  - Method: POST
  - Reserves a room for the current user
### Display Rooms for Current User:
  - Endpoint: /room/rooms
  - Method: GET
  - Displays rooms reserved by the current user
### Cancel Room Reservation:
  - Endpoint: /room/cancel-reservation
  - Method: POST
  - Cancels the reservation of a room

## User Controller
### Show All Users:
  - Endpoint: /user/list
  - Method: GET
  - Displays a list of all users
### Edit User:
  - Endpoint: /user/edit/{id}
  - Method: GET
  - Displays a form to edit an existing user
### Update User:
  - Endpoint: /user/update/{id}
  - Method: POST
  - Updates an existing user with new information
### Delete User:
  - Endpoint: /user/delete/{id}
  - Method: GET
  - Deletes a user from the system
### Show Registration Form:
  - Endpoint: /user/register
  - Method: GET
  - Displays a registration form for new users
### Register User:
  - Endpoint: /user/register
  - Method: POST
  - Registers a new user in the system
