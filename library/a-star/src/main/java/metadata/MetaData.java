package metadata;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MetaData {

    //If we can have some convention to naming the metadata.MetaData, floor and building strings can be omitted

    public static void metaToJson(String path, int floor, String building) throws IOException, ParseException {

        Scanner sc = new Scanner(new BufferedReader(new FileReader(path)));
        String dataString = "";
        do{
            dataString += sc.nextLine();
        }while(sc.hasNextLine());

        sc.close();

        JSONParser parser = new JSONParser();

        Object dataObj = parser.parse(dataString);
        JSONArray dataArray = (JSONArray) dataObj;

        JSONObject obj = new JSONObject();


        HashMap<String, HashMap<Integer, Dictionary>> room = new HashMap<>();



        for(int i = 0; i<dataArray.size(); i++){

            JSONArray dataRow = (JSONArray) dataArray.get(i);

            for(int j = 0; j<dataRow.size();j++){

                String place = (String) dataRow.get(j);
                if(place != null && !place.equals("N/A")){
                    int index = 0;

                    HashMap<Integer, Dictionary> coords = new HashMap<>();
                    Dictionary coord = new Hashtable();
                    coord.put("x", i);
                    coord.put("y", j+1);

                    if (room.containsKey(place)) {
                        coords = room.get(place);
                        index = coords.size();
                    }
                    coords.put(index,coord);

                    room.put(place, coords);
                }
            }
        }

        obj.put(floor,room);

        FileWriter file = new FileWriter(path+".json");
        file.write(obj.toJSONString());
        file.flush();
        file.close();

    }

}
