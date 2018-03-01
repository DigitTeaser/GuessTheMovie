import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This is a "Guess the Movie" game in the command line.
 */
public class Game {
    /**
     * Define the guess chances to ten times here.
     */
    private static final int GUESS_CHANCES = 10;

    public static void main(String[] args) {
        // Get a random movie string.
        String movie = getMovie();

        // Counter that tells how many times user guess wrong.
        int wrongCount = 0;
        // String that records the wrong letters that user input.
        StringBuilder wrongLetters = new StringBuilder();
        // String that saves the progress of user guessing. It initiate to all underlines.
        StringBuilder guessedMovie = new StringBuilder(movie.replaceAll("[a-z]", "_"));

        // While wrong guesses are less than ten times, keep asking user to guess.
        while (wrongCount < GUESS_CHANCES) {
            // Print the wrong times and all wrong letters.
            System.out.println("You have guessed (" + wrongCount + ") wrong letters: " + wrongLetters.toString());
            // Prompt user to input a guess letter.
            // Use print method here instead of println, to keep user input on the same line after the print.
            System.out.print("Guess a letter: ");

            // Get user input using scanner and save it to a string.
            Scanner inputScanner = new Scanner(System.in);
            String guess = inputScanner.nextLine();

            if (guess.matches("[a-z]")) {
                if (movie.contains(guess)) {
                    // If user guess right, set the right character to all indices in the string.
                    for (int i = 0; i < guessedMovie.toString().length(); i++) {
                        if (movie.charAt(i) == guess.charAt(0)) {
                            guessedMovie.setCharAt(i, guess.charAt(0));
                        }
                    }
                    // If user guess all right, print the winning text and break the loop.
                    if (!guessedMovie.toString().contains("_")) {
                        System.out.println("You Win!" + "\nYou have guessed '" + guessedMovie + "' correctly.");
                        break;
                    }
                } else if (!wrongLetters.toString().contains(guess)) {
                    // When user input wrong letters for the first time,
                    // add one to the wrong time counter and record the wrong letter.
                    wrongCount++;
                    wrongLetters.append(" ");
                    wrongLetters.append(guess);
                }

                // Print the current progress of the guessing movie string.
                System.out.println("You are guessing: " + guessedMovie);
            } else {
                // When user input characters beyond letters, prompt user the warning message.
                System.out.println("Input mismatch. Please input a lower case letter from a to z.");
            }
        }

        if (wrongCount == GUESS_CHANCES) {
            System.out.println("You run out of all ten chances. Better luck next time.");
        }
    }

    /**
     * Helper method that get a random movie from the list file.
     *
     * @return movie {@link String} from the movie list file.
     */
    private static String getMovie() {
        // Declare a movie string and initiate to null.
        String movie = null;

        try {
            // Read the movie list file and get a file scanner.
            File file = new File("res/movies.txt");
            Scanner fileScanner = new Scanner(file);

            // Get the total number of movies in the list file.
            int movieCount = 0;
            // Keep counting lines until reaching the bottom of the file.
            while (fileScanner.hasNextLine()) {
                movieCount++;
                fileScanner.nextLine();
            }

            // Reset the movie list file scanner.
            fileScanner = new Scanner(file);

            // Get a random index of the movie list according to the total number of movies.
            int movieIndex = (int) (Math.random() * movieCount);
            // Print the chosen movie after masked all characters with underline.
            for (int i = 0; i < movieCount; i++) {
                movie = fileScanner.nextLine();
                if (i == movieIndex) {
                    System.out.println("You are guessing: " + movie.replaceAll("[a-z]", "_"));
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No movie list file found.");
        }

        return movie;
    }
}
