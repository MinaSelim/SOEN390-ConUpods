package conupods.tools;

import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import conupods.tools.gui.MainFrame;

public class Main {
	
	public static void main(String[] args)
	{
		String path = "C:/Users/super/Desktop/floor_plans/Hall-1.png";
		final JFileChooser fc = new JFileChooser();
		int r = fc.showOpenDialog(null);
		
		
		if(r == JFileChooser.APPROVE_OPTION)
		{
			path = fc.getSelectedFile().getAbsolutePath();
			MainFrame gui = null;
			try {
				gui = new MainFrame(path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			gui.setSize(2000,2000);
			gui.setVisible(true);

			gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		}
		
		
	}
	

}
