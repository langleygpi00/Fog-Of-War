package folder;

public abstract class Projectile extends Entity{ //this is a superclass for projectiles
	
	protected final double xOrigin, yOrigin; //final variable.
	protected double angle;
	protected double x, y; //overrides the int variables in Entity, because we need the precision of double variables to create accurate firing angles.
	protected Sprite sprite;
	protected double nx, ny; //stands for newx, newy. Changes after each update as projectile moves
	protected double speed, range, damage;
	
	
	public Projectile(double x, double y, double dir) {
		xOrigin = x;
		yOrigin = y;
		angle = dir;
		this.x = x; //dont forget this. This makes sure the x and y from the entity superclass are actually correct.
		this.y = y;
	}
	
	protected void move() {
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public int getSpriteSize() {
		return sprite.SIZE;
	}
}
