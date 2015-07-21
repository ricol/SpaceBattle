/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite.spaceship.enemy;

import au.com.rmit.Game2dEngine.action.Action;
import au.com.rmit.Game2dEngine.action.CountdownByAction;
import au.com.rmit.Game2dEngine.action.ExpandByAction;
import au.com.rmit.Game2dEngine.node.Sprite;
import au.com.spacebattle.common.Common;
import au.com.spacebattle.sprite.missile.BossMainWeaponMissile;
import au.com.spacebattle.sprite.missile.MainWeapanFriendMissile;
import au.com.spacebattle.sprite.missile.Missile;
import au.com.spacebattle.sprite.missile.NormalWeanponFriendMissile;
import au.com.spacebattle.sprite.other.EnemyFire;
import au.com.spacebattle.sprite.spaceship.friend.MySpaceship;
import static java.lang.Math.abs;

/**
 *
 * @author ricolwang
 */
public class Boss extends Enemy
{

    public Boss()
    {
        super("PlaneBoss.png");

        this.lifetime = abs(theRandom.nextInt()) % 5 + 20;

        float num = abs(theRandom.nextInt()) % 50;
        float time = abs(theRandom.nextInt()) % 2 + 1;
        ExpandByAction aAction = new ExpandByAction();
        aAction.expandBy(num, time);
        aAction.identifer = "ActionExpand";
        this.addAction(aAction);

        this.layer = Common.LAYER_BOSS_SHIP;
        this.setLife(500);
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
            aMissile.setVelocityY(Common.SPEED_MAIN_MISSILE_ENEMY);
            aMissile.layer = this.layer;
            this.theScene.addSprite(aMissile);

            EnemyFire aFire = new EnemyFire();
            aFire.setCentreX(aMissile.getCentreX());
            aFire.setCentreY(aMissile.getCentreY() + aMissile.getHeight() / 2);
            aFire.layer = this.layer;
            aFire.setVelocityX(this.velocityX);
            aFire.setVelocityY(this.velocityY);

            this.theScene.addSprite(aFire);

            CountdownByAction aAction = new CountdownByAction();
            aAction.CountdownBy(abs(theRandom.nextFloat()) * 3);
            aAction.identifer = "ActionCountdownForFireingBossWeapon";
            this.addAction(aAction);
        }
    }

    @Override
    public void onCollideWith(Sprite target)
    {
        if (target instanceof MainWeapanFriendMissile)
        {
            this.decreaseLife(100);
            System.out.println("Hit Boss with main weapon...enemy life left: " + this.getLife());
        } else if (target instanceof NormalWeanponFriendMissile)
        {
            this.decreaseLife(20);
            System.out.println("Hit Boss with normal weapon...enemy life left: " + this.getLife());
        } else if (target instanceof MySpaceship)
        {
            this.decreaseLife(200);
            System.out.println("Hit Boss with my space ship...enemy life left: " + this.getLife());
        }
    }
}
