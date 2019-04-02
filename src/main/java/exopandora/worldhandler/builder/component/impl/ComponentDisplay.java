package exopandora.worldhandler.builder.component.impl;

import exopandora.worldhandler.builder.component.IBuilderComponent;
import exopandora.worldhandler.format.text.ColoredString;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ComponentDisplay implements IBuilderComponent 
{
	private ColoredString name = new ColoredString();
	private String[] lore = new String[2];
	
	@Override
	public INBTBase serialize()
	{
		NBTTagCompound display = new NBTTagCompound();
		
		String name = this.name.getText();
		
		if(name != null && !name.isEmpty())
		{
			display.setString("Name", ITextComponent.Serializer.toJson(new TextComponentString(this.name.toString())));
		}
		
		NBTTagList lore = new NBTTagList();
		
		for(int x = 0; x < this.lore.length; x++)
		{
			if(this.lore[x] != null && !this.lore[x].isEmpty())
			{
				lore.add(new NBTTagString(this.lore[x]));
			}
		}
		
		if(!lore.isEmpty())
		{
			display.setTag("Lore", lore);
		}
		
		if(!display.isEmpty())
		{
			return display;
		}
		
		return null;
	}
	
	public void setName(ColoredString name)
	{
		this.name = name;
	}
	
	public ColoredString getName()
	{
		return this.name;
	}
	
	public void setLore(String[] lore)
	{
		this.lore = lore;
	}
	
	public String[] getLore()
	{
		return this.lore;
	}
	
	public void setLore1(String lore)
	{
		this.lore[0] = lore;
	}
	
	public String getLore1()
	{
		return this.lore[0];
	}
	
	public void setLore2(String lore)
	{
		this.lore[1] = lore;
	}
	
	public String getLore2()
	{
		return this.lore[1];
	}

	@Override
	public String getTag()
	{
		return "display";
	}
}
