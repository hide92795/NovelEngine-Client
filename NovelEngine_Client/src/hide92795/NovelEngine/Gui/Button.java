package hide92795.NovelEngine.Gui;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import hide92795.NovelEngine.Utils;
import hide92795.NovelEngine.Client.NovelEngine;
import hide92795.NovelEngine.Command.CommandButton;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.Color;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.opengl.ImageIOImageData;
import org.newdawn.slick.opengl.Texture;

public class Button extends Gui {

	private int imageID;
	private int imageOmID;
	private int[] position;
	private final CommandButton command;
	private final int name;
	private final CommandButton commandOm;
	private Texture image;
	private Texture imageOm;
	private Rectangle rectangle;
	private boolean onmouse;
	private BufferedImage clickable;
	private boolean leftClick;
	private boolean rightClick;

	public Button(int name, int[] position, int texid, int texidOm,
			BufferedImage clickable, CommandButton command,
			CommandButton commandOm) {
		this.name = name;
		this.setPosition(position);
		this.imageID = texid;
		this.imageOmID = texidOm;
		this.clickable = clickable;
		this.command = command;
		this.commandOm = commandOm;

	}

	@Override
	public void init() {
		image = NovelEngine.theEngine.imageManager.getImage(imageID);
		imageOm = NovelEngine.theEngine.imageManager.getImage(imageOmID);
	}

	@Override
	public void render() {
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		if (onmouse) {
			imageOm.bind();
		} else {
			image.bind();
		}
		glBegin(GL_QUADS);
		{
			int x = rectangle.getX();
			int y = rectangle.getY();
			glTexCoord2f(0, 0);
			glVertex2f(x, y);
			glTexCoord2f(1, 0);
			glVertex2f(x + image.getTextureWidth(), y);
			glTexCoord2f(1, 1);
			glVertex2f(x + image.getTextureWidth(),
					y + image.getTextureHeight());
			glTexCoord2f(0, 1);
			glVertex2f(x, y + image.getTextureHeight());
		}
		glEnd();
	}

	@Override
	public void update() {
		int x = rectangle.getX();
		int y = rectangle.getY();
		int w = rectangle.getWidth();
		int h = rectangle.getHeight();
		int mx = Mouse.getX();
		int my = Display.getHeight() - Mouse.getY() - 1;
		if (x <= mx && x + w - 1 >= mx && y <= my && y + h - 1 >= my) {
			int ix = mx - x;
			int iy = my-y;
			int col = clickable.getRGB(ix, iy);
			Color c = Utils.integerToColor(col);
			if(c.equals(Color.WHITE)){
				onmouse = false;
				return;
			}
			if (!onmouse) {
				onmouse = true;
				NovelEngine.theEngine.onMouse(this);
			}

			if (Mouse.isButtonDown(0)) {
				if (!leftClick) {
					leftClick = true;
					NovelEngine.theEngine.actionPerformed(this, true);
				}
			} else {
				leftClick = false;
			}

			if (Mouse.isButtonDown(1)) {
				if (!rightClick) {
					rightClick = true;
					NovelEngine.theEngine.actionPerformed(this, false);
				}
			} else {
				rightClick = false;
			}
		} else
			onmouse = false;

	}

	public int getName() {
		return name;
	}

	public int[] getPosition() {
		return position;
	}

	public void setPosition(int[] position) {
		this.position = position;
	}

	public void setRectangle(Rectangle rect) {
		rect.setSize(clickable.getWidth(), clickable.getHeight());
		this.rectangle = rect;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public CommandButton getCommand() {
		return command;
	}

	public CommandButton getCommandOm() {
		return commandOm;
	}

}
