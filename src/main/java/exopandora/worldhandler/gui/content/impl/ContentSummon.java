package exopandora.worldhandler.gui.content.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.base.Predicates;
import com.mojang.blaze3d.matrix.MatrixStack;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.component.impl.ComponentAttribute;
import exopandora.worldhandler.builder.impl.BuilderSummon;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.button.EnumIcon;
import exopandora.worldhandler.gui.button.GuiButtonBase;
import exopandora.worldhandler.gui.button.GuiButtonIcon;
import exopandora.worldhandler.gui.button.GuiButtonItem;
import exopandora.worldhandler.gui.button.GuiSlider;
import exopandora.worldhandler.gui.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.button.LogicSliderAttribute;
import exopandora.worldhandler.gui.button.LogicSliderSimple;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.menu.impl.ILogicPageList;
import exopandora.worldhandler.gui.menu.impl.MenuPageList;
import exopandora.worldhandler.util.ActionHandler;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.CommandHelper;
import exopandora.worldhandler.util.RenderUtils;
import exopandora.worldhandler.util.TextUtils;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.item.Items;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
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
	
	private Page page = Page.START;
	
	private String mob;
	private String name;
	private String passenger;
	
	private final BuilderSummon builderSummon = new BuilderSummon();
	
	private final ResourceLocation[] helmets =
	{
			Blocks.AIR.getRegistryName(),
			Items.LEATHER_HELMET.getRegistryName(),
			Items.IRON_HELMET.getRegistryName(),
			Items.CHAINMAIL_HELMET.getRegistryName(),
			Items.GOLDEN_HELMET.getRegistryName(),
			Items.DIAMOND_HELMET.getRegistryName(),
			Items.field_234763_ls_.getRegistryName() // netherite_helmet
	};
	private final ResourceLocation[] chestplates =
	{
			Blocks.AIR.getRegistryName(),
			Items.LEATHER_CHESTPLATE.getRegistryName(),
			Items.IRON_CHESTPLATE.getRegistryName(),
			Items.CHAINMAIL_CHESTPLATE.getRegistryName(),
			Items.GOLDEN_CHESTPLATE.getRegistryName(),
			Items.DIAMOND_CHESTPLATE.getRegistryName(),
			Items.field_234764_lt_.getRegistryName() // netherite_chestplate
	};
	private final ResourceLocation[] leggings =
	{
			Blocks.AIR.getRegistryName(),
			Items.LEATHER_LEGGINGS.getRegistryName(),
			Items.IRON_LEGGINGS.getRegistryName(),
			Items.CHAINMAIL_LEGGINGS.getRegistryName(),
			Items.GOLDEN_LEGGINGS.getRegistryName(),
			Items.DIAMOND_LEGGINGS.getRegistryName(),
			Items.field_234765_lu_.getRegistryName() // netherite_leggings
	};
	private final ResourceLocation[] boots =
	{
			Blocks.AIR.getRegistryName(),
			Items.LEATHER_BOOTS.getRegistryName(),
			Items.IRON_BOOTS.getRegistryName(),
			Items.CHAINMAIL_BOOTS.getRegistryName(),
			Items.GOLDEN_BOOTS.getRegistryName(),
			Items.DIAMOND_BOOTS.getRegistryName(),
			Items.field_234766_lv_.getRegistryName() // netherite_boots
	};
	private final ResourceLocation[] swords =
	{
			Blocks.AIR.getRegistryName(),
			Items.WOODEN_SWORD.getRegistryName(),
			Items.STONE_SWORD.getRegistryName(),
			Items.IRON_SWORD.getRegistryName(),
			Items.GOLDEN_SWORD.getRegistryName(),
			Items.DIAMOND_SWORD.getRegistryName(),
			Items.field_234754_kI_.getRegistryName() // netherite_sword
	};
	private final ResourceLocation[][] armor = {this.helmets, this.chestplates, this.leggings, this.boots};
	private final ResourceLocation[][] hands = {this.swords, this.swords};
	
	@Override
	public ICommandBuilder getCommandBuilder()
	{
		return this.builderSummon;
	}
	
	@Override
	public void init(Container container)
	{
		for(Attribute attribute : this.builderSummon.getAttributes())
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
		this.mobField = new GuiTextFieldTooltip(x + 118, y, 114, 20, new StringTextComponent(I18n.format("gui.worldhandler.entities.summon.start.mob_id") + " (" + I18n.format("gui.worldhandler.generic.name") + ")"));
		this.mobField.setValidator(Predicates.notNull());
		this.mobField.setText(this.mob);
		this.mobField.setResponder(text ->
		{
			this.mob = text;
			this.builderSummon.setName(this.mob);
			container.initButtons();
		});
		
		this.customNameField = new GuiTextFieldTooltip(x + 118, y + 24, 114, 20, new TranslationTextComponent("gui.worldhandler.entities.summon.start.custom_name"));
		this.customNameField.setValidator(Predicates.notNull());
		this.customNameField.setText(this.name);
		this.customNameField.setResponder(text ->
		{
			this.name = text;
			this.builderSummon.setCustomName(this.name);
			container.initButtons();
		});
		
		this.passengerField = new GuiTextFieldTooltip(x + 118, y + 48, 114, 20, new TranslationTextComponent("gui.worldhandler.entities.summon.start.passenger_mob_id"));
		this.passengerField.setValidator(Predicates.notNull());
		this.passengerField.setText(this.passenger);
		this.passengerField.setResponder(text ->
		{
			this.passenger = this.passengerField.getText();
			this.builderSummon.setPassenger(0, this.passenger);
			container.initButtons();
		});
		
		if(Page.ATTRIBUTES.equals(this.page))
		{
			MenuPageList<Attribute> attributes = new MenuPageList<Attribute>(x + 118, y, ComponentAttribute.ATTRIBUTES, 114, 20, 3, container, new ILogicPageList<Attribute>()
			{
				@Override
				public IFormattableTextComponent translate(Attribute item)
				{
					return new TranslationTextComponent(item.func_233754_c_());
				}
				
				@Override
				public IFormattableTextComponent toTooltip(Attribute item)
				{
					return new StringTextComponent(item.getRegistryName().toString());
				}
				
				@Override
				public void onClick(Attribute item)
				{
					
				}
				
				@Override
				public GuiButtonBase onRegister(int x, int y, int width, int height, IFormattableTextComponent text, Attribute item, ActionHandler actionHandler)
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
		
		container.add(new GuiButtonBase(x, y + 96, 114, 20, new TranslationTextComponent("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, new TranslationTextComponent("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.add(button4 = new GuiButtonBase(x, y, 114, 20, new TranslationTextComponent("gui.worldhandler.entities.summon.start"), () ->
		{
			this.page = Page.START;
			container.func_231160_c_();
		}));
		container.add(button5 = new GuiButtonBase(x, y + 24, 114, 20, new TranslationTextComponent("gui.worldhandler.entities.summon.potion_effects"), () ->
		{
			this.page = Page.POTIONS;
			container.func_231160_c_();
		}));
		container.add(button6 = new GuiButtonBase(x, y + 48, 114, 20, new TranslationTextComponent("gui.worldhandler.entities.summon.attributes"), () ->
		{
			this.page = Page.ATTRIBUTES;
			container.func_231160_c_();
		}));
		container.add(button7 = new GuiButtonBase(x, y + 72, 114, 20, new TranslationTextComponent("gui.worldhandler.entities.summon.equipment"), () ->
		{
			this.page = Page.EQUIPMENT;
			container.func_231160_c_();
		}));
		
		if(Page.START.equals(this.page))
		{
			button4.field_230693_o_ = false;
			
			container.add(this.mobField);
			container.add(this.customNameField);
			container.add(this.passengerField);
			
			if(!this.builderSummon.needsCommandBlock() && !this.builderSummon.getCustomName().isSpecial())
			{
				container.add(button3 = new GuiButtonBase(x + 118, y + 72, 114, 20, new TranslationTextComponent("gui.worldhandler.title.entities.summon"), this::send));
			}
			else
			{
				container.add(button3 = new GuiButtonBase(x + 118, y + 72, 114, 20, new TranslationTextComponent("gui.worldhandler.actions.place_command_block"), this::send));
			}
			
			button3.field_230693_o_ = ForgeRegistries.ENTITIES.containsKey(this.builderSummon.getEntity());
		}
		else if(Page.POTIONS.equals(this.page))
		{
			button5.field_230693_o_ = false;
			
			container.add(button1 = new GuiButtonBase(x + 118, y + 72, 56, 20, TextUtils.ARROW_LEFT, () ->
			{
				this.potionPage--;
				container.func_231160_c_();
			}));
			container.add(button2 = new GuiButtonBase(x + 118 + 60, y + 72, 55, 20, TextUtils.ARROW_RIGHT, () ->
			{
				this.potionPage++;
				container.func_231160_c_();
			}));
			
			int count = 0;
			
			for(ResourceLocation location : this.getSortedPotionList())
			{
				Effect potion = ForgeRegistries.POTIONS.getValue(location);
				
				if(!potion.equals(Effects.INSTANT_DAMAGE) && !potion.equals(Effects.INSTANT_HEALTH))
				{
					if(this.potionPage == 0)
					{
						button1.field_230693_o_ = false;
					}
					
					if(this.potionPage == ForgeRegistries.POTIONS.getKeys().size() - 3)
					{
						button2.field_230693_o_ = false;
					}
					
					if(count == this.potionPage)
					{
						container.add(new GuiSlider(x + 118, y, 114, 20, 0, Config.getSliders().getMaxSummonPotionAmplifier(), 0, container, new LogicSliderSimple("amplifier" + potion.getRegistryName(), new TranslationTextComponent(potion.getName()), value ->
						{
							this.builderSummon.setAmplifier(potion, value.byteValue());
						})));
						container.add(new GuiSlider(x + 118, y + 24, 114, 20, 0, Config.getSliders().getMaxSummonPotionMinutes(), 0, container, new LogicSliderSimple("duration" + potion.getRegistryName(), new TranslationTextComponent("gui.worldhandler.potion.time.minutes"), value ->
						{
							this.builderSummon.setMinutes(potion, value);
						})));
						container.add(new GuiButtonBase(x + 118, y + 48, 114, 20, new TranslationTextComponent("gui.worldhandler.potions.effect.particles", this.builderSummon.getShowParticles(potion) ? new TranslationTextComponent("gui.worldhandler.generic.on") : new TranslationTextComponent("gui.worldhandler.generic.off")), () ->
						{
							this.builderSummon.setShowParticles(potion, !this.builderSummon.getShowParticles(potion));
							container.func_231160_c_();
						}));
						break;
					}
					
					count++;
				}
			}
		}
		else if(Page.ATTRIBUTES.equals(this.page))
		{
			button6.field_230693_o_ = false;
		}
		else if(Page.EQUIPMENT.equals(this.page))
		{
			for(int i = 0; i < 4; i++)
	 		{
				final int index = i;
				
				container.add(new GuiButtonBase(x + 118, y + 24 * i, 20, 20, TextUtils.ARROW_LEFT, () ->
				{
					this.builderSummon.setArmorItem(index, this.armor[index][Math.floorMod(ArrayUtils.indexOf(this.armor[index], this.builderSummon.getArmorItem(index)) - 1, this.armor[index].length)]);
					container.func_231160_c_();
				}));
				container.add(button1 = new GuiButtonItem(x + 118 + 24, y + 24 * i, 20, 20, ForgeRegistries.ITEMS.getValue(this.builderSummon.getArmorItem(i)), null));
				container.add(new GuiButtonBase(x + 118 + 47, y + 24 * i, 20, 20, TextUtils.ARROW_RIGHT, () ->
				{
					this.builderSummon.setArmorItem(index, this.armor[index][Math.floorMod(ArrayUtils.indexOf(this.armor[index], this.builderSummon.getArmorItem(index)) + 1, this.armor[index].length)]);
					container.func_231160_c_();
				}));
				
				button1.field_230693_o_ = false;
	 		}
			
			for(int i = 0; i < 2; i++)
	 		{
				final int index = i;
				
				container.add(new GuiButtonIcon(x + 118 + 70 + 24 * i, y + 12, 20, 20, EnumIcon.ARROW_UP, null, () ->
				{
					this.builderSummon.setHandItem(index, this.hands[index][Math.floorMod(ArrayUtils.indexOf(this.hands[index], this.builderSummon.getHandItem(index)) - 1, this.hands[index].length)]);
					container.func_231160_c_();
				}));
				container.add(button1 = new GuiButtonItem(x + 118 + 70 + 24 * i, y + 36, 20, 20, ForgeRegistries.ITEMS.getValue(this.builderSummon.getHandItem(i)), null));
				container.add(new GuiButtonIcon(x + 118 + 70 + 24 * i, y + 60, 20, 20, EnumIcon.ARROW_DOWN, null, () ->
				{
					System.out.println(index + " " + ArrayUtils.indexOf(this.hands[index], this.builderSummon.getHandItem(index)) + 1);
					this.builderSummon.setHandItem(index, this.hands[index][Math.floorMod(ArrayUtils.indexOf(this.hands[index], this.builderSummon.getHandItem(index)) + 1, this.hands[index].length)]);
					container.func_231160_c_();
				}));
				
				button1.field_230693_o_ = false;
	 		}
			
			button7.field_230693_o_ = false;
		}
	}
	
	private void send()
	{
		CommandHelper.sendCommand(this.builderSummon, this.builderSummon.getCustomName().isSpecial());
	}
	
	@Override
	public void tick(Container container)
	{
		if(Page.START.equals(this.page))
		{
			this.mobField.tick();
			this.customNameField.tick();
			this.passengerField.tick();
		}
	}
	
	@Override
	public void drawScreen(MatrixStack matrix, Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		if(Page.START.equals(this.page))
		{
			this.mobField.func_230431_b_(matrix, mouseX, mouseY, partialTicks);
			this.customNameField.func_230431_b_(matrix, mouseX, mouseY, partialTicks);
			this.passengerField.func_230431_b_(matrix, mouseX, mouseY, partialTicks);
		}
		else if(Page.POTIONS.equals(this.page))
		{
			Minecraft.getInstance().fontRenderer.func_238421_b_(matrix, (this.potionPage + 1) + "/" + (ForgeRegistries.POTIONS.getKeys().size() - 2), x + 118, y - 11, Config.getSkin().getHeadlineColor());
		}
		else if(Page.EQUIPMENT.equals(this.page))
		{
			RenderUtils.color(1.0F, 1.0F, 1.0F, 1.0F);
		 	Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/beacon.png"));
	 		container.func_230926_e_(0); //setBlitOffset
	 		
	 		for(int i = 0; i < 4; i++)
	 		{
		 		if(this.builderSummon.getArmorItem(i).equals(Items.AIR.getRegistryName()))
		 		{
			 		container.func_238474_b_(matrix, x + 118 + 24 + 2, y + 2 + 24 * i, 112, 221, 16, 16); //blit
		 		}
	 		}
	 		
	 		for(int i = 0; i < 2; i++)
	 		{
		 		if(this.builderSummon.getHandItem(i).equals(Items.AIR.getRegistryName()))
		 		{
			 		container.func_238474_b_(matrix, x + 118 + 70 + 2 + 24 * i, y + 2 + 36, 112, 221, 16, 16); //blit
		 		}
	 		}
		}
	}
	
	private List<ResourceLocation> getSortedPotionList()
	{
		return ForgeRegistries.POTIONS.getKeys().stream()
				.sorted((a, b) -> I18n.format(ForgeRegistries.POTIONS.getValue(a).getName()).compareTo(I18n.format(ForgeRegistries.POTIONS.getValue(b).getName())))
				.collect(Collectors.toList());
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.ENTITIES;
	}
	
	@Override
	public IFormattableTextComponent getTitle()
	{
		return new TranslationTextComponent("gui.worldhandler.title.entities.summon");
	}
	
	@Override
	public IFormattableTextComponent getTabTitle()
	{
		return new TranslationTextComponent("gui.worldhandler.tab.entities.summon");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.SUMMON;
	}
	
	@OnlyIn(Dist.CLIENT)
	public static enum Page
	{
		START,
		POTIONS,
		ATTRIBUTES,
		EQUIPMENT;
	}
}
