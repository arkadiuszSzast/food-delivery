FROM adoptopenjdk/openjdk14
ADD jwt-service-* app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=${ENV}","-jar","/app.jar"]
