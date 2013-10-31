RESTfulRouting
==============


Application is deployed to: http://sendhubrouter.herokuapp.com/


Usage
=====

POST a request to http://sendhubrouter.herokuapp.com/route


The header must specify Content-Type : application/json


The body must contain a JSON document which conforms to [prescribed format](https://github.com/thebrettd/RESTfulRouting/blob/master/src/main/resources/schema/input.json))

Design
======

* The REST API design should follow best practices when it comes to designating uniformresource identifier, the HTTP verbs used to interact with the API (GET/POST/PUT/DELETE etc).

Most of the HTTP verbs here are typically used for CRUD operations on server resources. As this application is essentially
an RPC tool, I have only implemented support for POST, and PUT.

Algorithm
============
* Provide an analysis of the computational complexity of the algorithm you used to solve the problem. What is its complexity?

In terms of time, this alogrithm is O(c) with respect to input size. The number of operations it will perform is determined by the number of Relays available.
In this case, there are 4 relays provided, so 4 modular division operations are performed to determine how many messages can be sent to each relay.

In terms of space, this algoritm is O(n) with respect to the input size - an Array element is created for each valid input.

* Can you categorize this problem into the same category of other well known problems?

This problem is similar to a typical "Linear Programming Problem" where you need to find the maximum or minimum value of
a linear equation subject to some number of constraints.

* Is it possible to optimally solve this problem in polynomial time? What about with other throughput values?

According to research, a polynomial time algorithm DOES exist for Linear Programming problems, provided the constraints are not required to be integers. 
If you constrain your variables to have integer values, a polynomial time algorithm is not currently know, and unlikely to exist.

http://www.cs.princeton.edu/~rs/AlgsDS07/22LinearProgramming.pdf



