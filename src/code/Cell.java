package code;

public class Cell {
	/*A Cell represents a single square on the Sudoku Game Board. 
	 * It knows it's number - 0 means it is not solved.
	 * It knows the potential numbers that it could have from 1-9.
	 * The Sudoku game board is sub-divided into 9 smaller 3x3 sections that I will call a box. 
	 * These boxes will be numbered from left to right, top to bottom, from 1 to 9.  Each cell
	 * will know which box it belongs in.
	 */
	
	private int number; // This is the solved value of the cell.
	private boolean[] potential = {false, true, true, true, true, true, true, true, true, true};
	/*This array represents the potential of the cell to be each of the given index numbers.  Index [0] is not used since
	 * the cell cannot be solved for 0. 
	 */
	private int boxID;//The boxID is the box to which the cell belongs.
	
	//USEFUL METHODS:
	
	///TODO: canBe 
	//This method returns TRUE or False depending on whether the cell has the potential to be number
	public boolean canBe(int number)
	{
		if (potential[number] == true)
			return true;
		else
			return false;
	}
	
	///TODO: cantBe
	//This sets the potential array to be false for a given number
	public void cantBe(int number)
	{
		potential[number] = false;
	}
	
	///TODO: numberOfPotentials
	//This method returns a count of the number of potential numbers that the cell could be.
	public int numberOfPotentials()
	{
		int counter = 0;
		for (int x = 1; x < 10; x++) {
			if (potential[x] == true)
				counter++;
		}
		return counter;
	}
	
	///TODO: getFirstPotential
	//This method will return the first number that a cell can possibly be.
	public int getFirstPotential()
	{
		int number = 0;
		for (int x = 1; x < 10; x++) {
			if (potential[x] == true) {
				number = x;
				break;
			}
		}
		return number;
	}
	
	public int getSecondPotential()
	{
		int number = 0;
		int counter = 0;
		for (int x = 1; x < 10; x++) {
			if (potential[x] == true) {
				counter++;
				if (counter == 2) {
					number = x;
					break;
				}
			}
		}
		//System.out.println("second potential: " + number);
		return number;
	}
	
	
	//GETTERS AND SETTERS
	public int getNumber() {
		return number;
	}
	
	///TODO: setNumber
	// This method sets the number for the cell but also sets all of the potentials for the cell (except for the solved number)
	//		to be false
	public void setNumber(int number) {
		this.number = number;
		for (int x = 0; x < 10; x++) {
			if (x != number) {
				potential[x] = false;
			}
		}
		
	}
	
	
	
	public void reset() {
		for (int x = 1; x < 10; x++) {
			potential[x] = true;
		}
	}
	public boolean[] getPotentialArray() {
		return potential;
	}
	
	public void turnOffPotential(int number) {
		potential[number] = false;
	}
	
	public void turnOnPotential(int number) {
		potential[number] = true;
	}
	
	public boolean[] getPotential() {
		return potential;
	}
	
	public void setPotential(boolean[] potential) {
		this.potential = potential;
	}
	public int getBoxID() {
		return boxID;
	}
	public void setBoxID(int boxID) {
		this.boxID = boxID;
	}

}