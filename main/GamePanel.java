package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import tile.TileManager;



public class GamePanel extends JPanel implements Runnable {
	
	// Screen Settings
	final int originalTileSize = 16; //16x16
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale; //48x48
	final int maxScreenCol = 22;
	final int maxScreenRow = 28; //16x14
	final int screenWidth = tileSize * maxScreenRow; // (48x14)px
	final int screenHeight = tileSize * maxScreenCol; // (48x16)px
	
	private Image backgroundImage;
	BufferedImage myBackgroundImg;
	
	int FPS = 60;
	
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	Random randNum = new Random();
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public double maxWidth = screenSize.getWidth();
	public double maxHeight = screenSize.getHeight();
	
	
	int rand = randNum.nextInt((int) (maxHeight-500));
	int rand2 = randNum.nextInt((int) (maxHeight-500));
	int rand3 = randNum.nextInt((int) (maxHeight-500));
	
	int score = 0;
	public int birdX = 100;
	public int birdY = 100;
	int birdJump = 27;
	
	int originalPlane1X = (int) (maxWidth-25);
	public int plane1X = (int) (maxWidth+75);
	public int plane1Y = rand;
	int plane1Speed = 10;
	public int plane1Height = 100;
	public int plane1Width = 180;
	
	double centerWidth = maxWidth/2;
	double centerHeight = maxHeight/3;

	int originalPlane2X = (int) (maxWidth-25);
	public int plane2X = (int) (maxWidth+75);
	int plane2Y = rand2;
	int plane2Speed = 18;
	int plane2Height = 95;
	int plane2Width = 135;
	
	int originalPlane3X = (int) (maxWidth-25);
	int plane3X = (int) (maxWidth+75);
	int plane3Y = rand3;
	int plane3Speed = 25;
	int plane3Height = 90;
	int plane3Width = 130;
	Color birdColor;


	int counter = 0;

	public boolean plane1InScreen = false;
	boolean birdPlaneCollision;
	public boolean gameIsOver = false;
	boolean isOnTitleScreen = true;
	public boolean firstAnim = true;
	boolean dayTime = true;
	boolean isPowerUpIn = false;

	
	
	
	public GamePanel(){
		//public GamePanel(String fileName) throws IOException
		//this.backgroundImage = ImageIO.read(new File(fileName));
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void paintComponent(Graphics g) {
		if(!isOnTitleScreen) {
		    super.paintComponent(g);

			
		    Graphics2D g2 = (Graphics2D)g;
			g2.drawImage(myBackgroundImg, 0, 0, (int)(maxWidth), (int)(maxHeight), this);
			
			tileM.draw(g2);
			Font curFont = g2.getFont();
			Font newFont = curFont.deriveFont(curFont.getSize() * 2F);

			g2.setFont(newFont);
		    
			g2.setColor(Color.white);
		    
		    if(score>300) {
		    	g2.fillRect(plane2X, plane2Y, plane2Width, plane2Height);
		    	
		    }

			if(score>625) {
		    	g2.fillRect(plane3X, plane3Y, plane3Width, plane3Height);
		    	
		    }
		    // Draw the background image.
			
		    g2.drawImage(backgroundImage, 0, 0, this);
		    
		    
		    g2.dispose();
		}
		else if(isOnTitleScreen) {
			super.paintComponent(g);
			
		    Graphics2D g2 = (Graphics2D)g;
			/*
			Font curFont = g2.getFont();
			Font newFont = curFont.deriveFont(curFont.getSize() * 3F);
			g2.setColor(Color.white);
			g2.setFont(newFont);
			g2.drawString("Press Enter to Start", (int)centerWidth, (int)centerHeight);
			*/
		    g2.setColor(Color.black);
		    g2.drawImage(backgroundImage, 0, 0, this);
		}
	  }

	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {
		
		double Interval = 1000000000/FPS; // 1billion nanoseconds = 1 second
		double nextDrawTime = System.nanoTime() + Interval; // When to draw
		while(gameThread != null) {
			
			// UPDATE: update character positions etc
			update();
			
			// DRAW/BLIT: blit updated screen with information
			repaint();
			
			
			
			try {
				double timeLeft = nextDrawTime - System.nanoTime();
				timeLeft /= 10000000; //Convert duration of sleep from nano to miliseconds
				
				if(timeLeft < 0) {
					timeLeft = 0;
				} // Check for a bad error
				
				Thread.sleep((long) timeLeft); // Sleep for a duration
				
				nextDrawTime += Interval;
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	public void jump(){
		birdY -= birdJump;
	}

	public void update() {
		if(!isOnTitleScreen) {
			if(!gameIsOver){
				if(keyH.upPressed == true) {
					if(birdY >= maxHeight - 200) {
						//end the game
						gameIsOver = true;
						;
						
					}
					
					else if(birdY < 0) {
						//height limit
						gameIsOver = true;
					}
					else{
						jump();
						firstAnim = false;

						
						
					}
				}
				//bird gravity
				birdY += 6;
			}

			else if(gameIsOver){
				;
				//dead bird gravity
				birdY += 12;
			}

			
			if(((birdX+40 >= plane1X) && (birdX <= plane1X + plane1Width)) && ((birdY+40 >= plane1Y) && (birdY <= plane1Y+plane1Height))){
				gameIsOver = true;
			}

			if(score>300){
				if(((birdX+40 >= plane2X) && (birdX <= plane2X + plane2Width)) && ((birdY+40 >= plane2Y) && (birdY <= plane2Y+plane2Height))){
					gameIsOver = true;
				}
			}

			if(score>625){
				if(((birdX+40 >= plane3X) && (birdX <= plane3X)) && ((birdY+40 >= plane3Y) && (birdY <= plane3Y+plane3Height))){
					gameIsOver = true;
				}
			}

			if((plane2Y > plane1Y-135) && (plane2Y < plane1Y+135) && (plane2X > plane1X)) {
				   
				rand2 = randNum.nextInt((int) (maxHeight-240));
	    		plane2Y = rand2;
			}
			if((plane3Y > plane2Y-140) && (plane3Y < plane2Y+140) && (plane3X > plane2X)) {
				   
				rand3 = randNum.nextInt((int) (maxHeight-240));
	    		plane3Y = rand3;
			}
			else if((plane3Y > plane1Y-140) && (plane3Y < plane1Y+140) && (plane3X > plane1X)) {
				   
				rand3 = randNum.nextInt((int) (maxHeight-240));
	    		plane3Y = rand3;
			}
			


			// plane constant movement
			plane1X -= plane1Speed;
			if(score>300){
				plane2X -= plane2Speed;
			}
			if(score>625){
				plane3X -= plane3Speed;
			}
			if(score%100==0 && score!=0){
				isPowerUpIn = true;
			}



			//
			if(plane1X < plane1Width*-2) {
				plane1InScreen = false;
				plane1X = originalPlane1X;
				rand = randNum.nextInt((int) (maxHeight-180));
				plane1Y = rand;
				plane1InScreen = true;
				tile.TileManager.rand = randNum.nextInt(5);
				
				
			}	
			
			if(plane2X < plane2Width*-2) {
				plane2X = originalPlane2X;
				rand2 = randNum.nextInt((int) (maxHeight-180));
				plane2Y = rand2;
				
			}

			if(plane3X < plane3Width*-2) {
				plane3X = originalPlane3X;
				rand3 = randNum.nextInt((int) (maxHeight-180));
				plane3Y = rand3;
				
			}
			
			if(birdY >= maxHeight) {
				//end the game
				gameIsOver = true;
				
			}
			
			

			if(counter % 90 == 0 && gameIsOver == false){
				score += 20;
			}

			else if(gameIsOver == true && birdY > maxHeight + 50) {
				isOnTitleScreen = true;
			}
		}
		
		else if(isOnTitleScreen) {
			if(keyH.enterPressed == true) {
				isOnTitleScreen = false;
				gameIsOver = false;
				score = 0;
				birdX = 100;
				birdY = 100;
				birdJump = 25;
				
				originalPlane1X = (int) (maxWidth-25);
				plane1X = (int) (maxWidth+75);
				plane1Y = rand;
				plane1Speed = 10;
				plane1Height = 100;
				plane1Width = 180;
				plane1InScreen = true;
				
				centerWidth = maxWidth/2;
				centerHeight = maxHeight/3;

				originalPlane2X = (int) (maxWidth-25);
				plane2X = (int) (maxWidth+75);
				plane2Y = rand2;
				plane2Speed = 18;
				plane2Height = 90;
				plane2Width = 135;
				
				originalPlane3X = (int) (maxWidth-25);
				plane3X = (int) (maxWidth+75);
				plane3Y = rand3;
				plane3Speed = 25;
				plane3Height = 110;
				plane3Width = 140;

				counter = 0;

			}
		}
		counter+=1;
		
		
	}

}