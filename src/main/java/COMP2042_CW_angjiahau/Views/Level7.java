package COMP2042_CW_angjiahau.Views;
import COMP2042_CW_angjiahau.Controllers.Animal;
import COMP2042_CW_angjiahau.Controllers.Level;
import COMP2042_CW_angjiahau.Models.BackgroundImage;
import COMP2042_CW_angjiahau.Models.Digit;
import COMP2042_CW_angjiahau.Controllers.HighScore;
import COMP2042_CW_angjiahau.Models.Log;
import COMP2042_CW_angjiahau.Models.Obstacle;
import COMP2042_CW_angjiahau.Models.Turtle;
import COMP2042_CW_angjiahau.Models.WetTurtle;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.util.List;


public class Level7 extends Level {
    Animal animal;
    List <Integer> HighScoreController;
    public Level7()  {
        //add background image
        BackgroundImage froggerback = new BackgroundImage("level7background");
        add(froggerback);


        //Add platforms
        add(new Log(150, 0, 166, 3.55,3) );
        add(new Log(150, 220, 166, 3.55,3) );
        add(new Log(150, 440, 166, 3.55,3) );
        add(new Log(300, 0, 276, -3.2,1) );
        add(new Log(300, 400, 276, -3.2,1) );
        add(new Log(150, 50, 329, 1.5,3) );
        add(new Log(150, 270, 329, 1.5,3) );
        add(new Log(150, 490, 329, 1.5,3) );

        add(new Turtle(500, 376, -3, 130, 130));
        add(new WetTurtle(300, 376, -3, 130, 130));
        add(new WetTurtle(700, 376, -3, 130, 130));
        add(new WetTurtle(600, 217, -2, 130, 130));
        add(new WetTurtle(400, 217, -2, 130, 130));
        add(new WetTurtle(200, 217, -2, 130, 130));



        //intialize starting main actor image
        animal = new Animal("froggerUp");
        add(animal);

        add(new Obstacle(0, 649, 1, 120, 120, "bigTruck"));
        add(new Obstacle(300, 649, 1, 120, 120, "bigTruck"));
        add(new Obstacle(600, 649, 1, 120, 120, "bigTruck"));
        add(new Obstacle(100, 597, -3, 50, 50, "racecar"));
        add(new Obstacle(400, 597, -3, 50, 50, "racecar"));
        add(new Obstacle(550, 597, -3, 50, 50, "racecar"));
        add(new Obstacle(0, 540, 1, 200, 200, "longTruck"));
        add(new Obstacle(500, 540, 1, 200, 200, "longTruck"));
        add(new Obstacle(500, 490, -5, 50, 50, "racecar"));
        add(new Obstacle(100, 490, -5, 50, 50, "racecar"));

        animal.waterLevel(413);

        add(new Digit(0, 30, 550, 40));
        add(new HighScore("hi-scoreImage"));
    }


    @Override
    public void act(long now) {

        if (animal.changeScore()) {
            setNumber(animal.getPoints());
        }
        if (animal.getStop()) {
            System.out.print("STOPP:");
            stop();
            try {
                HighScore.HighScoreController("Level7", animal.getPoints());
            } catch(IOException e) {
                e.printStackTrace();
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("You Have Won The Round!");
            if(HighScore.checkHigher(animal.getPoints())){
                alert.setHeaderText("You have a new High Score: "+animal.getPoints()+"!");
            }else {
                alert.setHeaderText("Your High Score: " + animal.getPoints() + "!");
            }
            alert.setContentText("Current Highscore List : " + HighScore.displayHighScore());
            alert.show();
        }
    }

    @Override
    public void start() {
        super.start();
        animal.reset();
    }

}
