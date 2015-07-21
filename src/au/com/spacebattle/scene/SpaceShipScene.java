/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.scene;

import au.com.rmit.Game2dEngine.node.Sprite;
import au.com.rmit.Game2dEngine.scene.Scene;
import au.com.spacebattle.common.Common;
import au.com.spacebattle.sprite.spaceship.enemy.Boss;
import au.com.spacebattle.sprite.spaceship.enemy.Enemy;
import au.com.spacebattle.sprite.spaceship.friend.MySpaceship;
import static com.sun.org.apache.xalan.internal.lib.ExsltMath.power;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Math.abs;
import javax.swing.Timer;

/**
 *
 * @author ricolwang
 */
public class SpaceShipScene extends Scene implements ActionListener
{

    boolean bUp;
    public MySpaceship theShip;

    Timer timerForFire = new Timer(200, this);
    Timer timerForFirMainWeapon = new Timer(300, this);
    Timer timerForEnemy = new Timer(400, this);

    public SpaceShipScene()
    {
//        try
//        {
//            this.theImageBackground = ImageIO.read(new File("space.jpg"));
//        } catch (IOException ex)
//        {
//            Logger.getLogger(FountainScene.class.getName()).log(Level.SEVERE, null, ex);
//        }

        this.theShip = new MySpaceship();
        this.theShip.lifetime = Sprite.EVER;

        this.theShip.setX(this.getWidth() / 2.0);
        this.theShip.setY(this.getHeight() * (3 / 4.0));

        this.addSprite(theShip);

        this.timerForEnemy.start();
        this.timerForFire.start();
        this.timerForFirMainWeapon.start();

//        Timer aTimer = new Timer(100, new ActionListener()
//        {
//
//            @Override
//            public void actionPerformed(ActionEvent e)
//            {
//                int tmpBlue = getBlue();
//                if (bUp)
//                {
//                    ++tmpBlue;
//                    if (tmpBlue > 50)
//                    {
//                        tmpBlue = 50;
//                        bUp = false;
//                    }
//                } else
//                {
//                    --tmpBlue;
//                    if (tmpBlue < 0)
//                    {
//                        tmpBlue = 0;
//                        bUp = true;
//                    }
//                }
//
//                setBlue(tmpBlue);
//            }
//        });
//        aTimer.start();
    }

    public void addABoss()
    {
        Boss aBoss = new Boss();
        boolean b = theRandom.nextBoolean();
        int index = b ? 1 : 0;
        index = (int) power(-1, index);
        int size = (int) (this.getWidth() * (1 / 4.0));

        aBoss.setX(this.getWidth() / 2 + index * abs(theRandom.nextInt()) % size);
        aBoss.setY(-100);

        b = theRandom.nextBoolean();
        index = b ? 1 : 0;
        index = (int) power(-1, index);

        float velocityX = index * abs(theRandom.nextInt()) % Common.SPEED_ENEMY_SHIP_CHANGE_X + Common.SPEED_ENEMY_SHIP_X;
        float velocttyY = abs(theRandom.nextInt()) % Common.SPEED_ENEMY_SHIP_CHANGE_Y + Common.SPEED_ENEMY_SHIP_Y;

        aBoss.setVelocityX(velocityX / 2);
        aBoss.setVelocityY(velocttyY / 2);

        aBoss.lifetime = 10;

        aBoss.theTarget = this.theShip;
        this.addSprite(aBoss);
    }

    public void addAEnemy()
    {
        String[] data = new String[]
        {
            "Plane1.png", "Plane2.png", "Plane3.png", "Plane4.png", "Plane5.png", "Plane6.png", "Plane7.png", "Plane8.png"
        };

        int index = abs(theRandom.nextInt()) % data.length;
        Enemy aEnemy = new Enemy(data[index]);
        boolean b = theRandom.nextBoolean();
        index = b ? 1 : 0;
        index = (int) power(-1, index);
        int size = (int) (this.getWidth() * (1 / 4.0));

        aEnemy.setX(this.getWidth() / 2 + index * abs(theRandom.nextInt()) % size);
        aEnemy.setY(-100);

        b = theRandom.nextBoolean();
        index = b ? 1 : 0;
        index = (int) power(-1, index);

        float velocityXTmp = index * abs(theRandom.nextInt()) % Common.SPEED_ENEMY_SHIP_CHANGE_X + Common.SPEED_ENEMY_SHIP_X;
        float velocttyYTmp = abs(theRandom.nextInt()) % Common.SPEED_ENEMY_SHIP_CHANGE_Y + Common.SPEED_ENEMY_SHIP_Y;

        aEnemy.setVelocityX(velocityXTmp);
        aEnemy.setVelocityY(velocttyYTmp);
        aEnemy.lifetime = 10;

        aEnemy.theTarget = this.theShip;
        this.addSprite(aEnemy);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource().equals(this.timerForEnemy))
        {
            if (abs(theRandom.nextInt()) % 100 > 80)
            {
                this.addABoss();
            } else
            {
                this.addAEnemy();
            }
        } else if (e.getSource().equals(this.timerForFire))
        {
            if (this.theShip.bAutoshot)
            {
                this.theShip.fire();
            }
        } else if (e.getSource().equals(this.timerForFirMainWeapon))
        {
            if (this.theShip.bAutoshot)
            {
                this.theShip.fireMainWeapon();
            }
        }
    }
}
