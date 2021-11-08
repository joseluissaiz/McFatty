import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Client extends Thread {
    private final MyRestaurant restaurant;
    private final BufferedImage[] clientImages;
    private int x;
    private int y;
    private       BufferedImage   actualPose;
    private       BufferedImage   poseDisplay;

    private String    action = "MOVE_RESTAURANT";
    private int       objectiveX;
    private int       objectiveY;
    private Direction direction;

    //-------------------------------------------------------------------------------------------------------CONSTRUCTOR

    public Client(MyRestaurant restaurant) {
        this.restaurant = restaurant;
        this.clientImages = restaurant.getImageLoader().getClientImages();
        this.direction = Direction.LEFT;
        this.actualPose = clientImages[0];
        this.getNewObjective();
        this.appearInRandomPosition();
    }

    //----------------------------------------------------------------------------------------------------COMPORTAMIENTO

    public void eat() {
        //takeMeal
        if (this.action.equals("ACTION_TAKE_MEAL")) {
            try {
                sleep(1000);
                restaurant.getTable().takeMeal();
                this.action = "MOVE_RESTAURANT_MEAL";
                getNewObjective();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } //Tarda en cajer el plato
        } else if (this.action.equals("ACTION_EAT")) {
            this.actualPose = clientImages[2];
            //eating   tic tac sleep(random)
            eating();
            this.action = "MOVE_TABLE";
        } else if (this.action.equals("MOVE_TABLE")) {
            if (restaurant.getTable().hasSomeMeal()) {
                getNewObjective();
                moveToTable();
            }
        } else if (this.action.equals("MOVE_RESTAURANT")) {
            moveToRestaurant();
        } else if (this.action.equals("MOVE_RESTAURANT_MEAL")) {
            moveToRestaurantMeal();
        }
        //repeat
    }

    private void eating() {
        System.out.println("Comiendo...");
        Random r = new Random();
        int eatingTime = r.nextInt(10000 - 5000) + 5000;
        try {
            sleep(eatingTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            eat();
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
        this.y = r.nextInt(420 - 340) + 340;
        this.x = 770;
    }

    private void getNewObjective() {
        Random r = new Random();
        if (this.action.equals("MOVE_RESTAURANT") || this.action.equals("MOVE_RESTAURANT_MEAL")) {
            this.objectiveX = r.nextInt(700 - 400) + 400;
            this.objectiveY = r.nextInt(700 - 150) + 150;
        } else if (this.action.equals("MOVE_TABLE")) {
            this.objectiveX = 280;
            this.objectiveY = r.nextInt(600 - 250) + 250;
        }

    }

    private void moveToRestaurant() {
        if (this.action.equals("MOVE_RESTAURANT")) {
            this.actualPose = clientImages[0];
            this.moveTo(objectiveX, objectiveY);
            if (isInPosition(objectiveX, objectiveY)) {
                this.direction = Direction.LEFT;
                this.action = "MOVE_TABLE";
            }
        }
    }

    private void moveToRestaurantMeal() {
        if (this.action.equals("MOVE_RESTAURANT_MEAL")) {
            this.actualPose = clientImages[1];
            this.moveTo(objectiveX, objectiveY);
            if (isInPosition(objectiveX, objectiveY)) {
                this.action = "ACTION_EAT";
            }
        }
    }

    private void moveToTable() {
        if (this.action.equals("MOVE_TABLE")) {
            this.actualPose = clientImages[0];
            this.moveTo(objectiveX, objectiveY);
            if (this.isInPosition(objectiveX, objectiveY)) {
                this.direction = Direction.LEFT;
                this.action = "ACTION_TAKE_MEAL";
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

        switch (this.direction) {
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
    }

}
