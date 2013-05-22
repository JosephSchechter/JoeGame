package JS.JoeGame.level;

import JS.JoeGame.graphics.Screen;
import JS.JoeGame.level.tile.Tile;

//random level generation OR from image file/data of tiles
public class Level {

//		protected Tile[] tiles;	//array of tiles, very precise, slower, not used
		protected int width, height;
		protected int[] tilesInt;	//data of which tile goes where, which index we draw specific tile
		//tiles[0] might be grass, 1 might be wood, 2 stone, 3 water
		
		//all the level's tiles (color values of the tiles in the pixel map)
		protected int[] tiles;
		
		
		public static Level spawn = new SpawnLevel("/levels/spawn.png");
		
		
		
		//constructor for Random level given the map size in tiles (width, height of tiles)
		public Level(int width, int height){
			this.width = width;
			this.height = height;
		//	tiles = new Tile[width* height];
			tilesInt = new int[width * height];	//tile for every square unit,1 integer for every tile on map, each int is what kind of tile it is
			generateLevel();
		}

		//constructor for a premade level 
		public Level(String path){
			loadLevel(path);		
			generateLevel();
		}
		
		
		protected void loadLevel(String path) {
		}

		protected void generateLevel() {
			
		}
		
		
		//updates level, like for AI, entities in level position, happens at 60 updates per second
		public void update(){
			
		}
		
		//x,y where map is at, screen class is what draws onto monitor
		//xscroll, yscroll is where the player is
		public void render(int xScroll, int yScroll, Screen screen){
			
			screen.setOffset(xScroll, yScroll);	//setting the offset to the location of the player, easy!	
			
			//x0,1,y0,1 define render region of screen
			//renders on screen top left to bottom right, tell comp which tiles need to be rendered using corner pins
			//corner pin = coords of each of the corners
			//why >> 4, divide by 16, 16 = size of tiles, splits it up into individual tiles, players arent snapped to tiles, they are pixel precision, but rendering tiles is by tile precision
			int x0 = xScroll >> 4;	//vertical asymptote on the leftmost side of screen, where we start rendering x
//			int x1 = (xScroll + screen.width) >> 4;	//x1 is rightmost side of screen, players xposition + the width of the screen
			int x1 = (xScroll + screen.width + 16) >> 4;	//add on the size of 1 tile to remove black bars
			int y0 = yScroll >> 4;
//			int y1 = (yScroll + screen.height) >> 4;
			int y1 = (yScroll + screen.height + 16) >> 4;
			//now its in tile precision
			
			
			//needs to go back to pixel precision in getTile
			//render only between the corners of the screen
			//x,y are the tile position of the rendering (16 pixels)	
			for(int y = y0; y < y1; y++){
				for(int x = x0; x < x1; x++){
					
					//render tile onto screen at pos x, y onto the screen
					getTile(x, y).render(x, y, screen);	
					
					
					
	/*
	 * loading a map from an image of pixels, slow but it works
	 * 
	 * 	
					//renders the tiles onto the screen only if they are in the screen's view
					if((x +  y * 16) < 0 || (x + y * 16) >= 16*16){
						Tile.voidTile.render(x, y, screen);
						continue;
					}
					tiles[x + y*16].render(x, y, screen);
	*/
					
				}
			}
		}
		
		
		/*
		 * Color codes
		 * Grass = 0xFF00FF00 (extra ff's in front is alpha channel)
		 * Flower = 0xFFFFFF00
		 * Rock = 0xFF7F7F00
		 * 
		 */
		//retrieves the tile we need to render, x,y = position of the tile (abs position)
		//more of a random way to do it
		//converts an array of integers into tiles
		public Tile getTile(int x, int y){
			
			//array index cant be negative/out of bounds, return void tile
			if(x < 0 || y < 0 || x >= width || y >= height) return Tile.voidTile;
			
			//check if the pixel is marked for a specific tile, if it is, return the Tile it wants
			if(tiles[x + y*width] == Tile.col_spawn_floor) return Tile.spawn_floor;
			else if(tiles[x + y*width] == Tile.col_spawn_grass) return Tile.spawn_grass; 
			else if(tiles[x + y*width] == Tile.col_spawn_hedge) return Tile.spawn_hedge;
			else if(tiles[x + y*width] == Tile.col_spawn_water) return Tile.spawn_water; 
			else if(tiles[x + y*width] == Tile.col_spawn_wall1) return Tile.spawn_wall1; 
			else if(tiles[x + y*width] == Tile.col_spawn_wall2) return Tile.spawn_wall2; 
			else if(tiles[x + y*width] == Tile.col_spawn_floor) return Tile.spawn_floor; 
			else{//if all else fails, return a voidTile
				return Tile.voidTile;
			}
		}
		
		
		//manages time (time of day or something)
		private void time(){
			
		}
	
	
}
