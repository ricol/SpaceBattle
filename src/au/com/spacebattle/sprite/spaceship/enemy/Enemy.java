/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite.spaceship.enemy;

import au.com.rmit.Game2dEngine.action.Action;
import au.com.rmit.Game2dEngine.action.AlphaToAction;
import au.com.rmit.Game2dEngine.node.Sprite;
import au.com.spacebattle.common.Common;
import au.com.spacebattle.scene.SpaceShipScene;
import au.com.spacebattle.sprite.missile.EnemyAutoFollowMissile;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Math.abs;
import javax.swing.Timer;

/**
 *
 * @author ricolwang
 */
public class Enemy extends Spaceship implements ActionListener
{

    public boolean bAutoAdjustGesture = false;

    protected Timer theTimerAutoadjust = new Timer(100, this);
    protected Timer theTimerFire = new Timer(3000, this);
    protected Timer theTimerForAutoFollowMissile = new Timer(5000, this);

    public Spaceship theTarget;

    public Enemy(String imagename)
    {
        super(imagename);

        this.bCollisionDetect = true;
        this.collisionCategory = Common.CATEGORY_ENEMY_SHIP;
        this.collisionTargetCategory = Common.CATEGORY_FRIEND_SHIP;

        this.layer = Common.LAYER_ENEMY_SHIP;
        this.theTimerAutoadjust.start();
        this.theTimerFire.start();
        this.theTimerForAutoFollowMissile.start();
    }

    @Override
    public void onActionComplete(Action theAction)
    {
    }

    @Override
    public void onDead()
    {
        super.onDead(); //To change body of generated methods, choose Tools | Templates.
        this.theTimerAutoadjust.stop();
        this.theTimerFire.stop();
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

    public void adjustGesture(Spaceship theShip)
    {
        if (theShip == null)
        {
            return;
        }

        double targetCentreX = theShip.getCentreX();
        double targetCentreY = theShip.getCentreY();
        double changeX = targetCentreX - this.getCentreX();
        double changeY = targetCentreY - this.getCentreY();
        double distance = Math.sqrt(changeX * changeX + changeY * changeY);
        double delta = Math.asin(changeX / distance);

        this.angle = -delta;
    }

    @Override
    public void fire()
    {
        Missile aMissile = new EnemyMissile("enemy-missile.png");
//        aMissile.bDrawFrame = true;
        aMissile.setX(this.getCentreX() - aMissile.getWidth() / 2);
        aMissile.setY(this.getCentreY() + this.getHeight() / 2);
//        aMissile.setAngle(this.angle);

//        aMissile.setVelocityX(Common.SPEED_MISSILE_ENEMY * Math.sin(-aMissile.getAngle()));
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
    }

    public void fireAutoFollowMissile()
    {
        EnemyAutoFollowMissile aMissile = new EnemyAutoFollowMissile();
        aMissile.theTarget = this.theTarget;
//        aMissile.bDrawFrame = true;
        aMissile.setX(this.getCentreX() - aMissile.getWidth() / 2);
        aMissile.setY(this.getCentreY() + this.getHeight() / 2);
        aMissile.setAngle(this.angle);

        aMissile.setVelocityX(Common.SPEED_MISSILE_ENEMY * Math.sin(-aMissile.getAngle()));
        aMissile.setVelocityY(Common.SPEED_MISSILE_ENEMY * Math.cos(-aMissile.getAngle()));

        aMissile.layer = this.layer;
        aMissile.fire();

        this.theScene.addSprite(aMissile);

        EnemyFire aFire = new EnemyFire();
        aFire.setCentreX(aMissile.getCentreX());
        aFire.setCentreY(aMissile.getCentreY() + aMissile.getHeight() / 2);
        aFire.layer = this.layer;
        aFire.setVelocityX(this.velocityX);
        aFire.setVelocityY(this.velocityY);

        this.theScene.addSprite(aFire);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource().equals(this.theTimerAutoadjust))
        {
            if (bAutoAdjustGesture)
            {
                adjustGesture(theTarget);
            }
        } else if (e.getSource().equals(this.theTimerFire))
        {
            fire();
        } else if (e.getSource().equals(this.theTimerForAutoFollowMissile))
        {
            if (abs(theRandom.nextInt()) % 100 > 80)
            {
                fireAutoFollowMissile();
            }
        }
    }
}
