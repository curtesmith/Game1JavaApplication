package com.brakeen.javagamebook.test;

import java.awt.*;
import javax.swing.ImageIcon;
import com.brakeen.javagamebook.graphics.ScreenManager;

public abstract class GameCore 
{
    protected static final int FONT_SIZE = 24;
    
    private static final DisplayMode POSSIBLE_MODES[] = {
        new DisplayMode(800,600,32,0),
        new DisplayMode(800,600,24,0),
        new DisplayMode(800,600,16,0)
    };
    
    private boolean isRunning;
    protected ScreenManager screen;
    
    
    public void stop()
    {
        isRunning = false;
    }
    
    
    public void run()
    {
        try
        {
            init();
            gameLoop();
        }
        finally 
        {
            screen.restoreScreen();
        }
    }
    
    
    public void init()
    {
        screen = new ScreenManager();
        DisplayMode displayMode = screen.findFirstCompatibleMode(POSSIBLE_MODES);
        screen.setFullScreen(displayMode);
        
        Window window = screen.getFullScreenWindow();
        window.setFont(new Font("Dialog", Font.PLAIN, FONT_SIZE));
        window.setBackground(Color.BLUE);
        window.setForeground(Color.WHITE);
        isRunning = true;
    }
    
    
    public Image loadImage(String fileName)
    {
        return new ImageIcon(fileName).getImage();
    }
    
    
    public void gameLoop()
    {
        long startTime = System.currentTimeMillis();
        long currTime = startTime;
        
        while(isRunning)
        {
            long elapsedTime = System.currentTimeMillis() - currTime;
            currTime += elapsedTime;
            update(elapsedTime);
            Graphics2D g = screen.getGraphics();
            draw(g);
            g.dispose();
            screen.update();
            try
            {
                Thread.sleep(20);
            }
            catch(InterruptedException ex){}
        }
    }
    
    
    public void update(long elapsedTime)
    {
        //do nothing
    }
    
    
    public abstract void draw(Graphics2D g);
}
