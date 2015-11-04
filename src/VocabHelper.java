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
	
	public static void main(String[] Args) {
		
		try {
			
			// parsing the question/answer list
			answerList = new ArrayList<String>();
			questionList = new ArrayList<String>();
			
			in = new Scanner(System.in);
			int vocabNum;
			do {
		    	System.out.println("choose vocab list: full, first half, or second half");
		    	vocabNum = in.nextInt();
		    } while (vocabNum < 1 || vocabNum > 3);
		    
			File vocabList = null;
		    //different casing modes
		    switch (vocabNum) {
			    case 1: vocabList = new File("resources/vocab5.txt");
			    	break;
			    case 2: vocabList = new File("resources/vocab_first_half.txt");
			    	break;
			    case 3: vocabList = new File("resources/vocab_second_half.txt");
			    	break;
		    }
						
		    Scanner scanner = new Scanner(new FileInputStream(vocabList));
		    
		    
		    while(scanner.hasNextLine()) {
		    	scanner.useDelimiter(" ");
		    	questionList.add(scanner.next().trim()); //korean list. can swap question and answer by swapping questionList with answerList
		    	scanner.useDelimiter("\n");
		    	answerList.add(scanner.next().trim()); //english list
		    }
		    
		    randList = new ArrayList<Integer>();
		    size = questionList.size();
		    int mode = 0;
		    do {
		    	System.out.println("choose mode: with answers, without answers, rng test, or adaptive");
		    	mode = in.nextInt();
		    } while (mode < 1 || mode > 4);
		    
		    //different casing modes
		    switch (mode) {
			    case 1: quizWithAnswers();
			    	break;
			    case 2: quizWithoutAnswers();
			    	break;
			    case 3: rngTest();
			    	break;
			    case 4: adaptiveTest();
			    	break;
		    
		    }
		    
		    
		    // quizzing
		    
		    
		    scanner.close();
		    in.close();

		} catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	
	// test that tests you more on things you get wrong
	// does this by deleting entry when you get it right, adding another when you get it wrong
	public static void adaptiveTest() {
		while(questionList.size() >= 4) {
	    	int random = randInt(0, size-1);
	    	int scenario = randInt(0, 3);
	    	System.out.println(questionList.get(random) + '\n');
	    	
	    	while (true) {
	    		size = questionList.size();
	    		int rand = randInt(0, answerList.size()-1);
	    		if ((!randList.contains((Integer) rand)) && rand != random) {
	    			randList.add(rand);
	    		}
	    		if (randList.size() == 3) {
	    			break;
	    		}
	    	}

	    	for (int i=0 ; i<4 ; i++) {
	    		if (i == scenario)
	    			System.out.println(answerList.get(random));
	    		else {
	    			System.out.println(answerList.get(randList.get(0)));
	    			randList.remove(0);
	    		}
	    	}
	    	int input = in.nextInt();
	    	if(input - 1 == scenario) {
	    		System.out.println("Correct!\n");
	    		questionList.remove(random);
	    		answerList.remove(random);
	    	}
	    	else if (input == 9)
	    		break;
	    	else {
	    		System.out.println("Wrong. Answer is " + answerList.get(random) + "\n");
	    		questionList.add(questionList.get(random));
	    		answerList.add(answerList.get(random));
	    	}
	    	
	    	randList.clear();
	    	clearConsole();
	    }
		
		System.out.println("Done! The last 4 are:");
		for (int x = 0; x < 4; x++) {
			System.out.println(questionList.get(x) + " is " + answerList.get(x));
		}
	}
	
	public static void quizWithAnswers() {
		
		while(true) {
	    	int random = randInt(0, size-1);
	    	int scenario = randInt(0, 3);
	    	System.out.println(questionList.get(random) + '\n');
	    	
	    	while (true) {
	    		int rand = randInt(0, answerList.size()-1);
	    		if ((!randList.contains((Integer) rand)) && rand != random) {
	    			randList.add(rand);
	    		}
	    		if (randList.size() == 3) {
	    			break;
	    		}
	    	}

	    	for (int i=0 ; i<4 ; i++) {
	    		if (i == scenario)
	    			System.out.println(answerList.get(random));
	    		else {
	    			System.out.println(answerList.get(randList.get(0)));
	    			randList.remove(0);
	    		}
	    	}
	    	int input = in.nextInt();
	    	if(input - 1 == scenario)
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

		
		while(true) {
	    	int random = randInt(0, size-1);
	    	System.out.println(questionList.get(random) + '\n');
	    	if (in.nextLine().equals("exit"))
	    		break;
	    	System.out.println(answerList.get(random) + '\n');
	    	in.nextLine();

	    	clearConsole();
	    }

	}
	
public static void rngTest() {

		
		while(true) {
	    	int random = randInt(0, size-1);
	    	System.out.println(random);
	    	if (in.nextLine().equals("exit"))
	    		break;
	    }

	}
	
	public static int randInt(int min, int max) {

	    // NOTE: This will (intentionally) not run as written so that folks
	    // copy-pasting have to think about how to initialize their
	    // Random instance.  Initialization of the Random instance is outside
	    // the main scope of the question, but some decent options are to have
	    // a field that is initialized once and then re-used as needed or to
	    // use ThreadLocalRandom (if using at least Java 1.7).
	    Random rand = new Random();

	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	public final static void clearConsole()
	{
	    try
	    {
	        final String os = System.getProperty("os.name");

	        if (os.contains("Windows"))
	        {
	            Runtime.getRuntime().exec("cls");
	        }
	        else
	        {
	            Runtime.getRuntime().exec("clear");
	        }
	    }
	    catch (final Exception e)
	    {
	        //  Handle any exceptions.
	    }
	}
	
}

