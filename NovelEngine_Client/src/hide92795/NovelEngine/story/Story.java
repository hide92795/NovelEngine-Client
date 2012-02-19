package hide92795.NovelEngine.story;

import hide92795.NovelEngine.panel.PanelStory;

public class Story {

	public static final int IF_EQUAL = 0;
	public static final int IF_GREATER = 1;
	public static final int IF_LESS = 2;
	public static final int IF_GREATER_EQUAL = 3;
	public static final int IF_LESS_EQUAL = 4;

	public static final int COMMAND_SET_CHAPTERID = -1;
	public static final int COMMAND_SET_SCENEID = 0;
	public static final int COMMAND_LOAD_CHAPTER = 1;
	public static final int COMMAND_MOVE_CHAPTER = 2;
	public static final int COMMAND_CHANGE_BG = 3;
	public static final int COMMAND_CHANGE_CHARACTER = 4;
	public static final int COMMAND_SHOW_CG = 5;
	public static final int COMMAND_SHOW_WORDS = 6;
	public static final int COMMAND_MAKE_BUTTON = 7;
	public static final int COMMAND_IF = 8;
	public static final int COMMAND_PLAY_BGM = 9;
	public static final int COMMAND_STOP_BGM = 10;
	public static final int COMMAND_PLAY_SE = 11;
	public static final int COMMAND_SHOW_BOX = 12;
	public static final int COMMAND_HIDE_BOX = 13;
	
	public boolean isFinish() {
		return true;
	}
	
	public boolean isWait(){
		return false;
	}
	
	public void init(PanelStory story){
		
	}
	
	public void update(PanelStory panelStory, int delta){
		
	}
	
	public void render(){
		
	}

}
