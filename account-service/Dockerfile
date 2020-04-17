FROM adoptopenjdk/openjdk14
ADD account-service-* app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=${ENV}","-jar","/app.jar"]
