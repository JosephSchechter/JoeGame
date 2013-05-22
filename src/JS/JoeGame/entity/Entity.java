package JS.JoeGame.entity;

import java.util.Random;

import JS.JoeGame.graphics.Screen;
import JS.JoeGame.level.Level;



//template for any entity used in the game
public abstract class Entity {
	
	//entities can move, x and y control the location of the entity on map
	public int x, y;	//redundant if it doesnt have a sprite, but if it does, then you need it
	
	
	private boolean removed = false;	//been removed from the level
	protected Level level;
	protected final Random random = new Random();
	
	//links into the game's update(), 60 updates/sec
	public void update(){
		
	}
	
	public void render(Screen screen){
	}
	
	public void remove(){
		//remove entity from the level
		removed = true;
	}
	
	//checked if removed or not
	public boolean isRemoved(){
		return removed;
	}
	
	//initializes a level
	public void init(Level level){
		this.level = level;
	}
}
