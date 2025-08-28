
// Imports
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PacmanGUI extends JFrame implements ActionListener, KeyListener{
	private final ImageIcon MENUBG = new ImageIcon("images/menuback.png");
	private final ImageIcon DIFFIMG = new ImageIcon("images/diffmenu.png");
	private final ImageIcon PATH = new ImageIcon("images/PATH.png");
	private final ImageIcon WALL = new ImageIcon("images/wall2.png");
	private final ImageIcon[] PACMAN = {new ImageIcon("images/pacman.png"), 
										new ImageIcon("images/pacman1.png"),
										new ImageIcon("images/pacman2.png"),
										new ImageIcon("images/pacman3.png")};
	// Blue ghost 1, 2 and food
	private final ImageIcon BLUEGHOST = new ImageIcon("images/blueGhost.png");
	private final ImageIcon BLUEGHOST2 = new ImageIcon("images/blueGhost2.png");
	private final ImageIcon FOOD = new ImageIcon("images/food.png");
	// Set number of food on map
	private final int NUM_FOOD = 25;
	// New pacman instance from Pacman.java
	private Pacman pacman = new Pacman(0, 0, PACMAN[0]);
	// New ghost instance from Ghost.java
	private Ghost blueGhost = new Ghost(5, 5, BLUEGHOST);
	private Ghost blueGhost2 = new Ghost(5, 5, BLUEGHOST);

	// Set starting score
	private int score = 0;
	// Time for timer
	private int time = 0;
	private long elapsedTime = 0;  // Elapsed time in milliseconds
	private Timer timeTimer;  
	// Ghost timer (Changed in different diffuculty for speed)
	private Timer ghostTimer;
	// Pacman panel on which pacman will go
	private JPanel pacmanPanel = new JPanel();
	// X Y sizing of 25x25 tiles
	private Tile[][] mapArray = new Tile[29][27];
	// Labels and panels for game
	private JPanel scoreboardPanel = new JPanel();
	private JLabel scoreLabel = new JLabel("0");
	private Timer gameTimer = new Timer(1000, this);
	// Boolean to check if the game is paused
	private boolean isPaused = false;
	// Audio clip
	private Clip clip;
	// Public construcor
    public PacmanGUI() {
		// Runs main menu and plays music when run
        mainMenu();
        playMusic("fx/backgroundmusic.wav");
    }
	// Music player
    private void playMusic(String filePATH2) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePATH2));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
			// Looping the music wav file
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error playing music: " + e.getMessage());
        }
    }
	// Game starting method
	public void startGame() {
		// Sets up 
		scoreboardPanelSetup();
		pacmanPanelSetup();
		pacmanFrameSetup();
		placeSecondBlueGhost();
		//
		ghostTimer = new Timer(300, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				//
				moveGhost(blueGhost);
				moveGhost(blueGhost2);
				movePacmanForward();
				repaint();
			}
		});
		// Start the timer that was made above
		ghostTimer.start();
	}
	// startGame method repeated only with a faster ghost timer delay
	public void startGameHard() {
		scoreboardPanelSetup();
		pacmanPanelSetup();
		pacmanFrameSetup();
		//
		ghostTimer = new Timer(100, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				//
				moveGhost(blueGhost);
				moveGhost(blueGhost2);
				movePacmanForward();
				repaint();
			}
		});
		
		ghostTimer.start();
	}
	// startGameHard method repeated only with a faster ghost timer delay
	public void startGameImpossible() {
		scoreboardPanelSetup();
		pacmanPanelSetup();
		pacmanFrameSetup();
		//
		ghostTimer = new Timer(1, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				//
				moveGhost(blueGhost);
				moveGhost(blueGhost2);
				movePacmanForward();
				repaint();
			}
		});
		
		ghostTimer.start();
	}
	// Setup components for the score board panel at the top of the game screen
	private void scoreboardPanelSetup() {
		scoreboardPanel.setBounds(0, 0, 27 * mapArray[0].length, 27);
		scoreboardPanel.setLayout(new GridLayout(1, 2, 10, 10));
		scoreboardPanel.setBackground(Color.BLACK);
		// Exit button (RED)
		JButton exit = new JButton();
		exit.setBounds(0,0,30,27);
		exit.setText("EXIT"); 
		exit.setBorderPainted(false);
		exit.setBackground(Color.BLACK);
		exit.setForeground(Color.RED);
		exit.setFocusable(false);
		exit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		exit.setContentAreaFilled(false);
		exit.setFont(new Font("Broadway", Font.BOLD, 20));

		ActionListener exitListener = new ActionListener() {

			
			public void actionPerformed(ActionEvent e) {
				// Closes program
				System.exit(0);
			}
			
		};

		exit.addActionListener(exitListener);
		// Timer object
		JLabel timer = new JLabel("0.00");
		timer.setBounds(scoreboardPanel.getWidth() / 2, 0, 100, 27);
		timer.setFont(new Font("Broadway", Font.BOLD, 20));
		timer.setForeground(Color.WHITE);
		// Pause button
		JButton pause = new JButton();
		pause.setBounds(0, 0, 30, 27);
		pause.setText("▐▐ ");
		pause.setBorderPainted(false);
		pause.setBackground(Color.BLACK);
		pause.setForeground(Color.WHITE);
		pause.setFocusable(false);
		pause.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		pause.setContentAreaFilled(false);

		ActionListener pauseListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// Toggle the game's paused state bool from the private bool above
				isPaused = !isPaused;

				if (isPaused) {
					pause.setText("▶");
					ghostTimer.stop();
					gameTimer.stop();
				} else {
					pause.setText("▐▐");
					ghostTimer.start();
					gameTimer.start();
				}
			}
		};

		pause.addActionListener(pauseListener);
		// Map editor button
		JButton editMapButton = new JButton();
		editMapButton.setBounds(0, 0, 30, 27);
		editMapButton.setText("MAP");
		editMapButton.setBorderPainted(false);
		editMapButton.setBackground(Color.BLACK);
		editMapButton.setForeground(Color.WHITE);
		editMapButton.setFocusable(false);
		editMapButton.setContentAreaFilled(false);
		editMapButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		editMapButton.setFont(new Font("", Font.BOLD, 20));
	
		ActionListener editAndSaveListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// Calls the same method used in the main menu
				editMap();
			}
		};
	
		editMapButton.addActionListener(editAndSaveListener);
		// Changes theme of scoreboard and ghosts
		JButton theme = new JButton();
		theme.setBounds(0,0,30,27);
		theme.setText("✎");
		theme.setBorderPainted(false);
		theme.setBackground(Color.BLACK);
		theme.setForeground(Color.WHITE);
		theme.setFocusable(false);
		theme.setContentAreaFilled(false);
		theme.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		theme.setFont(new Font("", Font.BOLD, 20));

		ActionListener themeListener = new ActionListener() {

			
			public void actionPerformed(ActionEvent e) {
				scoreboardPanel.setBackground(Color.WHITE);
				scoreLabel.setForeground(Color.BLACK);
				exit.setBackground(Color.WHITE);
				theme.setBackground(Color.WHITE);
				theme.setForeground(Color.BLACK);
				theme.setText("LIGHT");
				theme.setEnabled(false);
				editMapButton.setBackground(Color.WHITE);
				editMapButton.setForeground(Color.BLACK);
				blueGhost.setIcon(BLUEGHOST2);
				blueGhost2.setIcon(BLUEGHOST2);
			}
			
		};

		theme.addActionListener(themeListener);

		// Add a timer label
		JLabel timerLabel = new JLabel("0.00");
		timerLabel.setFont(new Font("Broadway", Font.BOLD, 20));
		timerLabel.setForeground(Color.WHITE);
	
		// Initialize the time timer
		timeTimer = new Timer(10, new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if (!isPaused) {
					elapsedTime += 10;  // Increment by 10 milliseconds
					updateTimerLabel(timerLabel);
				}
			}
		});
	
		// Start the time timer
		timeTimer.start();
	

		scoreLabel.setBounds(scoreboardPanel.getWidth() / 2, 0, 100, 27);
		scoreLabel.setFont(new Font("Broadway", Font.BOLD, 20));
		scoreLabel.setForeground(Color.WHITE);
		//add timer label and set text of timer label to reflect changes 
		// Adding all the components to the scoreboard panel
		scoreboardPanel.add(pause);
		scoreboardPanel.add(scoreLabel);
		scoreboardPanel.add(timerLabel);
		scoreboardPanel.add(theme);
		scoreboardPanel.add(editMapButton);
		scoreboardPanel.add(exit);

	}
	// This updates the timer label 
	private void updateTimerLabel(JLabel timerLabel) {
		long seconds = elapsedTime / 1000;
		long milliseconds = elapsedTime % 1000;
		timerLabel.setText(String.format("%d.%02d", seconds, milliseconds / 10));

	}
	// Pacman panel setup which holds food, ghosts and pacman
	private void pacmanPanelSetup() {
		pacmanPanel.setBounds(0, 50, 27 * mapArray[0].length, 27 * mapArray.length);
		// Sets the size of the paacman panel 25x25 pixels * 29x27 tiles
		pacmanPanel.setLayout(new GridLayout(29, 27, 0, 0));
		
		// loads the map file from the tiles
		loadMapArrayFromTile();
		// Adds game components to the frame
		addPacman();
		placeGhosts();
		placeFood();
	}
	// Edit map method that opens map.txt or throws error if map file not found
	private void editMap() {
		File file = new File("map.txt");

		try {
			// Open the default text editor for the file
			Desktop.getDesktop().edit(file);

			// Inform the user that the file is opened for editing
			JOptionPane.showMessageDialog(this, "File opened for editing. Save and rerun the program to see your changes on the map.");

		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error | Pacman Muadh, could not find file.");
		}
	}	
	// Loads map array from tile
	private void loadMapArrayFromTile() {
		int row = 0;
		char[] line;
		//
		try {
			Scanner inputFile = new Scanner(new File("map.txt"));
			//
			while (inputFile.hasNext()) {
				//
				line = inputFile.nextLine().toCharArray();
				//
				for (int col = 0; col < line.length; col++) {
					fillTile(line[col], row, col);
				}
				row++;
			}
			// closes the file input reader
			inputFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	// Sets the game frame with the walls and paths after reading map.txt 
	private void fillTile(char c, int row, int col) {
		mapArray[row][col] = new Tile(row, col);
		
		// If map.txt contains a 'W', then the tile is a wall
		if (c == 'W')
			mapArray[row][col].setIcon(WALL);
		// If map.txt contains a '.', then the tile is a path
		else if (c == '.')
			mapArray[row][col].setIcon(PATH);
		
		// Adds the walls and paths to the pacman panel
		pacmanPanel.add(mapArray[row][col]);
	}
	// Unused
	private void fillTileTheme2(char c, int row, int col) {
		//
		mapArray[row][col] = new Tile(row, col);
		
		//
		if (c == 'W')
			mapArray[row][col].setIcon(WALL);
		else if (c == '.')
			mapArray[row][col].setIcon(PATH);
		
		//
		pacmanPanel.add(mapArray[row][col]);
	}

	private void addPacman() {
		// Uses find empty tile method from below to locate a tile
		// that is not a wall tile, food tile or ghost tile and places pacman there
		Tile tile = findEmptyTile();
		// Sets the location of pacman on the located empty tile
		pacman.setRow(tile.getRow());
		pacman.setCol(tile.getCol());
		
		// Setting pacmans location
		mapArray[pacman.getRow()][pacman.getCol()].setIcon(pacman.getIcon());
	}

	// Modify the findEmptyTile method to check for walls
	private Tile findEmptyTile() {
		Tile tile = new Tile();

		do {
			tile.setRow((int)(Math.random() * 29));
			tile.setCol((int)(Math.random() * 27));
		} while (mapArray[tile.getRow()][tile.getCol()].getIcon() != PATH ||
				mapArray[tile.getRow()][tile.getCol()].getIcon() == WALL);

		return tile;
	}

	// Uses find empty tile for ghosts method to locate an empty tile without wall, food or pacman on it
	private void placeGhosts() {
		// Uses find empty tile for ghosts method to locate an empty tile without wall, food or pacman on it
		Tile tile = findEmptyTileForGhosts();
		// Places ghost1 on spot
		blueGhost.setRow(tile.getRow());
		blueGhost.setCol(tile.getCol());

		mapArray[blueGhost.getRow()][blueGhost.getCol()].setIcon(blueGhost.getIcon());
	}
	// Uses find empty tile for ghosts method to locate an empty tile without wall, food or pacman on it
	// Then places blueghost2 on it
	private Tile findEmptyTileForGhosts() {
		Tile tile = new Tile();
		do {
			tile.setRow((int) (Math.random() * 29));
			tile.setCol((int) (Math.random() * 27));
		} while (mapArray[tile.getRow()][tile.getCol()].getIcon() != PATH ||
				mapArray[tile.getRow()][tile.getCol()].getIcon() == WALL ||
				(tile.getRow() == pacman.getRow() && tile.getCol() == pacman.getCol()));

		return tile;
	}
	// Places the food component on it
	private void placeFood() {
		for (int food = 1; food <= NUM_FOOD; food++) {
			Tile tile = findEmptyTileForFood();
			mapArray[tile.getRow()][tile.getCol()].setIcon(FOOD);
		}
	}
	// Finding empty tile to place food
	private Tile findEmptyTileForFood() {
		Tile tile = new Tile();
		do {
			tile.setRow((int) (Math.random() * 29));
			tile.setCol((int) (Math.random() * 27));
		} while (mapArray[tile.getRow()][tile.getCol()].getIcon() != PATH ||
				mapArray[tile.getRow()][tile.getCol()].getIcon() == WALL);
	
		return tile;
	}
	// Difficulty levels method 
	private void diffMenu() {
		JFrame diffMenu = new JFrame();
		diffMenu.setTitle("Pacman Muadh - Levels");
		ImageIcon favicon = new ImageIcon("images/pacman.png");
		setIconImage(favicon.getImage());
		diffMenu.setIconImage(favicon.getImage());
		//d
		diffMenu.setSize(625, 375);
		
		diffMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		diffMenu.setResizable(false);
		diffMenu.setLocationRelativeTo(null);
		diffMenu.setVisible(true);

		JPanel start = new JPanel();
		start.setBounds(0, 0, 625, 735);
		start.setBackground(Color.BLACK);
		diffMenu.add(start);
		// Background image label
		JLabel bgImg = new JLabel(DIFFIMG);
		start.add(bgImg);
		// Normal game button
		JButton normalButton = new JButton();
		normalButton.setBounds(255, 133, 120, 30);
		normalButton.setBorderPainted(false);
		normalButton.setOpaque(false);
		normalButton.setBackground(Color.BLACK);
		normalButton.setContentAreaFilled(false);
		normalButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		normalButton.setFocusable(false);

		ActionListener normalListener = new ActionListener() {

			
			public void actionPerformed(ActionEvent e) {
				startGame();
				diffMenu.dispose();

			}
			
		};

		normalButton.addActionListener(normalListener);
		bgImg.add(normalButton);
		// Hard game button
		JButton hardButton = new JButton();
		hardButton.setBounds(255, 185, 120, 30);
		hardButton.setBorderPainted(false);
		hardButton.setOpaque(false);
		hardButton.setBackground(Color.BLACK);
		hardButton.setContentAreaFilled(false);
		hardButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		hardButton.setFocusable(false);

		ActionListener hardListener = new ActionListener() {

			
			public void actionPerformed(ActionEvent e) {
				diffMenu.dispose();
				startGameHard();
			}
			
		};

		hardButton.addActionListener(hardListener);
		bgImg.add(hardButton);
		// Impossible game method
		JButton impButton = new JButton();
		impButton.setBounds(245, 235, 140, 30);
		impButton.setBorderPainted(false);
		impButton.setOpaque(false);
		impButton.setBackground(Color.BLACK);
		impButton.setContentAreaFilled(false);
		impButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		impButton.setFocusable(false);

		ActionListener impListener = new ActionListener() {

			
			public void actionPerformed(ActionEvent e) {
				startGameImpossible();
				diffMenu.dispose();
			}
			
		};

		impButton.addActionListener(impListener);
		bgImg.add(impButton);

	}
	// Main menu frame that opens when you run
	private void mainMenu() {
		//
		JFrame menuFrame = new JFrame();
		menuFrame.setTitle("Pacman Muadh - Menu");
		ImageIcon favicon = new ImageIcon("images/pacman.png");
		setIconImage(favicon.getImage());
		menuFrame.setIconImage(favicon.getImage());
		//d
		menuFrame.setSize(625, 375);
		
		menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuFrame.setResizable(false);
		menuFrame.setLocationRelativeTo(null);
		menuFrame.setVisible(true);
		// Panel that buttons go on
		JPanel start = new JPanel();
		start.setBounds(0, 0, 625, 375);
		start.setBackground(Color.BLACK);
		menuFrame.add(start);

		JLabel bgImg = new JLabel(MENUBG);
		start.add(bgImg);
		// Unused
		JButton mute = new JButton();
		mute.setBounds(225, 165, 170, 60);
		mute.setBorderPainted(false);
		mute.setOpaque(false);
		mute.setBackground(Color.BLACK);
		mute.setContentAreaFilled(false);
		mute.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mute.setFocusable(false);

		ActionListener muteListener = new ActionListener() {

			
			public void actionPerformed(ActionEvent e) {
			}
			
		};

		mute.addActionListener(muteListener);
		//bgImg.add(mute);
		// Start button that opens the difficulty chooser frame
		JButton startButton = new JButton();
		startButton.setBounds(225, 165, 170, 60);
		startButton.setBorderPainted(false);
		startButton.setOpaque(false);
		startButton.setBackground(Color.BLACK);
		startButton.setContentAreaFilled(false);
		startButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		startButton.setFocusable(false);

		ActionListener startListener = new ActionListener() {

			
			public void actionPerformed(ActionEvent e) {
				diffMenu();
			}

		};

		startButton.addActionListener(startListener);
		bgImg.add(startButton);
		// Map editor button
		JButton editor = new JButton();
		editor.setBounds(175, 240, 290, 50);
		editor.setBorderPainted(false);
		editor.setOpaque(false);
		editor.setBackground(Color.BLACK);
		editor.setContentAreaFilled(false);
		editor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		editor.setFocusable(false);

		ActionListener editorListener = new ActionListener() {
			// Opens map.txt file to edit
			public void actionPerformed(ActionEvent e) {
				File file = new File("map.txt");
		// Try catch
		try {
			// Open the the file
			Desktop.getDesktop().edit(file);

			// Tell user file is opened
			JOptionPane.showMessageDialog(menuFrame, "File opened for editing. Save and rerun to see changes on the map.");
		// Error message
		} catch (IOException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(menuFrame, "Error | Pacman Muadh, could not find file.");
		}
			}
			
		};

		// JButton difficulty = new JButton();
		// difficulty.setBounds(185, 230, 265, 45);
		// difficulty.setBorderPainted(false);
		// difficulty.setOpaque(false);
		// difficulty.setBackground(Color.BLACK);
		// difficulty.setContentAreaFilled(false);
		// difficulty.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		// difficulty.setFocusable(false);

		// ActionListener difficultyListener = new ActionListener() {

		// 	
		// 	public void actionPerformed(ActionEvent e) {
		// 		startButton.setEnabled(false);
		// 		difficulty.setEnabled(false);
		// 		editor.setEnabled(false);
		// 		System.out.println("asd");

		// 		JButton normalButton = new JButton();
		// 		normalButton.setBounds(175, 270, 290, 50);
		// 		normalButton.setBorderPainted(false);
		// 		//normalButton.setOpaque(false);
		// 		normalButton.setBackground(Color.BLACK);
		// 		normalButton.setContentAreaFilled(false);
		// 		normalButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		// 		normalButton.setFocusable(false);
		// 		ActionListener normalListener = new ActionListener() {

		// 			
		// 			public void actionPerformed(ActionEvent e) {
		// 				System.out.println("asd");

		// 			}
					
		// 		};
		// 		normalButton.addActionListener(normalListener);
		// 		bgImg.add(normalButton);

		// 	}
				
		// };
		
		
		// difficulty.addActionListener(difficultyListener);
		// bgImg.add(difficulty);

	}
	
	// Main game frame
	private void pacmanFrameSetup() {
		//
		setTitle("Pacman Muadh");
		//
		setSize(27 * mapArray[0].length, 27 * mapArray.length);
		//
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		setBackground(Color.BLACK);

		//
		add(scoreboardPanel, BorderLayout.NORTH);
		add(pacmanPanel, BorderLayout.CENTER);	
		//
		addKeyListener(this);
		//System.out.println("Key Listener Has Been Added");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		Dimension size = this.getSize();
		System.out.println(size);
		setVisible(true);
		
		//specify what time will be
		gameTimer.start();
	}

	public void actionPerformed(ActionEvent e) {
		
	}

	public void keyTyped(KeyEvent e) {
		
	}

	// Modify the PacmanGUI class

	// Add a new variable to keep track of Pacman's direction
	private int pacmanDirection = KeyEvent.VK_RIGHT; // Initially set to move right

	// Modify the keyPressed method
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		// Update the direction based on the key pressed
		// Arrow keys and WASD work on this game
		if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
			pacmanDirection = KeyEvent.VK_UP;
		} else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
			pacmanDirection = KeyEvent.VK_DOWN;
		} else if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
			pacmanDirection = KeyEvent.VK_LEFT;
		} else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
			pacmanDirection = KeyEvent.VK_RIGHT;
		}
	}

	// Add a new method to move Pacman continuously in the current direction
	private void movePacmanForward() {
		int dRow = 0, dCol = 0;

		// Update the direction based on the current value of pacmanDirection
		switch (pacmanDirection) {
			case KeyEvent.VK_UP:
				dRow = -1;
				pacman.setIcon(PACMAN[3]); // Update the icon to face up
				break;
			case KeyEvent.VK_DOWN:
				dRow = 1;
				pacman.setIcon(PACMAN[2]); // Update the icon to face down
				break;
			case KeyEvent.VK_LEFT:
				dCol = -1;
				pacman.setIcon(PACMAN[1]); // Update the icon to face left
				break;
			case KeyEvent.VK_RIGHT:
				dCol = 1;
				pacman.setIcon(PACMAN[0]); // Update the icon to face right
				break;
		}

		// Perform the movement logic similar to the original movePacman method
		if (mapArray[pacman.getRow() + dRow][pacman.getCol() + dCol].getIcon() != WALL) {
			mapArray[pacman.getRow()][pacman.getCol()].setIcon(PATH);
			pacman.move(dRow, dCol);
			if (mapArray[pacman.getRow()][pacman.getCol()].getIcon() == FOOD) {
				score++;
				scoreLabel.setText(Integer.toString(score * 10));
			}
			mapArray[pacman.getRow()][pacman.getCol()].setIcon(pacman.getIcon());

			if (score == NUM_FOOD) {
				gameTimer.stop();
				JOptionPane.showMessageDialog(this, "You Win!");
				System.exit(0);
			}

			if (mapArray[pacman.getRow()][pacman.getCol()] == mapArray[blueGhost.getRow()][blueGhost.getCol()]) {
				JOptionPane.showMessageDialog(this, "You Lost!");
				System.exit(0);
			}
		}
	}

	// Remove the keyPressed and keyTyped methods as they are not needed anymore
	// Unused
	private void movePacman(Ghost ghost, int dRow, int dCol) {
		//		mapArray[pacman.getRow()][pacman.getCol()].setIcon(PATH);
				if (mapArray[pacman.getRow() + dRow][pacman.getCol() + dCol].getIcon() == FOOD) {
			score++;
			scoreLabel.setText(Integer.toString(score * 10));
		}
		
		pacman.move(dRow, dCol);
		mapArray[pacman.getRow()][pacman.getCol()].setIcon(pacman.getIcon());
		
		// If the score variable is equal to the set number of food placed, then end game
		if (score == NUM_FOOD) {
			gameTimer.stop();
			JOptionPane.showMessageDialog(this, "You Win!");
			System.exit(0);

		}
		
		if (mapArray[pacman.getRow()][pacman.getCol()] == mapArray[ghost.getRow()][ghost.getCol()]) {
			JOptionPane.showMessageDialog(this, "You Lost!");
			this.dispose();
			System.exit(0);
		}
	}
	// Places second blue ghost
	private void placeSecondBlueGhost() {
		Tile tile = findEmptyTile();
		blueGhost2.setRow(tile.getRow());
		blueGhost2.setCol(tile.getCol());
	
		mapArray[blueGhost2.getRow()][blueGhost2.getCol()].setIcon(blueGhost2.getIcon());
	}
	
	private void moveGhost(Ghost ghost) {
		if (isPacmanInLineOfSight(ghost)) {
			// Pacman is in line of sight, follow Pacman
			moveGhostTowardsPacman(ghost);
		} else {
			// Pacman is not in line of sight, move randomly
			moveGhostRandomly(ghost);
		}
	
		if (mapArray[pacman.getRow()][pacman.getCol()] == mapArray[ghost.getRow()][ghost.getCol()]) {
			this.dispose();
			System.exit(0);
			JOptionPane.showMessageDialog(this, "You Lost!");
		}
	}
	
	private boolean isPacmanInLineOfSight(Ghost ghost) {
		int pacmanRow = pacman.getRow();
		int pacmanCol = pacman.getCol();
	
		int ghostRow = ghost.getRow();
		int ghostCol = ghost.getCol();
	
		// Check if Pacman is in the same row or column and there are no WALL2s in between
		return pacmanRow == ghostRow || pacmanCol == ghostCol;
	}
	
	private void moveGhostRandomly(Ghost ghost) {
		// Randomly select a direction for movement
		int[] directions = {-1, 0, 1};
		int dRow = directions[(int) (Math.random() * 3)];
		int dCol = directions[(int) (Math.random() * 3)];
	
		if (nextTileIsNotAWALL2(ghost, dRow, dCol)) {
			// Move randomly
			moveGhostOnMap(ghost, dRow, dCol);
		}
	}
	
	private void moveGhostTowardsPacman(Ghost ghost) {
		int pacmanRow = pacman.getRow();
		int pacmanCol = pacman.getCol();
	
		int ghostRow = ghost.getRow();
		int ghostCol = ghost.getCol();
	
		int dRow = 0, dCol = 0;
	
		// Calculate the direction to move towards Pacman
		if (pacmanRow > ghostRow) {
			dRow = 1; // Move down
		} else if (pacmanRow < ghostRow) {
			dRow = -1; // Move up
		}
	
		if (pacmanCol > ghostCol) {
			dCol = 1; // Move right
		} else if (pacmanCol < ghostCol) {
			dCol = -1; // Move left
		}
	
		if (nextTileIsNotAWALL2(ghost, dRow, dCol)) {
			// Move towards Pacman
			moveGhostOnMap(ghost, dRow, dCol);
		}
	}
	
	


	private boolean nextTileIsNotAWALL2(Ghost ghost, int dRow, int dCol) {
		int newRow = ghost.getRow() + dRow;
		int newCol = ghost.getCol() + dCol;
		
		return newRow >= 0 && newRow < mapArray.length && 
				newCol >= 0 && newCol < mapArray[0].length &&
				mapArray[newRow][newCol].getIcon() != WALL;
				
	}
	// Unused
	private boolean nextTileIsFood(Ghost ghost, int dRow, int dCol) {
		boolean check = true;
		
		if (mapArray[ghost.getRow()][ghost.getCol()].getIcon() == FOOD) {
			check = false;
 		}
		return check;
	}
	// Moves ghost on map usign the map array tiles
	private void moveGhostOnMap(Ghost ghost, int dRow, int dCol) {
		int newRow = ghost.getRow() + dRow;
		int newCol = ghost.getCol() + dCol;
	
		if (newRow >= 0 && newRow < mapArray.length &&
			newCol >= 0 && newCol < mapArray[0].length) {
			// If pacman collides with non food objects then turn food into path object after pacman if not on the tile anymore
			if (mapArray[newRow][newCol].getIcon() != FOOD) {
				// Only move the ghost if the next tile is not food
				mapArray[ghost.getRow()][ghost.getCol()].setIcon(PATH);
				ghost.move(dRow, dCol);
				mapArray[ghost.getRow()][ghost.getCol()].setIcon(ghost.getIcon());
			}
		}
	}
	// Key released method
	public void keyReleased(KeyEvent e) {
		
	}
}