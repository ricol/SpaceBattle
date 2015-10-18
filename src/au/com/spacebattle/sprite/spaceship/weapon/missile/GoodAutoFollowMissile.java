/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite.spaceship.weapon.missile;

import au.com.rmit.Game2dEngine.math.vector.Vector;
import au.com.spacebattle.common.Common;
import au.com.spacebattle.scene.SpaceShipScene;
import au.com.spacebattle.sprite.basic.MovingObject;

/**
 *
 * @author ricolwang
 */
public class GoodAutoFollowMissile extends AutoFollowMissile
{

    public GoodAutoFollowMissile(String imagename)
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

        if (!theShip.isAlive())
        {
            if (theScene instanceof SpaceShipScene)
            {
                SpaceShipScene theSpaceScene = (SpaceShipScene) this.theScene;
                BossAutoFollowMissile aBossMissile = theSpaceScene.getARandomBossMissile();
                if (aBossMissile != null)
                {
                    this.theTarget = aBossMissile;
                } else
                {
                    EnemyAutoFollowMissile aEnemyMissile = theSpaceScene.getARandomEnemyMissile();
                    if (aEnemyMissile != null)
                    {
                        this.theTarget = aEnemyMissile;
                    } else
                    {
                        this.theTarget = theSpaceScene.getARandomTarget();
                    }
                }
            }
        }

        if (theShip == null)
        {
            return;
        }

        if (!theShip.isAlive())
            return;

        //adjust gesture
        double targetCentreX = theShip.getCentreX();
        double targetCentreY = theShip.getCentreY();
        double changeX = targetCentreX - this.getCentreX();
        double changeY = targetCentreY - this.getCentreY();

        Vector DISPLACEMENT = new Vector(changeX, changeY);
        Vector UNIT_DISPLACEMENT = DISPLACEMENT.getTheUnitVector();

        double velocityAbsolute = Common.SPEED_MISSILE_FRIEND;
        Vector VELOCITY = UNIT_DISPLACEMENT.multiplyNumber(velocityAbsolute);
        this.setVelocityX(VELOCITY.x);
        this.setVelocityY(VELOCITY.y);
        Vector Y = new Vector(0, -1);
        double angle = VELOCITY.getCosValueForAngleToVector(Y);
        angle = Math.acos(angle);
        if (changeX <= 0)
            angle = -angle;
        this.setAngle(angle);
    }
}
