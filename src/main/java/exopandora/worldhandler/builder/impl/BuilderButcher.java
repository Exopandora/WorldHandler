package exopandora.worldhandler.builder.impl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.Syntax;
import exopandora.worldhandler.builder.types.TargetSelector;
import exopandora.worldhandler.builder.types.Type;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BuilderButcher extends CommandBuilder
{
	private final TargetSelector targetSelector;
	
	public BuilderButcher()
	{
		this(new ResourceLocation("<entity_name>"), 0);
	}
	
	public BuilderButcher(ResourceLocation entity, int radius)
	{
		this.targetSelector = new TargetSelector();
		this.setEntity(entity);
		this.setRadius(radius);
	}
	
	public void setRadius(int radius)
	{
		this.targetSelector.set("r", radius);
		this.setNode(0, this.targetSelector);
	}

	@Nonnull
	public int getRadius()
	{
		return this.targetSelector.<Integer>get("r");
	}

	public void setEntity(ResourceLocation entity)
	{
		this.targetSelector.set("type", entity.toString());
		this.setNode(0, this.targetSelector);
	}
	
	@Nonnull
	public ResourceLocation getEntity()
	{
		return this.targetSelector.<ResourceLocation>get("type");
	}
	
	@Override
	public String getCommandName()
	{
		return "kill";
	}
	
	@Override
	public Syntax getSyntax()
	{
		Syntax syntax = new Syntax();
		
		syntax.addRequired("entity_name", Type.TARGET_SELECTOR);
		
		return syntax;
	}
}
