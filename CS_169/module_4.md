## Model View Controller 

1. Models = data manipulated by app (storing, operating, changing); typically have a model for each type of entity (i.e. movie db = movies, attendees, reviews, ... other entities)
2. Views = user-facing, contain info about models which users can interact with
3. Controllers = mediate interaction in both direction, controller action invoked when user interacts with view. One controller per model,

In SaaS apps on the web, controller actions / view contents are transmitted using HTTP.

##### Studied SQL, databases, RDBMS tech, etc...

##### Active Record 
- The MVC design pattern focuses on dividing program logic, and is not as concerned with the technical disparities within the implementations themselves, such as differences among languages and platforms.

##### Trip through a rails app
1. Routes map incoming URLs to controller actions + extract optional parameters
2. Controller actions set instance variables, visible to views
3. Controller actions eventually renders a view.