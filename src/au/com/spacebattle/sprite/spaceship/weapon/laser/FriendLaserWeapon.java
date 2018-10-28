/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite.spaceship.weapon.laser;

import au.com.rmit.Game2dEngine.Cache.ResourceCache;
import au.com.rmit.Game2dEngine.Shape.ESpecialRectangleShape;
import au.com.rmit.Game2dEngine.painter.interfaces.IEngineGraphics;
import au.com.spacebattle.common.Common;
import au.com.spacebattle.sprite.basic.RectangleShapeMovingObject;
import au.com.spacebattle.sprite.spaceship.Spaceship;
import java.awt.Color;
import java.awt.image.BufferedImage;
import static java.lang.Math.abs;

/**
 *
 * @author ricolwang
 */
public class FriendLaserWeapon extends RectangleShapeMovingObject
{

    public static final int HEIGHT = 1000;
    public static final int WIDTH = 20;

    Spaceship theShip;

    public FriendLaserWeapon(Spaceship ship)
    {
        super(0, 0, 0, HEIGHT, 0, 0, 0);
        this.setRed(abs(theRandom.nextInt()) % 255);
        this.setGreen(abs(theRandom.nextInt()) % 255);
        this.setBlue(abs(theRandom.nextInt()) % 255);
        this.setWidth(WIDTH * abs(theRandom.nextFloat()) + 5);
        this.setCentreX(ship.getCentreX());
        this.setCentreY(ship.getCentreY() - this.getHeight() / 2 - ship.getHeight() / 2);
        theShip = ship;

        this.setLayer(ship.getLayer());
        this.setLifeTime(0.001f);

        this.bCustomDrawing = true;

        this.bCollisionDetect = true;
        this.setCollisionCategory(Common.CATEGORY_FRIEND_SHIP);
        this.addTargetCollisionCategory(Common.CATEGORY_ENEMY_SHIP);
        this.setTheShape(new ESpecialRectangleShape(0, 0, 0, 0));
    }

    public FriendLaserWeapon(String imagename)
    {
        super(0, 0, 0, 0, 0, 0, 0);

        BufferedImage aImage;

        aImage = ResourceCache.getSharedInstance().getImage(imagename);
        int max = aImage.getWidth();
        if (aImage.getWidth() < aImage.getHeight())
        {
            max = aImage.getHeight();
        }
        this.setWidth(max);
        this.setHeight(max);

        this.setImage(imagename);

        this.setLifeTime(1);

        this.bCollisionDetect = true;
        this.setCollisionCategory(Common.CATEGORY_FRIEND_SHIP);
        this.addTargetCollisionCategory(Common.CATEGORY_ENEMY_SHIP);
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
