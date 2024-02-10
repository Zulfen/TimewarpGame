package com.zulfen.timewarp;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.zulfen.timewarp.entity.TimeWarpEntityFactory;

import static com.almasb.fxgl.dsl.FXGL.*;

public class TimeWarp extends GameApplication {

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
        spawn("player");
    }

    public void run(String[] commandLineArgs) {
        launch(commandLineArgs);
    }

}
