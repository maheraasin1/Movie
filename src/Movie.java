import java.util.Objects;
import java.util.Set;

public class Movie {
    private String title;
    private String directorName;
    private int duration;
    private Set<String> genres;
    private String rating;
    private int year;
    private double imdbScore;

    public Movie(String title, String directorName, int duration, Set<String> genres, String rating, int year, double imdbScore) {
        this.title = title;
        this.directorName = directorName;
        this.duration = duration;
        this.genres = genres;
        this.rating = rating;
        this.year = year;
        this.imdbScore = imdbScore;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Set<String> getGenres() {
        return genres;
    }

    public void setGenres(Set<String> genres) {
        this.genres = genres;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getImdbScore() {
        return imdbScore;
    }

    public void setImdbScore(double imdbScore) {
        this.imdbScore = imdbScore;
    }

    @Override
    public String toString() {
        return "Movie{" +
            "title='" + title + '\'' +
            ", directorName='" + directorName + '\'' +
            ", duration=" + duration +
            ", genres=" + genres +
            ", rating='" + rating + '\'' +
            ", year=" + year +
            ", imdbScore=" + imdbScore +
            '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, directorName, duration, genres, rating, year, imdbScore);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Movie movie = (Movie) obj;
        return duration == movie.duration && year == movie.year &&
               Double.compare(movie.imdbScore, imdbScore) == 0 &&
               Objects.equals(title, movie.title) &&
               Objects.equals(directorName, movie.directorName) &&
               Objects.equals(genres, movie.genres) &&
               Objects.equals(rating, movie.rating);
    }
}
