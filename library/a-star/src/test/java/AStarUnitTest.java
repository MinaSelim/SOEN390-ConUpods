import astar.*;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class AStarUnitTest {


    @Test
    public void pathDoesntExist() {
        int k = 4;
        Map[] startEnd = new Map[2];
        for(int i = 0; i<2; i++) {
            Map<String, Long> coord = new HashMap<>();
            long point;
            if(i==0) {
                point = 0;
            }else{
                point = k-1;
            }
            coord.put("minX", (long) point);
            coord.put("maxY", (long) point);
            coord.put("maxX", (long) point);
            coord.put("minY", (long) point);
            startEnd[i] = coord;
        }

        boolean[][] grid = new boolean[k][k];
        for(int i =0; i<k; i++){
            for(int j = 0; j<k; j++){
                grid[i][j] = true;
            }
        }

        Spot[][] spotGrid = AStar.createSpotGrid(grid, startEnd);

        assertNull(AStar.algo(spotGrid, 0,0,k-1,k-1));

    }

    @Test
    public void spotIsWall(){
        Spot spot = new Spot(0,0, true);
        assertTrue(spot.wall);
    }

    @Test
    public void spotIsNotWall(){
        Spot spot = new Spot(0,0, false);
        assertFalse(spot.wall);
    }

    @Test
    public void spotHasNeighbor(){
        Spot spot = new Spot(0,0, false);
        Spot spotNeighbor = new Spot(1,1, false);
        spot.addNeighbor(spotNeighbor);
        assertNotNull(spot.getNeighbors());
    }

    @Test
    public void spotNeighborIsSame(){
        Spot spot = new Spot(0,0, false);
        Spot spotNeighbor = new Spot(0,0, false);
        spot.addNeighbor(spotNeighbor);
        assertTrue((spot.getNeighbors()).isEmpty());
    }

}