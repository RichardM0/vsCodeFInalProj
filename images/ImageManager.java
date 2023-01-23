package images;

import java.io.IOException;

import main.GamePanel;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.util.Random;

public class ImageManager {
    
    GamePanel gp;
    Image[] Image;
    Image[] cropDuster;
    Image[] Plane;
    Image[] fighterJet;
    public static Image[] titleScreen;
    
    public static int rand;
    

    public ImageManager(GamePanel gp){

        this.gp = gp;

        Image = new Image[10];
        cropDuster = new Image[5];
        Plane = new Image[1];
        fighterJet = new Image[1];
        titleScreen = new Image[1];
        Random randNum = new Random();
        rand = randNum.nextInt(cropDuster.length);

        getImageImage();
    }

    public void getImageImage() {

        try {
                Image[0] = new Image();
                Image[0].image = ImageIO.read(getClass().getResourceAsStream("/main/sky.png"));
                Image[1] = new Image();
                Image[1].image = ImageIO.read(getClass().getResourceAsStream("/main/earth.png"));
                Image[2] = new Image();
                Image[2].image = ImageIO.read(getClass().getResourceAsStream("/main/bird1.png"));
                Image[3] = new Image();
                Image[3].image = ImageIO.read(getClass().getResourceAsStream("/main/bird2.png"));
                Image[4] = new Image();
                Image[4].image = ImageIO.read(getClass().getResourceAsStream("/main/bird3.png"));

                cropDuster[0] = new Image();
                cropDuster[0].image = ImageIO.read(getClass().getResourceAsStream("/main/cropDuster1.png"));
                cropDuster[1] = new Image();
                cropDuster[1].image = ImageIO.read(getClass().getResourceAsStream("/main/cropDuster2.png"));
                cropDuster[2] = new Image();
                cropDuster[2].image = ImageIO.read(getClass().getResourceAsStream("/main/cropDuster3.png"));
                cropDuster[3] = new Image();
                cropDuster[3].image = ImageIO.read(getClass().getResourceAsStream("/main/cropDuster4.png"));
                cropDuster[4] = new Image();
                cropDuster[4].image = ImageIO.read(getClass().getResourceAsStream("/main/cropDuster5.png"));

                Plane[0] = new Image();
                Plane[0].image = ImageIO.read(getClass().getResourceAsStream("/main/commercialPlane1.png"));

                fighterJet[0] = new Image();
                fighterJet[0].image = ImageIO.read(getClass().getResourceAsStream("/main/fighterJet.png"));

                titleScreen[0] = new Image();
                titleScreen[0].image = ImageIO.read(getClass().getResourceAsStream("/main/start_screen.png"));



        }
        catch(IOException e) {
            e.printStackTrace();

        }
        
        
    }
    //method to draw background
    public void draw(Graphics2D g2) {

        int ImagesWidth = (int)gp.maxWidth/gp.tileSize;
        int ImagesHeight = (int)gp.maxHeight/gp.tileSize;
        for(int i=0;i<ImagesWidth;i++){
            for(int k=0;k<ImagesHeight-2;k++){
                g2.drawImage(Image[0].image, 0 + i*gp.tileSize, (0 + k*gp.tileSize), gp.tileSize, gp.tileSize, null);
            }
        }
        if(gp.gameIsOver){
            g2.drawImage(Image[4].image, gp.birdX, gp.birdY, gp.tileSize, gp.tileSize, null);
        }
        else if(!gp.gameIsOver){
            g2.drawImage((gp.firstAnim ? Image[2].image : Image[3].image), gp.birdX, gp.birdY, gp.tileSize, gp.tileSize, null);
            gp.firstAnim = true;
        }


        
        g2.drawImage(cropDuster[rand].image, gp.plane1X, gp.plane1Y, gp.plane1Width, gp.plane1Height, null);
        if(gp.score>300){
            g2.drawImage(Plane[0].image, gp.plane2X, gp.plane2Y, gp.plane2Width, gp.plane2Height, null);
        }

        if(gp.score>625){
            g2.drawImage(fighterJet[0].image, gp.plane3X, gp.plane3Y, gp.plane3Width, gp.plane3Height, null);
        }
        
        
    }
}
