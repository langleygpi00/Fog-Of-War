package folder;

public class Unit extends Mob{

	private int time = 0;
	private double xa = 0;  //xa and ya are the incrememnts that the unit uses to move each update.
	private double ya = 0;
	private double xD, yD; //this is where the unit wants to go.
	private double xs, ys;
	
	private boolean selected;
	private double range; //range of unit
	private double speed;
	private int fireRate = 0;
	
	private boolean moving;
	private boolean shooting;
	
	
	public Unit (int x, int y) {
		this.x = x << 4; //converts to pixel coordinates by multiplying by 16
		this.y = y << 4;
		speed = .8;
		sprite = Sprite.unit; //arbitrary
		moving = false;
		shooting = false;
		fireRate = LaserProjectile.FIRE_RATE;
	}
	
	public void act(double x, double y, boolean shooting, boolean moving){
		this.shooting = shooting;
		this.moving = moving;
		int dist = (int) Math.sqrt((x - this.x) * (x - this.x) + (y - this.y) * (y - this.y)); //casting makes it inaccurate.
		
		xD = x;
		yD = y;
		
		//-8 offsets the position which accounts for the fact that this.x is defined as the top left corner of a tile.
		xs = xD - this.x - 8; //xd and yd are two sides of a triangle that we use to calculate the projection angle for a unit.
		ys = yD - this.y - 8; // we calculate xd and yd by finding the units desired location minus initial location
		dir = Math.atan2(ys, xs); //atan2 is a method that returns the arctan. Don't worry about dividing by zero. it deals with it.
		
		xa = Math.round(speed * Math.cos(dir) * 100.0) / 100.0; //rounds speed * direction to two decimal places.
		ya = Math.round(speed * Math.sin(dir) * 100.0) / 100.0;
		
	}
	
	
	public void update() {
		
		if (true == moving){
			if (stop()){
				moving = false;
			}else move(xa, ya); //YOU MUST ROUND TO A CERTAIN NUMBER OF DECIMAL PLACES IN ORDER TO REDUCE CHOPPINESS OF MOVEMENT
		}
		
		if (fireRate > 0) fireRate--; 
		if (shooting == true)
			updateShooting();
		
	}

	public void render(Screen screen) {
		screen.renderMob((int) x, (int) y, sprite);
		clear(); //clears "removed" projectiles
	}
	
	private void updateShooting() {
		if(fireRate <= 0) { // if you shoot, shoot in the direction of mouse. Also, firerate must be zero
			shoot(x, y, dir);
			fireRate = LaserProjectile.FIRE_RATE;
		}
	}
	
	public boolean stop() {
		boolean collide = false;
		for (int c = 0; c < 4; c++) { //this checks all 4 corners for collision
			double xt = ((x + 8) - c % 2 * 1 + 2); // i honestly don't know hoW this works. It just does. Episode 65.
			double yt = ((y + 8) - c / 2  * 1 + 2);
			
			int ix = (int) xt; //returns the smallest integer greater than or equal to a number
			int iy = (int) yt;
			
			if (ix == xD) collide = true; //this is the important statement for collision detection. It works by looking at the tile you want to go to and seeing if it is solid.
			if (iy == yD) collide = true;
		}

		return collide;
	}
	
	
	public void select(boolean select){ //deselects or selects a unit. After each turn it must be deselected.
		selected = select; 
	}
	public boolean getSelected(){
		return selected;
	}
	
	public void setMoving () {
		moving = true;
	}

	public void setShooting () {
		moving = true;
	}
	
	protected void reset() {
		shooting = false;
		moving = false;
	}
}
