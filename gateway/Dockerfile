FROM adoptopenjdk/openjdk13
ADD build/libs/gateway-* app.jar
EXPOSE 8060
ENTRYPOINT ["java","-Dspring.profiles.active=${ENV}","-jar","/app.jar"]
