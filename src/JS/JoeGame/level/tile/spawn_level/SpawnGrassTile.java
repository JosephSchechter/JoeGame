package JS.JoeGame.level.tile.spawn_level;

import JS.JoeGame.graphics.Screen;
import JS.JoeGame.graphics.Sprite;
import JS.JoeGame.level.tile.Tile;

public class SpawnGrassTile extends Tile{

	public SpawnGrassTile(Sprite sprite) {
		super(sprite);
	}
	
	//position and the screen
	public void render(int x, int y, Screen screen){
		screen.renderTile(x << 4, y << 4, this);	//<<4 == * 16
	}

}
