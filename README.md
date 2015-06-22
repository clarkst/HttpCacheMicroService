# store-locations
## Synopsis

A standalone microservice that collects data every hour from an external service via http and stores it in a local cache which can be accessed by a restful API.

## Features and Design Considerations

* The RESTful API - should service an HTTP get request with a city query parameter. Other methods can be added later after feedback from the product owner.
* Response codes - should be informative to help support the users of the API.
* External data source - resilience to errors and timeouts using Hystrix and monitoring (perhaps using Turbine in the future)
* Storage of data in memory - for the initial implementation a map backed repository will suffice as caching features of for example EHCache or MemCache are not required yet (e.g. LRU expiration,cache miss,off heap,etc). A persistent storage is unlikely to be needed with the current size of the item data structure and data set size (Need get more detail on what is meant by “reasonably long” from the product owner). Synchronised access rather than using a synchronised or concurrent hashmap as a removal of old data and adding new data would need to be atomic.
* Refreshing of data - synchronisation to provide consistency over latency.
* Scheduling refresh - asynchronous calls to update cache hourly.

### Future considerations / backlog items for discussion at review.
* Unit tests as well as integration tests - allow integration tests to be run separately.
* Case sensitivity of search parameter
* Include date of last refresh of data
* Better documentation via Alps,HAL etc
* Expand the rest api to include other data access patterns and cache refresh method
* Hystrix caching and monitoring
* Swagger for service documentation discovery
* Change Whitelabel error page to custom error 
* html - should this done via a view rather than a message converter?
* Security - i noticed the sample data url is https, does the real data service require authentication?

## API Reference

### GET /api/locations?city=[cityname]

Example: http://localhost:8080/api/v1/locations?city=London

Response body:

    [
      {
          "storeName": "Sample Store",
          "address": {
              "postcode": "BR1 1EA",
              "city": "London",
              "street": "12-14 High Street, Bromley",
              "coordinates": {
                  "longitude": "0.01661805",
                  "latitude": "51.40042"
              }
          }
      },
      {
          "storeName": "Sample Store",
          "address": {
              "postcode": "CR0 1LP",
              "city": "London",
              "street": "88-84 & 1084-1088 Whitgift Shopping Centre",
              "coordinates": {
                  "longitude": "-0.099875",
                  "latitude": "51.375692"
              }
          }
      }
    ]




## Installation

Provide code examples and explanations of how to get the project.


## Tests

Describe and show how to run the tests with code examples.



