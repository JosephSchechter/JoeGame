package JS.JoeGame.level;

import java.util.Random;

//inherits all of Level
public class RandomLevel extends Level{
	
	//wont need multiple random objects
	private static final Random random = new Random();

	public RandomLevel(int width, int height) {
		super(width, height);	//executes all code in Level(width, height) constructor
		
	}
	
	//overrides level.generateLevel() method
	protected void generateLevel() {
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				tilesInt[x + (y * width)] = random.nextInt(4);	//generates 0-3, the different kind of tile id
				//ex: tiles[10 + (8 * width)] = 3 //	(tile 10,8 is water(3))
			}
		}
	}
	
	
}
