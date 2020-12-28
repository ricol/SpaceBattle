/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.game.spacebattle.sprite.spaceship.weapon.laser;

import com.wang.Game2dEngine.Shape.ESpecialRectangleShape;
import com.wang.Game2dEngine.painter.interfaces.IEngineGraphics;
import com.wang.game.spacebattle.common.Common;
import com.wang.game.spacebattle.sprite.basic.RectangleShapeMovingObject;
import com.wang.game.spacebattle.sprite.spaceship.Spaceship;
import java.awt.Color;
import static java.lang.Math.abs;

/**
 *
 * @author ricolwang
 */
public class FriendLaserWeapon extends RectangleShapeMovingObject
{

    public static final int HEIGHT = 1000;
    public static final int WIDTH = 20;

    public FriendLaserWeapon(Spaceship ship)
    {
        super(0, 0, 0, HEIGHT, 0, 0, 0);
        this.setRed(abs(theRandom.nextInt()) % 255);
        this.setGreen(abs(theRandom.nextInt()) % 255);
        this.setBlue(abs(theRandom.nextInt()) % 255);
        this.setWidth(WIDTH * abs(theRandom.nextFloat()) + 5);
        this.setCentreX(ship.getCentreX());
        this.setCentreY(ship.getCentreY() - this.getHeight() / 2 - ship.getHeight() / 2);

        this.setLayer(ship.getLayer());
        this.setLifeTime(0.01f);

        this.bCustomDrawing = true;

        this.bCollisionDetect = true;
        this.setCollisionCategory(Common.CATEGORY_FRIEND_SHIP);
        this.addTargetCollisionCategory(Common.CATEGORY_ENEMY_SHIP);
        this.setTheShape(new ESpecialRectangleShape(0, 0, 0, 0));
    }

    @Override
    public void onCustomDraw(IEngineGraphics theEngineGraphics)
    {
        Color blackTransparent = new Color(0, 0, 0, 0);
        theEngineGraphics.setColor(blackTransparent);
        theEngineGraphics.fillRect(0, 0, (int) getWidth(), (int) getHeight());
        theEngineGraphics.setColor(getColor());
        theEngineGraphics.fillRect(0, 0, (int) getWidth(), (int) getHeight());
    }

}
