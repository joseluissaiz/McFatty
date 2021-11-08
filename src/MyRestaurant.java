import javax.swing.JFrame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

public class MyRestaurant extends JFrame {
    private final int widht  = 860 + (20 * 800 / 100);
    private final int height = 830;

    private final Image imageLoader;

    private ControlPanel cPanel;
    private Viewer       viewer;

    //Entities
    private List<Chef>   chefList;
    private List<Client> clientList;

    //Misc
    private List<Stove> stoveList;
    private Table       table;

    //-------------------------------------------------------------------------------------------------------CONSTRUCTOR

    public MyRestaurant() {
        this.setSize(widht, height);
        this.setTitle("McFatty");
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new GridBagLayout());
        this.imageLoader = new Image();
        buildFrame();
        createMiscellaneous();
        createEntities();
    }

    //-----------------------------------------------------------------------------------------------------------BUILDER

    public static void main(String[] args) {
        MyRestaurant task = new MyRestaurant();
        task.setVisible(true);
        task.viewer.start();
        for (Chef chef : task.chefList) {
            chef.start();
        }
        for (Client client : task.clientList) {
            client.start();
        }

    }

    private void createEntities() {
        chefList = new ArrayList<>();
        clientList = new ArrayList<>();

        Chef chef2 = new Chef(this);
        Chef chef3 = new Chef(this);
        Chef chef4 = new Chef(this);
        Chef chef5 = new Chef(this);
        Chef chef6 = new Chef(this);
        Chef chef7 = new Chef(this);
        Chef chef8 = new Chef(this);
        //Chef chef9 = new Chef(this);
        //Chef chef10 = new Chef(this);
        //Chef chef11 = new Chef(this);
        //Chef chef12 = new Chef(this);
        this.chefList.add(chef2);
        this.chefList.add(chef3);
        this.chefList.add(chef4);
        this.chefList.add(chef5);
        this.chefList.add(chef6);
        this.chefList.add(chef7);
        this.chefList.add(chef8);
        //this.chefList.add(chef9 );
        //this.chefList.add(chef10);
        //this.chefList.add(chef11);
        //this.chefList.add(chef12);

        Client client1 = new Client(this);
        Client client2 = new Client(this);
        Client client3 = new Client(this);
        Client client4 = new Client(this);
        Client client5 = new Client(this);

        this.clientList.add(client1);
        this.clientList.add(client2);
        this.clientList.add(client3);
        this.clientList.add(client4);
        this.clientList.add(client5);


        this.table = new Table(this, 20);
    }

    private void createMiscellaneous() {
        this.stoveList = new ArrayList<>();
        this.stoveList.add(new Stove(50, 150));
        this.stoveList.add(new Stove(50, 225));
        this.stoveList.add(new Stove(50, 340));
        this.stoveList.add(new Stove(50, 420));
        this.stoveList.add(new Stove(50, 560));
        this.stoveList.add(new Stove(50, 630));
    }

    //---------------------------------------------------------------------------------------------------------------DTO

    private void buildFrame() {
        GridBagConstraints c = new GridBagConstraints();

        this.cPanel = new ControlPanel(this);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.2f;
        c.weighty = 1.0f;
        c.gridx = 0;
        c.gridy = 0;
        this.add(cPanel, c);

        this.viewer = new Viewer(this);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.8f;
        c.weighty = 1.0f;
        c.gridx = 1;
        c.gridy = 0;
        this.add(viewer, c);
    }

    public Image getImageLoader() {
        return imageLoader;
    }

    public Table getTable() {
        return table;
    }

    public List<Stove> getStoveList() {
        return stoveList;
    }

    public List<Chef> getChefList() {
        return chefList;
    }

    //--------------------------------------------------------------------------------------------------------------MAIN

    public List<Client> getClientList() {
        return clientList;
    }
}
