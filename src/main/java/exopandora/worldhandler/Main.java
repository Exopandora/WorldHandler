package exopandora.worldhandler;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.UIManager;

import exopandora.worldhandler.installer.Window;

public class Main
{
	public static final String NAME = "World Handler";
	public static final String MODID = "worldhandler";
	public static final String MC_VERSION = "1.17.1";
	public static final String MOD_VERSION = "2.12.7";
	public static final String URL = "https://minecraft.curseforge.com/projects/world-handler-command-gui";
	
	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		EventQueue.invokeLater(new Window());
	}
	
	public static File getInitialDirectory()
	{
		String home = System.getProperty("user.home", ".");
		String os = String.valueOf(System.getProperty("os.name")).toLowerCase();
		String minecraft = ".minecraft";
		
		if(os.contains("win") && System.getenv("APPDATA") != null)
		{
			return new File(System.getenv("APPDATA"), minecraft);
		}
		else if(os.contains("mac"))
		{
			return new File(new File(new File(home, "Library"), "Application Support"), "minecraft");
		}
		
		return new File(home, minecraft);
	}
}
