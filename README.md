wishop-webapp
=============

## Features included
_This brief document tries to give a simple but effective explanation of how the technologies interact within the wishop application_

### Maven
 * The wishop build automation tool
 * **pom.xml**: file responsible for the build, running the junit tests, managing dependencies
 * **target/**: folder where Maven stores all output of the build.
 * **/home/vagrant/.m2**: local folder where maven stores all the dependencies  
 * **Commands**: On the CLI, go to the wishop-webapp folder and type: `mvn clean install`. It will perform jUnit tests if any is created.


### PostgreSQL
 * The wishop DB. _The world's most advanced open source database in the world_
 * **import.sql**: This file will provide a clean way of maintaining the DB in sync by loading seed data into the database using SQL statements


### Hibernate
 * The wishop object-relational mapping (ORM) framework. Maps an object-oriented domain model to a DB table
 * **daoContext-app.xml**: All the relevant Spring beans to setup the DAO layer
 * **hibernate-cfg.xml**: Has all the hibernate properties for the DB connection 


### Spring Core
 * The wishop application framework and inversion of control container, responsible for injecting and wiring up the beans.
 * **applicationContext-app.xml**: Contains all the relevant application beans, including model, service, dao, audit, internationalization, cache, aspects, etc
 * **WishopApplicationContext**: Provides an access to the Spring Framework context. With it we can access the beans.


### Internationalization
 * **src/main/resources/internationalization/**: Folder where all the properties files are located.
 * **applicationContext-app.xml**: Spring file where the beans are declared
 * **WishopMessages**: Class responsible for printing all the messages from the properties files


### Log4J
 * The wishop logging package for printing log output
 * **log4j.properties**: File responsible for the log4j configuration. It specifies which Level of logging we want for a package/file/project

 
### EH Cache
 * Ehcache is wishop open source cache framework, for boosting performance, offloading the database, and simplifying scalability. _http://ehcache.org/_
 * **ehcache.xml**: responsible for declaring and managing the cache models
 * **applicationContext-app.xml**: Spring file where the beans are declared
 * **Java Annotations**: An example cache metadata can be found on the BaseService/BaseServiceImpl classes  
 
 
### AspectsJ (AOP)
 * AspectJ is an aspect-oriented extension. Allowing wishop to manage mundane tasks, that should be done but the programmer should not need to repeat it throughout the code.
 * **AuditLog.java**: Class responsible for using the AspectsJ. It simply adds the AuditInfo (creation timestamp, creation user id, modification timestamp, modification user id) to all BaseObject classes and creates new entry on the AuditLogRecord. This will provide information about the who/when information against a business object.  


##Still missing

### jUnit

### Spring MVC

### Spring Social

### JSTL 
