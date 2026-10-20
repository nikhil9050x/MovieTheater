import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    private static final Movie[] movies = {
        new Movie(1, "Avengers Endgame", 250),
        new Movie(2, "Interstellar", 200),
        new Movie(3, "Inception", 220),
        new Movie(4, "Jawan", 180),
        new Movie(5, "3 Idiots", 150)
    };

    private static final String[] showTimes = {
        "10:00 AM",
        "2:00 PM",
        "6:00 PM"
    };

    private static final Theater theater = new Theater();

    private static final BookingService bookingService =
            new BookingService(theater);

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("   MOVIE THEATER SEAT BOOKING SYSTEM");
        System.out.println("======================================");

        boolean running = true;

        while (running) {

            System.out.println("\n========== MAIN MENU ==========");
            System.out.println("1. View Movies");
            System.out.println("2. View Show Timings");
            System.out.println("3. View Seats");
            System.out.println("4. Book Ticket");
            System.out.println("5. Cancel Booking");
            System.out.println("6. View All Bookings");
            System.out.println("7. Multithreading Demo");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice = readInt();

            switch (choice) {

                case 1:
                    displayMovies();
                    break;

                case 2:
                    displayShowTimes();
                    break;

                case 3:
                    selectShowAndDisplaySeats();
                    break;

                case 4:
                    bookTicket();
                    break;

                case 5:
                    cancelBooking();
                    break;

                case 6:
                    bookingService.displayAllBookings();
                    break;

                case 7:
                    multithreadingDemo();
                    break;

                case 8:
                    running = false;
                    System.out.println("Thank you for using our system!");
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }

        sc.close();
    }

    private static int readInt() {

        while (!sc.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            sc.next();
        }

        return sc.nextInt();
    }

    private static String readText(String message) {

        System.out.print(message);
        return sc.next();
    }

    private static void displayMovies() {

        System.out.println("\n========== MOVIES ==========");

        for (Movie movie : movies) {
            movie.displayMovie();
        }
    }

    private static Movie selectMovie() {

        displayMovies();

        System.out.print("Select movie number: ");
        int id = readInt();

        for (Movie movie : movies) {
            if (movie.getMovieId() == id) {
                return movie;
            }
        }

        System.out.println("Invalid movie selection!");
        return null;
    }

    private static String selectShowTime() {

        displayShowTimes();

        System.out.print("Select show number: ");
        int choice = readInt();

        if (choice < 1 || choice > showTimes.length) {
            System.out.println("Invalid show selection!");
            return null;
        }

        return showTimes[choice - 1];
    }

    private static void displayShowTimes() {

        System.out.println("\n========== SHOW TIMINGS ==========");

        for (int i = 0; i < showTimes.length; i++) {
            System.out.println((i + 1) + ". " + showTimes[i]);
        }
    }

    private static void selectShowAndDisplaySeats() {

        Movie movie = selectMovie();

        if (movie == null) {
            return;
        }

        String showTime = selectShowTime();

        if (showTime == null) {
            return;
        }

        theater.displaySeats(movie, showTime);
    }

    private static void bookTicket() {

        Movie movie = selectMovie();

        if (movie == null) {
            return;
        }

        String showTime = selectShowTime();

        if (showTime == null) {
            return;
        }

        theater.displaySeats(movie, showTime);

        String name = readText("Enter customer name (no spaces): ");

        System.out.print("Enter seat number: ");
        int seatNumber = readInt();

        System.out.println(
            "Ticket price: Rs. " + movie.getTicketPrice()
        );

        System.out.print("Confirm payment? (1 = Yes, 2 = No): ");
        int paymentChoice = readInt();

        if (paymentChoice != 1) {
            System.out.println("Booking cancelled before payment.");
            return;
        }

        bookingService.bookTicket(
            name,
            movie,
            showTime,
            seatNumber
        );
    }

    private static void cancelBooking() {

        System.out.print("Enter booking ID: ");
        int bookingId = readInt();

        bookingService.cancelBooking(bookingId);
    }

    private static void multithreadingDemo() {

        System.out.println("\n========== MULTITHREADING DEMO ==========");

        Movie movie = movies[0];
        String showTime = showTimes[0];
        int seatNumber = 20;

        System.out.println(
            "Two users will try to book the same seat: " + seatNumber
        );

        Thread user1 = new Thread(() -> {
            bookingService.bookTicket(
                "User1",
                movie,
                showTime,
                seatNumber
            );
        }, "User-1");

        Thread user2 = new Thread(() -> {
            bookingService.bookTicket(
                "User2",
                movie,
                showTime,
                seatNumber
            );
        }, "User-2");

        user1.start();
        user2.start();

        try {
            user1.join();
            user2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Demo interrupted.");
        }

        System.out.println("\nMultithreading demo completed.");
    }
}
