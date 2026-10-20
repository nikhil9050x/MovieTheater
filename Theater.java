import java.util.HashMap;
import java.util.Map;

public class Theater {

    private final int totalSeats = 20;

    /*
     * Har movie aur show ke liye alag seat arrangement.
     */
    private final Map<String, boolean[]> showSeats = new HashMap<>();

    private String getShowKey(Movie movie, String showTime) {
        return movie.getMovieId() + "-" + showTime;
    }

    private boolean[] getSeats(Movie movie, String showTime) {
        String key = getShowKey(movie, showTime);

        return showSeats.computeIfAbsent(
            key,
            k -> new boolean[totalSeats + 1]
        );
    }

    public synchronized boolean bookSeat(
            Movie movie,
            String showTime,
            int seatNumber) {

        if (seatNumber < 1 || seatNumber > totalSeats) {
            System.out.println("Invalid seat number!");
            return false;
        }

        boolean[] seats = getSeats(movie, showTime);

        if (seats[seatNumber]) {
            System.out.println(
                "Seat " + seatNumber + " is already booked!"
            );
            return false;
        }

        seats[seatNumber] = true;

        System.out.println(
            "Seat " + seatNumber + " booked successfully!"
        );

        return true;
    }

    public synchronized boolean cancelSeat(
            Movie movie,
            String showTime,
            int seatNumber) {

        if (seatNumber < 1 || seatNumber > totalSeats) {
            System.out.println("Invalid seat number!");
            return false;
        }

        boolean[] seats = getSeats(movie, showTime);

        if (!seats[seatNumber]) {
            System.out.println("This seat is not booked!");
            return false;
        }

        seats[seatNumber] = false;

        System.out.println(
            "Seat " + seatNumber + " cancelled successfully!"
        );

        return true;
    }

    public synchronized void displaySeats(
            Movie movie,
            String showTime) {

        boolean[] seats = getSeats(movie, showTime);

        System.out.println("\n========== SEAT LAYOUT ==========");
        System.out.println("Movie: " + movie.getMovieName());
        System.out.println("Show : " + showTime);
        System.out.println();

        for (int i = 1; i <= totalSeats; i++) {

            if (seats[i]) {
                System.out.printf("[X%02d] ", i);
            } else {
                System.out.printf("[%02d] ", i);
            }

            if (i % 5 == 0) {
                System.out.println();
            }
        }

        System.out.println("\n[X] = Booked");
        System.out.println("[Number] = Available");
    }

    public int getTotalSeats() {
        return totalSeats;
    }
}