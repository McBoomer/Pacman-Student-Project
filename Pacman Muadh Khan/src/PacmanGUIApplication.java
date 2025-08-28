/*Name: Muadh
Group #; Muadh
Date: 2023-12-15
Date of Submission
Course Code:
The Course and Section number and Instructor (e.g. ICS3U1-04/05 Mrs. Biswas)
Title:
Title of Project: Pacman Game Muadh Khan
Description: This game is a variation of the pacman game that allows the player to play as pacman and the players
Objective is to eat all the coins/food before the ghosts eat the pacman.
Paragraph that describes your project and its basic functions

My program has a title screen with two buttons on it, start and map editor. The start button will take the player to the difficulty frame
in which the player will select what level of difficulty the player wants to play. There is normal, hard and impossible. 
The harder levels will have a  faster ghost and a faster player. The ghost is programmed to draw lines to the pacman object. As long as there
are no obstructions such as walls in the path of the line, ghosts will lock onto the player and follow pacman. A map editor allows the player
to create and edit maps using the map.txt file. There is music in the background, different baord themes and a menubar with
a timer, score, map editor button, theme changer and edit button.

Major Skills:
Object-Oriented Programming (OOP):

The program uses classes (PacmanGUI, Pacman, Ghost, Tile) to model entities and their behaviors. This is a fundamental aspect of OOP.
Graphical User Interface (GUI) Programming:

The program uses Java's Swing library to create a graphical user interface for the Pacman game.
Event Handling:

The ActionListener and KeyListener interfaces are implemented to handle events triggered by buttons, timers, and keyboard input.
File Handling:

The program reads the game map from a text file (map.txt). It also opens the file in the system's default text editor for user editing.
Multithreading:

The program utilizes timers (Timer class) for periodic tasks, such as moving ghosts and updating the game state.
Exception Handling:

The program includes try-catch blocks to handle exceptions related to audio file processing and file input/output.
Array Manipulation:

The game board is represented using a 2D array (mapArray), and the program manipulates the array to represent the game state.
Random Number Generation:

Random numbers are generated to place Pacman, ghosts, and food randomly on the game board.
Use of External Libraries:

The program uses external libraries (AudioInputStream, AudioSystem, Clip) to handle sound playback.
Conditional Statements and Looping:

Conditional statements (if, switch) and loops (while, for) are used for various control flow and iteration tasks.
Swing Components:

Swing components such as buttons, labels, and panels are used to create the graphical user interface.
File Input/Output:

The program uses Scanner to read the game map from a text file.

Areas of Concern:
Map editor requires you to restart the game to see the changes on screen


Contribution to Assignment:
Student Name: Muadh
Which Java file(s) did you contribute to completing the assignment: All of them
Name the method(s) you completed: 
playMusic
startGame
startGameHard
startGameImpossible
scoreboardPanelSetup
pacmanPanelSetup
editMap
loadMapArrayFromTile
fillTile
fillTileTheme2
addPacman
findEmptyTile
placeGhosts
findEmptyTileForGhosts
placeFood
findEmptyTileForFood
diffMenu
mainMenu
pacmanFrameSetup
actionPerformed
keyPressed
movePacmanForward
movePacman
moveGhost
isPacmanInLineOfSight
moveGhostRandomly
moveGhostTowardsPacman
nextTileIsNotAWALL2
nextTileIsFood
moveGhostOnMap
keyReleased
What percentage (%)of the work on this page you completed: 100%

Other Required Documentation: https://stackoverflow.com/questions/16867976/how-do-you-add-music-to-a-jframe


Add a separate Opening Screen before the game starts
*  Title screen – A title screen is displayed before the game is started.
*  You can design the title screen freely, but you must include instructions such as "Press any key to start". You may also want to put the how-to-play information within the title screen.
 * Feature 2: Fix movement of ghost
 * Feature 3: Get player image to Face the Proper Direction as they move
 * Feature 4: Different board themes
 * Feature 5: Add a Level Creator feature where the user can create and save their own maps that can then be played in the future (Advanced Feature)
 * Feature 6: Add a Menubar - with a number of options (New Game, Quit, etc.)
 * Feature 7: Ghost actually is programmed to move towards Pacman
 * Feature 8: Add Music
 * Feature 9: Added pause button
 */

public class PacmanGUIApplication {
	public static void main(String[] args) {
		new PacmanGUI();
	}
}
