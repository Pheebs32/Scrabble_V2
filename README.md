# Scrabble

---
This is my second attempt at a Scrabble game project in Java.
<br>I believe my first attempt was too much all at once for such a new language, and so I am restarting with a simpler approach.
#### Motivation
I am making a Scrabble Board game in each language I know to show progression through the language.
<br>This will continue as I learn new languages as well - hopefully.
#### Design/ Classes
The board will be made up of a 15 X 15 array of Cell objects.
<br> It will show bonuses that the cells have (eg, starting centre, double letter/ word and triple letter/ word)
<br> It will connect all the tiles to one another by their own pointers.
### Scrabble cells
X will refer to which row and Y will refer to which column.
<br>The 'current' cell will be able to access all of it's neighboring cells when initializing the board.
<br>Each cell with have a left, right ,top and bottom pointer.
<br>While connecting the cell with its neighbors, if the formula outputs an X or Y less than 0 or greater than 14 that
neighboring cell will be set to null.
### Scrabble Tile

### Scrabble Bag
0 points: Blank x2
<br>1 point: E x12, A x9, I x9, O x8, N x6 , R x6, T x6, L x4, S x4, U x4
<br>2 points: D x4, G x3
<br>3 points: B x2, C x2, M x2, P x2
<br>4 points: F x2, H x2, V x2, W x2, Y x2
<br>5 points: K x1
<br>8 points: J x1, X x1
<br>10 points: Q x1, Z x1
<br> The bag is going to be in charge of creating tile objects identical to the list above in terms of
quantity and property, place it into its Tile array and then shuffle the array.
<br>The game will remove tiles from the bag's Tile array and move it to the player and computers rack array to use.
### Player
The player will have a Tile array 'rack' for a potential word play and an integer variable 'score'.
<br>They are able to shuffle the tiles in their rack as well as organize it so that the tiles will hug the left side of the rack.
These actions will be available on the GUI as a 'shuffle' button. The shuffle button will not do anything if the players rack is empty.
The players button 'undo' will undo their tile placement on the board and bring it back to their rack(in array and GUI).
<br>Finally, the button 'submit' will check the players tile placement on the board if it does make a word. If a legit word is played, then the score is updated. 
If not, nothing will happen and the score will remain the same.
### Main/GUI
The GUI (main) class deals with initializing all GUI panels, button ect.
### Engine
The engine class will mostly contain data structures and algorithms to verify user moves along with player, computer and board objects to update scores/data.
### Dictionary
Searches through 'file' to check if valid.
### Trie/ Sort
Insert and search methods.
