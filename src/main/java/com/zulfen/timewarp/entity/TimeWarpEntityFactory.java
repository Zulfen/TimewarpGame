package com.zulfen.timewarp.entity;

import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.sun.prism.paint.Paint;
import com.zulfen.timewarp.entity.controllers.PlayerComponent;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGL.*;

public class TimeWarpEntityFactory implements EntityFactory {

    private final PhysicsComponent sharedDynamicPhysics = new PhysicsComponent(); {
        sharedDynamicPhysics.setBodyType(BodyType.DYNAMIC);
        FixtureDef fixtureDef = new FixtureDef()
                .restitution(0.15f)
                .friction(0.0f);
        sharedDynamicPhysics.setFixtureDef(fixtureDef);
    }

    public PhysicsComponent getSharedDynamicPhysics() {
        return sharedDynamicPhysics;
    }

    @Spawns("player")
    public Entity newPlayer(SpawnData spawnData) {
        return entityBuilder(spawnData)
                .at(getAppCenter())
                .type(TimeWarpEntities.PLAYER)
                .viewWithBBox("playeridle.png")
                .anchorFromCenter()
                .with(sharedDynamicPhysics)
                .with(new PlayerComponent())
                .collidable()
                .build();
    }

    @Spawns("wall")
    public Entity newWall(SpawnData spawnData) {
        PhysicsComponent staticPhysics = new PhysicsComponent();
        staticPhysics.setBodyType(BodyType.STATIC);
        return entityBuilder(spawnData)
                .bbox(new HitBox(BoundingShape.box(spawnData.<Integer>get("width"), spawnData.<Integer>get("height"))))
                .type(TimeWarpEntities.WALL)
                .with(staticPhysics)
                .collidable()
                .build();
    }

    @Spawns("bullet")
    public Entity newBullet(SpawnData spawnData) {
        ProjectileComponent projectileComponent = new ProjectileComponent(new Point2D(1, 0), 400);
        projectileComponent.allowRotation(false);
        return entityBuilder(spawnData)
                .viewWithBBox(new Rectangle(10, 3, Color.BLUE))
                .type(TimeWarpEntities.BULLET)
                .with(projectileComponent)
                .collidable()
                .build();
    }

}
