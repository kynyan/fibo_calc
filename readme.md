Fibonacci Calculator
----------------------------------------

This is a simple app which uses matrix exponentiation algorithm  to calculate Fibonacci number by given index.

#Run calculator in Docker

* Install Docker
* Checkout source code with git or simply download .zip package and unpack it
* Open terminal (GitBash on Windows)
* Go to the project root directory (/fibo_calc)
* Execute `sh run.sh`
* After application is up, it is available at localhost:8099. 
You may change to any other port by editing docker-compose.yml

#Run calculator with Maven

* Install Maven
* Checkout source code with git or simply download .zip package and unpack it
* Uncomment tomcat7-maven-plugin lines in pom.xml
* Open terminal (GitBash on Windows)
* Go to the project root directory (/fibo_calc)
* Execute `mvn package`
* After application is up, it is available at localhost:8099.
You may change to any other port by changing config of tomcat7-maven-plugin