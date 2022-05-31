import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;

public class Kim_Nikolay_Assignment1 {
	/* 
	* Author Nikolay Kim #1224530073
	* Class: CSE 205
	* Class Time: Summer'22 / M T W Th @ 1 PM
	 * Instructor: Prof. Justin Selgrad
	 * Assignment #: 1
	 * Date: 30 May 2022
	 * Description: 
		 * Ice-cream shop ordering system. 
		 * This program allows manual order entry or a read from a file.
		 * User can order as many items as they want
		 * An invoice/receipt is generated in the console.
	 */

	public static void main(String[] args) {
		Scanner userInput = new Scanner(System.in);; 
		boolean showInputOptions = true;
		int userInputChoice = 0;
		
		String[][] menu = {
				{"Single Scoop Dish", "1.50", "singledish"},
				{"Double Scoop Dish", "3.00", "doubledish"},
				{"Single Scoop Cone", "2.00", "singlecone"},
				{"Double Scoop Cone", "3.50", "doublecone"},
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
		
		// Holds user orders in a 2d array
		ArrayList<ArrayList<String>> userOrder = new ArrayList<ArrayList<String>>();
		
		while(showInputOptions) {
			printOptionsMenu();
			System.out.print("Your choice: ");
			userInputChoice = userInput.nextInt();
			
			switch (userInputChoice) {
			case 1:
				printManualEntryMenu(menu, userInput, userOrder);
				eraseUserOrderArray(userOrder);
				System.out.println();
				break;
			case 2:
				printFileInput(menu, userInput, userOrder);
				eraseUserOrderArray(userOrder);
				System.out.println();
				break;
			case 3:
				displayInvoiceFile();
				break;
			case -1:
				System.out.println("Goodbye!");
				showInputOptions = false;
				break;
			default:
				// If user enters unavailable option, a message will be displayed and options menu will be displayed again 
				printIncorrectChoice(userInputChoice, 3);
			}
		}
			
		userInput.close();
	}
	
	public static void printOptionsMenu() {
		/**
		 Displays available options.
		 */
		System.out.println("Welcome to Immediate Indulgences Ice-Cream! 🍦\n"
				+ "Would you like to: \n"
				+ "1) Manually process an order\n"
				+ "2) Read an order from a file\n"
				+ "3) Display invoice.txt\n"
				+ "Type -1 to exit");
	}
	
	public static void printIncorrectChoice(int incorrectChoice, int numChoices) {
		/**
		 If user inputs unavailable option this method will display a message in the console.
		 @param 	incorrectChoice		user's input
		 @param		numChoices 			number of available options
		 */
		
		String errorMessage = "* "+ "Please choose between 1 and "+ numChoices + " *";
		String stars = "";
		
		for(int i = 0; i < errorMessage.length(); i++) {
			stars += "*";
		}
		System.out.println(stars);
		System.out.println(errorMessage);
		System.out.println(stars);
	}
	
	public static void printManualEntryMenu(String[][] menu, Scanner input, ArrayList<ArrayList<String>> userOrder) {
		/**
		 This method prints menu and takes/stores orders
		 @param 	menu		A 2D array of Strings. [["item 1", "item price", "lower-case string id"]]
		 @param 	input		Scanner reference with System.in
		 @param		userOrder	2D array of Strings. Stores user orders
		 */
		
		int numItemsOrdered = 1;
		boolean userOrdering = true;
		int userItemChoice = 0;
				
		while(userOrdering) {
			System.out.println("Menu:");
			
			for(int i = 0; i < menu.length; i++) {
				// ternary returns either dot or dot and space for uniform print
				System.out.printf("%-3s %-20s $%s%n", i+1 < 10 ? (i+1) + ". ": (i+1) + ".", menu[i][0], menu[i][1]);
			}
			
			System.out.println("To finish ordering type: -1");
			System.out.printf("Please choose item #%d: ", numItemsOrdered);
			userItemChoice = input.nextInt();
			input.nextLine();
			
			if(userItemChoice >= 1 && userItemChoice <= menu.length) {
				ArrayList<String> orderPiece = new ArrayList<String>();
				orderPiece.add(menu[userItemChoice-1][0]);
				orderPiece.add(menu[userItemChoice-1][1]);
				userOrder.add(orderPiece);
				numItemsOrdered++;				
			}else if(userItemChoice == -1){
				userOrdering = false;
			}else {
				printIncorrectChoice(userItemChoice, menu.length);
			}
		}
		
		if(userOrder.size() < 1) {
			System.out.println("Your shopping cart is empty.");
		}else {
			generateInvoice(userOrder);
		}
	}
	
	public static void printFileInput(String[][] menu, Scanner userInput, ArrayList<ArrayList<String>> userOrder) {
		/**
		 This method takes a file name, reads it and stores orders in userOrder
		 @param 	menu		A 2D array of Strings. [["item 1", "item price", "lower-case string id"]]
		 @param 	input		Scanner reference with System.in
		 @param		userOrder	A reference to the 2D array of Strings. Stores user orders
		 */
		
		File inputFile;
		Scanner input;
		String fileName;
		
		System.out.println("Processesing order from file.");
		userInput.nextLine();
		System.out.print("Please enter the filename: ");
		fileName = userInput.nextLine();
		
		try {
			inputFile = new File(fileName);
			input = new Scanner(inputFile);
			
			while(input.hasNextLine()) {
				String order = input.nextLine();
				System.out.println("read "+order);
				for(String[] menuChoiceStrings : menu) {
					if(menuChoiceStrings[2].equals(order)) {
						ArrayList<String> orderPiece = new ArrayList<String>();
						orderPiece.add(menuChoiceStrings[0]);
						orderPiece.add(menuChoiceStrings[1]);
						userOrder.add(orderPiece);
					}
				}
			}
			generateInvoice(userOrder);
			input.close();
		} catch (Exception e) {
			System.out.println("File does not exist.");
		}
		
		
	}
	
	public static void generateInvoice(ArrayList<ArrayList<String>> userOrder) {
		/**
		 This method generates an invoice from an array of user orders.
		 @param 	userOrder	A reference to 2D array of user orders.
		 */
		PrintWriter outfile;
		
		float subtotal = 0.0f;
		float tax = 0.0f;
		float taxRate = 0.05f;
		float total = 0.0f;
		
		try {
			outfile = new PrintWriter("invoice.txt");
			
			outfile.println(" IMMEDIATE INDULGENCES ICE-CREAM");
			outfile.println("---------------------------------");
			for(ArrayList<String> orderEntry: userOrder) {
				String orderItem = orderEntry.get(0);
				float orderPrice = Float.parseFloat(orderEntry.get(1));
				subtotal += orderPrice;
				outfile.printf("%-20s -      $%.2f%n", orderItem.toUpperCase(), orderPrice );
			}
			tax = subtotal * taxRate;
			total = (float)(Math.ceil((subtotal + tax) * 100)) /100;
			outfile.println("---------------------------------");
			outfile.printf("%20s -     %6.2f%n","Subtotal",subtotal);
			outfile.printf("%20s -     %6.2f%n","Tax",tax);
			outfile.printf("%20s -     %6.2f%n","Total",total);
			
			outfile.close();
			System.out.println("invoice.txt generated");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void displayInvoiceFile() {
		/**
		 Reads and displays invoice.txt
		 */
		try {
			 File invoiceFile = new File("invoice.txt");
			 Scanner inFile = new Scanner(invoiceFile);
			 
			 while(inFile.hasNextLine()) {
				 System.out.println(inFile.nextLine());
			 }
			 inFile.close();
		} catch (Exception e) {
			System.out.println("invoice.txt does not exist.");
		}
	}
	
	public static void eraseUserOrderArray(ArrayList<ArrayList<String>> userOrder) {
		/**
		 Removes everything from userOrder array between orders.
		 @param 	userOrder	A reference to the 2D array of Strings. Contains all orders.t
		 */
		while(userOrder.size() > 0) {
			userOrder.remove(0);
		}
	}
}

