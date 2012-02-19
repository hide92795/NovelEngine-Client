package hide92795.novelengine.client;

import java.awt.Canvas;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JFrame;

public class NovelEngineFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private NovelEngine engine;
	private Canvas canvas;
	public final AtomicReference<Dimension> newCanvasSize;

	public NovelEngineFrame(NovelEngine engine) {
		super(engine.dataBasic.getGamename());
		this.engine = engine;
		newCanvasSize = new AtomicReference<Dimension>();
		init();
	}

	private void init() {
		getContentPane().setPreferredSize(
				new Dimension(engine.width, engine.height));
		getContentPane().setBackground(Color.black);
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLayout(new CardLayout());
		add(getCanvas(), "GL");
		pack();
		setLocationRelativeTo(null);
	}

	public Canvas getCanvas() {
		if (canvas == null) {
			canvas = new Canvas();
			canvas.addComponentListener(new ComponentAdapter() {
				@Override
				public void componentResized(ComponentEvent e) {
					newCanvasSize.set(canvas.getSize());
				}
			});

			addWindowFocusListener(new WindowAdapter() {
				@Override
				public void windowGainedFocus(WindowEvent e) {
					canvas.requestFocusInWindow();
				}
			});

			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					engine.exit();
				}
			});
		}
		return canvas;
	}
}
