FROM eclipse-temurin:24-jre

ARG JAR_FILE=webapp.jar

COPY ${JAR_FILE} application/application.jar
RUN groupadd spring && useradd -g spring spring
USER spring:spring
WORKDIR application
ENTRYPOINT ["java", "-jar", "application.jar"]
