package exopandora.worldhandler.builder.argument.tag;

import javax.annotation.Nonnull;

import exopandora.worldhandler.util.UserStylableComponent;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;

public class TextTag implements ITagProvider
{
	private final int id;
	private final UserStylableComponent component = new UserStylableComponent();
	
	public TextTag(int id)
	{
		this.id = id;
	}
	
	public UserStylableComponent getComponent()
	{
		return this.component;
	}
	
	@Override
	public String key()
	{
		return "Text" + this.id;
	}
	
	@Nonnull
	@Override
	public Tag value()
	{
		return StringTag.valueOf(Component.Serializer.toJson(this.component));
	}
}
