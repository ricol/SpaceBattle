/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite.spaceship.weapon;

import au.com.spacebattle.sprite.spaceship.weapon.laser.FriendLaserWeapon;
import au.com.spacebattle.sprite.spaceship.Spaceship;

/**
 *
 * @author ricolwang
 */
public class LaserWeapon extends Weapon
{

    public LaserWeapon(Spaceship theShip)
    {
        super(theShip);
    }

    @Override
    public void fire()
    {
        super.fire(); //To change body of generated methods, choose Tools | Templates.

        FriendLaserWeapon laserWeapon = new FriendLaserWeapon(this.theShip);
        theScene.addSprite(laserWeapon);
    }

}
