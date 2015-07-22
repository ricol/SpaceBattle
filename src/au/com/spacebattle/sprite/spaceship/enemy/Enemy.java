/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite.spaceship.enemy;

import au.com.rmit.Game2dEngine.action.Action;
import au.com.rmit.Game2dEngine.action.AlphaToAction;
import au.com.rmit.Game2dEngine.action.CountdownByAction;
import au.com.rmit.Game2dEngine.action.MoveXByAction;
import au.com.rmit.Game2dEngine.action.MoveYByAction;
import au.com.rmit.Game2dEngine.node.Sprite;
import au.com.spacebattle.common.Common;
import au.com.spacebattle.scene.SpaceShipScene;
import au.com.spacebattle.sprite.missile.EnemyMissile;
import au.com.spacebattle.sprite.missile.FriendLaserWeapon;
import au.com.spacebattle.sprite.missile.MainWeapanFriendMissile;
import au.com.spacebattle.sprite.missile.Missile;
import au.com.spacebattle.sprite.missile.NormalWeanponFriendMissile;
import au.com.spacebattle.sprite.other.EnemyFire;
import au.com.spacebattle.sprite.other.ExpodeParticle;
import au.com.spacebattle.sprite.spaceship.Spaceship;
import au.com.spacebattle.sprite.spaceship.friend.MySpaceship;
import static com.sun.org.apache.xalan.internal.lib.ExsltMath.power;
import static java.lang.Math.abs;

/**
 *
 * @author ricolwang
 */
public class Enemy extends Spaceship
{

    public Spaceship theTarget;

    public Enemy(String imagename)
    {
        super(imagename);

        CountdownByAction aAction = new CountdownByAction();
        aAction.CountdownBy(abs(theRandom.nextFloat()));
        aAction.identifer = "ActionCountdownForFireing";
        this.addAction(aAction);

        aAction = new CountdownByAction();
        aAction.CountdownBy(abs(theRandom.nextFloat()) * 3);
        aAction.identifer = "ActionCountdownForFireingBossWeapon";
        this.addAction(aAction);

        aAction = new CountdownByAction();
        aAction.CountdownBy(abs(theRandom.nextFloat()) * 2);
        aAction.identifer = "ActionCountdownForChangingSpeed";
        this.addAction(aAction);

        this.bCollisionDetect = true;
        this.collisionCategory = Common.CATEGORY_ENEMY_SHIP;
        this.collisionTargetCategory = Common.CATEGORY_FRIEND_SHIP;

        this.layer = Common.LAYER_ENEMY_SHIP;
    }

    @Override
    public void onActionComplete(Action theAction)
    {
        if (theAction.identifer.equals("ActionCountdownForFireing"))
        {
            //fire
            Missile aMissile = new EnemyMissile("enemy-missile.png");
            aMissile.setX(this.getCentreX() - aMissile.getWidth() / 2);
            aMissile.setY(this.getCentreY() + this.getHeight() / 2);
//            aMissile.setVelocityX(abs(theRandom.nextInt()) % 50 + 50);
            aMissile.setVelocityY(Common.SPEED_MISSILE_ENEMY);
            aMissile.layer = this.layer;
            this.theScene.addSprite(aMissile);

            EnemyFire aFire = new EnemyFire();
            aFire.setCentreX(aMissile.getCentreX());
            aFire.setCentreY(aMissile.getCentreY() + aMissile.getHeight() / 2);
            aFire.layer = this.layer;
            aFire.setVelocityX(this.velocityX);
            aFire.setVelocityY(this.velocityY);

            this.theScene.addSprite(aFire);

            CountdownByAction aNewAction = new CountdownByAction();
            aNewAction.CountdownBy(abs(theRandom.nextFloat()) * 2);
            aNewAction.identifer = "ActionCountdownForFireing";
            this.addAction(aNewAction);
        } else if (theAction.identifer.equals("ActionCountdownForChangingSpeed"))
        {
            //change speed
            float time = abs(theRandom.nextInt()) % 2 + 15;
            float valueX = abs(theRandom.nextInt()) % Common.SPEED_ENEMY_SHIP_CHANGE_X + Common.SPEED_ENEMY_SHIP_X;
            if (this.theTarget != null)
            {
                float x = (float) this.theTarget.getX();
                valueX = (float) (x - this.getX());
            }

            MoveXByAction aMoveXByAction = new MoveXByAction();
            aMoveXByAction.moveXBy(valueX, time);
            this.addAction(aMoveXByAction);

            float valueY = abs(theRandom.nextInt()) % Common.SPEED_ENEMY_SHIP_CHANGE_Y + Common.SPEED_ENEMY_SHIP_Y;
            if (this.theTarget != null)
            {
                float y = (float) this.theTarget.getY();
                valueY = (float) (y - this.getY());
            }

            MoveYByAction aMoveYByAction = new MoveYByAction();
            aMoveYByAction.moveYBy(valueY, time);
            this.addAction(aMoveYByAction);

            CountdownByAction aCountdownByAction = new CountdownByAction();
            aCountdownByAction.CountdownBy(time + abs(theRandom.nextFloat()) * 2);
            aCountdownByAction.identifer = "ActionCountdownForChangingSpeed";
            this.addAction(aCountdownByAction);
        }
    }

    @Override
    public void onDead()
    {
        super.onDead(); //To change body of generated methods, choose Tools | Templates.
        this.theTarget = null;
    }

    @Override
    public void onCollideWith(Sprite target)
    {
        if (target instanceof MainWeapanFriendMissile)
        {
            this.decreaseLife(200);
        } else if (target instanceof NormalWeanponFriendMissile)
        {
            this.decreaseLife(20);
        } else if (target instanceof MySpaceship)
        {
            this.decreaseLife(300);
        } else if (target instanceof FriendLaserWeapon)
        {
            this.decreaseLife(20);
        }
        
        if (this.isAlive() == false)
        {
            if (this.theScene instanceof SpaceShipScene)
            {
                ((SpaceShipScene) this.theScene).killAEnemy();
            }
        }
    }

    @Override
    public void explode()
    {
        int number = abs(theRandom.nextInt()) % 10 + 10;

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
            aFire.setGreen(255);
            aFire.setBlue(0);
            aFire.bDeadIfNoActions = true;

            AlphaToAction aAction = new AlphaToAction(aFire);
            aAction.alphaTo(0, 0.2f);
            aFire.addAction(aAction);

            this.theScene.addSprite(aFire);
        }
    }

}
