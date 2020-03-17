import metadata.MetaData;
import astar.Spot;
import drawing.DrawingBoard;
import drawing.MyDrawable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

import static astar.AStar.*;
import static drawing.DrawingBoard.createGui;
import static java.awt.image.ImageObserver.ABORT;
import static pictureMapper.GetPixelColor.getBoolArr;
import static pictureMapper.GetPixelColor.getRGBarray;


public class Main {

    static final int w = 3;
    static final int h = 3;

    public static DrawingBoard gui;


    public static void main(String[] a) throws IOException, ParseException {

        String start = "H-903";
        String end = "H-945";

        String floor = "9";
        String path = "metadata/9th-floor-hall";

        // converts existing metadata to JSON object


        String pathToJSON = path + ".json";
        Map[] startEnd = getDictFromJSON(start,end,floor,pathToJSON);

        System.out.println(startEnd[0]);


        int x1 = Math.toIntExact(((long) startEnd[0].get("maxX") - (long) startEnd[0].get("minX"))/2 + (long) startEnd[0].get("minX"));
        int y1 = Math.toIntExact(((long) startEnd[0].get("maxY") - (long) startEnd[0].get("minY"))/2 + (long) startEnd[0].get("minY"));
        int x2 = Math.toIntExact(((long) startEnd[1].get("maxX") - (long) startEnd[1].get("minX"))/2 + (long) startEnd[1].get("minX"));
        int y2 = Math.toIntExact(((long) startEnd[1].get("maxY") - (long) startEnd[1].get("minY"))/2 + (long) startEnd[1].get("minY"));
        System.out.println(x1);
        System.out.println(y1);
        System.out.println(x2);
        System.out.println(y2);


        boolean[][] binGrid = createBinaryGrid("media/h9275.png");
        Spot[][] grid = createSpotGrid(binGrid, startEnd);
        drawMap(grid);
        linkNeighbors(grid);
        Spot walk = algo(grid, x1, y1, x2, y2);
        drawPath(walk);
        System.out.println("Done!");

    }

    public static void drawMap(Spot[][] grid) {
        gui = createGui(grid.length, grid[0].length);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                MyDrawable gridSquare = new MyDrawable(new Rectangle(j * w, i * h, w, h), Color.BLACK, new BasicStroke(1), false);
                gui.addMyDrawable(gridSquare);

                if (grid[i][j].wall) {
                    MyDrawable blackSquare = new MyDrawable(new Rectangle(j * w, i * h, w, h), Color.BLACK, new BasicStroke(1), true);
                    gui.addMyDrawable(blackSquare);
                }
            }
        }
    }

    public static void drawPath(Spot curr) {
        while (curr != null) {
            MyDrawable blue = new MyDrawable(new Rectangle(curr.getY() * w, curr.getX() * h, w, h), new Color(0f, 0f, 1f, 1f), new BasicStroke(0), true);
            gui.addMyDrawable(blue);
            curr = curr.getPrevious();
        }

    }


}

