/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.game.spacebattle.sprite.spaceship.weapon.missile;

import com.wang.Game2dEngine.action.AlphaToAction;
import com.wang.game.spacebattle.common.Common;
import com.wang.game.spacebattle.sprite.other.ExpodeParticle;
import static java.lang.Math.abs;
import static java.lang.Math.pow;

/**
 *
 * @author ricolwang
 */
public class BossMainWeaponMissile extends Missile
{

    public BossMainWeaponMissile(String imagename)
    {
        super(imagename);

        this.bCollisionDetect = true;
        this.setCollisionCategory(Common.CATEGORY_ENEMY_SHIP);
        this.addTargetCollisionCategory(Common.CATEGORY_FRIEND_SHIP);
    }

    @Override
    public void onWillDead()
    {
        super.onWillDead(); //To change body of generated methods, choose Tools | Templates.

        this.explode();
    }

    @Override
    protected void explode()
    {
        int number = abs(theRandom.nextInt()) % 10 + 30;

        for (int i = 0; i < number; i++)
        {
            double tmpX = pow(-1, theRandom.nextInt() % 10) * theRandom.nextFloat() * Common.SPEED_EXPLODE_PARTICLE;
            double tmpY = pow(-1, theRandom.nextInt() % 10) * theRandom.nextFloat() * Common.SPEED_EXPLODE_PARTICLE;

            ExpodeParticle aFire = new ExpodeParticle();
            aFire.setX(this.getCentreX());
            aFire.setY(this.getCentreY());
            aFire.setVelocityX(tmpX);
            aFire.setVelocityY(tmpY);
            aFire.setRed(255);
            aFire.setGreen(0);
            aFire.setBlue(0);
            aFire.bDeadIfNoActions = true;

            AlphaToAction aAction = new AlphaToAction(aFire);
            aAction.alphaTo(0, 0.5f);
            aFire.addAction(aAction);

            if (this.theScene == null)
            {
                break;
            }
            this.theScene.addSprite(aFire);
        }
    }
}
