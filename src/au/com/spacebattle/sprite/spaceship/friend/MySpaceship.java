/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite.spaceship.friend;

import au.com.rmit.Game2dEngine.action.Action;
import au.com.rmit.Game2dEngine.action.AlphaToAction;
import au.com.rmit.Game2dEngine.action.MoveCentreXToAction;
import au.com.rmit.Game2dEngine.action.MoveCentreYToAction;
import au.com.rmit.Game2dEngine.action.MoveXByAction;
import au.com.rmit.Game2dEngine.action.MoveYByAction;
import au.com.rmit.Game2dEngine.sprite.Sprite;
import au.com.rmit.Game2dEngine.scene.Layer;
import au.com.spacebattle.common.Common;
import au.com.spacebattle.scene.SpaceShipScene;
import au.com.spacebattle.sprite.other.ExpodeParticle;
import au.com.spacebattle.sprite.spaceship.Spaceship;
import au.com.spacebattle.sprite.spaceship.weapon.FriendAlternativeWeapon;
import au.com.spacebattle.sprite.spaceship.weapon.FriendAutoMissileWeapon;
import au.com.spacebattle.sprite.spaceship.weapon.FriendMainWeapon;
import au.com.spacebattle.sprite.spaceship.weapon.Weapon;
import au.com.spacebattle.sprite.spaceship.weapon.laser.FriendLaserWeapon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Math.abs;
import static java.lang.Math.pow;
import java.util.HashSet;
import java.util.Set;
import javax.swing.Timer;

/**
 *
 * @author ricolwang
 */
public class MySpaceship extends Spaceship implements ActionListener
{

    Timer timerForLaser = new Timer(10, this);
    Timer timerForStop = new Timer(3000, this);
    Timer theTimerForAutoFollowMissile = new Timer(300, this);

    boolean bLaser = false;
    public boolean bAutoshot;
    public boolean bAutoMissile;
    Weapon theWeaponMain = new FriendMainWeapon(this);
    Weapon theWeaponAlternative = new FriendAlternativeWeapon(this);
    Weapon theWeaponAutoMissile = new FriendAutoMissileWeapon(this);

    public MySpaceship()
    {
        super("my-spaceship.png");
        this.setLifeTime(Sprite.EVER);
        this.bCollisionDetect = true;
        this.setCollisionCategory(Common.CATEGORY_FRIEND_SHIP);
        this.addTargetCollisionCategory(Common.CATEGORY_ENEMY_SHIP);

        this.setLayer(Common.LAYER_FRIEND_SHIP);
        this.resetTotalLife(500);
        this.timerForLaser.start();
        theTimerForAutoFollowMissile.start();
        
        this.addAChild(this.theWeaponMain);
        this.addAChild(this.theWeaponAlternative);
        this.addAChild(this.theWeaponAutoMissile);
    }

    @Override
    public void onAddToLayer(Layer theLayer)
    {
        super.onAddToLayer(theLayer); //To change body of generated methods, choose Tools | Templates.

        this.theWeaponMain.setHeight(20);
        this.theWeaponMain.setWidth(20);
        this.theWeaponMain.setCentreX(this.getWidth() / 2);
        this.theWeaponMain.setY(0);

        this.theWeaponAlternative.setWidth(20);
        this.theWeaponAlternative.setCentreX(this.getWidth() / 2);
        this.theWeaponAlternative.setCentreY(this.getHeight() / 2 + this.theWeaponAlternative.getHeight());

        this.theWeaponAutoMissile.setWidth(20);
        this.theWeaponAutoMissile.setCentreX(this.getWidth() / 2);
        this.theWeaponAutoMissile.setY(0);
    }

    public void fire()
    {
        this.theWeaponAlternative.fire();
    }

    public void fireMainWeapon()
    {
        this.theWeaponMain.fire();
    }

    public void moveToXY(int x, int y)
    {
        double theShipCentreX = this.getCentreX();
        double theShipCentreY = this.getCentreY();

        MoveCentreXToAction aCentreXAction = new MoveCentreXToAction(this);
        aCentreXAction.MoveCentreXTo(x, 0);
        this.addAction(aCentreXAction);

        MoveCentreYToAction aCentreYAction = new MoveCentreYToAction(this);
        aCentreYAction.MoveCentreYTo(y, 0);
        this.addAction(aCentreYAction);
    }

    public void moveToXYInSequence(int x, int y, float duration)
    {
        double theShipCentreX = this.getCentreX();
        double theShipCentreY = this.getCentreY();

        Set<Action> aSetOfActions = new HashSet<>();

        MoveCentreXToAction aCentreXAction = new MoveCentreXToAction(this);
        aCentreXAction.MoveCentreXTo(x, duration);
        aSetOfActions.add(aCentreXAction);

        MoveCentreYToAction aCentreYAction = new MoveCentreYToAction(this);
        aCentreYAction.MoveCentreYTo(y, duration);
        aSetOfActions.add(aCentreYAction);
        this.enQueueActions(aSetOfActions);
    }

    public void openLaser()
    {
        bLaser = true;
        this.timerForStop.start();
    }

    public void moveDown(float value, float duration)
    {
        //move down
        MoveYByAction aAction = new MoveYByAction();
        aAction.moveYBy(value, duration);
        this.addAction(aAction);
    }

    public void moveUp(float value, float duration)
    {
        MoveYByAction aAction = new MoveYByAction();
        aAction.moveYBy(value, duration);
        this.addAction(aAction);
    }

    public void moveLeft(float value, float duration)
    {
        MoveXByAction aAction = new MoveXByAction();
        aAction.moveXBy(value, duration);
        this.addAction(aAction);
    }

    public void moveRight(float value, float duration)
    {
        MoveXByAction aAction = new MoveXByAction();
        aAction.moveXBy(value, duration);
        this.addAction(aAction);
    }

    public void moveDownInSequence(float value, float duration)
    {
        //move down
        MoveYByAction aAction = new MoveYByAction();
        aAction.moveYBy(value, duration);
        Set<Action> aSetOfActions = new HashSet<>();
        aSetOfActions.add(aAction);
        this.enQueueActions(aSetOfActions);
    }

    public void moveUpInSequence(float value, float duration)
    {
        MoveYByAction aAction = new MoveYByAction();
        aAction.moveYBy(value, duration);
        Set<Action> aSetOfActions = new HashSet<>();
        aSetOfActions.add(aAction);
        this.enQueueActions(aSetOfActions);
    }

    public void moveLeftInSequence(float value, float duration)
    {
        MoveXByAction aAction = new MoveXByAction();
        aAction.moveXBy(value, duration);
        Set<Action> aSetOfActions = new HashSet<>();
        aSetOfActions.add(aAction);
        this.enQueueActions(aSetOfActions);
    }

    public void moveRightInSequence(float value, float duration)
    {
        MoveXByAction aAction = new MoveXByAction();
        aAction.moveXBy(value, duration);
        Set<Action> aSetOfActions = new HashSet<>();
        aSetOfActions.add(aAction);
        this.enQueueActions(aSetOfActions);
    }

    @Override
    public void onCollideWith(Sprite target)
    {
//        if (target instanceof BossMainWeaponMissile)
//        {
//            this.decreaseLife(5);
//            System.out.println("Collide with the boss main weapon...life left: " + this.getLife());
//        } else if (target instanceof EnemyMissile)
//        {
//            this.decreaseLife(1);
//            System.out.println("Collide with enemy missile...life left: " + this.getLife());
//        } else if (target instanceof Enemy)
//        {
//            this.decreaseLife(3);
//            System.out.println("Collide with Enemy...life left: " + this.getLife());
//        } else if (target instanceof Boss)
//        {
//            this.decreaseLife(5);
//            System.out.println("Collide with Boss...life left: " + this.getLife());
//        }
    }

    @Override
    public void explode()
    {
        int number = abs(theRandom.nextInt()) % 10 + 30;

        for (int i = 0; i < number; i++)
        {
            double tmpX = pow(-1, theRandom.nextInt() % 10) * theRandom.nextFloat() * Common.SPEED_EXPLODE_PARTICLE * 2;
            double tmpY = pow(-1, theRandom.nextInt() % 10) * theRandom.nextFloat() * Common.SPEED_EXPLODE_PARTICLE * 2;

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

    @Override
    public void actionPerformed(ActionEvent e)
    {
        super.actionPerformed(e);

        if (this.getShouldDie())
        {
            return;
        }

        if (e.getSource().equals(this.timerForLaser))
        {
            if (bLaser)
            {
                FriendLaserWeapon laserWeapon = new FriendLaserWeapon(this);
                theScene.addSprite(laserWeapon);
            }
        } else if (e.getSource().equals(this.timerForStop))
        {
            bLaser = false;
            this.timerForStop.stop();
        } else if (e.getSource().equals(this.theTimerForAutoFollowMissile))
        {
            if (bAutoMissile)
            {
                this.fireAutoFollowMissile();
            }
        }
    }

    @Override
    public void onWillDead()
    {
        super.onWillDead(); //To change body of generated methods, choose Tools | Templates.

        this.timerForLaser.stop();
        this.timerForStop.stop();
        this.theTimerForAutoFollowMissile.stop();

        if (this.theScene instanceof SpaceShipScene)
        {
            ((SpaceShipScene) this.theScene).lostALife();
        }
    }

    public void fireAutoFollowMissile()
    {
        this.theWeaponAutoMissile.fire();
    }
}
