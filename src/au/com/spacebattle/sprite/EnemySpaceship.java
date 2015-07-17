/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.spacebattle.sprite;

/**
 *
 * @author ricolwang
 */
public class EnemySpaceship extends Spaceship
{

    public EnemySpaceship(double x, double y, double width, double height, double mass, double velocityX, double velocityY)
    {
        super(x, y, width, height, mass, velocityX, velocityY);
        this.setImage("enemy-spaceship.png");
        this.lifetime = 5;
    }
    
}
