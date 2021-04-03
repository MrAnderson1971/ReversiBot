# Reversi

## Board game + bot 

- What will the application do?  

The application will provide a computer version of the strategy board game *Reversi*, also known as *Othello*. 
The board consists of 8x8 squares, along with 64 pieces, each black on one side and white on the other. When a player places a piece, any opposing pieces between the piece that was just placed and another friendly piece (either horizontally, vertically, or diagonally) gets "flipped" and become friendly pieces. Game ends when a player has no moves left. Whoever has the most amount of friendly pieces on the board wins.

This application supports player vs player and player vs computer.

- Who will use it?

Anyone who's bored, or wish to practice Othello against a variable-difficulty robot.

- Why is this project of interest to you?

Board games are fun and interesting to program. Othello in particular, is neither too simply nor to complex to design. 
Also due to the fact that it's a strategy game that is not too complex, it should not be too hard to write an AI using a
tree search algorithm that can play the game proficiently.

### User stories

- As a user, I want to be able to play a 2 player Othello game against another human player.
- As a user, I also want to be able to play an Othello game against a computer opponent with an adjustable difficulty.
- As a user, I want to be able to view a record of all moves made by both players when a game ends.
- As a user, I want to add/delete logs of all moves made during a game to/from "record" so that I can view replays of previous games.

- As a user, I want to be able to autosave the game history record to a file.
- As a user, I want to be able to autoload the game history record from a file.

Sound effect from: https://www.youtube.com/watch?v=h8y0JMVwdmM

#### Phase 4: Task 2

Tree.updateMove() throws NodeNotFoundException to make it robust
tests in TreeTest.java

#### Phase 4: Task 3
There is a lot of refactoring that can be done, especially with the base "Othello.java" class. For example, the connection between Othello.java and MoveHistory can be removed, instead, Othello.java can call Game.java's "getMoveHistory()". The link between Game.java and Player.java can also be removed without any more shuffling of code.