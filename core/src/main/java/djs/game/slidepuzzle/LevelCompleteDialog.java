package djs.game.slidepuzzle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class LevelCompleteDialog extends Group {
    // interface
    public interface ILevelCompleteDialogListener{
        void on_retry(LevelCompleteDialog dlg);
        void on_next(LevelCompleteDialog dlg);
        void on_quit(LevelCompleteDialog dlg);
    }

    // variables
    private ILevelCompleteDialogListener m_listener;

    // methods
    public LevelCompleteDialog(ILevelCompleteDialogListener listener, Skin skin, int num_moves){
        // listener
        this.m_listener = listener;

        // size
        this.setSize(720 * 0.75f, 720 * 0.75f);

        // background
        Image image = new Image(
                new Texture(Gdx.files.internal("block.png"))
        );
        image.setSize(this.getWidth(), this.getHeight());
        image.setColor(0.0f, 0.0f, 0.0f, 0.90f);
        this.addActor(image);

        // moves and level
        Label label = new Label("Moves: " + num_moves, skin);
        label.setPosition(this.getWidth() / 2, this.getHeight() - label.getHeight() / 2 - 8, Align.center);
        label.setAlignment(Align.center);
        this.addActor(label);

        // buttons
        int button_width = 128;
        int button_height = 64;

        // ((this.width / 3) / 2) + (this.width / 3 * i)

        // retry button
        TextButton tb = new TextButton("Retry", skin);
        tb.setSize(button_width, button_height);
        tb.setPosition(
                ((this.getWidth() / 3) / 2) + (this.getWidth() / 3 * 0),
                8 + tb.getHeight() / 2,
                Align.center
        );
        tb.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                LevelCompleteDialog.this.m_listener.on_retry(LevelCompleteDialog.this);
            }
        });
        this.addActor(tb);

        // next level
        tb = new TextButton("Next", skin);
        tb.setSize(button_width, button_height);
        tb.setPosition(
                ((this.getWidth() / 3) / 2) + (this.getWidth() / 3 * 1),
                8 + tb.getHeight() / 2,
                Align.center
        );
        tb.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                LevelCompleteDialog.this.m_listener.on_next(LevelCompleteDialog.this);
            }
        });
        this.addActor(tb);

        // quit
        tb = new TextButton("Quit", skin);
        tb.setSize(button_width, button_height);
        tb.setPosition(
                ((this.getWidth() / 3) / 2) + (this.getWidth() / 3 * 2),
                8 + tb.getHeight() / 2,
                Align.center
        );
        tb.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                LevelCompleteDialog.this.m_listener.on_quit(LevelCompleteDialog.this);
            }
        });
        this.addActor(tb);




        // show lines
        this.debugAll();
    }
}
