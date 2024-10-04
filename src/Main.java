import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        Collection<Movie> movies = parseMovies();

        List<Movie> topTenMovies = movies.stream()
            .sorted(Comparator.comparingDouble(Movie::getImdbScore).reversed())
            .limit(10)
            .collect(Collectors.toList());
        printMovies("Top ten highest rated movies:", topTenMovies);

        List<Movie> topEightMovies2000s = movies.stream()
            .filter(movie -> movie.getYear() >= 2000)
            .sorted(Comparator.comparingDouble(Movie::getImdbScore).reversed())
            .limit(8)
            .collect(Collectors.toList());
        printMovies("Top eight highest rated movies released on or after the year 2000:", topEightMovies2000s);

        List<Movie> fiveShortestFantasyMovies = movies.stream()
            .filter(movie -> movie.getGenres().contains("Fantasy"))
            .sorted(Comparator.comparingInt(Movie::getDuration))
            .limit(5)
            .collect(Collectors.toList());
        printMovies("Five shortest fantasy movies:", fiveShortestFantasyMovies);

        List<Movie> tarantinoMovies = movies.stream()
            .filter(movie -> movie.getDirectorName().equalsIgnoreCase("Quentin Tarantino"))
            .sorted(Comparator.comparingInt(Movie::getDuration).reversed())
            .collect(Collectors.toList());
        printMovies("Quentin Tarantino movies:", tarantinoMovies);

        OptionalDouble averageAdventureMovieDuration = movies.stream()
            .filter(movie -> movie.getGenres().contains("Adventure"))
            .mapToInt(Movie::getDuration)
            .average();
        System.out.printf("Average length of an adventure movie: %.2f\n", averageAdventureMovieDuration.orElse(-1));

        String longestMovie = movies.stream()
            .max(Comparator.comparingInt(Movie::getDuration))
            .map(Movie::getTitle)
            .orElse("No movies available");
        System.out.println("Longest movie: " + longestMovie);

        String highestRatedPG13Movie = movies.stream()
            .filter(movie -> movie.getRating().equalsIgnoreCase("PG-13"))
            .max(Comparator.comparingDouble(Movie::getImdbScore))
            .map(Movie::getTitle)
            .orElse("No PG-13 movies available");
        System.out.println("Best PG-13 movie: " + highestRatedPG13Movie);

        List<Movie> longestMovies = movies.stream()
            .sorted(Comparator.comparingInt(Movie::getDuration).reversed())
            .limit(5)
            .collect(Collectors.toList());
        printMovies("Top 5 longest movies:", longestMovies);

        Map<String, Double> averageScoreByGenre = movies.stream()
            .flatMap(movie -> movie.getGenres().stream().map(genre -> Map.entry(genre, movie.getImdbScore())))
            .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.averagingDouble(Map.Entry::getValue)));
        System.out.println("Average IMDb Score by Genre: " + averageScoreByGenre);
    }

    private static Collection<Movie> parseMovies() {
        Set<Movie> movies = new HashSet<>();
        try (Stream<String> stream = Files.lines(Paths.get("movie_metadata.csv"))) {
            stream
                .skip(1)
                .forEach(line -> {
                    Movie movie = parseMovie(line);
                    if (movie != null) {
                        movies.add(movie);
                    }
                });
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return movies;
    }

    private static Movie parseMovie(String line) {
        List<String> tokens = Arrays.asList(line.split(","));
        if (tokens.size() != 7) {
            return null;
        }

        String directorName = tokens.get(0);
        int duration = -1;
        try {
            if (!tokens.get(1).isEmpty()) {
                duration = Integer.parseInt(tokens.get(1));
            }
        } catch (NumberFormatException e) {
            System.err.println("Unable to parse duration: " + tokens.get(1));
        }

        Set<String> genres = Set.of(tokens.get(2).split("\\|"));
        String title = tokens.get(3);
        String rating = tokens.get(4);
        int year = -1;
        try {
            if (!tokens.get(5).isEmpty()) {
                year = Integer.parseInt(tokens.get(5));
            }
        } catch (NumberFormatException e) {
            System.err.println("Unable to parse year: " + tokens.get(5));
        }

        double imdbScore = -1.0;
        try {
            if (!tokens.get(6).isEmpty()) {
                imdbScore = Double.parseDouble(tokens.get(6));
            }
        } catch (NumberFormatException e) {
            System.err.println("Unable to parse IMDB Score: " + tokens.get(6));
        }

        return new Movie(title, directorName, duration, genres, rating, year, imdbScore);
    }

    private static void printMovies(String label, Collection<Movie> movies) {
        System.out.println(label + ": ");
        for (Movie movie : movies) {
            System.out.printf("\t%-40s%-6d%-10s%-5d%-5.1f%-10s%-30s%n",
                movie.getTitle(),
                movie.getYear(),
                movie.getRating(),
                movie.getDuration(),
                movie.getImdbScore(),
                movie.getDirectorName(),
                movie.getGenres());
        }
        System.out.println("");
    }
}


