/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.game.spacebattle.scene;

import com.sun.glass.events.KeyEvent;
import com.wang.Game2dEngine.action.AlphaByAction;
import com.wang.Game2dEngine.action.AlphaToAction;
import com.wang.Game2dEngine.action.MoveYToAction;
import com.wang.Game2dEngine.monitor.InputMonitor;
import com.wang.Game2dEngine.scene.Scene;
import com.wang.Game2dEngine.sprite.Sprite;
import com.wang.Game2dEngine.sprite.UI.SLabel;
import com.wang.game.spacebattle.common.Common;
import com.wang.game.spacebattle.sprite.other.Score;
import com.wang.game.spacebattle.sprite.other.SpaceBackground;
import com.wang.game.spacebattle.sprite.spaceship.enemy.Boss;
import com.wang.game.spacebattle.sprite.spaceship.enemy.Enemy;
import com.wang.game.spacebattle.sprite.spaceship.friend.MySpaceship;
import com.wang.game.spacebattle.sprite.spaceship.weapon.missile.BossAutoFollowMissile;
import com.wang.game.spacebattle.sprite.spaceship.weapon.missile.EnemyAutoFollowMissile;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Math.abs;
import static java.lang.Math.pow;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author ricolwang
 */
public class SpaceShipScene extends Scene implements ActionListener, InputMonitor.IKeyTyped
{

    public boolean bGameRunning;
    public boolean bMouseControl = false;
    boolean bUp;
    public MySpaceship theShip;
    public SLabel lblEnemyKilled;
    public SLabel lblBossKilled;
    public SLabel lblMyLife;
    public SLabel lblScore;
    public SLabel lblHelpLeft;
    public SLabel lblHelpRight;
    SpaceBackground theBackgroundFirst;
    SpaceBackground theBackgroundSecond;

    int enemyKilled = 0;
    int bossKilled = 0;
    int mylife = 3;
    int score = 0;

    Timer timerForFire = new Timer(200, this);
    Timer timerForFirMainWeapon = new Timer(200, this);
    Timer timerForEnemy = new Timer(300, this);

    ArrayList<Boss> allBosses = new ArrayList<>();
    ArrayList<Enemy> allEnemies = new ArrayList<>();
    ArrayList<BossAutoFollowMissile> allBossMissiles = new ArrayList<>();
    ArrayList<EnemyAutoFollowMissile> allEnemyMissiles = new ArrayList<>();

    public SpaceShipScene()
    {
        this.enableCollisionDetect();

//        try
//        {
//            this.theImageBackground = ImageIO.read(new File("space.jpg"));
//        } catch (IOException ex)
//        {
//        }
//        this.theBackgroundFirst = new SpaceBackground();
//        this.addSprite(this.theBackgroundFirst);
//
//        this.theBackgroundSecond = new SpaceBackground();
//        this.theBackgroundSecond.setY((this.theBackgroundSecond.getHeight() + this.getHeight())* -1);
//        this.addSprite(this.theBackgroundSecond);
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
        index = (int) pow(-1, index);
        int size = (int) (this.getWidth() * (1 / 4.0));

        aBoss.setX(this.getWidth() / 2 + index * abs(theRandom.nextInt()) % size);
        aBoss.setY(-100);

        b = theRandom.nextBoolean();
        index = b ? 1 : 0;
        index = (int) pow(-1, index);

        float velocityX = index * abs(theRandom.nextInt()) % Common.SPEED_ENEMY_SHIP_CHANGE_X + Common.SPEED_ENEMY_SHIP_X;
        float velocttyY = abs(theRandom.nextInt()) % Common.SPEED_ENEMY_SHIP_CHANGE_Y + Common.SPEED_ENEMY_SHIP_Y;

        aBoss.setVelocityX(velocityX / 2);
        aBoss.setVelocityY(velocttyY / 2);

        aBoss.setLifeTime(10);

        aBoss.theTarget = this.theShip;
        aBoss.bAutoAdjustGesture = true;
        this.addSprite(aBoss);

        this.addABoss(aBoss);
    }

    public void addAEnemy()
    {
        String[] data = new String[]
        {
            "Plane1.png", "Plane2.png", "Plane3.png", "Plane4.png", "Plane5.png", "Plane6.png", "Plane7.png", "Plane8.png"
        };

        int index = abs(theRandom.nextInt()) % data.length;
        Enemy aEnemy = new Enemy("resource/" + data[index]);
//        if (index == 1) 
        aEnemy.bAutoAdjustGesture = true;

        boolean b = theRandom.nextBoolean();
        index = b ? 1 : 0;
        index = (int) pow(-1, index);
        int size = (int) (this.getWidth() * (1 / 4.0));

        aEnemy.setX(this.getWidth() / 2 + index * abs(theRandom.nextInt()) % size);
        aEnemy.setY(-100);

        b = theRandom.nextBoolean();
        index = b ? 1 : 0;
        index = (int) pow(-1, index);

        float velocityXTmp = index * abs(theRandom.nextInt()) % Common.SPEED_ENEMY_SHIP_CHANGE_X + Common.SPEED_ENEMY_SHIP_X;
        float velocttyYTmp = abs(theRandom.nextInt()) % Common.SPEED_ENEMY_SHIP_CHANGE_Y + Common.SPEED_ENEMY_SHIP_Y;

        aEnemy.setVelocityX(velocityXTmp);
        aEnemy.setVelocityY(velocttyYTmp);
        aEnemy.setLifeTime(10);

        aEnemy.theTarget = this.theShip;
        this.addSprite(aEnemy);

        this.addAEnemy(aEnemy);
    }

    private void addLabels()
    {
        int tmpWidth = 150;
        int tmpHeight = 20;

        lblMyLife = new SLabel(0, 0, "My Life: " + this.mylife, null);

        lblMyLife.setWidth(tmpWidth);

        lblMyLife.setHeight(tmpHeight);

        lblMyLife.setRed(
                255);
        lblMyLife.bTextFrame = false;
        lblMyLife.setLayer(Common.LAYER_TEXT);

        addSprite(lblMyLife);

        lblEnemyKilled = new SLabel(0, 0, "Enemy Killed: " + this.enemyKilled, null);

        lblEnemyKilled.setWidth(tmpWidth);

        lblEnemyKilled.setHeight(tmpHeight);

        lblEnemyKilled.setRed(
                255);
        lblEnemyKilled.bTextFrame = false;
        lblEnemyKilled.setLayer(Common.LAYER_TEXT);

        addSprite(lblEnemyKilled);

        lblBossKilled = new SLabel(0, 0, "Boss Killed: " + this.bossKilled, null);

        lblBossKilled.setWidth(tmpWidth);

        lblBossKilled.setHeight(tmpHeight);

        lblBossKilled.setRed(
                255);
        lblBossKilled.bTextFrame = false;
        lblBossKilled.setLayer(Common.LAYER_TEXT);

        addSprite(lblBossKilled);

        lblScore = new SLabel(0, 0, "Score: " + this.score, null);

        lblScore.setWidth(tmpWidth);

        lblScore.setHeight(tmpHeight);

        lblScore.setRed(
                255);
        lblScore.bTextFrame = false;
        lblScore.setLayer(Common.LAYER_TEXT);

        addSprite(lblScore);

        //add usage
        int tmpBottom = 25;
        int tmpMarginRight = 140;
        int tmpGap = 1;
        lblHelpLeft = new SLabel(this.getWidth() - tmpMarginRight, this.getHeight() - (tmpBottom + tmpGap) * 2, "Left Mouse - Fire", null);
        lblHelpLeft.setWidth(tmpWidth);
        lblHelpLeft.setHeight(tmpHeight);
        lblHelpLeft.setRed(255);
        lblHelpLeft.bTextFrame = false;
        lblHelpLeft.setLayer(Common.LAYER_TEXT);

        addSprite(lblHelpLeft);

        lblHelpRight = new SLabel(this.getWidth() - tmpMarginRight, this.getHeight() - (tmpBottom + tmpGap), "Right Mouse - Laser", null);
        lblHelpRight.setWidth(tmpWidth);
        lblHelpRight.setHeight(tmpHeight);
        lblHelpRight.setRed(255);
        lblHelpRight.bTextFrame = false;
        lblHelpRight.setLayer(Common.LAYER_TEXT);

        addSprite(lblHelpRight);

        this.adjustLabelPos();
    }

    public void adjustLabelPos()
    {
        int tmpY = 20;
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
        if (lblScore != null)
        {
            lblScore.setX(this.getWidth() - tmpMarginRight);
            lblScore.setY(tmpY + (tmpHeight + tmpGap) * 3);
        }
        if (lblHelpLeft != null)
        {
            int tmpBottom = 25;
            lblHelpLeft.setX(this.getWidth() - tmpMarginRight);
            lblHelpLeft.setY(this.getHeight() - (tmpBottom + tmpGap) * 2);
        }
        if (lblHelpRight != null)
        {
            int tmpBottom = 25;
            lblHelpRight.setX(this.getWidth() - tmpMarginRight);
            lblHelpRight.setY(this.getHeight() - (tmpBottom + tmpGap));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (!this.bGameRunning)
        {
            return;
        }

        if (e.getSource().equals(this.timerForEnemy))
        {
            if (abs(theRandom.nextInt()) % 100 > 90)
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

    public void killAEnemy(Enemy aEnemy)
    {
        Score aScore = new Score("+" + Common.SCORE_ENEMY);
        aScore.setCentreX(aEnemy.getCentreX());
        aScore.setCentreY(aEnemy.getCentreY());
        aScore.setWidth(50);
        aScore.setHeight(15);
        this.addSprite(aScore);

        this.score += Common.SCORE_ENEMY;

        this.enemyKilled++;
        this.updateLabels();
    }

    public void killABoss(Boss aBoss)
    {
        Score aScore = new Score("+" + Common.SCORE_BOSS);
        aScore.setCentreX(aBoss.getCentreX());
        aScore.setCentreY(aBoss.getCentreY());
        aScore.setWidth(50);
        aScore.setHeight(15);
        this.addSprite(aScore);

        this.score += Common.SCORE_BOSS;

        this.bossKilled++;
        this.updateLabels();
    }

    public void lostALife()
    {
        this.mylife--;
        this.updateLabels();
    }

    public void updateLabels()
    {
        if (this.lblBossKilled != null)
        {
            this.lblBossKilled.setText("Boss Killed: " + this.bossKilled);
        }
        if (this.lblEnemyKilled != null)
        {
            this.lblEnemyKilled.setText("Enemy Killed: " + this.enemyKilled);
        }
        if (this.lblScore != null)
        {
            this.lblScore.setText("Score: " + this.score);
        }
        if (this.lblMyLife != null)
        {
            this.lblMyLife.setText("My Life: " + this.mylife);
        }
    }

    @Override
    public void setSize(Dimension d)
    {
        super.setSize(d); //To change body of generated methods, choose Tools | Templates.

        this.adjustLabelPos();
    }

    public Enemy getARandomEnemy()
    {
        if (this.allEnemies.size() > 0)
        {
            int index = abs(theRandom.nextInt()) % this.allEnemies.size();
            return this.allEnemies.get(index);
        } else
        {
            return null;
        }
    }

    public Boss getARandomBoss()
    {
        if (this.allBosses.size() > 0)
        {
            int index = abs(theRandom.nextInt()) % this.allBosses.size();
            return this.allBosses.get(index);
        } else
        {
            return null;
        }
    }

    public EnemyAutoFollowMissile getARandomEnemyMissile()
    {
        if (this.allEnemyMissiles.size() > 0)
        {
            int index = abs(theRandom.nextInt()) % this.allEnemyMissiles.size();
            return this.allEnemyMissiles.get(index);
        } else
        {
            return null;
        }
    }

    public BossAutoFollowMissile getARandomBossMissile()
    {
        if (this.allBossMissiles.size() > 0)
        {
            int index = abs(theRandom.nextInt()) % this.allBossMissiles.size();
            return this.allBossMissiles.get(index);
        } else
        {
            return null;
        }
    }

    public void addABoss(Boss aBoss)
    {
        this.allBosses.add(aBoss);
    }

    public void deleteABoss(Boss aBoss)
    {
        this.allBosses.remove(aBoss);
    }

    public void addAEnemy(Enemy aEnemy)
    {
        this.allEnemies.add(aEnemy);
    }

    public void deleteAEnemy(Enemy aEnemy)
    {
        this.allEnemies.remove(aEnemy);
    }

    public void addAEnemyMissile(EnemyAutoFollowMissile aMissile)
    {
        this.allEnemyMissiles.add(aMissile);
    }

    public void deleteAEnemyMissile(EnemyAutoFollowMissile aMissile)
    {
        this.allEnemyMissiles.remove(aMissile);
    }

    public void addABossMissile(BossAutoFollowMissile aMissile)
    {
        this.allBossMissiles.add(aMissile);
    }

    public void deleteABossMissile(BossAutoFollowMissile aMissile)
    {
        this.allBossMissiles.remove(aMissile);
    }

    public Enemy getARandomTarget()
    {
        ArrayList<Enemy> tmpAllEnemies = new ArrayList<>();
        tmpAllEnemies.addAll(this.allBosses);
        tmpAllEnemies.addAll(this.allEnemies);

        Enemy aEnemy = null;
        if (tmpAllEnemies.size() > 0)
        {
            int index = abs(theRandom.nextInt()) % tmpAllEnemies.size();
            aEnemy = tmpAllEnemies.get(index);
            tmpAllEnemies.clear();
        }

        return aEnemy;
    }

    public void gameStart()
    {
        enemyKilled = 0;
        bossKilled = 0;
        mylife = 3;
        score = 0;
        this.updateLabels();

        SLabel aLabel = new SLabel("Game Start", new Font("TimesRoman", Font.PLAIN, 30));
        aLabel.setWidth(150);
        aLabel.setHeight(30);
        aLabel.textPosY = 25;
        aLabel.setVelocityY(-50);
        aLabel.bTextFrame = true;
        aLabel.bDeadIfNoActions = true;
        aLabel.setCentreX(this.getWidth() / 2);
        aLabel.setCentreY(this.getHeight() / 2);

        AlphaToAction aAction = new AlphaToAction(aLabel);
        aAction.alphaTo(0, 1.5f);
        aLabel.addAction(aAction);

        this.addSprite(aLabel);

        theShip = new MySpaceship();
        theShip.setLifeTime(Sprite.EVER);
        theShip.bAutoMissile = false;

        theShip.setCentreX(this.getWidth() / 2.0f);
        theShip.setCentreY(this.getHeight());

        MoveYToAction aMoveAction = new MoveYToAction(theShip);
        aMoveAction.moveYTo(getHeight() - theShip.getHeight() * 2, 1);
        theShip.addAction(aMoveAction);

        addSprite(theShip);

        bGameRunning = true;

        new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(2000);
                } catch (InterruptedException ex)
                {
                    Logger.getLogger(SpaceShipScene.class.getName()).log(Level.SEVERE, null, ex);
                }

                SwingUtilities.invokeLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        timerForEnemy.start();
                        timerForFire.start();
                        timerForFirMainWeapon.start();
                        theShip.bAutoMissile = true;
                    }
                });

            }

        }).start();

    }

    public void gameEnd()
    {
        this.timerForEnemy.stop();
        this.timerForFirMainWeapon.stop();
        this.timerForFire.stop();

        if (this.theShip != null)
        {
            AlphaByAction aAction = new AlphaByAction();
            aAction.alphaBy(-1, 1);
            theShip.addAction(aAction);

            theShip.setVelocityY(-500);
            theShip.bDeadIfNoActions = true;
        }

        for (Boss aBoss : this.allBosses)
        {
            AlphaByAction aAction = new AlphaByAction();
            aAction.alphaBy(-1, 1);
            aBoss.addAction(aAction);
            aBoss.bDeadIfNoActions = true;
        }

        for (Enemy aEnemy : this.allEnemies)
        {
            AlphaByAction aAction = new AlphaByAction();
            aAction.alphaBy(-1, 1);
            aEnemy.addAction(aAction);
            aEnemy.bDeadIfNoActions = true;
        }

        enemyKilled = 0;
        bossKilled = 0;
        mylife = 3;
        score = 0;
        this.updateLabels();

        SLabel aLabel = new SLabel("Game End", new Font("TimesRoman", Font.PLAIN, 30));
        aLabel.setWidth(150);
        aLabel.setHeight(30);
        aLabel.textPosY = 25;
        aLabel.setVelocityY(-50);
        aLabel.bTextFrame = true;
        aLabel.bDeadIfNoActions = true;
        aLabel.setCentreX(this.getWidth() / 2);
        aLabel.setCentreY(this.getHeight() / 2);

        AlphaToAction aAlphaAction = new AlphaToAction(aLabel);
        aAlphaAction.alphaTo(0, 1.5f);
        aLabel.addAction(aAlphaAction);

        this.addSprite(aLabel);
        this.bGameRunning = false;
    }

    public void gamePause()
    {
        SLabel aLabel = new SLabel("Game Pause", new Font("TimesRoman", Font.PLAIN, 20));
        aLabel.setWidth(100);
        aLabel.setHeight(20);
        aLabel.bTextFrame = false;
        aLabel.bDeadIfNoActions = true;
        aLabel.setCentreX(this.getWidth() / 2);
        aLabel.setCentreY(this.getHeight() / 2);

        AlphaToAction aAction = new AlphaToAction(aLabel);
        aAction.alphaTo(0, 1.5f);
        aLabel.addAction(aAction);

        this.addSprite(aLabel);
        this.bGameRunning = false;
    }

    @Override
    protected void didUpdateModel()
    {
        super.didUpdateModel(); //To change body of generated methods, choose Tools | Templates.
        if (bGameRunning)
        {
            if (InputMonitor.getSharedInstance().rightButtonPressed)
                theShip.openLaser();

            if (InputMonitor.getSharedInstance().leftButtonPressed)
                theShip.bAutoshot = !theShip.bAutoshot;

            if (bMouseControl)
            {
                if (InputMonitor.getSharedInstance().mouseEntered)
                {
                    int x = InputMonitor.getSharedInstance().MouseX;
                    int y = InputMonitor.getSharedInstance().MouseY;
                    if ((abs(theShip.getX() - x) > 0.1)
                            || (abs(theShip.getY() - y) > 0.1))
                    {
                        theShip.moveToXYInSequence(x, y, 0.1f);
                    }
                }
            } else
            {
                InputMonitor monitor = InputMonitor.getSharedInstance();

                //control ship by w, a, s, d keys
                double velocity = 250;
                double velocityX = 0;
                double velocityY = 0;

                if (monitor.isKeyPressed(KeyEvent.VK_A))
                {
                    velocityX -= velocity;
                }

                if (monitor.isKeyPressed(KeyEvent.VK_D))
                {
                    velocityX += velocity;
                }

                if (monitor.isKeyPressed(KeyEvent.VK_S))
                {
                    velocityY += velocity;
                }

                if (monitor.isKeyPressed(KeyEvent.VK_W))
                {
                    velocityY -= velocity;
                }

                theShip.setVelocityX(velocityX);
                theShip.setVelocityY(velocityY);
            }
        }
    }

    @Override
    public void inputMonitorKeyTyped(char key)
    {
        switch (key)
        {
            case KeyEvent.VK_SPACE:
                this.theShip.openLaser();
                break;
            case KeyEvent.VK_ESCAPE:
                this.bMouseControl = !this.bMouseControl;
                break;
            default:
                break;
        }
    }

}
