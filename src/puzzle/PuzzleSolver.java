package puzzle;

import java.util.*;

public class PuzzleSolver {
	public static void main(String[] args) {
		int[][] eightPuzzle = null;
		
		Scanner inp = new Scanner (System.in);
		System.out.println("input something");
		int num = inp.nextInt();
		System.out.println("input is " + num);
		
		//check if there's atleast 1 arg
		if(args.length < 1) {
			System.out.println("Please provide a file name.");
		}
		else {
			//create maze through input file given by first arg
			try {
				eightPuzzle = PuzzleHelper.createMatrix(args[0]);
				
				if(eightPuzzle != null)
					PuzzleHelper.PrintMatrix(eightPuzzle);
			}
			catch (Exception e){
				System.out.println("Error: " + e);
				System.out.println("Input file not in correct format.");
			}
		}

		//if puzzle could not be created
		if (eightPuzzle == null) {
			System.out.println("Please provide a valid puzzle");
		}
		//checks for a second argument
		else if (args.length < 2) {
			System.out.println("Please provide search algorithm");
		}
		else {
			//check which search algorithm
			switch (args[1]) {
			case "BFS" :	BFS.BFSSearch(eightPuzzle);
							break;
							
			case "DFS" :	if (args.length < 3) {
								System.out.println("Please provide a depth limit");
							}
							if (Integer.parseInt(args[2]) > 0) {
								DFS.DFSSearch(eightPuzzle, Integer.parseInt(args[2]));
							}
							else {
								System.out.println("Invalid depth limit");
							}
							break;
							
			case "ID" :		if (args.length < 3) {
								System.out.println("Please provide a depth limit");
							}
							if (Integer.parseInt(args[2]) > 0) {
								ID.IDSearch(eightPuzzle, Integer.parseInt(args[2]));
							}
							else {
								System.out.println("Invalid depth limit");
							}
							break;
							
			case "A_Star" :	if (args.length < 3) {
								System.out.println("Please provide a heuristic");
							}
							if (args[2] == "Manhattan" || args[2] == "Other") {
								A_Star.A_StarSearch(eightPuzzle, args[2]);
							}
							else {
								System.out.println("Invalid Heuristic");
							}
							break;
							
			case "Greedy" :	if (args.length < 3) {
								System.out.println("Please provide a heuristic");
							}
							if (args[2] == "Manhattan" || args[2] == "Other") {
								Greedy.GreedySearch(eightPuzzle, args[2]);
							}
							else {
								System.out.println("Invalid Heuristic");
							}
							break;
							
			default :		System.out.println("Invalid Search Algorithm");
							break;
			}
		}
	}
	

}
