import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Booking {

    private static int nextBookingId = 1001;

    private final int bookingId;
    private final String customerName;
    private final Movie movie;
    private final String showTime;
    private final int seatNumber;
    private final LocalDateTime bookingTime;

    public Booking(
            String customerName,
            Movie movie,
            String showTime,
            int seatNumber) {

        synchronized (Booking.class) {
            this.bookingId = nextBookingId++;
        }

        this.customerName = customerName;
        this.movie = movie;
        this.showTime = showTime;
        this.seatNumber = seatNumber;
        this.bookingTime = LocalDateTime.now();
    }

    public int getBookingId() {
        return bookingId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Movie getMovie() {
        return movie;
    }

    public String getShowTime() {
        return showTime;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void displayBooking() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        System.out.println("--------------------------------");
        System.out.println("Booking ID  : " + bookingId);
        System.out.println("Customer    : " + customerName);
        System.out.println("Movie       : " + movie.getMovieName());
        System.out.println("Show Time   : " + showTime);
        System.out.println("Seat Number : " + seatNumber);
        System.out.println("Price       : Rs. " + movie.getTicketPrice());
        System.out.println("Booked At   : " + bookingTime.format(formatter));
        System.out.println("--------------------------------");
    }
}
