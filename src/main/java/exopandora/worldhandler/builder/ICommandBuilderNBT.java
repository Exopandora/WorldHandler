package exopandora.worldhandler.builder;

import net.minecraft.nbt.CompoundTag;

public interface ICommandBuilderNBT extends ICommandBuilder
{
	void setNBT(CompoundTag nbt);
}
