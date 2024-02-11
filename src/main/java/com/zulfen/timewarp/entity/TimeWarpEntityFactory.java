package com.zulfen.timewarp.entity;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.geometry.Point2D;

import static com.almasb.fxgl.dsl.FXGL.*;

public class TimeWarpEntityFactory implements EntityFactory {

    @Spawns("player")
    public Entity newPlayer(SpawnData spawnData) {
        return entityBuilder(spawnData)
                .at(getAppCenter())
                .type(TimeWarpEntities.PLAYER)
                .viewWithBBox("playeridle.png")
                .anchorFromCenter()
                .collidable()
                .build();
    }

    @Spawns("wall")
    public Entity newWall(SpawnData spawnData) {
        return entityBuilder(spawnData)
                .bbox(new HitBox(BoundingShape.box(spawnData.getX(), spawnData.getY())))
                .type(TimeWarpEntities.WALL)
                .collidable()
                .build();
    }

}
