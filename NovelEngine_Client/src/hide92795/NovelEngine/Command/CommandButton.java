package hide92795.NovelEngine.Command;

public class CommandButton {
	public static final int MENU_COMMAND_NONE = 4;
	public static final int MENU_COMMAND_START = 0;
	public static final int MENU_COMMAND_MOVE = 1;
	public static final int MENU_COMMAND_LOAD = 5;
	public static final int MENU_COMMAND_QLOAD = 2;
	public static final int MENU_COMMAND_CONFIG = 6;
	public static final int MENU_COMMAND_EXIT = 3;
	public static final int MENU_COMMAND_CHANGEBG = 7;

	private final int command;
	private final int id;

	public CommandButton(int com) {
		this(com, 0);
	}

	public CommandButton(int com, int id) {
		this.command = com;
		this.id = id;
	}

	public int getCommand() {
		return command;
	}


	public int getId() {
		return id;
	}

}
