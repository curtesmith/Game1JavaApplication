package com.brakeen.javagamebook.test;

import com.brakeen.javagamebook.graphics.Animation;
import com.brakeen.javagamebook.input.GameAction;
import com.brakeen.javagamebook.input.InputManager;
import com.brakeen.javagamebook.input.Player;
import java.awt.*;
import java.awt.event.*;


public class InputManagerTest extends GameCore
{
    protected GameAction jump;
    protected GameAction exit;
    protected GameAction moveLeft;
    protected GameAction moveRight;
    protected GameAction pause;
    protected InputManager inputManager;
    private Player player;
    private Image bgImage;
    private boolean paused;
    
    
    public static void main(String[] args)
    {
        new InputManagerTest().run();
    }
    
    
    @Override
    public void init()
    {
        super.init();
        Window window = screen.getFullScreenWindow();
        inputManager = new InputManager(window);
        createGameActions();
        createSprite();
        paused = false;
    }
    
    
    public boolean isPaused()
    {
        return paused;
    }
    
    
    public void setPaused(boolean p)
    {
        if(paused != p)
        {
             this.paused = p;
             inputManager.resetAllGameActions();
        }
    }
    
    
    @Override
    public void update(long elapsedTime)
    {
        checkSystemInput();
        
        if(!isPaused())
        {
            checkGameInput();
            player.update(elapsedTime);
        }
    }
    
    
    public void checkSystemInput()
    {
        if(pause.isPressed())
        {
            setPaused(!isPaused());
        }
        
        if(exit.isPressed())
        {
            stop();
        }
    }
    
    
    public void checkGameInput()
    {
        float velocityX = 0;
        if(moveLeft.isPressed())
        {
            velocityX -= Player.SPEED;
        }
        
        if(moveRight.isPressed())
        {
            velocityX += Player.SPEED;
        }
        
        player.setVelocityX(velocityX);
        
        if(jump.isPressed() && player.getState() != Player.STATE_JUMPING)
        {
            player.jump();
        }
    }
    
    
    public void draw(Graphics2D g)
    {
        g.drawImage(bgImage, 0, 0, null);
        int x = Math.round(player.getX());
        int y = Math.round(player.getY());
        g.drawImage(player.getImage(), x, y, null);
    }
    
    
    public void createGameActions()
    {
        jump = new GameAction("jump", GameAction.DETECT_INITIAL_PRESS_ONLY);
        exit = new GameAction("exit", GameAction.DETECT_INITIAL_PRESS_ONLY);
        moveLeft = new GameAction("moveLeft");
        moveRight = new GameAction("moveRight");
        pause = new GameAction("pause", GameAction.DETECT_INITIAL_PRESS_ONLY);
        inputManager.mapToKey(exit, KeyEvent.VK_ESCAPE);
        inputManager.mapToKey(pause, KeyEvent.VK_P);
        inputManager.mapToKey(jump, KeyEvent.VK_SPACE);
        inputManager.mapToMouse(jump, InputManager.MOUSE_BUTTON_1);
        inputManager.mapToKey(moveLeft, KeyEvent.VK_A);
        inputManager.mapToKey(moveRight, KeyEvent.VK_D);
        inputManager.mapToMouse(moveLeft, InputManager.MOUSE_MOVE_LEFT);
        inputManager.mapToMouse(moveRight, InputManager.MOUSE_MOVE_RIGHT);        
    }
    
    
    private void createSprite()
    {
        bgImage = loadImage("images/background.jpg");
        Image player1 = loadImage("images/profile.jpg");
        Animation anim = new Animation();
        anim.addFrame(player1, 250);
        player = new Player(anim);
        player.setFloorY(screen.getHeight() - player.getHeight());
    }
}