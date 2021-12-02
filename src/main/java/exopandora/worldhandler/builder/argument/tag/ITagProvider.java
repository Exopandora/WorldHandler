package exopandora.worldhandler.builder.argument.tag;

import javax.annotation.Nullable;

import net.minecraft.nbt.Tag;

public interface ITagProvider
{
	@Nullable
	String key();
	
	@Nullable
	Tag value();
}
