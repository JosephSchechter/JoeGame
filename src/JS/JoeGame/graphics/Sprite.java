package JS.JoeGame.graphics;

//individual sprite
public class Sprite {

	public final int SIZE;	//size of sprite or tile(1 dimension of the square, monsters may be larger than 1 tile)
	private int x, y;	//coordinates of the sprite on the sheet //location of target sprite in spritesheet
	public int[] pixels;
	
	//which spritesheet this is part of
	private SpriteSheet sheet;
	
	//once you define a sprite, it will stay the same
	//new static instance of sprite class, each sprite has unique variables, so sprite.grass's pixels are different than other sprites
	public static Sprite grass = new Sprite(16, 0, 5, SpriteSheet.tiles);	//16x16 that starts at 0,4 on the sheet
	public static Sprite flower = new Sprite(16, 1, 0, SpriteSheet.tiles);
	public static Sprite rock = new Sprite(16, 1, 1, SpriteSheet.tiles);
	//for the voidTile, use a voidsprite a solid pink 16x16 square
	public static Sprite voidSprite = new Sprite(16, 0xFF00FF);	//0xffffff= white

	
	//spawn level sprites:
	public static Sprite spawn_grass = new Sprite(16, 0, 0, SpriteSheet.spawn_level);
	public static Sprite spawn_hedge = new Sprite(16, 1, 0, SpriteSheet.spawn_level);	
	public static Sprite spawn_water = new Sprite(16, 2, 0, SpriteSheet.spawn_level);	
	public static Sprite spawn_wall1 = new Sprite(16, 0, 1, SpriteSheet.spawn_level);	
	public static Sprite spawn_wall2 = new Sprite(16, 0, 2, SpriteSheet.spawn_level);	
	public static Sprite spawn_floor = new Sprite(16, 1, 1, SpriteSheet.spawn_level);	
	
	
	
	
	
	//Player Sprites:
/*	changed to 32x32 below
	//4 16x16 sections of the sprite of the player
	public static Sprite player0 = new Sprite(16, 0, 10, SpriteSheet.tiles);	//player sprite starts on sprite sheet on (0, 10) coord
	public static Sprite player1 = new Sprite(16, 1, 10, SpriteSheet.tiles);
	public static Sprite player2 = new Sprite(16, 0, 11, SpriteSheet.tiles);
	public static Sprite player3 = new Sprite(16, 1, 11, SpriteSheet.tiles);
*/	
	
	//the 4 directions for the player
	public static Sprite player_up = new Sprite(32, 0, 5, SpriteSheet.tiles);	//this is a 32x32 version of the player sprite, so the x,y coords are now in 32's not 16
	public static Sprite player_down = new Sprite(32, 2, 5, SpriteSheet.tiles);
//	public static Sprite player_left = new Sprite(32, 3, 5, SpriteSheet.tiles);
	public static Sprite player_side = new Sprite(32, 1, 5, SpriteSheet.tiles);
	
	//rest of sprites for animating the player
	public static Sprite player_up_1 = new Sprite(32, 0, 6, SpriteSheet.tiles);
	public static Sprite player_up_2 = new Sprite(32, 0, 7, SpriteSheet.tiles);
	
	public static Sprite player_side_1 = new Sprite(32, 1, 6, SpriteSheet.tiles);
	public static Sprite player_side_2 = new Sprite(32, 1, 7, SpriteSheet.tiles);
	
	public static Sprite player_down_1 = new Sprite(32, 2, 6, SpriteSheet.tiles);
	public static Sprite player_down_2 = new Sprite(32, 2, 7, SpriteSheet.tiles);
	
	
	
	//constructor for voidSprite/ solid color sprite
	public Sprite(int size, int color){
		SIZE = size;
		pixels = new int[SIZE*SIZE];
		setColor(color);
	}
	
	//fills pixel aray to a single color
	public void setColor(int color){
		for(int i =0; i< SIZE * SIZE; i++){
			pixels[i] = color;
		}
	}
	
	//constructor
	public Sprite(int size, int x, int y, SpriteSheet sheet){
		SIZE = size;	//SIZE == this.SIZE
		
		pixels = new int[SIZE * SIZE];	//pixel array of the sprite
		
		this.x = x*SIZE;	//(5th sprite across, 2nd sprite down == (5,2))
		this.y = y*SIZE;
		this.sheet = sheet;
		
		load();		
	}
	
	//access the spritesheets pixels and find the right sprite
	private void load(){
		for(int y = 0; y < SIZE; y++){
			for (int x = 0; x < SIZE; x++){
				//cycle through each of the pixels, set them to appropriate pixel in ssheet
				//extracts single sprite from sprite sheet
				pixels[x + (y*SIZE)] = sheet.pixels[(x + this.x) + (y + this.y)*sheet.SIZE];
			}
		}
	}
	
}
