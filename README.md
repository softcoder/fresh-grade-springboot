# Spring Boot based Student Editor

This project shows a simple Student Editor using Rest API's on Google Cloud Platform [cloud-java]. 
It uses the [Google App Engine flexible environment][App Engine-flexible].

This application uses Googles Memcached system for storage, so all work will be eventually dunmped from memory
as per Googles shared memcached policies.

### Google Cloud Platform URL Deployment:

https://fresh-grade.appspot-preview.com/

To Deploy Locally run the following from the root project folder:

mvn spring-boot:run

To Deploy to Google Cloud run the following from the root project folder:
(first ensure you have configured your project as per https://github.com/GoogleCloudPlatform/getting-started-java/tree/master/helloworld-springboot)

mvn appengine:deploy

Thanks
