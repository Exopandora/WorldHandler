package exopandora.worldhandler.installer;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import exopandora.worldhandler.Main;

public class Window implements Runnable
{
	private final JFrame frame = new JFrame();
	
	@Override
	public void run()
	{
		String titleString = Main.NAME + " " + Main.MC_VERSION + "-" + Main.MOD_VERSION + " Installer";
		
		this.frame.setResizable(false);
		this.frame.setTitle(titleString);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		List<Image> icons = new ArrayList<Image>();
		
		icons.add(new ImageIcon(this.getClass().getResource("/assets/worldhandler/installer/icon/icon16.png")).getImage());
		icons.add(new ImageIcon(this.getClass().getResource("/assets/worldhandler/installer/icon/icon32.png")).getImage());
		icons.add(new ImageIcon(this.getClass().getResource("/assets/worldhandler/installer/icon/icon64.png")).getImage());
		icons.add(new ImageIcon(this.getClass().getResource("/assets/worldhandler/installer/icon/icon128.png")).getImage());
		icons.add(new ImageIcon(this.getClass().getResource("/assets/worldhandler/installer/icon/icon256.png")).getImage());
		
		this.frame.setIconImages(icons);
		this.frame.setLayout(new GridBagLayout());
		
		JLabel logo = new JLabel();
		logo.setIcon(new ImageIcon(Window.class.getResource("/assets/worldhandler/installer/logo.png")));
		
		GridBagConstraints gbcLogo = new GridBagConstraints();
		gbcLogo.insets = new Insets(15, 10, 15, 5);
		gbcLogo.gridx = 0;
		gbcLogo.gridy = 0;
		this.frame.add(logo, gbcLogo);
		
		JPanel panel = new JPanel(new GridBagLayout());
		
		GridBagConstraints gbcPanel = new GridBagConstraints();
		gbcPanel.insets = new Insets(15, 5, 15, 10);
		gbcPanel.fill = GridBagConstraints.BOTH;
		gbcPanel.gridx = 1;
		gbcPanel.gridy = 0;
		this.frame.add(panel, gbcPanel);
		
		JLabel title = new JLabel(titleString);
		panel.add(title, this.getButtonConstraints(0, 0));
		
		JTextField textField = new JTextField(Main.getInitialDirectory().getAbsolutePath());
		textField.setCaretPosition(textField.getText().length());
		textField.setColumns(30);
		panel.add(textField, this.getButtonConstraints(0, 1));
		
		JButton directory = new JButton("Change Minecraft Folder");
		directory.addActionListener(new ChangeFolderListener(textField));
		panel.add(directory, this.getButtonConstraints(0, 2));
		
		JButton changelog = new JButton("Forum");
		changelog.addActionListener(new ForumListener());
		panel.add(changelog, this.getButtonConstraints(0, 3));
		
		JButton install = new JButton("Install");
		install.addActionListener(new InstallListener(this.frame, textField));
		panel.add(install, this.getButtonConstraints(0, 4));
		
		JButton exit = new JButton("Exit");
		exit.addActionListener(event -> this.frame.dispose());
		panel.add(exit, this.getButtonConstraints(0, 5));
		
		this.frame.pack();
		this.frame.setLocationRelativeTo(null);
		this.frame.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{textField, directory, changelog, install, exit}));
		this.frame.setVisible(true);
		
		install.requestFocusInWindow();
	}
	
	private GridBagConstraints getButtonConstraints(int x, int y)
	{
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = x;
		gbc.gridy = y;
		
		return gbc;
	}
	
	private static final class FocusTraversalOnArray extends FocusTraversalPolicy
	{
		private final Component components[];
		
		public FocusTraversalOnArray(Component components[])
		{
			this.components = components;
		}
		
		private int indexCycle(int index, int delta)
		{
			int size = this.components.length;
			int next = (index + delta + size) % size;
			
			return next;
		}
		
		private Component cycle(Component currentComponent, int delta)
		{
			int index = -1;
			
			loop : for(int i = 0; i < this.components.length; i++)
			{
				Component component = this.components[i];
				
				for(Component c = currentComponent; c != null; c = c.getParent())
				{
					if(component == c)
					{
						index = i;
						break loop;
					}
				}
			}
			
			int initialIndex = index;
			
			while(true)
			{
				int newIndex = this.indexCycle(index, delta);
				
				if(newIndex == initialIndex)
				{
					break;
				}
				
				index = newIndex;
				
				Component component = this.components[newIndex];
				
				if(component.isEnabled() && component.isVisible() && component.isFocusable())
				{
					return component;
				}
			}
			
			return currentComponent;
		}
		
		public Component getComponentAfter(Container container, Component component)
		{
			return this.cycle(component, 1);
		}
		
		public Component getComponentBefore(Container container, Component component)
		{
			return this.cycle(component, -1);
		}
		
		public Component getFirstComponent(Container container)
		{
			return this.components[0];
		}
		
		public Component getLastComponent(Container container)
		{
			return this.components[this.components.length - 1];
		}
		
		public Component getDefaultComponent(Container container)
		{
			return this.getFirstComponent(container);
		}
	}
}
