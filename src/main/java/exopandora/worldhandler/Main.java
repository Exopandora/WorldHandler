package exopandora.worldhandler;

import java.awt.EventQueue;
import java.io.File;
import java.util.Locale;

import javax.swing.UIManager;

import exopandora.worldhandler.installer.Window;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Main
{
	public static final String NAME = "World Handler";
	public static final String MODID = "worldhandler";
	
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
		String os = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
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
