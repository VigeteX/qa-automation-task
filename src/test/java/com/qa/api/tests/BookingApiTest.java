package com.qa.api.tests;

import com.qa.api.client.BookingClient;
import com.qa.api.models.Booking;
import com.qa.api.models.BookingDates;
import com.qa.api.models.CreateBookingResponse;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("api")
class BookingApiTest {

    private final BookingClient bookingClient = new BookingClient();

    @Test
    void created_booking_can_be_retrieved_with_same_data() {
        Booking booking = new Booking(
                "John",
                "Doe",
                150,
                true,
                new BookingDates("2026-11-01", "2026-11-05"),
                "Breakfast");

        CreateBookingResponse created = bookingClient.create(booking);

        assertThat(created.bookingid()).isPositive();
        assertThat(created.booking()).isEqualTo(booking);

        Booking retrieved = bookingClient.getById(created.bookingid());

        assertThat(retrieved).isEqualTo(booking);
    }
}