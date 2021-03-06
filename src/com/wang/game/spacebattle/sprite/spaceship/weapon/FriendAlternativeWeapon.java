/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.game.spacebattle.sprite.spaceship.weapon;

import com.wang.game.spacebattle.common.Common;
import com.wang.game.spacebattle.sprite.other.FriendFire;
import com.wang.game.spacebattle.sprite.spaceship.Spaceship;
import com.wang.game.spacebattle.sprite.spaceship.weapon.missile.Missile;
import com.wang.game.spacebattle.sprite.spaceship.weapon.missile.NormalWeanponFriendMissile;

/**
 *
 * @author ricolwang
 */
public class FriendAlternativeWeapon extends FriendWeapon
{

    public FriendAlternativeWeapon(Spaceship theShip)
    {
        super(theShip);
    }

    @Override
    public void fire()
    {
        super.fire(); //To change body of generated methods, choose Tools | Templates.

        //fire
        float speed = Common.SPEED_MISSILE_FRIEND;
        Missile aMissile = new NormalWeanponFriendMissile("resource/red-missile.png");
        aMissile.setX(this.theShip.getX() - aMissile.getWidth() / 2);
        aMissile.setY(this.theShip.getY() + 20);
        aMissile.setVelocityY(-speed);

        this.theShip.theScene.addSprite(aMissile);

        FriendFire aLeftFire = new FriendFire();
        aLeftFire.setCentreX(aMissile.getCentreX());
        aLeftFire.setCentreY(aMissile.getCentreY() + 10);
        aLeftFire.setLayer(this.theShip.getLayer());
        aLeftFire.setVelocityX(this.theShip.getVelocityX());
        aLeftFire.setVelocityY(this.theShip.getVelocityY());

        this.theShip.theScene.addSprite(aLeftFire);

        aMissile = new NormalWeanponFriendMissile("resource/red-missile.png");
        aMissile.setX(this.theShip.getX() + this.theShip.getWidth() - aMissile.getWidth() / 2);
        aMissile.setY(this.theShip.getY() + 20);
        aMissile.setVelocityY(-speed);

        this.theShip.theScene.addSprite(aMissile);

        FriendFire aRightFire = new FriendFire();
        aRightFire.setCentreX(aMissile.getCentreX());
        aRightFire.setCentreY(aMissile.getCentreY() + 10);
        aRightFire.setLayer(this.theShip.getLayer());
        aRightFire.setVelocityX(this.theShip.getVelocityX());
        aRightFire.setVelocityY(this.theShip.getVelocityY());

        this.theShip.theScene.addSprite(aRightFire);
    }

}
