# QA Automation Test Task

UI and API test automation project.

- **UI:** [SauceDemo](https://www.saucedemo.com), tests with Selenide
- **API:** [Restful Booker](https://restful-booker.herokuapp.com), tests with REST Assured
- **Stack:** Java 17, Maven, JUnit 5, Selenide, REST Assured, AssertJ

## Prerequisites

Local run:
- JDK 17+
- Maven 3.9+
- Google Chrome

Docker run:
- Docker (Docker Desktop on Windows/macOS)

## Run locally

```
mvn test                  # all tests
mvn test -Dgroups=ui      # UI tests only
mvn test -Dgroups=api     # API tests only
```

To watch the browser, disable headless mode:

```
mvn test -Dgroups=ui -Dbrowser.headless=false
```

Any value from `src/test/resources/config.properties` can be overridden with `-Dkey=value`.

## Run in Docker

```
docker build -t qa-automation .
docker run --rm qa-automation
```

Run a subset:

```
docker run --rm qa-automation mvn -B test -Dgroups=api
```

The image contains JDK 17, Maven and Google Chrome. UI tests run in headless mode.

## Test scenarios

UI (SauceDemo):
1. Login with valid credentials opens the products page.
2. Products can be sorted by price (low to high).
3. A product added to the cart appears in the cart.

API (Restful Booker):
1. Create a booking (POST), then retrieve it (GET) and verify the data matches.

## Approach

- **Page Object pattern** for UI: pages expose actions, tests contain only scenario and assertions.
- **Stable locators:** `data-test` attributes instead of CSS classes or XPath.
- **No hard waits:** Selenide waits for elements automatically (explicit conditions only).
- **Typed API layer:** `BookingClient` with a shared request specification, JSON mapped to Java records.
- **Test independence:** the browser is closed after each test; every API test creates its own data.
- **Configuration** in one place (`config.properties`) with command-line overrides.
- **Tags** (`ui`, `api`) allow running each suite separately.

## Project structure

```
src/test/java/com/qa
├── config/      Config
├── ui/pages/    LoginPage, ProductsPage, CartPage
├── ui/tests/    BaseUiTest, LoginTest, ProductsTest
├── api/client/  BookingClient
├── api/models/  Booking, BookingDates, CreateBookingResponse
└── api/tests/   BookingApiTest
```

## Notes

The Restful Booker API rejects the default multi-value `Accept` header sent by REST Assured
(responds with HTTP 418), so the client sends `Accept: application/json` explicitly.
The public API is shared and resets periodically, so tests do not depend on pre-existing data.