# TDL (To DO List)

## Introduction
This is a simple project for managing a generic list that can contain other items (such as a #
shopping list, or a literal to do list). This project provides the user with a web based interface
to create these lists and add items to them (with CRUD functionality for both). The back-end
for is made in Java using the spring framework, the front-end is made in HTML with CSS styling
from the bootstrap framework, with additional functionality provided by the Javascript fetch API.

## Getting Started
### Requirements
* Java (version 11 or above)
* Maven (optional, for compiling from source)

There are 3 ways to run this project, you can either navigate to the releases page and download
the latest release, download the .jar file. Then in the download folder run the command
```java -jar *.jar``` where * is the name of the file. <br>
The next option is to download the source code by clicking the clone button and copying the
link. Then going to the desired download folder and cloning the repository. Once the files are
done downloading open the folder in your chosen editor (at this point you will need to make
sure you have the dependencies downloaded locally, you could download them from online source
or alternatively use ```mvn install``` to download  these repositories) and then run the QaTDLApplication
class file. <br>
The final way to run this project is to compile locally, this can be done by cloning this 
repository locally. Once the files have been downloaded open a terminal in the folder and
use the command ```mvn package``` this will create a target folder and build a .jar file in the 
folder which can be run with ```java -jar *.jar``` where * is the name of the file.

## Operational Instructions
Once you have started the application you can navigate to http://localhost:8393 this will open
a webpage where you can create To Do Lists and Tasks. For this system to work you must first create
a To Do List, when created this will be displayed in a table on the right-hand side. You should
remember the ID of this To Do List (although if you forget this you can press "read all" under the
To Do List section to get all the basket IDs). Then under the task section you can create a
task with a name and a description, and then assign this to a list. All other CRUD functionality 
follows this logic. 

## Tests
#### Current Coverage : 88%
To see the coverage of this project make sure you have an IDE that has JaCoCo installed (such as
IntelliJ or Eclipse, and then run the tests with coverage), or install JaCoCo on your IDE. I have
user 3 types of testing for this project. Unit tests, Integration tests and Acceptance tests.

### Running The Tests

#### Unit Tests
Unit tests allow us to investigate how a class performs on its own
without relying on any other classes, other classes are "mocked"
using mockito, and the methods are tested using JUnit5. These tests
are labelled with *UnitTest and can be run within your IDE as long
as you have JUnit5 and mockito installed. Coverage can be generated
using the JaCoCo plugin (IntelliJ and Eclipse have JaCoCo built in).

#### Integration Tests
Integration tests allow us to investigate how classes interact with 
other object, in this case I have used integration testing to see how
the controller class behaves. This test can also be run in an IDE as
long as the dependencies have been met, they are labelled as *IntegrationTest.

#### Acceptance Test
Acceptance tests show us whether our program behaves as it should
and that the business logic that is wanted has actually been implemented.
This usually revolves around user stories. These tests require the Chrome
browser to be installed. The test scripts can be modified to use another browser,
simple add the driver for the browser into the driver section, change the 
driver in the given script to be the representative driver for that browser (
e.g. change ChromeDriver() to be FirefoxDriver()). You can then run the 
tests, a browser will pop-up, and the tests' logic will be simulated.

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Versioning

We use [SemVer](http://semver.org/) for versioning.

##Author
* **Henry Oliver-Edwards** - *Continued work* - [QAHenryOliver-Edwards](https://github.com/QAHenryOliverEdwards)
