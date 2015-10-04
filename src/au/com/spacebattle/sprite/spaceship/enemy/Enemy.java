/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite.spaceship.enemy;

import au.com.rmit.Game2dEngine.action.AlphaToAction;
import au.com.rmit.Game2dEngine.math.vector.Vector;
import au.com.rmit.Game2dEngine.scene.Layer;
import au.com.rmit.Game2dEngine.sprite.Sprite;
import au.com.spacebattle.common.Common;
import au.com.spacebattle.scene.SpaceShipScene;
import au.com.spacebattle.sprite.other.ExpodeParticle;
import au.com.spacebattle.sprite.spaceship.Spaceship;
import au.com.spacebattle.sprite.spaceship.friend.MySpaceship;
import au.com.spacebattle.sprite.spaceship.weapon.EnemyAutoMissileWeapon;
import au.com.spacebattle.sprite.spaceship.weapon.EnemyMainWeapon;
import au.com.spacebattle.sprite.spaceship.weapon.Weapon;
import au.com.spacebattle.sprite.spaceship.weapon.laser.FriendLaserWeapon;
import au.com.spacebattle.sprite.spaceship.weapon.missile.FriendAutoFollowMissile;
import au.com.spacebattle.sprite.spaceship.weapon.missile.MainWeapanFriendMissile;
import au.com.spacebattle.sprite.spaceship.weapon.missile.NormalWeanponFriendMissile;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Math.abs;
import static java.lang.Math.pow;
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
    protected Timer theTimerForAutoFollowMissile = new Timer(3000, this);

    public Spaceship theTarget;
    protected Weapon theWeaponMain;
    protected Weapon theWeaponAutoMissile;

    public Enemy(String imagename)
    {
        super(imagename);

        this.bCollisionDetect = true;
        this.setCollisionCategory(Common.CATEGORY_ENEMY_SHIP);
        this.addTargetCollisionCategory(Common.CATEGORY_FRIEND_SHIP);

        this.setLayer(Common.LAYER_ENEMY_SHIP);
        this.theTimerAutoadjust.start();
        this.theTimerFire.start();
        this.theTimerForAutoFollowMissile.start();
        this.theWeaponMain = new EnemyMainWeapon(this);
        this.theWeaponAutoMissile = new EnemyAutoMissileWeapon(this);
    }

    public void loadWeapon()
    {
        this.addAChild(this.theWeaponMain);
        this.addAChild(this.theWeaponAutoMissile);

        this.theWeaponMain.setCentreX(this.getWidth() / 2);
        this.theWeaponMain.setCentreY(this.getHeight() / 2 + this.theWeaponMain.getHeight());

        this.theWeaponAutoMissile.setCentreX(this.getWidth() / 2);
        this.theWeaponAutoMissile.setCentreY(this.getHeight() / 2 + this.theWeaponMain.getHeight());
    }

    @Override
    public void onWillDead()
    {
        super.onWillDead(); //To change body of generated methods, choose Tools | Templates.

        this.theTimerAutoadjust.stop();
        this.theTimerFire.stop();
        this.theTimerForAutoFollowMissile.stop();
        this.theTarget = null;

        if (this.theScene instanceof SpaceShipScene)
        {
            SpaceShipScene theSpaceShipScene = (SpaceShipScene) this.theScene;
            theSpaceShipScene.deleteAEnemy(this);
        }
    }

    @Override
    public void onAddToLayer(Layer theLayer)
    {
        super.onAddToLayer(theLayer); //To change body of generated methods, choose Tools | Templates.

        this.loadWeapon();
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
            this.decreaseLife(100);
        } else if (target instanceof FriendAutoFollowMissile)
        {
            this.decreaseLife(300);
        }

        if (this.getShouldDie() == true)
        {
            if (this.theScene instanceof SpaceShipScene)
            {
                ((SpaceShipScene) this.theScene).killAEnemy(this);
            }
        }
    }

    @Override
    public void explode()
    {
        int number = abs(theRandom.nextInt()) % 10 + 10;

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

        double changeX = theShip.getCentreX() - this.getCentreX();
        double changeY = theShip.getCentreY() - this.getCentreY();
        Vector DISPLACEMENT = new Vector(changeX, changeY);
        Vector Y = new Vector(0, -1);
        double angle = DISPLACEMENT.getTheNegativeVector().getCosValueForAngleToVector(Y);
        angle = Math.acos(angle);
        if (changeX > 0)
            angle = Math.PI * 2 - angle;
        this.setAngle(angle);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (this.getShouldDie())
        {
            return;
        }

        if (e.getSource().equals(this.theTimerAutoadjust))
        {
            if (this.getShouldDie())
            {
                return;
            }
            if (bAutoAdjustGesture)
            {
                adjustGesture(theTarget);
            }
        } else if (e.getSource().equals(this.theTimerFire))
        {
            if (this.getShouldDie())
            {
                return;
            }

            this.theWeaponMain.fire();
        } else if (e.getSource().equals(this.theTimerForAutoFollowMissile))
        {
            if (this.getShouldDie())
            {
                return;
            }
            if (abs(theRandom.nextInt()) % 100 > 80)
            {
                this.theWeaponAutoMissile.fire();
            }
        }
    }
}
