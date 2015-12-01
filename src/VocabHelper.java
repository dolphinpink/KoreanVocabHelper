import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class VocabHelper {

	static ArrayList<String> answerList;
	static ArrayList<String> questionList;
	static ArrayList<Integer> randList;
	static Scanner in;
	static int size;
	static String vliststring = ("ALL, FIRST HALF, SECOND HALF, ETC");

	public static void main(String[] Args) {

		// parsing the question/answer list
		ArrayList<String> kList = new ArrayList<String>();
		ArrayList<String> eList = new ArrayList<String>();

		int englishIsQuestion = 1;

		try {
			in = new Scanner(System.in);
			int vocabNum;
			do {
				System.out.println("choose mode: choose vocab list " + vliststring);
				vocabNum = in.nextInt();
			} while (vocabNum < 1 || vocabNum > 4);

			File vocabList = null;

			// different casing modes
			switch (vocabNum) {
			case 1:
				vocabList = new File("resources/vocab_8.txt");
				break;
			case 2:
				vocabList = new File("resources/vocab_first_half.txt");
				break;
			case 3:
				vocabList = new File("resources/vocab_second_half.txt");
				break;
			case 4:
				vocabList = new File("resources/vocab5.txt");
				break;
			}

			Scanner scanner = new Scanner(new FileInputStream(vocabList));

			while (scanner.hasNextLine()) {
				scanner.useDelimiter(" ");
				kList.add(scanner.next().trim()); // making korean list

				scanner.useDelimiter("\n");
				eList.add(scanner.next().trim()); // making english list
			}

			if (englishIsQuestion == 1) {
				questionList = eList;
				answerList = kList;
			} else {
				questionList = kList;
				answerList = eList;
			}

			randList = new ArrayList<Integer>();
			size = questionList.size();
			int mode = 0;
			do {
				System.out.println(
						"choose mode:\t1. WITH ANSWERS\n\t\t2. WITHOUT ANSWERS\n\t\t3. RNG TEST\n\t\t4. FOR MEMRISE");
				mode = in.nextInt();
			} while (mode < 1 || mode > 4);

			in.nextLine();
			// different casing modes
			switch (mode) {
			case 1:
				quizWithAnswers();
				break;
			case 2:
				quizWithoutAnswers();
				break;
			case 3:
				rngTest();
				break;
			case 4:
				formemrise();
				break;

			}

			// quizzing

			scanner.close();
			in.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void quizWithAnswers() {

		while (true) {
			int random = randInt(0, size - 1);
			int scenario = randInt(0, 3);
			System.out.println(questionList.get(random) + '\n');

			while (true) {
				int rand = randInt(0, answerList.size() - 1);
				if ((!randList.contains((Integer) rand)) && rand != random) {
					randList.add(rand);
				}
				if (randList.size() == 3) {
					break;
				}
			}

			for (int i = 0; i < 4; i++) {
				if (i == scenario)
					System.out.println(answerList.get(random));
				else {
					System.out.println(answerList.get(randList.get(0)));
					randList.remove(0);
				}
			}
			int input = in.nextInt();
			if (input - 1 == scenario)
				System.out.println("Correct!\n");
			else if (input == 9)
				break;
			else
				System.out.println("Wrong. Answer is " + answerList.get(random) + "\n");

			randList.clear();
			clearConsole();
		}

	}

	public static void quizWithoutAnswers() {

		while (true) {
			int random = randInt(0, size - 1);
			System.out.println(questionList.get(random) + '\n');
			if (in.nextLine().equals("exit"))
				break;
			System.out.println(answerList.get(random) + '\n');
			in.nextLine();

			clearConsole();
		}

	}

	public static void writingTest() {

		while (true) {
			int random = randInt(0, size - 1);
			System.out.println(answerList.get(random));
			if (in.nextLine().equals("exit"))
				break;
		}

	}

	public static void formemrise() {
		for (int i = 0; i < answerList.size(); i++) {
			System.out.println(questionList.get(i) + ", " + answerList.get(i));
		}
	}

	public static void rngTest() {

		while (true) {
			int random = randInt(0, size - 1);
			System.out.println(random);
			if (in.nextLine().equals("exit"))
				break;
		}

	}

	public static int randInt(int min, int max) {

		// NOTE: This will (intentionally) not run as written so that folks
		// copy-pasting have to think about how to initialize their
		// Random instance. Initialization of the Random instance is outside
		// the main scope of the question, but some decent options are to have
		// a field that is initialized once and then re-used as needed or to
		// use ThreadLocalRandom (if using at least Java 1.7).
		Random rand = new Random();

		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	public final static void clearConsole() {
		try {
			final String os = System.getProperty("os.name");

			if (os.contains("Windows")) {
				Runtime.getRuntime().exec("cls");
			} else {
				Runtime.getRuntime().exec("clear");
			}
		} catch (final Exception e) {
			// Handle any exceptions.
		}
	}

}
