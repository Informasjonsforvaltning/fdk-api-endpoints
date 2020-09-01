# fdk-api-endpoints

## Requirements
- maven
- java 8

## Run tests
```
% mvn verify
```

## Run locally
```
mvn clean compile
mvn exec:java -Dexec.mainClass="no.fdk.endpoints.Application"
```

Then in another terminal e.g.
```
% curl http://localhost:8080/endpoints?serviceType=Kontoopplysninger&environment=production
```