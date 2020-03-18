FROM adoptopenjdk/openjdk13
ADD build/libs/config-server-* app.jar
EXPOSE 8888
ENTRYPOINT ["java","-jar","/app.jar"]
