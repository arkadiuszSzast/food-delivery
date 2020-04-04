FROM adoptopenjdk/openjdk13
ADD okta-adapter-* app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=${ENV}","-jar","/app.jar"]
