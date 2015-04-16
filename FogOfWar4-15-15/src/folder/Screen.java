package folder;

import java.util.Random;

import folder.tile.Tile;

public class Screen {
	
	public int width; // dimensions of the SCREEN!
	public int height;
	public int[] pixels;
	public final int MAP_SIZE = 64; //number of tiles
	public final int MAP_SIZE_MASK= MAP_SIZE - 1; //MAP_SIZE-1, for arrays
	public double xOffset, yOffset; //stores offset values that help account for taking into account player movement during rendering
	public int[] tiles = new int[MAP_SIZE * MAP_SIZE]; //64x64 = 4096 tiles on the whole map
	private Random random = new Random();
	
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width*height]; //50,400 pixels
		
		for (int i = 0; i < MAP_SIZE * MAP_SIZE; i++) {
			tiles[i] = random.nextInt(0x79BDE8-0x79BDAA) + 0x79BAA; //assigns a random color to each of the 4096 tiles
		}
	}
	
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

	
	public void renderSprite (int xp, int yp, Sprite sprite, boolean fixed) { //this is a render method that will render any square or rectangular sprite!
		if (fixed) { //differentiates between fixed and unfixed objects!
			xp -= xOffset;
			yp -= yOffset;
		}
		
		for (int y = 0; y < sprite.getHeight(); y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp;
				
				if (xa < 0 || xa >= width || ya < 0 || ya > height) continue;
				
				System.out.println(xa);
				pixels[xa + ya * width] = sprite.pixels[x + y * sprite.getWidth()];
			}
		}
		
	}
	
	public void renderTile(int xp, int yp, Tile tile) { //since the constructor is Tile but not Sprite, we are able to change the type of sprite it is. i.e. grass to rock
		xp -= xOffset; //subtracting the Offset accounts for player movement. This also somehow reverses the inversion of the controls. Understand why we are subtracting as opposed to adding.
		yp -= yOffset;
		for (int y = 0; y < tile.sprite.SIZE; y++) {
			int ya = y + yp; // ya refers to absolute position. y is the rendering thing moving from left to right. yp is the offset. The reason for needing an absolute position is that this method only renders a single tile
			for (int x = 0; x < tile.sprite.SIZE; x++) {
				int xa = x + xp;
				if (xa < -tile.sprite.SIZE || xa >= width || ya < 0 || ya >= height) break; //width and height refer to the screen width and height. this if statement insures that only tiles on the screen are rendered! There are 4 bounds representing top, bottom, left and right. the far left bound isn't zero because...see andrew ton.
				//"xa < -tile.sprite.SIZE" insures that we get an extra tile on the far left so rendering is smooth.
				if (xa < 0) xa = 0; //we set xa equal to zero if "xa < 0" in case we are on the border. It ONLY solves the arrayindexoutofbounds error. doesnt have affect rendering
				pixels[xa + ya * width] = tile.sprite.pixels[x + y * tile.sprite.SIZE];
			}
		}
	}
	
	public void renderInterface(int xp, int yp, int iWidth, int iHeight, int button, boolean clicked) {
		xp -= xOffset; //subtracting the Offset accounts for player movement. This also somehow reverses the inversion of the controls. Understand why we are subtracting as opposed to adding.
		yp -= yOffset;
		
		//the outer if loops are for if the interface is clicked or not
		if(button == 0) {
			for (int y = 0; y < 1; y++) {
				int ya = y + yp; // ya refers to absolute position. y is the rendering thing moving from left to right. yp is the offset. The reason for needing an absolute position is that this method only renders a single tile
				for (int x = 0; x < iWidth; x++) {
					int xa = x + xp;
					if (xa < -32 || xa >= width || ya < 0 || ya >= height) break; //width and height refer to the screen width and height. this if statement insures that only tiles on the screen are rendered! There are 4 bounds representing top, bottom, left and right. the far left bound isn't zero because...see Andrew ton.
					if (xa < 0) xa = 0;
				
					pixels[xa + ya * width] = 0xffffffff;
				}
			}
		
			for (int y = 1; y < iHeight; y++) {
				int ya = y + yp; // ya refers to absolute position. y is the rendering thing moving from left to right. yp is the offset. The reason for needing an absolute position is that this method only renders a single tile
				for (int x = 0; x < iWidth; x++) {
					int xa = x + xp;
					if (xa < -32 || xa >= width || ya < 0 || ya >= height) break; //width and height refer to the screen width and height. this if statement insures that only tiles on the screen are rendered! There are 4 bounds representing top, bottom, left and right. the far left bound isn't zero because...see Andrew ton.
					if (xa < 0) xa = 0;
				
					pixels[xa + ya * width] = 0xaaaaaaaa;
				}	
			}
		} 
		
		else if(button == 1) {
			for (int y = 0; y < 1; y++) {
				int ya = y + yp; // ya refers to absolute position. y is the rendering thing moving from left to right. yp is the offset. The reason for needing an absolute position is that this method only renders a single tile
				for (int x = 0; x < iWidth; x++) {
					int xa = x + xp;
					if (xa < -32 || xa >= width || ya < 0 || ya >= height) break; //width and height refer to the screen width and height. this if statement insures that only tiles on the screen are rendered! There are 4 bounds representing top, bottom, left and right. the far left bound isn't zero because...see Andrew ton.
					if (xa < 0) xa = 0;
				
					pixels[xa + ya * width] = 0xabcdefed;
				}
			}
		
			for (int y = iHeight - 1; y < iHeight; y++) {
				int ya = y + yp; // ya refers to absolute position. y is the rendering thing moving from left to right. yp is the offset. The reason for needing an absolute position is that this method only renders a single tile
				for (int x = 0; x < iWidth; x++) {
					int xa = x + xp;
					if (xa < -32 || xa >= width || ya < 0 || ya >= height) break; //width and height refer to the screen width and height. this if statement insures that only tiles on the screen are rendered! There are 4 bounds representing top, bottom, left and right. the far left bound isn't zero because...see Andrew ton.
					if (xa < 0) xa = 0;
				
					pixels[xa + ya * width] = 0xabcdefed;
				}
			}
		
			for (int y = 0; y < iHeight; y++) {
				int ya = y + yp; // ya refers to absolute position. y is the rendering thing moving from left to right. yp is the offset. The reason for needing an absolute position is that this method only renders a single tile
				for (int x = 0; x < 1; x++) {
					int xa = x + xp;
					if (xa < -32 || xa >= width || ya < 0 || ya >= height) break; //width and height refer to the screen width and height. this if statement insures that only tiles on the screen are rendered! There are 4 bounds representing top, bottom, left and right. the far left bound isn't zero because...see Andrew ton.
					if (xa < 0) xa = 0;
				
					pixels[xa + ya * width] = 0xabcedef;
				}
			}
		
			for (int y = 0; y < iHeight; y++) {
				int ya = y + yp; // ya refers to absolute position. y is the rendering thing moving from left to right. yp is the offset. The reason for needing an absolute position is that this method only renders a single tile
				for (int x = iWidth - 1; x < iWidth; x++) {
					int xa = x + xp;
					if (xa < -32 || xa >= width || ya < 0 || ya >= height) break; //width and height refer to the screen width and height. this if statement insures that only tiles on the screen are rendered! There are 4 bounds representing top, bottom, left and right. the far left bound isn't zero because...see Andrew ton.
					if (xa < 0) xa = 0;
				
					pixels[xa + ya * width] = 0xabcdefed;
				}
			}
		
			for (int y = 1; y < iHeight - 1; y++) {
				int ya = y + yp; // ya refers to absolute position. y is the rendering thing moving from left to right. yp is the offset. The reason for needing an absolute position is that this method only renders a single tile
				for (int x = 1; x < iWidth - 1; x++) {
					int xa = x + xp;
					if (xa < -32 || xa >= width || ya < 0 || ya >= height) break; //width and height refer to the screen width and height. this if statement insures that only tiles on the screen are rendered! There are 4 bounds representing top, bottom, left and right. the far left bound isn't zero because...see Andrew ton.
					if (xa < 0) xa = 0;
				
					pixels[xa + ya * width] = 0xfedcbabc;
				}	
			}
		}
	
	}

	//same as renderTile basically.
	public void renderProjectile(int xp, int yp, Projectile p) { //since the constructor is Tile but not Sprite, we are able to change the type of sprite it is. i.e. grass to rock
		xp -= xOffset; //subtracting the Offset accounts for player movement. This also somehow reverses the inversion of the controls. Understand why we are subtracting as opposed to adding.
		yp -= yOffset;
		for (int y = 0; y < p.getSpriteSize(); y++) {
			int ya = y + yp; // ya refers to absolute position. y is the rendering thing moving from left to right. yp is the offset. The reason for needing an absolute position is that this method only renders a single tile
			for (int x = 0; x < p.getSpriteSize(); x++) {
				int xa = x + xp;
				if (xa < -p.getSpriteSize() || xa >= width || ya < 0 || ya >= height) break; //width and height refer to the screen width and height. this if statement insures that only tiles on the screen are rendered! There are 4 bounds representing top, bottom, left and right. the far left bound isn't zero because...see andrew ton.
				if (xa < 0) xa = 0;
				
				int col = p.getSprite().pixels[x + y * p.sprite.SIZE];
				if (col != 0xffffffff)
				pixels[xa + ya * width] = col;
			}
		}
	}
	
	
	
	public void renderMob(int xp, int yp, Sprite sprite) { 
		xp -= xOffset; //subtracting the Offset accounts for player movement. This also somehow reverses the inversion of the controls. Understand why we are subtracting as opposed to adding.
		yp -= yOffset;
		for (int y = 0; y < sprite.getHeight(); y++) {
			int ya = y + yp; // ya refers to absolute position. y is the rendering thing moving from left to right. yp is the offset. The reason for needing an absolute position is that this method only renders a single tile
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp;
				if (xa < -sprite.getWidth() || xa >= width || ya < 0 || ya >= height) break; //width and height refer to the screen width and height. this if statement insures that only tiles on the screen are rendered! There are 4 bounds representing top, bottom, left and right. the far left bound isn't zero because...see Andrew ton.
				if (xa < 0) xa = 0;
				
				int col = sprite.pixels[x + y * sprite.getWidth()]; //col is exactly the same as sprite.pixels[], but we use it because it helps us use the following if-statement...
				if (col != 0xffffffff) {//this if-statement tells the computer to not render pixels of a certain color. This is important, because when you draw stuff in paint, there are background colors you dont want. NOTE: Add an extra ff at the beginning, because...idk. If this doesn't work for you, see Andrew Ton!
					pixels[xa + ya * width] = col;
				}
			}
		}
	}
	
	public void renderPlayer(int xp, int yp, Sprite sprite){
		for(int y = 0; y < sprite.getHeight(); y++){
			for(int x = 0; x < sprite.getWidth(); x++) {
				int xs = x + xp;
				int ys = y + yp;
				
				if (xs >= width || xs < 0) {
					continue;
				}
				
				if (ys >= height || ys < 0) {
					continue;
				}
				
				int col = sprite.pixels[x + y * sprite.getWidth()]; //col is exactly the same as sprite.pixels[], but we use it because it helps us use the following if-statement...
				if (col != 0xffffffff) {//this if-statement tells the computer to not render pixels of a certain color. This is important, because when you draw stuff in paint, there are background colors you dont want. NOTE: Add an extra ff at the beginning, because...idk. If this doesn't work for you, see Andrew Ton!
					pixels[xs + ys * width] = col;
				}
			}
		}
	}
	
	public void setOffset(int xOffset, int yOffset) { //this method is called by Level.render
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
}
