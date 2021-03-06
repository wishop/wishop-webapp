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
 * **Documentation**:
  * http://maven.apache.org/guides/
  * http://maven.apache.org/users/index.html
  * http://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html


### PostgreSQL
 * The wishop DB. _The world's most advanced open source database in the world_
 * **import.sql**: This file will provide a clean way of maintaining the DB in sync by loading seed data into the database using SQL statements
 * **Documentation**:
  * http://www.postgresql.org/docs/9.1/static/index.html
  * http://www.postgresql.org/docs/9.1/static/tutorial.html


### Hibernate
 * The wishop object-relational mapping (ORM) framework. Maps an object-oriented domain model to a DB table
 * **daoContext-app.xml**: All the relevant beans to setup the DAO layer for the Java Application
 * **daoContext-web.xml**: All the relevant beans to setup the DAO layer for the JBoss Application 
 * **hibernate-cfg.xml**: Has all the hibernate properties for the DB connection 
 * **Documentation**: 
  * http://docs.jboss.org/hibernate/orm/4.0/manual/en-US/html/


### Spring Core
 * The wishop application framework and inversion of control container, responsible for injecting and wiring up the beans.
 * **applicationContext-app.xml**: Contains all the relevant application beans, including model, service, dao, audit, internationalization, cache, aspects, etc
 * **WishopApplicationContext**: Provides an access to the Spring Framework context. With it we can access the beans.
 * **Documentation**: 
  * http://static.springsource.org/spring/docs/3.2.x/spring-framework-reference/html/


### Internationalization
 * **src/main/resources/internationalization/**: Folder where all the properties files are located.
 * **applicationContext-app.xml**: Spring file where the beans are declared
 * **WishopMessages**: Class responsible for printing all the messages from the properties files
 * **Documentation**:
  * http://static.springsource.org/spring/docs/3.2.x/spring-framework-reference/html/beans.html#context-functionality-messagesource _(**why not use it?**)_


### Log4J
 * The wishop logging package for printing log output
 * **log4j.properties**: File responsible for the log4j configuration. It specifies which Level of logging we want for a package/file/project
 * **Documentation**:
  * http://logging.apache.org/log4j/2.x/
  * http://www.mkyong.com/spring-mvc/spring-mvc-log4j-integration-example/

 
### EH Cache
 * Ehcache is wishop open source cache framework, for boosting performance, offloading the database, and simplifying scalability. _http://ehcache.org/_
 * **ehcache.xml**: responsible for declaring and managing the cache models
 * **applicationContext-app.xml**: Spring file where the beans are declared
 * **Java Annotations**: An example cache metadata can be found on the BaseService/BaseServiceImpl classes
 * **Documentation**:
  * http://ehcache.org/documentation
  * http://static.springsource.org/spring/docs/3.2.x/spring-framework-reference/html/cache.html#cache-store-configuration-ehcache
 
 
### AspectsJ (AOP)
 * AspectJ is an aspect-oriented extension. Allowing wishop to manage mundane tasks, that should be done but the programmer should not need to repeat it throughout the code.
 * `com.wishop.service.aspects` is the package where all AspectJ advices are defined. Adds the AuditInfo (creation timestamp, creation user id, modification timestamp, modification user id) to all BaseObject classes and creates new entry on the AuditLogRecord. This will provide information about the who/when information against a business object.
 * **Documentation:**
  *  http://static.springsource.org/spring/docs/3.2.x/spring-framework-reference/html/aop.html


### jUnit (TDD) & Spring Test
 * The wishop Test Driven Development (TDD) frameworks
 * **src/test/java**: Package where all the tests should be placed.
 * **Maven integration**: on the CLI, go to the folder and run `mvn clean test`
 * **Documentation**:
  * http://static.springsource.org/spring/docs/3.2.x/spring-framework-reference/html/testing.html#spring-mvc-test-framework
  * https://github.com/SpringSource/spring-framework/tree/master/spring-test-mvc
  * http://blog.zenika.com/index.php?post/2013/01/15/spring-mvc-test-framework


### JBoss AS Integration
 * On Eclipse perform the following tasks:
  * Right click on the project > Properties > Web Project Settings > Change context root to "/" > Save. This will inform Eclipse what is the correct context root.
  * Right click on the project > Properties > Project Web Facets > Activate it > Choose Dynamic Web Module > Save. This will allow Eclipse to do the Run/Debug As Server.
  * Right click on the project > Properties > Deployment Assembly > Add > Java Build Path Entries > Maven
  * Right click on the project > Properties > Deployment Assembly > Add > Folder > `src/main/webapp` and all folders under `java/main/resources`
 * **jboss-web.xml**: This file informs the JBossAS that the context root should be "/" instead of the default "/wishop-webapp/"
 * **persistence.xml**:  This is a standard configuration file in JPA. In JBoss AS the default and only recommended JPA provider is Hibernate. This file configures the EntityManager.
 * **daoContext-web.xml**: All the relevant beans to setup the DAO layer for the JBoss Application
 * **jboss-deployment-structure.xml**: File used for JBoss deployment (_nothing relevant there yet_)
 * **Documentation**:
  * https://docs.jboss.org/author/display/AS71/All+JBoss+AS+7+documentation


### Spring MVC
 * The default package on the project is `com.wishop.mvc`
 * **web.xml**: File where the DispatcherServlet servlet is declared
 * **wishop-servlet.xml**: Configuration file where all the Spring MVC beans are declared
 * **Documentation**:
  * http://static.springsource.org/spring/docs/3.2.x/spring-framework-reference/html/mvc.html
 

### JSTL 
 * The Tag library for the JSP pages.
 * The file `src/main/webapp/WEB-INF/jsp/includes.jsp` contains all the important JSTL tags


### Apache Tiles
 * Apache Tiles is a templating framework built to simplify the development of web application user interfaces.
 * **tiles.xml**: File that holds the Tiles definitions. In addition to this file all Tiles templates are under `src/main/webapp/WEB-INF/tiles`
 * **Documentation**:
  * http://tiles.apache.org/framework/tutorial/index.html
  * http://tech.finn.no/2012/07/25/the-ultimate-view-tiles-3/ 


##Still missing

### Spring Security


### Spring Social



