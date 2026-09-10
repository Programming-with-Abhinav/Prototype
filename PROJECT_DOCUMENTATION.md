# SIH26006 — Technical Prototype Documentation

## What is real and what is a prototype

The application is a functional Spring Boot decision-support prototype. Port constraints, vessel specifications and freight history are **sample data**. The current forecasting formula is intentionally explainable and must not be described as 80–90% accurate until it has been trained and independently evaluated against validated historical market data.

Live weather is obtained through the backend from Open-Meteo when the server has internet access. Vessel-AIS positions are not included because genuine real-time vessel locations require a licensed AIS provider and its API credentials.

## Architecture

`Browser → Spring Boot REST API → Service / recommendation rules → JPA repositories → MySQL`

The API has a small Hikari connection pool (12 maximum connections, batch size 25), pagination capped at 50 records per request, indexed request history, and stateless request handling. This is appropriate for a small 50+ concurrent-user prototype, subject to load testing on the intended machine.

## API endpoints

| Method | Endpoint | Purpose |
| --- | --- | --- |
| GET | `/api/health` | server health check |
| GET | `/api/ports` | port reference data |
| GET | `/api/vessels` | vessel reference data |
| GET | `/api/cargo?page=0&size=20` | paginated saved cargo requests |
| POST | `/api/forecast` | saves cargo input and returns forecast, compatibility, risk and recommendation |
| GET | `/api/weather/{port}` | cached live weather for a configured port |

## Run

1. Install JDK 17+ and Maven 3.9+.
2. Either run `mvn spring-boot:run` in `backend` for the no-setup H2 demo database, or run `docker compose up -d` for MySQL.
3. For MySQL, set these PowerShell environment variables before starting the backend:

```powershell
$env:DATABASE_URL='jdbc:mysql://localhost:3306/sih26006?useSSL=false&serverTimezone=Asia/Kolkata'
$env:DATABASE_USERNAME='freight_user'
$env:DATABASE_PASSWORD='freight_dev_password'
cd backend
mvn spring-boot:run
```

4. Open `frontend/index.html` using Live Server. The forecast form calls `http://localhost:8080/api/forecast`. If the backend is unavailable, it displays a connection message rather than inventing a forecast.

## Safe production path

Before claiming production readiness: add real authentication with hashed passwords and roles, HTTPS, restrictive CORS, rate limiting, audit logs, database backups, secret management, server monitoring, automated tests and load tests. Integrate a contracted AIS provider for live vessel tracking, and validate forecast performance by route, commodity and horizon using held-out data.
