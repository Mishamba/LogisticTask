FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD

COPY pom.xml /build/
COPY src /build/src/

WORKDIR /build/

RUN mvn -B -U -e clean verify

FROM openjdk:8-jre-alpine

WORKDIR /

COPY --from=MAVEN_BUILD /build/target/MyApp-0.0.1-SNAPSHOT.jar /

ENTRYPOINT ["java", "-jar", "MyApp-0.0.1-SNAPSHOT.jar"]
