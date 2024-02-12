package com.zulfen.timewarp.entity.controllers;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;

public class PlayerComponent extends Component {

    private PhysicsComponent physicsComponent;

    public void moveRight() {
        physicsComponent.applyForceToCenter(new Point2D(50, 0));
    }

    public void moveLeft() {
        physicsComponent.applyForceToCenter(new Point2D(-50, 0));
    }

    public void moveUp() {
        physicsComponent.applyForceToCenter(new Point2D(0, -50));
    }

    public void moveDown() {
        physicsComponent.applyForceToCenter(new Point2D(0, 50));
    }

}
