# SIH26006 — Intelligent Freight Forecasting & Vessel Chartering

Phase 1 frontend prototype for Smart India Hackathon 2026. It demonstrates a polished, responsive decision-support interface using clearly labelled local demo data.

## Run Phase 1

Open `frontend/index.html` in a browser, or use VS Code Live Server. Start at the landing page and select **Enter demo workspace**.

## Included in this phase

- Landing page, dashboard, forecast form, vessel comparison, port compatibility, recommendations and alerts
- Responsive vanilla HTML/CSS/JavaScript UI
- Chart.js freight history and prototype forecast chart
- Local demo scenario: Australia → Paradip, Coal, 70,000 tonnes, 3 months

## Current project status

The Spring Boot backend, database schema, sample data and REST APIs are included. The Forecast form submits to the local backend at `http://localhost:8080/api/forecast`; start the backend before using that flow.

## Backend and database (now included)

The Spring Boot backend, MySQL schema, sample data and Docker Compose configuration are in `backend/`, `database/` and `docker-compose.yml`. See `PROJECT_DOCUMENTATION.md` for the API and exact startup commands. The default backend setup uses an in-memory H2 database, so it can be run without MySQL for a quick demonstration.

> Prototype uses sample/demo data for demonstration. Production deployment requires validated historical freight, port infrastructure, congestion, commodity and market data.
