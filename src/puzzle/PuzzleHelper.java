package puzzle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class PuzzleHelper {
	//creates matrix
	public static int[][] createMatrix(String fileName) throws IOException {
		BufferedReader ifile = new BufferedReader( new FileReader(fileName) );
		List<int[]> tempPuzzle = new ArrayList<int[]>();
		int[] tempRow;
		StringTokenizer tokens;
		//String[] tempRowString;
		int rowSize;
		int[][] puzzle = null;
		
		//if input file is empty, return null
		String line = ifile.readLine();
		if (line == null) {
			return null;
		}

		//start creating matrix from input file
		while (line != null) {
			tokens = new StringTokenizer(line, ",");
			tempRow = new int[tokens.countTokens()];
			for(int i=0; i < tempRow.length; i++) {
				tempRow[i] = Integer.parseInt(tokens.nextToken());
			}
			tempPuzzle.add(tempRow);
			line = ifile.readLine();
		}
		
		rowSize = tempPuzzle.get(0).length;
		puzzle = new int[tempPuzzle.size()][rowSize];
		
		for (int i=0; i<tempPuzzle.size();i++) {
			if(rowSize != tempPuzzle.get(i).length) {
				System.out.println("Matrix not a Square or Rectangle");
				return null;
			}
			for(int j=0; j<rowSize;j++) {
				puzzle[i][j] = tempPuzzle.get(i)[j];
			}
			
		}
		
		return puzzle;
	}
	
	//function: takes in 2 dimensional int matrix and outputs
	public static void PrintMatrix(int[][] matrix) {
		if(matrix.length == 0) {
			System.out.println("Empty Matrix");
		}
		else {
			System.out.println("Printing Matrix:");
			System.out.println("Number of rows: " + matrix.length);
			System.out.println("Number of Columns: " + matrix[0].length);
			for(int i=0;i<matrix.length;i++) {
				for(int j=0;j<matrix[i].length;j++) {
					System.out.print(matrix[i][j] + " ");
				}
				System.out.println();
			}
		}
	}
	
	//checks if piece is moveable in that direction
	//takes in int[][] puzzle and direction character 'N', 'S', 'W', or 'E'
	public static boolean isMoveable (int[][] puzzle, char direction) {
		direction = Character.toUpperCase(direction);
		int rowLoc=0;
		int colLoc=0;
		boolean found=false; //0 checker; if 0 doesn't exist, return false
		
		//checks direction is valid
		if(direction != 'N' && direction != 'S' &&
				direction != 'W' && direction != 'E') {
			return false;
		}
		
		//search for 0 piece
		for(int row=0; row<puzzle.length; row++) {
			for(int col=0; col<puzzle[row].length; col++) {
				if(puzzle[row][col] == 0) {
					rowLoc = row;
					colLoc = col;
					found = true;
				}
			}
		}
		
		if(!found) {
			return false;
		}
		
		if (direction == 'N') {
			if(rowLoc == 0) {
				return false;
			}
		}
		else if (direction == 'S') {
			if(rowLoc+1 == puzzle.length) {
				return false;
			}
		}
		else if (direction == 'W') {
			if(colLoc == 0) {
				return false;
			}
		}
		else if (direction == 'E') {
			if(colLoc+1 == puzzle[rowLoc].length) {
				return false;
			}
		}
		
		return true;
	}
	
	//moves piece in that direction. returns false if not possible
	//takes in int[][] puzzle and direction character 'N', 'S', 'W', or 'E'
	public static int[][] movePiece (int[][] puzzle, char direction) {
		int rowLoc=0;
		int colLoc=0;
		int temp;
		boolean found=false; //0 checker; if 0 doesn't exist, return false
		int[][] tempPuzzle = new int[puzzle.length][];; //to keep original puzzle
		
		//check if piece is moveable
		if(!isMoveable(puzzle, direction)) {
			return puzzle;
		}
		
		for(int i=0; i<puzzle.length; i++) {
			tempPuzzle[i] = Arrays.copyOf(puzzle[i], puzzle[i].length);
		}
		
		//search for 0 piece
		for(int row=0; row<tempPuzzle.length; row++) {
			for(int col=0; col<tempPuzzle[row].length; col++) {
				if(tempPuzzle[row][col] == 0) {
					rowLoc = row;
					colLoc = col;
					found = true;
				}
			}
		}
		
		if(!found) {
			return tempPuzzle;
		}

		switch (direction) {
		case 'N' :	temp = tempPuzzle[rowLoc-1][colLoc];
					tempPuzzle[rowLoc-1][colLoc] = 0;
					tempPuzzle[rowLoc][colLoc] = temp;
				break;
			
		case 'S' :	temp = tempPuzzle[rowLoc+1][colLoc];
					tempPuzzle[rowLoc+1][colLoc] = 0;
					tempPuzzle[rowLoc][colLoc] = temp;
				break;
			
		case 'W' :	temp = tempPuzzle[rowLoc][colLoc-1];
					tempPuzzle[rowLoc][colLoc-1] = 0;
					tempPuzzle[rowLoc][colLoc] = temp;
				break;
			
		case 'E' :	temp = tempPuzzle[rowLoc][colLoc+1];
					tempPuzzle[rowLoc][colLoc+1] = 0;
					tempPuzzle[rowLoc][colLoc] = temp;
				break;
			
		default :
		}
		
		return tempPuzzle;
	}
	
	//calculates heuristic if piece moved in the parameter direction
	//takes in int[][] puzzle, direction ('N', 'S', 'W', or 'E'), and heuristic type
	//1 for Manhattan heuristic, 2 for other. Other calculates misplaced tiles
	public static double calcHeuristicFor (int[][] puzzle, char direction, int type) {
		direction = Character.toUpperCase(direction);
		double cost = 0;
		int squareNum = 0;
		int rowSize = puzzle.length;
		int colSize = puzzle[0].length;
		int[][] checkPuzzle = null;
		
		if(direction != 'N' && direction != 'S' &&
				direction != 'W' && direction != 'E') {
			return -1;
		}
		
		if(!isMoveable(puzzle, direction)) {
			return -1;
		}
		
		checkPuzzle = movePiece(puzzle, direction);
		
		//check each piece
		for(int row=0; row<checkPuzzle.length; row++) {
			for(int col=0; col<checkPuzzle[row].length; col++) {
				squareNum = row*checkPuzzle[row].length + col;
				if(checkPuzzle[row][col] != squareNum && 
						squareNum != checkPuzzle.length*checkPuzzle[checkPuzzle.length-1].length) {
					if(type == 1) {
						//dist in row
						cost += Math.abs(squareNum%rowSize - checkPuzzle[row][col]%rowSize);
						//dist in column
						cost += Math.abs(squareNum/colSize - checkPuzzle[row][col]/colSize);
					}
					if(type == 2) {
						cost++;
					}
				}
			}
		}
		
		return cost;
	}
	
	public static boolean isSameMatrix() {
		
		return false;
	}
}
