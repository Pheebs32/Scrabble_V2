public class Board {
    Cell[][] cellMatrix;

    public Board() {
        createBoard();
        connectCells();
        createBonus();
    }

    //creates board with 15x15 array of cells
    public void createBoard() {
        cellMatrix = new Cell[15][15];

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                cellMatrix[i][j] = new Cell();
            }
        }
    }

    public void connectCells() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                connectTop(i, j, cellMatrix);
                connectBottom(i, j, cellMatrix);
                connectLeft(i, j, cellMatrix);
                connectRight(i, j, cellMatrix);
            }
        }
    }

    public void createBonus() {
        // starting center
        cellMatrix[7][7].setBonus("X");
        // triple word

        // triple letter

        // double word

        // double letter
    }

    public void connectTop(int row, int col, Cell[][] cellMatrix) {
        if ((row - 1) < 0) {
            cellMatrix[row][col].setTop(null);
            return;
        }
        int newRow = row - 1;
        cellMatrix[row][col].setTop(cellMatrix[newRow][col]);
    }

    public void connectBottom(int row, int col, Cell[][] cellMatrix) {
        if ((row + 1) > 14) {
            cellMatrix[row][col].setBottom(null);
            return;
        }
        int newRow = row + 1;
        cellMatrix[row][col].setBottom(cellMatrix[newRow][col]);
    }

    public void connectLeft(int row, int col, Cell[][] cellMatrix) {
        if ((col - 1) < 0) {
            cellMatrix[row][col].setLeft(null);
            return;
        }
        int newCol = col - 1;
        cellMatrix[row][col].setLeft(cellMatrix[row][newCol]);
    }

    public void connectRight(int row, int col, Cell[][] cellMatrix) {
        if ((col + 1) > 14) {
            cellMatrix[row][col].setRight(null);
            return;
        }
        int newCol = col + 1;
        cellMatrix[row][col].setRight(cellMatrix[row][newCol]);
    }

}
