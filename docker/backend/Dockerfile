# ==========================
# Build
# ==========================
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /build

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

# application.properties não versionado; imagem sempre usa o template com placeholders
RUN cp src/main/resources/application.properties.example \
       src/main/resources/application.properties

RUN mvn clean package -DskipTests

# ==========================
# Runtime
# ==========================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /build/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]