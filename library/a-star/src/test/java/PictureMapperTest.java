import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static pictureMapper.GetPixelColor.*;

public class PictureMapperTest {

    @Test
    public void imageIsConvertedToBool() {

//        scalePNG("media/h9.png", "media/h9325.png", 325);
        boolean[][] bArr = null;
        try {
            BufferedImage image = ImageIO.read(new File("media/h9275.png"));
            int[][] rgbArr = getRGBarray(image);
            bArr = getBoolArr(rgbArr, -534826);
        } catch (IOException e) {
            System.err.println(e);
        }
        assertNotNull(bArr);
    }

    @Test
    public void imageDoesntExist() {

        boolean[][] bArr = null;
        try {
            BufferedImage image = ImageIO.read(new File("media/h9400.png"));
            int[][] rgbArr = getRGBarray(image);
            bArr = getBoolArr(rgbArr, -534826);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    @Test
    public void imageScales() {

        int size = 123;
        String filePath = "media/h9";
        scalePNG(filePath + ".png", filePath + size + ".png", size);
        int width = 0;
        int height = 0;
        try {
            BufferedImage image = ImageIO.read(new File(filePath + size + ".png"));
            width = image.getWidth();
            height = image.getHeight();
        } catch (IOException e) {
            System.err.println(e);
        }

        assertTrue(width == size || height == size);
        File file = new File(filePath + size + ".png");
        file.delete();

    }
}
