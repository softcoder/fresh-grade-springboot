# Spring Boot based Student Editor

This project shows a simple Student Editor using REst API's on Google Cloud Platform][cloud-java]. 
It uses the [Google App Engine flexible environment][App Engine-flexible].

[App Engine-flexible]: https://cloud.google.com/appengine/docs/flexible/
[cloud-java]: https://cloud.google.com/java/
[spring-boot]: http://projects.spring.io/spring-boot/

### Download Maven

These samples use the [Apache Maven][maven] build system. Before getting
started, be sure to [download][maven-download] and [install][maven-install] it.
When you use Maven as described here, it will automatically download the needed
client libraries.

[maven]: https://maven.apache.org
[maven-download]: https://maven.apache.org/download.cgi
[maven-install]: https://maven.apache.org/install.html

### Google Cloud Platform URL Deployed:

https://fresh-grade.appspot-preview.com/

To Deploy Locally run the following from this folder:

mvn spring-boot:run

To Deploy to Google Cloud run the following from this folder:

mvn appengine:deploy

Thanks
