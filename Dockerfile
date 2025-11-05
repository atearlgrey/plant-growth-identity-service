# Build stage
FROM maven:3.9.5-eclipse-temurin-21-alpine AS builder
WORKDIR /app

COPY pom.xml .
# Download dependencies
RUN mvn dependency:go-offline -B

COPY src ./src
# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Final Keycloak image with custom SPI
FROM quay.io/keycloak/keycloak:25.0.2

COPY --from=builder /app/target/*.jar /opt/keycloak/providers/

RUN /opt/keycloak/bin/kc.sh build
