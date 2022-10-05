package a9;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;

/**
 * 
 * @author Tanner Ensign u1254981
 * This class is the base of the ProtectTheCastle game. This add's the sprites, creates the window, rows, columns, buttons, money, etc. This controls the whole game.
 * ProtectTheCastle is a 2D game in which the player places either a knight or archer to protect the castle from dragons and wolfs.
 */
public class ProtectTheCastle extends JPanel implements ActionListener, MouseListener {

	private JPanel gameOverPanel; //panel that I couldn't get to work
	private JLabel gameOverText; // on the gameOverPanel
	private JButton playAgain; // on the gameOverPanel
	private JRadioButton knightButton; //button selection for knight placement
	private JRadioButton archerButton; //button selection for archer placement
	private ButtonGroup defensiveButtonGroup; //groups the knightButton and archerButton so only one can be chosen
	private JLabel noNoNo; // label that will pop up only if somehow both buttons are selected
	private JLabel money; //label displaying how much money you have
	private JLabel levelLabel; //label displaying the level you are on
	private static final long serialVersionUID = 1L;
	private Timer timer; //timer
	private Random randGenerator; //random number generator
	private Point2D.Double position1;//position that will end the game
	private Point2D.Double position2;//position that will end the game
	private Point2D.Double position3;//position that will end the game
	private Point2D.Double position4;//position that will end the game
	private Point2D.Double position5;//position that will end the game
	private Point2D.Double actorPosition;//the position of the wolf or dragon
	private ArrayList<Actor> actors; // Knights, archers, wolfs, and dragons go here.
	private ArrayList<Actor> enemies; // only dragons and wolfs in here
	int numRows; //number of rows
	int numCols; //number of columns
	int cellSize; //cell size
	int x; //x coordinates
	int y; //y coordinates
	int coins = 20; //coins you have/start with
	int knightCost = 3;//the cost of the knight
	int archerCost = 5;//the cost of the archer
	int level = 1;//the level
	int counter = 0;//counter to change the level
	

	/**
	 * Setup the basic info for the example
	 */
	public ProtectTheCastle() {
		super();

		// Define some quantities of the scene
		numRows = 5;
		numCols = 7;
		cellSize = 75;
		setPreferredSize(new Dimension(50 + numCols * cellSize, 50 + numRows * cellSize));


		randGenerator = new Random();
		// Store all the plants and zombies in here.
		actors = new ArrayList<>();
		enemies = new ArrayList<>();
		
		addMouseListener(this);
		
		// The timer updates the game each time it goes.
		// Get the javax.swing Timer, not from util.
		timer = new Timer(30, this);
		timer.start();
		panelSetter();
		

	}

	/***
	 * Implement the paint method to draw the plants
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Actor actor : actors) {
			actor.draw(g, 0);
			actor.drawHealthBar(g);
		}
	}

	/**
	 * 
	 * This is triggered by the timer. It is the game loop of this test.
	 * In this actionPerformed loop, it is always updating the game, player movement, if a player dies,
	 * takes them off the JFrame/JPanel and also spawns the players.
	 * 
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {		
		
		
		levels(); //calls the levels method which spawns the players.
		
		
		if(archerButton.isSelected() && knightButton.isSelected()) //this displays the JLabel noNoNo if somehow two buttons are selected
		{
			noNoNo.setText("Please only select one || ");
		}
		
		
		// This method is getting a little long, but it is mostly loop code
		// Increment their cooldowns and reset collision status
		for (Actor actor : actors) {
			actor.update();
		}

		for (Actor actor : enemies) {
			callGameOver(actor);
		}
		

		// Try to attack 
		for (Actor actor : actors) {
			for (Actor other : actors) {
					actor.attack(other);
			}
		}

		// Remove defensive and enemies with low health
		ArrayList<Actor> nextTurnActors = new ArrayList<>();
		for (Actor actor : actors) {
			if (actor.isAlive())
				nextTurnActors.add(actor);
			else
			{
				actor.removeAction(actors); 
			}// any special effect or whatever on removal
		
			
		}
		actors = nextTurnActors;

		// Check for collisions between enemies and defense and set collision status
		for (Actor actor : actors) {
			for (Actor other : actors) {
				actor.setCollisionStatus(other);
			}
		}

		// Move the actors.
		for (Actor actor : actors) {
			actor.move(); // for enemies, only moves if not colliding.
		
		}

	
		// Redraw the new scene
		repaint();
	}
	
/**
 * addKnight method
 * This method, when called, calls the knight file and inputs a knight into our JFrame. It also subtracts coins from the user and adds the knight to the 
 * actors arraylist.
 * @param int col, the column to be placed in
 * @param int row, the row to be placed in
 */
	public void addKnight(int col, int row)

	{
				Knight knight = new Knight(new Point2D.Double(col, row)); //calling the knight file to create new knight in col and row
				actors.add(knight);//adding knight to actors arraylist
				coins = coins - 3;//taking away money
	}
	/**
	 * addArcher method
	 * This method, when called, calls the archer file and inputs a archer into our JFrame. It also subtracts coins from the user and adds the archer to the 
	 * actors arraylist.
	 * @param int col, the column to be placed in
	 * @param int row, the row to be placed in
	 */
	public void addArcher(int col, int row)
	{
		Archer archer = new Archer(new Point2D.Double(col, row)); //calling the archer file to add a new archer to our JFrame
		actors.add(archer);//adding archer to the actors arraylist
		coins = coins - 5;//subtracting money from user for buying the archer
	}
	/**
	 * levels method
	 * This method has no parameters. All this method does is control the amount and speed at which the dragons and wolfs spawn in at. It also controls the level 
	 * which is what controls how fast they spawn and if there are wolfs.
	 */
	public void levels()
	{
		if(level == 1)//level 1
		{
			if(counter < 5)
			{
				if(randGenerator.nextInt(200) == 5)
				{
					addDragon(1);
				}
			}
			else
			{
				level++;
				coins = coins + 20;
				money.setText("|| You have $" + coins);
			}
		}
		if(level == 2)//level 2
		{
			levelLabel.setText("Level: 2");
			if(counter < 15)
			{
				if(randGenerator.nextInt(250) == 5)
				{
					addDragon(2);
				}
			}
			else
			{
				level++;
				coins = coins + 40;
				money.setText("|| You have $" + coins);
			}
		}
		if(level == 3)//level 3
		{
			int random = randGenerator.nextInt(300);
			levelLabel.setText("Level: 3");
			if(counter < 25)
			{
				if(random == 5)
				{
					addWolf(3);
				}
				if(random == 10)
				{
					addDragon(3);
				}
			}
			else
			{
				level++;
				coins = coins + 60;
				money.setText("|| You have $" + coins);
			}
		}
		if(level == 4)//level 4
		{
			int random = randGenerator.nextInt(300);
			levelLabel.setText("Level: 4");
			if(counter < 35)
			{
				if(random == 5)
				{
					addWolf(4);
				}
				if(random == 10)
				{
					addDragon(4);
				}
			}
			else
			{
				level++;
				coins = coins + 80;
				money.setText("|| You have $" + coins);
			}
		}
		if(level == 5)//level 5
		{
			int random = randGenerator.nextInt(300);
			levelLabel.setText("Level: 5");
			if(counter < 45)
			{
				if(random == 5)
				{
					addWolf(5);
				}
				if(random == 10)
				{
					addDragon(5);
				}
			}
			else
			{
				level++;
				coins = coins + 100;
				money.setText("|| You have $" + coins);
			}
		}
		
	}
	/**
	 * addDragon method
	 * This method adds a dragon to the scene when called at a random position.
	 * @param int allowedRows, this parameter is the number of allowed rows which is controlled by the levels method.
	 */
	public void addDragon(int allowedRows)
	{
		int randRow = randGenerator.nextInt(allowedRows); //random number generator
				Dragon dragon = new Dragon(new Point2D.Double(7*75, randRow*75)); //calls dragon file and adds one to scene
				actors.add(dragon);//adds dragon to actors arraylist
				enemies.add(dragon);//adds dragon to enemies arraylist
				counter++;//increases counter to increase level
	}
	/**
	 * addWolf method
	 * This method adds a wolf to the scene in a random position.
	 * @param int allowedRows, this parameter is the number of allowed rows which is controlled by the levels method.
	 */
	public void addWolf(int allowedRows)
	{
		int randRow = randGenerator.nextInt(allowedRows);//random number generator
		WolfTank wolf = new WolfTank(new Point2D.Double(7*75, randRow*75)); //calling wolf file to add to our scene
		actors.add(wolf);//add wolf to actors arraylist
		enemies.add(wolf);//add wolf to enemies arraylist
		counter++;//increase counter to increase level

	}
	/**
	 * panelSetter method
	 * This method has no parameters because this method sets the panel and layout of the panel in which the buttons are in.
	 * It sets the placement of the buttons at the bottom of the screen along with the JLabels.
	 */
	public void panelSetter()
	{
		buttonAdderAndSetter();
		JPanel defensiveButtonPanel = new JPanel();
		defensiveButtonPanel.setPreferredSize(new Dimension(80 , 50));
		this.setLayout(new BorderLayout());
		
		defensiveButtonGroup.add(knightButton);
		defensiveButtonGroup.add(archerButton);
		defensiveButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		defensiveButtonPanel.add(levelLabel);
		defensiveButtonPanel.add(knightButton);
		defensiveButtonPanel.add(archerButton);
		defensiveButtonPanel.add(money);
		this.add(defensiveButtonPanel,BorderLayout.SOUTH);
	
		
	}
	/**
	 * buttonAdderAndSetter method
	 * This method has no parameters and all it does is set the buttons and JLabels. 
	 */
	public void buttonAdderAndSetter()
	{
		knightButton = new JRadioButton("Knight $3");
		archerButton = new JRadioButton("Archer $5");
		defensiveButtonGroup = new ButtonGroup();
		money = new JLabel("|| You have $" + coins);
		levelLabel = new JLabel("Level: 1");
		noNoNo = new JLabel("");
	}
	/**
	 * callGameOver method
	 * This method keeps track of the enemy position and checks if it hits the game over points.
	 * If triggered, a JPanel is suppose to pop up with game over on it, but instead the program stops.
	 * @param Actor actor, the actor that is being watched (which is all enemies)
	 */
	public void callGameOver(Actor actor)
	{
		position1 = new Point2D.Double(0,0);//game over point
		position2 = new Point2D.Double(0,1*75);//game over point
		position3 = new Point2D.Double(0,2*75);//game over point
		position4 = new Point2D.Double(0,3*75);//game over point
		position5 = new Point2D.Double(0,4*75);//game over point
		actorPosition = actor.getPosition();//actor position
		//if actor hits one of the positions one of these is triggered.
			if(actorPosition.equals(position1))
			{
				this.add(gameOverPanel);
			}
			if(actorPosition.equals(position2))
			{
				this.add(gameOverPanel);
			}
			if(actorPosition.equals(position3))
			{
				this.add(gameOverPanel);
			}
			if(actorPosition.equals(position4))
			{
				this.add(gameOverPanel);
			}
			if(actorPosition.equals(position5))
			{
				this.add(gameOverPanel);
			}
	}
	/**
	 * gameOver method
	 * This method has no parameters. This method only sets the JPanel, button, and text for if the gameOver is called, but instead
	 * the game just crashes.
	 */
	public void gameOver()
	{
		gameOverPanel = new JPanel();
		gameOverText = new JLabel("Game Over");
		playAgain = new JButton("Exit");
		
		gameOverPanel.setPreferredSize(new Dimension(50 + numCols * cellSize, 50 + numRows * cellSize));
		gameOverPanel.setBackground(Color.RED);
		gameOverPanel.add(gameOverText);
		gameOverPanel.add(playAgain);
		this.add(gameOverPanel);
		
	}
	/**
	 * Make the game and run it
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame app = new JFrame("Protect The Castle");
				app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				ProtectTheCastle panel = new ProtectTheCastle();

				app.setContentPane(panel);
				app.pack();
				app.setVisible(true);
		
			}
		});
	}

/**
 * mouseClicked mouseEvent
 * This mouseEvent is the only mouseEvent used. This finds the corner of the column and row
 * and adds the character selected there.
 */
	@Override
	public void mouseClicked(MouseEvent e) {
		//setting x and y variables to the coordinates.
		x = e.getX();
		y = e.getY();
		int findRow = y/cellSize;
		int row = cellSize * findRow;
		int findCol = x/cellSize;
		int col = cellSize * findCol;
		
		if(knightButton.isSelected())
		{
			if(coins >= knightCost)
			{
				addKnight(col,row);
				money.setText("|| You have $" + coins);
				archerButton.setSelected(false);
			}
		}
		
		if(archerButton.isSelected())
		{
			if(coins >= archerCost)
			{
				addArcher(col,row);
				money.setText("|| You have $" + coins);
				knightButton.setSelected(false);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}