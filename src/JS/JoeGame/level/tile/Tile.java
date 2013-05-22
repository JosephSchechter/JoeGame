package JS.JoeGame.level.tile;

import JS.JoeGame.graphics.Screen;
import JS.JoeGame.graphics.Sprite;
import JS.JoeGame.level.tile.spawn_level.SpawnFloorTile;
import JS.JoeGame.level.tile.spawn_level.SpawnGrassTile;
import JS.JoeGame.level.tile.spawn_level.SpawnHedgeTile;
import JS.JoeGame.level.tile.spawn_level.SpawnWallTile;
import JS.JoeGame.level.tile.spawn_level.SpawnWaterTile;

//need to display tile at all times if it is supposed to be on screen
//possible template class
//every tile renders itself, no method in level that actually renders, it calls the tile and the screen class does the rendering
public class Tile {
	//sprite
	public int x, y;	//x,y coords on screen
	public Sprite sprite;
	
	
	//static object of a grass- there is only 1 grass object, wont change
	//GrassTile extends Tile, so can assign Tile to a GrassTile
	public static Tile grass = new GrassTile(Sprite.grass);
	public static Tile flower = new FlowerTile(Sprite.flower);
	public static Tile rock = new RockTile(Sprite.rock);
	
	
	//spawn level
	public static Tile spawn_grass = new SpawnGrassTile(Sprite.spawn_grass);
	public static Tile spawn_hedge = new SpawnHedgeTile(Sprite.spawn_hedge);
	public static Tile spawn_water = new SpawnWaterTile(Sprite.spawn_water);
	public static Tile spawn_wall1 = new SpawnWallTile(Sprite.spawn_wall1);
	public static Tile spawn_wall2 = new SpawnWallTile(Sprite.spawn_wall2);
	public static Tile spawn_floor = new SpawnFloorTile(Sprite.spawn_floor);
	
	//their colors used in mapmaking
	public static final int col_spawn_grass = 0xff00ff00;	//green w alpha channel
	public static final int col_spawn_hedge = 0;	//unused
	public static final int col_spawn_water = 0;	//unused
	public static final int col_spawn_wall1 = 0xff515a53;
	public static final int col_spawn_wall2 = 0xff000000;
	public static final int col_spawn_floor = 0xffba9649;
	
	public static final int col_spawn_location = 0xff00ffff;	//blue, spawn location
	
	
	
	//a void tile used in case of errors
	public static Tile voidTile = new VoidTile(Sprite.voidSprite);
	
	//constructor given a sprite
	public Tile(Sprite sprite){
		this.sprite = sprite;
	}
	
	//position and the screen (abstract method)
	public void render(int x, int y, Screen screen){
	}
	
	//can objects/mobs pass through it, or is it a wall-like
	//dont need to override for: GrassTile
	public boolean solid(){
		return false;	//by default returns false, subclasses may be solid, but defualt they are not solid
	}

}
