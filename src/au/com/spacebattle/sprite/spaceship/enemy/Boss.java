/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite.spaceship.enemy;

import au.com.rmit.Game2dEngine.action.Action;
import au.com.rmit.Game2dEngine.action.ExpandByAction;
import au.com.spacebattle.sprite.missile.BossMainWeaponMissile;
import au.com.spacebattle.sprite.missile.Missile;
import au.com.spacebattle.sprite.other.EnemyFire;
import static java.lang.Math.abs;

/**
 *
 * @author ricolwang
 */
public class Boss extends Enemy
{

    public Boss(double x, double y, double width, double height, double mass, double velocityX, double velocityY)
    {
        super(x, y, width, height, mass, velocityX, velocityY);
    }

    public Boss()
    {
        super("boss-enemy.png");

        this.lifetime = abs(theRandom.nextInt()) % 5 + 5;

        float num = abs(theRandom.nextInt()) % 50;
        float time = abs(theRandom.nextInt()) % 2 + 1;
        ExpandByAction aAction = new ExpandByAction();
        aAction.expandBy(num, time);
        aAction.identifer = "ActionExpand";
        this.addAction(aAction);
        
        this.layer = 4;
    }

    @Override
    public void onActionComplete(Action theAction)
    {
        super.onActionComplete(theAction);

        if (theAction.identifer.equals("ActionExpand"))
        {
            if (theAction instanceof ExpandByAction)
            {
                ExpandByAction action = (ExpandByAction) theAction;
                ExpandByAction aAction = new ExpandByAction();
                float value = action.getExpandBy();
                float time = action.getExpandByDuration();
                aAction.expandBy(-action.getExpandBy(), action.getExpandByDuration() / 1000.0f);
                aAction.identifer = "ActionExpandBack";
                this.addAction(aAction);
            }
        } else if (theAction.identifer.equals("ActionCountdownForFireingBossWeapon"))
        {
            Missile aMissile = new BossMainWeaponMissile("red-enemy-missile.png");
            aMissile.setX(this.getCentreX());
            aMissile.setY(this.getCentreY() + this.getHeight() / 2);
//            aMissile.setVelocityX(abs(theRandom.nextInt()) % 50 + 50);
            aMissile.setVelocityY(abs(theRandom.nextInt()) % 200 + 500);
            aMissile.layer = this.layer;
            this.theScene.addSprite(aMissile);

            EnemyFire aFire = new EnemyFire();
            aFire.setCentreX(aMissile.getCentreX());
            aFire.setCentreY(aMissile.getCentreY() + aMissile.getHeight() / 2);
            aFire.layer = this.layer;
            aFire.setVelocityX(this.velocityX);
            aFire.setVelocityY(this.velocityY);

            this.theScene.addSprite(aFire);
        }
    }

}
