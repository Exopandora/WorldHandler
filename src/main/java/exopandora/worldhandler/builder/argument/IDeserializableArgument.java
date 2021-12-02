package exopandora.worldhandler.builder.argument;

import javax.annotation.Nullable;

public interface IDeserializableArgument extends IArgument
{
	void deserialize(@Nullable String string);
}
