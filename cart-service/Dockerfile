FROM maven:3.9.9-eclipse-temurin-23 as builder

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:23-jdk as runner
WORKDIR /app

COPY --from=builder /app/target/cart-service-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 8085

#ENV EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka-server:8761/eureka/

ENTRYPOINT ["java", "-jar", "app.jar"]
