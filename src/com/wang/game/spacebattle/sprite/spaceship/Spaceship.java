/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.game.spacebattle.sprite.spaceship;

import com.wang.Game2dEngine.Cache.ResourceCache;
import com.wang.Game2dEngine.action.AlphaToAction;
import com.wang.Game2dEngine.scene.Layer;
import com.wang.Game2dEngine.sprite.other.LifeBar;
import com.wang.game.spacebattle.common.Common;
import com.wang.game.spacebattle.sprite.basic.CircleShapeMovingObject;
import com.wang.game.spacebattle.sprite.other.ExpodeParticle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import static java.lang.Math.abs;
import static java.lang.Math.pow;
import javax.swing.Timer;

/**
 *
 * @author ricolwang
 */
public class Spaceship extends CircleShapeMovingObject implements ActionListener
{

    private LifeBar theLifeBar;
    protected boolean bShowLifeBar = true;
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

        aImage = ResourceCache.getSharedInstance().getImage(imagename);
        this.setWidth(aImage.getWidth());
        this.setHeight(aImage.getHeight());

        this.setImage(imagename);

        theTimerForEngine.start();

    }

    @Override
    public void onAddToLayer(Layer theLayer)
    {
        super.onAddToLayer(theLayer); //To change body of generated methods, choose Tools | Templates.

        if (this.theLifeBar == null && this.bShowLifeBar)
        {
            this.theLifeBar = new LifeBar((int) this.getWidth(), 5, this.totalLife);
            this.theLifeBar.setCentreX(this.getCentreX());
            this.theLifeBar.setY(this.getY() + this.getHeight() + 20);
            this.addAttached(this.theLifeBar);
        }
    }

    public void decreaseLife(int value)
    {
//        AlphaToAction aAction = new AlphaToAction(this);
//        aAction.alphaTo((this.currentLife - value) / (this.totalLife * 1.0f), 0);
//        this.addAction(aAction);
        this.currentLife -= value;
        if (this.currentLife <= 0)
        {
            this.setShouldDie();
        }

        this.theLifeBar.decreaseLifeBy(value);
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

        this.theTimerForEngine.stop();

        this.explode();
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
            aFire.setRed(abs(theRandom.nextInt()) % 255);
            aFire.setGreen(abs(theRandom.nextInt()) % 255);
            aFire.setBlue(abs(theRandom.nextInt()) % 255);
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
        if (this.getShouldDie())
        {
            return;
        }

        if (e.getSource().equals(this.theTimerForEngine))
        {
            engine();
        }
    }
}
