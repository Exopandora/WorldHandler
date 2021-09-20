package exopandora.worldhandler.builder;

import javax.annotation.Nullable;

import net.minecraft.nbt.Tag;

public interface INBTWritable
{
	@Nullable
	Tag serialize();
}
