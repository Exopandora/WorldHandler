package exopandora.worldhandler.builder;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface ICommandBuilderNBT extends ICommandBuilder
{
	void setNBT(CompoundNBT nbt);
}
