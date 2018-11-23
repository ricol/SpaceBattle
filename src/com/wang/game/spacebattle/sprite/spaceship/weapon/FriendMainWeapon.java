/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.game.spacebattle.sprite.spaceship.weapon;

import com.wang.game.spacebattle.common.Common;
import com.wang.game.spacebattle.sprite.other.FriendFire;
import com.wang.game.spacebattle.sprite.spaceship.Spaceship;
import com.wang.game.spacebattle.sprite.spaceship.weapon.missile.MainWeapanFriendMissile;
import com.wang.game.spacebattle.sprite.spaceship.weapon.missile.Missile;

/**
 *
 * @author ricolwang
 */
public class FriendMainWeapon extends FriendWeapon
{

    public FriendMainWeapon(Spaceship theShip)
    {
        super(theShip);
    }

    @Override
    public void fire()
    {
        super.fire(); //To change body of generated methods, choose Tools | Templates.

        float speed = Common.SPEED_MISSILE_FRIEND;
        Missile aMissile = new MainWeapanFriendMissile("resource/blue-missile.png");
        aMissile.setCentreX(this.theShip.getCentreX());
        aMissile.setCentreY(this.theShip.getCentreY() - aMissile.getHeight());

        aMissile.setVelocityY(-speed * 1.2);
        this.theShip.theScene.addSprite(aMissile);

        FriendFire aFire = new FriendFire();
        aFire.setCentreX(aMissile.getCentreX());
        aFire.setCentreY(aMissile.getCentreY() + aMissile.getHeight() / 2);
        aFire.setLayer(this.theShip.getLayer());
        aFire.setVelocityX(this.theShip.getVelocityX());
        aFire.setVelocityY(this.theShip.getVelocityY());

        this.theShip.theScene.addSprite(aFire);
    }

}
