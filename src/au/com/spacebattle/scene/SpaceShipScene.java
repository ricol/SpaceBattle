/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.scene;

import au.com.rmit.Game2dEngine.node.LabelSprite;
import au.com.rmit.Game2dEngine.node.Sprite;
import au.com.rmit.Game2dEngine.scene.Scene;
import au.com.spacebattle.common.Common;
import au.com.spacebattle.sprite.spaceship.enemy.Boss;
import au.com.spacebattle.sprite.spaceship.enemy.Enemy;
import au.com.spacebattle.sprite.spaceship.friend.MySpaceship;
import static com.sun.org.apache.xalan.internal.lib.ExsltMath.power;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Math.abs;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author ricolwang
 */
public class SpaceShipScene extends Scene implements ActionListener
{

    boolean bUp;
    public MySpaceship theShip;
    public LabelSprite lblEnemyKilled;
    public LabelSprite lblBossKilled;
    public LabelSprite lblMyLife;

    int enemyKilled = 0;
    int bossKilled = 0;
    int mylife = 3;

    Timer timerForFire = new Timer(200, this);
    Timer timerForFirMainWeapon = new Timer(300, this);
    Timer timerForEnemy = new Timer(600, this);

    public SpaceShipScene()
    {
//        try
//        {
//            this.theImageBackground = ImageIO.read(new File("space.jpg"));
//        } catch (IOException ex)
//        {
//        }

        theShip = new MySpaceship();
        theShip.lifetime = Sprite.EVER;

        theShip.setX(getWidth() / 2.0);
        theShip.setY(getHeight() * (3 / 4.0));

        addSprite(theShip);

        this.timerForEnemy.start();
        this.timerForFire.start();
        this.timerForFirMainWeapon.start();

        new Thread()
        {
            public void run()
            {
                try
                {
                    Thread.sleep(1000);
                } catch (InterruptedException ex)
                {
                }

                SwingUtilities.invokeLater(new Runnable()
                {
                    public void run()
                    {
                        addLabels();
                    }
                });
            }
        }.start();
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
        aBoss.bAutoAdjustGesture = true;
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
//        if (index == 1) 
        aEnemy.bAutoAdjustGesture = true;

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

    private void addLabels()
    {
        int tmpY = 10;
        int tmpMarginRight = 140;
        int tmpWidth = 150;
        int tmpHeight = 20;
        int tmpGap = 1;

        lblMyLife = new LabelSprite(this.getWidth() - tmpMarginRight, tmpY, "My Life: " + this.mylife, null);

        lblMyLife.setWidth(tmpWidth);

        lblMyLife.setHeight(tmpHeight);

        lblMyLife.setRed(
            255);
        lblMyLife.bTextFrame = false;
        lblMyLife.layer = Common.LAYER_TEXT;

        addSprite(lblMyLife);

        lblEnemyKilled = new LabelSprite(this.getWidth() - tmpMarginRight, tmpY + tmpHeight + tmpGap, "Enemy Killed: " + this.enemyKilled, null);

        lblEnemyKilled.setWidth(tmpWidth);

        lblEnemyKilled.setHeight(tmpHeight);

        lblEnemyKilled.setRed(
            255);
        lblEnemyKilled.bTextFrame = false;
        lblEnemyKilled.layer = Common.LAYER_TEXT;

        addSprite(lblEnemyKilled);

        lblBossKilled = new LabelSprite(this.getWidth() - tmpMarginRight, tmpY + (tmpHeight + tmpGap) * 2, "Boss Killed: " + this.bossKilled, null);

        lblBossKilled.setWidth(tmpWidth);

        lblBossKilled.setHeight(tmpHeight);

        lblBossKilled.setRed(
            255);
        lblBossKilled.bTextFrame = false;
        lblBossKilled.layer = Common.LAYER_TEXT;

        addSprite(lblBossKilled);
    }

    void adjustLabelPos()
    {
        int tmpY = 10;
        int tmpMarginRight = 140;
        int tmpHeight = 20;
        int tmpGap = 1;
        if (lblMyLife != null)
        {
            lblMyLife.setX(this.getWidth() - tmpMarginRight);
            lblMyLife.setY(tmpY);
        }
        if (lblEnemyKilled != null)
        {
            lblEnemyKilled.setX(this.getWidth() - tmpMarginRight);
            lblEnemyKilled.setY(tmpY + tmpHeight + tmpGap);
        }
        if (lblBossKilled != null)
        {
            lblBossKilled.setX(this.getWidth() - tmpMarginRight);
            lblBossKilled.setY(tmpY + (tmpHeight + tmpGap) * 2);
        }
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

    public void killAEnemy()
    {
        this.enemyKilled++;
        this.lblEnemyKilled.setText("Enemy Killed: " + this.enemyKilled);
    }

    public void killABoss()
    {
        this.bossKilled++;
        this.lblBossKilled.setText("Boss Killed: " + this.bossKilled);
    }

    public void lostALife()
    {
        this.mylife--;
        this.lblMyLife.setText("My Life: " + this.mylife);
    }

    @Override
    public void setSize(Dimension d)
    {
        super.setSize(d); //To change body of generated methods, choose Tools | Templates.

        this.adjustLabelPos();
    }

}
