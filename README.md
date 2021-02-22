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

## Running The Tests
#### Current Coverage : 88%
To see the coverage of this project make sure you have an IDE that has JaCoCo installed (such as
IntelliJ or Eclipse, and then run the tests with coverage), or install JaCoCo on your IDE. I have
user 3 types of testing for this project. Unit tests, Integration tests and Acceptance tests.