
# Product ordering demo API

## Prerequisites
* [Java 11](https://openjdk.java.net/)
* [Docker](https://www.docker.com/)
* [IntelliJ IDEA](https://www.jetbrains.com/idea/)
* [Postman](https://www.postman.com/)


## Database

### Start database
````bash
cd <project folder>
docker-compose up -d  --build postgres
````
### Start database and migrations
````bash
cd <project folder>
docker-compose up -d  --build postgres flyway
````

### Close database
````bash
docker-compose down
````

## Running application

### Running development project

Under IntelliJ Idea find class com.company.Application and click "Run Application".

### Running *.jar file

1) Run the database if it has not already been done.
2) Run Gradle task "build". This will create jar file into build\libs folder.
3) On a Windows machine, run the following commands:
````bash
set JAVA_HOME=<Path to java installation folder>
set outputPath=<Path to log file>
set pathToJar=<Path to project folder>\order-api\build\libs\order-api-1.0-SNAPSHOT.jar

%JAVA_HOME%\bin\java -jar %pathToJar% >> %outputPath%

# stop application
# ctrl+c
````
if the appropriate java is on the system path, then run:
````bash
java -jar %pathToJar% >> %outputPath%
````