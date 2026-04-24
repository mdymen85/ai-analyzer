# MySQL Docker Setup for AI Analyzer

This directory contains the Docker Compose configuration to run a MySQL database for the AI Analyzer application.

## Prerequisites

- Docker installed on your system
- Docker Compose installed

## Usage

### Start the MySQL container

```bash
cd docker
docker-compose up -d
```

### Stop the MySQL container

```bash
cd docker
docker-compose down
```

### View logs

```bash
cd docker
docker-compose logs -f mysql
```

### Connect to MySQL directly

```bash
docker exec -it ai-analyzer-mysql mysql -u root -p ai_analyzer
```

When prompted for password, enter: `rootpassword`

## Configuration

The MySQL container is configured with:
- **Database**: `ai_analyzer`
- **Username**: `root`
- **Password**: `rootpassword`
- **Port**: `3306` (mapped to host port 3306)
- **Root Password**: `rootpassword`

## Data Persistence

Database data is persisted in a Docker volume named `mysql_data`. This ensures your data survives container restarts.

## Health Check

The container includes a health check that verifies MySQL is ready to accept connections before the container is considered healthy.

## Database Initialization

The MySQL container includes an initialization script (`init/data.sql`) that will automatically:

1. **Create the required tables**:
   - `entry` table for transaction data
   - `outbox` table for event publishing

2. **Insert 10,000 sample records**:
   - Random transaction values, operation types (D/C), branch/account numbers
   - Each Entry record gets a corresponding Outbox record with JSON representation
   - Outbox records are marked with status 'NEW' for processing

The initialization runs only on the **first startup** of the container. If you need to re-run it, you'll need to remove the container and volume:

```bash
docker-compose down -v  # Remove container and volumes
docker-compose up -d    # Re-create with fresh initialization
```

## Troubleshooting

If you encounter connection issues:
1. Ensure the container is running: `docker-compose ps`
2. Check container logs: `docker-compose logs mysql`
3. Verify the application.yml configuration matches the container settings
4. Wait for the health check to pass (may take a few seconds on first startup)