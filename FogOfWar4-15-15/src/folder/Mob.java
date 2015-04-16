package folder;



public abstract class Mob extends Entity{
	
	protected Sprite sprite;
	double dir = 0;
	protected boolean moving = false;
	
	
	
	public void move(double xa, double ya) { //xa and ya track the change in position
		
		if ( xa != 0 && ya != 0) { //this says if we are moving in two axis' at once, this separates it into two different commands.
			move(xa, 0);
			move(0, ya);
			return;
		}
		

		
		//this while loop allows us to use collision detection for faster moving units. It checks collision in INCREMENTS. However. Currently it causes too many collision problems with the units.

		while (xa != 0){ 

			if (Math.abs(xa) > 1) { // if the xa is more than onetile... (in negative OR positive direction)
				if (!collision(abs(xa), ya)) { //"abs()" is different than "Math.abs()" !!
					this.x += abs(xa);
				}
				xa -= abs(xa);
			} else {
				if (!collision(abs(xa), ya))
					this.x += xa;	
				xa = 0;
			}
			
		}
			
		while (ya != 0) {
			if (Math.abs(ya) > 1) {
				if (!collision(xa, abs(ya)));{
					this.y += abs(ya);
				}
				ya -= abs(ya);
			} else {
				if (!collision(xa, abs(ya)))
					this.y += ya;
				ya = 0;
			}
		}		
	}
	
	
	private int  abs(double value) { //returns either 1 or -1 based on if a value is positive or not.
		if (value < 0) return -1;
		if (value > 0) return 1;
		else return 0;
	}
	
	public abstract void update();  //abstract means that subclasses must be define this method.
	
	public abstract void render(Screen screen);
	
	protected void shoot (double x, double y, double dir) {
		//dir = dir * 180 / 3.14; //converts direction to double variable
		Projectile p = new LaserProjectile(x - 8, y - 5, dir); //LaserProjectile is generic btw! Eventually we are gonna override this for different units
		level.addProjectile(p); //note: when this method is called, it adds projectile p BUT IT ALSO INITIALIZES LEVEL for the projectile class! "p.init(this)"
	}
	
	protected boolean collision(double xa, double ya) { //returns true if there is a collision
		boolean solid = false;
		
		for (int c = 0; c < 4; c++) { //this checks all 4 corners for collision
			double xt = ((x + xa) - c % 2 * 8 ) / 16; // i honestly don't know hoW this works. It just does. Episode 65.
			double yt = ((y + ya) - c / 2  * 8 - 2) / 16;
			
			int ix = (int) Math.ceil(xt); //returns the smallest integer greater than or equal to a number
			int iy = (int) Math.ceil(yt);
			
			if (level.getTile(ix, iy).solid()) solid = true; //this is the important statement for collision detection. It works by looking at the tile you want to go to and seeing if it is solid.
		}
		
		return solid;
	}
	

	
	protected void clear() { //clears projectiles!

		for (int i= 0; i < level.getProjectiles().size(); i++) { //cycles through all "removed" projectiles and actually removes them.
			Projectile p = level.getProjectiles().get(i);
			if (p.isRemoved()) level.getProjectiles().remove(i);
		}
		
	}	
}
