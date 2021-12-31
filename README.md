# SimpleFourSquare
Video Demo:  <URL HERE ####>
an application that uses FourSquare API to show all the near places in a list and details of any item in a separate screen.

# API_KEY
you must get an API_KEY from <https://developer.foursquare.com/> and define it as API_KEY in your gradle.properties

# Mock API responses
because the free plan of Foursquare API is too limited I have provide some mock data in assets folder.
And by add the @MOCKUP annotation for any retrofit API services and define the path of mock file and response code, it shows the mock data.
And there is a buildConfig variable named MOCKUP which it shows if Mocking mode is on or off.

# rubrics of project 
 - using MVVM Architecture
 - implement Repository pattern
 - Offline first
 - battery and data efficiency
 - clean code

# PR
the doors are open for any pull requests.

# TODOS
- add and use DI
- using Flows instead of RXJava
- Using Flows instead of LiveData
- add Test
- show Photos
- show Tips






