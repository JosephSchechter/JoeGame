package JS.JoeGame.level;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import JS.JoeGame.level.tile.Tile;

//safehouse/nexus area
public class SpawnLevel extends Level{
	
	//private int[] tiles; //moved to Level.java

	//will make spawn level from a specific file on the path
	//constructor
	public SpawnLevel(String path) {
		super(path);
	}
	
	
	//loads the level from a file, given a path
	protected void loadLevel(String path){
		try{
			//reads the map from an image of the map
			BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
			//double assignment
			int w = width = image.getWidth();		//width/height of the image in pixels is the same width/height of the map in tiles
			int h = height = image.getHeight();
			
			//1 pixel can be converted to a tile (0xffffff different tile choices), 1 color to a tile choice
			//tiles = new Tile[w * h];	//no longer used
			tiles = new int[w * h];
			
	//		image.getRGB(startX, startY, w, h, rgbArray, offset, scansize)
			//converts image into array of pixels, to differentiate between tiles
			image.getRGB(0, 0, w, h, tiles, 0, w);
		} catch(IOException e) {
			e.printStackTrace();
			System.out.println("EXCEPTION! could not load level file");
		}
	}
	
	//takes the pixel data and converts the pixels[] into tiles[]
	/*
	 * Color codes
	 * Grass = 0xFF00FF00 (extra ff's in front is alpha channel)
	 * Flower = 0xFFFFFF00
	 * Rock = 0xFF7F7F00
	 * 
	 */
	protected void generateLevel(){
		//System.out.println("Tiles: " + tiles[0]);
		
		
/*
 * used when we were using tile by pixel generation
 * 
		//which pixel we want to be which tile
		for(int i = 0; i < levelPixels.length; i++){
			if(levelPixels[i] == 0xFF00FF00) tiles[i] = Tile.grass;
			else if(levelPixels[i] == 0xFFFFFF00) tiles[i] = Tile.flower;
			else if(levelPixels[i] == 0xFF7F7F00) tiles[i] = Tile.rock;
			else tiles[i] = Tile.voidTile;	//worst case color not found
		}
*/
		
	}


}
