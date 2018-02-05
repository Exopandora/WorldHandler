package exopandora.worldhandler.config;

import java.util.HashMap;
import java.util.Map;

import exopandora.worldhandler.helper.EntityHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ConfigButcher
{	
	private static Map<String, Boolean> ENTITIES = new HashMap<String, Boolean>();
	
	public static final String CATEGORY = "butcher";
	
	public static void load(Configuration config)
	{
		for(ResourceLocation location : EntityList.ENTITY_EGGS.keySet())
		{
			String entity = EntityHelper.getEntityName(location);
			String translationKey = "entity." + entity + ".name";
			String translation = I18n.format(translationKey);
			
			if(!translation.equals(translationKey))
			{
				ENTITIES.put(entity, config.getBoolean(entity, CATEGORY, false, I18n.format("gui.worldhandler.config.comment.butcher", translation), translationKey));
			}
		}
		
		if(config.hasChanged())
		{
			config.save();
		}
	}
	
	public static Map<String, Boolean> getEntitiyMap()
	{
		return ENTITIES;
	}
}
