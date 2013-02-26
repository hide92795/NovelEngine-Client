package hide92795.novelengine.background;

import hide92795.novelengine.Effectable;
import hide92795.novelengine.client.NovelEngine;
import hide92795.novelengine.panel.PanelStory;
import hide92795.novelengine.story.StoryEffect;

/**
 * 背景及び画面に対して視覚効果を提供するクラスです。
 *
 * @author hide92795
 */
public abstract class EffectBackGround extends Effectable {
	/**
	 * エフェクトを初期化します。
	 *
	 * @param story
	 *            現在、画面を描画している{@link hide92795.novelengine.panel.Panel Panel} オブジェクト
	 * @param target
	 *            描画するレイヤーID
	 */
	public abstract void init(PanelStory story, byte target);

	/**
	 * エフェクトを更新します。
	 *
	 * @param story
	 *            このエフェクトを実行している{@link hide92795.novelengine.story.StoryEffect StoryEffect} オブジェクト
	 * @param delta
	 *            前回のupdateとの時間差
	 */
	public abstract void update(StoryEffect story, int delta);

	/**
	 * エフェクトを描画します。
	 *
	 * @param engine
	 *            　実行中の{@link hide92795.novelengine.client.NovelEngine}オブジェクト
	 */
	public abstract void render(NovelEngine engine);

	/**
	 * このエフェクトの実行元の{@link hide92795.novelengine.story.StoryEffect StoryEffect}
	 * オブジェクトが、マウス及びキーボードイベントによりスキップされた際に呼び出されます。
	 */
	public abstract void skip();
}
