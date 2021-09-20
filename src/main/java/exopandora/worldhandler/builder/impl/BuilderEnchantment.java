package exopandora.worldhandler.builder.impl;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandSyntax;
import exopandora.worldhandler.builder.types.ArgumentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;

public class BuilderEnchantment extends CommandBuilder
{
	@Nullable
	public String getPlayer()
	{
		return this.getNodeAsString(0);
	}
	
	public void setPlayer(String player)
	{
		this.setNode(0, player);
	}
	
	@Nullable
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
	public CommandSyntax getSyntax()
	{
		CommandSyntax syntax = new CommandSyntax();
		
		syntax.addRequired("player", ArgumentType.STRING);
		syntax.addRequired("enchantment", ArgumentType.RESOURCE_LOCATION);
		syntax.addOptional("level", ArgumentType.INT, 1);
		
		return syntax;
	}
}
