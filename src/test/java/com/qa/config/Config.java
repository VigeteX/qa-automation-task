package com.qa.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Central access to test configuration.
 * Values come from config.properties and can be overridden with -Dkey=value.
 */
public final class Config {

    private static final Properties PROPERTIES = load();

    private Config() {
    }

    public static String uiBaseUrl() {
        return get("ui.base.url");
    }

    public static String username() {
        return get("ui.username");
    }

    public static String password() {
        return get("ui.password");
    }

    public static String browser() {
        return get("browser");
    }

    public static boolean headless() {
        return Boolean.parseBoolean(get("browser.headless"));
    }

    public static String browserSize() {
        return get("browser.size");
    }

    public static long timeoutMs() {
        return Long.parseLong(get("timeout.ms"));
    }

    public static String apiBaseUrl() {
        return get("api.base.url");
    }

    private static String get(String key) {
        String override = System.getProperty(key);
        if (override != null && !override.isBlank()) {
            return override;
        }
        String value = PROPERTIES.getProperty(key);
        if (value == null) {
            throw new IllegalStateException("Missing config key: " + key);
        }
        return value;
    }

    private static Properties load() {
        Properties props = new Properties();
        try (InputStream in = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in == null) {
                throw new IllegalStateException("config.properties not found on classpath");
            }
            props.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read config.properties", e);
        }
        return props;
    }
}
