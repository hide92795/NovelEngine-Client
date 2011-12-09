package hide92795.NovelEngine.Panel;

import java.util.ArrayList;
import java.util.HashMap;

import hide92795.NovelEngine.Client.NovelEngine;
import hide92795.NovelEngine.Gui.Button;

public class Panel {

	public NovelEngine engine;
	public HashMap<Integer, Button> buttons;
	

	public Panel(NovelEngine engine) {
		this.engine = engine;
		this.buttons = new HashMap<Integer, Button>();
	}

	public void render() {

	}

	public void update() {
		for (Button b : buttons.values()) {
			b.update();
		}
	}
	
	public void actionPerformed(Button b, boolean isLeft){
		
	}
	
	public void onMouse(Button b){
		
	}

	public void init() {

	}

	public void leftClick(int x, int y) {
	}

	public void rightClick(int x, int y) {
	}
}
