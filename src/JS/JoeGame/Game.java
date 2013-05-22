//finished ep 7
package JS.JoeGame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import JS.JoeGame.entity.mob.Player;
import JS.JoeGame.graphics.Screen;
import JS.JoeGame.input.Keyboard;
import JS.JoeGame.input.Mouse;
import JS.JoeGame.level.Level;
import JS.JoeGame.level.RandomLevel;
import JS.JoeGame.level.SpawnLevel;
import JS.JoeGame.level.TileCoordinate;



//runnable, allows us to use "this" when calling start(this), allows us to use this instance of the game
//canvas = blank rectangle area where we can draw/process inputs, manipulate
//game inherits canvas's methods/ is a subclass of canvas
public class Game extends Canvas implements Runnable {
	
	//since it is a serializable class, need serial version, java convention
	private static final long serialVersionUID = 1L;
	//size of the game (window/screen res)
	public static int width = 300;
	public static int height = (width/16) * 9;	//16x9 aspect ratio, (162 for me, 168 for cherno)
	public static int scale = 3;	//how much game gets scaled up to (retro style)
	
	public static String title = "ShanQuest";
	
	
	private Thread gameThread;	//handles the game, itself
	
	private Screen screen;	//screen object
	private Keyboard key;
	private JFrame frame;	//window, pretty much
	
	
	private Level level;	//only have 1 level up at a time
	private Player player;	//the player
	private boolean running = false;	//is game loop/program running or not
	
	
	//image with an accessable buffer, size = width x height, type = RGB int, (no alpha through here)
	private BufferedImage image =  new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB); //main image object (our final, rendered view in game)
	
	//convert image into array of integers, raster = data struct of rectangle grid of pixels that have a color, retrieve writable raster
	//cast as Int data buffer
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData(); //allows us to write to the buffered image
	//contacts image, gets array of pixels that make up the image, get the data buffer that handles the raster
	//uplaods pixel data to the buffer to be displayed on screen
	
	
	
	
	//constructor, when game is created
	public Game(){
		Dimension size = new Dimension(width*scale, height*scale);
		setPreferredSize(size);	//preferred size of our window (canvas/component method)
		
		screen = new Screen(width, height);	//new screen object given the unscaled width/height 
		frame = new JFrame();
		
		
		key = new Keyboard();
		addKeyListener(key);	//need keylistener to get key input, is a component method
		
		
		Mouse mouse = new Mouse();	
		addMouseListener(mouse);	//buttons
		addMouseMotionListener(mouse);	//location
		
		//level = new RandomLevel(64,64); //64x64 tiles
		level = Level.spawn;
		
		TileCoordinate playerSpawn = new TileCoordinate(19, 62);
		player = new Player(playerSpawn.x(), playerSpawn.y(), key);	//loads player, player will use they kb key
		player.init(level);
		
	}
	
	
	
	//starts the thread
	//synchronized prevents thread interferences/memory consistency issues
	public synchronized void start(){
		running = true;
		//thread implements runnable, and this thread will start the run() method
		gameThread = new Thread(this, "Display");	//name is Display
		gameThread.start();
	}
	
	//will be used to stop as an applet
	public synchronized void stop(){
		running = false;
		try {
		gameThread.join();
		} catch(InterruptedException e){	//if it fails, print stacktrace
			e.printStackTrace();
		}
	}
	
	//when you call start(), the gameThread will be made, and it will call the run() method
	public void run(){
		// when run is called in the beginning, it sets the current time
		long lastTime = System.nanoTime();	//time in nanoseconds (very precise)
		
		long timer = System.currentTimeMillis();	//1 second timer, millliseconds, 1000 ms = 1s
		
		
		//required delay between 2 executions
		final double ns = 1000000000.0 / 60.0;	//converting nano to miliseconds (1 bil/how many times we update)
		double delta = 0;	//change in time
		
		int frames = 0;	//how many frames we can render every second
		int updates = 0;	//how many times update gets called every second (game logic updates), should be ~60 always;
		
		requestFocus(); 	//method in component class, makes in focus
				
		while(running){	//game loop that the gameThread runs
	
			long now =  System.nanoTime();	//current time
			
			//what fraction of the delay has been completed
			delta = delta + ((now - lastTime) / ns);	//when the fraction >= 1, it means that delay for 1 execution has been achieved
			
			lastTime = now;	//sets current time to lastTime, so next round can get new current time
			
			while(delta >= 1){	//only 60 times a second
				update(); //tick - all logic, 60fps
				updates++;
				delta--;
			}
			
			
			render();	//displays,  unlimited update
			frames++; 	//every loop add 1 to frames variable
			
			//once per second display the fps
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;	//add 1 second to timer, ticks every second
				//System.out.println(updates + " updates per second, " + frames + " fps");	//display the fps
				frame.setTitle(title  + "	|	" + updates + " updates per second, " + frames + " fps");
				
				updates = 0;
				frames = 0;
			}
			
			//System.out.println("running...." + (now - lastTime));	

		}
	}
	
//	int x = 0, y = 0;	//offsets for render location, adjusted based on what we hit
	public void update(){
		
		//key inputs
		key.update();	//take updates from the keyboard using the key Keyboard class
		
		player.update();	//players location/stats update
		
		
/*	
 * will be replaced with entity movement updates
		if(key.up) y--;	//drags map up
		if(key.down) y++;	//down
		if(key.left) x--;	//left
		if(key.right) x++;	//right
*/

	}
	
	
	
	//what appears on the screen, the main render method
	public void render(){
		//create buffer strategy (pre-calculated rendered image to grab when you need it - your next frames)
		BufferStrategy bs = getBufferStrategy();	//canvas has its own buffer strategy method and data
		//dont want to create bs every time we render, only do it once
		if(bs == null){
			//3 == triple buffering (on screen, next, one after), double is too slow, will have to wait for backbuffer sometimes, 4 wont see much more improvement
			//if buffer strategy doesn't exist, create it (canvas returns null)
			createBufferStrategy(3);	
			return;
		}
		
		screen.clear();		//clears previous screen image
		//horiz poisiton of player on the screen
		int xScroll = player.x - screen.width/2;	//middle of screen
		int yScroll = player.y - screen.height/2;
		
		//renders level with x, y and screen (x and y are player's location)
		level.render(xScroll, yScroll, screen);
		
		//renders the player onto the screen
		player.render(screen);
		
		//x, y are offsets for the renderer
//		screen.render(x, y);	//calls the Screen render method, changed to the level's renderer, which is the tile's renderer, which goes to screen's renderTile

		
		for(int i = 0; i < pixels.length; i++){
			pixels[i] = screen.pixels[i];	//copies the pixel data from the screen class
		}
		
		
		
		//actually applying data to buffer/ apply buffer to graphics object
		Graphics g = bs.getDrawGraphics();	//getDrawGraphics creates link between graphics on the screen and the buffer, allowing you to write graphics to the screen from the bs- a graphics context for the drawing buffer
		//any graphics on screen must be displayed here before g.dispose
		
		/*		
		//sets graphical color to black
		g.setColor(Color.BLACK);	//g.setColor(new Color(R, G, B)) for specific color
		
		//fills rectangle (starting at pt 0,0 topleft corner, size of rect = width x height
		g.fillRect(0,0,getWidth(), getHeight());	//getwidth/height = part of canvas/component class, returns size of window

		*/		
		
		//draws buffered image to the screen, (buffered image, starting location, width/height, image observer)
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

		//displays x, y		keeps track of where player is
		g.setColor(Color.WHITE);
		g.setFont(new Font("Verdana", 0, 50));
//		g.drawString("X: " + player.x + ", Y: " + player.y, 450, 400);
		if(Mouse.getButton() != -1)	g.drawString("button: " + Mouse.getButton() , 80, 80);
		//mouse coords, 64x64 = num tiles
		g.fillRect(Mouse.getX() - 32, Mouse.getY() - 32, 64, 64);
		//g.fillRect(Mouse.getX() - 8, Mouse.getY() - 8, 16, 16);
		
		
		g.dispose();	//disposes of current graphics, releases system resources, will crash game without this
		
		//swap the buffers (called blitting)
		//make next avail buffer for display - release memory, display pixels in the screen
		bs.show();	//next avail buffer visible
		
	}
	
	
	
	
	
	
	//program starts here
	public static void main(String[] args) {
		System.out.println("main function");
		
		//make new game object
		Game game = new Game();
		game.frame.setResizable(false);	//can cause graphical errors if is on
		game.frame.setTitle(Game.title);
		game.frame.add(game);	//add method adds a component into the frame, fills the window with an instance of the game object, class extends canvas, so can be added to a frame
		game.frame.pack();	//sets size of the frame according to component (900xheight*scale), pack sizes frame up to size 
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//hit close X button, will terminate application
		game.frame.setLocationRelativeTo(null); //center of screen
		game.frame.setVisible(true);	//shows window
		
		game.start();	//starts game
		
		return;
	}

}
