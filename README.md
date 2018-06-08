# Ticket Service Coding Challenge
## Objective
Implement a simple ticket service that facilitates the discovery, temporary hold, and final reservation of seats within a high-demand performance
venue.

## Design
+ `Venue` class represents a matrix of all seats in the physical venue.
+ Row `0` of the matrix represents the group of seats closest to the stage.
+ `Seat` at location `[0][0]`, the first seat in the first row represents the front-most left-most seat in the venue.
+ `SeatHold` represents a group of seats held for a particular customer. It expires after `expireAfterSeconds` seconds.
+ `toString()` method of `Venue` returns a map of the current booking status of the venue. Seats marked as F, H, B represent the stauses **Free**, **Hold** and **Booked** respectively.

## Assumptions
+ The best seats are the ones closest to the stage. That is, all seats in Row `i` are better than all seats in Row `i+k` since Row `i` is closer to the stage.
+ The system tries to allocate the requested number of tickets in the best possible row.
+ One `SeatHold` can not span more than a length of row. If a group of customers are booking to attend an event together, it makes sense to allocate them seats together only.
+ Bookings larger than length of a row are modelled as multiple `SeatHold` objects. The idea behind this being, if a group of customers can't be seated together, their bookings should be handled seperately as well. 
+ One customer can hold any number of `SeatHold` objects.

## Test Cases Execution
+ The test cases are contained in `src\test\java\edu\buffalo\cse` in the files `SeatHoldTest.java`, `TicketServiceTest.java` and `VenueTest.java`
+ They can be executed in the terminal using Maven from the project root folder by running
>    mvn test

## Possible optimizations for future
+ Multi-threaded API for discovery, hold and reservation. This could be built using Java `ExecutorService` framework.
+ Thread-safe message queue to keep track of incoming requests.This could be built using a `PriorityQueue` with timestamp of the request as priority.
