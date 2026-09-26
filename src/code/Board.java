package code;

import java.io.File;
import java.util.Scanner;

public class Board{
	
	/*The Sudoku Board is made of 9x9 cells for a total of 81 cells.
	 * In this program we will be representing the Board using a 2D Array of cells.
	 * 
	 */

	private Cell[][] board = new Cell[9][9];
	
	//The variable "level" records the level of the puzzle being solved.
	private String level = "";
	private Guess[] listOfGuesses = new Guess[81];
	private int numberOfGuesses = 0;
	
	public int getNumberOfPotentials(int x, int y) {
		boolean [] potential = new boolean [10];
		potential = board[x][y].getPotential();
		int NumberOfPotentials = 0;
		for (int i = 1; i < 10; i++) {
			if (potential[i] == true)
				NumberOfPotentials++;
		}
		return NumberOfPotentials;
	}
	///TODO: CONSTRUCTOR
	//This must initialize every cell on the board with a generic cell.  It must also assign all of the boxIDs to the cells
	public Board()
	{
		for(int x = 0; x < 9; x++)
			for(int y = 0 ; y < 9; y++)
			{
				board[x][y] = new Cell();
				board[x][y].setBoxID( 3*(x/3) + (y)/3+1);
			}
	}
	
	public int getNumber (int x, int y) {
		return board[x][y].getNumber();
	}
	
	public boolean[] getPotential (int x, int y) {
		return board[x][y].getPotentialArray();
	}
	
	
//	public Board copy() {
//		Board copy = new Board();
//		Cell[][] nCopy = new Cell[9][9];
//		boolean [] potential = new boolean [10];
//		
//		//copying numbers
//		for (int x = 0; x < 9; x++) {
//			for (int y = 0; y < 9; y++) {
//				nCopy[x][y] = new Cell();
//				nCopy[x][y].setNumber(board[x][y].getNumber());
//				
//			}
//		}
//		
//		for (int x = 0; x < 9; x++) {
//			for (int y = 0; y < 9; y++) {
//				potential = board[x][y].getPotential();
//				nCopy[x][y].setPotential(potential);
//			}
//		}
//		copy.setBoard(nCopy);
//		return copy;
//	}
//	
	///TODO: loadPuzzle
	/*This method will take a single String as a parameter.  The String must be either "easy", "medium" or "hard"
	 * If it is none of these, the method will set the String to "easy".  The method will set each of the 9x9 grid
	 * of cells by accessing either "easyPuzzle.txt", "mediumPuzzle.txt" or "hardPuzzle.txt" and setting the Cell.number to 
	 * the number given in the file.  
	 * 
	 * This must also set the "level" variable
	 * TIP: Remember that setting a cell's number affects the other cells on the board.
	 */
	public void loadPuzzle(String level) throws Exception
	{
		if (!level.equals("easy") && !level.equals("hard")) {
			throw new IllegalArgumentException("Available puzzles: easy, hard");
		}
		this.level = level;
		String fileName = "src/" + level + "Puzzle.txt";
		
		Scanner input = new Scanner (new File(fileName));
		
		for(int x = 0; x < 9; x++)
			for(int y = 0 ; y < 9; y++)
			{
				int number = input.nextInt();
				if(number != 0) {
					solve(x, y, number);
				}
			}
						
		input.close();
		
	}
	
	///TODO: isSolved
	/*This method scans the board and returns TRUE if every cell has been solved.  Otherwise it returns FALSE
	 * 
	 */
	public boolean isSolved()
	{
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				if (board[x][y].getNumber() == 0)
					return false;
			}
		}
		return true;
	}


	///TODO: DISPLAY
	/*This method displays the board neatly to the screen.  It must have dividing lines to show where the box boundaries are
	 * as well as lines indicating the outer border of the puzzle
	 */
	public void display()
	{
		boolean [] potential = new boolean[10];
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				System.out.print(board[x][y].getNumber());
				if (y < 8) {
					System.out.print(" ");
					if (y % 3 == 2) {
						System.out.print("| ");
					}
				}
				else {
					System.out.println();
					if (x % 3 == 2 && x != 8) {
						System.out.println("---------------------");
					}
				}
				
			}
		}
//		///TODO: potential display - Get rid of it later
//		System.out.println();
//		for (int x = 0; x < 9; x++) {
//			for (int y = 0; y < 9; y++) {
//				System.out.print("* ");
//				for (int i = 0; i < 10; i++) {
//					potential = board[x][y].getPotential();
//					
//					if (potential[i] == true)
//						System.out.print(i + " ");
//				}
//				System.out.println();
//			}
//			System.out.println("next line");
//		}
		System.out.println();
	}
	
//	public void display(int [][] numbers, boolean potentials[][][])
//	{
//		boolean [] potential = new boolean[10];
//		for (int x = 0; x < 9; x++) {
//			for (int y = 0; y < 9; y++) {
//				System.out.print(numbers[x][y]);
//				if (y < 8) {
//					System.out.print(" ");
//					if (y % 3 == 2) {
//						System.out.print("| ");
//					}
//				}
//				else {
//					System.out.println();
//					if (x % 3 == 2 && x != 8) {
//						System.out.println("---------------------");
//					}
//				}
//				
//			}
//		}
//		///TODO: potential display - Get rid of it later
//		System.out.println();
//		for (int x = 0; x < 9; x++) {
//			for (int y = 0; y < 9; y++) {
//				System.out.print("* ");
//				for (int i = 0; i < 10; i++) {
//					potential[i] = potentials[x][y][i];
//					
//					if (potential[i] == true)
//						System.out.print(i + " ");
//				}
//				System.out.println();
//			}
//			System.out.println("next line");
//		}
//		
//	}
	
	///TODO: solve
	/*This method solves a single cell at x,y for number.  It also must adjust the potentials of the remaining cells in the same row,
	 * column, and box.
	 */
	public void solve(int x, int y, int number)
	{
		int sameBox = board[x][y].getBoxID();
		boolean [] potential = new boolean[10];
		int originalX = 0;
		int originalY = 0;
		board[x][y].setNumber(number);
		
		//row
		for (int i = 0; i < 9; i++) {
			//System.out.println("nth cell: " + i);
			if (board[x][i].getNumber() == 0) {
				potential = board[x][i].getPotential();
				if (i != y) {
					potential[number] = false; 
					//System.out.println("false: " + number);
	
				}
				else {
					potential[number] = true;
				}
				board[x][i].setPotential(potential);
			}
		}

		//column
		for (int i = 0; i < 9; i++) {
			if (board[i][y].getNumber() == 0) {
				potential = board[i][y].getPotential();
				if (i != x) {
					potential[number] = false; 
	
				}
				else {
					potential[number] = true;
				}
				board[i][y].setPotential(potential);
			}
		}
		
		//box
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				potential = board[i][j].getPotential();
				if (board[i][j].getNumber() == 0) {
					if (board[i][j].getBoxID() == sameBox) {
						potential[number] = false;
					}
					
					board[i][j].setPotential(potential);
				}
			}
		}
		//System.out.println("solve");
		
		//System.out.println("next line11111111111111111111111111111111111");
		
		//display();
	}
	
	
	//TODO: logicCycles() continuously cycles through the different logic algorithms until no more changes are being made.
	public void logicCycles()throws Exception
	{

		boolean [] potential = new boolean [10];
		int [][] tried = new int [10][2];
		int [][] numbers = new int [9][9];
		boolean[][][] potentials = new boolean [9][9][10];
		int counter = 0;
		Board copy = new Board();
		boolean guess = false;
		
//		for (int x = 0; x < 9; x++) {
//			for (int y = 0; y < 9; y++) {
//				numbers[x][y] = board[x][y].getNumber();
//				potential = board[x][y].getPotential();
//				for (int i = 1; i < 10; i++) {
//					potentials[x][y][i] = potential[i];
//				}
//				for (int i = 1; i < 10; i++) {
//					potential[i] = false;
//				}
//			}
//		}
		while(isSolved() == false)
		{
			int changesMade = 0;
			do
			{
				changesMade = 0;

				changesMade += logic1();
//				System.out.println("logic 1 done");
//				System.out.println("changes: " + changesMade);
				changesMade += logic2();
//				System.out.println("logic 2 done");
//				System.out.println("changes: " + changesMade);
				changesMade += logic3();
//				System.out.println("logic 3 done");
//				System.out.println("changes: " + changesMade);
				changesMade += logic4();
//				System.out.println("logic 4 done");
//				System.out.println("changes: " + changesMade);
				
//				if(errorFound() == false && changesMade == 0) {
//					System.out.println("errorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
//					board = copy.getBoard();
//					copy = new Board();
//					for (int x = 0; x < 10; x++) {
//						if (tried[x][0] != 0) {
//							System.out.println("wordked");
//							for (int i = 1; i < 10; i++) {
//								if (potential[i] == true && tried[i][0] == 0) {
//									copy = copy();
//									solve(tried[x][0],tried[x][1],i);
//									tried[i][0] = tried[x][0];
//									tried[i][1] = tried[x][1];
//									System.out.println("x: " + tried[x][0] + " y: " + tried[x][1] + " guessssssssssssssssssssssssssssssssssssssssss " + i);
//								}
//							}
//						}
//					}
//				}
				if (errorFound())  {
					revert();
					changesMade++;
					break;
				}
				
				if (changesMade == 0 && isSolved() == false) {
//					display();
					guess();
//					display();
					changesMade++;
					
					
				}
				//System.out.println("changes: " + changesMade);
				//System.out.println();
//				if (counter < 10) {
//					display();
//				}
				counter++;
			}while(changesMade != 0);
			//display();
			
//			if (changesMade == 0 && isSolved() == false) {
//				for (int x = 0; x < 9; x++) {
//					for (int y = 0; y < 9; y++) {
//						numbers[x][y] = board[x][y].getNumber();
//						potential = board[x][y].getPotential();
//						for (int i = 1; i < 10; i++) {
//							potentials[x][y][i] = potential[i];
//						}
//						for (int i = 1; i < 10; i++) {
//							potential[i] = false;
//						}
//					}
//				}
//				
//				
//				for (int x = 0; x < 9; x++) {
//					for (int y = 0; y < 9; y++) {
//						if (board[x][y].getNumber() == 0) {
//							for (int i = 1; i < 10; i++) {
//								if (potentials[x][y][i] == true) {
//									solve(x,y,i);
//									guess = true;
//									break;
//								}
//							}
//						}
//						if (guess == true)
//							break;
//					}
//					if (guess == true)
//						break;
//				}
//				guess = false;
//			}
////				guess= false;
//			counter++;
//			if (counter < 10) {
//				display();
//			}
			}
			
		
	}			
	
	 public void revert() {
	        for(int x = 0; x < 9; x++) {
	            for(int y = 0; y < 9; y++)
	            {
	                board[x][y].reset();
	                board[x][y].setNumber(listOfGuesses[numberOfGuesses-1].getNumber(x,y));
	                
	                boolean[] guessPotentials = listOfGuesses[numberOfGuesses-1].getPotentialArray(x,y);
	            
	                for(int number = 1; number < 10; number++)
	                {
	                    if(guessPotentials[number] == true)
	                        board[x][y].turnOnPotential(number);
	                    else
	                        board[x][y].turnOffPotential(number);
	                }
	                
	                
	            
	            }
	         
	        }
	        numberOfGuesses--;
            
            boolean guessMade = false;
	 
	        for(int x = 0; x < 9 && !guessMade ; x++) {
                for(int y = 0; y < 9 && !guessMade ; y++) {
                    if(board[x][y].getNumber() == 0) 
                    {
                        board[x][y].turnOffPotential(board[x][y].getFirstPotential());            
                        guessMade = true;
                    }
                }
            }
	 }
	        
	
	
	  public void guess() {
	        
	        boolean guessMade = false;
	        
	        for(int x = 0; x < 9 && !guessMade ; x++) {
	            for(int y = 0; y < 9 && !guessMade ; y++)
	                if(board[x][y].getNumber() == 0) 
	                {
	                    listOfGuesses[numberOfGuesses++] = new Guess(this);
	                    
	                    solve(x,y,board[x][y].getFirstPotential());
	                    guessMade = true;
	                }
	        }
	    }
	
	///TODO: logic1
	/*This method searches each row of the puzzle and looks for cells that only have one potential.  If it finds a cell like this, it solves the cell 
	 * for that number. This also tracks the number of cells that it solved as it traversed the board and returns that number.
	 */
	public int logic1()
	{
		int changesMade = 0;
		int potentialCount = 0;
		int cellNumber = 0;
		boolean[] potential = new boolean[10];
		
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				potential = board[x][y].getPotential();
				
				for (int i = 0; i < 10; i++) {
					if (potential[i] == true) {
						potentialCount++;
						cellNumber = i;
					}
					
				}
				
				if (potentialCount == 1 && board[x][y].getNumber() == 0) {
					solve(x,y,cellNumber);
					changesMade++;
				}
				potentialCount = 0;
					
			}
		}
		return changesMade;
					
	}
	
	///TODO: logic2
	/*This method searches each row for a cell that is the only cell that has the potential to be a given number.  If it finds such a cell and it
	 * is not already solved, it solves the cell.  It then does the same thing for the columns.This also tracks the number of cells that 
	 * it solved as it traversed the board and returns that number.
	 */
	
	public int logic2()
	{
		int changesMade = 0;
		//int cellNumber = 0;
		boolean[] potential = new boolean[10];
		int[][] potentialCount = new int[10][2];
		//int[] onlyCell = new int[10];
		
		//row
		for (int x = 0; x < 9; x++) {
			
			for (int y = 0; y < 9; y++) {
				potential = board[x][y].getPotential();
				
				if (board[x][y].getNumber() == 0) {
					
					//checks what potential numbers does a cell have
					for (int i = 1; i < 10; i++) {
						if (potential[i] == true && board[x][y].getNumber() == 0) {
							potentialCount[i][0]++;
							potentialCount[i][1] = y;
						}
					}
				}
			}
			
			//find the cell
			for (int i = 1; i < 10; i++) {
				if (potentialCount[i][0] == 1 && board[x][potentialCount[i][1]].getNumber() == 0) {
					//System.out.println("FOUND ++++++++++++++++++++++++++++++++++++++++++++++++++++" + "location:"+ x + " " + potentialCount[i][1] + " number: " + i);
					solve(x,potentialCount[i][1], i);
					changesMade++;
				}
			}
			//resets the potentialCount array everytime it changes the row
			for (int j = 1; j < 10; j++) {
				potentialCount[j][0] = 0;
				potentialCount[j][1] = 0;
			}		
		}	
		
		
		//column
		for (int y = 0; y < 9; y++) {
			
			for (int x = 0; x < 9; x++) {
				potential = board[x][y].getPotential();
				
				if (board[x][y].getNumber() == 0) {
					
					//checks what potential numbers does a cell have
					for (int i = 1; i < 10; i++) {
						if (potential[i] == true && board[x][y].getNumber() == 0) {
							potentialCount[i][0]++;
							potentialCount[i][1] = x;
						}
					}
				}
				
				//after checks the last cell of that row
			}
			
			//find the cell
			for (int i = 1; i < 10; i++) {
				if (potentialCount[i][0] == 1 && board[potentialCount[i][1]][y].getNumber() == 0) {
					
					solve(potentialCount[i][1], y, i);
					changesMade++;
				}
			}
			for (int j = 1; j < 10; j++) {
				potentialCount[j][0] = 0;
				potentialCount[j][1] = 0;
			}		
		}
		
//		for (int x = 0; x < 9; x++) {
//			for (int y = 0; y < 9; y++) {
//				if (board[x][y].getNumber() == 0 && board[x][y].getNumberofPotentials)
//			}
//		}
		return changesMade;
	}
	
	///TODO: logic3
	/*This method searches each box for a cell that is the only cell that has the potential to be a given number.  If it finds such a cell and it
	 * is not already solved, it solves the cell. This also tracks the number of cells that it solved as it traversed the board and returns that number.
	 */
	public int logic3()
	{
		boolean[] potential = new boolean[10];
	
		int[][] potentialCount = new int[10][3];
		int changesMade = 0;
		//box
		for (int i = 1; i < 10; i++) {
			for (int x = 0; x < 9; x++) {		
				for (int y = 0; y < 9; y++) {
					if (board[x][y].getNumber() == 0 && board[x][y].getBoxID() == i) {
						potential = board[x][y].getPotential();
						//checks what potential numbers does a cell have
						for (int s = 1; s < 10; s++) {
							if (potential[s] == true && board[x][y].getNumber() == 0) {
								//System.out.println("x: " + x + "y: " + y);
								potentialCount[s][0]++;
								potentialCount[s][1] = x; 
								potentialCount[s][2] = y;
							}
						}
					}
				}		
			}	
					
			for (int s = 1; s < 10; s++) {
				if (potentialCount[s][0] == 1 && board[potentialCount[s][1]][potentialCount[s][2]].getNumber() == 0) {
					//System.out.println("FOUND ++++++++++++++++++++++++++++++++++++++++++++++++++++" + "location:"+ potentialCount[s][1] + " " + potentialCount[s][2] + " number: " + s);
					solve(potentialCount[s][1],potentialCount[s][2], s);
					changesMade++;
				}
			}
			
			//resets the potentialCount array everytime it finished checking one box
			for (int j = 1; j < 10; j++) {
				potentialCount[j][0] = 0;
				potentialCount[j][1] = 0;
				potentialCount[j][2] = 0;
			}
						
		}
		return changesMade;		
	}
	
	
	///TODO: logic4
		/*This method searches each row for the following conditions:
		 * 1. There are two unsolved cells that only have two potential numbers that they can be
		 * 2. These two cells have the same two potentials (They can't be anything else)
		 * 
		 * Once this occurs, all of the other cells in the row cannot have these two potentials.  Write an algorithm to set these two potentials to be false
		 * for all other cells in the row.
		 * 
		 * Repeat this process for columns and rows.
		 * 
		 * This also tracks the number of cells that it solved as it traversed the board and returns that number.
		 */
	public int logic4()
	{
		int changesMade = 0;
		int [] pairs = new int [4];
		boolean [] potential = new boolean [10];
		int potentialCounter = 0;
		boolean firstN = false;
		boolean secondN = false;
		pairs[0] = -1;
		pairs[1] = -1;
		
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				if (board[x][y].numberOfPotentials() == 2) {
					for (int i = x + 1; i < 9; i++) {
						if (board[i][y].numberOfPotentials() == 2) {
							if (board[x][y].getFirstPotential() == board[i][y].getFirstPotential() && 
								board[x][y].getSecondPotential() == board[i][y].getSecondPotential()) {
								
								for (int j = 0; j < 9; j++) {
									if (j != x && j != i) {
										potential = board[j][y].getPotential();
										
										if (potential[board[x][y].getFirstPotential()] == true) {
											board[j][y].cantBe(board[x][y].getFirstPotential());
											changesMade++;
										}
										if (potential[board[x][y].getSecondPotential()] == true) {
											board[j][y].cantBe(board[x][y].getSecondPotential());
											changesMade++;
										}
									}
								}
							}
						}
					}
				}
			}
		}
//				potential = board[x][y].getPotential();
//				//System.out.println();
//				
//				for (int i = 1; i < 10; i++) {
//					//System.out.print(" " + potential[i]);
//					if (potential[i] == true)
//						potentialCounter++;
//				}
//
//				//when find the first there's only two potentials in one cell
//				
//				//System.out.println("x: " + x + " y: " + y + " counter: " + potentialCounter);
//				if (potentialCounter == 2) {
//					//System.out.println(pairs[0]);
//					if (pairs[0] == -1) {
//						pairs[0] = y;
//						for (int i = 1; i < 10; i++) {
//							if (potential[i] == true) {
//								if (pairs[2] == 0) {
//									pairs[2] = i;
//									//System.out.println("First "+ i);
//								}
//								else if (pairs[3] == 0 && pairs[2] != 0) {
//									pairs[3] = i;
//									//System.out.println(i);
//								}
//							}
//							
//						}
//						
//					}
//					else {
//						for (int i = 1; i < 10; i++) {
//							if (potential[i] == true && pairs[2]== i) {
//								firstN = true;
//								//System.out.println("good");
//							}
//							else if (potential[i] = true && pairs[3] == i) {
//								secondN = true;
//								//System.out.println("good");
//							}
//						}
//						if (firstN == true && secondN == true) {
//							pairs[1] = y;
////							System.out.println("wooooooooooooooooooooooooooooooooooooooooooooooorked");
////							System.out.println(pairs[0] + " " + pairs[1]);
//						}
//						firstN = false;
//						secondN = false;
//					}
//					//System.out.println("Those two numbers: " + pairs[2] + " " + pairs[3]);
//				}
//					
//				potentialCounter = 0;
//			}
//			
//			//getting rid of potentials
//			if (pairs[1] != -1) {
//			//System.out.println("****************************");
//				for (int i = 0; i < 9; i++) {
//					//System.out.println("nth cell: " + i);
//					if (board[x][i].getNumber() == 0) {
//						potential = board[x][i].getPotential();
//						
//						if (i != pairs[0] && i != pairs[1]) {
//							potential[pairs[2]] = false; 
//							potential[pairs[3]] = false; 
//							//System.out.println("false: " + number);
//						}
//						board[x][i].setPotential(potential);
//					}
//				}
//			}
//			
//			//resetting
//			for (int i = 0; i < 4; i++) {
//				pairs[i] = 0;
//			}
//			pairs[0] = -1;
//			pairs[1] = -1;
		
//		potentialCounter = 0;
		
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {

			//when find the first there's only two potentials in one cell
				
			if (board[x][y].numberOfPotentials() == 2) {
					//System.out.println(pairs[0]);
				
				for (int i = y + 1; i < 9; i++) {
					if (board[x][i].numberOfPotentials() == 2) {
						if (board[x][y].getFirstPotential() == board[x][i].getFirstPotential() && 
							board[x][y].getSecondPotential() == board[x][i].getSecondPotential()) {
//							System.out.println("1: " +board[x][y].getFirstPotential());
//							System.out.println("2: " + board[x][y].getSecondPotential());
							
							for (int j = 0; j < 9; j++) {
								if (j != y && j != i) {
									potential = board[x][j].getPotential();
									
									if (potential[board[x][y].getFirstPotential()] == true) {
										board[x][j].cantBe(board[x][y].getFirstPotential());
										changesMade++;
									}
									if (potential[board[x][y].getSecondPotential()] == true) {
										board[x][j].cantBe(board[x][y].getSecondPotential());
										changesMade++;
									}
								}
							}
						}
					}
				}
			}
//				if (pairs[0] == -1) {
//					pairs[0] = x;
//					for (int i = 1; i < 10; i++) {
//						if (potential[i] == true) {
//							if (pairs[2] == 0) {
//								pairs[2] = i;
//								//System.out.println("First "+ i);
//							}
//							else if (pairs[3] == 0 && pairs[2] != 0) {
//								pairs[3] = i;
//								//System.out.println(i);
//							}
//						}
//							
//					}
//						
//				}
//				else {
//					for (int i = 1; i < 10; i++) {
//						if (potential[i] == true && pairs[2]== i) {
//							firstN = true;
//								//System.out.println("good");
//						}
//						else if (potential[i] = true && pairs[3] == i) {
//							secondN = true;
//								//System.out.println("good");
//						}
//					}
//					if (firstN == true && secondN == true) {
//						pairs[1] = x;
////						System.out.println("wooooooooooooooooooooooooooooooooooooooooooooooorked");
////						System.out.println(pairs[0] + " " + pairs[1]);
//					}
//					firstN = false;
//					secondN = false;
			}
					//System.out.println("Those two numbers: " + pairs[2] + " " + pairs[3]);
			}
					
//			potentialCounter = 0;
//		}
//			
//			//getting rid of potentials
//			if (pairs[1] != -1) {
//			//System.out.println("****************************");
//				for (int i = 0; i < 9; i++) {
//					//System.out.println("nth cell: " + i);
//					if (board[i][y].getNumber() == 0) {
//						potential = board[i][y].getPotential();
//						
//						if (i != pairs[0] && i != pairs[1]) {
//							potential[pairs[2]] = false; 
//							potential[pairs[3]] = false; 
//							//System.out.println("false: " + number);
//						}
//						board[i][y].setPotential(potential);
//					}
//				}
//			}
//			
//			//resetting
//			for (int i = 0; i < 4; i++) {
//				pairs[i] = 0;
//			}
//			pairs[0] = -1;
//			pairs[1] = -1;
//		}
		
		//////////
		
		int[] sameBox = new int [9];
		int r = 0;
		int c = 0;
		int r1 = 0;
		int c1 = 0;
		
		for (int b = 1; b < 10; b++) {
			sameBox = sameBoxNumbers(b);
			for (int i = 0; i < 9; i++) {
				r = sameBox[i] % 9;
				c = sameBox[i] / 9;
				
				if (board[r][c].numberOfPotentials() == 2) {
					//System.out.println(pairs[0]);
				
					for (int q = i + 1; q < 9; q++) {
						r1 = sameBox[q] % 9;
						c1 = sameBox[q] / 9;
						
						if (board[r1][c1].numberOfPotentials() == 2) {
							if (board[r][c].getFirstPotential() == board[r1][c1].getFirstPotential() && 
								board[r][c].getSecondPotential() == board[r1][c1].getSecondPotential()) {
								
								for (int j = 0; j < 9; j++) {
									if (sameBox[j] % 9 != r && sameBox[j] / 9 != c && sameBox[j] % 9 != r1 && sameBox[j] / 9 != c1) {
										potential = board[sameBox[j] % 9][sameBox[j] / 9].getPotential();
										
										if (potential[board[r][c].getFirstPotential()] == true) {
											board[sameBox[j] % 9][sameBox[j] / 9].cantBe(board[r][c].getFirstPotential());
											changesMade++;
										}
										if (potential[board[r][c].getSecondPotential()] == true) {
											board[sameBox[j] % 9][sameBox[j] / 9].cantBe(board[r][c].getSecondPotential());
											changesMade++;
										}
									}
								}
							}
						}
					}
			}
				
			}
		}
		
		
		return changesMade;

	}
	
	
	///TODO: errorFound
	/*This method scans the board to see if any logical errors have been made.  It can detect this by looking for a cell that no longer has the potential to be 
	 * any number.
	 */
	 public boolean errorFound()
	 {
	        for(int x = 0; x < 9; x++)
	            for(int y = 0; y < 9; y++)
	                if(board[x][y].numberOfPotentials() == 0) {
	                    return true;
	                }
	        return false;
	        
	  }

	public Cell[][] getBoard() {
		return board;
	}

	public void setBoard(Cell[][] board) {
		this.board = board;
	}
	
	public int[] sameBoxNumbers(int boxID) {
		int [] sameBoxNumbers = new int[9];
		int firstNumber = 0;
		if (boxID < 4) {
			firstNumber = 3 * (boxID - 1);
		}
		else if (boxID < 7) {
			firstNumber = 3 * (boxID - 4) + 27;
		}
		else if (boxID < 10) {
			firstNumber = 3 * (boxID - 7) + 54;
		}
		
		sameBoxNumbers[0] = firstNumber;
		sameBoxNumbers[1] = firstNumber + 1;
		sameBoxNumbers[2] = firstNumber + 2;
		sameBoxNumbers[3] = firstNumber + 9;
		sameBoxNumbers[4] = firstNumber + 10;
		sameBoxNumbers[5] = firstNumber + 11;
		sameBoxNumbers[6] = firstNumber + 18;
		sameBoxNumbers[7] = firstNumber + 19;
		sameBoxNumbers[8] = firstNumber + 20;
		
		return sameBoxNumbers;
	}
	
	
}
