import java.io.File; 
import java.io.FileNotFoundException; 
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.Scanner; 
import java.util.ArrayList;
import java.util.Arrays;


public class Source {
	/* Descriptive comment block */

	public static void main(String[] args) {
		File inputFile = null; 
		Scanner input = null; 
		PrintWriter outFile = null; 
		
		boolean showInputOptions = true;
		int userInputChoice = 0;
		
		input = new Scanner(System.in);
		
		while(showInputOptions) {
			printChoiceMenu();
			System.out.print("Your choice: ");
			userInputChoice = input.nextInt();
			
			if(userInputChoice != 1 && userInputChoice != 2) {
				printIncorrectChoice(userInputChoice, 2);
			}else {
				showInputOptions = false;
			}
		}
		
		if(userInputChoice == 1)
			printManualEntryMenu(input);
		if(userInputChoice == 2)
			printFileInput();
	}
	
	public static void printChoiceMenu() {
		System.out.println("Welcome to Immediate Indulgences Ice-Cream! 🍦\n"
				+ "Would you like to: \n"
				+ "1) Manually process an order \n"
				+ "2) Read an order from a file");
	}
	
	public static void printIncorrectChoice(int incorrectChoice, int numChoices) {
		String errorMessage = "* "+ "Please choose between 1 and "+ numChoices + " *";
		String stars = "";
		
		for(int i = 0; i < errorMessage.length(); i++) {
			stars += "*";
		}
		System.out.println(stars);
		System.out.println(errorMessage);
		System.out.println(stars);
	}
	
	public static ArrayList<Integer> printManualEntryMenu(Scanner input) {
		int numItemsOrdered = 1;
		boolean userOrdering = true;
		int userItemChoice = 0;
		ArrayList<Integer> userChoiceArray = new ArrayList<Integer>();
		
		String[][] menu = {
				{"Single Scoop Dish", "1.50", "singledish"},
				{"Double Scoop Dish", "3.00", "doubledish"},
				{"Single Scoop Cone", "2.00", "singlescoop"},
				{"Double Scoop Cone", "3.50", "doublescoop"},
				{"Waffle Cone", "4.00", "wafflecone"},
				{"Banana Split", "5.00", "bananasplit"},
				{"Turtle Sundae", "6.25", "turtle"},
				{"Shake", "4.00", "shake"},
				{"Malt", "4.50", "malt"},
				{"Concrete Malt", "5.00", "concrete"},
				{"Soda", "1.50", "soda"},
				{"Soda Float", "3.25", "float"},
				{"Ice Cream Sandwich","3.75", "icesandwich"}
		};
		
		while(userOrdering) {
			System.out.println("Menu:");
			
			for(int i = 0; i < menu.length; i++) {
				// ternary will return a number followed by dot and space single digits and just dot for double digit numbers
				System.out.printf("%-3s %-20s $%s%n", i+1 < 10 ? (i+1) + ". ": (i+1) + ".", menu[i][0], menu[i][1]);
			}
			
			System.out.println("To finish ordering type: -1");
			System.out.printf("Please choose item #%d: ", numItemsOrdered);
			userItemChoice = input.nextInt();
			if(userItemChoice >= 1 && userItemChoice <=13) {
				userChoiceArray.add(userItemChoice);
				numItemsOrdered++;				
			}else if(userItemChoice == -1){
				userOrdering = false;
			}else {
				printIncorrectChoice(userItemChoice, menu.length);
			}
		}
//		System.out.println(userChoiceArray.toString());
		return userChoiceArray;
	}
	
	public static void printFileInput() {
		
	}

}
