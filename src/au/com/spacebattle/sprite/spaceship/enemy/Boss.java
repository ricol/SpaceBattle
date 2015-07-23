/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite.spaceship.enemy;

import au.com.rmit.Game2dEngine.action.Action;
import au.com.rmit.Game2dEngine.action.AlphaToAction;
import au.com.rmit.Game2dEngine.action.ExpandByAction;
import au.com.rmit.Game2dEngine.node.Sprite;
import au.com.spacebattle.common.Common;
import au.com.spacebattle.scene.SpaceShipScene;
import au.com.spacebattle.sprite.missile.BossAutoFollowMissile;
import au.com.spacebattle.sprite.missile.BossMainWeaponMissile;
import au.com.spacebattle.sprite.missile.EnemyAutoFollowMissile;
import au.com.spacebattle.sprite.missile.FriendAutoFollowMissile;
import au.com.spacebattle.sprite.missile.FriendLaserWeapon;
import au.com.spacebattle.sprite.missile.MainWeapanFriendMissile;
import au.com.spacebattle.sprite.missile.Missile;
import au.com.spacebattle.sprite.missile.NormalWeanponFriendMissile;
import au.com.spacebattle.sprite.other.EnemyFire;
import au.com.spacebattle.sprite.other.ExpodeParticle;
import au.com.spacebattle.sprite.spaceship.friend.MySpaceship;
import static com.sun.org.apache.xalan.internal.lib.ExsltMath.power;
import java.awt.event.ActionEvent;
import static java.lang.Math.abs;
import javax.swing.Timer;

/**
 *
 * @author ricolwang
 */
public class Boss extends Enemy
{

    protected Timer theTimerForMainWealpon = new Timer(3000, this);

    public Boss()
    {
        super("PlaneBoss.png");

        float num = abs(theRandom.nextInt()) % 50;
        float time = abs(theRandom.nextInt()) % 2 + 1;
        ExpandByAction aAction = new ExpandByAction();
        aAction.expandBy(num, time);
        aAction.identifer = "ActionExpand";
        this.addAction(aAction);

        this.layer = Common.LAYER_BOSS_SHIP;
        this.resetTotalLife(500);
        this.theTimerForMainWealpon.start();
    }

    @Override
    public void onActionComplete(Action theAction)
    {
        super.onActionComplete(theAction);

        if (theAction.identifer.equals("ActionExpand"))
        {
            if (theAction instanceof ExpandByAction)
            {
                ExpandByAction action = (ExpandByAction) theAction;
                ExpandByAction aAction = new ExpandByAction();
                float value = action.getExpandBy();
                float time = action.getExpandByDuration();
                aAction.expandBy(-action.getExpandBy(), action.getExpandByDuration() / 1000.0f);
                aAction.identifer = "ActionExpandBack";
                this.addAction(aAction);
            }
        }
    }

    @Override
    public void onCollideWith(Sprite target)
    {
        if (target instanceof MainWeapanFriendMissile)
        {
            this.decreaseLife(100);
        } else if (target instanceof NormalWeanponFriendMissile)
        {
            this.decreaseLife(20);
        } else if (target instanceof MySpaceship)
        {
            this.decreaseLife(200);
        } else if (target instanceof FriendLaserWeapon)
        {
            this.decreaseLife(20);
        } else if (target instanceof FriendAutoFollowMissile)
        {
            this.decreaseLife(100);
        }

        if (this.isAlive() == false)
        {
            if (this.theScene instanceof SpaceShipScene)
            {
                ((SpaceShipScene) this.theScene).killABoss();
            }
        }
    }

    @Override
    public void onDead()
    {
        super.onDead(); //To change body of generated methods, choose Tools | Templates.
        this.theTimerForMainWealpon.stop();
    }

    @Override
    public void explode()
    {
        int number = abs(theRandom.nextInt()) % 10 + 20;

        for (int i = 0; i < number; i++)
        {
            double tmpX = power(-1, theRandom.nextInt() % 10) * theRandom.nextFloat() * Common.SPEED_EXPLODE_PARTICLE * 2;
            double tmpY = power(-1, theRandom.nextInt() % 10) * theRandom.nextFloat() * Common.SPEED_EXPLODE_PARTICLE * 2;

            ExpodeParticle aFire = new ExpodeParticle();
            aFire.setX(this.getCentreX());
            aFire.setY(this.getCentreY());
            aFire.setVelocityX(tmpX);
            aFire.setVelocityY(tmpY);
            aFire.setRed(255);
            aFire.setGreen(0);
            aFire.setBlue(0);
            aFire.bDeadIfNoActions = true;

            AlphaToAction aAction = new AlphaToAction(aFire);
            aAction.alphaTo(0, 0.2f);
            aFire.addAction(aAction);

            this.theScene.addSprite(aFire);
        }
    }

    void fireMainWeapon()
    {
        Missile aMissile = new BossMainWeaponMissile("red-enemy-missile.png");
//        aMissile.bDrawFrame = true;

        aMissile.setX(this.getCentreX());
        aMissile.setY(this.getCentreY() + this.getHeight() / 2);

//        aMissile.setAngle(this.getAngle());
//        aMissile.setVelocityX(Common.SPEED_MAIN_MISSILE_ENEMY * Math.sin(-aMissile.getAngle()));
        aMissile.setVelocityY(Common.SPEED_MAIN_MISSILE_ENEMY);

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

    @Override
    public void fireAutoFollowMissile()
    {
        BossAutoFollowMissile aMissile = new BossAutoFollowMissile();
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
        super.actionPerformed(e); //To change body of generated methods, choose Tools | Templates.

        if (e.getSource().equals(this.theTimerForMainWealpon))
        {
            if (theRandom.nextBoolean())
            {
                this.fireMainWeapon();
            }
        }
    }
}
