package com.zulfen.timewarp;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import com.almasb.fxgl.physics.box2d.dynamics.Body;
import com.zulfen.timewarp.entity.TimeWarpEntityFactory;
import com.zulfen.timewarp.entity.controllers.PlayerComponent;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGL.*;

// for times sake, putting most of the logic here.
public class TimeWarp extends GameApplication {

    private Entity player;
    private TimeWarpEntityFactory entityFactory;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Timewarp");
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setVersion("0.1");
    }

    @Override
    protected void initInput() {

        getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).moveLeft();
            }
        }, KeyCode.A, VirtualButton.LEFT);

        getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).moveRight();
            }

        }, KeyCode.D, VirtualButton.RIGHT);

        getInput().addAction(new UserAction("Up") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).moveUp();
            }

        }, KeyCode.W, VirtualButton.UP);

        getInput().addAction(new UserAction("Down") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).moveDown();
            }

        }, KeyCode.S, VirtualButton.DOWN);

        getInput().addAction(new UserAction("Shoot") {
            @Override
            protected void onActionBegin() {
                spawn("bullet", player.getX() + 23, player.getY() + 23);
                play("pistol.wav");
            }
        }, KeyCode.SPACE);

    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0, 0);
        Body body = entityFactory.getSharedDynamicPhysics().getBody();
        body.setFixedRotation(true);
        body.setLinearDamping(0.1f);
    }

    @Override
    protected void initGame() {

        entityFactory = new TimeWarpEntityFactory();
        getGameWorld().addEntityFactory(entityFactory);

        int halfWidth = getAppWidth() / 2;
        int halfHeight = getAppHeight() / 2;

        setLevelFromMap("main.tmx");
        player = spawn("player");

        Viewport viewport = getGameScene().getViewport();
        viewport.bindToEntity(player, halfWidth , halfHeight);

    }

    public void run(String[] commandLineArgs) {
        launch(commandLineArgs);
    }

}
