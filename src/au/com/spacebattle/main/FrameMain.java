package au.com.spacebattle.main;

import au.com.rmit.Game2dEngine.action.MoveCentreXToAction;
import au.com.rmit.Game2dEngine.action.MoveCentreYToAction;
import au.com.rmit.Game2dEngine.action.MoveXByAction;
import au.com.rmit.Game2dEngine.action.MoveYByAction;
import au.com.rmit.Game2dEngine.director.Director;
import au.com.spacebattle.scene.SpaceShipScene;
import au.com.spacebattle.scene.TestScene;
import au.com.spacebattle.sprite.BlueMissile;
import au.com.spacebattle.sprite.Missile;
import au.com.spacebattle.sprite.MySpaceship;
import au.com.spacebattle.sprite.RedMissile;
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

        this.theShip = new MySpaceship(this.getWidth() / 2.0, this.getHeight() * (3 / 4.0), 100, 100, 0, 0, 0);
        this.theScene.addSprite(theShip);
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
            //move left
            MoveXByAction aAction = new MoveXByAction();
            aAction.moveXBy(-value, duration);
            this.theShip.addAction(aAction);
            System.out.println("Left Action");
        } else if (e == 'd')
        {
            //move right
            MoveXByAction aAction = new MoveXByAction();
            aAction.moveXBy(value, duration);
            this.theShip.addAction(aAction);
            System.out.println("Right Action");
        } else if (e == 'w')
        {
            //move up
            MoveYByAction aAction = new MoveYByAction();
            aAction.moveYBy(-value, duration);
            this.theShip.addAction(aAction);
            System.out.println("Up Action");
        } else if (e == 's')
        {
            //move down
            MoveYByAction aAction = new MoveYByAction();
            aAction.moveYBy(value, duration);
            this.theShip.addAction(aAction);
            System.out.println("Down Action");
        } else if (e == 'f')
        {
            //fire
            Missile aMissile = new RedMissile(theShip.getX(),
                theShip.getY(), 0, 0, -200);
            this.theScene.addSprite(aMissile);

            aMissile = new BlueMissile(theShip.getX() + theShip.getWidth() / 2,
                theShip.getY(), 0, 0, -300);
            aMissile.setX(aMissile.getX() - aMissile.getWidth() / 2);

            this.theScene.addSprite(aMissile);

            aMissile = new RedMissile(theShip.getX() + theShip.getWidth(),
                theShip.getY(), 0, 0, -200);
            aMissile.setX(aMissile.getX() - aMissile.getWidth());

            this.theScene.addSprite(aMissile);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        this.fireKey(c);
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        c = 'f';
        this.keyTimer.start();
        this.fireKey(c);
    }

    @Override
    public void mousePressed(MouseEvent e)
    {

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
        int x = e.getX();
        int y = e.getY();

        double theShipCentreX = this.theShip.getCentreX();
        double theShipCentreY = this.theShip.getCentreY();

        MoveCentreXToAction aCentreXAction = new MoveCentreXToAction(theShip);
        aCentreXAction.MoveCentreXTo(x, 0);
        theShip.addAction(aCentreXAction);

        MoveCentreYToAction aCentreYAction = new MoveCentreYToAction(theShip);
        aCentreYAction.MoveCentreYTo(y, 0);
        theShip.addAction(aCentreYAction);
    }
}
