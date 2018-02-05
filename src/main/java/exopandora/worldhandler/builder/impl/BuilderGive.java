package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilderNBT;
import exopandora.worldhandler.builder.Syntax;
import exopandora.worldhandler.builder.types.Type;
import exopandora.worldhandler.helper.ResourceHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BuilderGive extends CommandBuilderNBT
{
	public BuilderGive(String player, ResourceLocation item)
	{
		this.setPlayer(player);
		this.setItem(item);
		this.setMetadata(0);
	}
	
	public BuilderGive()
	{
		this(null, null);
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
		this.setItem(ResourceHelper.stringToResourceLocationNullable(item, ResourceHelper::isRegisteredItem));
	}
	
	public void setItem(ResourceLocation item)
	{
		this.setNode(1, item);
	}
	
	public ResourceLocation getItem()
	{
		return this.getNodeAsResourceLocation(1);
	}
	
	public void setAmount(int ammount)
	{
		this.setNode(2, ammount);
	}
	
	public int getAmount()
	{
		return this.getNodeAsInt(2);
	}
	
	@Deprecated
	public void setMetadata(int metadata)
	{
		this.setNode(3, metadata);
	}

	@Deprecated
	public int getMetadata()
	{
		return this.getNodeAsInt(3);
	}
	
	@Override
	public void setNBT(NBTTagCompound nbt)
	{
		this.setNode(4, nbt);
	}
	
	public NBTTagCompound getNBT()
	{
		return this.getNodeAsNBT(4);
	}
	
	@Override
	public String getCommandName()
	{
		return "give";
	}
	
	@Override
	public final Syntax getSyntax()
	{
		Syntax syntax = new Syntax();
		
		syntax.addRequired("player", Type.STRING);
		syntax.addRequired("item", Type.RESOURCE_LOCATION);
		syntax.addRequired("amount", Type.INT);
		syntax.addRequired("data_value", Type.INT);
		syntax.addOptional("nbt", Type.NBT);
		
		return syntax;
	}
}
