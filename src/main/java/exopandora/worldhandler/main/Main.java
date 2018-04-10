package exopandora.worldhandler.main;

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
	public static final String MC_VERSION = "1.12.2";
	public static final String MC_COMPATIBLE = "1.12";
	public static final String VERSION = "2.1.1";
	public static final String FULL_VERSION = MC_VERSION + "-" + VERSION;
	public static final String NAME_AND_VERSION = NAME + " " + FULL_VERSION;
	public static final String URL = "https://minecraft.curseforge.com/projects/world-handler-command-gui";
	public static final String UPDATE_URL = "https://raw.githubusercontent.com/Exopandora/worldhandler/master/version.json";
	public static final String CERTIFICATE = "d6261bb645f41db84c74f98e512c2bb43f188af2";
	
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
