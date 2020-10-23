package djs.game.slidepuzzle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class ScreenPlay extends ScreenAbstract implements GestureDetector.GestureListener {
    // tag
    private static final String TAG = ScreenPlay.class.getSimpleName();

    // variables
    private int m_level_number;
    private Level m_level;

    // methods
    public ScreenPlay(IGameServices game_services, int level_number) {
        super(game_services);

        Gdx.app.log(TAG, "ScreenPlay()");

        // save
        this.m_level_number = level_number;

        // create the level
        this.m_level = new Level(this.m_level_number, 48);
        this.m_level.setPosition(720.0f / 2, 1280.0f / 2, Align.center);
        this.m_stage.addActor(this.m_level);

        // reset button
        TextButton tb = new TextButton("Reset", this.m_game_services.get_ui_skin());
        tb.setSize(128, 64);
        tb.setPosition(720 - tb.getWidth(), tb.getHeight() + 4);
        tb.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenPlay.this.m_level.remove();
                ScreenPlay.this.m_level = new Level(ScreenPlay.this.m_level_number, 48);
                ScreenPlay.this.m_level.setPosition(720.0f / 2, 1280.0f / 2, Align.center);
                ScreenPlay.this.m_stage.addActor(ScreenPlay.this.m_level);
                ScreenPlay.this.m_level.toBack();
            }
        });
        this.m_stage.addActor(tb);

        tb = new TextButton("Next", this.m_game_services.get_ui_skin());
        tb.setSize(128, 64);
        tb.setPosition(720 - tb.getWidth(), 0);
        tb.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenPlay.this.m_level.remove();
                ScreenPlay.this.m_level = new Level(++ScreenPlay.this.m_level_number, 48);
                ScreenPlay.this.m_level.setPosition(720.0f / 2, 1280.0f / 2, Align.center);
                ScreenPlay.this.m_stage.addActor(ScreenPlay.this.m_level);
                ScreenPlay.this.m_level.toBack();
            }
        });
        this.m_stage.addActor(tb);


        this.m_stage.setDebugAll(true);
    }

    @Override
    public void show() {
        Gdx.app.log(TAG, "show()");

        InputMultiplexer im = new InputMultiplexer();
        im.addProcessor(new GestureDetector(this));
        im.addProcessor(this.m_stage);
        Gdx.input.setInputProcessor(im);
    }

    // gesturelistener
    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        Gdx.app.log(TAG, "fling(" + velocityX + ", " + velocityY + ", " + button + ")");

        if (Math.abs(velocityX) > Math.abs(velocityY)){
            if (velocityX < -350.0f){
                // left
                this.m_level.fling_direction(-1, 0);
            }
            else if (velocityX > 350.0f){
                // right
                this.m_level.fling_direction(1, 0);
            }
        }
        else{
            if (velocityY < -350.0f){
                // up
                this.m_level.fling_direction(0, 1);
            }
            else if (velocityY > 350.0f){
                // down
                this.m_level.fling_direction(0, -1);
            }
        }
        return true;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}