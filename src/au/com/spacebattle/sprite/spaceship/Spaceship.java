/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite.spaceship;

import au.com.rmit.Game2dEngine.action.AlphaToAction;
import au.com.spacebattle.common.Common;
import au.com.spacebattle.common.MovingObject;
import au.com.spacebattle.sprite.other.ExpodeParticle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static java.lang.Math.abs;
import static java.lang.Math.pow;
import javax.imageio.ImageIO;
import javax.swing.Timer;

/**
 *
 * @author ricolwang
 */
public class Spaceship extends MovingObject implements ActionListener
{

    private int totalLife = 100;
    private int currentLife = totalLife;

    Timer theTimerForEngine = new Timer(10, this);

    public Spaceship(double x, double y, double width, double height, double mass, double velocityX, double velocityY)
    {
        super(x, y, width, height, mass, velocityX, velocityY);
        this.setLifeTime(20);
        theTimerForEngine.start();
    }

    public Spaceship(String imagename)
    {
        super(0, 0, 0, 0, 0, 0, 0);
        this.setLifeTime(20);
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

        theTimerForEngine.start();
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
            this.setShouldDie();
        }
    }

    public int getCurrentLife()
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
    public void onWillDead()
    {
        super.onWillDead(); //To change body of generated methods, choose Tools | Templates.

        this.explode();
        this.theTimerForEngine.stop();
    }

    public void engine()
    {
        int number = abs(theRandom.nextInt()) % 5 + 10;

        for (int i = 0; i < number; i++)
        {
            double tmpX = pow(-1, theRandom.nextInt() % 10) * theRandom.nextFloat() * Common.SPEED_ENGINE_X * 4;
            double tmpY = theRandom.nextFloat() * Common.SPEED_ENGINE_Y * 10;

            int size = 6;
            ExpodeParticle aFire = new ExpodeParticle();
            aFire.setX(this.getCentreX() - size / 2);
            aFire.setY(this.getCentreY() + this.getHeight() / 2);
            aFire.setVelocityX(tmpX);
            aFire.setVelocityY(tmpY);
            aFire.setRed(255);
            aFire.setGreen(255);
            aFire.setBlue(255);
            aFire.setWidth(size);
            aFire.setHeight(size);
            aFire.bDeadIfNoActions = true;

            AlphaToAction aAction = new AlphaToAction(aFire);
            aAction.alphaTo(0, 0.1f);
            aFire.addAction(aAction);

            this.theScene.addSprite(aFire);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource().equals(this.theTimerForEngine))
        {
            engine();
        }
    }
}
