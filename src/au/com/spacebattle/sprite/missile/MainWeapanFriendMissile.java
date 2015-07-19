/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite.missile;

import au.com.rmit.Game2dEngine.action.AlphaToAction;
import au.com.spacebattle.common.Common;
import au.com.spacebattle.sprite.other.ExpodeParticle;
import static com.sun.org.apache.xalan.internal.lib.ExsltMath.power;
import static java.lang.Math.abs;

/**
 *
 * @author ricolwang
 */
public class MainWeapanFriendMissile extends FriendMissile
{

    public MainWeapanFriendMissile(String imagename)
    {
        super(imagename);
    }

    @Override
    public void explode()
    {
        int number = abs(theRandom.nextInt()) % 10 + 50;

        for (int i = 0; i < number; i++)
        {
            double tmpX = power(-1, theRandom.nextInt() % 10) * theRandom.nextFloat() * Common.SPEED_EXPLODE_PARTICLE;
            double tmpY = power(-1, theRandom.nextInt() % 10) * theRandom.nextFloat() * Common.SPEED_EXPLODE_PARTICLE;

            ExpodeParticle aFire = new ExpodeParticle();
            aFire.setX(this.getCentreX());
            aFire.setY(this.getCentreY());
            aFire.setVelocityX(tmpX);
            aFire.setVelocityY(tmpY);
            aFire.setRed(0);
            aFire.setGreen(0);
            aFire.setBlue(255);
            aFire.bDeadIfNoActions = true;
            
            AlphaToAction aAction = new AlphaToAction(aFire);
            aAction.alphaTo(0, 0.3f);
            aFire.addAction(aAction);
            
            this.theScene.addSprite(aFire);
        }
    }

}
