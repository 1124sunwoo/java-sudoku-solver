package code;

public class Guess {
	private Cell[][] copy = new Cell [9][9];
	
	public Guess(Board original) {
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				copy[x][y] = new Cell();
				copy[x][y].setBoxID(3*(x/3) + (y/3) + 1);
				
				if (original.getNumber(x, y) != 0)
					copy[x][y].setNumber(original.getNumber(x, y));
				
				boolean[] originalPotentials = original.getPotential(x, y);
				
				//copying the original potential array
				for (int number = 1; number < 10; number++) {
					if (originalPotentials[number] == true) {
						copy[x][y].turnOnPotential(number);
					}
					else
						copy[x][y].turnOffPotential(number);
				}
			}
		}
	}
	public int getNumber(int x, int y) {
		return copy[x][y].getNumber();
	}
	
	public boolean[] getPotentialArray(int x, int y) {
		return copy[x][y].getPotentialArray();
	}
}
