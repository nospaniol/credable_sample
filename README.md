# credable_sample

The REST Api has been created using Springboot and Documented API's using Swagger.

It was Developed Using Intellij IDEA. MySQL database was used to create this.

To Run The application you need to do the following:

On the project folder under src/main/resources

EDIT THE FOLLOWING:

#Create Database with name "robot_production", also you can edit the DB IP address spring.datasource.url=jdbc:mysql://127.0.0.1:3306/robot_production?autoReconnect=true&&zeroDateTimeBehavior=convertToNull

#You can modify the db user to suit your MySQL user spring.datasource.username=root

#spring.datasource.password= CHANGE TO USER(ABOVE) PASSWORD and uncomment by removing hash, leave it this way if no password is set.

NB; in my case I used the XAMPP MySQL which user is root and has no password

The Hibernate ORM handles database tables creation and other db operations.

You can also change the port in the application properties.

To run the Application, use the below command :

./mvnw spring-boot:run

To access the API Documentation and try out use the below link after the Application is up :

http://127.0.0.1:5500/swagger-ui.html#/
