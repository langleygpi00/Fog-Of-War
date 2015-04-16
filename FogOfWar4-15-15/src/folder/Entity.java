package folder;

import java.util.Random;

public abstract class Entity { //abstract means it can't be instantiated.
	
	public double x, y; //only used if there is a sprite
	private boolean removed = false; //if it is removed or nah
	protected Level level; //keeps track of what level you are on
	protected final Random random = new Random(); //for AI in the future
	
	
	public void update () { //linked to update method in Game. still 60 ups
	}
	
	public void render (Screen screen) { // we have a screen parameter because these entities CAN move unlike tiles
	}
	
	public void remove() {
		//Remove from level
		removed = true;
	}
	
	public boolean isRemoved() { //returns if the entity is removed or not.
		return removed;
	}
	
	public void init(Level level) { //since we reference the Level class when checking for collisions in Mob, we must initialize Level somewhere.
		this.level = level;
	}
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}

	protected void reset(){ //entities dont really use this. only units!
		
	}
}
