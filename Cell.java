public class Cell {
    private Tile tile;
    private Cell top, bottom, left, right;
    private String bonus;

    public Cell() {
        tile = null;
        top = bottom = left = right = null;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public Tile getTile() {
        return this.tile;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
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
        this.bottom = cell;
    }

    public Cell getBottom() {
        return this.bottom;
    }

    public void setLeft(Cell cell){
        this.left = cell;
    }

    public Cell getLeft() {
        return this.left;
    }

    public void setRight(Cell cell){
        this.right = cell;
    }

    public Cell getRight() {
        return this.right;
    }
}
