/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite.missile;

import au.com.rmit.Game2dEngine.action.AlphaToAction;
import au.com.spacebattle.common.Common;
import au.com.spacebattle.common.MovingObject;
import au.com.spacebattle.scene.SpaceShipScene;
import au.com.spacebattle.sprite.other.ExpodeParticle;
import static java.lang.Math.abs;
import static java.lang.Math.pow;

/**
 *
 * @author ricolwang
 */
public class FriendAutoFollowMissile extends AutoFollowMissile
{

    public FriendAutoFollowMissile()
    {
        super("nuclear.png");

        this.setLifeTime(5);
        this.times = 100;
        this.bCollisionDetect = true;
        this.setCollisionCategory(Common.CATEGORY_FRIEND_SHIP);
        this.addTargetCollisionCategory(Common.CATEGORY_ENEMY_SHIP);
    }

    @Override
    protected void explode()
    {
        int number = abs(theRandom.nextInt()) % 10 + 40;

        for (int i = 0; i < number; i++)
        {
            double tmpX = pow(-1, theRandom.nextInt() % 10) * theRandom.nextFloat() * Common.SPEED_EXPLODE_PARTICLE;
            double tmpY = pow(-1, theRandom.nextInt() % 10) * theRandom.nextFloat() * Common.SPEED_EXPLODE_PARTICLE;

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

            if (this.theScene == null) break;
            this.theScene.addSprite(aFire);
        }
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
        
        if (!theShip.isAlive()) return;

        //adjust gesture
        double targetCentreX = theShip.getCentreX();
        double targetCentreY = theShip.getCentreY();
        double changeX = targetCentreX - this.getCentreX();
        double changeY = targetCentreY - this.getCentreY();
        double distance = Math.sqrt(changeX * changeX + changeY * changeY);

        //adjust velocity
        if (changeY <= 0)
        {
            double delta = Math.asin(changeX / distance);
            this.setAngle(delta);
            this.setVelocityX(Common.SPEED_MISSILE_FRIEND * Math.sin(this.getAngle()));
            this.setVelocityY(-Common.SPEED_MISSILE_FRIEND * Math.cos(this.getAngle()));
        } else
        {
            double delta = Math.acos(changeX / distance);
            this.setAngle(Math.PI / 2.0f + delta);
            this.setVelocityX(Common.SPEED_MISSILE_FRIEND * Math.cos(delta));
            this.setVelocityY(Common.SPEED_MISSILE_FRIEND * Math.sin(delta));
        }
    }

}
