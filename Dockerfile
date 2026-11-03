FROM maven:3-eclipse-temurin-21 as build
WORKDIR /temporal-shift
COPY pom.xml pom.xml
COPY src src
COPY conf conf
RUN mvn package

FROM eclipse-temurin:21-jdk
WORKDIR /temporal-shift
COPY --from=build /temporal-shift/target/stork .
EXPOSE 8080
CMD ["bin/temporal-shift", "--run"]
