import java.awt.Graphics;

public class Table {
    private       int          mealCount;
    private final int          mealMaxCount;
    private final MyRestaurant restaurant;

    //-----------------------------------------------------------------------------------------------------CONSTRUCTORES

    public Table(MyRestaurant restaurant, int maxMeals) {
        this.mealCount = 0;
        this.mealMaxCount = maxMeals;
        this.restaurant = restaurant;
    }

    //-----------------------------------------------------------------------------------------------------------GESTION

    public synchronized void placeMeal() {
        while (!hasEmptySpace()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.mealCount++;
        System.out.println("Plato depositado :" + mealCount);
        notifyAll();
    }

    public synchronized void takeMeal() {
        while (!hasSomeMeal()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.mealCount--;
        System.out.println("Plato recogido :" + mealCount);
        notifyAll();
    }

    public boolean hasEmptySpace() {
        return this.mealCount < mealMaxCount;
    }

    public boolean hasSomeMeal() {
        return this.mealCount > 0;
    }

    //----------------------------------------------------------------------------------------------------------GRAFICOS

    public void draw(Graphics g) {
        if (mealCount > 0) {
            g.drawImage(restaurant.getImageLoader().getTableMisc(), 220, 320, null);
        }
        if (mealCount > 1) {
            g.drawImage(restaurant.getImageLoader().getTableMisc(), 220, 200, null);
        }
        if (mealCount > 2) {
            g.drawImage(restaurant.getImageLoader().getTableMisc(), 220, 460, null);
        }
        if (mealCount > 4) {
            g.drawImage(restaurant.getImageLoader().getTableMisc(), 220, 270, null);
        }
        if (mealCount > 6) {
            g.drawImage(restaurant.getImageLoader().getTableMisc(), 220, 400, null);
        }
    }

}
