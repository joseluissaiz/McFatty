import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image {
    private BufferedImage   backingImage;
    private BufferedImage[] chefImages;
    private BufferedImage   fireFX;
    private BufferedImage   tableMisc;
    private BufferedImage[] clientOrangeImages;
    private BufferedImage   logo;

    //-------------------------------------------------------------------------------------------------------CONSTRUCTOR

    public Image() {
        try {
            cargarBackground();
            cargarChef();
            loadClients();
            loadFireFX();
            loadTableMisc();
            loadLogo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------------------------------------------------------RECURSOS

    private void cargarBackground() throws IOException {
        this.backingImage = ImageIO.read(new File("src/Images/Background/background.png"));
    }

    private void cargarChef() throws IOException {
        this.chefImages = new BufferedImage[]
                {
                        ImageIO.read(new File("src/Images/Chef/chef_standing.png")),
                        ImageIO.read(new File("src/Images/Chef/chef_action.png")),
                        ImageIO.read(new File("src/Images/Chef/chef_carry.png")),
                };
    }


    private void loadClients() throws IOException {
        this.clientOrangeImages = new BufferedImage[]
                {
                        ImageIO.read(new File("src/Images/Clients/Orange/client_orange_standing.png")),
                        ImageIO.read(new File("src/Images/Clients/Orange/client_orange_carry.png")),
                        ImageIO.read(new File("src/Images/Clients/Orange/client_orange_eating.png")),
                };
    }

    private void loadFireFX() throws IOException {
        this.fireFX = ImageIO.read(new File("src/Images/Background/fire.png"));
    }

    private void loadTableMisc() throws IOException {
        this.tableMisc = ImageIO.read(new File("src/Images/Background/table_burgers.png"));
    }

    private void loadLogo() throws IOException {
        this.logo = ImageIO.read(new File("src/Images/Cpanel/mcfatty_logo_small.png"));
    }

    //---------------------------------------------------------------------------------------------------------------DTO

    public BufferedImage getBackingImage() { return backingImage; }

    public BufferedImage[] getChefImages() { return chefImages; }

    public BufferedImage getFireFX() { return fireFX; }

    public BufferedImage getTableMisc() { return tableMisc; }

    public BufferedImage[] getClientImages() { return clientOrangeImages; }

    public BufferedImage getLogo() { return logo; }
}
