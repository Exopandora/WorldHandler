package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.Syntax;
import exopandora.worldhandler.builder.types.Type;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BuilderEnchantment extends CommandBuilder
{
	public void getPlayer(String player)
	{
		this.getNodeAsString(0);
	}
	
	public void setPlayer(String player)
	{
		this.setNode(0, player);
	}
	
	public ResourceLocation getEnchantment()
	{
		return this.getNodeAsResourceLocation(1);
	}
	
	public void setEnchantment(Enchantment enchantment)
	{
		this.setEnchantment(enchantment.getRegistryName());
	}
	
	public void setEnchantment(ResourceLocation enchantment)
	{
		this.setNode(1, enchantment);
	}
	
	public void setLevel(int level)
	{
		this.setNode(2, level);
	}
	
	public int getLevel()
	{
		return this.getNodeAsInt(2);
	}
	
	@Override
	public String getCommandName()
	{
		return "enchant";
	}
	
	@Override
	public Syntax getSyntax()
	{
		Syntax syntax = new Syntax();
		
		syntax.addRequired("player", Type.STRING);
		syntax.addRequired("enchantment", Type.RESOURCE_LOCATION);
		syntax.addOptional("level", Type.INT, 1);
		
		return syntax;
	}
}
