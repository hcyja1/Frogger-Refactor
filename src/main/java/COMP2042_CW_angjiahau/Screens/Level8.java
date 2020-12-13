package COMP2042_CW_angjiahau.Screens;
import COMP2042_CW_angjiahau.Controllers.Level;
import COMP2042_CW_angjiahau.Models.Display.BackgroundImage;
import COMP2042_CW_angjiahau.Models.Platforms.Log;
import COMP2042_CW_angjiahau.Models.Platforms.Turtle;
import COMP2042_CW_angjiahau.Models.Platforms.WetTurtle;



public class Level8 extends Level {

    /**
     * This method generates the eighth level of the game.
     * Firstly, each level has its own background image which is added in the method.
     * After that, the obstacles and platforms for each rows are added.
     * The values for the y-coordinates of the rows are fetched from an enumeration at {@link Level}
     * Main character is called to front so it does not get blocked by other variables
     * Initial high score showing value of 0 and label is shown on top right corner
     * Water Level is set.
     */
    public Level8()  {
        BackgroundImage froggerback = new BackgroundImage("level8background");
        add(froggerback);

        add(new WetTurtle(600, Rows.ROW1.getValue()-5, -2, 130, 130));
        add(new WetTurtle(100, Rows.ROW1.getValue()-5, -2, 130, 130));
        add(new Log(300, 0, Rows.ROW2.getValue(), -3,1) );
        add(new Log(300, 400, Rows.ROW2.getValue(), -3,1) );
        add(new Log(150, 600, Rows.ROW3.getValue(), 1.85,3) );
        add(new Log(150, 150, Rows.ROW3.getValue(), 1.85,3) );
        add(new Log(150, 410, Rows.ROW3.getValue(), 1.85,3) );
        add(new WetTurtle(250, Rows.ROW4.getValue(), -2, 130, 130));
        add(new WetTurtle(600, Rows.ROW4.getValue(), -2, 130, 130));
        add(new Log(150, 0, Rows.ROW5.getValue()+5, 1.85,3) );
        add(new Log(150, 220, Rows.ROW5.getValue()+5, 1.85,3) );
        add(new Log(150, 440, Rows.ROW5.getValue()+5, 1.85,3) );
        add(new Turtle(500, Rows.ROW6.getValue(), -3, 130, 130));
        add(new Turtle(300, Rows.ROW6.getValue(), -3, 130, 130));
        add(new Log(300, 0, Rows.ROW7.getValue(), -3,1) );
        add(new Log(300, 400, Rows.ROW7.getValue(), -3,1) );
        add(new WetTurtle(700, Rows.ROW8.getValue()-5, -2, 130, 130));
        add(new WetTurtle(350, Rows.ROW8.getValue()-5, -2, 130, 130));
        add(new Log(150, 50, Rows.ROW9.getValue()+5, 1.85,3) );
        add(new Log(150, 270, Rows.ROW9.getValue()+5, 1.85,3) );
        add(new Log(150, 490, Rows.ROW9.getValue()+5, 1.85,3) );
        add(new WetTurtle(300, Rows.ROW10.getValue()-4, -2, 130, 130));
        add(new WetTurtle(600, Rows.ROW10.getValue()-4, -3, 130, 130));
        add(new Turtle(300, Rows.ROW10.getValue()-4, -2, 130, 130));

        getAnimal().toFront();
        getAnimal().waterLevel(680);
        showInitialHighScore();
    }

    /**
     * Overriden method which is called in every frame as long as AnimationTimer is active.
     * This Overriden method makes sure the score is kept updated according to the level's occurrence.
     * It also makes sure that the game ends when 5 frogs are brought home to the end.
     * Lastly, the method makes sure after each round, a high score file for the level will be created(if it does not already exist)
     * the high score of the player will be compared to the existing(if any) high scores within the high score file, and stored if it is higher or equal to any of the existing high score values.
     * @param now is the Timestamp of the current frame given in nanoseconds. This value will be the same for all AnimationTimers called during one frame.
     */
    @Override
    public void act(long now) {
        if (getAnimal().changeScore()) {
            setNumber(getAnimal().getPoints());
        }
        if (getAnimal().getStop()) {
            stop();
            highScoreCaller();
        }
    }

    /**
     * Makes sure Timer is started causing it to start sending action events to its listeners.
     * This method also makes sure each round is properly reset.
     */
    @Override
    public void start() {
        super.start();
        getAnimal().reset();
    }



}