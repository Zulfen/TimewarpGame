package com.zulfen.timewarp;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.zulfen.timewarp.entity.TimeWarpEntityFactory;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGL.*;

// for times sake, putting most of the logic here.
public class TimeWarp extends GameApplication {

    private Entity player;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Timewarp");
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setVersion("0.1");
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new TimeWarpEntityFactory());
        setLevelFromMap("main.tmx");
        player = spawn("player");
    }

    public void run(String[] commandLineArgs) {
        launch(commandLineArgs);
    }

}
