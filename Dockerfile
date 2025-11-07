# Stage 1: Build
FROM maven:3.9-eclipse-temurin-17-alpine AS builder

WORKDIR /app

# Copy pom.xml and download dependencies (cached layer)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests -B

# Stage 2: Runtime
# Usando imagen más reciente y segura
FROM eclipse-temurin:17-jre-alpine

# Actualizar paquetes del sistema para reducir vulnerabilidades
RUN apk update && apk upgrade && apk add --no-cache dumb-init

WORKDIR /app

# Create non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy the built jar from builder stage
COPY --from=builder /app/target/demo-1.0-SNAPSHOT.jar app.jar

# Expose port (Render will set PORT env variable)
EXPOSE 8080

# Run the application
# Use dumb-init to handle signals properly and $PORT from Render
ENTRYPOINT ["/usr/bin/dumb-init", "--"]
CMD ["sh", "-c", "java -Dserver.port=${PORT:-8080} -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -jar /app/app.jar"]
