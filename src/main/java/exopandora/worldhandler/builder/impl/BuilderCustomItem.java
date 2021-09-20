package exopandora.worldhandler.builder.impl;

import java.util.Set;

import exopandora.worldhandler.builder.component.impl.ComponentAttributeItem;
import exopandora.worldhandler.builder.component.impl.ComponentDisplay;
import exopandora.worldhandler.builder.component.impl.ComponentEnchantment;
import exopandora.worldhandler.util.MutableTextComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.enchantment.Enchantment;

public class BuilderCustomItem extends BuilderGive
{
	private final ComponentAttributeItem attribute;
	private final ComponentDisplay display;
	private final ComponentEnchantment enchantment;
	
	public BuilderCustomItem()
	{
		this(null, null);
	}
	
	public BuilderCustomItem(String player, ResourceLocation item)
	{
		super(player, item);
		this.attribute = this.registerNBTComponent(new ComponentAttributeItem());
		this.display = this.registerNBTComponent(new ComponentDisplay());
		this.enchantment = this.registerNBTComponent(new ComponentEnchantment());
	}
	
	public void setEnchantment(Enchantment enchantment, short level)
	{
		this.enchantment.setLevel(enchantment, level);
	}
	
	public short getEnchantmentLevel(Enchantment enchantment)
	{
		return this.enchantment.getLevel(enchantment);
	}
	
	public Set<Enchantment> getEnchantments()
	{
		return this.enchantment.getEnchantments();
	}
	
	public void setAttribute(Attribute attribute, double ammount)
	{
		this.attribute.set(attribute, ammount);
	}
	
	public void removeAttribute(Attribute attribute)
	{
		this.attribute.remove(attribute);
	}
	
	public double getAttributeAmmount(Attribute attribute)
	{
		return this.attribute.getAmmount(attribute);
	}
	
	public Set<Attribute> getAttributes()
	{
		return this.attribute.getAttributes();
	}
	
	public void setName(MutableTextComponent name)
	{
		this.display.setName(name);
	}
	
	public MutableTextComponent getName()
	{
		return this.display.getName();
	}
	
	public void setLore1(Component lore)
	{
		this.display.setLore1(lore);
	}
	
	public Component getLore1()
	{
		return this.display.getLore1();
	}
	
	public void setLore2(Component lore)
	{
		this.display.setLore2(lore);
	}
	
	public Component getLore2()
	{
		return this.display.getLore2();
	}
}
