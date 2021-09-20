package exopandora.worldhandler.builder.component.impl;

import exopandora.worldhandler.builder.component.IBuilderComponent;
import exopandora.worldhandler.util.MutableTextComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;

public class ComponentDisplay implements IBuilderComponent 
{
	private MutableTextComponent name = new MutableTextComponent();
	private Component[] lore = new Component[2];
	
	@Override
	public Tag serialize()
	{
		CompoundTag display = new CompoundTag();
		
		if(this.name.getText() != null && !this.name.getText().isEmpty())
		{
			display.putString("Name", Component.Serializer.toJson(this.name));
		}
		
		ListTag lore = new ListTag();
		
		for(int x = 0; x < this.lore.length; x++)
		{
			if(this.lore[x] != null && !this.lore[x].getString().isEmpty())
			{
				lore.add(StringTag.valueOf(Component.Serializer.toJson(this.lore[x])));
			}
		}
		
		if(!lore.isEmpty())
		{
			display.put("Lore", lore);
		}
		
		if(!display.isEmpty())
		{
			return display;
		}
		
		return null;
	}
	
	public void setName(MutableTextComponent name)
	{
		this.name = name;
	}
	
	public MutableTextComponent getName()
	{
		return this.name;
	}
	
	public void setLore(Component[] lore)
	{
		this.lore = lore;
	}
	
	public Component[] getLore()
	{
		return this.lore;
	}
	
	public void setLore1(Component lore)
	{
		this.lore[0] = lore;
	}
	
	public Component getLore1()
	{
		return this.lore[0];
	}
	
	public void setLore2(Component lore)
	{
		this.lore[1] = lore;
	}
	
	public Component getLore2()
	{
		return this.lore[1];
	}

	@Override
	public String getTag()
	{
		return "display";
	}
}
