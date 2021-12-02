package exopandora.worldhandler.builder.argument;

import javax.annotation.Nullable;

public interface IArgument
{
	@Nullable
	String serialize();
	
	boolean isDefault();
	
	default boolean hasValue()
	{
		return this.serialize() != null;
	}
}
