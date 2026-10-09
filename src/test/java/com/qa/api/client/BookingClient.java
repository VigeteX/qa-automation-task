package com.qa.api.client;

import com.qa.api.models.Booking;
import com.qa.api.models.CreateBookingResponse;
import com.qa.config.Config;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BookingClient {

    private static final String BOOKING_PATH = "/booking";

    private final RequestSpecification spec = new RequestSpecBuilder()
            .setBaseUri(Config.apiBaseUrl())
            .setContentType(ContentType.JSON)
            .setAccept("application/json")
            .log(io.restassured.filter.log.LogDetail.ALL)
            .build();

    public CreateBookingResponse create(Booking booking) {
        return given(spec)
                .body(booking)
                .when()
                .post(BOOKING_PATH)
                .then()
                .statusCode(200)
                .extract()
                .as(CreateBookingResponse.class);
    }

    public Booking getById(int id) {
        return given(spec)
                .when()
                .get(BOOKING_PATH + "/" + id)
                .then()
                .statusCode(200)
                .extract()
                .as(Booking.class);
    }
}