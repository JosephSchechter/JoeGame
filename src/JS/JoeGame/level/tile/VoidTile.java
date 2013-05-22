package JS.JoeGame.level.tile;

import JS.JoeGame.graphics.Screen;
import JS.JoeGame.graphics.Sprite;

//void tiles are used if unexpected errors in tile-calling occur
public class VoidTile extends Tile {

	public VoidTile(Sprite sprite) {
		super(sprite);
	}
	
	
	//position and the screen
	public void render(int x, int y, Screen screen){
		//will need to perform operations so the x, y are turned into tile pos, not pixel pos
	//	screen.renderTile(x, y, this);	//renderTile method gets x, y and GrassTile type
		
		screen.renderTile(x << 4, y << 4, this);	//convert back to pixel precision, *16
		
	}

	
	
}
