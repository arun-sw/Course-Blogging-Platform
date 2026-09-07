FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8030

ENTRYPOINT ["java", "-jar", "app.jar"]