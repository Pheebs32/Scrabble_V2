import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
public class Main {

    private JFrame frame;
    JButton[][] cellButtons; // 15x15 button array representing cells on board
    JButton[] rackButtons; // 7 buttons for each tile in players rack

    HashMap<JButton, Cell> cellMap = new HashMap<>(); // helps locate board cell by button
    HashMap<JButton, Tile> rackMap = new HashMap<>(); // helps match rack GUI with actual rack data structure

    Stack<Tile> recentlyPlayedTileStack = new Stack<>(); // keep chronological order of recent tile/cell/cell button played
    Stack<Cell> recentlyPlayedCellStack = new Stack<>();
    Stack<JButton> recentlyPlayedCellButtonStack = new Stack<>();

    JPanel boardPanel; // a panel to hold cellButtons array or board of cells
    JPanel playerPanel;  // a panel to hold rack panel of tiles to play + player options in game such as skip turn, play tiles, ect
    JPanel rackPanel; // a panel to hold only tiles ready to play

    Board board;
    Bag bag;
    Player player;
    Tile rackTileSelected; // when players clicks on a tile button in their rack to play on board, this variable stores the tile to a copy onto the board

    private JPanel logoPanel;
    private JLabel logoLabel;

    // Launch the application.
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main window = new Main();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Create the application.
    public Main() {
        initialize();
    }

    // Initialize the contents of the frame.
    public void initialize() {
        createWindow();
        createBoardPanel();
        createPlayerPanel();
        createRackPanel();

        board = new Board();
        player = new Player();
        bag = new Bag();
        rackTileSelected = null;

        // create and insert cell buttons into board panel, then connect cell buttons to actual cells in matrix
        createCellButtons(board.cellMatrix);
        givePlayerStartingTiles();
        createButtonsForPlayerRack();
        updatePlayerRackGUI();
    }

    // Create window and panels - START

    private void createWindow() {
        frame = new JFrame();
        frame.setBounds(100, 100, 866, 802);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        // create windows for all panels
    }

    // Create Scrabble board panel for cell button matrix that will be connected to actual cell matrix
    private void createBoardPanel() {
        boardPanel = new JPanel();
        boardPanel.setBounds(10, 63, 831, 541);
        frame.getContentPane().add(boardPanel);
        boardPanel.setLayout(new GridLayout( 15, 15, 0, 0));
    }

    // Create player panel that shows hold rack panel, has play/pass/shuffle tiles/undo buttons, ect
    private void createPlayerPanel() {
        playerPanel = new JPanel();
        playerPanel.setBounds(10, 615, 831, 143);
        frame.getContentPane().add(playerPanel);
        playerPanel.setLayout(null);
        {
            logoPanel = new JPanel();
            logoPanel.setBounds(10, 11, 831, 47);
            frame.getContentPane().add(logoPanel);
            logoPanel.setLayout(new BorderLayout(0, 0));
            {
                logoLabel = new JLabel("S C R A B B L E");
                logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
                logoLabel.setFont(new Font("Calibre light", Font.BOLD, 22));
                logoLabel.add(logoPanel);
            }
        }
    }

    // Create rack panel for players tiles
    private void createRackPanel() {
        rackPanel = new JPanel();
        rackPanel.setBounds(165, 60, 500, 61);
        playerPanel.add(rackPanel);
        rackPanel.setLayout(new GridLayout(0, 7, 0, 0));

        JPanel actionPanel = new JPanel();
        actionPanel.setBounds(165, 12, 500, 37);
        playerPanel.add(actionPanel);
        actionPanel.setLayout(new GridLayout(1, 4, 0, 0));

        // button to shuffle tile pieces in players rack
        JButton shuffleButton = new JButton("Shuffle");
        actionPanel.add(shuffleButton);
        shuffleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.shuffleRack();
                player.organizeRack();
                updatePlayerRackGUI();
            }
        });

        // button to undo tile placement on board, cannot undo submitted moves
        JButton undoButton = new JButton("Undo");
        actionPanel.add(undoButton);
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // check if stack containing previous move (tile, tile index in player rack, rack button, cell and cell button
                // all exist to undo previous move as a whole).
                if (recentlyPlayedTileStack.size() > 0 && recentlyPlayedCellStack.size() > 0 && recentlyPlayedCellButtonStack.size() > 0 && rackTileSelected == null)
                {
                    Tile recentTilePlayed = recentlyPlayedTileStack.pop();

                    // return recently played tile rack to player's rack
                    player.addTileToRack(recentTilePlayed);

                    // set recent cell played to null
                    Cell recentCell = recentlyPlayedCellStack.pop();
                    recentCell.setTile(null);

                    //undo recent cell button played GUI
                    JButton recentCellButton = recentlyPlayedCellButtonStack.pop();
                    recentCellButton.setText(recentCell.getBonus());
                    recentCellButton.setBackground(Color.DARK_GRAY);

                    player.organizeRack();
                    updatePlayerRackGUI();
                }
            }
        });
        JButton submitButton = new JButton("Submit");
        actionPanel.add(submitButton);

        JButton passButton = new JButton("Pass");
        actionPanel.add(passButton);
    }
    // Create window and panels - END

    // Creating buttons - START
    // Creating, connecting, and labeling cell buttons
    private void createCellButtons(Cell[][] cellMatrix) {
        cellButtons = new JButton[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j< 15; j++) {
                // creating then adding cell buttons to board panel
                JButton cellButton = new JButton();
                cellButton.setBackground(Color.DARK_GRAY);
                Cell currentCell = cellMatrix[i][j];

                // connecting cells in cell matrix to their buttons
                cellButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (rackTileSelected != null && currentCell.getTile() == null) {
                            currentCell.setTile(rackTileSelected); // set celL matched with board button pressed to store current tile from rack chosen
                            cellButton.setText(rackTileSelected.getLetter()); // set cell button text by tile from rack played
                            cellButton.setBackground(Color.GREEN);
                            rackTileSelected = null;
                            recentlyPlayedCellStack.push(currentCell); // push cell played into stack for undoing purposes
                            recentlyPlayedCellButtonStack.push(cellButton);
                        }
                    }
                });
                cellButtons[i][j] = cellButton;
                boardPanel.add(cellButton);

                // labeling cell buttons with bonus label (Triple/Double word/letter on specific cell buttons)
                if (cellMatrix[i][j].getBonus() != null) {
                    cellButtons[i][j].setText(cellMatrix[i][j].getBonus());
                }
            }
        }
    }

    // Create button for each of 7 tiles for player's rack
    private void createButtonsForPlayerRack() {
        rackButtons = new JButton[7];

        // rack button 1
        JButton rackButton1 = new JButton();
        rackButton1.setBackground(Color.GREEN);
        rackButton1.setText("(" + player.getRack()[0].getPoints() + ")" + player.getRack()[0].getLetter()); // set tile's letter and points to show on button

        rackButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // check if player has no tiles from rack selected before selecting another tile from the rack. Also update rack tile button GUI
                if (rackTileSelected == null && rackButton1.getText() != "") {
                    rackTileSelected = player.getAndRemoveFromRackAt(0); // to transfer tile from player's rack onto board
                    recentlyPlayedTileStack.push(rackTileSelected); // for undo purposes

                    rackButton1.setBackground(Color.DARK_GRAY);
                    rackButton1.setText("");
                }
            }
        });
        rackButtons[0] = rackButton1;
        rackPanel.add(rackButton1);

        // rack button 2
        JButton rackButton2 = new JButton();
        rackButton2.setBackground(Color.GREEN);
        rackButton2.setText("(" + player.getRack()[1].getPoints() + ")" + player.getRack()[1].getLetter());

        rackButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rackTileSelected == null && rackButton2.getText() != "") {
                    rackTileSelected = player.getAndRemoveFromRackAt(1);
                    recentlyPlayedTileStack.push(rackTileSelected);

                    rackButton2.setBackground(Color.DARK_GRAY);
                    rackButton2.setText("");
                }
            }
        });
        rackButtons[1] = rackButton2;
        rackPanel.add(rackButton2);

        // rack button 3
        JButton rackButton3 = new JButton();
        rackButton3.setBackground(Color.GREEN);
        rackButton3.setText("(" + player.getRack()[2].getPoints() + ")" + player.getRack()[2].getLetter());

        rackButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rackTileSelected == null && rackButton3.getText() != "") {
                    rackTileSelected = player.getAndRemoveFromRackAt(2);
                    recentlyPlayedTileStack.push(rackTileSelected);

                    rackButton3.setBackground(Color.DARK_GRAY);
                    rackButton3.setText("");
                }
            }
        });
        rackButtons[2] = rackButton3;
        rackPanel.add(rackButton3);

        // rack button 4
        JButton rackButton4 = new JButton();
        rackButton4.setBackground(Color.GREEN);
        rackButton4.setText("(" + player.getRack()[3].getPoints() + ")" + player.getRack()[3].getLetter());

        rackButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rackTileSelected == null && rackButton4.getText() != "") {
                    rackTileSelected = player.getAndRemoveFromRackAt(3);
                    recentlyPlayedTileStack.push(rackTileSelected);

                    rackButton4.setBackground(Color.DARK_GRAY);
                    rackButton4.setText("");
                }
            }
        });
        rackButtons[3] = rackButton4;
        rackPanel.add(rackButton4);

        // rack button 5
        JButton rackButton5 = new JButton();
        rackButton5.setBackground(Color.GREEN);
        rackButton5.setText("(" + player.getRack()[4].getPoints() + ")" + player.getRack()[4].getLetter());

        rackButton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rackTileSelected == null && rackButton5.getText() != "") {
                    rackTileSelected = player.getAndRemoveFromRackAt(4);
                    recentlyPlayedTileStack.push(rackTileSelected);

                    rackButton5.setBackground(Color.DARK_GRAY);
                    rackButton5.setText("");
                }
            }
        });
        rackButtons[4] = rackButton5;
        rackPanel.add(rackButton5);

        // rack button 6
        JButton rackButton6 = new JButton();
        rackButton6.setBackground(Color.GREEN);
        rackButton6.setText("(" + player.getRack()[5].getPoints() + ")" + player.getRack()[5].getLetter());

        rackButton6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rackTileSelected == null && rackButton6.getText() != "") {
                    rackTileSelected = player.getAndRemoveFromRackAt(5);
                    recentlyPlayedTileStack.push(rackTileSelected);

                    rackButton6.setBackground(Color.DARK_GRAY);
                    rackButton6.setText("");
                }
            }
        });
        rackButtons[5] = rackButton6;
        rackPanel.add(rackButton6);

        // rack button 7
        JButton rackButton7 = new JButton();
        rackButton7.setBackground(Color.GREEN);
        rackButton7.setText("(" + player.getRack()[6].getPoints() + ")" + player.getRack()[6].getLetter());

        rackButton7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rackTileSelected == null && rackButton7.getText() != "") {
                    rackTileSelected = player.getAndRemoveFromRackAt(6);
                    recentlyPlayedTileStack.push(rackTileSelected);

                    rackButton7.setBackground(Color.DARK_GRAY);
                    rackButton7.setText("");
                }
            }
        });
        rackButtons[6] = rackButton7;
        rackPanel.add(rackButton7);
    }

    // Creating buttons - END

    // Updating buttons/Data structure - START
    // Add a tile from bag to player's rack if possible
    private void givePlayerANewTile() {
        // cannot add tiles to player's rack if bag is empty or rack is full (rack has more than 7 elements)
        if (bag.bagIsEmpty() || (player.getRackSize() > 7)) {
            return;
        }

        Tile newTile = bag.getNextTile();
        player.addTileToRack(newTile);
    }

    // add 7 tiles from bag to player's rack at start of the game
    private void givePlayerStartingTiles() {
        for (int i = 0; i < 7; i++) {
            givePlayerANewTile();
        }
    }

    // places tile pieces found directly in player's rack onto rack button GUI
    private void updatePlayerRackGUI() {
        for (int i = 0; i < 7; i++) {
            if (player.getRack()[i] != null) {
                rackButtons[i].setText("(" + player.getRack()[i].getPoints() + ")" + player.getRack()[i].getLetter());
                rackButtons[i].setBackground(Color.GREEN);
            } else if (player.getRack()[i] == null) {
                rackButtons[i].setText("");
                rackButtons[i].setBackground(Color.DARK_GRAY);
            }
        }
    }
}
