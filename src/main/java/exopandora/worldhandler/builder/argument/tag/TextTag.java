package exopandora.worldhandler.builder.argument.tag;

import exopandora.worldhandler.util.SignText;
import net.minecraft.nbt.Tag;

public class TextTag implements ITagProvider
{
	private final int id;
	private final SignText text = new SignText();
	
	public TextTag(int id)
	{
		this.id = id;
	}
	
	public SignText getText()
	{
		return this.text;
	}
	
	@Override
	public String key()
	{
		return "Text" + this.id;
	}
	
	@Override
	public Tag value()
	{
		return this.text.serialize();
	}
}
