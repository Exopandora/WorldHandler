package exopandora.worldhandler.builder;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface ICommandBuilderNBT extends ICommandBuilder
{
	void setNBT(NBTTagCompound nbt);
}
