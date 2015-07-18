/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite;

import au.com.rmit.Game2dEngine.node.MovingSprite;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author ricolwang
 */
public class Missile extends MovingSprite
{

    public Missile(double x, double y, double width, double height, double mass, double velocityX, double velocityY)
    {
        super(x, y, width, height, mass, velocityX, velocityY);

    }

    public Missile(String imagename)
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

}
