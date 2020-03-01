package conupods.tools.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import conupods.tools.util.Utility;

public class MainFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	private final int SIDE_OF_GRID = 25;
	private String [][] gridProperties;
	private JButton [][] gridButtons;
	
	public MainFrame(String imagePath) throws IOException
	{
		super("Metadata Generation Tool");
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		JMenuItem menuItem = new JMenuItem("export");
		JMenuItem menuItem2 = new JMenuItem("import");
		menu.add(menuItem);
		menu.add(menuItem2);
		menuBar.add(menu);
		
		menuItem.addActionListener(e ->{
			JFileChooser fc = new JFileChooser();
			int r = fc.showSaveDialog(null);
			if(r == JFileChooser.APPROVE_OPTION) {
				String path = fc.getSelectedFile().getAbsolutePath();
				try {
					Utility.exportToFile(path, Utility.get2DArrayAsJson(gridProperties));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
			
		menuItem2.addActionListener(e -> {
			JFileChooser fc = new JFileChooser();
			int r = fc.showOpenDialog(null);
			if(r == JFileChooser.APPROVE_OPTION) {
				String path = fc.getSelectedFile().getAbsolutePath();
				try {
					String json = Utility.importFile(path);
					gridProperties = Utility.getJsonAs2DArray(json);
					refreshButtons();
					
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		
		JPanel buttons = new JPanelWithBackground(imagePath);
		
		
		buttons.setLayout(new GridLayout(SIDE_OF_GRID,SIDE_OF_GRID));

		gridButtons = new JButton[SIDE_OF_GRID][SIDE_OF_GRID];
		gridProperties = new String[SIDE_OF_GRID][SIDE_OF_GRID];
		
		for(int i = 0; i<gridButtons.length ; i++)
		{
			for(int j = 0; j<gridButtons[0].length ; j++)
			{
				gridButtons[i][j] = new JButton(gridProperties[i][j]);
				gridButtons[i][j].setOpaque(false);
				gridButtons[i][j].setContentAreaFilled(false);
				gridButtons[i][j].addActionListener(new ButtonListener(i,j));
				gridButtons[i][j].setForeground(Color.BLUE);
				buttons.add(gridButtons[i][j]);
			}
		}
		buttons.setOpaque(false);
		
		this.add(menuBar, BorderLayout.NORTH);
		this.add(buttons);
		
	}
	
	private void refreshButtons()
	{
		for(int i = 0; i<gridButtons.length ; i++)
		{
			for(int j = 0; j<gridButtons[0].length ; j++)
			{
				gridButtons[i][j].setText(gridProperties[i][j]);;
			}
		}
	}
	
	private class ButtonListener implements ActionListener
	{
		private int x,y;		
		
		public ButtonListener(int a, int b)
		{
			x = a;
			y = b;
		}
		
		public void actionPerformed(ActionEvent arg0) 
		{
			
			String tags = JOptionPane.showInputDialog("modify meta-tags and seperate them with commas. (Current selection would be displayed)", gridProperties[x][y]);
			gridProperties[x][y] = tags;
			gridButtons[x][y].setText(tags);
		}
		
	}

}
