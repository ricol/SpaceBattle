/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite.spaceship.enemy;

import au.com.rmit.Game2dEngine.action.Action;
import au.com.rmit.Game2dEngine.action.CountdownByAction;
import au.com.rmit.Game2dEngine.action.MoveXByAction;
import au.com.rmit.Game2dEngine.action.MoveYByAction;
import au.com.spacebattle.common.Common;
import au.com.spacebattle.sprite.missile.EnemyMissile;
import au.com.spacebattle.sprite.missile.Missile;
import au.com.spacebattle.sprite.other.EnemyFire;
import au.com.spacebattle.sprite.spaceship.Spaceship;
import static java.lang.Math.abs;

/**
 *
 * @author ricolwang
 */
public class Enemy extends Spaceship
{

    public Spaceship theTarget;

    public Enemy(double x, double y, double width, double height, double mass, double velocityX, double velocityY)
    {
        super(x, y, width, height, mass, velocityX, velocityY);
    }

    public Enemy(String imagename)
    {
        super(imagename);

        CountdownByAction aAction = new CountdownByAction();
        aAction.CountdownBy(abs(theRandom.nextFloat()));
        aAction.identifer = "ActionCountdownForFireing";
        this.addAction(aAction);
        
        aAction = new CountdownByAction();
        aAction.CountdownBy(abs(theRandom.nextFloat()) * 3);
        aAction.identifer = "ActionCountdownForFireingBossWeapon";
        this.addAction(aAction);

        aAction = new CountdownByAction();
        aAction.CountdownBy(abs(theRandom.nextFloat()) * 2);
        aAction.identifer = "ActionCountdownForChangingSpeed";
        this.addAction(aAction);
    }

    @Override
    public void onActionComplete(Action theAction)
    {
        if (theAction.identifer.equals("ActionCountdownForFireing"))
        {
            //fire
            Missile aMissile = new EnemyMissile("enemy-missile.png");
            aMissile.setX(this.getCentreX() - aMissile.getWidth() / 2);
            aMissile.setY(this.getCentreY() + this.getHeight() / 2);
//            aMissile.setVelocityX(abs(theRandom.nextInt()) % 50 + 50);
            aMissile.setVelocityY(Common.SPEED_MISSILE_ENEMY);
            aMissile.layer = this.layer;
            this.theScene.addSprite(aMissile);

            EnemyFire aFire = new EnemyFire();
            aFire.setCentreX(aMissile.getCentreX());
            aFire.setCentreY(aMissile.getCentreY() + aMissile.getHeight() / 2);
            aFire.layer = this.layer;
            aFire.setVelocityX(this.velocityX);
            aFire.setVelocityY(this.velocityY);

            this.theScene.addSprite(aFire);

            CountdownByAction aNewAction = new CountdownByAction();
            aNewAction.CountdownBy(abs(theRandom.nextFloat()) * 2);
            aNewAction.identifer = "ActionCountdownForFireing";
            this.addAction(aNewAction);
        } else if (theAction.identifer.equals("ActionCountdownForChangingSpeed"))
        {
            //change speed
            float time = abs(theRandom.nextInt()) % 2 + 15;
            float valueX = abs(theRandom.nextInt()) % Common.SPEED_ENEMY_SHIP_CHANGE_X + Common.SPEED_ENEMY_SHIP_X;
            if (this.theTarget != null)
            {
                float x = (float) this.theTarget.getX();
                valueX = (float) (x - this.getX());
            }

            MoveXByAction aMoveXByAction = new MoveXByAction();
            aMoveXByAction.moveXBy(valueX, time);
            this.addAction(aMoveXByAction);

            float valueY = abs(theRandom.nextInt()) % Common.SPEED_ENEMY_SHIP_CHANGE_Y + Common.SPEED_ENEMY_SHIP_Y;
            if (this.theTarget != null)
            {
                float y = (float) this.theTarget.getY();
                valueY = (float) (y - this.getY());
            }

            MoveYByAction aMoveYByAction = new MoveYByAction();
            aMoveYByAction.moveYBy(valueY, time);
            this.addAction(aMoveYByAction);

            CountdownByAction aCountdownByAction = new CountdownByAction();
            aCountdownByAction.CountdownBy(time + abs(theRandom.nextFloat()) * 2);
            aCountdownByAction.identifer = "ActionCountdownForChangingSpeed";
            this.addAction(aCountdownByAction);
        }
    }

    @Override
    public void onDead()
    {
        this.theTarget = null;
    }
}
