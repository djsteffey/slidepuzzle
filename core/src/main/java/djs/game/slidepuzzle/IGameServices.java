package djs.game.slidepuzzle;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public interface IGameServices {
    void set_next_screen(ScreenAbstract screen);
    Skin get_ui_skin();
}
