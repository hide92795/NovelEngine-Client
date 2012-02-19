package hide92795.novelengine.panel;

import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.gui.Button;

import java.util.HashMap;

public class Panel {

	public NovelEngine engine;
	public HashMap<Integer, Button> buttons;
	

	public Panel(NovelEngine engine) {
		this.engine = engine;
		this.buttons = new HashMap<Integer, Button>();
	}

	public void render() {

	}

	public void update(int delta) {
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
