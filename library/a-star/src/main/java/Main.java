import astar.AStar;
import astar.Destination;
import astar.Edges;
import astar.Spot;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        AStar aStar = new AStar();
        aStar.mMetadataFilePath = "metadata/json/Metadata.json";

        String startString = "H-945";
        String endString = "H-927";

        Destination start = aStar.setDestFromString(startString);
        Destination end = aStar.setDestFromString(endString);

        InputStreamReader in = new InputStreamReader(new FileInputStream(new File(aStar.mMetadataFilePath)));

        Edges[] startEnd = aStar.getDictFromJSON(start, end, in);


        int x1 = (startEnd[0].getRight() - startEnd[0].getLeft()) / 2 + startEnd[0].getLeft();
        int y1 = (startEnd[0].getTop() - startEnd[0].getBottom()) / 2 + startEnd[0].getBottom();
        int x2 = (startEnd[1].getRight() - startEnd[1].getLeft()) / 2 + startEnd[1].getLeft();
        int y2 = (startEnd[1].getTop() - startEnd[1].getBottom()) / 2 + startEnd[1].getBottom();


        boolean[][] binGrid = null;

        try {
            binGrid = createBinaryGrid("metadata/gridfile/h9");
        } catch (IOException e) {
            System.err.println(e);
        }


        aStar.createSpotGrid(binGrid, startEnd);
        aStar.linkNeighbors();
        Spot walk = aStar.runAlgorithm(x1, y1, x2, y2);

        walk.setBuilding("H");

        System.out.println("Done!");

    }

    public static boolean[][] createBinaryGrid(String filePath) throws FileNotFoundException {

        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath))));

        List<String> mapString = new ArrayList<>();

        while (true) {
            try {
                String nextLine = in.readLine();
                if (nextLine == null) {
                    break;
                }
                mapString.add(nextLine);
            } catch (IOException e) {
                break;
            }
        }

        boolean[][] bool = new boolean[mapString.size()][mapString.get(mapString.size() - 1).length()];

        for (int i = 0; i < bool.length; i++) {

            String[] chars = mapString.get(i).split("");

            for (int j = 0; j < bool[i].length; j++) {

                if (chars[j].equals("0")) {
                    bool[j][i] = false;
                } else {
                    bool[j][i] = true;
                }
            }
            System.out.println();
        }

        return bool;

    }
}
