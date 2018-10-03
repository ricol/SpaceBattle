/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite.spaceship.weapon.missile;

import au.com.rmit.math.vector.Vector;
import au.com.spacebattle.common.Common;
import au.com.spacebattle.sprite.basic.MovingObject;

/**
 *
 * @author ricolwang
 */
public class BadAutoFollowMissile extends AutoFollowMissile
{

    public BadAutoFollowMissile(String imagename)
    {
        super(imagename);
    }

    @Override
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

            Vector DISPLACEMENT = new Vector(changeX, changeY);
            Vector UNIT_DISPLACEMENT = DISPLACEMENT.getTheUnitVector();

            double velocityAbsolute = Common.SPEED_MISSILE_ENEMY;
            Vector VELOCITY = UNIT_DISPLACEMENT.multiplyNumber(velocityAbsolute);
            this.setVelocityX(VELOCITY.x);
            this.setVelocityY(VELOCITY.y);
            Vector Y = new Vector(0, -1);
            double angle = VELOCITY.getTheNegativeVector().getCosValueForAngleToVector(Y);
            angle = Math.acos(angle);
            if (changeX > 0)
            {
                angle = Math.PI * 2 - angle;
            }
            this.setAngle(angle);
        }
    }

}
