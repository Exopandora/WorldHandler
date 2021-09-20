package exopandora.worldhandler.builder.impl;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandSyntax;
import exopandora.worldhandler.builder.types.ArgumentType;
import exopandora.worldhandler.util.EnumHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

public class BuilderRecipe extends CommandBuilder
{
	public BuilderRecipe()
	{
		this(null, null, null);
	}
	
	public BuilderRecipe(EnumMode mode, String player, ResourceLocation recipe)
	{
		this.setMode(mode);
		this.setPlayer(player);
		this.setRecipe(recipe);
	}
	
	public void setMode(EnumMode mode)
	{
		this.setNode(0, mode != null ? mode.toString() : null);
	}
	
	@Nullable
	public EnumMode getMode()
	{
		return EnumHelper.valueOf(this.getNodeAsString(0), EnumMode.class);
	}
	
	public void setPlayer(String player)
	{
		this.setNode(1, player);
	}
	
	@Nullable
	public String getPlayer()
	{
		return this.getNodeAsString(1);
	}
	
	public void setRecipe(Recipe<?> recipe)
	{
		this.setRecipe(recipe.getId());
	}
	
	public void setRecipe(ResourceLocation recipe)
	{
		this.setNode(2, recipe);
	}
	
	@Nullable
	public ResourceLocation getRecipe()
	{
		return this.getNodeAsResourceLocation(2);
	}
	
	public BuilderRecipe build(EnumMode mode)
	{
		return new BuilderRecipe(mode, this.getPlayer(), this.getRecipe());
	}
	
	@Override
	public String getCommandName()
	{
		return "recipe";
	}
	
	@Override
	public CommandSyntax getSyntax()
	{
		CommandSyntax syntax = new CommandSyntax();
		
		syntax.addRequired("give|take", ArgumentType.STRING);
		syntax.addOptional("player", ArgumentType.STRING);
		syntax.addOptional("recipe", ArgumentType.RESOURCE_LOCATION);
		
		return syntax;
	}
	
	public static enum EnumMode
	{
		GIVE,
		TAKE;
		
		@Override
		public String toString()
		{
			return this.name().toLowerCase();
		}
	}
}
