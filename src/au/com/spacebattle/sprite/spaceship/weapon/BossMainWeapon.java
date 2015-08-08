/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite.spaceship.weapon;

import au.com.spacebattle.common.Common;
import au.com.spacebattle.sprite.other.EnemyFire;
import au.com.spacebattle.sprite.spaceship.Spaceship;
import au.com.spacebattle.sprite.spaceship.weapon.missile.BossMainWeaponMissile;
import au.com.spacebattle.sprite.spaceship.weapon.missile.Missile;

/**
 *
 * @author ricolwang
 */
public class BossMainWeapon extends EnemyWeapon
{

    public BossMainWeapon(Spaceship theShip)
    {
        super(theShip);
    }

    @Override
    public void fire()
    {
        super.fire(); //To change body of generated methods, choose Tools | Templates.

        Missile aMissile = new BossMainWeaponMissile("resource/red-enemy-missile.png");
//        aMissile.bDrawFrame = true;

        aMissile.setCentreX(this.theShip.getCentreX());
        aMissile.setY(this.theShip.getCentreY() + this.theShip.getHeight() / 2);

//        aMissile.setAngle(this.getAngle());
//        aMissile.setVelocityX(Common.SPEED_MAIN_MISSILE_ENEMY * Math.sin(-aMissile.getAngle()));
        aMissile.setVelocityY(Common.SPEED_MAIN_MISSILE_ENEMY);

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
