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
import au.com.rmit.Game2dEngine.node.Sprite;
import au.com.spacebattle.common.Common;
import au.com.spacebattle.scene.SpaceShipScene;
import au.com.spacebattle.sprite.missile.BossAutoFollowMissile;
import au.com.spacebattle.sprite.missile.EnemyAutoFollowMissile;
import au.com.spacebattle.sprite.missile.FriendAutoFollowMissile;
import au.com.spacebattle.sprite.missile.MainWeapanFriendMissile;
import au.com.spacebattle.sprite.missile.Missile;
import au.com.spacebattle.sprite.missile.NormalWeanponFriendMissile;
import au.com.spacebattle.sprite.other.ExpodeParticle;
import au.com.spacebattle.sprite.other.FriendFire;
import au.com.spacebattle.sprite.spaceship.Spaceship;
import au.com.spacebattle.sprite.missile.FriendLaserWeapon;
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

    public MySpaceship()
    {
        super("my-spaceship.png");
        this.setLifeTime(Sprite.EVER);
        this.bCollisionDetect = true;
        this.collisionCategory = Common.CATEGORY_FRIEND_SHIP;
        this.collisionTargetCategory = Common.CATEGORY_ENEMY_SHIP;

        this.setLayer(Common.LAYER_FRIEND_SHIP);
        this.resetTotalLife(500);
        this.timerForLaser.start();
        theTimerForAutoFollowMissile.start();
    }

    @Override
    public void fire()
    {
        //fire
        float speed = Common.SPEED_MISSILE_FRIEND;
        Missile aMissile = new NormalWeanponFriendMissile("red-missile.png");
        aMissile.setX(this.getX() - aMissile.getWidth() / 2);
        aMissile.setY(this.getY() + 20);
        aMissile.setVelocityY(-speed);

        this.theScene.addSprite(aMissile);

        FriendFire aLeftFire = new FriendFire();
        aLeftFire.setCentreX(aMissile.getCentreX());
        aLeftFire.setCentreY(aMissile.getCentreY() + 10);
        aLeftFire.setLayer(this.getLayer());
        aLeftFire.setVelocityX(this.getVelocityX());
        aLeftFire.setVelocityY(this.getVelocityY());

        this.theScene.addSprite(aLeftFire);

        aMissile = new NormalWeanponFriendMissile("red-missile.png");
        aMissile.setX(this.getX() + this.getWidth() - aMissile.getWidth() / 2);
        aMissile.setY(this.getY() + 20);
        aMissile.setVelocityY(-speed);

        this.theScene.addSprite(aMissile);

        FriendFire aRightFire = new FriendFire();
        aRightFire.setCentreX(aMissile.getCentreX());
        aRightFire.setCentreY(aMissile.getCentreY() + 10);
        aRightFire.setLayer(this.getLayer());
        aRightFire.setVelocityX(this.getVelocityX());
        aRightFire.setVelocityY(this.getVelocityY());

        this.theScene.addSprite(aRightFire);
    }

    public void fireMainWeapon()
    {
        float speed = Common.SPEED_MISSILE_FRIEND;
        Missile aMissile = new MainWeapanFriendMissile("blue-missile.png");
        aMissile.setCentreX(this.getCentreX());
        aMissile.setCentreY(this.getCentreY() - aMissile.getHeight());

        aMissile.setVelocityY(-speed * 1.2);
        this.theScene.addSprite(aMissile);

        FriendFire aFire = new FriendFire();
        aFire.setCentreX(aMissile.getCentreX());
        aFire.setCentreY(aMissile.getCentreY() + aMissile.getHeight() / 2);
        aFire.setLayer(this.getLayer());
        aFire.setVelocityX(this.getVelocityX());
        aFire.setVelocityY(this.getVelocityY());

        this.theScene.addSprite(aFire);
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

    public void openSheld()
    {
//        //sheld up
//        Sheld aSheld = new Sheld(this.getCentreX(), this.getCentreY(), 0, 0, 0, 0, 0);
//        aSheld.lifetime = 1;
//        aSheld.layer = this.layer;
//
//        theScene.addSprite(aSheld);
        openLaser();
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

        if (this.getShouldDie()) return;
        
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

    @Override
    public void fireAutoFollowMissile()
    {
        FriendAutoFollowMissile aMissile = new FriendAutoFollowMissile();
        if (theScene instanceof SpaceShipScene)
        {
            SpaceShipScene theSpaceScene = (SpaceShipScene) this.theScene;
            BossAutoFollowMissile aBossMissile = theSpaceScene.getARandomBossMissile();
            if (aBossMissile != null)
            {
                aMissile.theTarget = aBossMissile;
            } else
            {
                EnemyAutoFollowMissile aEnemyMissile = theSpaceScene.getARandomEnemyMissile();
                if (aEnemyMissile != null)
                {
                    aMissile.theTarget = aEnemyMissile;
                } else
                {
                    aMissile.theTarget = theSpaceScene.getARandomTarget();
                }
            }
        }
//        aMissile.bDrawFrame = true;
        aMissile.setCentreX(this.getCentreX());
        aMissile.setCentreY(this.getCentreY() - aMissile.getHeight());
        aMissile.setAngle(this.getAngle());

//        aMissile.setVelocityX(Common.SPEED_MISSILE_FRIEND * Math.sin(aMissile.getAngle()));
        aMissile.setVelocityY(-Common.SPEED_MISSILE_FRIEND);

        aMissile.setLayer(this.getLayer());
        aMissile.fire();

        this.theScene.addSprite(aMissile);

        FriendFire aFire = new FriendFire();
        aFire.setCentreX(aMissile.getCentreX());
        aFire.setCentreY(aMissile.getCentreY() + aMissile.getHeight() / 2);
        aFire.setLayer(this.getLayer());
        aFire.setVelocityX(this.getVelocityX());
        aFire.setVelocityY(this.getVelocityY());

        this.theScene.addSprite(aFire);
    }
}
