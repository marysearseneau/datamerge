# Datamerger Application Submission

The Datamerger application is responsible for parsing, merging, sorting and reporting on Csv, Json, and Xml data formatted files. The records within each file must have 8 fields representing max hole size, packets serviced and requested, client Guid and address, request timestamp, service Guid and retries request count.

# Design 
The Generated JavaDocs of this application are [here](https://marysearseneau.github.io/datamerge/docs/).

![UML Diagram of Datamerge application](/assets/datamerge-uml.png "UML Diagram of Datamerge application")

## Datamerger
The Datamerger class is the entry point of this project. It is responsible for loading a list of RecordParser objects during construction. During processing, all records will be filtered, sorted by request time and written out to a final report file called `final-report.csv`. Additionally, a tally of Service Guid instances is counted and written to the console.

## DataRecord
The DataRecord model object encapsulates a unified representation of records found in each file format. ( JSON, CSV, and XML ). Typically, this object would follow the Data transfer object ( DTO ) design pattern, as it does not have any specific behavior defined except for storage, retrieval, formatting, serialization and deserialization of its own data.

## RecordParser Interface
The Record Parser interface defines parsing behavior that iterates through an underlying data format, supplying a generalized List of DataRecord objects to be consumed by calling classes. The implemented parsing classes assume the responsibility of reading underlying data formats, preparing/formatting record fields and creating the resulting list of DataRecord objects.

## CsvRecordParser
The CSV Record Parser is responsible for iterating through an arbitrary Comma-Separated List of records and parsing them into a List of DataRecord objects as defined by the required parse() method in it's RecordParser interface.

## JsonRecordParser
The JSON Record Parser is responsible for iterating through an arbitrary Comma-Separated List of records and parsing them into a List of DataRecord objects as defined by the required parse() method in it's RecordParser interface.

## XmlRecordParser
The XML Record Parser is responsible for iterating through an arbitrary Comma-Separated List of records and parsing them into a List of DataRecord objects as defined by the required parse() method in it's RecordParser interface.

# Build and Execute
This application is build using a standardized Maven installation which inherits simple library management and build lifecycle. If we observe the pom.xml file, we can see that the following dependencies exist:

	org.apache.commons:commons-csv:1.5 - Used to parse and write Csv files
	com.googlecode.json-simple:json-simple:1.1.1 - Used to parse and write Json files

Additionally, 2 build plugins are included, the exec-maven-plugin, which will execute the program's entry point during the maven 'test' phase, and the maven-javadoc-plugin which will build the JavaDoc files from the application source comments (see 'target/apidocs/index.html' after a successful build)

## Steps to build and run

### Environment:
  - Make sure that Apache Maven is installed in your environment (https://maven.apache.org/download.cgi).
  - Make sure JDK 1.8 is insatlled in your environment (https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
1. To perform a full build execute `mvn clean install` from the command line. This will download all project dependencies, compile, build and package the project source, build and package the JavaDoc from source ( see 'target/apidocs/index.html' ), and execute the entry point class, `com.mariner.datamerge.Datamerger`, of the application.
2. To perform only an execution of the entry point run the command `mvn test`. This will create the resulting file `final-report.csv` and output a Service Guid Summary report to the command terminal. The final output should appear as follows:

    ```
    Final report has been rendered to final-report.csv

    ----- Service Guid Summary -----
    60b84364-645b-444c-90ed-879d893f7920: 17
    1efced85-b0e3-464b-ac63-472d35909c05: 14
    e49b6654-fca2-47b2-b816-4bb7e09c6fa3: 14
    fa7b1137-1e85-4d26-aa9d-8da7a812cad6: 18
    e16b762f-d0a2-4e1f-95a2-2ee9985c6aff: 11
    586b3947-c63c-4054-a437-d3f8a6836bcf: 17
    dd7691ef-4b29-4992-b48d-cb449dfc65b6: 15
    1d0a29e3-b3b0-4f0d-a684-a0f025955a4d: 11
    db7b9cde-0a4b-459a-a301-88a447410499: 17
    26ea4cf2-ed09-43d4-a8e6-e3ff1f8e5893: 18
    94f93cba-de8c-4fb6-a03c-d52c10f81247: 12
    90e5b65b-4f12-4bb2-9554-3f5969f4d78b: 14
    50b89ee3-f4cb-46d9-8d5c-319f6a032406: 9
    2536e29e-3570-4d53-be7c-b9d059c632a7: 12
    52073b51-cd51-4438-bd54-3bce3a7c239f: 19
    92aa1c5c-98ab-49f8-a427-4c0bc49d6872: 18
    0d5157f7-8012-4455-b66f-4f81d14092b3: 18
    0ee908d7-d767-4da5-9cbe-fc78e9c6a192: 10
    8151ff61-4777-4187-b719-e750b91281c8: 18
    318e9d5d-53f9-43eb-a9d1-88b5c51687f8: 15
    d15e4790-67f7-4488-a28d-32ced8673c58: 18
    0a5f0733-d058-4738-8666-f0468c5ee9c3: 20
    d224de85-cd41-4f03-bff6-de9815877975: 14
    19f26076-5d21-4200-b26c-0f32cd87ebde: 14
    a6c5d611-45be-43bf-a027-c1e98a89c8f6: 14
    ef48c441-4a26-4f90-97cf-cc3d0665b814: 9
    5d35c640-9cf3-43ec-ae1a-5feebed26a37: 15
    147fe620-dd62-4eb6-9a0d-8b8ca1e743b1: 20
    f78fb4fb-3e02-4f42-9c15-45480342cba9: 11
    dc81cacc-af4f-4b0a-8a10-34e106ffdf23: 12
    edf32a15-bb1c-42f4-9fe6-afa9f9b21522: 8
    3fb4c246-cf94-4f60-bf47-b709b288f9b2: 13
    4ab70dbe-f5e7-4688-aa5c-2ae75faf6c03: 17
    7d619a45-2b4d-4a54-9e85-6913c9545e34: 17
    98279a79-c822-45b4-8429-45ce97a336e5: 20
    601152f5-477a-4ff3-afdb-8bf315341254: 23
    9e3d54d8-bcea-4bd5-94f5-bd636df152fa: 17
    4a949998-0e79-4ec1-9894-11360712fc45: 12
    35501e3f-e06c-4cb5-9062-e8611a591cab: 14
    7f386bb2-4bbf-4f14-b56e-3dad78f28674: 11
    a944a262-6fdb-4f7f-a510-d123ed9618e6: 14
    fa005302-61ce-44ca-88a5-015fd4392f37: 15
    3cc76b74-7d16-4651-9699-34332a56f6e7: 15
    8105e514-c0d5-40ba-8c7a-90bf998ba9e4: 21
    c5a5edbb-338d-449a-99cb-1baeba48abb2: 12
    bed314cb-4c59-41ee-9c23-3155a80e2bc3: 18
    8232ac92-6020-4101-88c0-74d51ea1542b: 14
    6c7444d3-9bca-46d7-a8c6-96939981ae01: 12
    1664d39d-45f1-4939-87e2-b7221e30d157: 13
    fbce0713-676d-4dc9-a33b-25ff937c1ae3: 11
    caaca31e-bee2-4ed3-8e72-5dab24079744: 14
    a5adb83b-0380-4618-aef6-7179e39eff26: 6
    185ec7fa-2efc-424e-835f-de882857f55e: 9
    4cb309f6-8c9a-4f61-8544-6b458e42f04c: 16
    b8cf6fd8-06e2-4e5b-8ed6-6efc8e0ecda3: 21
    d70c87de-7837-450c-9965-17c9f366d183: 19
    b3be9c1c-5712-420d-a359-e9ef0e93c9c5: 22
    8de44210-f265-4f16-a94b-8b0eadc0de23: 15
    0342f6df-6e8c-48c3-8390-667eb678638b: 12
    d7d6b499-509a-4c08-b95e-68d9662f25ca: 9
    ```

# Existing Instructions

Below are the existing instructions for building the solution

# Data sorting and filtering

Read the 3 input files reports.json, reports.csv, reports.xml and output a combined CSV file with the following characteristics:

- The same column order and formatting as reports.csv
- All report records with packets-serviced equal to zero should be excluded
- records should be sorted by request-time in ascending order

Additionally, the application should print a summary showing the number of records in the output file associated with each service-guid.

Please provide source, documentation on how to run the program and an explanation on why you chose the tools/libraries used.

## Submission

You may fork this repo, commit your work and let us know of your project's location, or you may email us your project files in a zip file.
