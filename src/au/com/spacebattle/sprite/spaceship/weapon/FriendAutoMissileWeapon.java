/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite.spaceship.weapon;

import au.com.spacebattle.common.Common;
import au.com.spacebattle.scene.SpaceShipScene;
import au.com.spacebattle.sprite.missile.BossAutoFollowMissile;
import au.com.spacebattle.sprite.missile.EnemyAutoFollowMissile;
import au.com.spacebattle.sprite.missile.FriendAutoFollowMissile;
import au.com.spacebattle.sprite.other.FriendFire;
import au.com.spacebattle.sprite.spaceship.Spaceship;

/**
 *
 * @author ricolwang
 */
public class FriendAutoMissileWeapon extends FriendWeapon
{

    public FriendAutoMissileWeapon(Spaceship theShip)
    {
        super(theShip);
    }

    @Override
    public void fire()
    {
        super.fire(); //To change body of generated methods, choose Tools | Templates.

        FriendAutoFollowMissile aMissile = new FriendAutoFollowMissile();
        if (this.theShip.theScene instanceof SpaceShipScene)
        {
            SpaceShipScene theSpaceScene = (SpaceShipScene) this.theShip.theScene;
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
        aMissile.setCentreX(this.theShip.getCentreX());
        aMissile.setCentreY(this.theShip.getCentreY() - aMissile.getHeight());
        aMissile.setAngle(this.theShip.getAngle());

//        aMissile.setVelocityX(Common.SPEED_MISSILE_FRIEND * Math.sin(aMissile.getAngle()));
        aMissile.setVelocityY(-Common.SPEED_MISSILE_FRIEND);

        aMissile.setLayer(this.theShip.getLayer());
        aMissile.fire();

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
