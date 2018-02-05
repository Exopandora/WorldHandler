package exopandora.worldhandler.helper;

import java.util.List;

import exopandora.worldhandler.builder.impl.BuilderAdvancement.EnumMode;
import net.minecraft.advancements.AdvancementManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class AdvancementHelper
{
	public static final AdvancementManager ADVANCEMENT_MANAGER = new AdvancementManager(null);
	private final Node modes = new Node();
	
	public AdvancementHelper()
	{
		this.init();
	}
	
	private void init()
	{
		for(EnumMode mode : EnumMode.values())
		{
			this.modes.addNode(mode.toString());
		}
	}
	
	public List<Node> getModes()
	{
		if(this.modes != null)
		{
			return this.modes.getEntries();
		}
		
		return null;
	}
}
