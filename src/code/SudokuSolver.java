package code;

public class SudokuSolver {

	public static void main(String[] args)throws Exception {
		
		Board puzzle = new Board();
		String level = args.length > 0 ? args[0] : "easy";
		puzzle.loadPuzzle(level);
		puzzle.display();
		puzzle.logicCycles();
		puzzle.display();
		//System.out.println(puzzle.errorFound());
		//System.out.println(puzzle.isSolved());
		

	}

}
