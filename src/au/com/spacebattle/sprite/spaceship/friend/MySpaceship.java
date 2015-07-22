/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite.spaceship.friend;

import au.com.rmit.Game2dEngine.action.AlphaToAction;
import au.com.rmit.Game2dEngine.action.MoveCentreXToAction;
import au.com.rmit.Game2dEngine.action.MoveCentreYToAction;
import au.com.rmit.Game2dEngine.action.MoveXByAction;
import au.com.rmit.Game2dEngine.action.MoveYByAction;
import au.com.rmit.Game2dEngine.node.Sprite;
import au.com.spacebattle.common.Common;
import au.com.spacebattle.scene.SpaceShipScene;
import au.com.spacebattle.sprite.missile.MainWeapanFriendMissile;
import au.com.spacebattle.sprite.missile.Missile;
import au.com.spacebattle.sprite.missile.NormalWeanponFriendMissile;
import au.com.spacebattle.sprite.other.ExpodeParticle;
import au.com.spacebattle.sprite.other.FriendFire;
import au.com.spacebattle.sprite.spaceship.Spaceship;
import au.com.spacebattle.sprite.missile.FriendLaserWeapon;
import static com.sun.org.apache.xalan.internal.lib.ExsltMath.power;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Math.abs;
import javax.swing.Timer;

/**
 *
 * @author ricolwang
 */
public class MySpaceship extends Spaceship implements ActionListener
{

    Timer timerForLaser = new Timer(10, this);
    Timer timerForStop = new Timer(3000, this);
    boolean bLaser = false;
    public boolean bAutoshot;

    public MySpaceship()
    {
        super("my-spaceship.png");
        this.lifetime = Sprite.EVER;
        this.bCollisionDetect = true;
        this.collisionCategory = Common.CATEGORY_FRIEND_SHIP;
        this.collisionTargetCategory = Common.CATEGORY_ENEMY_SHIP;

        this.layer = Common.LAYER_FRIEND_SHIP;
        this.resetTotalLife(500);
        this.timerForLaser.start();
    }

    public void fire()
    {
        //fire
        float delta = 15;
        float speed = Common.SPEED_MISSILE_FRIEND;
        Missile aMissile = new NormalWeanponFriendMissile("red-missile.png");
        aMissile.setX(this.getX() - delta + 20);
        aMissile.setY(this.getY() + 20);
        aMissile.setVelocityY(-speed);

        this.theScene.addSprite(aMissile);

        FriendFire aLeftFire = new FriendFire();
        aLeftFire.setCentreX(aMissile.getCentreX());
        aLeftFire.setCentreY(aMissile.getCentreY() + 10);
        aLeftFire.layer = this.layer;
        aLeftFire.setVelocityX(this.getVelocityX());
        aLeftFire.setVelocityY(this.getVelocityY());

        this.theScene.addSprite(aLeftFire);

        aMissile = new NormalWeanponFriendMissile("red-missile.png");
        aMissile.setX(this.getX() + this.getWidth() - delta - 10);
        aMissile.setY(this.getY() + 20);
        aMissile.setVelocityY(-speed);

        this.theScene.addSprite(aMissile);

        FriendFire aRightFire = new FriendFire();
        aRightFire.setCentreX(aMissile.getCentreX());
        aRightFire.setCentreY(aMissile.getCentreY() + 10);
        aRightFire.layer = this.layer;
        aRightFire.setVelocityX(this.getVelocityX());
        aRightFire.setVelocityY(this.getVelocityY());

        this.theScene.addSprite(aRightFire);
    }

    public void fireMainWeapon()
    {
        float delta = 12;
        float speed = Common.SPEED_MISSILE_FRIEND;
        Missile aMissile = new MainWeapanFriendMissile("blue-missile.png");
        aMissile.setX(this.getX() + this.getWidth() / 2 - delta);
        aMissile.setY(this.getY() - delta * 5);
        aMissile.setVelocityY(-speed * 1.2);
        this.theScene.addSprite(aMissile);

        FriendFire aFire = new FriendFire();
        aFire.setCentreX(aMissile.getCentreX());
        aFire.setCentreY(aMissile.getCentreY() + aMissile.getHeight() / 2);
        aFire.layer = this.layer;
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
            double tmpX = power(-1, theRandom.nextInt() % 10) * theRandom.nextFloat() * Common.SPEED_EXPLODE_PARTICLE * 2;
            double tmpY = power(-1, theRandom.nextInt() % 10) * theRandom.nextFloat() * Common.SPEED_EXPLODE_PARTICLE * 2;

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
        }
    }

    @Override
    public void onDead()
    {
        super.onDead(); //To change body of generated methods, choose Tools | Templates.

        this.timerForLaser.stop();
        this.timerForStop.stop();

        if (this.theScene instanceof SpaceShipScene)
        {
            ((SpaceShipScene) this.theScene).lostALife();
        }
    }
}
