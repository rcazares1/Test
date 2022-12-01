import java.awt.Color;

/**
 * Sand lab adapted from http://nifty.stanford.edu/2017/feinberg-falling-sand/
 *
 * Student name: YOUR NAME GOES HERE
 *
 * TODO: Document expected behavior of various materials here
 */
public class SandLab {

    /**
     * Enum for material types of the particles
     */
    public enum Material {
        EMPTY,
        METAL,
        SAND,
        GAS,
        WATER,
        WETSAND
    }

    public enum Move {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    /** grid of particles of various materials*/
    private Material[][] grid;

    /** The display window */
    private SandDisplay display;

    /**
     * Create a new SandLab of given size.
     * @param numRows number of rows
     * @param numCols number of columns
     */
    public SandLab(int numRows, int numCols) {
        // TODO: Include names for all Materials used in simulation
        //       (Can you do it without manually listing them all?)
        String[] names = new String[]{"Empty", "Metal", "Sand", "Gas", "Water", "WetSand"};
        display = new SandDisplay("Falling Sand", numRows, numCols, names);
        grid = new Material[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                grid[i][j] = Material.EMPTY;
            }
        }
    }

    /**
     * called when the user clicks on a location using the given tool
     * @param row Row of location
     * @param col Column of location
     * @param tool Name of selected tool
     */
    public void locationClicked(int row, int col, String tool) {
        if (tool.equals("METAL")) {
            grid[row][col] = Material.METAL;
        }
        if (tool.equals("EMPTY")) {
            grid[row][col] = Material.EMPTY;
        }
        if (tool.equals("SAND")) {
            grid[row][col] = Material.SAND;
        }
        if (tool.equals("GAS")) {
            grid[row][col] = Material.GAS;
        }
        if (tool.equals("WATER")) {
            grid[row][col] = Material.WATER;
        }
        if (tool.equals("WETSAND")) {
            grid[row][col] = Material.WETSAND;
        }
    }

    /**
     * copies each element of grid into the display
     */
    public void updateDisplay() {
        int row = grid.length;
        int col = grid[0].length;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j].equals(Material.EMPTY)) {
                    display.setColor(i, j, Color.black);
                }
                if (grid[i][j].equals(Material.METAL)) {
                    display.setColor(i, j, new Color(165,184,193));
                }
                if (grid[i][j].equals(Material.SAND)) {
                    display.setColor(i, j, new Color(255,251,100));
                }
                if (grid[i][j].equals(Material.GAS)) {
                    display.setColor(i, j, new Color(100,246,255));
                }
                if (grid[i][j].equals(Material.WATER)) {
                    display.setColor(i, j, new Color(8,67,169));
                }
                if (grid[i][j].equals(Material.WETSAND)) {
                    display.setColor(i, j, new Color(198,190,81));
                }
            }
        }
    }

    /**
     * Update the simulation by one step.
     * Called repeatedly.
     * Causes one random particle to maybe do something
     */
    public void step() {
        int rani = (int) (Math.random() * (grid.length) );
        int ranj = (int) (Math.random() * grid[rani].length);
        int ran3 = (int) (Math.random() * 3);

        switch (grid[rani][ranj]) {
            case WETSAND:
                pxlMove(Move.DOWN, rani, ranj, Material.WETSAND, Material.EMPTY);
                break;
            case WATER:
                switch(ran3) {
                    case 0:
                        pxlMove(Move.DOWN, rani, ranj, Material.WATER, Material.EMPTY);
                        break;
                    case 1:
                        pxlMove(Move.RIGHT, rani, ranj, Material.WATER, Material.EMPTY);
                        break;
                    case 2:
                        pxlMove(Move.LEFT, rani, ranj, Material.WATER, Material.EMPTY);
                        break;
                    default:
                        break;
                }
                break;
            case GAS:
                switch(ran3) {
                    case 0:
                        pxlMove(Move.UP, rani, ranj, Material.GAS, Material.EMPTY);
                        break;
                    case 1:
                        pxlMove(Move.RIGHT, rani, ranj, Material.GAS, Material.EMPTY);
                        break;
                    case 2:
                        pxlMove(Move.LEFT, rani, ranj, Material.GAS, Material.EMPTY);
                        break;
                    default:
                        break;
                }
                break;
            case SAND:
                if (grid[rani - 1][ranj].equals(Material.EMPTY)) {
                    pxlMove(Move.DOWN, rani, ranj, Material.SAND, Material.EMPTY);
                }
                else {
                    switch(ran3) {
                        case 0:
                            pxlMove(Move.DOWN, rani, ranj, Material.SAND, Material.EMPTY);
                            break;
                        case 1:
                            pxlMove(Move.RIGHT, rani, ranj, Material.SAND, Material.EMPTY);
                            break;
                        case 2:
                            pxlMove(Move.LEFT, rani, ranj, Material.SAND, Material.EMPTY);
                            break;
                        default:
                            break;
                    }
                }
                break;
            default:
                break;
        }
    }

    private void pxlMove (Move movement, int i, int j, Material m1, Material m2) {
        switch (movement) {
            case DOWN:
                if ((i + 1) < grid.length) {
                    if (grid[i + 1][j] == m2) {
                        grid[i + 1][j] = m1;
                        grid[i][j] = m2;
                    }
                }
                break;
            case UP:
                if (i - 1 >= 0) {
                    if (grid[i - 1][j] == m2) {
                        grid[i - 1][j] = m1;
                        grid[i][j] = m2;
                    }
                }
                break;
            case RIGHT:
                if (j + 1 < grid[i].length) {
                    if (grid[i][j + 1] == m2) {
                        grid[i][j + 1] = m1;
                        grid[i][j] = m2;
                    }
                }
                break;
            case LEFT:
            if (j - 1 < grid[i].length && j > 0) {
                if (grid[i][j - 1] == m2) {
                    grid[i][j - 1] = m1;
                    grid[i][j] = m2;
                }
            }
            default:
                break;
        }
    }
    /**
     * Run the SandLab particle simulation.
     *
     * DO NOT MODIFY THIS METHOD!
     */
    public void run() {
        // keep updating as long as the program is running
        while (true) {
            // update some number of particles, as determined by the speed slider
            for (int i = 0; i < display.getSpeed(); i++) {
                step();
            }
            // Update the display object's colors
            updateDisplay();
            // wait for redrawing and for mouse events
            display.repaintAndPause(1);

            int[] mouseLoc = display.getMouseLocation();
            //test if mouse clicked
            if (mouseLoc != null) {
                locationClicked(mouseLoc[0], mouseLoc[1], display.getTool().toUpperCase());
            }
        }
    }

    /** Creates a new SandLab and sets it running */
    public static void main(String[] args) {
        SandLab lab = new SandLab(120, 80);
        lab.run();
    }
}
//Extra note