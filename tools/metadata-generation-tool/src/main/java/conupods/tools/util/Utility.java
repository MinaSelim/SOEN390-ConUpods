package conupods.tools.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import com.google.gson.Gson;

public class Utility 
{
	public static String get2DArrayAsJson(String[][] arr)
	{
		Gson gson = new Gson();
		return gson.toJson(arr);
	}
	
	public static void exportToFile(String path, String json) throws IOException
	{
		PrintWriter writer = new PrintWriter(path);
		
		writer.print(json);
		
		writer.close();
	}
	
	public static String importFile(String path) throws FileNotFoundException 
	{
		Scanner filereader = new Scanner(new FileInputStream(path));
		StringBuilder builder = new StringBuilder();
		
		while(filereader.hasNext())
		{
			builder.append(filereader.nextLine());
		}
		
		filereader.close();
		
		return builder.toString();
	}
	
	public static String[][] getJsonAs2DArray(String json)
	{
		Gson gson = new Gson();
		String[][] arr = gson.fromJson(json, String[][].class);
		
		return arr;
	}
}
