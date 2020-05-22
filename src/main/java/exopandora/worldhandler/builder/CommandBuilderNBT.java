package exopandora.worldhandler.builder;

import java.util.ArrayList;
import java.util.List;

import exopandora.worldhandler.builder.component.IBuilderComponent;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class CommandBuilderNBT extends CommandBuilder implements ICommandBuilderNBT
{
	private final List<IBuilderComponent> TAG_TO_COMPONENT = new ArrayList<IBuilderComponent>();
	
	@Override
	public String toCommand()
	{
		return this.toCommand(true);
	}
	
	public String toCommand(boolean rebuildNBT)
	{
		if(rebuildNBT)
		{
			this.setNBT(this.buildNBT());
		}
		
		return super.toCommand();
	}
	
	@Override
	public String toActualCommand()
	{
		return this.toActualCommand(true);
	}
	
	public String toActualCommand(boolean rebuildNBT)
	{
		if(rebuildNBT)
		{
			this.setNBT(this.buildNBT());
		}
		
		return super.toActualCommand();
	}
	
	private CompoundNBT buildNBT()
	{
		CompoundNBT nbt = new CompoundNBT();
		
		for(IBuilderComponent component : this.TAG_TO_COMPONENT)
		{
			INBT serialized = component.serialize();
			
			if(component.getTag() != null && serialized != null)
			{
				if(!nbt.contains(component.getTag()))
				{
					nbt.put(component.getTag(), serialized);
				}
			}
		}
		
		if(nbt.isEmpty())
		{
			return null;
		}
		
		return nbt;
	}
	
	public <T extends IBuilderComponent> T registerNBTComponent(T component)
	{
		this.TAG_TO_COMPONENT.add(component);
		return component;
	}
}
