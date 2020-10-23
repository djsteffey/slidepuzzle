package djs.game.slidepuzzle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public abstract class ScreenAbstract implements Screen {
    // tag
    private static final String TAG = ScreenAbstract.class.getSimpleName();

    // variables
    protected IGameServices m_game_services;
    protected Stage m_stage;

    // methods
    public ScreenAbstract(IGameServices game_services){
        Gdx.app.log(TAG, "ScreenAbstract()");

        this.m_game_services = game_services;
        this.m_stage = new Stage(new FitViewport(720, 1280));
    }

    @Override
    public void dispose() {
        Gdx.app.log(TAG, "dispose()");

        this.m_stage.dispose();
    }

    @Override
    public void render(float delta) {
        this.m_stage.act(delta);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.m_stage.draw();
    }

    @Override
    public void show() {
        Gdx.app.log(TAG, "show()");

        Gdx.input.setInputProcessor(this.m_stage);
    }

    @Override
    public void hide() {
        Gdx.app.log(TAG, "hide()");

        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void resume() {
        Gdx.app.log(TAG, "resume()");
    }

    @Override
    public void pause() {
        Gdx.app.log(TAG, "pause()");
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log(TAG, "resize(" + width +", " + height + ")");
    }
}
