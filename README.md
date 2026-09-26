# Java Sudoku Solver

A coursework Sudoku solver written in Java. It tracks possible values for each
cell, applies row, column, and 3×3 box rules, and uses guesses with backtracking
when those rules alone cannot complete a puzzle.

## Requirements

- Java Development Kit (JDK)

## Run

From the project root, compile the source files:

```bash
javac -d out src/code/*.java
```

On Windows PowerShell, use `src\code\*.java` instead. Then run either puzzle:

```bash
java -cp out code.SudokuSolver easy
java -cp out code.SudokuSolver hard
```

With no argument, the program uses the easy puzzle. The input files are in
`src/easyPuzzle.txt` and `src/hardPuzzle.txt`. Each file contains nine rows of
nine space-separated digits; `0` represents an empty cell. Run the program
from the project root so it can locate the input files.

## Project files

- `src/code/SudokuSolver.java`: entry point
- `src/code/Board.java`: board state, candidate elimination, solving rules, and guesses
- `src/code/Cell.java`: individual cell and its candidate values
- `src/code/Guess.java`: snapshot used to restore a board after a failed guess

## Note

This is a coursework project. This package contains two puzzle inputs, easy
and hard. The program has been checked against those two inputs; it does not
claim to solve every valid Sudoku puzzle.
