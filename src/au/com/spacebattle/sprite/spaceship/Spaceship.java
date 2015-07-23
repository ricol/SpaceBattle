/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite.spaceship;

import au.com.rmit.Game2dEngine.action.AlphaToAction;
import au.com.rmit.Game2dEngine.node.MovingSprite;
import au.com.spacebattle.common.Common;
import au.com.spacebattle.sprite.missile.BossAutoFollowMissile;
import au.com.spacebattle.sprite.other.EnemyFire;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author ricolwang
 */
public class Spaceship extends MovingSprite
{

    private int totalLife = 100;
    private int currentLife = totalLife;

    public Spaceship(double x, double y, double width, double height, double mass, double velocityX, double velocityY)
    {
        super(x, y, width, height, mass, velocityX, velocityY);
        this.lifetime = 20;
    }

    public Spaceship(String imagename)
    {
        super(0, 0, 0, 0, 0, 0, 0);
        this.lifetime = 20;
        BufferedImage aImage;

        try
        {
            aImage = ImageIO.read(new File(imagename));
            this.setWidth(aImage.getWidth());
            this.setHeight(aImage.getHeight());

            this.setImage(imagename);
        } catch (IOException e)
        {

        }
    }

    public void fire()
    {

    }
    
    public void fireAutoFollowMissile()
    {
        
    }

    public void decreaseLife(int value)
    {
        AlphaToAction aAction = new AlphaToAction(this);
        aAction.alphaTo((this.currentLife - value) / (this.totalLife * 1.0f), 0);
        this.addAction(aAction);
        this.currentLife -= value;
        if (this.currentLife <= 0)
        {
            this.setDead();
        }
    }

    public int getLife()
    {
        return currentLife;
    }

    public void resetTotalLife(int life)
    {
        this.totalLife = life;
        this.currentLife = life;
    }

    public void explode()
    {

    }

    @Override
    public void onDead()
    {
        super.onDead(); //To change body of generated methods, choose Tools | Templates.

        this.explode();
    }
}
