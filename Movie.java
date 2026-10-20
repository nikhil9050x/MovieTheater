public class Movie {

    private int movieId;
    private String movieName;
    private double ticketPrice;

    public Movie(int movieId, String movieName, double ticketPrice) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.ticketPrice = ticketPrice;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void displayMovie() {
        System.out.println(
            movieId + ". " + movieName +
            " | Ticket Price: Rs. " + ticketPrice
        );
    }
}
