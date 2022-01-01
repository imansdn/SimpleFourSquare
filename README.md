# CS50 final project: SimpleFourSquare
it is an android application that uses FourSquare API to show all the near places in a list and show details of each item in another screen by click on that.
in addition, there is a change location listener service that detects location to show the new nearby places!

as the last task of course asks, it is a short presentation of the project:

Video Demo:  <https://youtu.be/cWMjLrcf6_U>


# first page - places items
- detect location when it is changed
- fetch nearby places from Api by demand based on pagination
- store records inside the database
- if there is no network show the recent places from the database

# second page - details page
displaying:

- tips and reviews of other users about the item
- pictures
- Rates
- Likes
- address
- link to the main app
- open direction on Google map

# API
check out the Api here : <https://developer.foursquare.com/reference/place-details>
there are some edge-cases in implementing app because the structure of Api, for instance there is no way to get all the details of a place in one request. there is an end-point to return places based on latitude and longitude but each item just include a brief details of that place and for getting image and tips it needs to send another request separately.

# API_KEY
you must get an API_KEY from <https://developer.foursquare.com/> and define it as API_KEY in your gradle.properties

# Mock API responses
because the free plan of Foursquare API is too limited I have provided some mock data in the assets folder.
I created an annotation named "MOCKUP" and a wrapper for retrofit CallAdapterFactory.

to providing mock data most consider to:

1)build config variable named MOCKUP shows if the Mocking mode is on or off.

2)by adding the @MOCKUP annotation for any retrofit API services and defining the path of the mock file and response code, it shows the mock data.

3)the default path for mock data is in the asset folder.

# rubrics of project
- using MVVM Architecture:
  view model must be totally separate from view and just emit data to outside.
- implement Repository pattern:
  check the offline data when there is no network.
- battery efficiency:
  because there is a location change listener in project it's a must to shutting it down when there is no need.
 -data efficiency:
  pagination it's one way to help user less data uses, getting 10 item per page it's a good choice.
- clean code:
  consider to clean architecture

# PR
the doors are open for any pull requests.

the App has implemented simple and basic and there are many technical debts which should be done and several features that it could have.

# TODOS
just listed some of the important TODOS:

- getting place images from Api
- getting tips of places from Api
- make a good combination and sequence of fetching data
- add and use Dependency injection
- using Flows instead of RXJava
- Using Flows instead of LiveData (based on jetpack life-cycle dependency)
- show Photos
- show Tips
- redesign UI(using compose?)
- add unit Test
- let user control location detector service from notification





