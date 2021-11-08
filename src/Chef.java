import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Chef extends Thread {
    private int x;
    private int y;

    private final MyRestaurant restaurant;

    private final BufferedImage[] chefImages;
    private       BufferedImage   actualPose;
    private       BufferedImage   poseDisplay;
    private final BufferedImage fireFx;

    private Stove cookingStove;

    private String action = "MOVE_CORRIDOR";
    private Direction direction;


    //-------------------------------------------------------------------------------------------------------CONSTRUCTOR

    public Chef(MyRestaurant restaurant) {
        this.restaurant = restaurant;
        this.chefImages = restaurant.getImageLoader().getChefImages();
        this.fireFx = restaurant.getImageLoader().getFireFX();
        this.actualPose = chefImages[0];
        this.poseDisplay = actualPose;
        this.direction = Direction.UP;
        this.cookingStove = getRandomStove();
        this.appearInRandomPosition();
    }

    //----------------------------------------------------------------------------------------------------COMPORTAMIENTO

    public void cook() {

        //------------------------------------------------------------------
        if (this.action.equals("ACTION_COOK")) {
            //cooking tic tac (sleep(Tiempo random))
            cooking();
            this.action = "MOVE_TABLE_CORRIDOR";
            //placeMeal

            //repeat
        } else if (this.action.equals("ACTION_PUT_MEAL")) {
            restaurant.getTable().placeMeal();
            this.cookingStove = getRandomStove();
            this.action = "MOVE_CORRIDOR";
        } else if (this.action.equals("MOVE_CORRIDOR") || this.action.equals("MOVE_STOVE")) {
            moveToStove(cookingStove);
        } else if (this.action.equals("MOVE_TABLE_CORRIDOR") || this.action.equals("MOVE_TABLE")) {
            moveToTable();
        }
        //--------------------------------------------------------en pruebas
    }


    private void cooking() {
        Random r = new Random();
        int cookingTime = r.nextInt(10000 - 5000) + 5000;
        try {
            sleep(cookingTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.action = "MOVE_TABLE";
    }


    
    @Override
    public void run() {
        while (true) {
            cook();
            try {
                sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    //----------------------------------------------------------------------------------------------------------MOVEMENT

    private void appearInRandomPosition() {
        Random r = new Random();
        int minX = 50;
        int maxX = 160;
        this.x = r.nextInt(maxX - minX) + minX;

        int minY = 20;
        int maxY = 720;
        this.y = r.nextInt(maxY - minY) + minY;
    }


    private Stove getRandomStove() {
        Random r = new Random();
        return restaurant.getStoveList().get(r.nextInt(restaurant.getStoveList().size()));
    }


    private void moveToStove(Stove stove) {
        if (this.action.equals("MOVE_CORRIDOR")) {
            this.actualPose = chefImages[0];
            int corridorX = stove.getX() + 50;
            if (this.isInPosition(corridorX, stove.getY())) {
                this.action = "MOVE_STOVE";
            } else {
                this.moveTo(corridorX, stove.getY());
            }
        } else if (this.action.equals("MOVE_STOVE")) {
            if (this.isInPosition(stove.getX(), stove.getY())) {
                this.actualPose = chefImages[1];
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } //tarda en encender la olla
                this.action = "ACTION_COOK";
            } else {
                this.moveTo(stove.getX(), stove.getY());
            }
        }

    }


    private void moveToTable() {
        int tableRandomY = new Random().nextInt(550 - 200) + 200;
        int tableX = 190;

        if (this.action.equals("MOVE_TABLE_CORRIDOR")) {
            this.actualPose = chefImages[2];
            int corridorX = tableX - 70;
            if (this.isInPosition(corridorX, tableRandomY)) {
                this.action = "MOVE_TABLE";
            } else {
                this.moveTo(corridorX, tableRandomY);
            }
        } else if (this.action.equals("MOVE_TABLE")) {
            if (this.isInPosition(tableX, tableRandomY)) {
                this.direction = Direction.RIGHT;
                //this.actualPose = chefImages[1];
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } //tarda en dejar la comida
                this.action = "ACTION_PUT_MEAL";
            } else {
                this.moveTo(tableX, tableRandomY);
            }
        }

    }


    private boolean isInPosition(int x, int y) {
        int detectionThreshold = 15;
        return ((x - detectionThreshold <= this.x && this.x <= x + detectionThreshold)
                && (y - detectionThreshold <= this.y && this.y <= y + detectionThreshold));
    }


    private void moveTo(int x, int y) {
        if (this.y < y) {
            this.direction = Direction.DOWN;
            this.y++;
        } else if (this.y > y) {
            this.direction = Direction.UP;
            this.y--;
        }
        if (this.x < x) {
            this.direction = Direction.RIGHT;
            this.x++;
        } else if (this.x > x) {
            this.direction = Direction.LEFT;
            this.x--;
        }
    }


    //----------------------------------------------------------------------------------------------------------GRAFICOS


    public void draw(Graphics g) {

        int angle = 0;
        int w = actualPose.getWidth();
        int h = actualPose.getHeight();

        switch(this.direction){
            case UP -> angle = 90;
            case DOWN -> angle = -90;
            case RIGHT -> angle = 180;
        }
        this.poseDisplay = actualPose;
        BufferedImage rotated = new BufferedImage(w, h, actualPose.getType());
        Graphics2D graphic = rotated.createGraphics();
        graphic.rotate(Math.toRadians(angle), w / 2, h / 2);
        graphic.drawImage(poseDisplay, null, 0, 0);
        this.poseDisplay = rotated;

        g.drawImage(poseDisplay, x, y, null);

        if (this.action.equals("ACTION_COOK")) //Displays the stove flame
        {
            g.drawImage(fireFx, cookingStove.getX() - 30, cookingStove.getY(), null);
        }

    }


}
