package folder;

import java.util.ArrayList;
import java.util.List;

import folder.tile.Tile;


public class Level { //will contain all level subclasses and tiles, and anything related to the level. very big class.
	
	protected int width, height;
	protected int[] tilesInt; // contains integers, each one representing a type of tile (wood, stone, etc.) that relates to its position
	protected int[] tiles; //contains the colors for each tile in the map
	protected Player player;

	protected Mouse mouse;
	protected Screen screen;
	protected Keyboard input; //key variable from Game class
	private int lastKey; //stores the integer value of the last key
	
	private int mouseClickCounter;
	private int turnTime; //turn time * 60
	private int scale;
	
	private int xScroll; 
	private int yScroll;
	
	private int time; //game time, dawg. in seconds
	
	public boolean turn;

	
	private List<Entity> entities = new ArrayList<Entity>(); //contains all the entities in a level. This allows simple rendering and updating for entities to be streamlined into the LEvel class
	private List<Projectile> projectiles = new ArrayList<Projectile>(); //separate arraylist exclusively for projectiles.
	

	
	public Level (int width, int height) { // this is the constructor that you call to generate a RANDOM level. note, currently it will not work without some modifications. See Andrew
		this.width = width;
		this.height = height;
		tilesInt = new int[width * height];
		generateLevel();
	}
	
	public Level (String path, Player player, Screen screen, Keyboard input) { // this second constructor. Its used for generating a map from a .png file
		this.input = input;
		loadLevel(path);
		System.out.println(path);
		generateLevel();
		
		turnTime = 900;
		
		this.player = player;
		this.screen = screen;
		xScroll = 0;
		yScroll = 0;
		
		time = 0;
		turn = true;
	}
	
	protected void generateLevel() {	
	}
	


	protected void loadLevel(String path) {
	}

	public void update() { //updates the level. Anything AI related gets updated. 60 ups still		
		scroll(); //checks if the cursor is off the screen, and scrolls if necessary
		
		if (turn) {
			select(); //selection method made by Andy
		}
		else if (turn == false) {
			for(int i = 0; i < entities.size(); i++){ //updates all entities in arraylist
				entities.get(i).update(); 
			}

		}
		
		for(int i = 0; i < projectiles.size(); i++) { //updates all projectiles
			projectiles.get(i).update();
		}

		time();
	}
	
	
	private void select() { //andy's code
		if(Mouse.getClicked()){
			boolean shooting = false;
			boolean moving = false;
			if(input.lastKey == 83) shooting = true;
			if(input.lastKey == 77) moving = true;
			mouseClickCounter++;
			Mouse.resetClick(); //resets it to zero.
			if(mouseClickCounter > 1){
				mouseClickCounter = 0;
			}
			if(mouseClickCounter == 1){
				
				boolean selected = false;
				for(int i = 0 ; i < entities.size(); i++){
					Entity ent = entities.get(i);
					if(ent instanceof Unit){ //insures that only units are picked
						Unit unit = (Unit)ent;
						//System.out.println("X: " + unit.getX() / 16 + ", Y: " + unit.getY() / 16);
						//System.out.println("PLAYER X: " + (player.x + screen.xOffset + 16) / 16 + ", Y: " + (player.y + screen.yOffset + 16) / 16);
						if( Math.abs((int) unit.getX() - ((int) player.x + (int)screen.xOffset )) < 11 && Math.abs(((int) unit.getY())  - (((int)player.y + (int)screen.yOffset + 1))) < 11)  { // selection code. note the xOffset and yOffset because player.x and player.y are relative to the actual position.
							selected = true;
							unit.select(selected);
							player.switchSprite();
							System.out.println("selected");
							break;
							
						}
					}
				}
				if(!selected){
					mouseClickCounter--;
				}
			}
			
			
			if(mouseClickCounter == 0){
				for(int i = 0 ; i < entities.size(); i++){
					Entity ent = entities.get(i);
					if(ent instanceof Unit){ //insures that only units are picked
						Unit unit = (Unit)ent;
						if(unit.getSelected()){
							unit.setMoving();
							unit.act((player.x + screen.xOffset + 16), (player.y + screen.yOffset + 16), shooting, moving); //moves the player directly to the wanted position. Coordinates are in terms of the entire map (pixel precision)
							player.switchSprite();
							unit.select(false); //resets selection to false.
						}
					}
				}
			}
			
		}
	}
	
	public void time(){
		if (time % 60 == 0) {
			if (time % turnTime == 0 && time != 0){
				turn = !turn; //switches from true to false. "xor statement"
			}
				
		}
		time++;
	}
	
	private void scroll() {
		if(player.x < 0){
			xScroll -= 2;
		}else if(player.x + 32 > screen.width){
			xScroll += 2;
		}
		if(player.y < 0){
			yScroll -= 2;
		}else if(player.y + 32 > screen.height){
			yScroll +=2;
		}
		
		
		screen.setOffset(xScroll, yScroll);
	}
	
	
	public boolean tileCollision (double x, double y, double xa, double ya, int size) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			int xt = (((int) x + (int) xa) + c % 2 * size / 3 + 10) / 16;
			int yt = (((int) y + (int) ya) + c / 2 * size / 2 + 11) / 16;
			
			if (getTile(xt, yt).solid()) solid = true;
		}
		
		return solid;
	}
	
	
	//xscroll and yscroll keep track of the position of the top left corner of the screen... in pixels i believe.
	public void render(Screen screen) {	//x1, x0, y0, y1 are called "corner pins". They represent the coordinates of the 4 corners of the screen.
		//scroll code

		int x0 = xScroll >> 4; // ">>" shifts the value over by 4, making it equivalent to dividing by 16. This is more efficient than using the "/"! 
		int x1 = (xScroll + screen.width + 16) >> 4; // the reason why we are dividing by 16 is to convert the number of pixels to tiles which is a significantly lower number. We are adding sixteen to add 1 tile to the number of tiles being rendered. This gets ride of the black edging problem.
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + 16) >> 4;
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				getTile(x,y).render(x, y, screen); //allows us to have a map out of bounds error
				
			}
		}
		
		
		for(int i = 0; i < entities.size(); i++) { //for each entity, render it!
			entities.get(i).render(screen); 
		}
		
		for(int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(screen);
		}
	}
	
	//adds entity to level
	public void add(Entity e) {
		e.init(this);
		entities.add(e);
	}
	
	public void addProjectile(Projectile p) {
		p.init(this);
		projectiles.add(p);
	}
	
	public List<Projectile> getProjectiles() {
		return projectiles;
	}
	
	//color KEY for pixel-size map. See the variables in the Tile method
	// 0xff7F6A00 = graintile
	// 0xffFFD800 = flowertile
	// 0xff000000 = rocktile
	// 0xff267F00 = bush
	// 0xffFFFF00 = flower
	// THIS method looks at the pixel size map and returns the corresponding tile
	public Tile getTile (int x, int y) { // x and y are in terms of tile position, NOT pixels
		if (x < 0 || x >= width || y < 0 || y >= height) {
			return Tile.voidTile;
		}
		
		if (tiles[x + y * width] == Tile.col_grass) return Tile.grass_vis; // this means a zero indicates a grass tile. 
		if (tiles[x + y * width] == Tile.col_wall) return Tile.wall_vis;
		if (tiles[x + y * width] == Tile.col_flower) return Tile.flower_vis;
		if (tiles[x + y * width] == Tile.col_bush) return Tile.bush_vis;
		if (tiles[x + y * width] == Tile.col_grain) return Tile.grain_vis;
		if (tiles[x + y * width] == Tile.col_rock) return Tile.rock_vis;
		if (tiles[x + y * width] == 0) return Tile.voidTile; //temporary for the vision class		
		
		return Tile.voidTile; //default
	}
	
	public void addUnits(int numunits) { // whatever is inside here is arbitrary
	
		for (int i = 0; i < numunits; i++) {
			add(new Unit(28, 2 * i + 2));
		}
	}
	
	public void setScale(int scale){
		this.scale = scale;
	}
	
	
	public boolean getTurn() {
		return turn;
	}
	
	public int getTimeS() {
		return time / 60;
	}
	
}