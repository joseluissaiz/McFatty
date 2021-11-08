import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import static java.lang.Thread.sleep;

public class Viewer extends Canvas implements Runnable {
    MyRestaurant  restaurant;

    public Viewer(MyRestaurant restaurant) {
        this.restaurant = restaurant;
    }

    private void draw() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        //Dibujamos las distintas entidades por orden
        g.drawImage(restaurant.getImageLoader().getBackingImage(), 0, 0, null);
        restaurant.getTable().draw(g);
        for (Chef chef : restaurant.getChefList()) {
            chef.draw(g);
        }
        for (Client client : restaurant.getClientList()) {
            client.draw(g);
        }


        bs.show();
        g.dispose();

    }

    @Override
    public void run() {
        while (true) {
            this.draw();

            try {
                sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void start() {
        new Thread(this).start();
    }
}
