package exopandora.worldhandler.builder.component;

import javax.annotation.Nullable;

import net.minecraft.nbt.INBTBase;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IBuilderComponent
{
	@Nullable
	INBTBase serialize();
	String getTag();
}
