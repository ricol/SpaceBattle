/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite.missile;

import au.com.rmit.Game2dEngine.action.AlphaToAction;
import au.com.spacebattle.common.Common;
import au.com.spacebattle.sprite.other.ExpodeParticle;
import au.com.spacebattle.sprite.spaceship.Spaceship;
import static com.sun.org.apache.xalan.internal.lib.ExsltMath.power;
import static java.lang.Math.abs;

/**
 *
 * @author ricolwang
 */
public class FriendAutoFollowMissile extends AutoFollowMissile
{

    public FriendAutoFollowMissile()
    {
        super("nuclear.png");

        this.lifetime = 5;
        this.times = 50;
        this.bCollisionDetect = true;
        this.collisionCategory = Common.CATEGORY_FRIEND_SHIP;
        this.collisionTargetCategory = Common.CATEGORY_ENEMY_SHIP;
    }

    @Override
    public void explode()
    {
        int number = abs(theRandom.nextInt()) % 10 + 40;

        for (int i = 0; i < number; i++)
        {
            double tmpX = power(-1, theRandom.nextInt() % 10) * theRandom.nextFloat() * Common.SPEED_EXPLODE_PARTICLE;
            double tmpY = power(-1, theRandom.nextInt() % 10) * theRandom.nextFloat() * Common.SPEED_EXPLODE_PARTICLE;

            ExpodeParticle aFire = new ExpodeParticle();
            aFire.setX(this.getCentreX());
            aFire.setY(this.getCentreY());
            aFire.setVelocityX(tmpX);
            aFire.setVelocityY(tmpY);
            aFire.setRed(0);
            aFire.setGreen(0);
            aFire.setBlue(255);
            aFire.bDeadIfNoActions = true;

            AlphaToAction aAction = new AlphaToAction(aFire);
            aAction.alphaTo(0, 0.5f);
            aFire.addAction(aAction);

            this.theScene.addSprite(aFire);
        }
    }
    
    @Override
    public void adjustGesture(Spaceship theShip)
    {
        if (theShip == null)
        {
            return;
        }

        //adjust gesture
        double targetCentreX = theShip.getCentreX();
        double targetCentreY = theShip.getCentreY();
        double changeX = targetCentreX - this.getCentreX();
        double changeY = targetCentreY - this.getCentreY();
        double distance = Math.sqrt(changeX * changeX + changeY * changeY);
        double delta = Math.asin(changeX / distance);

        this.angle = delta;

        //adjust velocity
        this.setVelocityX(Common.SPEED_MISSILE_FRIEND * Math.sin(this.getAngle()));
        this.setVelocityY(-Common.SPEED_MISSILE_FRIEND * Math.cos(this.getAngle()));
    }
}
