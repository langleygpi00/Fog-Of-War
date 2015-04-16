package folder;
import java.awt.Rectangle;

public class Inter extends Rectangle {
	
	private Keyboard input;
	private Game game;
	public int x;
	public int y;
	public int width;
	public int height;
	public boolean clicked; //determines whether the button is clicked or not
	public int button; //determines what type of button it is
	
	protected int dir = 0;
	
	public Inter(Game mainGame, Keyboard input, int x, int y, int width, int height, int button) {
		game = mainGame;
		this.input = input;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.button = button;
		
		clicked = false; //whether the interface is clicked or not
		
	}
	
	public void move(int xa, int ya) { //xa and ya track the change in position
		
		
		if ( xa != 0 && ya != 0) { //this says if we are moving in two axis' at once, this separates it into two different commands.
			move(xa, 0);
			move(0, ya);
			return;
		}
		
		x += xa;
		y += ya;
	}
	
	public void update() { // green icon on the left indicates it is overriding an update method.
		int xa = 0, ya = 0; 
		if(game.getPlayer().x < 0){
			xa -= 2;
		}else if(game.getPlayer().x + 32 > game.getScreen().width){
			xa += 2;
		}
		if(game.getPlayer().y < 0){
			ya -= 2;
		}else if(game.getPlayer().y + 32 > game.getScreen().height){
			ya +=2;
		}
		
		if (xa != 0 || ya!= 0) move(xa, ya);
		
		//detects if the mouse is clicking on the interface
		/*if(Mouse.getButton() == 1 && Mouse.getX() >= 0 && Mouse.getX() <= 1200 && Mouse.getY() > 525 && Mouse.getY() < 675) {
			clicked = true;
		} else {
			clicked = false;
		}*/
		
		return;
	}
	
	public void render(Screen screen) {
		//The "-16" helps center character by shifting it over to the left. 
		screen.renderInterface(x, y, width, height, button, clicked); //renders all 4 pieces of the 32x32 sprite. Modifying the x or y will change where the screen is centered.
	}
	
}
