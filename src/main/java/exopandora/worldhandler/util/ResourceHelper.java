package exopandora.worldhandler.util;

import javax.annotation.Nullable;

import exopandora.worldhandler.Main;
import exopandora.worldhandler.config.Config;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;

public class ResourceHelper
{
	@Nullable
	public static ResourceLocation stringToResourceLocation(@Nullable String resource)
	{
		if(resource != null && !resource.isEmpty())
		{
			return ResourceLocation.tryParse(resource.toLowerCase().replaceAll("[^a-z0-9/._\\-:]", "_"));
		}
		
		return null;
	}
	
	public static boolean isRegistered(ResourceLocation resource, Registry<?> registry)
	{
		return resource != null && registry != null && registry.containsKey(resource);
	}
	
	@Nullable
	public static ResourceLocation assertRegistered(ResourceLocation resource, Registry<?> registry)
	{
		if(resource != null && ResourceHelper.isRegistered(resource, registry))
		{
			return resource;
		}
		
		return null;
	}
	
	public static ResourceLocation iconTexture()
	{
		return ResourceLocation.fromNamespaceAndPath(Main.MODID, "textures/icons/icons_" + Config.getSkin().getIconSize().name() + ".png");
	}
}
