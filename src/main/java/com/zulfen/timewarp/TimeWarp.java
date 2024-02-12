package com.zulfen.timewarp;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.entity.level.tiled.TMXLevelLoader;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import com.almasb.fxgl.physics.CollisionHandler;
import com.zulfen.timewarp.entity.TimeWarpEntities;
import com.zulfen.timewarp.entity.TimeWarpEntityFactory;
import com.zulfen.timewarp.entity.controllers.MonsterComponent;
import com.zulfen.timewarp.entity.controllers.PlayerComponent;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.almasb.fxgl.dsl.FXGL.*;

// for times sake, putting most of the logic here.
public class TimeWarp extends GameApplication {

    private Entity player;
    private TimeWarpEntityFactory entityFactory;

    private static final int ENEMY_AMOUNT = 25;
    private final ArrayList<Entity> monsters = new ArrayList<>(ENEMY_AMOUNT);

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Timewarp");
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setVersion("0.1");
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("score", 0);
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

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(TimeWarpEntities.PLAYER, TimeWarpEntities.MONSTER) {
            @Override
            protected void onCollision(Entity a, Entity b) {
                getDialogService().showMessageBox("you died lol", () -> getGameController().exit());
            }
        });

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(TimeWarpEntities.BULLET, TimeWarpEntities.MONSTER) {
            @Override
            protected void onCollision(Entity a, Entity b) {
                inc("score", 1);
                monsters.remove(b);
                b.removeFromWorld();
            }
        });

    }

    @Override
    protected void initGame() {

        entityFactory = new TimeWarpEntityFactory();
        getGameWorld().addEntityFactory(entityFactory);

        int halfWidth = getAppWidth() / 2;
        int halfHeight = getAppHeight() / 2;

        Level level = getAssetLoader().loadLevel("main.tmx", new TMXLevelLoader());
        int levelWidth = level.getWidth();
        int levelHeight = level.getHeight();
        getGameWorld().setLevel(level);

        player = spawn("player");
        List<Entity> entities = getGameWorld().getEntities();

        Viewport viewport = getGameScene().getViewport();
        viewport.bindToEntity(player, halfWidth , halfHeight);

        int index = 0;
        Random random = new Random();

        while (index < ENEMY_AMOUNT) {
            int randomX = random.nextInt(levelWidth);
            int randomY = random.nextInt(levelHeight);
            if (isValidSpawn(randomX, randomY, entities)) {
                monsters.add(spawn("monster", randomX, randomY));
                index++;
            }
        }

        getGameTimer().runAtInterval(() -> {

            for (Entity monster : monsters) {
                MonsterComponent monsterComponent = monster.getComponent(MonsterComponent.class);
                monsterComponent.moveRandom();
            }

        }, Duration.seconds(2));

        getGameTimer().runAtInterval(() -> {
            getDialogService().showMessageBox("you killed " + geti("score") + " monsters in 20 seconds... nice one c:");
        }, Duration.seconds(20));

    }

    private boolean isValidSpawn(int x, int y, List<Entity> entities) {

        for (Entity entity : entities) {
            double entityWidth = entity.getWidth();
            double entityHeight = entity.getHeight();
            double entityX = entity.getX();
            double entityY = entity.getY();
            if ((x < (entityX - entityWidth) + 10) && (y < (entityY - entityHeight) + 10)) {
                return true;
            }
        }

        return false;

    }

    public void run(String[] commandLineArgs) {
        launch(commandLineArgs);
    }

}
