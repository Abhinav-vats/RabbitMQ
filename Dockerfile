FROM openjdk:8
EXPOSE 8080
ADD target/app-mq.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]