FROM adoptopenjdk/openjdk13
ADD company-service-* app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=${ENV}","-jar","/app.jar"]
