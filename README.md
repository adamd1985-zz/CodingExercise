# Coding Exercise

## USAGE

Note: An assembled JAR is available stand-alone in this project's root dir as "addressbook.jar" for those who wish to skip compilation.

java -jar addressbook ADDRESSBOOKFILE QUERY [QUERYPARAMS, ...]

ADDRESSBOOKFILE
- The location of an address book with comma separated values as contact fields and each new line a new record. 

QUERY: [searchGender, getEldest or getAgeDifference]
- searchGender: search for gender, gender parameter required.
- getEldest: get eldest.
- getAgeDifference: Get age difference in days of two contacts, two name parameters required.

QUERYPARAMS:
- for query searchGender: ["Male" or "Female"].
- for query getAgeDifference these two space separated (use quotes): "Firstname Lastname" "Firstname Lastname"

EXAMPLES
- java -jar addressbook searchGender "Female" "C:\addressbook.csv"
- java -jar addressbook getEldest "C:\addressbook.csv"
- java -jar addressbook getAgeDifference "Adam Darmanin" "Joe Temple" "C:\addressbook.csv"

## The story 

The user wants to perform a simple set of queries on a lightweight address book.

## The Acceptance Criteria

1. Given a list of people, when the user asks for a certain sex, then all members of that sex in the book are to be given.
2. Given a list of people, when the user asks for the oldest, then the oldest member is returned.
3. Given two people, when user asks the age difference, then the age difference is returned in days.

## Iteration 1

- Time estimate 1hr.
- lightweight application with minimal code.
- Embedded/in-memory DB for thin query logic.
- Boot the given file or create contacts in DB.
- An address book contact is made up of name, sex and date of birth.

## Build and Execution

- MAVEN: 
- mvn clean install
- mvn exec:java: for quick execution. Parameters can be found in the build section of the POM file.

## Iteration 2 (Future)
- Simple JQuery front end
- Spring MVC to service browser requests.