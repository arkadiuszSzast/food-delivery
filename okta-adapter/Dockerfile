FROM adoptopenjdk/openjdk14
ADD okta-adapter-* app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=${ENV}","-jar","/app.jar"]
