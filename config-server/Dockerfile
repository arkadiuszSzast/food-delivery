FROM adoptopenjdk/openjdk13
ADD config-server-* app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=${ENV}","-jar","/app.jar"]
