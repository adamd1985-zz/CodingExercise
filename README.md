# Coding Exercise

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

### Build and Execution

- MAVEN: 
-- mvn clean install
-- mvn exec:java 