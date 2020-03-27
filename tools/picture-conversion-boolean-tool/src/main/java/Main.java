import com.conupods.pictureMapper.PixelGridBoolean;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class Main {

    public static void main(String[] args) {

        int size = 275;
        String rootDir = "media/";
        String orgDir = "original/";
        String scaledDir = "scaled/";
        String charDir = "charFiles/";

        File directoryPath = new File(rootDir+orgDir);
        String files[] = directoryPath.list();

        for (String file : files){

            String fileName = file.substring(0, file.length() - 4);
            String fileExt = file.substring(file.length()-4);
            PixelGridBoolean.scalePNG(rootDir+orgDir+file, rootDir+scaledDir+ fileName +size+fileExt, size);

            BufferedImage image = null;

            try {
                image = ImageIO.read(new File(rootDir+scaledDir+ fileName +size+fileExt));
            } catch (IOException e){
                System.err.println(e);
            }

            int[][] pixelArr = PixelGridBoolean.getRGBArray(image);
            String[] byteArr = PixelGridBoolean.getArr(pixelArr);

            PrintWriter out = null;


            try{
                out = new PrintWriter(rootDir+charDir+fileName, "UTF-8");
            }catch (FileNotFoundException | UnsupportedEncodingException e){
                System.err.println(e);
            }

            for(int i = 0; i<byteArr.length;i++){
                out.println(byteArr[i]);
            }
            out.close();


        }



    }
}
