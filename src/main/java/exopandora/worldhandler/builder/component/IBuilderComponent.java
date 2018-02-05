package exopandora.worldhandler.builder.component;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IBuilderComponent
{
	@Nullable
	NBTBase serialize();
	String getTag();
}
