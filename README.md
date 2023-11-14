# Data Validator

[![Actions Status](https://github.com/SerKonstantin/java-project-78/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/SerKonstantin/java-project-78/actions)
[![Actions Status](https://github.com/SerKonstantin/java-project-78/actions/workflows/main.yml/badge.svg)](https://github.com/SerKonstantin/java-project-78/actions)
[![Maintainability](https://api.codeclimate.com/v1/badges/b1c94be4e6e0c705ee79/maintainability)](https://codeclimate.com/github/SerKonstantin/java-project-78/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/b1c94be4e6e0c705ee79/test_coverage)](https://codeclimate.com/github/SerKonstantin/java-project-78/test_coverage)

Data Validator is a library that provides ability to validate data based on customizable requirements. It offers support for validating strings, numbers, and maps, allowing to define specific rules for each type of data.

This project is created for educational purposes.

## Usage example

~~~
Validator v = new Validator();

// Create validation rules for a map with data
// Each field is set up with its own rules
Map<String, BaseSchema> validationRules = new HashMap<>();

// Field "name" should be string and cannot be blank
validationRules.put("name", v.string().required());
// Field "age" should be a positive number if used, but can be left blank (null)
validationaRules.put("age", v.number().positive());

// Get object to check map with data using created rules
MapSchema schema = v.map().shape(validationRules);

// Check objects
Map<String, Object> user1 = Map.of(
        "name", "David",
        "age", 25
);
schema.isValid(user1); // -> true

Map<String, Object> user2 = Map.of(
        "name", "Ann",
        "age", null
);
schema.isValid(user2); // -> true, age is not required

Map<String, Object> user2 = Map.of(
        "name", "",
        "age", 21
);
schema.isValid(user3); // -> false, user name cannot be empty
~~~

## Features

- All validation types:
    - `required()`: Set checking data for being not null (or empty string).
    - `isValid()`: Perform actual checking with boolean return.


- String validation:
    - `string()`: Set up validator to check a string.
    - `minLength(int length)`: Set the minimum required length for a string.
    - `contains(String substring)`: Validate if a string contains a specific substring.


- Number Validation:
    - `number()`: Set up validator to check an integer.
    - `positive()`: Ensure that a number is positive.
    - `range(int start, int end)`: Define a range within which the number should fall.


- Map Validation:
    - `map()`: Set up validator to check a map.
    - `sizeof(int exactSize)`: Verify the exact size of a map.
    - `shape(Map<String, BaseSchema> validationRules)`: Define a map by specifying field names and their corresponding validation rules.


