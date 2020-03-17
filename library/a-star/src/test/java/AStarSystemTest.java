import astar.Spot;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static astar.AStar.*;
import static org.junit.Assert.assertNotNull;

public class AStarSystemTest {

    @Test
    public void SystemTest() throws IOException, ParseException {

        String start = "H-903";
        String end = "H-945";

        String floor = "9";
        String path = "metadata/9th-floor-hall";

        // converts existing metadata to JSON object


        String pathToJSON = path + ".json";
        Map[] startEnd = getDictFromJSON(start, end, floor, pathToJSON);

        System.out.println(startEnd[0]);


        int x1 = Math.toIntExact(((long) startEnd[0].get("maxX") - (long) startEnd[0].get("minX")) / 2 + (long) startEnd[0].get("minX"));
        int y1 = Math.toIntExact(((long) startEnd[0].get("maxY") - (long) startEnd[0].get("minY")) / 2 + (long) startEnd[0].get("minY"));
        int x2 = Math.toIntExact(((long) startEnd[1].get("maxX") - (long) startEnd[1].get("minX")) / 2 + (long) startEnd[1].get("minX"));
        int y2 = Math.toIntExact(((long) startEnd[1].get("maxY") - (long) startEnd[1].get("minY")) / 2 + (long) startEnd[1].get("minY"));
        System.out.println(x1);
        System.out.println(y1);
        System.out.println(x2);
        System.out.println(y2);


        boolean[][] binGrid = createBinaryGrid("media/h9275.png");
        Spot[][] grid = createSpotGrid(binGrid, startEnd);
        linkNeighbors(grid);
        Spot walk = algo(grid, x1, y1, x2, y2);

        assertNotNull(walk);

        System.out.println("Done!");

    }


}
