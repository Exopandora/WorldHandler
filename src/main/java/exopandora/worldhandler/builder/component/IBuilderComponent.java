package exopandora.worldhandler.builder.component;

import javax.annotation.Nullable;

import net.minecraft.nbt.INBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IBuilderComponent
{
	@Nullable
	INBT serialize();
	String getTag();
}
