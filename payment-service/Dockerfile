FROM eclipse-temurin:17-jdk

WORKDIR /app

# Копируем файл сборки JAR в контейнер
COPY target/grpc-payment-service*.jar payment-service.jar

# запуск JAR
CMD ["java", "-jar", "payment-service.jar"]
