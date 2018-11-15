## The Accountant

The simple implementation of the accountant business process.
The application consists of two modules:
1. ```Http-Server```. That is a very simple realization of http-server. I used the ```netty``` under the hood;
2. ```Accountant```. An application to transfer money from an account to another account;

The ```Http-Server``` does not contain session support so auth is not supported and cookies too.
But the server supports the http request handling functional and it will return the http-500 in case if some wrong occurred and http-404 if a path in URI was not mapped to some handler.

### How to use the http-server as a library in your project?
First of all you should add the maven dependency on it in your project or place the jar into the classpath if you do not use the maven.
The second thing you should do, that is to implements the ```RequestHandler``` interface in your app and map the handler to the URL and http method. See the ```Main``` class in the Accountant module.
That is all about http-server. See its implementation in the GIT repository.

The Accountant is a simple REST aplication to transfer mony between accounts.
Application has REST API:

1. Show accounts info  
GET - /api/account/list  
Content type: application/json  
BODY  
Empty

2. Show specified account info  
GET - /api/account/info?number=...  
Content type: application/json  
BODY  
Empty

3. Transfer money  
POST - /api/transfer  
Content type: application/json  
BODY  
```
{
  "from": "...",
  "to": "...",
  "amount": 100
}
```

4. Show transactions  
GET - /api/transaction/list  
Content type: application/json  
BODY  
Empty  

### How to start application?
First of all you should clone source code from the Github `https://github.com/aefimovrevolute/accountent`
Next, enter into the directory with source code and execute command ```mvn clean compile assembly:single``` to make the jar with dependencies.
Next, enter into the ```accountant``` -> ```target``` and run ```java -jar accountant-1.0.0-jar-with-dependencies.jar``` or ```nohup java -jar java -jar accountant-1.0.0-jar-with-dependencies.jar &``` if you want to release terminal(console).

Or, if you do not want to do the thing that I described above, you can simply open the project in the IDEA, find the ```main.java``` in accountant module and press the green triangle on the task panel.

**Important!!!**  
The server will be bind to ```7899``` port.  
The DDL applied to the in-memory-DB when the server is starting up and base INSERT scripts too.

Sorry, but I did not have time to move some extractable properties into the config file, like binding port.
And application does not contain some validation.

### The main libraries which I use in project
1. Netty;
2. Plain JDBC;
3. Google Guice;
4. H2 Database;
5. JUnit (testing)
6. Mockito (testing)  
And some third-party libraries like as Apache commons etc.