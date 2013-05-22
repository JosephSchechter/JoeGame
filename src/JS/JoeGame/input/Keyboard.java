package JS.JoeGame.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//KeyListener is an interface that listens to keystrokes based on a component (canvas)
public class Keyboard implements KeyListener{

	//65536 maximum value of of character array, so there can't be more chars than that
	//120 is fine though, more than 120 keys on kb? probably not
	private boolean[] keys	= new boolean[120];//every key on the kb, pressed/unpressed
	public boolean up, down, left, right;	//if we've pressed a direction
	
	//every cycle checks if key is pressed/released
	public void update(){
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];	//check if up/w has been pressed
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		
		for(int i = 0; i< keys.length; i++){
			if(keys[i]){
				System.out.println("KEY PRESSED: " + i);	//says which key (id) is pressed
			}
		}
		
		
	//	System.out.println(up);
	}
	
	public void keyPressed(KeyEvent e) {
		//when key is pressed, get the key code (id of key pressed) and set it to true
		keys[e.getKeyCode()] = true;
	}


	public void keyReleased(KeyEvent e) {
		//when key is released, get the key code and set it to false
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {
		
	}

	

}
