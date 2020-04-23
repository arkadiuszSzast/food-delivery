FROM adoptopenjdk/openjdk14
ADD gateway-* app.jar
EXPOSE 8060
ENTRYPOINT ["java","-Dspring.profiles.active=${ENV}","-jar","/app.jar"]
