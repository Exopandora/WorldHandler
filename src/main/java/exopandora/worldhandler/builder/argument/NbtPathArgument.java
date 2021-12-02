package exopandora.worldhandler.builder.argument;

import javax.annotation.Nullable;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.commands.arguments.NbtPathArgument.NbtPath;

public class NbtPathArgument implements IDeserializableArgument
{
	private NbtPath path;
	
	protected NbtPathArgument()
	{
		super();
	}
	
	@Override
	public void deserialize(@Nullable String string)
	{
		if(string != null)
		{
			try
			{
				this.path = net.minecraft.commands.arguments.NbtPathArgument.nbtPath().parse(new StringReader(string));
			}
			catch(CommandSyntaxException e)
			{
				this.path = null;
			}
		}
		else
		{
			this.path = null;
		}
	}
	
	@Nullable
	public NbtPath getPath()
	{
		return this.path;
	}
	
	@Override
	@Nullable
	public String serialize()
	{
		if(this.path == null)
		{
			return null;
		}
		
		return this.path.toString();
	}
	
	@Override
	public boolean isDefault()
	{
		return this.path == null;
	}
}
