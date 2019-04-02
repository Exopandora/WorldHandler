package exopandora.worldhandler.builder;

import java.util.ArrayList;
import java.util.List;

import exopandora.worldhandler.builder.component.IBuilderComponent;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
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
	
	private NBTTagCompound buildNBT()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		
		for(IBuilderComponent component : this.TAG_TO_COMPONENT)
		{
			INBTBase serialized = component.serialize();
			
			if(serialized != null)
			{
				if(!nbt.hasKey(component.getTag()))
				{
					nbt.setTag(component.getTag(), serialized);
				}
			}
		}
		
		if(nbt.isEmpty())
		{
			return null;
		}
		
		return nbt;
	}
	
	protected <T extends IBuilderComponent> T registerNBTComponent(T component, String id)
	{
		this.TAG_TO_COMPONENT.add(component);
		return component;
	}
	
	protected <T extends IBuilderComponent> T registerNBTComponent(T component)
	{
		return this.registerNBTComponent(component, component.getTag());
	}
}
