/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite.missile;

import au.com.spacebattle.common.Common;

/**
 *
 * @author ricolwang
 */
public class FriendMissile extends Missile
{

    public FriendMissile(String imagename)
    {
        super(imagename);
        this.bCollisionDetect = true;
        this.collisionCategory = Common.CATEGORY_FRIEND_SHIP;
        this.collisionTargetCategory = Common.CATEGORY_ENEMY_SHIP;
    }
}