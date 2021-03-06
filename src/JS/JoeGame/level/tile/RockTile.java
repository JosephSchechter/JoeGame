package JS.JoeGame.level.tile;

import JS.JoeGame.graphics.Screen;
import JS.JoeGame.graphics.Sprite;

public class RockTile extends Tile {

	public RockTile(Sprite sprite) {
		super(sprite);
	}
	
	//position and the screen
	public void render(int x, int y, Screen screen){
		//will need to perform operations so the x, y are turned into tile pos, not pixel pos
	//	screen.renderTile(x, y, this);	//renderTile method gets x, y and GrassTile type
		
		//the x and y as they are, are tile precision integers, need to convert them back to pixel precision by * 16
		screen.renderTile(x << 4, y << 4, this);	//<<4 == * 16
	}
	
	
	//can objects/mobs pass through it, or is it a wall-like - rocks are walllike
	public boolean solid(){
		return true;	//by default returns false, subclasses may be solid, but defualt they are not solid
	}


}
