FROM adoptopenjdk/openjdk11
ARG JAR_FILE
COPY target/fastmessage-statistic-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]