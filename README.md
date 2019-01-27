# CPEN 221 2016 - Restaurant Database
### Machine Problem 5 for Course CPEN 221
#### By Keenan McConkey and Glyn Fink

**NB:** The original repository for this project was deleted as the course moved on to its next year so the code contribution time and amount from each group member has not been preserved. 


## Project Objective

The objective of this project was to build a command line server in Java capable of processing JSON data from a database of restaurants, and to build a client that retrieves relevant information from the server via structured queries. The server and client are meant to run concurrently on a single system and to allow for multiple client connections at the same time while handling data races. The server uses *k-means clustering* and *least squares regression* to group restaurants and to predict user ratings based on previous data.

## Impemented Simple Requests

+ `RANDOMREVIEW <restaurant name>`: If a client sends a string that begins with `RANDOMREVIEW` then the next word in that string should be interpreted as a restaurant name and the server should return (in JSON format, using the Yelp standard) a random review for that restaurant. If there is to such restaurant, the server should return the string `ERR: NO_RESTAURANT_FOUND`. If the restaurant name matches more than one restaurant then the server should return `ERR: MULTIPLE_RESTAURANTS`. (Note that the restaurant name is not wrapped in `< >`. The use of `< >` is to indicate that the command should be followed by a required argument.)
+ `GETRESTAURANT <business id>`: To this request, the server should respond with the restaurant details in JSON format for the restaurant that has the provided business identifier. If there is no such restaurant then one should use the error message as above.
+ `ADDUSER <user information>`: This request is a string that begins with the keyword (in our protocol), `ADDUSER`, followed by user details in JSON, formatted as suited for the Yelp dataset. Since we are adding a new user the JSON formatted information will contain only the user's name. So the JSON string may look like this `{"name": "Sathish G."}`. The server should interact with the RestaurantDB to create a new user, generate a new userid (it does not have to be in the Yelp userid format, you can use your own format for new users), generate a new URL (although it is a dummy URL) and then acknowledge that the user was created by responding with a more complete JSON string such as:
  `{"url": "http://www.yelp.com/user_details?userid=42", "votes": {}, "review_count": 0, "type": "user", "user_id": "42", "name": "Sathish G.", "average_stars": 0}`. If the argument to the `ADDUSER` command is invalid (not JSON format, missing name, etc.) then the server would respond with the message `ERR: INVALID_USER_STRING`. Note that the server can create a new user if the argument to this command is a valid JSON string and has a field called `name` but also has other information (which can be ignored).
+ `ADDRESTAURANT <restaurant information>`: This command has structure similar to the `ADDUSER` command in that the JSON string representing a restaurant should have all the necessary details to create a new restaurant except for details such as `business_id` and `stars`. The valid error messages (as needed) are `ERR: INVALID_RESTAURANT_STRING` and `ERR: DUPLICATE_RESTAURANT` (if a new restaurant is added at the same location as another restaurant).
+ `ADDREVIEW <review information>`: The last simple command to implement is an `ADDREVIEW` command which has the same principle as the other commands. The possible error codes are `ERR: INVALID_REVIEW_STRING`, `ERR: NO_SUCH_USER` and `ERR: NO_SUCH_RESTAURANT`.

## Structured Queries

The grammar for the query language looks something like this:

```
<orExpr> ::= <andExpr>(<or><andExpr>)*
<andExpr> ::= <atom>(<and><atom>)*
<atom> ::= <in>|<category>|<rating>|<price>|<name>|<LParen><orExpr><RParen>
<or> ::= "||"
<and> ::= "&&"
<in> ::= "in" <LParen><string><RParen>
<category> ::= "category" <LParen><string><RParen>
<name> ::= "name" <LParen><string><RParen>
<rating> ::= "rating" <LParen><range><RParen>
<price> ::= "price" <LParen><range>|<singlePrice><RParen>
<range> ::= [1-5]..[1-5]
<singlePrice> ::= [1-5]
<LParen> ::= "("
<RParen> ::= ")"
```

## Notices

A credit for this project idea goes to the instructors of CPEN 221 for Fall 2016. Some example code was provided by the instructors to get us started but the majority of code is our own. See `instructor_description.md` for orignal project outline given by the instructors.

#### Created by Keenan McConkey and Glyn Fink for CPEN 221 Fall 2016
