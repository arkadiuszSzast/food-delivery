FROM adoptopenjdk/openjdk14
ADD mail-sender-* app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=${ENV}","-jar","/app.jar"]
