package hide92795.novelengine.data;

import hide92795.novelengine.story.Story;
import hide92795.novelengine.story.StoryScene;

import java.util.HashMap;
import java.util.LinkedList;

public class DataStory extends Data {
	private int chapterId;
	public HashMap<Integer, Integer> scenes;
	private LinkedList<Story> commandLine;
	private int pos;

	public DataStory() {
		scenes = new HashMap<Integer, Integer>();
		commandLine = new LinkedList<Story>();
		pos = 0;
	}

	public final int getChapterId() {
		return chapterId;
	}

	public final void setChapterId(int chapterId) {
		this.chapterId = chapterId;
	}

	public final void addStory(Story story) {
		if (story instanceof StoryScene) {
			scenes.put(((StoryScene) story).getSceneId(), commandLine.size());
		}
		commandLine.add(story);
	}
	
	public final Story next(){
		Story s = commandLine.get(pos);
		pos++;
		return s;
	}
}
