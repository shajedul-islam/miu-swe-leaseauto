# LeaseAuto

LeaseAuto is a web application developed using Spring Boot for the backend and Angular for the frontend. This application allows users to view available vehicles and make bookings, while administrators can manage the entire system.

## Prerequisites

Before you can run this project, ensure that the following dependencies are installed and properly configured on your machine:

1. **Java**: Ensure you have the latest version of Java installed. You can download it from [Oracle's official website](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) or use an OpenJDK distribution.

2. **Maven**: Maven is used as the project management and comprehension tool. You can download and install it from [Maven's official website](https://maven.apache.org/download.cgi).

3. **Node.js**: This application uses Node to run the development web server and build the frontend project. You can install Node from [Node.js's official website](https://nodejs.org/).

4. **MySQL**: The application stores its data in a MySQL database. Make sure to have MySQL installed and properly configured. You can get it from [MySQL's official website](https://www.mysql.com/downloads/).

## Configuration

1. **Database Setup**:

   - Create a new MySQL database named `LeaseAuto`.

2. **Update Configuration Files**:
   - Open the `application-dev.yml` file located in the resources directory.
   - Locate the `datasource` section and update the MySQL password (and username if it's different from the default) to match your local MySQL installation.

## Development

### Running Backend

1. Navigate to the project root directory in your terminal.
2. Execute the following command to start the Spring Boot backend:
   ```bash
   ./mvnw spring-boot:run
   ```

### Running Frontend

1. Before starting the frontend, ensure all Node.js dependencies are installed. Navigate to the project root and execute:

   ```bash
   npm install
   ```

2. Start the frontend development server using:
   ```bash
   npm start
   ```

The application will be accessible on `http://localhost:8080` by default, unless you've changed the port configurations.

## Test Application

1. **Login as Admin**:

   - Navigate to the login page.
   - Use the default admin credentials:
     - **Username**: `admin`
     - **Password**: `admin`
   - As an admin, you'll have complete access to all functionalities.

2. **Customer Registration**:

   - From the home page, click on the "Create an Account" link or button.
   - Complete the registration form.
   - After registering, you can log in using your new credentials.

3. **Customer Functions**:

   - **View Vehicles**: After logging in, go to the Vehicles section to see available vehicles.
   - **Book a Vehicle**: Choose your preferred vehicle from the list and proceed to book.
   - **View Bookings**: Go to the "My Bookings" section to view all your bookings.

4. **Admin Features**:
   - As an admin, you have unrestricted access, including:
     - Managing vehicles.
     - Viewing and handling all bookings.
     - User management.
     - Accessing and managing all other application functionalities.
