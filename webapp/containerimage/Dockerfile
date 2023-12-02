FROM eclipse-temurin:21-jre

ARG JAR_FILE=webapp-0.0.1-SNAPSHOT.jar
ARG VERSION=0.0.1
ARG GIT_REVISION=5ede76a285c195fe0c9e6c8624c9254742600b7f

LABEL org.opencontainers.image.authors="hectoc@seism0saurus.de"
LABEL org.opencontainers.image.license="MIT License"
LABEL org.opencontainers.image.url="https://github.com/seism0saurus/hectoc/tree/main/webapp"
LABEL org.opencontainers.image.source="https://github.com/seism0saurus/hectoc/blob/main/webapp/containerimage/Dockerfile"
LABEL org.opencontainers.image.version=${VERSION}
LABEL org.opencontainers.image.revision=${GIT_REVISION}
LABEL org.opencontainers.image.title="Hectoc Challenger"
LABEL org.opencontainers.image.description="A simple single page application that gives you Hectoc challenges and checks your answer"
LABEL org.opencontainers.image.base.name="eclipse-temurin:21-jre"

COPY ${JAR_FILE} application/application.jar
RUN groupadd spring && useradd -g spring spring
USER spring:spring
WORKDIR application
ENTRYPOINT ["java", "-jar", "application.jar"]
