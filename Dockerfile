FROM eclipse-temurin:17-jdk AS build

RUN apt-get update && apt-get install -y maven
WORKDIR /app
COPY pom.xml ./
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/drone-service-api-1.0.0-SNAPSHOT.jar ./
EXPOSE 8091

CMD ["java", "-jar", "drone-service-api-1.0.0-SNAPSHOT.jar"]
