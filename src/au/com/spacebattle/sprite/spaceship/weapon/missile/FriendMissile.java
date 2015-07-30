/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite.spaceship.weapon.missile;

import au.com.spacebattle.common.Common;

/**
 *
 * @author ricolwang
 */
public class FriendMissile extends Missile
{

    public FriendMissile(double x, double y, double width, double height, double mass, double velocityX, double velocityY)
    {
        super(x, y, width, height, mass, velocityX, velocityY);
        this.bCollisionDetect = true;
        this.setCollisionCategory(Common.CATEGORY_FRIEND_SHIP);
        this.addTargetCollisionCategory(Common.CATEGORY_ENEMY_SHIP);
    }

    public FriendMissile(String imagename)
    {
        super(imagename);
        this.bCollisionDetect = true;
        this.setCollisionCategory(Common.CATEGORY_FRIEND_SHIP);
        this.addTargetCollisionCategory(Common.CATEGORY_ENEMY_SHIP);
    }
}
