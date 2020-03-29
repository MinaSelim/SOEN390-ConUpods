import com.conupods.OutdoorMaps.Models.Building.Building;
import com.conupods.OutdoorMaps.Models.Building.Campus;
import com.conupods.OutdoorMaps.Models.Building.Classroom;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AbstractCampusLocationsTest {
    Campus mCampus = null;
    Building mBuilding = null;
    Classroom mClassroom = null;
    LatLng mExpectedBuildingCoordinates;
    String[] mPreBuiltClassrooms;
    List<String> mExprectedClassrooms = new ArrayList<>();

    @Before
    public void setUp() {
        mExpectedBuildingCoordinates = new LatLng(45.495304, -73.579044);
        mCampus = new Campus("SGW", mExpectedBuildingCoordinates);
        mBuilding = new Building(
                mExprectedClassrooms,
                mExpectedBuildingCoordinates,
                "MB Building",
                mCampus,
                "John Molson Building",
                "1450 Guy Street",
                "MB",
                mExpectedBuildingCoordinates );

        mClassroom = new Classroom("MS1 340",new LatLng(45.495304, -73.579044), mBuilding);


        mPreBuiltClassrooms = new String[]{"MS1 340", "MS1 350", "MS1 130", "MS1 265", "MS1 210",
                "MS1 430", "MS1 275", "MS1 309", "MS1 410", "MS1 134", "MS1 335", "MS1 115", "MS1 345",
                "MS1 301", "MS1 315", "MS1 424", "MS1 426", "MS1 437","MSS2 285", "MSS2 105", "MSS2 148",
                "MSS2 401", "MSS2 445", "MSS2 245", "MSS2 146", "MSS2 465", "MSS2 145", "MSS2 420",
                "MSS2 440", "MSS2 418", "MSS2 437", "MSS2 230", "MSS2 273", "MSS2 315", "MSS2 435",
                "MSS2 115", "MSS2 455", "MSS2 410", "MSS2 277", "MSS2 255", "MSS2 210", "MSS2 275",
                "MSS2 330"};

        mExprectedClassrooms = Arrays.asList(mPreBuiltClassrooms);
    }

    @Test
    public void locationsCoordinatesTest() {
        assertEquals("SGW", mCampus.getIdentifier());
        assertEquals(mExpectedBuildingCoordinates, mCampus.getCoordinates());
        assertEquals(mExpectedBuildingCoordinates, mBuilding.getCoordinates());
        assertEquals(mExpectedBuildingCoordinates, mClassroom.getCoordinates());

    }

    @Test
    public void locationsIdentifierTest() {
        assertEquals("SGW", mCampus.getIdentifier());
        assertEquals("MS1 340", mClassroom.getIdentifier());
        assertEquals("MB Building", mBuilding.getIdentifier());
        assertEquals("1450 Guy Street", mBuilding.getAddress());
        assertEquals("MB", mBuilding.getCode());
        assertEquals("John Molson Building", mBuilding.getLongName());
    }

    @Test
    public void locationsExpectedClassroomsTest() {
        int i = 0;
        for(String classroom : mBuilding.getClassRooms()) {
            assertEquals(classroom, mExprectedClassrooms.get(i));
            i++;
        }
    }

}
