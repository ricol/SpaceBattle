/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite.spaceship.weapon.missile;

import au.com.rmit.Game2dEngine.action.AlphaToAction;
import au.com.spacebattle.common.Common;
import au.com.spacebattle.sprite.basic.MovingObject;
import au.com.spacebattle.sprite.other.ExpodeParticle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Math.abs;
import static java.lang.Math.pow;
import javax.swing.Timer;

/**
 *
 * @author ricolwang
 */
public class AutoFollowMissile extends Missile implements ActionListener
{

    public MovingObject theTarget;
    int times = 30;
    int currentTimers = 0;
    Timer theTimer = new Timer(50, this);

    public AutoFollowMissile(String imagename)
    {
        super(imagename);
        this.setLifeTime(5);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (this.getShouldDie()) return;
        
        if (e.getSource().equals(this.theTimer))
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
                this.setAngle(-delta);
                //adjust velocity
                this.setVelocityX(Common.SPEED_MISSILE_ENEMY * Math.sin(delta));
                this.setVelocityY(Common.SPEED_MISSILE_ENEMY * Math.cos(delta));
            } else
            {
                double delta = Math.asin(changeX / distance);
                this.setAngle(delta + Math.PI);
                //adjust velocity
                this.setVelocityX(Common.SPEED_MISSILE_ENEMY * Math.sin(delta));
                this.setVelocityY(-Common.SPEED_MISSILE_ENEMY * Math.cos(delta));
            }
        }
    }

    public void fire()
    {
        this.theTimer.start();
    }

    @Override
    public void onWillDead()
    {
        super.onWillDead(); //To change body of generated methods, choose Tools | Templates.

        this.theTimer.stop();
        this.explode();
    }

    @Override
    protected void explode()
    {
        int number = abs(theRandom.nextInt()) % 10 + 30;

        for (int i = 0; i < number; i++)
        {
            double tmpX = pow(-1, theRandom.nextInt() % 10) * theRandom.nextFloat() * Common.SPEED_EXPLODE_PARTICLE;
            double tmpY = pow(-1, theRandom.nextInt() % 10) * theRandom.nextFloat() * Common.SPEED_EXPLODE_PARTICLE;

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

            if (this.theScene == null)
            {
                break;
            }
            this.theScene.addSprite(aFire);
        }
    }
}
