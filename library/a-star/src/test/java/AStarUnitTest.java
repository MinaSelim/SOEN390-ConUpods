import astar.AStar;
import astar.Edges;
import astar.Spot;
import org.junit.Test;

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
        Edges[] startEnd = new Edges[2];
        for (int i = 0; i < 2; i++) {
            int point;
            if (i == 0) {
                point = 0;
            } else {
                point = k - 1;
            }
            Edges coord = new Edges(point, point, point, point, 1);
            startEnd[i] = coord;
        }

        boolean[][] grid = new boolean[k][k];
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                grid[i][j] = true;
            }
        }

        Spot[][] spotGrid = AStar.createSpotGrid(grid, startEnd);

        assertNull(AStar.algo(spotGrid, 0, 0, k - 1, k - 1));

    }

    @Test
    public void spotIsWall() {
        Spot spot = new Spot(0, 0, true);
        assertTrue(spot.isWall());
    }

    @Test
    public void spotIsNotWall() {
        Spot spot = new Spot(0, 0, false);
        assertFalse(spot.isWall());
    }

    @Test
    public void spotHasNeighbor() {
        Spot spot = new Spot(0, 0, false);
        Spot spotNeighbor = new Spot(1, 1, false);
        spot.addNeighbor(spotNeighbor);
        assertNotNull(spot.getNeighbors());
    }

    @Test
    public void spotNeighborIsSame() {
        Spot spot = new Spot(0, 0, false);
        Spot spotNeighbor = new Spot(0, 0, false);
        spot.addNeighbor(spotNeighbor);
        assertTrue((spot.getNeighbors()).isEmpty());
    }

}