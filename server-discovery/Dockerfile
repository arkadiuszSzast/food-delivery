FROM adoptopenjdk/openjdk13
ADD build/libs/server-discovery-* app.jar
EXPOSE 8761
ENTRYPOINT ["java","-Dspring.profiles.active=${ENV}","-jar","/app.jar"]
