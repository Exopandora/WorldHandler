package exopandora.worldhandler.gui.content.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.lwjgl.opengl.GL11;

import com.google.common.base.Predicates;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderSummon;
import exopandora.worldhandler.builder.impl.abstr.EnumAttributes;
import exopandora.worldhandler.builder.impl.abstr.EnumAttributes.Applyable;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.button.GuiButtonBase;
import exopandora.worldhandler.gui.button.GuiButtonItem;
import exopandora.worldhandler.gui.button.GuiSlider;
import exopandora.worldhandler.gui.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.content.element.impl.ElementPageList;
import exopandora.worldhandler.gui.logic.ILogicPageList;
import exopandora.worldhandler.gui.logic.LogicSliderAttribute;
import exopandora.worldhandler.gui.logic.LogicSliderSimple;
import exopandora.worldhandler.helper.ActionHelper;
import exopandora.worldhandler.helper.CommandHelper;
import exopandora.worldhandler.util.ActionHandler;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Items;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

@OnlyIn(Dist.CLIENT)
public class ContentSummon extends Content
{
	private GuiTextFieldTooltip mobField;
	private GuiTextFieldTooltip customNameField;
	private GuiTextFieldTooltip passengerField;
	
	private int potionPage = 0;
	private int equipmentPage = 0;
	
	private String page = "main";
	private String mob;
	private String name;
	private String passenger;
	
	private final BuilderSummon builderSummon = new BuilderSummon();
	
	private final List<EnumAttributes> attributes = Stream.concat(EnumAttributes.getAttributesFor(Applyable.BOTH).stream(), EnumAttributes.getAttributesFor(Applyable.MOB).stream()).collect(Collectors.toList());
	
	@Override
	public ICommandBuilder getCommandBuilder()
	{
		return this.builderSummon;
	}
	
	@Override
	public void init(Container container)
	{
		for(EnumAttributes attribute : this.builderSummon.getAttributes())
		{
			double ammount = this.builderSummon.getAttributeAmmount(attribute);
			
			if(ammount > Config.getSliders().getMaxSummonAttributes())
			{
				this.builderSummon.setAttribute(attribute, Config.getSliders().getMaxSummonAttributes());
			}
		}
		
		for(Effect potion : this.builderSummon.getEffects())
		{
			byte amplifier = this.builderSummon.getAmplifier(potion);
			
			if(amplifier > Config.getSliders().getMaxSummonPotionAmplifier())
			{
				this.builderSummon.setAmplifier(potion, (byte) Config.getSliders().getMaxSummonPotionAmplifier());
			}
			
			int minutes = this.builderSummon.getMinutes(potion);
			
			if(minutes > Config.getSliders().getMaxSummonPotionMinutes())
			{
				this.builderSummon.setMinutes(potion, (int) Config.getSliders().getMaxSummonPotionMinutes());
			}
		}
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.mobField = new GuiTextFieldTooltip(x + 118, y, 114, 20, I18n.format("gui.worldhandler.entities.summon.start.mob_id") + " (" + I18n.format("gui.worldhandler.generic.name") + ")");
		this.mobField.setValidator(Predicates.notNull());
		this.mobField.setText(this.mob);
		this.mobField.func_212954_a(text ->
		{
			this.mob = text;
			this.builderSummon.setEntity(this.mob);
			container.initButtons();
		});
		
		this.customNameField = new GuiTextFieldTooltip(x + 118, y + 24, 114, 20, I18n.format("gui.worldhandler.entities.summon.start.custom_name"));
		this.customNameField.setValidator(Predicates.notNull());
		this.customNameField.setText(this.name);
		this.customNameField.func_212954_a(text ->
		{
			this.name = text;
			this.builderSummon.setCustomName(this.name);
			container.initButtons();
		});
		
		this.passengerField = new GuiTextFieldTooltip(x + 118, y + 48, 114, 20, I18n.format("gui.worldhandler.entities.summon.start.passenger_mob_id"));
		this.passengerField.setValidator(Predicates.notNull());
		this.passengerField.setText(this.passenger);
		this.passengerField.func_212954_a(text ->
		{
			this.passenger = this.passengerField.getText();
			this.builderSummon.setPassenger(this.passenger);
			container.initButtons();
		});
		
		if(this.page.equals("attributes"))
		{
			ElementPageList<EnumAttributes> attributes = new ElementPageList<EnumAttributes>(x + 118, y, this.attributes, 114, 20, 3, container, new ILogicPageList<EnumAttributes>()
			{
				@Override
				public String translate(EnumAttributes item)
				{
					return item.getTranslation();
				}
				
				@Override
				public String toTooltip(EnumAttributes item)
				{
					return item.getAttribute();
				}
				
				@Override
				public void onClick(EnumAttributes item)
				{
					
				}
				
				@Override
				public GuiButtonBase onRegister(int x, int y, int width, int height, String text, EnumAttributes item, ActionHandler actionHandler)
				{
					return new GuiSlider(x, y, width, height, -Config.getSliders().getMaxSummonAttributes(), Config.getSliders().getMaxSummonAttributes(), 0, container, new LogicSliderAttribute(item, text, value ->
					{
						ContentSummon.this.builderSummon.setAttribute(item, value);
					}));
				}
				
				@Override
				public boolean doDisable()
				{
					return false;
				}
				
				@Override
				public String getId()
				{
					return "attributes";
				}
			});
			
			container.add(attributes);
		}
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		GuiButtonBase button1;
		GuiButtonBase button2;
		GuiButtonBase button3;
		GuiButtonBase button4;
		GuiButtonBase button5;
		GuiButtonBase button6;
		GuiButtonBase button7;
		GuiButtonItem button8;
		GuiButtonItem button9;
		GuiButtonItem button10;
		GuiButtonItem button11;
		GuiButtonItem button12;
		GuiButtonBase button13;
		GuiButtonItem button14;
		GuiButtonItem button15;
		GuiButtonItem button16;
		GuiButtonItem button17;
		GuiButtonItem button18;
		GuiButtonBase button19;
		GuiButtonItem button20;
		GuiButtonItem button21;
		GuiButtonItem button22;
		GuiButtonItem button23;
		GuiButtonItem button24;
		GuiButtonBase button25;
		
		container.add(new GuiButtonBase(x, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.add(button4 = new GuiButtonBase(x, y, 114, 20, I18n.format("gui.worldhandler.entities.summon.start"), () ->
		{
			this.page = "main";
			container.init();
		}));
		container.add(button5 = new GuiButtonBase(x, y + 24, 114, 20, I18n.format("gui.worldhandler.entities.summon.potion_effects"), () ->
		{
			this.page = "potionEffects";
			container.init();
		}));
		container.add(button6 = new GuiButtonBase(x, y + 48, 114, 20, I18n.format("gui.worldhandler.entities.summon.attributes"), () ->
		{
			this.page = "attributes";
			container.init();
		}));
		container.add(button7 = new GuiButtonBase(x, y + 72, 114, 20, I18n.format("gui.worldhandler.entities.summon.equipment"), () ->
		{
			this.page = "equipment";
			container.init();
		}));
		
		if(this.page.equals("main"))
		{
			button4.active = false;
			
			container.add(this.mobField);
			container.add(this.customNameField);
			container.add(this.passengerField);
			
			if(!this.builderSummon.needsCommandBlock() && !this.builderSummon.getCustomName().isSpecial())
			{
				container.add(button3 = new GuiButtonBase(x + 118, y + 72, 114, 20, I18n.format("gui.worldhandler.title.entities.summon"), this::send));
			}
			else
			{
				container.add(button3 = new GuiButtonBase(x + 118, y + 72, 114, 20, I18n.format("gui.worldhandler.actions.place_command_block"), this::send));
			}
			
			button3.active = ForgeRegistries.ENTITIES.containsKey(this.builderSummon.getEntity());
		}
		else if(this.page.equals("potionEffects"))
		{
			button5.active = false;
			
			container.add(button1 = new GuiButtonBase(x + 118, y + 72, 56, 20, "<", () ->
			{
				this.potionPage--;
				container.init();
			}));
			container.add(button2 = new GuiButtonBase(x + 118 + 60, y + 72, 55, 20, ">", () ->
			{
				this.potionPage++;
				container.init();
			}));
			
			int count = 0;
			
			for(ResourceLocation location : this.getSortedPotionList())
			{
				Effect potion = ForgeRegistries.POTIONS.getValue(location);
				
				if(!potion.equals(Effects.INSTANT_DAMAGE) && !potion.equals(Effects.INSTANT_HEALTH))
				{
					if(this.potionPage == 0)
					{
						button1.active = false;
					}
					
					if(this.potionPage == ForgeRegistries.POTIONS.getKeys().size() - 3)
					{
						button2.active = false;
					}
					
					if(count == this.potionPage)
					{
						container.add(new GuiSlider(x + 118, y, 114, 20, 0, Config.getSliders().getMaxSummonPotionAmplifier(), 0, container, new LogicSliderSimple("amplifier" + potion.getRegistryName(), I18n.format(potion.getName()), value ->
						{
							this.builderSummon.setAmplifier(potion, value.byteValue());
						})));
						container.add(new GuiSlider(x + 118, y + 24, 114, 20, 0, Config.getSliders().getMaxSummonPotionMinutes(), 0, container, new LogicSliderSimple("duration" + potion.getRegistryName(), I18n.format("gui.worldhandler.potion.time.minutes"), value ->
						{
							this.builderSummon.setMinutes(potion, value);
						})));
						container.add(new GuiButtonBase(x + 118, y + 48, 114, 20, I18n.format("gui.worldhandler.potions.effect.particles", this.builderSummon.getShowParticles(potion) ? I18n.format("gui.worldhandler.generic.on") : I18n.format("gui.worldhandler.generic.off")), () ->
						{
							this.builderSummon.setShowParticles(potion, !this.builderSummon.getShowParticles(potion));
							container.init();
						}));
						break;
					}
					
					count++;
				}
			}
		}
		else if(this.page.equals("attributes"))
		{
			button6.active = false;
		}
		else if(this.page.equals("equipment"))
		{
			container.add(button1 = new GuiButtonBase(x + 118, y + 72, 56, 20, "<", () ->
			{
				this.equipmentPage--;
				container.init();
			}));
			container.add(button2 = new GuiButtonBase(x + 118 + 60, y + 72, 54, 20, ">", () ->
			{
				this.equipmentPage++;
				container.init();
			}));
			
			if(this.equipmentPage == 0)
			{
				button1.active = false;
				
				container.add(button8 = new GuiButtonItem(x + 118, y, 18, 20, Items.LEATHER_HELMET, () ->
				{
					this.builderSummon.setArmorItem(3, Items.LEATHER_HELMET);
					container.init();
				}));
				container.add(button9 = new GuiButtonItem(x + 118 + 20 - 1, y, 18, 20, Items.IRON_HELMET, () ->
				{
					this.builderSummon.setArmorItem(3, Items.IRON_HELMET);
					container.init();
				}));
				container.add(button10 = new GuiButtonItem(x + 118 + 20 * 2 - 2, y, 18, 20, Items.CHAINMAIL_HELMET, () ->
				{
					this.builderSummon.setArmorItem(3, Items.CHAINMAIL_HELMET);
					container.init();
				}));
				container.add(button11 = new GuiButtonItem(x + 118 + 20 * 3 - 3, y, 18, 20, Items.GOLDEN_HELMET, () ->
				{
					this.builderSummon.setArmorItem(3, Items.GOLDEN_HELMET);
					container.init();
				}));
				container.add(button12 = new GuiButtonItem(x + 118 + 20 * 4 - 4, y, 18, 20, Items.DIAMOND_HELMET, () ->
				{
					this.builderSummon.setArmorItem(3, Items.DIAMOND_HELMET);
					container.init();
				}));
				container.add(button13 = new GuiButtonBase(x + 118 + 20 * 5 - 5, y, 20, 20, null, () ->
				{
					this.builderSummon.setArmorItem(3, Blocks.AIR);
					container.init();
				}));
				
				container.add(button14 = new GuiButtonItem(x + 118, y + 24, 18, 20, Items.LEATHER_CHESTPLATE, () ->
				{
					this.builderSummon.setArmorItem(2, Items.LEATHER_CHESTPLATE);
					container.init();
				}));
				container.add(button15 = new GuiButtonItem(x + 118 + 20 - 1, y + 24, 18, 20, Items.IRON_CHESTPLATE, () ->
				{
					this.builderSummon.setArmorItem(2, Items.IRON_CHESTPLATE);
					container.init();
				}));
				container.add(button16 = new GuiButtonItem(x + 118 + 20 * 2 - 2, y + 24, 18, 20, Items.CHAINMAIL_CHESTPLATE, () ->
				{
					this.builderSummon.setArmorItem(2, Items.CHAINMAIL_CHESTPLATE);
					container.init();
				}));
				container.add(button17 = new GuiButtonItem(x + 118 + 20 * 3 - 3, y + 24, 18, 20, Items.GOLDEN_CHESTPLATE, () ->
				{
					this.builderSummon.setArmorItem(2, Items.GOLDEN_CHESTPLATE);
					container.init();
				}));
				container.add(button18 = new GuiButtonItem(x + 118 + 20 * 4 - 4, y + 24, 18, 20, Items.DIAMOND_CHESTPLATE, () ->
				{
					this.builderSummon.setArmorItem(2, Items.DIAMOND_CHESTPLATE);
					container.init();
				}));
				container.add(button19 = new GuiButtonBase(x + 118 + 20 * 5 - 5, y + 24, 20, 20, null, () ->
				{
					this.builderSummon.setArmorItem(2, Blocks.AIR);
					container.init();
				}));
				
				container.add(button20 = new GuiButtonItem(x + 118, y + 48, 18, 20, Items.LEATHER_LEGGINGS, () ->
				{
					this.builderSummon.setArmorItem(1, Items.LEATHER_LEGGINGS);
					container.init();
				}));
				container.add(button21 = new GuiButtonItem(x + 118 + 20 - 1, y + 48, 18, 20, Items.IRON_LEGGINGS, () ->
				{
					this.builderSummon.setArmorItem(1, Items.IRON_LEGGINGS);
					container.init();
				}));
				container.add(button22 = new GuiButtonItem(x + 118 + 20 * 2 - 2, y + 48, 18, 20, Items.CHAINMAIL_LEGGINGS, () ->
				{
					this.builderSummon.setArmorItem(1, Items.CHAINMAIL_LEGGINGS);
					container.init();
				}));
				container.add(button23 = new GuiButtonItem(x + 118 + 20 * 3 - 3, y + 48, 18, 20, Items.GOLDEN_LEGGINGS, () ->
				{
					this.builderSummon.setArmorItem(1, Items.GOLDEN_LEGGINGS);
					container.init();
				}));
				container.add(button24 = new GuiButtonItem(x + 118 + 20 * 4 - 4, y + 48, 18, 20, Items.DIAMOND_LEGGINGS, () ->
				{
					this.builderSummon.setArmorItem(1, Items.DIAMOND_LEGGINGS);
					container.init();
				}));
				container.add(button25 = new GuiButtonBase(x + 118 + 20 * 5 - 5, y + 48, 20, 20, null, () ->
				{
					this.builderSummon.setArmorItem(1, Blocks.AIR);
					container.init();
				}));
				
				button8.active = !this.builderSummon.getArmorItem(3).equals(Items.LEATHER_HELMET.getRegistryName());
				button9.active = !this.builderSummon.getArmorItem(3).equals(Items.IRON_HELMET.getRegistryName());
				button10.active = !this.builderSummon.getArmorItem(3).equals(Items.CHAINMAIL_HELMET.getRegistryName());
				button11.active = !this.builderSummon.getArmorItem(3).equals(Items.GOLDEN_HELMET.getRegistryName());
				button12.active = !this.builderSummon.getArmorItem(3).equals(Items.DIAMOND_HELMET.getRegistryName());
				button13.active = !this.builderSummon.getArmorItem(3).equals(Blocks.AIR.getRegistryName());

				button14.active = !this.builderSummon.getArmorItem(2).equals(Items.LEATHER_CHESTPLATE.getRegistryName());
				button15.active = !this.builderSummon.getArmorItem(2).equals(Items.IRON_CHESTPLATE.getRegistryName());
				button16.active = !this.builderSummon.getArmorItem(2).equals(Items.CHAINMAIL_CHESTPLATE.getRegistryName());
				button17.active = !this.builderSummon.getArmorItem(2).equals(Items.GOLDEN_CHESTPLATE.getRegistryName());
				button18.active = !this.builderSummon.getArmorItem(2).equals(Items.DIAMOND_CHESTPLATE.getRegistryName());
				button19.active = !this.builderSummon.getArmorItem(2).equals(Blocks.AIR.getRegistryName());
				
				button20.active = !this.builderSummon.getArmorItem(1).equals(Items.LEATHER_LEGGINGS.getRegistryName());
				button21.active = !this.builderSummon.getArmorItem(1).equals(Items.IRON_LEGGINGS.getRegistryName());
				button22.active = !this.builderSummon.getArmorItem(1).equals(Items.CHAINMAIL_LEGGINGS.getRegistryName());
				button23.active = !this.builderSummon.getArmorItem(1).equals(Items.GOLDEN_LEGGINGS.getRegistryName());
				button24.active = !this.builderSummon.getArmorItem(1).equals(Items.DIAMOND_LEGGINGS.getRegistryName());
				button25.active = !this.builderSummon.getArmorItem(1).equals(Blocks.AIR.getRegistryName());
			}
			else if(this.equipmentPage == 1)
			{
				button2.active = false;

				container.add(button8 = new GuiButtonItem(x + 118, y, 18, 20, Items.LEATHER_BOOTS, () ->
				{
					this.builderSummon.setArmorItem(0, Items.LEATHER_BOOTS);
					container.init();
				}));
				container.add(button9 = new GuiButtonItem(x + 118 + 20 - 1, y, 18, 20, Items.IRON_BOOTS, () ->
				{
					this.builderSummon.setArmorItem(0, Items.IRON_BOOTS);
					container.init();
				}));
				container.add(button10 = new GuiButtonItem(x + 118 + 20 * 2 - 2, y, 18, 20, Items.CHAINMAIL_BOOTS, () ->
				{
					this.builderSummon.setArmorItem(0, Items.CHAINMAIL_BOOTS);
					container.init();
				}));
				container.add(button11 = new GuiButtonItem(x + 118 + 20 * 3 - 3, y, 18, 20, Items.GOLDEN_BOOTS, () ->
				{
					this.builderSummon.setArmorItem(0, Items.GOLDEN_BOOTS);
					container.init();
				}));
				container.add(button12 = new GuiButtonItem(x + 118 + 20 * 4 - 4, y, 18, 20, Items.DIAMOND_BOOTS, () ->
				{
					this.builderSummon.setArmorItem(0, Items.DIAMOND_BOOTS);
					container.init();
				}));
				container.add(button13 = new GuiButtonBase(x + 118 + 20 * 5 - 5, y, 20, 20, null, () ->
				{
					this.builderSummon.setArmorItem(0, Blocks.AIR);
					container.init();
				}));
				
				container.add(button14 = new GuiButtonItem(x + 118, y + 24, 18, 20, Items.WOODEN_SWORD, () ->
				{
					this.builderSummon.setHandItem(0, Items.WOODEN_SWORD);
					container.init();
				}));
				container.add(button15 = new GuiButtonItem(x + 118 + 20 - 1, y + 24, 18, 20, Items.STONE_SWORD, () ->
				{
					this.builderSummon.setHandItem(0, Items.STONE_SWORD);
					container.init();
				}));
				container.add(button16 = new GuiButtonItem(x + 118 + 20 * 2 - 2, y + 24, 18, 20, Items.IRON_SWORD, () ->
				{
					this.builderSummon.setHandItem(0, Items.IRON_SWORD);
					container.init();
				}));
				container.add(button17 = new GuiButtonItem(x + 118 + 20 * 3 - 3, y + 24, 18, 20, Items.GOLDEN_SWORD, () ->
				{
					this.builderSummon.setHandItem(0, Items.GOLDEN_SWORD);
					container.init();
				}));
				container.add(button18 = new GuiButtonItem(x + 118 + 20 * 4 - 4, y + 24, 18, 20, Items.DIAMOND_SWORD, () ->
				{
					this.builderSummon.setHandItem(0, Items.DIAMOND_SWORD);
					container.init();
				}));
				container.add(button19 = new GuiButtonBase(x + 118 + 20 * 5 - 5, y + 24, 20, 20, null, () ->
				{
					this.builderSummon.setHandItem(0, Blocks.AIR);
					container.init();
				}));
				
				container.add(button20 = new GuiButtonItem(x + 118, y + 48, 18, 20, Items.WOODEN_SWORD, () ->
				{
					this.builderSummon.setHandItem(1, Items.WOODEN_SWORD);
					container.init();
				}));
				container.add(button21 = new GuiButtonItem(x + 118 + 20 - 1, y + 48, 18, 20, Items.STONE_SWORD, () ->
				{
					this.builderSummon.setHandItem(1, Items.STONE_SWORD);
					container.init();
				}));
				container.add(button22 = new GuiButtonItem(x + 118 + 20 * 2 - 2, y + 48, 18, 20, Items.IRON_SWORD, () ->
				{
					this.builderSummon.setHandItem(1, Items.IRON_SWORD);
					container.init();
				}));
				container.add(button23 = new GuiButtonItem(x + 118 + 20 * 3 - 3, y + 48, 18, 20, Items.GOLDEN_SWORD, () ->
				{
					this.builderSummon.setHandItem(1, Items.GOLDEN_SWORD);
					container.init();
				}));
				container.add(button24 = new GuiButtonItem(x + 118 + 20 * 4 - 4, y + 48, 18, 20, Items.DIAMOND_SWORD, () ->
				{
					this.builderSummon.setHandItem(1, Items.DIAMOND_SWORD);
					container.init();
				}));
				container.add(button25 = new GuiButtonBase(x + 118 + 20 * 5 - 5, y + 48, 20, 20, null, () ->
				{
					this.builderSummon.setHandItem(1, Blocks.AIR);
					container.init();
				}));
				
				button8.active = !this.builderSummon.getArmorItem(0).equals(Items.LEATHER_BOOTS.getRegistryName());
				button9.active = !this.builderSummon.getArmorItem(0).equals(Items.IRON_BOOTS.getRegistryName());
				button10.active = !this.builderSummon.getArmorItem(0).equals(Items.CHAINMAIL_BOOTS.getRegistryName());
				button11.active = !this.builderSummon.getArmorItem(0).equals(Items.GOLDEN_BOOTS.getRegistryName());
				button12.active = !this.builderSummon.getArmorItem(0).equals(Items.DIAMOND_BOOTS.getRegistryName());
				button13.active = !this.builderSummon.getArmorItem(0).equals(Blocks.AIR.getRegistryName());
				
				button14.active = !this.builderSummon.getHandItem(0).equals(Items.WOODEN_SWORD.getRegistryName());
				button15.active = !this.builderSummon.getHandItem(0).equals(Items.STONE_SWORD.getRegistryName());
				button16.active = !this.builderSummon.getHandItem(0).equals(Items.IRON_SWORD.getRegistryName());
				button17.active = !this.builderSummon.getHandItem(0).equals(Items.GOLDEN_SWORD.getRegistryName());
				button18.active = !this.builderSummon.getHandItem(0).equals(Items.DIAMOND_SWORD.getRegistryName());
				button19.active = !this.builderSummon.getHandItem(0).equals(Blocks.AIR.getRegistryName());
				
				button20.active = !this.builderSummon.getHandItem(1).equals(Items.WOODEN_SWORD.getRegistryName());
				button21.active = !this.builderSummon.getHandItem(1).equals(Items.STONE_SWORD.getRegistryName());
				button22.active = !this.builderSummon.getHandItem(1).equals(Items.IRON_SWORD.getRegistryName());
				button23.active = !this.builderSummon.getHandItem(1).equals(Items.GOLDEN_SWORD.getRegistryName());
				button24.active = !this.builderSummon.getHandItem(1).equals(Items.DIAMOND_SWORD.getRegistryName());
				button25.active = !this.builderSummon.getHandItem(1).equals(Blocks.AIR.getRegistryName());
			}
			
			button7.active = false;
		}
	}
	
	private void send()
	{
		CommandHelper.sendCommand(this.builderSummon, this.builderSummon.getCustomName().isSpecial());
	}
	
	@Override
	public void tick(Container container)
	{
		if(this.page.equals("main"))
		{
			this.mobField.tick();
			this.customNameField.tick();
			this.passengerField.tick();
		}
	}
	
	@Override
	public void drawScreen(Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		if(this.page.equals("main"))
		{
			this.mobField.renderButton(mouseX, mouseY, partialTicks);
			this.customNameField.renderButton(mouseX, mouseY, partialTicks);
			this.passengerField.renderButton(mouseX, mouseY, partialTicks);
		}
		else if(this.page.equals("equipment"))
		{
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		 	Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/beacon.png"));
		 	
		 	for(int row = 0; row < 3; row++)
		 	{
		 		container.blit(x + 116 + 99, y + 2 + 24 * row, 112, 221, 16, 16);
		 	}
		}
	}
	
	private List<ResourceLocation> getSortedPotionList()
	{
		List<ResourceLocation> potions = new ArrayList<ResourceLocation>(ForgeRegistries.POTIONS.getKeys());
		potions.sort((a, b) -> I18n.format(ForgeRegistries.POTIONS.getValue(a).getName()).compareTo(I18n.format(ForgeRegistries.POTIONS.getValue(b).getName())));
		
		return potions;
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.ENTITIES;
	}
	
	@Override
	public String getTitle()
	{
		return I18n.format("gui.worldhandler.title.entities.summon");
	}
	
	@Override
	public String getTabTitle()
	{
		return I18n.format("gui.worldhandler.tab.entities.summon");
	}
	
	@Override
	public String[] getHeadline()
	{
		String[] headline = new String[2];
		
		if(this.page.equals("potionEffects"))
		{
			headline[1] = (this.potionPage + 1) + "/" + (ForgeRegistries.POTIONS.getKeys().size() - 2);
		}
		else if(this.page.equals("equipment"))
		{
			headline[1] = (this.equipmentPage + 1) + "/2";
		}
		
		return headline;
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.SUMMON;
	}
}
