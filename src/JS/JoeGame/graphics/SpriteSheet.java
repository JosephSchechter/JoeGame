package JS.JoeGame.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

//in charge of any sprite we need, in charge of caching them
public class SpriteSheet {

	private String path;	//system path
	public final int SIZE;	//size of the sprite sheet (1 dimension of the square)
	public int[] pixels;
	
	//only 1 instance that can be accessed throughout every class without instantiating it
	public static SpriteSheet tiles =  new SpriteSheet("/textures/sheets/spritesheet.png", 256);	//in res folder, it is 256x256
	
	public static SpriteSheet spawn_level = new SpriteSheet("/textures/sheets/spawn_level.png", 48);
	
	
	
	//constructor
	public SpriteSheet(String path, int size){
		this.path = path;
		this.SIZE = size;
		pixels = new int[SIZE*SIZE];	//maps every pixel of the sprite sheet
		load();		//sets pixels to the sprite
	}
	
	//loads physical image file
	private void load(){
		//sets image equal to the image at the path
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();//height/width of image loaded
			
			//want to deal with image as pixels, not as bufferedimage
		//	image.getRGB(startX, startY, w, h, rgbArray, offset, scansize) scansize is horizontal dimension, array is where we upt it, offset is where in image we start
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}

}
