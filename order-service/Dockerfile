FROM eclipse-temurin:17-jdk

WORKDIR /app

# Копируем файл сборки JAR в контейнер
COPY target/grpc-order-service*.jar order-service.jar

# запуск JAR
CMD ["java", "-jar", "order-service.jar"]
