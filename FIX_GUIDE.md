# Project Fix Guide

## Audit results and resolution status

| Priority | Finding | Resolution |
| --- | --- | --- |
| High | The project cannot be built or started on this computer because `java`, `mvn`, and `node` are not on `PATH`. | Install JDK 17+ and Maven 3.9+, configure their `bin` folders in `PATH`, then run `mvn spring-boot:run` from `backend`. Node is optional and only needed for JavaScript tooling. |
| High | The forecast screen was not using the existing backend API client. | Fixed: the form now posts to `/api/forecast`, displays the response, and passes the result to Recommendations. |
| Medium | A failed upstream weather response could be cached and returned as if it were valid weather data. | Fixed: only successful HTTP responses are cached and returned. |
| Medium | The Dashboard Analytics navigation target did not exist. | Fixed: it now navigates to the dashboard analytics section. |
| Medium | Backend build output and a bundled JDK are located beneath source/project folders. | Added ignore rules for future source control. Keep the bundled JDK until a system JDK is installed; then move it outside `src/main/java` manually. |
| Low | README described an earlier phase and the technical guide claimed an offline fallback that does not exist. | Fixed documentation to match the implemented API-connected form. |
| Low | There are no automated tests. | Add controller/service tests after the Java/Maven toolchain is available; this cannot be verified in the current environment. |

## Verification after installing the toolchain

1. In `backend`, run `mvn clean test`.
2. Run `mvn spring-boot:run` and open `http://localhost:8080/api/health`; it should return `status: UP`.
3. Serve `frontend` with VS Code Live Server, open `index.html`, then submit Forecast.
4. Confirm that Forecast displays a live response and Recommendations shows the same route and recommendation.

## Production items not implemented

Authentication, restrictive CORS, rate limiting, secrets management, real forecast data, and licensed AIS vessel tracking need product and infrastructure decisions. They are deliberately not guessed or enabled in this prototype.
