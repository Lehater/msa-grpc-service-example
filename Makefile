# Названия приложений и их JAR-файлов
ORDER_SERVICE = order-service
PAYMENT_SERVICE = payment-service

ORDER_JAR = target/$(ORDER_SERVICE).jar
PAYMENT_JAR = target/$(PAYMENT_SERVICE).jar

# Названия Docker-образов
ORDER_IMAGE = $(ORDER_SERVICE)
PAYMENT_IMAGE = $(PAYMENT_SERVICE)

# Файл Docker Compose
DOCKER_COMPOSE_FILE = docker-compose.yml

# Команды
.PHONY: all build jar docker run stop clean

# Полный процесс сборки всех сервисов: JAR + Docker
all: build

# Сборка JAR-файлов
jar: jar-order jar-payment

jar-order:
	@echo "Building JAR for $(ORDER_SERVICE)..."
	cd $(ORDER_SERVICE) && mvn clean package -DskipTests

jar-payment:
	@echo "Building JAR for $(PAYMENT_SERVICE)..."
	cd $(PAYMENT_SERVICE) && mvn clean package -DskipTests

# Создание Docker-образов
docker: docker-order docker-payment

docker-order: jar-order
	@echo "Building Docker image for $(ORDER_SERVICE)..."
	cd $(ORDER_SERVICE) && docker build -t $(ORDER_IMAGE) .

docker-payment: jar-payment
	@echo "Building Docker image for $(PAYMENT_SERVICE)..."
	cd $(PAYMENT_SERVICE) && docker build -t $(PAYMENT_IMAGE) .

# Полная сборка (JAR + Docker) для всех сервисов
build: docker

# Запуск приложений через Docker Compose
run:
	@echo "Starting services with Docker Compose..."
	docker-compose -f $(DOCKER_COMPOSE_FILE) up -d

# Остановка приложений
stop:
	@echo "Stopping services..."
	docker-compose -f $(DOCKER_COMPOSE_FILE) down

# Очистка (остановка контейнеров, удаление JAR и Docker-образов)
clean: stop clean-jar clean-docker

clean-jar:
	@echo "Cleaning JAR files..."
	cd $(ORDER_SERVICE) && mvn clean
	cd $(PAYMENT_SERVICE) && mvn clean

clean-docker:
	@echo "Removing Docker images..."
	docker rmi -f $(ORDER_IMAGE) || true
	docker rmi -f $(PAYMENT_IMAGE) || true
