package exopandora.worldhandler.builder.impl;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.CommandBuilderNBT;
import exopandora.worldhandler.builder.CommandSyntax;
import exopandora.worldhandler.builder.types.ItemResourceLocation;
import exopandora.worldhandler.util.ResourceHelper;
import exopandora.worldhandler.builder.types.ArgumentType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

@OnlyIn(Dist.CLIENT)
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
	public void setNBT(CompoundNBT nbt)
	{
		this.itemResourceLocation.setNBT(nbt);
		this.setNode(1, this.itemResourceLocation);
	}
	
	public CompoundNBT getNBT()
	{
		return this.getNodeAsItemResourceLocation(1).getNBT();
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
