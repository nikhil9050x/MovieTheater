import java.util.ArrayList;
import java.util.List;

public class BookingService {

    private final Theater theater;

    private final List<Booking> bookings = new ArrayList<>();

    public BookingService(Theater theater) {
        this.theater = theater;
    }

    public synchronized Booking bookTicket(
            String customerName,
            Movie movie,
            String showTime,
            int seatNumber) {

        boolean success = theater.bookSeat(
            movie,
            showTime,
            seatNumber
        );

        if (!success) {
            return null;
        }

        Booking booking = new Booking(
            customerName,
            movie,
            showTime,
            seatNumber
        );

        bookings.add(booking);

        System.out.println("\nBooking successful!");
        booking.displayBooking();

        return booking;
    }

    public synchronized boolean cancelBooking(int bookingId) {

        Booking selectedBooking = null;

        for (Booking booking : bookings) {
            if (booking.getBookingId() == bookingId) {
                selectedBooking = booking;
                break;
            }
        }

        if (selectedBooking == null) {
            System.out.println("Booking ID not found!");
            return false;
        }

        boolean cancelled = theater.cancelSeat(
            selectedBooking.getMovie(),
            selectedBooking.getShowTime(),
            selectedBooking.getSeatNumber()
        );

        if (cancelled) {
            bookings.remove(selectedBooking);

            System.out.println(
                "Booking ID " + bookingId +
                " cancelled successfully."
            );

            return true;
        }

        return false;
    }

    public synchronized void displayAllBookings() {

        if (bookings.isEmpty()) {
            System.out.println("No bookings available.");
            return;
        }

        System.out.println("\n========== ALL BOOKINGS ==========");

        for (Booking booking : bookings) {
            booking.displayBooking();
        }
    }

    public Theater getTheater() {
        return theater;
    }
}
