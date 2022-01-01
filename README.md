# SimpleFourSquare
as the last task of course asks, it is a short presentation of the project:

Video Demo:  <https://youtu.be/cWMjLrcf6_U>

an application that uses FourSquare API to show all the near places in a list and details of any item in a separate screen.
in addition, there is change location listener service which detect location to show the new nearby places!

# API_KEY
you must get an API_KEY from <https://developer.foursquare.com/> and define it as API_KEY in your gradle.properties

# Mock API responses
because the free plan of Foursquare API is too limited I have provide some mock data in assets folder.
to providing mock data most consider to:
1)there is a buildConfig variable named MOCKUP which it shows if Mocking mode is on or off.
2)by add the @MOCKUP annotation for any retrofit API services and define the path of mock file and response code, it shows the mock data.
3)the default path for mock data are in the asset folder.

# rubrics of project 
 - using MVVM Architecture
 - implement Repository pattern
 - Offline first
 - battery and data efficiency
 - clean code

# PR
the doors are open for any pull requests.

the App has implemented simple and basic and there are many technical debt which should be done and several features that it could has.

# TODOS
just listed some of important TODOS:

- add and use DI
- using Flows instead of RXJava
- Using Flows instead of LiveData
- add Test
- show Photos
- show Tips
- using compose?





