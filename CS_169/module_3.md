# Microservices, APIs, Rest

 1. Client server pattern, HTTP routes, uses request-reply protocol to combine a GET/POST with a URI (resource) + optional parameters
 2. API = formal description of what a microservice can do...
 3. Request-reply (client begins every interaction) in the web, vs push architecture (i.e. phone)


 Originally, URL named a static file or html page, but later, URLs became a way to invoke a remote program.
 Before, HTTP contained content to be displayed in browser, but now, HTTP responses contain data.

 APIs
 1. URI endpoint that receives HTTP request
 2. GET/POST https://hostname:port/resource path?query
 3. REST; route = resource and operation

 REST APIs
 - 400 (understand request but have to deny), 500(error, don't get it), 200(success of some sort)
 - use curl command line tool to query
 - CRUDI
    - Create - POST
    - Read - Get
    - Update - Patch or Put
    - Delete - Delete
    - Index or List - Get

# KEY QUESTION...
#### A web app is a service whose resources/operation results can have multiple representations (JSON, HTML); key question is what are the resources, what are their relationships, and what operations are allowed?

##### client request server response ... plus more trivia
1. HTTP is inherently stateless, but you can use curl to save cookies and then pass those cookies in when making a future request, creating a sort of "state".
2. Curl is a SaaS client, Netcat is the Saas Server (netcat is an example of a monitoring service.)
3. The client sends HTTP requests, to server the server generates HTTP responses which are received by the client.


### Rails request-response dynamic order
1. A user types a URL into their browser and hits enter to send a request
2. The Rails router (config/routes.rb) receives the request and maps the URL to its corresponding controller and action to be handled
3. The request is sent to the controller action, which then uses the request to ask the model to fetch data from the database
4. The models sends the requested list of data to back to the controller action
5. The controller action sends the list of data over to the view
6. The view uses the list of data to render the page as HTML
7. The controller returns the HTML back to the browser
8. The browser loads the page for the user to see