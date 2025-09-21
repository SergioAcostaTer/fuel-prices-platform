# Project Makefile for fuel-ingestor + Kafka stack

COMPOSE_FILE = docker-compose.yml

up:
	@echo "🚀 Starting services..."
	docker compose -f $(COMPOSE_FILE) up -d --build

down:
	@echo "🛑 Stopping services..."
	docker compose -f $(COMPOSE_FILE) down

restart:
	@echo "🔄 Restarting fuel-ingestor..."
	docker compose -f $(COMPOSE_FILE) restart fuel-ingestor market-sinker

reset:
	@echo "🔄 Resetting fuel-ingestor..."
	docker compose -f $(COMPOSE_FILE) down -v --remove-orphans
	docker compose -f $(COMPOSE_FILE) up -d --build

logs:
	@echo "📜 Tailing logs..."
	docker compose -f $(COMPOSE_FILE) logs -f $(SERVICE)

build:
	@echo "🔨 Building fuel-ingestor..."
	docker compose -f $(COMPOSE_FILE) build fuel-ingestor market-sinker

clean:
	@echo "🧹 Cleaning up unused Docker resources..."
	docker system prune -f

seminuke:
	@echo "💥 Semi-nuking containers and volumes..."
	docker compose -f $(COMPOSE_FILE) down -v --remove-orphans

nuke:
	@echo "💣 Nuking all containers, volumes, and images..."
	docker compose -f $(COMPOSE_FILE) down -v --rmi all --remove-orphans

ps:
	@docker compose -f $(COMPOSE_FILE) ps

test:
	@echo "🔍 Testing fuel-ingestor endpoint..."
	curl -s http://localhost:8081/fuel | head -n 20
