package COMP2042_CW_angjiahau.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


import COMP2042_CW_angjiahau.Models.Digit;

import javafx.animation.AnimationTimer;

public class Level extends World {
	ArrayList<End> ends = new ArrayList<>();
	ArrayList<Digit> digits = new ArrayList<>();
	public Level() {
	//set 5 slots for frog to be filled in 
	ends.add(new End(13,96));
	ends.add(new End(141,96));
	ends.add(new End(141 + 141-13,96));
	ends.add(new End(141 + 141-13+141-13+1,96));
	ends.add(new End(141 + 141-13+141-13+141-13+3,96));
	ends.forEach(new Consumer<End>() {
		@Override
		public void accept(End end) {
			add(end);
		}
	});
}
	Animal animal;
	
	@Override
	public void act(long now) {
			
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
