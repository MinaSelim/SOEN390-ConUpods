package com.conupods;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.ls.LSOutput;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MetaData {

    public static void main(String[] args) {

        String pattern = "\\S*\\/(\\S+)-(\\S+)";
        Pattern p = Pattern.compile(pattern);

        try(Stream<Path> walk = Files.walk(Paths.get("../../app/src/main/assets/json"))){
            List<String> result = walk.filter(Files::isRegularFile)
                    .map(x -> x.toString()).collect(Collectors.toList());

            for(String path : result){
                System.out.println(path);
                Matcher m = p.matcher(path);
                if(m.find()) {
                    try {
                        metaToJson(path, m);
                    } catch (IOException | ParseException e){
                        e.printStackTrace();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    //If we can have some convention to naming the metadata.MetaData, floor and building strings can be omitted

    public static void metaToJson(String path, Matcher m) throws IOException, ParseException {

        Scanner sc = new Scanner(new BufferedReader(new FileReader(path)));
        String dataString = "";
        do {
            dataString += sc.nextLine();
        } while (sc.hasNextLine());

        sc.close();

        JSONParser parser = new JSONParser();

        Object dataObj = parser.parse(dataString);
        JSONArray dataArray = (JSONArray) dataObj;

        JSONObject obj = new JSONObject();


        HashMap<String, HashMap<Integer, Dictionary>> room = new HashMap<>();


        for (int i = 0; i < dataArray.size(); i++) {

            JSONArray dataRow = (JSONArray) dataArray.get(i);

            for (int j = 0; j < dataRow.size(); j++) {

                String place = (String) dataRow.get(j);
                if (place != null && !place.equals("N/A")) {
                    int index = 0;

                    HashMap<Integer, Dictionary> coords = new HashMap<>();
                    Dictionary coord = new Hashtable();
                    coord.put("x", i);
                    coord.put("y", j + 1);

                    if (room.containsKey(place)) {
                        coords = room.get(place);
                        index = coords.size();
                    }
                    coords.put(index, coord);

                    room.put(place, coords);
                }
            }
        }

        obj.put(m.group(1), room);

        FileWriter file = new FileWriter(path + ".json");
        file.write(obj.toJSONString());
        file.flush();
        file.close();

    }

}
