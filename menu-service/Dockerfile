FROM adoptopenjdk/openjdk14
ADD menu-service-* app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=${ENV}","-jar","/app.jar"]
