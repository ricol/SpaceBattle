/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.game.spacebattle.sprite.spaceship.weapon.missile;

import com.wang.Game2dEngine.Cache.ResourceCache;
import com.wang.Game2dEngine.sprite.Sprite;
import com.wang.game.spacebattle.sprite.basic.CircleShapeMovingObject;
import java.awt.image.BufferedImage;

/**
 *
 * @author ricolwang
 */
public class Missile extends CircleShapeMovingObject
{

    public Missile(double x, double y, double width, double height, double mass, double velocityX, double velocityY)
    {
        super(x, y, width, height, mass, velocityX, velocityY);

    }

    public Missile(String imagename)
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
    }

    @Override
    public void onCollideWith(Sprite target)
    {
        this.setShouldDie();
    }

    protected void explode()
    {

    }
}
