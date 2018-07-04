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
- **HttpServerVerticle**       -> Default verticle from the vertx-boot library. Can be extened for the functionality.
- **MessagingVerticle**        -> Sample Messaging verticle to send messages (no functionality for now).
- **ServerStatusHandler**      -> Sample handler to send a "OK" Json response.
- **PingHandler**              -> Default handler from the vertx-boot library to send a "OK" Json response.

## Running the app

For running the app, (IDE used here is IntelliJ)
- Open **appConfig.json** file and set the "http_server_port" as per your choice.
- Once, changes are done in **appConfig.json**, add/edit Run/Debug Configurations for the project("vertx-social") and set:
  * **Main class**: com.greyseal.vertx.boot.AppLauncher
  * **VM options**: -Dlogback.configurationFile=file:../vertx-social/src/main/resources/logback.xml
  * **Program arguments**: run com.greyseal.vertx.boot.verticle.MainVerticle -conf ../vertx-social/src/main/resources/appConfig.json 
  * **Environment variables**: ENV=dev 
 <br /><br /> 

After setting the variables, Run/Debug the project. If app starts successfully, then try <br /><br /> 
**Type:** *GET http://localhost:8080/runner/api/ping* <br />
**Headers:** *Content-Type: application/json*;  *Trace-Id: c1d887063c3e492b9951b0479fadddda* <br />

Response<br />
```
{
    "status": "OK"
}
```
That's it.

## Vertx-Social usage
- Currently added support for LinkedIn/Github apis. To run/debug...<br  /><br  />
* To get **Authorization** url <br /><br />
**Type:** *POST http://localhost:8080/runner/api/social/authorizationurl?provider=LINKEDIN* <br />
**Headers:** *Content-Type: application/json*;  *Trace-Id: c1d887063c3e492b9951b0479faddddu* <br />
**Request body:**
 ```
 {
   "scopes": [
     "r_basicprofile",
     "r_emailaddress",
     "rw_company_admin",
     "w_share"
   ],
   "redirect_uri": "{redirect_uri}",
   "state": "STATE"
 }
 ```
 **Response:**
 ```
 {
    "authorizationURL": "{authorization_url}"
}
 ```
Click on **Authorization url** to get the Authorization code. <br /> <br />
* To get **Access Token** url <br /><br />
**Type:** *POST http://localhost:8080/runner/api/social/accesstoken?provider=LINKEDIN* <br />
**Headers:** *Content-Type: application/json*;  *Trace-Id: c1d887063c3e492b9951b0479faddddu* <br />
**Request body:**
 ```
{
"code":"{authorization_code}",
"redirect_uri":"{redirect_uri}"
}
 ```
 **Response:**
 ```
{
    "access_token": "{access_token}",
    "expires_in": 5163999,
    "expires_at": 1561910752077
}
 ```
Get the **access_token** and pass it to the concrete apis
* To get **Linkedin user profile** url <br /><br />
**Type:** *POST http://localhost:8080/runner/api/social/profile?provider=LINKEDIN* <br />
**Headers:** *Content-Type: application/json*;  *Trace-Id: c1d887063c3e492b9951b0479faddddu* <br />
**Request body:**
 ```
{
"access_token":"{access_token}"
}
 ```
 **Response:**
 ```
{
    "firstName": "ABC",
    "headline": "Software Engineer",
    "id": "8mAy5C1epf",
    "lastName": "XYZ",
    "siteStandardProfileRequest": {
        "url": "{url}"
    }
}
 ```
 
Try same for **Github** by updating provider=LINKEDIN t0 provider=GITHUB


## Built With

* [Vertx](http://vertx.io/) - The web framework used
* [Gradle](https://gradle.org/) - Dependency Management
