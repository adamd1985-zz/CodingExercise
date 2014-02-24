# Coding Exercise

## Tags

The project is split into to iterations tagged on github, please select the iteration you need to download:

- iteration-1: simple jar file that takes an address book and queries from the command line.
- iteration-2: self contained webapp that presents a control panel and prints results on browser.

## USAGE

In this iteration one may find in the root directory of this project the jar: addressbook-warexec.jar. 
This war is a self-sustained Webapp with an embedded tomcat7. Execute the war as follows:

- java -jar addressbook-warexec.jar
- On tomcat startup, go to the brower's url bar and type: http://localhost:8080/addressbook/exercise
- From the control panel choose any of the three queries available: Get Eldest, Get By Gender and Get DOB Difference; and fill in their details. 

## Iteration 2
- Simple JQuery front end
- Spring MVC to service browser requests.
- Runs same queries and prints these to browser.
- For simplicity addressbook is not changeable and styling and webfront is basic.

## Build and Execution

- MAVEN: 
- mvn clean install
- mvn tomcat7:run: to start the embedded tomcat with the contructed WAR.

## The story 

The user wants to perform a simple set of queries on a lightweight address book.

## The Acceptance Criteria

1. Given a list of people, when the user asks for a certain sex, then all members of that sex in the book are to be given.
2. Given a list of people, when the user asks for the oldest, then the oldest member is returned.
3. Given two people, when user asks the age difference, then the age difference is returned in days.
