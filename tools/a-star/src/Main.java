import astar.Spot;
import drawing.DrawingBoard;
import drawing.MyDrawable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static astar.A_Star.A_Star;
import static astar.A_Star.linkNeighbors;
import static drawing.DrawingBoard.createGui;
import static pictureMapper.GetPixelColor.getBoolArr;
import static pictureMapper.GetPixelColor.getRGBarray;


public class Main {

    static final int w = 2;
    static final int h = 2;

    public static DrawingBoard gui;


    public static void main(String[] a) {

        // (x1,y1) define the start
        int x1 = 60;
        int y1 = 60;

        // (x2,y2) define the end
        int x2 = 242;
        int y2 = 42;
        try {
            Spot[][] grid = createGridFromMap("media/h9275.png");
            drawMap(grid);
            linkNeighbors(grid);
            Spot path = A_Star(grid, x1, y1, x2, y2);
            drawPath(path);
        } catch (IOException e) {
            System.out.println("Image does not exist");
        }

    }

    public static Spot[][] createGridFromMap(String path) throws IOException {


        BufferedImage image = ImageIO.read(new File(path));

        int[][] result = getRGBarray(image);
        boolean[][] bool = getBoolArr(result, -534826);

        Spot[][] grid = new Spot[bool.length][bool[bool.length - 1].length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new Spot(i, j, bool[i][j]);
            }
        }

        return grid;

    }

    public static void drawMap(Spot[][] grid) {
        gui = createGui(grid.length, grid[0].length);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                MyDrawable gridSquare = new MyDrawable(new Rectangle(i * w, j * h, w, h), Color.BLACK, new BasicStroke(1), false);
                gui.addMyDrawable(gridSquare);

                if (grid[i][j].wall) {
                    MyDrawable blackSquare = new MyDrawable(new Rectangle(i * w, j * h, w, h), Color.BLACK, new BasicStroke(1), true);
                    gui.addMyDrawable(blackSquare);
                }
            }
        }


    }

    public static void drawPath(Spot curr) {
        while (curr != null) {
            MyDrawable blue = new MyDrawable(new Rectangle(curr.getX() * w, curr.getY() * h, w, h), new Color(0f, 0f, 1f, 1f), new BasicStroke(0), true);
            gui.addMyDrawable(blue);
            curr = curr.getPrevious();
        }

    }


}

