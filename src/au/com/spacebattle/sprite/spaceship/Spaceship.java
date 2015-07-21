/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite.spaceship;

import au.com.rmit.Game2dEngine.node.MovingSprite;
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
    int life = 100;
    
    public Spaceship(double x, double y, double width, double height, double mass, double velocityX, double velocityY)
    {
        super(x, y, width, height, mass, velocityX, velocityY);
    }

    public Spaceship(String imagename)
    {
        super(0, 0, 0, 0, 0, 0, 0);

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

    public void fire(String imagename)
    {

    }

    public void increaseLife(int value)
    {
        this.life += value;
    }
    
    public void decreaseLife(int value)
    {
        this.life -= value;
        if (this.life <= 0) 
        {
            this.setDead();
        }
    }
    
    public void setLife(int life)
    {
        this.life = life;
    }
    
    public int getLife()
    {
        return life;
    }
}
