package exopandora.worldhandler.util;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementNode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;

public class AdvancementHelper
{
	private static final AdvancementHelper INSTANCE = new AdvancementHelper();
	
	public Collection<AdvancementHolder> getAdvancements()
	{
		ClientPacketListener connection = Minecraft.getInstance().getConnection();
		
		if(connection == null)
		{
			return Collections.emptyList();
		}
		
		return connection.getAdvancements().getTree().nodes().stream()
				.map(AdvancementNode::holder)
				.collect(Collectors.toList());
	}
	
	public static AdvancementHelper getInstance()
	{
		return INSTANCE;
	}
}
