package folder;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable{
	private static final long serialVersionUID = 1L;
	
		//changing width greatly affects FPS! 300 is optimal
	private static int width = 400; // IF YOU WANT TO ACCESS WIDTH, HEIGHT OR SCALE. Use getWindowWidth, getWindowHeight, getWindowScale!
	private static int height = width * 9 / 16;
	private static int scale = 3; //modifying the scale variable in order to change zoom does not work.. Maybe adjust the screen class?

	
	private Thread thread; //threads are a new concept. They allow multiple tasks to run simultaneously which is important for games
	private JFrame frame;
	private Keyboard key;
	private Level level; //only need 1 level at a time
	private Player player; //kenny changed to public for interface
	private Inter inter;
	private Inter button1;
	private Inter button2;
	private Inter button3;
	private Inter button4;
	private Boolean running = false;
	
	private Screen screen;
	
	private BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB); //BufferedImage class very useful
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData(); //allows you to convert BufferedImage into an integer array that you can actually modify
	
	
	//levels
		public static Level map1;
		public static Level mappie;
		public static Level map_test;
		
	
	public Game() {
		Dimension size = new Dimension(width*scale, height*scale);
		setPreferredSize(size);
		
		screen = new Screen(width, height); //multiply these by scale in order to zoom?
		frame = new JFrame();
		key = new Keyboard(); //variable that records keystrokes
		addKeyListener(key); //this is important. easy to forget, cuz I didn't actually code for this method. It is from the subclass Component
		
		Mouse mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		
		TileCoordinate playerSpawn = new TileCoordinate(10, 10); //stores the playerspawning position inside a TileCoordinate object. This is useful. The coordinates -2, -2 put the player off the map.
		inter = new Inter(this, key, -16, height - 50, width + 32, 66, 0);
		button1 = new Inter(this, key, 85, height - 42, 50, 15, 1);
		button2 = new Inter(this, key, 150, height - 42, 50, 15, 1);
		button3 = new Inter(this, key, 85, height - 22, 50, 15, 1);
		button4 = new Inter(this, key, 150, height - 22, 50, 15, 1);
		player = new Player(playerSpawn.x(), playerSpawn.y());
		player.setScale(scale);
		
		map1 = new SpawnLevel("/textures/map_1.png", player, screen, key);
		mappie = new SpawnLevel("/textures/mappie.png", player, screen, key);
		map_test = new SpawnLevel("/textures/map_test.png", player, screen, key);
		
		level = map_test;//loads the level file from the folder res. Level.map1 is a Level instantiated in the Level class.
		level.setScale(scale);
		
		player.init(level);
		
		Sound.BACKGROUND.loop(); //plays background music
	}
	
	public static int getWindowWidth() { //watch out!, these values are multiplied by scale
		return width * scale;
	}
	
	public static int getWindowHeight() {
		return height * scale;
	}
	
	
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
		
	}
	
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();                      //exits out of program
		}
	}
	
	public void run() { //this is the code that is constantly running during the game until running==false. It automatically runs cuz its linked to "implements runnable"
		long lastTime = System.nanoTime(); // tells the time at which the game started
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0; // # of nanoseconds in  second divided by frames per second we want (nanoseconds per frame)
		double delta = 0;
		int frames = 0; //measure frames/second
		int updates = 0; //measure updates/second
		
		requestFocus(); //when the window opens, the keys are already focused into it.
		while (running) {
			long now = System.nanoTime(); // this value is different than lastTime, cuz time passes in 4 lines of code
			delta += (now - lastTime) / ns; //adds the time 
			lastTime = now;
			while (delta >= 1) { // whenever delta equals 1, update and subtract 1
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			
			if (System.currentTimeMillis() - timer > 1000) { //forces the inside of the if loop to occur once per second
				timer+= 1000;
				System.out.println(updates + " ups, " + frames + " fps");
				frame.setTitle("Rain     ||      " + updates + " ups, " + frames + " fps");
				
				updates = 0;
				frames = 0;
			}
		}
	}
	
	public void update(){
		key.update(); // constantly checks if any keys are being pressed
		level.update(); //handles AI, projectiles, Tiles, etc.
		player.update(); //calls update method of player class. basically outsources the keyboard tracking.
		inter.update();
		button1.update();
		button2.update();
		button3.update();
		button4.update();
		
	}
	
	public void render(){
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		screen.clear();
		
		level.render(screen); //by inputing xScroll and yScroll instead of player.x and player.y, we put our screen center on the character. 
		player.render(screen);
		inter.render(screen);
		button1.render(screen);
		button2.render(screen);
		button3.render(screen);
		button4.render(screen);
		
		//test UI. Interestingly enough, rendering in this way results in a 2-300 decrease in fps
		//Sprite sprite = new Sprite (40, height - 10, 0xff000000); //test. Interestingly enough, rendering a sprite in this way results in a 300 decrease in fps
		//screen.renderSprite(10, 10, sprite, false);
		
		for (int i = 0; i < pixels.length ; i++) {  //equates the pixels from screen to the buffered image which is what can display the image 
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null); //this is what actually draws the image.
		
		//this is just some basic output stuff that tells me the program is working.
		g.setColor(Color.WHITE); //next 3 lines draw the font and color of the coordinate location thing.
		g.setFont(new Font("Verdana", 0, 40));
		g.drawString("X: " + player.x + ", Y: " + player.y, 0, 600); //position in terms of the screen
		g.drawString("Time (s): " + level.getTimeS(), 10, 50);
		g.drawString("Turn: " + level.getTurn(), 10, 100);		

		g.setFont(new Font("Comic Sans MS", 0, 28));
		g.drawString("Move", 295, 582);
		g.drawString("Stay", 495, 582);
		g.setFont(new Font("Comic Sans MS", 0, 20));
		g.drawString("Move/Attack", 269, 639);
		g.drawString("Stay/Attack", 467, 639);
		//g.fillRect(Mouse.getX() - 32, Mouse.getY() -32, 64, 64); //draws the stuff on the mouse
		if (Mouse.getButton() != -1) g.drawString("Mousebutton:" + Mouse.getButton(), 80, 80); //shows if the mouse is visible and which button is being pressed..
		
		
		g.dispose();
		bs.show();
	
	}
	
	public static void main (String[] args){
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle("thecherno");
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //stops the game when the Jframe closes
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		
		game.start(); //starts the game, lol
	}
	
	public Screen getScreen() {
		return screen;
	}
	
	public Player getPlayer() {
		return player;
	}


	
}
