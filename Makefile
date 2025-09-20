# Project Makefile for fuel-ingestor + Kafka stack

# Default docker-compose file
COMPOSE_FILE = docker-compose.yml

# Build and start all services
up:
	@echo "🚀 Starting services..."
	docker compose -f $(COMPOSE_FILE) up -d --build

# Stop services but keep volumes/data
down:
	@echo "🛑 Stopping services..."
	docker compose -f $(COMPOSE_FILE) down

# Restart the fuel-ingestor service only
restart:
	@echo "🔄 Restarting fuel-ingestor..."
	docker compose -f $(COMPOSE_FILE) restart fuel-ingestor

# Tail logs (all services or one if SERVICE=...)
logs:
	@echo "📜 Tailing logs..."
	docker compose -f $(COMPOSE_FILE) logs -f $(SERVICE)

# Build only the fuel-ingestor image
build:
	@echo "🔨 Building fuel-ingestor..."
	docker compose -f $(COMPOSE_FILE) build fuel-ingestor

clean:
	@echo "🧹 Cleaning up unused Docker resources..."
	docker system prune -f

seminuke:
	@echo "💥 Semi-nuking containers and volumes..."
	docker compose -f $(COMPOSE_FILE) down -v --remove-orphans

# Completely nuke everything (containers + volumes + images)
nuke:
	@echo "💣 Nuking all containers, volumes, and images..."
	docker compose -f $(COMPOSE_FILE) down -v --rmi all --remove-orphans

# Check status of all services
ps:
	@docker compose -f $(COMPOSE_FILE) ps

# Run curl test against the app endpoint
test:
	@echo "🔍 Testing fuel-ingestor endpoint..."
	curl -s http://localhost:8081/fuel | head -n 20
