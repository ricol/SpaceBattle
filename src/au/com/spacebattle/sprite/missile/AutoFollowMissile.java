/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite.missile;

import au.com.rmit.Game2dEngine.action.AlphaToAction;
import au.com.spacebattle.common.Common;
import au.com.spacebattle.common.MovingObject;
import au.com.spacebattle.sprite.other.ExpodeParticle;
import static com.sun.org.apache.xalan.internal.lib.ExsltMath.power;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Math.abs;
import javax.swing.Timer;

/**
 *
 * @author ricolwang
 */
public class AutoFollowMissile extends Missile implements ActionListener
{

    public MovingObject theTarget;
    int times = 10;
    int currentTimers = 0;
    Timer theTimer = new Timer(100, this);

    public AutoFollowMissile(double x, double y, double width, double height, double mass, double velocityX, double velocityY)
    {
        super(x, y, width, height, mass, velocityX, velocityY);
    }

    public AutoFollowMissile(String imagename)
    {
        super(imagename);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (theTarget != null)
        {
            if (currentTimers < times)
            {
                currentTimers++;

                //adjust
                this.adjustGesture(theTarget);
            }
        }
    }

    public void adjustGesture(MovingObject theShip)
    {
        if (theShip == null)
        {
            return;
        }

        if (theShip.isAlive())
        {
            //adjust gesture
            double targetCentreX = theShip.getCentreX();
            double targetCentreY = theShip.getCentreY();
            double changeX = targetCentreX - this.getCentreX();
            double changeY = targetCentreY - this.getCentreY();
            double distance = Math.sqrt(changeX * changeX + changeY * changeY);

            if (changeY >= 0)
            {
                double delta = Math.asin(changeX / distance);
                this.angle = -delta;
                //adjust velocity
                this.setVelocityX(Common.SPEED_MISSILE_ENEMY * Math.sin(delta));
                this.setVelocityY(Common.SPEED_MISSILE_ENEMY * Math.cos(delta));
            } 
//            else
//            {
//                double delta = Math.asin(changeX / distance);
////                this.angle = Math.PI * 2 - delta - Math.PI / 2.0f;
//                //adjust velocity
//                this.setVelocityX(Common.SPEED_MISSILE_ENEMY * Math.cos(delta));
//                this.setVelocityY(Common.SPEED_MISSILE_ENEMY * Math.sin(delta));
//            }
        }
    }

    public void fire()
    {
        this.theTimer.start();
    }

    @Override
    public void onDead()
    {
        super.onDead(); //To change body of generated methods, choose Tools | Templates.

        this.explode();
    }

    @Override
    public void explode()
    {
        int number = abs(theRandom.nextInt()) % 10 + 30;

        for (int i = 0; i < number; i++)
        {
            double tmpX = power(-1, theRandom.nextInt() % 10) * theRandom.nextFloat() * Common.SPEED_EXPLODE_PARTICLE;
            double tmpY = power(-1, theRandom.nextInt() % 10) * theRandom.nextFloat() * Common.SPEED_EXPLODE_PARTICLE;

            ExpodeParticle aFire = new ExpodeParticle();
            aFire.setX(this.getCentreX());
            aFire.setY(this.getCentreY());
            aFire.setVelocityX(tmpX);
            aFire.setVelocityY(tmpY);
            aFire.setRed(255);
            aFire.setGreen(255);
            aFire.setBlue(255);
            aFire.bDeadIfNoActions = true;

            AlphaToAction aAction = new AlphaToAction(aFire);
            aAction.alphaTo(0, 0.5f);
            aFire.addAction(aAction);

            this.theScene.addSprite(aFire);
        }
    }
}
