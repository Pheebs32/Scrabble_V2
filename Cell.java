public class Cell {
    private Tile tile;
    private Cell top, bottom, left, right;
    private String bonus;

    public Cell() {
        tile = null;
        top = bottom = left = right = null;
    }

    public void setTile(Tile tile) {

    }

    public Tile getTile() {
        return this.tile;
    }

    public void setBonus() {

    }

    public String getBonus() {
        return this.bonus;
    }

    public void setTop(Cell cell) {
        this.top = cell;
    }

    public Cell getTop() {
        return this.top;
    }

    public void setBottom(Cell cell){
        this.top = cell;
    }

    public Cell getBottom() {
        return this.bottom;
    }

    public void setLeft(Cell cell){
        this.top = cell;
    }

    public Cell getLeft() {
        return this.left;
    }

    public void setRight(Cell cell){
        this.top = cell;
    }

    public Cell getRight() {
        return this.right;
    }
}
