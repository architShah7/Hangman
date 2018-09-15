package shah.archit;	

import java.util.*;

/**
 * Hangman is an interactive program that requires the user to guess one letter
 * at a time to form a word and win before all parts of the hangman are listed.
 * 
 * @author A. Shah
 * @version 1.0
 */
public class Hangman {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		// Display an introductory message
		introduction();

		// Select the secret word and get its length
		String wordToGuess = determineWord();
		int wordLength = wordToGuess.length();

		// letters of the alphabet
		char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

		// Update the number of wrong guesses to print body parts
		int badGuesses = 0;

		// Sentinel for keeping track of the game and user's guess
		boolean gameOver = false;
		boolean validGuess = false;

		// Dashes to be replaced by correct guesses
		char[] trueGuesses = initializeBoard(wordLength);

		// Characters of the randomly chosen word
		char[] chosenWordArray = wordToGuess.toCharArray();

		while (!gameOver) {

			printBoard(trueGuesses, wordLength);
			System.out.println("");

			String userInput = "";
			// Get the letter and validate
			while (!validGuess) {
				remainingLetters(alphabet);
				userInput = takeInput(in);
				boolean singleLetter = validateLength(userInput);
				boolean repeatedGuess = removeUsedLetter(userInput, alphabet,
						singleLetter);
				if (singleLetter == true && repeatedGuess == false) {
					break;
				}
			}

			// Evaluate user's guess and determine if it is a right or wrong
			System.out.println("");
			char checkedLetter = userInput.charAt(0);
			boolean userRight = guessCheck(checkedLetter, trueGuesses,
					chosenWordArray);
			badGuesses = wrongGuessUpdate(userRight, badGuesses);
			bodyPartList(badGuesses);
			remainingLetters(alphabet);
			//System.out.println("");

			// Determine win or loss and print to the screen
			gameOver = checkWin(trueGuesses, badGuesses, gameOver,
					chosenWordArray);
			printWin(gameOver, badGuesses, wordToGuess);
			printLoss(gameOver, badGuesses, wordToGuess);

			if (gameOver == true) {
				break;
			}
		}
		in.close();
	}

	/**
	 * Print the instructions.
	 */
	private static void introduction() {
		System.out
				.println("---------------------------------------------------------------------------------------------------------------------------------");
		System.out.print("Welcome to Hangman!");
		System.out.println(" Let's hope you're ready to do some thinking.");
		System.out
				.println("In order to win, you must guess the correct word within 6 tries.");
		System.out
				.println("Hint: The word will be chosen from the countries' category.");
		System.out
				.println("NOTE: repeated inputs will not be accepted, and you will be asked to guess again.");
		System.out.println("Good luck!");
		System.out
				.println("---------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("");
		System.out.println("");
	}

	/**
	 * Obtain a random word from the word list.
	 * @return word randomly selected word
	 */
	private static String determineWord() {
		String[] listOfWords = { "canada", "india", "england", "australia","japan" };
		int wordToGuess = new Random().nextInt(listOfWords.length);
		String word = listOfWords[wordToGuess];
		return word;
	}

	/**
	 * Initialize the board with dashes.
	 * @param wordLength
	 * @return trueGuesses - Dashes that are to replaced by correct guesses
	 */
	private static char[] initializeBoard(int wordLength) {

		char[] trueGuesses = new char[wordLength];
		System.out.print("Your word to guess is " + wordLength
				+ " characters long: ");
		for (int i = 0; i < wordLength; i++) {
			trueGuesses[i] = '_';
		}
		return trueGuesses;
	}

	/**
	 * Print the board with dashes.
	 * @param trueGuesses 	
	 * @param wordLength	
	 */
	private static void printBoard(char[] trueGuesses, int wordLength) {

		for (int i = 0; i < wordLength; i++) {
			System.out.print(trueGuesses[i] + " ");
		}
		System.out.println("");
	}

	/**
	 * Takes a single line input from the user.
	 * @param in open Scanner object for user input
	 * @return userInput 
	 */
	private static String takeInput(Scanner in) {
		System.out.println("");
		System.out.print("Please enter a letter: ");
		String userInput = in.nextLine().trim().toLowerCase();
		return userInput;
	}

	/**
	 * Check whether input is a single character or not.
	 * @param letterInput
	 * @return True or false depending on user's input
	 */
	private static boolean validateLength(String userInput) {
		boolean letterOrNot = false;
		if (userInput.length() == 1) {
			letterOrNot = true;
		} else {
			letterOrNot = false;
			System.out.print("Please enter a letter, not multiple letters. ");
			System.out.println("");
		}
		return letterOrNot;
	}

	/**
	 * Remove the guessed letter from the alphabet while checking for a repeated
	 * guess.
	 * @param letterInput
	 * @param alphabet
	 * @param singleLetter Check whether the user's input is a letter or not
	 * @return true or false, depending on whether the input is repeated or not.
	 */
	private static boolean removeUsedLetter(String userInput, char[] alphabet,
			boolean singleLetter) {

		boolean repeatInput = true;
		if (singleLetter == true) {
			for (int i = 0; i < alphabet.length; i++) {
				if (alphabet[i] == userInput.charAt(0)) {
					alphabet[i] = '-';
					repeatInput = false;
				}
			}
			if (repeatInput == true) {
				System.out.println("When prompted, enter a letter that you haven't guessed.");
				System.out.println("");
			}
		}
		return repeatInput;
	}
	
	/**
	 * Print out the letters that the user can choose to guess from while replacing the guessed
	 * letters with dashes. 
	 * @param alphabet
	 */
	private static void remainingLetters(char[] alphabet) {
		//System.out.println("");
		System.out.print("Letters to guess from: ");
		for (int i = 0; i < alphabet.length; i++) {
			System.out.print(alphabet[i] + " ");
		}
		System.out.println("");
	}

	/**
	 * Check if the user's guessed letter is right or not by comparing it to the
	 * letters from the original word.
	 * @param checkedLetter
	 * @param trueGuesses 
	 * @param chosenWordArray 
	 * @return - True or false, depending on whether the user is right or not
	 */
	private static boolean guessCheck(char checkedLetter, char[] trueGuesses,
			char[] chosenWordArray) {
		boolean userRight = false;

		for (int i = 0; i < chosenWordArray.length; i++) {
			if (chosenWordArray[i] == checkedLetter) {
				trueGuesses[i] = checkedLetter;
				userRight = true;
			}
		}
		return userRight;
	}

	/**
	 * Update the number of wrong guesses.
	 * @param userRight
	 * @param badGuesses
	 * @return number of wrong guesses.
	 */
	private static int wrongGuessUpdate(boolean userRight, int badGuesses) {

		if (userRight == false) {
			badGuesses++;
		}
		return badGuesses;
	}

	/**
	 * Print out the list of body parts that the user has.
	 * @param badGuesses
	 */
	private static void bodyPartList(int badGuesses) {
		String[] gallowsParts = { "Head", "Body", "Left Arm", "Right Arm",
				"Left Leg", "Right Leg" };
		System.out.print("Body parts: ");

		for (int i = 0; i < badGuesses; i++) {
			System.out.print(gallowsParts[i] + " ");
		}
		System.out.println(" ");
		System.out.println(" ");
	}

	/**
	 * Check for user's win or loss by comparing user's good guesses with the
	 * selected word.
	 * @param trueGuesses
	 * @param badGuesses
	 * @param gameOver	 
	 * @param chosenWordArray	 
	 * @return gameOver
	 */
	private static boolean checkWin(char[] trueGuesses, int badGuesses,
			boolean gameOver, char[] chosenWordArray) {
		if (Arrays.equals(chosenWordArray, trueGuesses) && badGuesses < 6
				|| badGuesses >= 6) {
			gameOver = true;
		}
		return gameOver;
	}

	/**
	 * Print to the screen a message of victory along with the chosen word. 
	 * @param gameOver
	 * @param badGuesses
	 * @param wordToGuess
	 */
	private static void printWin(boolean gameOver, int badGuesses,
			String wordToGuess) {
		if (gameOver == true && badGuesses < 6) {
			System.out.println(" ");
			System.out.println("-------------");
			System.out.println("YES! YOU WIN!");
			System.out.println("-------------");
			System.out.println("The word was indeed " + wordToGuess + ".");
		}
	}

	/**
	 * Print to the screen a message of defeat along with the chosen word.
	 * @param gameOver
	 * @param badGuesses
	 * @param wordToGuess
	 */
	private static void printLoss(boolean gameOver, int badGuesses,String wordToGuess) {
		if (gameOver == true && badGuesses >= 6) {
			System.out.println(" ");
			System.out.println("-------------");
			System.out.println("NO! YOU LOSE!");
			System.out.println("-------------");
			System.out.println("The word was " + wordToGuess + ".");
		}
	}

}
