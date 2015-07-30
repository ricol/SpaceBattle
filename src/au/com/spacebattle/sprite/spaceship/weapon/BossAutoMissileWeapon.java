/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite.spaceship.weapon;

import au.com.spacebattle.common.Common;
import au.com.spacebattle.scene.SpaceShipScene;
import au.com.spacebattle.sprite.spaceship.weapon.missile.BossAutoFollowMissile;
import au.com.spacebattle.sprite.other.EnemyFire;
import au.com.spacebattle.sprite.spaceship.Spaceship;
import au.com.spacebattle.sprite.spaceship.enemy.Enemy;

/**
 *
 * @author ricolwang
 */
public class BossAutoMissileWeapon extends EnemyWeapon
{

    public BossAutoMissileWeapon(Spaceship theShip)
    {
        super(theShip);
    }

    @Override
    public void fire()
    {
        super.fire(); //To change body of generated methods, choose Tools | Templates.

        BossAutoFollowMissile aMissile = new BossAutoFollowMissile();
        aMissile.theTarget = ((Enemy) this.theShip).theTarget;
//        aMissile.bDrawFrame = true;
        aMissile.setX(this.theShip.getCentreX() - aMissile.getWidth() / 2);
        aMissile.setY(this.theShip.getCentreY() + this.theShip.getHeight() / 2);
        aMissile.setAngle(this.theShip.getAngle());

        aMissile.setVelocityX(Common.SPEED_MISSILE_ENEMY * Math.sin(-aMissile.getAngle()));
        aMissile.setVelocityY(Common.SPEED_MISSILE_ENEMY * Math.cos(-aMissile.getAngle()));

        aMissile.setLayer(this.theShip.getLayer());
        aMissile.fire();

        this.theShip.theScene.addSprite(aMissile);
        ((SpaceShipScene) this.theShip.theScene).addABossMissile(aMissile);

        EnemyFire aFire = new EnemyFire();
        aFire.setCentreX(aMissile.getCentreX());
        aFire.setCentreY(aMissile.getCentreY() + aMissile.getHeight() / 2);
        aFire.setLayer(this.theShip.getLayer());
        aFire.setVelocityX(this.theShip.getVelocityX());
        aFire.setVelocityY(this.theShip.getVelocityY());

        this.theShip.theScene.addSprite(aFire);
    }

}
