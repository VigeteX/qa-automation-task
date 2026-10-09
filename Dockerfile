FROM maven:3.9-eclipse-temurin-17

# Google Chrome for the UI tests
RUN apt-get update \
    && apt-get install -y --no-install-recommends wget ca-certificates \
    && wget -q https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb \
    && apt-get install -y --no-install-recommends ./google-chrome-stable_current_amd64.deb \
    && rm google-chrome-stable_current_amd64.deb \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Cache dependencies in a separate layer
COPY pom.xml .
RUN mvn -B -q dependency:go-offline

COPY src ./src

# Default: run all tests. Override, e.g.: docker run qa-automation mvn test -Dgroups=api
CMD ["mvn", "-B", "test"]