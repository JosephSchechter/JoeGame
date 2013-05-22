package JS.JoeGame.entity.mob;

import JS.JoeGame.graphics.Screen;
import JS.JoeGame.graphics.Sprite;
import JS.JoeGame.input.Keyboard;

public class Player extends Mob{
	
	private Keyboard input;
	private Sprite sprite;
	private int anim = 0;	//animation
	private boolean walking = false;
	
	
	//constructor with location (specific spawn point) and the input 
	public Player(int x, int y, Keyboard input){
		this.input = input;
		this.x = x;
		this.y = y;
		sprite = Sprite.player_down;
	}
	
	//constructor at a default location with input to use
	public Player(Keyboard input){
		this.input = input;
		sprite = Sprite.player_down;
	}
	
	//whenever we move, etc, overrides mob and entity
	public void update(){
		//note the direction the player is supposed to move
		int xa = 0, ya = 0;
		
		
		if(anim < 7500){
			anim++;
		}
		else anim = 0;
		
		
		//if any dir is pressed, change y or x direction
		if(input.up) ya--;
		if(input.down) ya++;
		if(input.left) xa--;
		if(input.right) xa++;
		
		//it can either be -1 or +1, then feed it into move method as long as something has changed
		if(xa != 0 || ya != 0){
			move(xa, ya);
			walking = true;
		}
		else{
			walking = false;
		}
		
/*
 *	one method of movement
 * 
		//if you press the input direction, you change the entity's x or y position, good for keeping track of all entity's positions
		if(input.up) y--;
		if(input.down) y++;
		if(input.left) x--;
		if(input.right) x++; 
*/
	}
	
	//overrides mob, draws the player 
	public void render(Screen screen){
/*		
		//these variables are used to center the player around its 4 quarters
		int xx = x - 16;
		int yy = y - 16;
		screen.renderPlayer(xx, yy, Sprite.player0);
		screen.renderPlayer(xx+16, yy, Sprite.player1);
		screen.renderPlayer(xx, yy+16, Sprite.player2);
		screen.renderPlayer(xx+16, yy+16, Sprite.player3);
*/
		int flip = 0;
		//the 32x32 player, find the direction (mob has dir)
		if(dir == 0){	//up
			sprite = Sprite.player_up;
			if(walking){	//walk cycle
				if(anim % 30 > 20){
					sprite = Sprite.player_up_1;
				}
				else if(anim % 30 > 10){
					sprite = Sprite.player_up;
				}
				else{
					sprite = Sprite.player_up_2;
				}
			}
		}
		else if(dir == 1){	//right
			sprite = Sprite.player_side;
			if(walking){
				if(anim % 30 > 20){
					sprite = Sprite.player_side_1;
				}
				else if(anim % 30 > 10){
					sprite = Sprite.player_side;
				}
				else{
					sprite = Sprite.player_side_2;
				}
			}
		}
		else if(dir == 2){	//down
			sprite = Sprite.player_down;
			if(walking){
				if(anim % 30 > 20){
					sprite = Sprite.player_down_1;
				}
				else if(anim % 30 > 10){
					sprite = Sprite.player_down;
				}
				else{
					sprite = Sprite.player_down_2;
				}
			}
		}
		else if(dir == 3){	//left
			flip = 1;	//horiz flip
			sprite = Sprite.player_side;
			if(walking){
				if(anim % 30 > 20){
					sprite = Sprite.player_side_1;
				}
				else if(anim % 30 > 10){
					sprite = Sprite.player_side;
				}
				else{
					sprite = Sprite.player_side_2;
				}
			}
		}
		
//		else if(dir == 3) sprite = Sprite.player_left;	//left changed to side and flip
		
		screen.renderPlayer(x - 16, y - 16, sprite, flip);
		
	}
	
}
