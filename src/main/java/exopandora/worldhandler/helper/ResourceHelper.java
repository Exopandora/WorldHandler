package exopandora.worldhandler.helper;

import java.util.function.Predicate;

import javax.annotation.Nullable;

import exopandora.worldhandler.Main;
import exopandora.worldhandler.config.ConfigSkin;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ResourceHelper
{
	private static final ResourceLocation BACKGROUND = new ResourceLocation("textures/gui/demo_background.png");
	private static final ResourceLocation BACKGROUND_VANILLA = new ResourceLocation(Main.MODID, "textures/skins/vanilla/vanilla.png");
	private static final ResourceLocation BUTTON = new ResourceLocation("textures/gui/widgets.png");
	
	public static ResourceLocation stringToResourceLocation(String resource)
	{
		if(resource != null)
		{
			return new ResourceLocation(resource.replaceAll(" ", "_"));
		}
		
		return null;
	}
	
	public static boolean isRegisteredItem(String item)
	{
		return Item.REGISTRY.getKeys().contains(stringToResourceLocation(item));
	}
	
	public static boolean isRegisteredBlock(String block)
	{
		return Block.REGISTRY.getKeys().contains(stringToResourceLocation(block));
	}
	
	public static boolean isRegisteredMob(String mob)
	{
		return EntityList.isRegistered(stringToResourceLocation(mob));
	}
	
	public static boolean isRegisteredAdvancement(String advancement)
	{
		return AdvancementHelper.ADVANCEMENT_MANAGER.getAdvancement(stringToResourceLocation(advancement)) != null;
	}
	
	@Nullable
	public static ResourceLocation stringToResourceLocationNullable(String resource, Predicate<String> predicate)
	{
		if(predicate.test(resource))
		{
			return stringToResourceLocation(resource);
		}
		
		return null;
	}
	
	public static ResourceLocation getBackgroundTexture()
	{
		if(ConfigSkin.getTextureType().equals("resourcepack"))
		{
			return BACKGROUND;
		}
		
		return BACKGROUND_VANILLA;
	}
	
	public static ResourceLocation getIconTexture()
	{
		return new ResourceLocation(Main.MODID, "textures/icons/icons" + ConfigSkin.getIconSize() + ".png");
	}
	
	public static ResourceLocation getButtonTexture()
	{
		if(ConfigSkin.getTextureType().equals("resourcepack"))
	    {
			return BUTTON;
	    }
		
		return new ResourceLocation(Main.MODID, "textures/skins/" + ConfigSkin.getTextureType() + "/" + ConfigSkin.getTextureType() + "_buttons.png");
	}
}
