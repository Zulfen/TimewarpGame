package com.zulfen.timewarp.entity.controllers;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;

import java.util.Random;

public class MonsterComponent extends Component {

    private PhysicsComponent physicsComponent;

    // called every 2 seconds
    public void moveRandom() {

        Random random = new Random();
        double angle = random.nextDouble() * 2 * Math.PI;
        double forceMagnitude = 1000.0;

        double fx = Math.cos(angle) * forceMagnitude;
        double fy = Math.sin(angle) * forceMagnitude;

        physicsComponent.applyForceToCenter(new Point2D(fx, fy));

    }

}
