package exopandora.worldhandler.builder.impl;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.CommandBuilderNBT;
import exopandora.worldhandler.builder.CommandSyntax;
import exopandora.worldhandler.builder.types.ArgumentType;
import exopandora.worldhandler.builder.types.ItemResourceLocation;
import exopandora.worldhandler.util.ResourceHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class BuilderGive extends CommandBuilderNBT
{
	private final ItemResourceLocation itemResourceLocation = new ItemResourceLocation();
	
	public BuilderGive()
	{
		this(null, null);
	}
	
	public BuilderGive(String player, ResourceLocation item)
	{
		this.setPlayer(player);
		this.setItem(item);
		this.setCount(1);
	}
	
	public void setPlayer(String username)
	{
		this.setNode(0, username);
	}
	
	@Nullable
	public String getPlayer()
	{
		return this.getNodeAsString(0);
	}
	
	public void setItem(String item)
	{
		this.setItem(ResourceHelper.assertRegistered(ResourceHelper.stringToResourceLocation(item), ForgeRegistries.ITEMS));
	}
	
	public void setItem(ResourceLocation item)
	{
		this.itemResourceLocation.setResourceLocation(item);
		this.setNode(1, this.itemResourceLocation);
	}
	
	@Nullable
	public ItemResourceLocation getItem()
	{
		return this.getNodeAsItemResourceLocation(1);
	}
	
	public void setCount(int count)
	{
		this.setNode(2, count);
	}
	
	public int getCount()
	{
		return this.getNodeAsInt(2);
	}
	
	@Override
	public void setNBT(CompoundTag nbt)
	{
		this.itemResourceLocation.setNBT(nbt);
		this.setNode(1, this.itemResourceLocation);
	}
	
	@Nullable
	public CompoundTag getNBT()
	{
		ItemResourceLocation item = this.getItem();
		
		if(item != null)
		{
			return item.getNBT();
		}
		
		return null;
	}
	
	@Override
	public String getCommandName()
	{
		return "give";
	}
	
	@Override
	public final CommandSyntax getSyntax()
	{
		CommandSyntax syntax = new CommandSyntax();
		
		syntax.addRequired("player", ArgumentType.STRING);
		syntax.addRequired("item", ArgumentType.ITEM_RESOURCE_LOCATION);
		syntax.addRequired("count", ArgumentType.INT);
		
		return syntax;
	}
}
