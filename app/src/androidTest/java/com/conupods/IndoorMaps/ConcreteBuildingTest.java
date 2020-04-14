package com.conupods.IndoorMaps;

import com.conupods.IndoorMaps.ConcreteBuildings.CCBuilding;
import com.conupods.IndoorMaps.ConcreteBuildings.VLBuilding;
import com.conupods.IndoorMaps.ConcreteBuildings.MBBuilding;
import com.conupods.IndoorMaps.ConcreteBuildings.HBuilding;
import com.conupods.OutdoorMaps.Models.Building.Building;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class ConcreteBuildingTest {
    @Test
    public void CCBuilding() {
        Building CC = CCBuilding.getInstance();
        LatLng coordinateCC = new LatLng(45.458204,-73.6403);
        LatLng coordinateOverlayCC = new LatLng(45.45863873466155,-73.64075660705566);


        assertEquals("7141 Sherbrooke West",CC.getAddress());
        assertEquals("CC",CC.getCode());
        assertEquals("LOY",CC.getConcreteParent());
        assertEquals("Central Building",CC.getLongName());
        assertEquals("CC Building",CC.getName());
        assertEquals(new ArrayList<>(),CC.getClassRooms());
        assertEquals(coordinateCC,CC.getLatLng());
        assertEquals(coordinateOverlayCC,CC.getOverlayLatLng());
    }

    @Test
    public void VLBuilding() {
        Building VL = VLBuilding.getInstance();
        LatLng coordinateVL = new LatLng(45.459026, -73.638606);
        LatLng coordinateOverlayVL = new LatLng(45.45948158019394, -73.63869398832321);

        assertEquals("7141 Sherbrooke W",VL.getAddress());
        assertEquals("VL",VL.getCode());
        assertEquals("LOY",VL.getConcreteParent());
        assertEquals("Vanier Library Building",VL.getLongName());
        assertEquals("VL Building",VL.getName());
        assertEquals(new ArrayList<>(),VL.getClassRooms());
        assertEquals(coordinateVL,VL.getLatLng());
        assertEquals(coordinateOverlayVL,VL.getOverlayLatLng());
    }

    @Test
    public void MBBuilding() {
        Building MB = MBBuilding.getInstance();
        LatLng coordinateMB = new LatLng(45.495304, -73.579044);
        LatLng coordinateOverlayMB = new LatLng( 45.49534539338842, -73.57823699712753);

        assertEquals("1450 Guy Street",MB.getAddress());
        assertEquals("MB",MB.getCode());
        assertEquals("SGW",MB.getConcreteParent());
        assertEquals("John Molson Building",MB.getLongName());
        assertEquals("MB Building",MB.getName());
   //     assertEquals(new ArrayList<String>(Arrays.asList("MS1 340", "MS1 350", "MS1 130", "MS1 265", "MS1 210", "MS1 430", "MS1 275", "MS1 309", "MS1 410", "MS1 134", "MS1 335", "MS1 115", "MS1 345", "MS1 301", "MS1 315", "MS1 424", "MS1 426", "MS1 437","MSS2 285", "MSS2 105", "MSS2 148", "MSS2 401", "MSS2 445", "MSS2 245", "MSS2 146", "MSS2 465", "MSS2 145", "MSS2 420", "MSS2 440", "MSS2 418", "MSS2 437", "MSS2 230", "MSS2 273", "MSS2 315", "MSS2 435", "MSS2 115", "MSS2 455", "MSS2 410", "MSS2 277", "MSS2 255", "MSS2 210", "MSS2 275", "MSS2 330")),MB.getClassRooms());
        assertEquals(coordinateMB,MB.getLatLng());
        assertEquals(coordinateOverlayMB,MB.getOverlayLatLng());
    }

    @Test
    public void HallBuilding() {
        Building H = HBuilding.getInstance();
        LatLng coordinateH = new LatLng(45.497092,-73.5788);
        LatLng coordinateOverlayH = new LatLng(45.497178500594934, -73.57966929674149);

        assertEquals("1455 DeMaisonneuve W",H.getAddress());
        assertEquals("H",H.getCode());
        assertEquals("SGW",H.getConcreteParent());
        assertEquals("Henry F. Hall Building",H.getLongName());
        assertEquals("H Building",H.getName());
        assertEquals(new ArrayList<String>(Arrays.asList("H 110", "H 120", "H 125", "H 114", "H 124", "H 112", "H 122", "H 190", "H 150", "H 0052", "H 118", "H 127", "H 126", "H 109", "H 231", "H 252", "H 260", "H 239", "H 802 01", "H 871", "H 860 03", "H 860 01", "H 806 02", "H 860 06", "H 860 05", "H 806 01", "H 860 04","H 838 01", "H 838", "H 833", "H 831", "H 837", "H 851 02", "H 835", "H 851 03", "H 805 03", "H 840", "H 881", "H 805 02", "H 805 01", "H 807", "H 849", "H 843", "H 842", "H 841", "H 804", "H 803", "H 847", "H 802", "H 801", "H 845", "H 819", "H 817", "H 811", "H 855", "H 810", "H 854", "H 853", "H 852", "H 815", "H 859", "H 832 03", "H 813", "H 857", "H 832 01", "H 856", "H 832 02", "H 851 01", "H 832 06", "H 862", "H 861", "H 829", "H 827", "H 822", "H 865", "H 821", "H 820", "H 863", "H 825", "H 867", "H 823", "H 802 01", "H 871", "H 860 03", "H 860 01", "H 806 02", "H 860 06", "H 860 05", "H 806 01", "H 860 04", "H 838 01", "H 838", "H 833", "H 831", "H 837", "H 851 02", "H 835", "H 851 03", "H 805 03", "H 840", "H 881", "H 805 02", "H 805 01", "H 807", "H 849", "H 843", "H 842", "H 841", "H 804", "H 803", "H 847", "H 802", "H 801", "H 845", "H 819", "H 817", "H 811", "H 855", "H 810", "H 854", "H 853", "H 852", "H 815", "H 859", "H 832 03", "H 813", "H 857", "H 832 01", "H 856", "H 832 02", "H 851 01", "H 832 06", "H 862", "H 861", "H 829", "H 827", "H 822", "H 865", "H 821", "H 820", "H 863", "H 825", "H 867", "H 823", "H 961 25", "H 961 27", "H 961 26", "H 961 29", "H 961 28", "H 917", "H 915", "H 962 1", "H 919", "H 910", "H 961 30", "H 914", "H 961 32", "H 913", "H 961 31", "H 966 2", "H 961 34", "H 911", "H 966 1","H 961 33", "H 961 14", "H 961 13", "H 961 15", "H 960", "H 961 17", "H 961 19", "H 929", "H 928", "H 927", "H 921", "H 964", "H 920", "H 963", "H 962", "H 961 21", "H 933 2", "H 968", "H 933 1", "H 967", "H 923", "H 961 23", "H 966", "H 961 07", "H 933 11", "H 961 4","H 961 3", "H 961 2", "H 937", "H 961 1", "H 925 3", "H 927 1", "H 932", "H 925 2", "H 931", "H 927 3", "H 961 9", "H 961 10", "H 961 8", "H 925 1", "H 961 7", "H 961 12", "H 961 6", "H 961 11", "H 933", "H 927 4", "H 981", "H 980","H 907", "H 906", "H 909", "H 908", "H 986", "H 929 25", "H 941", "H 903", "H 902", "H 945")),H.getClassRooms());
        assertEquals(coordinateH,H.getLatLng());
        assertEquals(coordinateOverlayH,H.getOverlayLatLng());
    }
}
