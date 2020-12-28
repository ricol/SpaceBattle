/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.game.spacebattle.sprite.spaceship.weapon;

import com.wang.game.spacebattle.common.Common;
import com.wang.game.spacebattle.sprite.other.EnemyFire;
import com.wang.game.spacebattle.sprite.spaceship.Spaceship;
import com.wang.game.spacebattle.sprite.spaceship.weapon.missile.EnemyMissile;
import com.wang.game.spacebattle.sprite.spaceship.weapon.missile.Missile;

/**
 *
 * @author ricolwang
 */
public class EnemyMainWeapon extends EnemyWeapon
{

    public EnemyMainWeapon(Spaceship theShip)
    {
        super(theShip);
    }

    @Override
    public void fire()
    {
        super.fire(); //To change body of generated methods, choose Tools | Templates.

        Missile aMissile = new EnemyMissile("resource/enemy-missile.png");
//        aMissile.bDrawFrame = true;
        aMissile.setX(this.theShip.getCentreX() - aMissile.getWidth() / 2);
        aMissile.setY(this.theShip.getCentreY() + this.theShip.getHeight() / 2);
//        aMissile.setAngle(this.angle);

//        aMissile.setVelocityX(Common.SPEED_MISSILE_ENEMY * Math.sin(-aMissile.getAngle()));
        aMissile.setVelocityY(Common.SPEED_MISSILE_ENEMY);

        aMissile.setLayer(this.theShip.getLayer());

        this.theShip.theScene.addSprite(aMissile);

        EnemyFire aFire = new EnemyFire();
        aFire.setCentreX(aMissile.getCentreX());
        aFire.setCentreY(aMissile.getCentreY() + aMissile.getHeight() / 2);
        aFire.setLayer(this.theShip.getLayer());
        aFire.setVelocityX(this.theShip.getVelocityX());
        aFire.setVelocityY(this.theShip.getVelocityY());

        this.theShip.theScene.addSprite(aFire);
    }

}
