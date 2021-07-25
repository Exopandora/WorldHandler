package exopandora.worldhandler.builder;

import javax.annotation.Nullable;

import net.minecraft.nbt.Tag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface INBTWritable
{
	@Nullable
	Tag serialize();
}
