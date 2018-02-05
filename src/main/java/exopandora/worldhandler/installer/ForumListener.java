package exopandora.worldhandler.installer;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

import exopandora.worldhandler.main.Main;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ForumListener implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent event)
	{
		if(Desktop.isDesktopSupported())
		{
		    try
			{
				Desktop.getDesktop().browse(new URI(Main.URL));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
 