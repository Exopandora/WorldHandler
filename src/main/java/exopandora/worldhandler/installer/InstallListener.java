package exopandora.worldhandler.installer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import exopandora.worldhandler.Main;

public class InstallListener implements ActionListener
{
	private final JFrame parent;
	private final JTextField textField;
	
	public InstallListener(JFrame parent, JTextField textField)
	{
		this.parent = parent;
		this.textField = textField;
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		File directory = new File(this.textField.getText());
		
		if(directory.isDirectory())
		{
			File mods = new File(directory, "mods");
			File versions = new File(directory, "versions");
			
			if(!this.isPartialFileNameInFolder(versions, Main.MC_VERSION, "Forge"))
			{
				JOptionPane.showMessageDialog(null, "Please install Mineceaft Forge", null, JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				for(File file : mods.listFiles())
				{
					if(this.containsIgnoreCase(file.getName(), "World") && this.containsIgnoreCase(file.getName(), "Handler"))
					{
						file.delete();
					}
				}
				
				try
				{
					Path path = new File(InstallListener.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).toPath();
					Files.copy(path, new File(mods, "WorldHandler-" + Main.MC_VERSION + "-" + Main.MOD_VERSION + "-Universal.jar").toPath(), StandardCopyOption.REPLACE_EXISTING);
					JOptionPane.showMessageDialog(null, Main.NAME + " " + Main.MC_VERSION + "-" + Main.MOD_VERSION + " has been successfully installed", null, JOptionPane.INFORMATION_MESSAGE);
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Error:\n" + e.getMessage(), null, JOptionPane.ERROR_MESSAGE);
				}
			}
			
			this.parent.dispose();
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Invalid directory", null, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private boolean containsIgnoreCase(String string, String sequence)
	{
		return string.toLowerCase().contains(sequence.toLowerCase());
	}
	
	private boolean isPartialFileNameInFolder(File path, String... parts)
	{
		if(path.exists() && path.isDirectory())
		{
			for(File file : path.listFiles())
			{
				int matches = 0;
				
				for(String part : parts)
				{
					if(this.containsIgnoreCase(file.getName(), part))
					{
						if(matches < parts.length)
						{
							matches++;
						}
						else
						{
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}
}
