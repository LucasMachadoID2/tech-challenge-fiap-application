FROM maven AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/tech-challenge-fiap-0.0.1-SNAPSHOT.jar app.jar

# Defina as variáveis de ambiente aqui
ENV SPRING_DATA_MONGODB_HOST=mongodb \
    SPRING_DATA_MONGODB_PORT=27017 \
    SPRING_DATA_MONGODB_AUTHENTICATION_DATABASE=admin \
    SPRING_DATA_MONGODB_USERNAME=admin \
    SPRING_DATA_MONGODB_PASSWORD=password \
    SPRING_DATA_MONGODB_DATABASE=tech-chall

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]