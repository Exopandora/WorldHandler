package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.Syntax;
import exopandora.worldhandler.builder.types.Type;
import exopandora.worldhandler.helper.EnumHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BuilderAdvancement extends CommandBuilder
{
	public BuilderAdvancement(EnumMode mode)
	{
		this.setMode(mode);
	}
	
	public BuilderAdvancement(EnumActionType action, String player, EnumMode mode, ResourceLocation advancement)
	{
		this(mode);
		this.setActionType(action);
		this.setPlayer(player);
		this.setAdvancement(advancement);
	}
	
	public void setActionType(EnumActionType action)
	{
		this.setNode(0, action != null ? action.toString() : null);
	}
	
	public EnumActionType getActionType()
	{
		return EnumHelper.valueOf(EnumActionType.class, this.getNodeAsString(1));
	}
	
	public void setPlayer(String player)
	{
		this.setNode(1, player);
	}
	
	public String getPlayer()
	{
		return this.getNodeAsString(1);
	}
	
	public void setMode(EnumMode mode)
	{
		this.setNode(2, mode != null ? mode.toString() : null);
	}
	
	public EnumMode getMode()
	{
		return EnumHelper.valueOf(EnumMode.class, this.getNodeAsString(2));
	}
	
	public void setAdvancement(ResourceLocation advancement)
	{
		this.setNode(3, advancement);
	}
	
	public ResourceLocation getAdvancement()
	{
		return this.getNodeAsResourceLocation(3);
	}
	
	public BuilderAdvancement getBuilderForAction(EnumActionType action)
	{
		return this.getBuilder(action, this.getMode());
	}
	
	public BuilderAdvancement getBuilder(EnumActionType action, EnumMode mode)
	{
		return new BuilderAdvancement(action, this.getPlayer(), mode, (mode != null && !mode.equals(EnumMode.EVERYTHING)) ? this.getAdvancement() : null);
	}
	
	@Override
	public String getCommandName()
	{
		return "advancement";
	}
	
	@Override
	public final Syntax getSyntax()
	{
		Syntax syntax = new Syntax();
		
		syntax.addRequired("grant|revoke|test", Type.STRING);
		syntax.addRequired("player", Type.STRING);
		syntax.addRequired("only|until|from|through|everything", Type.STRING);
		syntax.addOptional("advancement", Type.RESOURCE_LOCATION);
		
		return syntax;
	}
	
	public static enum EnumActionType
	{
		GRANT,
		REVOKE;
		
		@Override
		public String toString()
		{
			return this.name().toLowerCase();
		}
	}
	
	public static enum EnumMode
	{
		ONLY,
		UNTIL,
		FROM,
		THROUGH,
		EVERYTHING;
		
		@Override
		public String toString()
		{
			return this.name().toLowerCase();
		}
	}
}
