package puzzle;

public class testing {
	public static void main(String[] args) {
		int[][] puzzle = {{6,1,2}, {3,4,5}, {0,7,8}};
		int[][] temp;

		PuzzleHelper.PrintMatrix(puzzle);
		temp = PuzzleHelper.movePiece(puzzle, 'N');
		PuzzleHelper.PrintMatrix(temp);
		temp = PuzzleHelper.movePiece(puzzle, 'S');
		PuzzleHelper.PrintMatrix(temp);
		temp = PuzzleHelper.movePiece(puzzle, 'W');
		PuzzleHelper.PrintMatrix(temp);
		temp = PuzzleHelper.movePiece(puzzle, 'E');
		PuzzleHelper.PrintMatrix(temp);
		
		double cost = PuzzleHelper.calcHeuristicFor(puzzle, 'N', 2);
		System.out.println("Cost: " + cost);
	}
}
