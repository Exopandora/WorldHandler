package exopandora.worldhandler.builder.impl;

import javax.annotation.Nonnull;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.Syntax;
import exopandora.worldhandler.builder.types.TargetSelector;
import exopandora.worldhandler.builder.types.Type;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BuilderButcher extends CommandBuilder
{
	private final TargetSelector targetSelector = new TargetSelector();
	
	public BuilderButcher()
	{
		this("<entity_name>", 0);
	}
	
	public BuilderButcher(ResourceLocation entity, int distance)
	{
		this(entity.toString(), distance);
	}
	
	private BuilderButcher(String entity, int distance)
	{
		this.setEntity(entity);
		this.setDistance(distance);
	}
	
	public void setDistance(int distance)
	{
		this.targetSelector.set("distance", "0.." + distance);
		this.setNode(0, this.targetSelector);
	}
	
	public int getDistance()
	{
		return Integer.parseInt(this.targetSelector.<String>get("distance").substring(3));
	}
	
	private void setEntity(String entity)
	{
		if(entity != null)
		{
			this.targetSelector.set("type", entity);
		}
		
		this.setNode(0, this.targetSelector);
	}
	
	public void setEntity(ResourceLocation entity)
	{
		this.setEntity(entity.toString());
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
