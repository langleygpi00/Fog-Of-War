package folder;

import java.util.Random;

public class RandomLevel extends Level{
	
	private static final Random random = new Random();

	public RandomLevel(int width, int height) {
		super(width, height);
	}
	
	protected void generateLevel() { //since this is protected, this overwrites the code in the class level for the method generateLevel
		for (int y = 0; y < height; y++) { // this fills up the array tiles which contains integers representing the type of tile
			for (int x = 0; x < width; x++) {
				tilesInt[x + y * width] = random.nextInt(5);
			}
		}
	}
	

}
