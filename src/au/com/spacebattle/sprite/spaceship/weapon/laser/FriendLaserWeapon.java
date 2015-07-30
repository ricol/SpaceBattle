/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite.spaceship.weapon.laser;

import au.com.spacebattle.common.Common;
import au.com.spacebattle.sprite.basic.RectangleShapeMovingObject;
import au.com.spacebattle.sprite.spaceship.Spaceship;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static java.lang.Math.abs;
import javax.imageio.ImageIO;

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
    }

    public FriendLaserWeapon(String imagename)
    {
        super(0, 0, 0, 0, 0, 0, 0);

        BufferedImage aImage;

        try
        {
            aImage = ImageIO.read(new File(imagename));
            int max = aImage.getWidth();
            if (aImage.getWidth() < aImage.getHeight())
            {
                max = aImage.getHeight();
            }
            this.setWidth(max);
            this.setHeight(max);

            this.setImage(imagename);
        } catch (IOException e)
        {

        }

        this.setLifeTime(1);

        this.bCollisionDetect = true;
        this.setCollisionCategory(Common.CATEGORY_FRIEND_SHIP);
        this.addTargetCollisionCategory(Common.CATEGORY_ENEMY_SHIP);
    }

    @Override
    public void onCustomDraw(Graphics2D theGraphics2D)
    {
        Color blackTransparent = new Color(0, 0, 0, 0);
        theGraphics2D.setColor(blackTransparent);
        theGraphics2D.fillRect(0, 0, (int) getWidth(), (int) getHeight());
        theGraphics2D.setColor(getColor());
        theGraphics2D.fillRect(0, 0, (int) getWidth(), (int) getHeight());
    }

}
