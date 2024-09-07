# Checking the Performance of an Endpoint
##  Karate
```bash
mvn clean test -Pkarate
```
* https://github.com/karatelabs/karate
* env:
  * System.setProperty("karate.env", "pre-prod"); 
    ```
    mvn test -Pkarate  -Dkarate.env=e2e 
    ```
  * https://github.com/karatelabs/karate?tab=readme-ov-file#environment-specific-config
  * https://github.com/karatelabs/karate/tree/master/karate-junit5
  * https://github.com/karatelabs/karate/tree/master/karate-e2e-tests
* Karate UI  
  * https://github.com/karatelabs/karate/tree/master/karate-core

##  gatling
```bash
 mvn test -P gatling 
```
