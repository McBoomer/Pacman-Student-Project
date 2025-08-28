# Pacman Game – Muadh Khan

https://github.com/user-attachments/assets/9e271cde-fcc4-4410-bd66-3d77d4a36634

## Project Information
- **Name:** Muadh  
- **Group #:** Muadh  
- **Date:** 2023-12-15  
- **Course Code:**  
- **Section & Instructor:** ICS3U1-04/05 – Mrs. Biswas  
- **Title of Project:** Pacman Game – Muadh Khan  

## Description
This game is a variation of the Pacman game that allows the player to play as Pacman. The objective is to eat all the coins/food before the ghosts catch Pacman.  

The program has a title screen with two buttons: **Start** and **Map Editor**.  
- The **Start** button takes the player to a difficulty frame where they can select the level of difficulty (Normal, Hard, or Impossible).  
- Harder levels increase the speed of both the ghosts and the player.  
- Ghosts use line-of-sight detection to chase Pacman if no walls obstruct their path.  
- The **Map Editor** allows the player to create and edit maps using `map.txt`.  
- Additional features include background music, multiple board themes, and a menubar with a timer, score, map editor button, theme changer, and edit button.  

---

## Major Skills Used

### Object-Oriented Programming (OOP)
- Classes (`PacmanGUI`, `Pacman`, `Ghost`, `Tile`) used to model entities and behaviors.  

### Graphical User Interface (GUI) Programming
- Implemented with Java Swing.  

### Event Handling
- Uses `ActionListener` and `KeyListener` for buttons, timers, and keyboard input.  

### File Handling
- Reads maps from `map.txt`.  
- Opens maps in the system’s default text editor for editing.  

### Multithreading
- Uses `Timer` class for ghost movement and game state updates.  

### Exception Handling
- Try-catch blocks for audio file processing and I/O handling.  

### Array Manipulation
- Game board represented with a 2D array (`mapArray`).  

### Random Number Generation
- Randomly places Pacman, ghosts, and food on the board.  

### External Libraries
- Uses `AudioInputStream`, `AudioSystem`, and `Clip` for sound playback.  

### Control Flow
- Conditional statements (`if`, `switch`) and loops (`while`, `for`).  

### Swing Components
- Buttons, labels, panels for GUI.  

---

## Areas of Concern
- Map editor requires restarting the game to see changes.  

---

## Contribution to Assignment
- **Student Name:** Muadh  
- **Files Contributed:** All  
- **Methods Completed:**  
  - `playMusic`  
  - `startGame`, `startGameHard`, `startGameImpossible`  
  - `scoreboardPanelSetup`, `pacmanPanelSetup`  
  - `editMap`, `loadMapArrayFromTile`  
  - `fillTile`, `fillTileTheme2`  
  - `addPacman`, `findEmptyTile`  
  - `placeGhosts`, `findEmptyTileForGhosts`  
  - `placeFood`, `findEmptyTileForFood`  
  - `diffMenu`, `mainMenu`, `pacmanFrameSetup`  
  - `actionPerformed`, `keyPressed`, `keyReleased`  
  - `movePacmanForward`, `movePacman`  
  - `moveGhost`, `moveGhostRandomly`, `moveGhostTowardsPacman`, `moveGhostOnMap`  
  - `isPacmanInLineOfSight`  
  - `nextTileIsNotAWALL2`, `nextTileIsFood`  

- **Percentage of Work Completed:** 100%  

---

## Other Documentation
- [StackOverflow Reference – Adding Music to JFrame](https://stackoverflow.com/questions/16867976/how-do-you-add-music-to-a-jframe)  

---

## Features Implemented
1. Title Screen – with instructions ("Press any key to start").  
2. Fixed ghost movement.  
3. Player image faces correct direction.  
4. Different board themes.  
5. Level Creator – allows user-created maps (`map.txt`).  
6. Menubar – with options (New Game, Quit, etc.).  
7. Ghosts move toward Pacman with line-of-sight detection.  
8. Background music added.  
9. Pause button functionality.  
