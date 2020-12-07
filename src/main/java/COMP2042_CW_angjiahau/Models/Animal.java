package COMP2042_CW_angjiahau.Models;
import static COMP2042_CW_angjiahau.Main.RESOURCE_PATH;
import java.util.ArrayList;
import java.util.HashMap;
import COMP2042_CW_angjiahau.Controllers.Actor;
import COMP2042_CW_angjiahau.Controllers.End;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;


public class Animal extends Actor {

	Music music = new Music();
	public static String FROG_RESOURCE_PATH = RESOURCE_PATH + "frog/";
	int points = 0;
	int end = 0;
	private boolean second = false;
	boolean noMove = false;
	double movement = 13.3333333*2;
	double movementX = 10.666666*2;
	int imgSize = 40;
	boolean carDeath = false;
	boolean waterDeath = false;
	boolean changeScore = false;
	int carD = 0;
	int waterD=0;
	double w = 800;
	double waterLevel =0;


	ArrayList<End> inter = new ArrayList<End>();
	HashMap<String,Image> carDeathAnimation = new HashMap<String, Image>();
	HashMap<String,Image> waterDeathAnimation = new HashMap<String,Image>();

	public Animal(String animalImage) {
		setImage(new Image(FROG_RESOURCE_PATH + animalImage + ".png", imgSize, imgSize, true, true));
		Image froggerNoJump = new Image(FROG_RESOURCE_PATH + "froggerUp.png", imgSize, imgSize, true, true);
		Image froggerJump = new Image(FROG_RESOURCE_PATH + "froggerUpJump.png", imgSize, imgSize, true, true);
		startingPosition();
		addHashCar();
		addHashWater();




				setOnKeyPressed(new EventHandler<KeyEvent>() {

					public void handle(KeyEvent event){

						if(noMove) {
				}
				else {
					music.hopSound();
					switch(event.getCode()) {
						
					case UP:
					case W :						
							move(0, -movement);
							changeScore = false;
							setImage(second ? froggerNoJump : froggerJump);
							setRotate(0);
							break;
							
					case LEFT:		               					    
					case A :					
						move(-movementX, 0);
						changeScore = false;
						setImage(second ? froggerNoJump : froggerJump);
						setRotate(-90);
						break;
						
					case DOWN:		           					
					case S : 					
						move(0, movement);
						changeScore = false;
						setImage(second ? froggerNoJump : froggerJump);
						setRotate(-180);
						
						break;
						
					case RIGHT:
					case D : 				 	
						move(movementX, 0);
						changeScore = false;
						setImage(second ? froggerNoJump : froggerJump);
						setRotate(90);
						break;
						
					default:
						break;							
							}
					
					second = !second;
						}
					}
			});


	setOnKeyReleased(new EventHandler<KeyEvent>() {
		
		public void handle(KeyEvent event){		
			
			if(noMove) {					
			} 			
			else {
				music.hopSound();							
				switch(event.getCode()) {
				
				case UP:
				case W :					
					move(0, -movement);
	                setImage(froggerNoJump);
	                second = false;	                
					if (getY() < w) {
						changeScore = true;
						w = getY();
						points+=10;
					}
					break;
	             
				case LEFT:
				case A :					
					 move(-movementX, 0);
	            	 setImage(froggerNoJump);
	            	 second = false;
	            	 break;
				
				case DOWN:
				case S : 					
					move(0, movement);
	            	 setImage(froggerNoJump);
	            	 second = false;
	            	 break;
				
				case RIGHT:
				case D : 				 	
					 move(movementX, 0);
	            	 setImage(froggerNoJump);
	            	 second = false;
	            	 break;
					
				default:
						break;						
						}
					}
				}
		});	

}

	@Override
	public void act(long now) {
		checkoutOfBounds();
		collisionCheck();
		carDeathAnimation(now);
		waterDeathAnimation(now);
	}

	public void collisionCheck(){
			if (getIntersectingObjects(Obstacle.class).size() >= 1) {
				carDeath = true;
			}
			if (getIntersectingObjects(Platform.class).size() >= 1 && !noMove ) {
					Platform currentPlatform = getIntersectingObjects(Platform.class).get(0);
					move(currentPlatform.getSpeed(), 0);
				if (currentPlatform instanceof SinkingPlatform && ((SinkingPlatform)currentPlatform).isSunk()) {
					waterDeath = true;
				}
			}
			else if (getIntersectingObjects(End.class).size() >= 1) {
				inter = (ArrayList<End>) getIntersectingObjects(End.class);
				if (getIntersectingObjects(End.class).get(0).isActivated()) {
					end--;
					points-=50;
				}
				points+=50;
				changeScore = true;
				w=800;
				getIntersectingObjects(End.class).get(0).setEnd();
				end++;
				startingPosition();
			}
			else if (getY()<waterLevel){
				waterDeath = true;
			}
	}

	public void carDeathAnimation(long time){
		if (carDeath) {
			music.squashSound();
			noMove = true;
			if ((time)%11==0 && carD!=4) {
				carD++;
				setImage(carDeathAnimation.get("carDeathAnimation"+carD));
			}
			if(carD==4) {
				deathReset();
			}
		}
	}

	public void waterDeathAnimation(long time){
		if (waterDeath) {
			noMove = true;
			music.plunkSound();
			if ((time) % 11 == 0 && waterD != 5) {
				waterD++;
				setImage(waterDeathAnimation.get("waterDeathAnimation"+waterD));
			}
			if (waterD == 5) {
				deathReset();
			}
		}
	}

	public void addHashCar(){
		for(int carDImages=0;carDImages<=3;carDImages++) {
			carDeathAnimation.put("carDeathAnimation"+carDImages, new Image(FROG_RESOURCE_PATH + "death_animations/cardeath" + carDImages + ".png", imgSize, imgSize, true, true));
		}
	}

	public void addHashWater(){
		for(int waterDImages=0;waterDImages<=4;waterDImages++) {
			waterDeathAnimation.put("waterDeathAnimation"+waterDImages, new Image(FROG_RESOURCE_PATH + "death_animations/waterdeath" + waterDImages + ".png", imgSize, imgSize, true, true));
		}
	}

	public void deathReset(){
		startingPosition();
		setImage(new Image(FROG_RESOURCE_PATH + "froggerUp.png", imgSize, imgSize, true, true));
		noMove = false;
		if (points > 50) {
			points -= 50;
			changeScore = true;
		}
		waterDeath = false;
		carDeath = false;
		waterD = 0;
		carD = 0;
	}

	public void checkoutOfBounds(){
		if (getY()<0 || getY()>734) {
			startingPosition();
		}
		if (getX()<0) {
			move(movement*2, 0);
		}
		if (getX()>600) {
			move(-movement*2, 0);
		}
	}
	public void startingPosition(){
		setX(300);
		setY(679.8 + movement);
	}

	public void waterLevel(int waterLevel) {
		this.waterLevel = waterLevel;
	}

	public boolean waterDeath() {		
		return waterDeath;
	}
	public boolean nowaterDeath() {		
		return waterDeath = false;
	}
	public boolean getStop() {
		return end==1;
	}
	
	public int getPoints() {
		return points;
	}
	
	public boolean changeScore() {
		if (changeScore) {
			changeScore = false;
			return true;
		}
		return false;
	}
	
	public void reset() {
		deathReset();
		end = 0;
		points = 0;
	}
}
