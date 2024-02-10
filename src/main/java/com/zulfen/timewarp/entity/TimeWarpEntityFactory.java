package com.zulfen.timewarp.entity;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
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

}
