import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {
    private MyRestaurant restaurant;

    public ControlPanel(MyRestaurant restaurant) {
        this.restaurant = restaurant;
        //this.setLayout(new GridBagLayout());
        this.setSize(300, 900);
        //this.setBackground(new Color(230, 190, 100, 255));
        this.setBackground(Color.black);
        buildStatistics();
    }

    private void buildStatistics() {

        //JLabel statTxt = new JLabel("Statistics");
//
        //GridBagConstraints c = new GridBagConstraints();
//
        //c.anchor = GridBagConstraints.WEST;
        //c.fill = GridBagConstraints.BOTH;
        //c.gridx = 0;
        //c.gridy = 0;
        //c.weightx = 0.2F;
        //c.weighty = 0.1F;
//
        //this.add(statTxt, c);
//
        //JLabel chefStatTxt = new JLabel("Chefs");
//
        //c.anchor = GridBagConstraints.WEST;
        //c.fill = GridBagConstraints.BOTH;
        //c.gridx = 0;
        //c.gridy = 1;
        //c.weightx = 0.5F;
        //c.weighty = 0.1F;
//
        //this.add(chefStatTxt, c);
//
        //JLabel actionTxt = new JLabel("Action Controls");
//
        //c.anchor = GridBagConstraints.WEST;
        //c.fill = GridBagConstraints.BOTH;
        //c.gridx = 0;
        //c.gridy = 2;
        //c.weightx = 0.5F;
        //c.weighty = 0.3F;
//
        //this.add(actionTxt, c);

    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(restaurant.getImageLoader().getLogo(), 0, 0, this);
    }
}
