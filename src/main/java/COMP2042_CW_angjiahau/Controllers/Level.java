package COMP2042_CW_angjiahau.Controllers;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import COMP2042_CW_angjiahau.Models.Animal;
import COMP2042_CW_angjiahau.Models.Display.Digit;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Alert;

public class Level extends World {
	 private final Animal animal;
	ArrayList<End> ends = new ArrayList<>();
	ArrayList<Digit> digits = new ArrayList<>();

	public enum Rows{
		ROW1(649),
		ROW2(597),
		ROW3(540),
		ROW4(490),
		ROW5(435),
		ROW6(376),
		ROW7(329),
		ROW8(276),
		ROW9(217),
		ROW10(166);

		private final int yCoordinate;
		Rows(final int yCoordinate) {
			this.yCoordinate = yCoordinate;
		}
		public int getValue() { return this.yCoordinate; }

	}

	public Level() {
	//set 5 slots for frog to be filled in 
	ends.add(new End(13,96));
	ends.add(new End(141,96));
	ends.add(new End(141 + 141-13,96));
	ends.add(new End(141 + 141-13+141-13+1,96));
	ends.add(new End(141 + 141-13+141-13+141-13+3,96));


	animal = new Animal("froggerUp");
	add(animal);

	ends.forEach(new Consumer<End>() {
		@Override
		public void accept(End end) {
			add(end);
		}
	});
}

	@Override
	public void act(long now) {

	}

	public Animal getAnimal(){
		return animal;
	}

	public void highScoreCaller(){
		try {
			HighScore.HighScoreController(getClass().getSimpleName(), getAnimal().getPoints());
		} catch(IOException e){
			e.printStackTrace();
			System.out.println("Error in File Controller");
		}

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("You Have Won The Round!");
		if(HighScore.checkHigher(getAnimal().getPoints())){
			alert.setHeaderText("You have a new High Score: "+ getAnimal().getPoints()+"!");
		}else {
			alert.setHeaderText("Your High Score: " + getAnimal().getPoints() + "!");
		}
		alert.setContentText("Current Highscore List : " + HighScore.displayHighScore());
		alert.show();
	}

	@Override
	public void createTimer() {
	 super.createTimer(new AnimationTimer() {
	        @Override
	        public void handle(long now) {
	            act(now);
	            List<Actor> actors = getObjects(Actor.class);

	            for (Actor anActor: actors) {
	                anActor.act(now);
	            }
	        }
	    });
	}
	public void showInitialHighScore(){
		add(new Digit(0, 30, 550, 40));
		add(new HighScore("hi-scoreImage"));
	}

	public void setNumber(int n) {
		digits.forEach(new Consumer<Digit>() {
			@Override
			public void accept(Digit digit) {
				remove(digit);
			}
		});
		digits.clear();
        int shift = 0;
        while (n > 0) {
            int d = n / 10;
            int k = n - d * 10;
            n = d;
            Digit temp = new Digit(k, 30, 550 - shift, 40);
            digits.add(temp);
            add(temp);
            shift+=30;
        }
}

	    // Resets visual for score & and end
	    //also resets activation state of end 
	    @Override
		public void start() {
			super.start();
			ends.forEach(new Consumer<End>() {
				@Override
				public void accept(End end) {
					end.reset();
				}
			});
			setNumber(0);
		}


}

