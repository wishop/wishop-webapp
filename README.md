wishop-webapp
=============

## Features included


### Hibernate
 * The wishop object-relational mapping (ORM) framework. Maps an object-oriented domain model to a DB table
 * **daoContext-app.xml**: All the relevant Spring beans to setup the DAO layer
 * **hibernate-cfg.xml**: Has all the hibernate properties in order to connect to the DB 


### Spring
* The wishop application framework and inversion of control container, responsible for injecting and wiring up the beans.
* **applicationContext-app.xml**: Contains all the relevant application beans, including model, service, dao, audit, internationalization, cache, aspects, etc
* **WishopApplicationContext**: Provides an access to the Spring Framework context. With it we can access the beans.