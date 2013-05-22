package JS.JoeGame.entity.mob;

import JS.JoeGame.entity.Entity;
import JS.JoeGame.graphics.Sprite;

//cant be instantiated since its abstract, but its a template for actual mobs
//mobs are creatures/character/something that is mobile
public abstract class Mob extends Entity{
	
	//only subclasses can access this sprite
	protected Sprite sprite;
	protected int dir = 0;	//direction, 0 = north/up, 1 = East/right, 2 = south/down, 3 = west/left
	
	protected boolean moving = false;	//usually have animation when moving
	
	//when anything moved, x and y need to change
	//xa/ya = which way mob moves on x axis and y axis
	public void move(int xa, int ya){
		//if he is moving in both axis, do for 1 axis each
		if(xa != 0 && ya != 0){
			move(xa, 0);
			move(0, ya);
			return;
		}
		
		
		
		//direction change, where we are moving/facing
		if(xa > 0) dir = 1;	//east
		else if(xa < 0)	dir = 3;	//west
		
		if(ya > 0) dir = 2;	//south
		else if(ya < 0) dir = 0;	//north
		
		
		if(!collision(xa, ya)){ 
			//x = -1, 0, 1 will add 1 pixel to x, moves 1 pixel to left/right/up down
			x += xa;
			y += ya;
		}
		

	}
	
	//location, positioning, etc
	public void update(){
		
	}
	
	public void render(){
		
	}
	
	//collision detected?
	private boolean collision(int xa, int ya){
		//based on what we are moving into(the next tile), if its solid, stop us from moving
		boolean solid = false;
		//four corner of tile precision, if any of the 4 corners belongs to a solid tile, no longer just the top left corner
		for(int c = 0; c < 4; c++){
			//current pos + xa(future position), put it into tile precision by >>4(/16)
			//adjusts the center-point of the tile
			//int the c /% 2 * x +- y, the x and y are the pixels used for precision in collision to make it look nice
			//translated (-8, 3) from middle point, has width of 14 pixels, height of 13 pixels, so the players collision area is in the lower half of the sprite
			int xt = ((x + xa) + c % 2 * 14 - 8)>>4;	//%2 = width
			int yt = ((y + ya) + c / 2 * 13 + 3)>>4;	// /2 for height/y's
			if(level.getTile(xt, yt).solid()) solid = true;
		}
		return solid;
	}
	

}
