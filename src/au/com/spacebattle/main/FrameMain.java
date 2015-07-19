package au.com.spacebattle.main;

import au.com.rmit.Game2dEngine.director.Director;
import au.com.rmit.Game2dEngine.node.Sprite;
import au.com.spacebattle.scene.SpaceShipScene;
import au.com.spacebattle.scene.TestScene;
import au.com.spacebattle.sprite.spaceship.enemy.BlueEnemy;
import au.com.spacebattle.sprite.spaceship.enemy.Boss;
import au.com.spacebattle.sprite.spaceship.enemy.Enemy;
import au.com.spacebattle.sprite.spaceship.enemy.GreenEnemy;
import au.com.spacebattle.sprite.missile.Missile;
import au.com.spacebattle.sprite.spaceship.enemy.PurpleEnemy;
import au.com.spacebattle.sprite.spaceship.friend.MySpaceship;
import static com.sun.org.apache.xalan.internal.lib.ExsltMath.power;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import static java.lang.Math.abs;
import java.util.Random;
import javax.swing.Timer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Philology
 */
public class FrameMain extends javax.swing.JFrame implements KeyListener, ActionListener, MouseListener, MouseMotionListener
{

    Random theRandom = new Random();
    SpaceShipScene theScene;
    MySpaceship theShip;

    char c;
    Timer keyTimer = new Timer(200, this);
    Timer timerForEnemy = new Timer(400, this);
    Timer timerForFire = new Timer(200, this);
    Timer timerForFirMainWeapon = new Timer(300, this);

    public FrameMain()
    {
        initComponents();

        this.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent evt)
            {
                int x = 10, y = 10;
                panelGame.setLocation(x, y);
                panelGame.setSize(new Dimension(getWidth() - 2 * x, getHeight() - 4 * y));
                Director.getSharedInstance().updatePosition(0, 0, panelGame.getWidth(), panelGame.getHeight());
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        panelGame = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowOpened(java.awt.event.WindowEvent evt)
            {
                formWindowOpened(evt);
            }
            public void windowActivated(java.awt.event.WindowEvent evt)
            {
                formWindowActivated(evt);
            }
        });

        panelGame.setBackground(new java.awt.Color(0, 0, 102));

        javax.swing.GroupLayout panelGameLayout = new javax.swing.GroupLayout(panelGame);
        panelGame.setLayout(panelGameLayout);
        panelGameLayout.setHorizontalGroup(
            panelGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 756, Short.MAX_VALUE)
        );
        panelGameLayout.setVerticalGroup(
            panelGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 577, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelGame, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelGame, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowOpened
    {//GEN-HEADEREND:event_formWindowOpened
        Director.getSharedInstance().setParent(this.panelGame);
    }//GEN-LAST:event_formWindowOpened

    private void formWindowActivated(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowActivated
    {//GEN-HEADEREND:event_formWindowActivated
        // TODO add your handling code here:
//        this.launchTest();
        this.launchGame();

    }//GEN-LAST:event_formWindowActivated

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panelGame;
    // End of variables declaration//GEN-END:variables

    void launchGame()
    {
        this.panelGame.addKeyListener(this);
        this.panelGame.addMouseListener(this);
        this.panelGame.addMouseMotionListener(this);

        this.panelGame.setFocusable(true);
        this.panelGame.requestFocusInWindow();

        this.theScene = new SpaceShipScene();
        Director.getSharedInstance().setParent(this.panelGame);
        Director.getSharedInstance().showScene(theScene);

        this.theShip = new MySpaceship();
        this.theShip.lifetime = Sprite.EVER;

        this.theShip.setX(this.getWidth() / 2.0);
        this.theShip.setY(this.getHeight() * (3 / 4.0));

        this.theScene.addSprite(theShip);

        this.timerForEnemy.start();
        this.timerForFire.start();
        this.timerForFirMainWeapon.start();
    }

    void launchTest()
    {
        Director.getSharedInstance().setParent(this.panelGame);
        Director.getSharedInstance().showScene(new TestScene());
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        c = e.getKeyChar();
        this.keyTimer.start();
        this.fireKey(c);
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        this.keyTimer.stop();
    }

    void fireKey(char e)
    {
        float value = 50;
        float duration = 0.2f;

        if (e == 'a')
        {
            this.theShip.moveLeft(-value, duration);
        } else if (e == 'd')
        {
            //move right
            this.theShip.moveLeft(value, duration);
        } else if (e == 'w')
        {
            this.theShip.moveUp(-value, duration);
        } else if (e == 's')
        {
            this.theShip.moveDown(value, duration);
        } else if (e == 'f')
        {
            theShip.fire();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource().equals(this.keyTimer))
        {
            this.fireKey(c);
        } else if (e.getSource().equals(this.timerForEnemy))
        {
            if (abs(theRandom.nextInt()) % 100 > 80)
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

                float velocityX = index * abs(theRandom.nextInt()) % 100 + 50;
                float velocttyY = abs(theRandom.nextInt()) % 300 + 200;

                aBoss.setVelocityX(velocityX / 2);
                aBoss.setVelocityY(velocttyY / 2);

                aBoss.theTarget = this.theShip;
                this.theScene.addSprite(aBoss, 5);
            } else
            {
                Enemy aEnemy = null;
                int index = abs(theRandom.nextInt()) % 3;
                switch (index)
                {
                    case 0:
                        aEnemy = new BlueEnemy();
                        break;
                    case 1:
                        aEnemy = new GreenEnemy();
                        break;
                    case 2:
                        aEnemy = new PurpleEnemy();
                        break;
                    default:
                        aEnemy = new BlueEnemy();
                        break;
                }

                boolean b = theRandom.nextBoolean();
                index = b ? 1 : 0;
                index = (int) power(-1, index);
                int size = (int) (this.getWidth() * (1 / 4.0));

                aEnemy.setX(this.getWidth() / 2 + index * abs(theRandom.nextInt()) % size);
                aEnemy.setY(-100);

                b = theRandom.nextBoolean();
                index = b ? 1 : 0;
                index = (int) power(-1, index);

                float velocityXTmp = index * abs(theRandom.nextInt()) % 50 + 20;
                float velocttyYTmp = abs(theRandom.nextInt()) % 100 + 100;

                aEnemy.setVelocityX(velocityXTmp);
                aEnemy.setVelocityY(velocttyYTmp);
                aEnemy.lifetime = 5;

                aEnemy.theTarget = this.theShip;
                this.theScene.addSprite(aEnemy, aEnemy.layer);
            }
        } else if (e.getSource().equals(this.timerForFire))
        {
            this.fireKey('f');
        } else if (e.getSource().equals(this.timerForFirMainWeapon))
        {
            theShip.fireMainWeapon();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        if (e.getButton() == MouseEvent.BUTTON3)
        {
            theShip.openSheld();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        this.keyTimer.stop();
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        theShip.moveToXY(e.getX(), e.getY());
    }

    Missile createAFriendlyMissile()
    {
        String[] data = new String[]
        {
            "blue-missile.png", "green-missile.png", "nuclear.png", "red-missile.png"
        };
        int index = abs(theRandom.nextInt()) % data.length;

        Missile aMissile = new Missile(data[index]);

        return aMissile;
    }

    Missile createARocketMissile()
    {
        Missile aMissile = new Missile("rocket.png");
        return aMissile;
    }

    Missile createAEnemyMissile()
    {
        String[] data = new String[]
        {
            "red-enemy-missile.png"
        };
        int index = abs(theRandom.nextInt()) % data.length;

        Missile aMissile = new Missile(data[index]);

        return aMissile;
    }

}
