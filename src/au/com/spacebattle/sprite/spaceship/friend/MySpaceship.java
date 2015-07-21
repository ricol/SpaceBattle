/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite.spaceship.friend;

import au.com.rmit.Game2dEngine.action.MoveCentreXToAction;
import au.com.rmit.Game2dEngine.action.MoveCentreYToAction;
import au.com.rmit.Game2dEngine.action.MoveXByAction;
import au.com.rmit.Game2dEngine.action.MoveYByAction;
import au.com.rmit.Game2dEngine.node.Sprite;
import au.com.spacebattle.common.Common;
import au.com.spacebattle.sprite.missile.BossMainWeaponMissile;
import au.com.spacebattle.sprite.missile.EnemyMissile;
import au.com.spacebattle.sprite.missile.MainWeapanFriendMissile;
import au.com.spacebattle.sprite.missile.Missile;
import au.com.spacebattle.sprite.missile.NormalWeanponFriendMissile;
import au.com.spacebattle.sprite.other.FriendFire;
import au.com.spacebattle.sprite.other.Sheld;
import au.com.spacebattle.sprite.spaceship.Spaceship;
import au.com.spacebattle.sprite.spaceship.enemy.Boss;
import au.com.spacebattle.sprite.spaceship.enemy.Enemy;

/**
 *
 * @author ricolwang
 */
public class MySpaceship extends Spaceship
{

    public boolean bAutoshot;

    public MySpaceship()
    {
        super("my-spaceship.png");
        this.lifetime = Sprite.EVER;
        this.bCollisionDetect = true;
        this.collisionCategory = Common.CATEGORY_FRIEND_SHIP;
        this.collisionTargetCategory = Common.CATEGORY_ENEMY_SHIP;
        
        this.layer = Common.LAYER_FRIEND_SHIP;
        this.setLife(500);
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
        //sheld up
        Sheld aSheld = new Sheld(this.getCentreX(), this.getCentreY(), 0, 0, 0, 0, 0);
        aSheld.lifetime = 1;
        aSheld.layer = this.layer;

        theScene.addSprite(aSheld);
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
    public void onCollisionWith(Sprite target)
    {
        if (target instanceof BossMainWeaponMissile)
        {
            this.decreaseLife(100);
        }else if (target instanceof EnemyMissile)
        {
            this.decreaseLife(10);
        }else if (target instanceof Enemy)
        {
            this.decreaseLife(100);
        }else if (target instanceof Boss)
        {
            this.setDead();
        }
    }
}
