package JS.JoeGame.level.tile;

import JS.JoeGame.graphics.Screen;
import JS.JoeGame.graphics.Sprite;


public class GrassTile extends Tile{

	public GrassTile(Sprite sprite) {	
		super(sprite);	//executes the Tile's constructor method
	}

	//position and the screen
	public void render(int x, int y, Screen screen){
		//will need to perform operations so the x, y are turned into tile pos, not pixel pos
	//	screen.renderTile(x, y, this);	//renderTile method gets x, y and GrassTile type
		
		//the x and y as they are, are tile precision integers, need to convert them back to pixel precision by * 16
		screen.renderTile(x << 4, y << 4, this);	//<<4 == * 16
	}
	
	//dont need solid(), its false
	
	

	

}
