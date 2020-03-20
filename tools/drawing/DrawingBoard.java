package drawing;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class DrawingBoard extends JPanel {
    public final List<MyDrawable> drawables = new ArrayList<>();

    public DrawingBoard(int w, int h) {
        setBackground(Color.white);
        setPreferredSize(new Dimension(w, h));
    }

    public void addMyDrawable(MyDrawable myDrawable) {
        drawables.add(myDrawable);
        revalidate();
        repaint();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        for (int i = 0; i < drawables.size(); i++) {
            if (drawables.get(i).fill) {
                drawables.get(i).fill(g2);
            }
            drawables.get(i).draw(g2);
        }
    }

    public void clearAll() {
        drawables.clear();
        repaint();
    }

    public static DrawingBoard createGui(int w, int h) {
        final DrawingBoard drawChit = new DrawingBoard(w, h);

        JFrame frame = new JFrame("DrawingBoard");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(drawChit);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        return drawChit;
    }
}



