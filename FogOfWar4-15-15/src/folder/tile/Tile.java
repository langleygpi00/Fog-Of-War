package folder.tile;

import folder.Screen;
import folder.Sprite;


//this will be the superclass for types of tiles such as rock, grass, etc.
//each tile needs to have a position, sprite (allows it to be visible), solid?
public class Tile {
	
	public int x, y;
	public Sprite sprite;

	public static Tile grass_vis = new GrassTile(Sprite.grass_vis); //notice: Tile is set equal to GrassTile which is its subclass...understand?
	public static Tile rock_vis = new RockTile(Sprite.rock_vis);
	public static Tile flower_vis = new FlowerTile(Sprite.flower_vis);
	public static Tile bush_vis = new BushTile(Sprite.bush_vis);
	public static Tile grain_vis = new GrainTile(Sprite.grain_vis);
	public static Tile wall_vis = new WallTile(Sprite.wall_vis);

	public static Tile grass_fog = new GrassTile(Sprite.grass_fog); //notice: Tile is set equal to GrassTile which is its subclass...understand?
	public static Tile rock_fog = new RockTile(Sprite.rock_fog);
	public static Tile flower_fog = new FlowerTile(Sprite.flower_fog);
	public static Tile bush_fog = new BushTile(Sprite.bush_fog);
	public static Tile grain_fog = new GrainTile(Sprite.grain_fog);
	public static Tile wall_fog = new WallTile(Sprite.wall_fog);
	
	
	public static Tile voidTile = new VoidTile(Sprite.voidSprite);
	
	//keeps track of the color on the pixel-size map that refers to grass. There is one for each tile. See getTile method in Level class.
	public static int col_grass = 0xff4CFF00;
	public static int col_rock = 0xff404040;
	public static int col_flower = 0xffFFD800;
	public static int col_bush = 0xff267F00;
	public static int col_grain = 0xff7F6A00;
	public static int col_wall = 0xff000000;
	
	public Tile (Sprite sprite) { //constructor
		this.sprite = sprite;
	}
	
	public void render (int x, int y, Screen screen) {
	}

	public boolean solid() { //makes the tile solid by default. must be overriden in subclass to make it collideable
		return false;
	}
	
	public boolean breakable() { // makes the tile unbreakable, but subclasses can overrirde 
		return false;
	}
}
