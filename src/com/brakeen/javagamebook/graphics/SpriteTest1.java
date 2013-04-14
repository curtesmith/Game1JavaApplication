package com.brakeen.javagamebook.graphics;


import java.awt.*;
import javax.swing.ImageIcon;
import java.awt.geom.AffineTransform;


public class SpriteTest1
{
    public static void main(String args[])
    {
        SpriteTest1 test = new SpriteTest1();
        test.run();
    }


    private static final DisplayMode POSSIBLE_MODES[] = {
        new DisplayMode(1366, 768, 32, 0),
        new DisplayMode(1366, 768, 24, 0),
        new DisplayMode(1366, 768, 16, 0)
    };

    private static final long DEMO_TIME = 10000;
    private static final long FADE_TIME = 1000;

    private ScreenManager screen;
    private Image bgImage;
    private Sprite sprite;


    public void loadImages()
    {
        bgImage = loadImage("images/background.jpg");
        Image player1 = loadImage("images/profile.jpg");
        Animation anim = new Animation();
        anim.addFrame(player1, 200);

        sprite = new Sprite(anim);
        sprite.setVelocityX(0.2f);
        sprite.setVelocityY(0.2f);
    }


    private Image loadImage(String fileName)
    {
        return new ImageIcon(fileName).getImage();
    }


    public void run()
    {
        screen = new ScreenManager();
        try
        {
            DisplayMode displayMode = screen.findFirstCompatibleMode(POSSIBLE_MODES);
            screen.setFullScreen(displayMode);
            loadImages();
            animationLoop();
        }
        finally
        {
            screen.restoreScreen();
        }
    }


    public void animationLoop()
    {
        long startTime = System.currentTimeMillis();
        long currTime = startTime;

        while(currTime - startTime < DEMO_TIME)
        {
            long elapsedTime = System.currentTimeMillis() - currTime;
            currTime += elapsedTime;
            update(elapsedTime);
            Graphics2D g = screen.getGraphics();
            draw(g);
            drawFade(g, currTime - startTime);
            g.dispose();
            screen.update();

            try
            {
                Thread.sleep(20);
            }
            catch(InterruptedException ex) { /* do nothing */ }
        }
    }


    public void drawFade(Graphics2D g, long currTime)
    {
        long time = 0;
        if(currTime <= FADE_TIME)
            time = FADE_TIME - currTime;
        else if(currTime > DEMO_TIME - FADE_TIME)
            time = FADE_TIME - DEMO_TIME + currTime;
        else
            return;

        byte numBars = 8;
        int barHeight = screen.getHeight() / numBars;
        int blackHeight = (int) (time * barHeight / FADE_TIME);

        g.setColor(Color.black);
        for(int i=0; i<numBars; i++)
        {
            int y = i * barHeight + (barHeight - blackHeight) / 2;
            g.fillRect(0, y, screen.getWidth(), blackHeight);
        }
    }
    

    public void update(long elapsedTime)
    {
        if(sprite.getX() < 0)
            sprite.setVelocityX(Math.abs(sprite.getVelocityX()));
        else if(sprite.getX() + sprite.getWidth() >= screen.getWidth())
            sprite.setVelocityX(-Math.abs(sprite.getVelocityX()));

        if(sprite.getY() < 0)
            sprite.setVelocityY(Math.abs(sprite.getVelocityY()));
        else if(sprite.getY() + sprite.getHeight() >= screen.getHeight())
            sprite.setVelocityY(-Math.abs(sprite.getVelocityY()));

        sprite.update(elapsedTime);
    }


    public void draw(Graphics2D g)
    {
        g.drawImage(bgImage, 0, 0, null);

        AffineTransform transform = new AffineTransform();
        transform.setToTranslation(sprite.getX(), sprite.getY());

        if(sprite.getVelocityX() < 0)
        {
            transform.scale(-1, 1);
            transform.translate(-sprite.getWidth(), 0);
        }

        g.drawImage(sprite.getImage(), transform, null);
    }
}
