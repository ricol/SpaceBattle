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
import au.com.spacebattle.sprite.missile.FriendAutoFollowMissile;
import au.com.spacebattle.sprite.missile.FriendLaserWeapon;
import au.com.spacebattle.sprite.missile.MainWeapanFriendMissile;
import au.com.spacebattle.sprite.missile.NormalWeanponFriendMissile;
import au.com.spacebattle.sprite.other.ExpodeParticle;
import au.com.spacebattle.sprite.spaceship.friend.MySpaceship;
import au.com.spacebattle.sprite.spaceship.weapon.BossAlternativeWeapon;
import au.com.spacebattle.sprite.spaceship.weapon.BossAutoMissileWeapon;
import au.com.spacebattle.sprite.spaceship.weapon.BossMainWeapon;
import au.com.spacebattle.sprite.spaceship.weapon.Weapon;
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

    public Boss()
    {
        super("PlaneBoss.png");

        float num = abs(theRandom.nextInt()) % 50;
        float time = abs(theRandom.nextInt()) % 2 + 1;
        ExpandByAction aAction = new ExpandByAction();
        aAction.expandBy(num, time);
        aAction.identifer = "ActionExpand";
        this.addAction(aAction);

        this.setLayer(Common.LAYER_BOSS_SHIP);
        this.resetTotalLife(500);
        this.theTimerForMainWealpon.start();
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
            this.decreaseLife(100);
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
        }
    }
}
