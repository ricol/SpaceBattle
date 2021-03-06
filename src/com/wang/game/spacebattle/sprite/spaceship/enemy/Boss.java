/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.game.spacebattle.sprite.spaceship.enemy;

import com.wang.Game2dEngine.action.Action;
import com.wang.Game2dEngine.action.AlphaToAction;
import com.wang.Game2dEngine.action.ExpandByAction;
import com.wang.Game2dEngine.sprite.Sprite;
import com.wang.game.spacebattle.common.Common;
import com.wang.game.spacebattle.scene.SpaceShipScene;
import com.wang.game.spacebattle.sprite.other.ExpodeParticle;
import com.wang.game.spacebattle.sprite.spaceship.friend.MySpaceship;
import com.wang.game.spacebattle.sprite.spaceship.weapon.BossAlternativeWeapon;
import com.wang.game.spacebattle.sprite.spaceship.weapon.BossAutoMissileWeapon;
import com.wang.game.spacebattle.sprite.spaceship.weapon.BossMainWeapon;
import com.wang.game.spacebattle.sprite.spaceship.weapon.Weapon;
import com.wang.game.spacebattle.sprite.spaceship.weapon.laser.FriendLaserWeapon;
import com.wang.game.spacebattle.sprite.spaceship.weapon.missile.FriendAutoFollowMissile;
import com.wang.game.spacebattle.sprite.spaceship.weapon.missile.MainWeapanFriendMissile;
import com.wang.game.spacebattle.sprite.spaceship.weapon.missile.NormalWeanponFriendMissile;
import java.awt.event.ActionEvent;
import static java.lang.Math.abs;
import static java.lang.Math.pow;
import javax.swing.Timer;

/**
 *
 * @author ricolwang
 */
public class Boss extends Enemy
{

    protected Weapon theWeaponAlternative;
    protected Timer theTimerForMainWealpon = new Timer(2000, this);
    protected Timer theTimerForExpand = new Timer(6000, this);

    public Boss()
    {
        super("resource/PlaneBoss.png");

        this.setLayer(Common.LAYER_BOSS_SHIP);
        this.resetTotalLife(500);
        this.theTimerForMainWealpon.start();
        this.theTimerForExpand.start();
        this.theWeaponMain = new BossMainWeapon(this);
        this.theWeaponAutoMissile = new BossAutoMissileWeapon(this);
        this.theWeaponAlternative = new BossAlternativeWeapon(this);
    }

    @Override
    public void loadWeapon()
    {
        this.addAChild(this.theWeaponMain);
        this.addAChild(this.theWeaponAutoMissile);
        this.addAChild(this.theWeaponAlternative);

        this.theWeaponMain.setCentreX(this.getWidth() / 2);
        this.theWeaponMain.setCentreY(this.getHeight() / 2 + this.theWeaponMain.getHeight());

        this.theWeaponAutoMissile.setCentreX(this.getWidth() / 2);
        this.theWeaponAutoMissile.setCentreY(this.getHeight() / 2 + this.theWeaponMain.getHeight());

        this.theWeaponAlternative.setCentreX(this.getWidth() / 2);
        this.theWeaponAlternative.setCentreY(this.getHeight() / 2 + this.theWeaponAlternative.getHeight());
    }

    @Override
    public void onActionComplete(Action theAction)
    {
        super.onActionComplete(theAction);

        if (theAction.identifier.equals("ActionExpand"))
        {
            if (theAction instanceof ExpandByAction)
            {
                this.shrinkShape((ExpandByAction) theAction);
            }
        }
    }

    @Override
    public void onCollideWith(Sprite target)
    {
        if (target instanceof MainWeapanFriendMissile)
        {
            this.decreaseLife(50);
        } else if (target instanceof NormalWeanponFriendMissile)
        {
            this.decreaseLife(20);
        } else if (target instanceof MySpaceship)
        {
            this.decreaseLife(50);
        } else if (target instanceof FriendLaserWeapon)
        {
            this.decreaseLife(50);
        } else if (target instanceof FriendAutoFollowMissile)
        {
            this.decreaseLife(100);
        }

        if (this.getShouldDie() == true)
        {
            if (this.theScene instanceof SpaceShipScene)
            {
                ((SpaceShipScene) this.theScene).killABoss(this);
            }
        }
    }

    @Override
    public void onWillDead()
    {
        super.onWillDead(); //To change body of generated methods, choose Tools | Templates.

        this.theTimerForMainWealpon.stop();

        if (this.theScene instanceof SpaceShipScene)
        {
            SpaceShipScene theSpaceShipScene = (SpaceShipScene) this.theScene;
            theSpaceShipScene.deleteABoss(this);
        }
    }

    @Override
    public void explode()
    {
        int number = abs(theRandom.nextInt()) % 10 + 20;

        for (int i = 0; i < number; i++)
        {
            double tmpX = pow(-1, theRandom.nextInt() % 10) * theRandom.nextFloat() * Common.SPEED_EXPLODE_PARTICLE * 2;
            double tmpY = pow(-1, theRandom.nextInt() % 10) * theRandom.nextFloat() * Common.SPEED_EXPLODE_PARTICLE * 2;

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

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (this.getShouldDie())
        {
            return;
        }

        if (e.getSource().equals(this.theTimerFire))
        {
            this.theWeaponAlternative.fire();
        } else if (e.getSource().equals(this.theTimerForAutoFollowMissile))
        {
            if (abs(theRandom.nextInt()) % 100 > 80)
            {
                this.theWeaponAutoMissile.fire();
            }
        }

        if (e.getSource().equals(this.theTimerForMainWealpon))
        {
            if (theRandom.nextBoolean())
            {
                this.theWeaponMain.fire();
            }
        } else if (e.getSource().equals(this.theTimerForExpand))
        {
            Action aAction = this.expandShape();
            this.addAction(aAction);
            this.theTimerForExpand.stop();
        }
    }

    ExpandByAction expandShape()
    {
        float num = abs(theRandom.nextInt()) % 50;
        float time = abs(theRandom.nextInt()) % 2 + 1;
        ExpandByAction aAction = new ExpandByAction();
        aAction.expandBy(num, time);
        aAction.identifier = "ActionExpand";
        return aAction;
    }

    void shrinkShape(ExpandByAction action)
    {
        ExpandByAction aAction = new ExpandByAction();
        float value = action.getExpandBy();
        float time = action.getExpandByDuration();
        aAction.expandBy(-action.getExpandBy(), action.getExpandByDuration() / 1000.0f);
        aAction.identifier = "ActionExpandBack";
        this.addAction(aAction);
    }
}
