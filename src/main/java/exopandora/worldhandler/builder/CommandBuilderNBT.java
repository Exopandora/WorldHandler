package exopandora.worldhandler.builder;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.component.IBuilderComponent;
import exopandora.worldhandler.util.NBTHelper;
import net.minecraft.nbt.CompoundTag;

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
	
	@Nullable
	protected CompoundTag buildNBT()
	{
		CompoundTag nbt = new CompoundTag();
		
		for(IBuilderComponent component : this.TAG_TO_COMPONENT)
		{
			NBTHelper.append(nbt, component);
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
