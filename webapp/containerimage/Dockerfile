FROM eclipse-temurin:21-jre
ARG JAR_FILE=webapp-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} application/application.jar
RUN groupadd spring && useradd -g spring spring
USER spring:spring
WORKDIR application
ENTRYPOINT ["java", "-jar", "application.jar"]
