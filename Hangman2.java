package Assignment0;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;


import java.util.Scanner;
import java.util.Stack;


public class Hangman2 {

	public static void main(String[] args) {
		ArrayList<String> words = new ArrayList<String>(Arrays.asList("BATTLESHIP","BASEBALL","RUBBER","SOLITAIRE","VOLLEYBALL","UMPIRE","CHECKER","SKITTLE","PRESERVE","HUNTER","CHESSMAN","JACKPOT","HANDICAP","OUTPLAY","PUZZLE","DOUBLET","CERTIFICATE","NAPOLEON","RETRIEVER","SCRIMMAGE","GUESSTIMATE","CAMPING","DRAUGHTS","CANDLEPIN","CRADDLES","SPECULATIVE","CHARADE","REVERSIS","KNUCKLEBONE","COMPUTER","PADDLEBALL","BACCARAT","SHUFFLEBOARD","BOLIVIA","FORFEIT","RANGER","JOYSTICK","COMPETITION","COCKALORUM","GRIDIRON","NEWMARKET","SHUTTLECOCK","BEANBAG","MARLOES","DUCKPIN","TRAMPOLINE"));
		Scanner scan = new Scanner(System.in);
		Stack<Integer> stack = new Stack<Integer>();
		HashMap<String, Integer> lb = new HashMap<String, Integer>();

		//THIS SNIPPET OF CODE CHECKS IF THERE CONTAINS ANY DATA IN THE FILE IT'S CORRESPONDING ARRAY LIST AND DICTIONARY IF SO THE THE DATA IS EXTRACTED AND PUT IN

		File file = new File("LeaderBoard.txt");             	
		if(file.length()!=0) {								 	
			try {		

				Scanner myReader = new Scanner(file);
				while (myReader.hasNextLine()) {					
					String data = myReader.nextLine();				
					String[] splited = data.split(" ");
					lb.put(splited[2],Integer.parseInt(splited[1]));
					stack.add(Integer.parseInt(splited[1]));
				}
				myReader.close();
			} catch (FileNotFoundException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}

		}
		System.out.println("---------------------------HANGMAN_VARIATION_B---------------------------");
		System.out.println("                ------------WITH LEADERBOARD------------");
		System.out.println();
		System.out.println("___________________");
		System.out.println("    |           |");
		System.out.println("    |	    	|");
		System.out.println("    |	     0^^^^0");
		System.out.println("    |      0  -  -  0");
		System.out.println("    |     0   * | *  0");
		System.out.println("    |      0   __   0");
		System.out.println("    |        0 ww 0");
		System.out.println("    |	       |");
		System.out.println("    |      ---------");
		System.out.println("    |     /    |    \\");
		System.out.println("    |    /     |     \\");
		System.out.println("    |          |");
		System.out.println("    |       -------");
		System.out.println("    |      /       \\");
		System.out.println("    |     /         \\");
		System.out.println();
		System.out.println("You are given 10 guesses to find the full word");
		System.out.println("You will recieve a warning if you input anything other than a single letter or repeat the right letter again");
		System.out.println("3 warnings will cost you a guess");
		System.out.println("Oh and be carefull, dont waste your guesses on repeating the same letter ");
		System.out.println("All the Best");
		System.out.println();
		System.out.println("--------------------------------------------------------------------------");
		Random rand = new Random();								

		int score;
		String name;
		int choice = 1;


		while(choice==1) {

			int count=10;
			int warns=0;

			//A RANDOM NUMBER IS PROCESSED A USING THAT NUMBER A WORD IS CHOSSEN FROM THE LIST OF WORDS AND INPUTTED TO "rand_word"

			int int_random = rand.nextInt(words.size());						
			String rand_word= words.get(int_random);							
			words.remove(int_random);


			System.out.println("Enter your name:");
			name = scan.next();

			//THIS SNIPPET OF CODE CREATES "-" OF THE NUMBER OF LETTERS IN THE WORD AND INITIALIZES "dash” WITH THAT VALUE

			String dash = new String(new char[rand_word.length()]).replace("\0", "-");   
			System.out.println(dash);											  

			//WHEN COUNT(MO. OF GUESSES) IS NOT 0 AND "dash" IS NOT SAME AS "rand_word" LETTER FROM USER INPUTTED

			while(!dash.equals(rand_word) && count!=0) {							
				System.out.println("Enter one letter");
				String guessed_letter = scan.next(); 								
				guessed_letter=guessed_letter.toUpperCase();

				String empty = "";

				if(dash.contains(guessed_letter) || !(guessed_letter.charAt(0) >= 'A' && guessed_letter.charAt(0) <= 'Z') || guessed_letter.length()>1 ) 
				{warns+=1;																		  
				System.out.println("Warning : "+warns);	

				//THIS SNIPPET OF CODE IS USED TO CHECK IF THE LETTER INPUTTED IS VALID(NOT MORE THAN ONE LETTER, NOT A SYMBOL) IF SO A WARNING IS DISPLAYED AND WARN IS INCREMENTED

				if(warns==3) {														
					warns=0;													
					count-=1;
				}

				//IF ITS A WARNING THE LOOP BREAKS
				continue;															
				}
				else if(rand_word.contains(guessed_letter)) {

					for (int i = 0; i < rand_word.length(); i++) { 

						if(guessed_letter.charAt(0)==rand_word.charAt(i)) {
							//IF THE LETTER INPUTTED IS IN “rand_word” THE "-" IS REPLACED WITH THE LETTER
							empty=empty+guessed_letter;}
						else {								
							//ELSE THE SAME "-" IS UPDATED IN THAT PLACE
							empty=empty+dash.charAt(i);										
						}
					}
					//THE NEW "dash" IS  EDITED
					dash=empty;															
				}
				//THE NEW "dash" IS DISPLAYED
				System.out.println(dash);
				//AFTER EACH LETTER THE NUMBER GUESSES REDUCE
				count-=1;			
				//GUESSES DISPLAYED
				System.out.println("Guesses Remaining : "+count);							

			}
			//IF "t" EQUALS "a" WIN OR ELSE LOOSE
			if(dash.equals(rand_word)) {                                                      
				System.out.println("     You Win");

			}
			else {
				System.out.println("     You Loose");

			}



			score=Score(count,rand_word);           //FINDING THE SCORE USING FUNCTION                                       
			stack.add(score);						//APPENDING THE SCORE TO STACK							   
			stack=sortstk(stack);					//SORTING THE STACK USING FUNCTION							   

			lb.put(name,score);						//INSERTING NAME AND SCORE IN A DICTIONARY							  

			System.out.println("LeaderBoard:");	    //Leader Board Displayed

			for (Integer item: stack) {
				for (Entry<String, Integer> entry : lb.entrySet()) {
					if (entry.getValue().equals(item)) {
						System.out.println((stack.indexOf(item)+1)+" "+entry.getKey()+" "+item);
					}
				}
			}
			//Getting Users Choice on repeating or ending game
			System.out.println("Enter your choice: 1- To continue Playing/0- To end game");

			try {
				choice = scan.nextInt(); 
			}
			catch(Exception e) {
				System.out.println("Ending Game due to Wrong choice");
				choice = 0;

			}
		}
		System.out.println("__Thank You for Playing__");

		//THE NEW/EDITED DATA IS AGAIN WRITTEN ON THE FILE IN THIS SNIPPET OF CODE
		try {
			FileWriter myWriter = new FileWriter("LeaderBoard.txt");

			for (Integer item: stack) {
				for (Entry<String, Integer> entry : lb.entrySet()) {
					if (entry.getValue().equals(item)) {
						myWriter.write((stack.indexOf(item)+1)+" "+item+" "+entry.getKey()+"\r\n");
					}
				}
			}

			myWriter.close(); 
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}


	}

	//THIS FUNCTION PROCESS THE SCORE
	public static int Score(int guesses, String word) {		
		String k="";
		for (int i = 0; i < word.length(); i++)
			if(k.contains(""+word.charAt(i))) {
				continue;
			}
			else {
				k=k+word.charAt(i);
			}
		int result=guesses*word.length();
		return result;
	}	

	//THE FUNCTION SORTS THE STACK
	public static Stack sortstk( Stack s )		
	{
		Stack<Integer> temp = new Stack<Integer>();

		while( !s.isEmpty() )
		{

			int s1 = (int) s.pop();

			while( !temp.isEmpty() && (temp.peek() < s1) )
			{
				s.push( temp.pop() );
			}
			temp.push( s1 );

		}
		int j=temp.size()-1;
		for(int i=0;i<=j;i++){
			if(i!=j) {
				if(temp.get(i) == temp.get(i + 1)) {
					temp.remove(i);
					j-=1;
				}
			}

		}


		return temp;


	}



}

