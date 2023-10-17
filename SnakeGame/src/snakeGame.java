import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class snakeGame extends JPanel implements ActionListener, KeyListener {
	tile sneakHead;
	tile food;
	Random random;
	Timer loop;
	ArrayList<tile> snakeBody;

	int velocityX;
	int velocityY;
	int width;
	int height;
	int tileSize = 25;
	boolean gameOver = false;

	public snakeGame(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		//to set the details of frame on which game will be executed
		setPreferredSize(new Dimension(this.width, this.height));
		setBackground(Color.black);
		addKeyListener(this);
		setFocusable(true);
		
		sneakHead = new tile(5, 5);
		food = new tile(10, 10);
		random = new Random();
		snakeBody = new ArrayList<tile>();
		placeFood();
		velocityX = 0;
		velocityY = 0;
		loop = new Timer(100, this);
		loop.start();
	}

	//to pain the Sneakhead and Food
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);

	}

	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		/*
		 * for(int i=0;i< width/tileSize;i++) { g.drawLine(i*tileSize, 0, i*tileSize,
		 * height); g.drawLine(0,i*tileSize, width, i*tileSize); }
		 */
		// colours for food
		g.setColor(Color.red);
		g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true);

		// Colours for snakehead
		g.setColor(Color.green);
		g.fill3DRect(sneakHead.x * tileSize, sneakHead.y * tileSize, tileSize, tileSize, true);

		// colours for sneakbody
		for (int i = 0; i < snakeBody.size(); i++) {
			tile sneakPart = snakeBody.get(i);
			g.fill3DRect(sneakPart.x * tileSize, sneakPart.y * tileSize, tileSize, tileSize, true);
		}

		// Score
		g.setFont(new Font("Arial", Font.PLAIN, 16));
		if (gameOver) {
			g.setColor(Color.red);
			g.drawString("Game Over: " + String.valueOf(snakeBody.size()), tileSize * 10, tileSize * 10);
		} else {
			g.drawString("Score " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);

		}

	}

	public void placeFood() {
		food.x = random.nextInt(width / tileSize);
		food.y = random.nextInt(height / tileSize);

	}

	public boolean collesion(tile t1, tile t2) {
		return t1.x == t2.x && t1.y == t2.y;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		move();
		repaint();
		if (gameOver == true) {
			loop.stop();
		}

	}

	public void move() {
		// for collision with food
		if (collesion(sneakHead, food)) {
			snakeBody.add(new tile(food.x, food.y));
			placeFood();
		}

		// to move body traveseing reverse because if head move first body ill not have
		// any lead
		for (int i = snakeBody.size() - 1; i >= 0; i--) {
			tile sneakPart = snakeBody.get(i);
			// if loop is at the head of sneak head will move
			if (i == 0) {
				sneakPart.x = sneakHead.x;
				sneakPart.y = sneakHead.y;
			}
			// if loop is previous part of the head that will traverse
			else {
				tile prevSneakPart = snakeBody.get(i - 1);
				sneakPart.x = prevSneakPart.x;
				sneakPart.y = prevSneakPart.y;
			}

		}
		sneakHead.x += velocityX;
		sneakHead.y += velocityY;

		// Game over Conditions
		for (int i = 0; i < snakeBody.size(); i++) {
			tile sneakPart = snakeBody.get(i);
			// collide with sneakhead
			if (collesion(sneakPart, sneakHead)) {
				gameOver = true;
			}
		}

		if (sneakHead.x * tileSize < 0 || sneakHead.x * tileSize > width || sneakHead.y * tileSize < 0
				|| sneakHead.y * tileSize > height) {
			gameOver = true;
		}

	}

	// it will listen to the key pressing
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
			velocityX = 0;
			velocityY = -1;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
			velocityX = 0;
			velocityY = 1;

		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
			velocityX = 1;
			velocityY = 0;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
			velocityX = -1;
			velocityY = 0;

		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
