/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite.missile;

import au.com.rmit.Game2dEngine.action.AlphaToAction;
import au.com.spacebattle.common.Common;
import au.com.spacebattle.scene.SpaceShipScene;
import au.com.spacebattle.sprite.other.ExpodeParticle;
import static com.sun.org.apache.xalan.internal.lib.ExsltMath.power;
import static java.lang.Math.abs;

/**
 *
 * @author ricolwang
 */
public class EnemyAutoFollowMissile extends AutoFollowMissile
{

    public EnemyAutoFollowMissile()
    {
        super("enemy-auto-follow-missile.png");

        this.bCollisionDetect = true;
        this.collisionCategory = Common.CATEGORY_ENEMY_SHIP;
        this.collisionTargetCategory = Common.CATEGORY_FRIEND_SHIP;
    }

    @Override
    public void explode()
    {
        int number = abs(theRandom.nextInt()) % 10 + 30;

        for (int i = 0; i < number; i++)
        {
            double tmpX = power(-1, theRandom.nextInt() % 10) * theRandom.nextFloat() * Common.SPEED_EXPLODE_PARTICLE;
            double tmpY = power(-1, theRandom.nextInt() % 10) * theRandom.nextFloat() * Common.SPEED_EXPLODE_PARTICLE;

            ExpodeParticle aFire = new ExpodeParticle();
            aFire.setX(this.getCentreX());
            aFire.setY(this.getCentreY());
            aFire.setVelocityX(tmpX);
            aFire.setVelocityY(tmpY);
            aFire.setRed(255);
            aFire.setGreen(255);
            aFire.setBlue(0);
            aFire.bDeadIfNoActions = true;

            AlphaToAction aAction = new AlphaToAction(aFire);
            aAction.alphaTo(0, 0.5f);
            aFire.addAction(aAction);

            this.theScene.addSprite(aFire);
        }
    }

    @Override
    public void onDead()
    {
        super.onDead(); //To change body of generated methods, choose Tools | Templates.
        ((SpaceShipScene) this.theScene).deleteAEnemyMissile(this);
    }
}
