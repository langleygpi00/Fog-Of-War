package folder;

public class LaserProjectile extends Projectile{

	public static final int FIRE_RATE = 15; //5 seems optimal. adjust for burst fireee
	
	public LaserProjectile (double x, double y, double dir) {
		super(x, y, dir);
		range = 120; //arbitrary values
		damage = 20;
		sprite = Sprite.projectile_laser; //sprite.grass is arbitrary
		speed = 1; //4 seems optimal 
		
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}
	
	public void update () {
		if (level.tileCollision(x, y, nx, ny, 4)) //4 is an arbitrary number that refers to the size of the pixel and affects its collision detection
			remove();
			
		move();
	}
	
	public void move() { //overrides move from entity
		x += nx; 
		y += ny;
		
		if (distance() > range) { // remove projectile if its far away
			 remove(); // makes remove true
		}
	}
	
	private double distance () {
		double dist = 0;
		dist = Math.sqrt(Math.abs( (xOrigin - x) * (xOrigin - x) + (yOrigin - y) * (yOrigin -y))); //pythagorean theorem
		return dist;
	}
	
	
	public void render(Screen screen) { //the 8 and 5 will eventually need to be changed.
		screen.renderProjectile((int)x + 8, (int) y + 5, this); //must cast to int from double. doesnt affect the angle precision :D
	}
}
