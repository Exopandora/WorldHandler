package exopandora.worldhandler.builder.impl;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.Syntax;
import exopandora.worldhandler.builder.types.Type;
import exopandora.worldhandler.helper.EnumHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
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

	@Nullable
	public EnumActionType getActionType()
	{
		return EnumHelper.valueOf(this.getNodeAsString(1), EnumActionType.class);
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
	
	public void setMode(EnumMode mode)
	{
		this.setNode(2, mode != null ? mode.toString() : null);
	}

	@Nullable
	public EnumMode getMode()
	{
		return EnumHelper.valueOf(this.getNodeAsString(2), EnumMode.class);
	}
	
	public void setAdvancement(ResourceLocation advancement)
	{
		this.setNode(3, advancement);
	}
	
	@Nullable
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
	
	@OnlyIn(Dist.CLIENT)
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
	
	@OnlyIn(Dist.CLIENT)
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
