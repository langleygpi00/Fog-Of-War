package folder;


public class Sprite { //this object keeps track of INDIVIDUAL sprites.
	public final int SIZE; //size of the sprite, usually it will be 1 tile by 1 tile, but it could be bigger
	private int x, y; // position of the sprite in the spritesheet
	private int width, height;
	public int[] pixels; //the pixels data of the sprite
	private SpriteSheet sheet; //keeps track of which spritesheet the sprite it is located in
	
	//this block of code is where you initialize block types. They are by default fog tiles.
	public static Sprite grass_fog = new Sprite(16, 4, 0, SpriteSheet.tilesfog); //By creating a sprite in the sprite class, it makes the sprite final and unchangeable afterward.
	public static Sprite rock_fog = new Sprite(16, 1, 0, SpriteSheet.tilesfog);
	public static Sprite flower_fog = new Sprite(16, 2, 0, SpriteSheet.tilesfog);
	public static Sprite bush_fog = new Sprite(16, 0, 0, SpriteSheet.tilesfog);
	public static Sprite grain_fog = new Sprite(16, 3, 0, SpriteSheet.tilesfog);
	public static Sprite wall_fog = new Sprite(16, 14, 0, SpriteSheet.tilesfog);
	public static Sprite voidSprite = new Sprite (16, 0x5D330A); // creates a voidSprite which serves as a default filler tile that has a solid color. in this case, "0" indicates black

	
	// exact copy of the Sprites above but lighter.
	public static Sprite grass_vis = new Sprite(16, 4, 0, SpriteSheet.tiles); //By creating a sprite in the sprite class, it makes the sprite final and unchangeable afterward.
	public static Sprite rock_vis = new Sprite(16, 1, 0, SpriteSheet.tiles);
	public static Sprite flower_vis = new Sprite(16, 2, 0, SpriteSheet.tiles);
	public static Sprite bush_vis = new Sprite(16, 0, 0, SpriteSheet.tiles);
	public static Sprite grain_vis = new Sprite(16, 3, 0, SpriteSheet.tiles);
	public static Sprite wall_vis = new Sprite(16, 14, 0, SpriteSheet.tiles);
	
	
	//unit sprites
	public static Sprite player = new Sprite(32, 0, 7, SpriteSheet.tiles); //creates a 32x32 size sprite. Since the size is twice that of a normal sprite, we count in the tiles in pairs inside the spritesheet. Thus, 
	public static Sprite playerSelecting = new Sprite(32, 1, 7, SpriteSheet.tiles); //creates a 32x32 size sprite. Since the size is twice that of a normal sprite, we count in the tiles in pairs inside the spritesheet. Thus, 
	public static Sprite unit = new Sprite(16, 0, 0, SpriteSheet.units);
	
	//projectile sprites
	public static Sprite projectile_laser = new Sprite(16, 0 , 0, SpriteSheet.projectiles);
	
	public Sprite (int size, int x, int y, SpriteSheet sheet) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int [SIZE * SIZE];
		this.x = x * size; //x and y are the coordinates of the sprite in the sprite sheet IN TERMS OF TILES, NOT PIXELS
		this.y = y * size;
		this.sheet = sheet;
		load();
	}
	
	public Sprite (int width, int height, int colour) { //creates a non-square sprite
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		SIZE = -1; //cuz we wont use it in this case.
		setColour(colour);
	}
	
	
	
// this is an alternate constructor if you want sprites that are just filled with a single color instead of something drawn in a spritesheet.
	public Sprite(int size, int colour) { // note colour instead of color
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		setColour(colour);
	}
	
	private void setColour(int colour) {
		for (int i = 0; i < width * height; i++) {
			pixels[i] = colour;
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	
	private void load() { //loads the sprite from the spritesheet class into computer
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				pixels[x + y * SIZE] = sheet.pixels[(x + this.x) + (y + this.y)*sheet.SIZE]; //the x versus this.x distinction is difficult.
			}
		}
	}
}
