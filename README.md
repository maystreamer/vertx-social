# vertx-social
Sample vertx web application to showcase the usage of [vertx-boot](https://github.com/greyseal/vertx-boot) library. 

## Getting Started

Git clone the project on your local machine and import it to your favorite ide.

### Prerequisites

For runnning this, you will need
- Java 1.8
- Gradle support - In Eclipse editor, goto help -> eclipse marketplace -> search for buildship (buildship gradle integration) and install it.
- [Vertx-Boot](https://github.com/greyseal/vertx-boot) library. 

## Brief
This sample application make use of [Vertx-Boot](https://github.com/greyseal/vertx-boot) library to expose a rest API **/runner/api/ping**
- **HttpServerVerticle**       -> HTTP Server verticle to handle incoming HTTP Requests.
- **PingHandler**              -> Sample handler to send a "OK" Json response.

## Running the app

For running the app, (IDE used here is IntelliJ)
- Open **appConfig.json** file and set the "http_server_port" as per your choice.
- Once, changes are done in **appConfig.json**, add/edit Run/Debug Configurations for the project("vertx-social") and set:
  * **Main class**: com.greyseal.vertx.boot.AppLauncher
  * **VM options**: -Dlogback.configurationFile=file:../vertx-social/src/main/resources/logback.xml
  * **Program arguments**: -run com.greyseal.vertx.boot.verticle.MainVerticle -conf ../vertx-social/src/main/resources/appConfig.json 
  * **Environment variables**: ENV=dev 
 <br /><br /> After setting the variables, Run/Debug the project.<br />
- If app starts successfully, then try <br />
**Type:** *GET http://localhost:8080/runner/api/ping* <br />
**Headers:** *Content-Type: application/json*;  *CORRELATION-ID: c1d887063c3e492b9951b0479fadddda* <br />

Response<br />
```
{
    "status": "OK"
}
```
That's it.

## Built With

* [Vertx](http://vertx.io/) - The web framework used
* [Gradle](https://gradle.org/) - Dependency Management
