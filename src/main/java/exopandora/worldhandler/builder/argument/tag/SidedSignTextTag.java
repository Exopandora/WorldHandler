package exopandora.worldhandler.builder.argument.tag;

import java.util.Arrays;

import javax.annotation.Nonnull;

import exopandora.worldhandler.util.UserStylableComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;

public class SidedSignTextTag implements ITagProvider
{
	private boolean isFront;
	private final UserStylableComponent[] lines = new UserStylableComponent[]
	{
		new UserStylableComponent(),
		new UserStylableComponent(),
		new UserStylableComponent(),
		new UserStylableComponent()
	};
	
	@Override
	public String key()
	{
		return this.isFront ? "front_text" : "back_text";
	}
	
	@Nonnull
	@Override
	public Tag value()
	{
		ListTag messages = new ListTag();
		
		for(UserStylableComponent text : this.lines)
		{
			messages.add(StringTag.valueOf(Component.Serializer.toJson(text)));
		}
		
		CompoundTag tag = new CompoundTag();
		tag.put("messages", messages);
		return tag;
	}
	
	public UserStylableComponent getLine(int index)
	{
		return this.lines[index];
	}
	
	public boolean isStyled()
	{
		return Arrays.stream(this.lines).anyMatch(UserStylableComponent::isStyled);
	}
	
	public void setIsFront(boolean isFront)
	{
		this.isFront = isFront;
	}
}
