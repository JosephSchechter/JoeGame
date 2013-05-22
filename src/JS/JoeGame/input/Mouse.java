package JS.JoeGame.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

//cant extent multipls classes, can implement many
public class Mouse implements MouseListener, MouseMotionListener{

	//static since we only have 1 mouse
	private static int mouseX = -1;
	private static int mouseY = -1;
	private static int mouseB = -1;	//button

	public static int getX(){
		return mouseX;
	}
	public static int getY(){
		return mouseY;
	}
	public static int getButton(){
		return mouseB;
	}
	
	
	
	public void mouseDragged(MouseEvent e) {
	}

	
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	
	public void mouseClicked(MouseEvent e) {
	}

	
	public void mouseEntered(MouseEvent e) {
	}

	
	public void mouseExited(MouseEvent e) {
	}

	
	public void mousePressed(MouseEvent e) {
		mouseB = e.getButton();	
	}

	
	public void mouseReleased(MouseEvent e) {
		mouseB = -1;	
	}
	

}
