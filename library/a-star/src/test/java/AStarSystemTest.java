import astar.AStar;
import astar.Edges;
import astar.Spot;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;

import static astar.AStar.*;
import static org.junit.Assert.assertNotNull;

public class AStarSystemTest {

    @Test
    public void SystemTest() throws IOException, ParseException {

        AStar aStar = new AStar();

        String start = "H-903";
        String end = "H-945";

        String floor = "9";
        String path = "metadata/9th-floor-hall";

        // converts existing metadata to JSON object

        String pathToJSON = path + ".json";
        Edges[] startEnd = aStar.getDictFromJSON(start, end, floor, pathToJSON);

        System.out.println(startEnd[0]);

        int x1 = (startEnd[0].getRight() - startEnd[0].getLeft()) / 2 + startEnd[0].getLeft();
        int y1 = (startEnd[0].getTop() - startEnd[0].getBottom()) / 2 + startEnd[0].getBottom();
        int x2 = (startEnd[1].getRight() - startEnd[1].getLeft()) / 2 + startEnd[1].getLeft();
        int y2 = (startEnd[1].getTop() - startEnd[1].getBottom()) / 2 + startEnd[1].getBottom();
        System.out.println(x1);
        System.out.println(y1);
        System.out.println(x2);
        System.out.println(y2);


        boolean[][] binGrid = aStar.createBinaryGrid("media/h9275.png");
        Spot[][] grid = aStar.createSpotGrid(binGrid, startEnd);
        aStar.linkNeighbors(grid);
        Spot walk = aStar.runAlgorithm(grid, x1, y1, x2, y2);

        assertNotNull(walk);

        System.out.println("Done!");

    }


}
