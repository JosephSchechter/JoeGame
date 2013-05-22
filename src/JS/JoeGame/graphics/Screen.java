package JS.JoeGame.graphics;	//in graphics package, all things dealing with gfx go in .graphics package

import java.util.Random;

import JS.JoeGame.entity.mob.Player;
import JS.JoeGame.level.tile.Tile;

//class that displays the pixels, fills pixels in for us to put on screen
public class Screen {

	public int width;
	public int height;
	
	public int[] pixels;	//pixel data, 1 int for each pixel in game
	
	public final int MAP_SIZE = 64;	//one dimension of the square sized map
	public final int MAP_SIZE_MASK = MAP_SIZE - 1; //useful for bitwise operations
	
	//stores the offset values
	public int xOffset, yOffset;
	
	
	
	
	//map
	//64 tiles wide, 64 tiles high
	public int[] tiles = new int[MAP_SIZE*MAP_SIZE];	//4096 tiles
	private Random random = new Random();	//random number generator
	
	
	
	public Screen(int width, int height){
		this.width = width;
		this.height = height;
		
		//create pixel array
		pixels = new int[width * height];	//300*168 = 50400, 300*162 = 48600
		
		for(int i = 0; i < MAP_SIZE*MAP_SIZE; i++){
			tiles[i] = random.nextInt(0xffffff);	//makes a random color from 000000-ffffff, black to white
		}
		
		
	}
	
	//clears the screen
	public void clear(){
		for(int i = 0; i<pixels.length; i++){
			pixels[i] = 0;	//blacks out pixels
		}
	}
	
	
	//to render a tile, need to think about: where to render tile (offset based system, think about players positon and base offsets off that
	//just renders tiles
	//xp/yp = individual position of the tile, Tile = what kind of tile or sprite we are rendering
	public void renderTile(int xp, int yp, Tile tile){
		//adjusting the yp variable/xp, should be opposite map motion so the player goes in the real direction
		xp -= xOffset;
		yp -= yOffset;
		
		//y,x are the pixels that it is up to rendering
		for(int y = 0; y < tile.sprite.SIZE; y++){	//for the size of the sprite, some might be bigger than 16 px tiles
			
			//absolute tile value = actual location of the tile on the map (xa, ya)
			int ya = y + yp;	//yp, xp changes based on an offset
			
			for(int x = 0; x < tile.sprite.SIZE; x++){
				int xa = x + xp;
				
				//restrict the screen, if tile exits screen, dont show the tile
//				if(xa < 0 || xa >= width || ya < 0 || ya >= height) break;	//this will create black bars on left, right, bottom
				
				//xa < -tile.sprite.SIZE means it will render 1 tile to the left of the 0th
				if(xa < -tile.sprite.SIZE || xa >= width  || ya < 0 || ya >= height) break;
				if(xa < 0) xa = 0;
				//which pixels on screen get rendered	=	//which pixels on the sprite get used for rendering
				pixels[xa + ya * width] = tile.sprite.pixels[x + y * tile.sprite.SIZE];

			}
		}
	}
	
	//rendering mobs, different than tiles different iterations, different size, etc	//0 = noflip, 1 x, 2 y, 3 both
	public void renderPlayer(int xp, int yp, Sprite sprite, int flip){
		xp -= xOffset;
		yp -= yOffset;
		
		//y,x are the pixels that it is up to rendering, doing it by 16x16 chunks, changed to 32x32 whole
		for(int y = 0; y < 32; y++){	
			int ya = y + yp;	//yp, xp changes based on an offset
			int ys = y;
			if(flip == 2 || flip == 3) ys = 31 - y;	//flips upside down
			
			for(int x = 0; x < 32; x++){
				int xa = x + xp;
				int xs = x;
				//xsprite, useful for flipping the right to look left
				if(flip == 1 || flip == 3)	xs = 31 - x;	//flips left/rigth
				
				
				if(xa < -32 || xa >= width  || ya < 0 || ya >= height) break;
				if(xa < 0) xa = 0;
				
				//to not draw the "transparancy color" 0xa92cd2, but also need to add ff for alpha value
				int col = sprite.pixels[xs + ys * 32];
				if(col != 0xffa92cd2){
					pixels[xa + ya * width] = col;
				}
			}
		}
	}
	
	
	
	
	//when its called, set the screen class's x/y offset values
	public void setOffset(int xOffset, int yOffset){
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	

	
}







//these are old render methods

/*	

REWORKED ABOVE, AGAIN, ABOVE WILL START USING TILES


public void render(int xOffset, int yOffset){
	
	


	for(int y = 0; y < height; y++){
		int yp = y + yOffset;	//y pixel
		if(yp < 0 || yp >= height) continue;
		
		for(int x = 0; x < width; x++){
			int xp = x + xOffset;	//x pixel
			if(xp < 0 || xp >= width) continue;
			//displays map in correct position relative to player
			pixels[xp + (yp * width)] = Sprite.grass.pixels[(x & 15) + (y & 15) * Sprite.grass.SIZE];
		}
	}
//	

//REWORKED ABOVE
* 
* 		
	//fill in pixel array
	for (int y = 0; y < height; y++){
		int yy = y + yOffset;	//
		//if pixel goes out of screen/loop, ignore drawing it
	//	if(yy >= height || yy < 0) break;	//breaks out of for loop
		
		
		for (int x= 0; x < width; x++){
			int xx = x + xOffset;	//allows us to loop the screen
		//	if(xx >= width || xx < 0) break;
			
			//find the tile that needs to be rendered at particular position (which element it needs to retrieve color data from)
			//int tileIndex = (x / 32) + ((y / 32) * 64);	//64 is map width (1st 32 pixels of x or y are the same color since same tile)//tilesize = 32x32 pixels	
			//int tileIndex = (x / 16) + ((y / 16) * 64);	// changed size to 16x16
//dont need anymore				int tileIndex = ((xx >> 4) & MAP_SIZE_MASK) + (((yy >> 4) & MAP_SIZE_MASK) * MAP_SIZE);	//bitwise optimization, x / 16 is the same as x shifted right 4 places (2^4 = 16)
			// /\ bitwise and,when xx/16 > 63, returns back to 0, aka tile[64] -> tile[0]
			
			//for tile by tile movement add the offest after you divide/bitshift
//			int tileIndex = ((x >> 4) + xOffset & MAP_SIZE_MASK) + (((yy >> 4) & MAP_SIZE_MASK) * MAP_SIZE);
			
			//displaying colors
//			pixels[x + (y * width)] = tiles[tileIndex];
			
		
			
			// (x, y), (horiz, vertical), (20, 30) -> 20 + 30*width
		//	pixels[20 + (30 * width)] = 0x000aaa;	//makes pixel (20x,30y) color blue
		//	pixels[x + (y * width)] = 0xff00ff;		//0xff00ff = pink
		//	pixels[x + (y * width)] = 0xff00ff; //animated pink line going down
		 * 
		
			
			
			
			//showing grass, loops at 15 pixels, size of grass
			pixels[x + (y * width)] = Sprite.grass.pixels[(x & 15) + (y & 15) * Sprite.grass.SIZE];
			

		}
	}
	

*
*

	
}
*/
