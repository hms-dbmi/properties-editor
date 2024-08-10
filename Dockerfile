FROM maven:3-amazoncorretto-21 AS build

COPY pom.xml .

COPY src src

RUN mvn clean package

FROM amazoncorretto:21.0.1-alpine3.18

# Copy jar and access token from maven build
COPY --from=build target/properties-editor-*-SNAPSHOT-jar-with-dependencies.jar /properties-editor.jar

# Time zone
ENV TZ="US/Eastern"

ENTRYPOINT ["java", "-jar", "/properties-editor.jar"]
