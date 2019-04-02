package exopandora.worldhandler.builder;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface ICommandBuilderNBT extends ICommandBuilder
{
	void setNBT(NBTTagCompound nbt);
}
