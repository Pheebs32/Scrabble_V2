import java.util.*;

public class Player {
    private Tile[] rack; // tiles that can be played from the rack
    private Tile[] pendingRack; // to keep up with the tiles that they have placed down on the board and have not played.
    private int score;

    public Player() {
        rack = new Tile[7];
        pendingRack = new Tile[7];
        score = 0;
    }

    public void shuffleRack() {
        List<Tile> list = Arrays.asList(rack);
        Collections.shuffle(list);
        list.toArray(rack);
    }

    // pushes tiles in rack to left side, leaving no spaces in between tiles.
    public void organizeRack() {

    }

    public Tile getFromRackAt(int index) {
        return i;
    }

    public Tile getAndRemoveFromRackAt(int index) {
        return i;
    }

    // adds tile to next free index in rack array the returns which index, -1 if no free index to insert tile
    public Tile addTileToRack(int index) {
        return i;
    }

    public void addTileToPending(Tile tile) {

    }

    public boolean rackIsFull() {
        return false;
    }

    public int getRackSize() {
        return 1;
    }

    public int getPendingRackSize() {
        return 1;
    }

    public Tile[] getRack() {
        return this.rack;
    }
}
