package tile;

import java.io.IOException;

import main.GamePanel;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.util.Random;

public class TileManager {
    
    GamePanel gp;
    Tile[] tile;
    Tile[] cropDuster;

    
    public static int rand;
    

    public TileManager(GamePanel gp){

        this.gp = gp;

        tile = new Tile[10];
        cropDuster = new Tile[5];
        Random randNum = new Random();
        rand = randNum.nextInt(cropDuster.length);

        getTileImage();
    }

    public void getTileImage() {

        try {
                tile[0] = new Tile();
                tile[0].image = ImageIO.read(getClass().getResourceAsStream("/main/sky.png"));
                tile[1] = new Tile();
                tile[1].image = ImageIO.read(getClass().getResourceAsStream("/main/earth.png"));
                tile[2] = new Tile();
                tile[2].image = ImageIO.read(getClass().getResourceAsStream("/main/bird1.png"));
                tile[3] = new Tile();
                tile[3].image = ImageIO.read(getClass().getResourceAsStream("/main/bird2.png"));
                tile[4] = new Tile();
                tile[4].image = ImageIO.read(getClass().getResourceAsStream("/main/bird3.png"));

                cropDuster[0] = new Tile();
                cropDuster[0].image = ImageIO.read(getClass().getResourceAsStream("/main/cropDuster1.png"));
                cropDuster[1] = new Tile();
                cropDuster[1].image = ImageIO.read(getClass().getResourceAsStream("/main/cropDuster2.png"));
                cropDuster[2] = new Tile();
                cropDuster[2].image = ImageIO.read(getClass().getResourceAsStream("/main/cropDuster3.png"));
                cropDuster[3] = new Tile();
                cropDuster[3].image = ImageIO.read(getClass().getResourceAsStream("/main/cropDuster4.png"));
                cropDuster[4] = new Tile();
                cropDuster[4].image = ImageIO.read(getClass().getResourceAsStream("/main/cropDuster5.png"));
        }
        catch(IOException e) {
            e.printStackTrace();

        }
        
        
    }
    //method to draw background
    public void draw(Graphics2D g2) {

        int tilesWidth = (int)gp.maxWidth/gp.tileSize;
        int tilesHeight = (int)gp.maxHeight/gp.tileSize;
        for(int i=0;i<tilesWidth;i++){
            for(int k=0;k<tilesHeight-2;k++){
                g2.drawImage(tile[0].image, 0 + i*gp.tileSize, (0 + k*gp.tileSize), gp.tileSize, gp.tileSize, null);
            }
        }
        if(gp.gameIsOver){
            g2.drawImage(tile[4].image, gp.birdX, gp.birdY, gp.tileSize, gp.tileSize, null);
        }
        else if(!gp.gameIsOver){
            g2.drawImage((gp.firstAnim ? tile[2].image : tile[3].image), gp.birdX, gp.birdY, gp.tileSize, gp.tileSize, null);
            gp.firstAnim = true;
        }



        g2.drawImage(cropDuster[rand].image, gp.plane1X, gp.plane1Y, gp.plane1Width, gp.plane1Height, null);
        
    }
}
