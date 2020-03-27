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

    @Test
    public void getGroups(){
        String room = "FG B080";
        AStar astar = new AStar();
        astar.setDestFromString(room);
    }

}