package exopandora.worldhandler.gui.content.impl;

import java.util.ArrayList;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderMultiCommand;
import exopandora.worldhandler.builder.impl.BuilderPotionEffect;
import exopandora.worldhandler.builder.impl.BuilderPotionItem;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.menu.impl.ILogicPageList;
import exopandora.worldhandler.gui.menu.impl.MenuPageList;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiButtonTooltip;
import exopandora.worldhandler.gui.widget.button.GuiSlider;
import exopandora.worldhandler.gui.widget.button.LogicSliderSimple;
import exopandora.worldhandler.util.ActionHandler;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.CommandHelper;
import exopandora.worldhandler.util.TextUtils;
import net.minecraft.item.Items;
import net.minecraft.potion.Effect;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

@OnlyIn(Dist.CLIENT)
public class ContentPotions extends ContentChild
{
	private int potionPage;
	
	private final BuilderPotionEffect builderPotion = new BuilderPotionEffect();
	private final BuilderPotionItem builderPotionItem = new BuilderPotionItem();
	
	@Override
	public ICommandBuilder getCommandBuilder()
	{
		return new BuilderMultiCommand(this.builderPotion, this.builderPotionItem);
	}
	
	@Override
	public void init(Container container)
	{
		if(this.builderPotion.getAmplifier() > Config.getSliders().getMaxPotionAmplifier())
		{
			this.builderPotion.setAmplifier((byte) Config.getSliders().getMaxPotionAmplifier());
		}
		
		for(Effect potion : this.builderPotionItem.getEffects())
		{
			byte amplifier = this.builderPotionItem.getAmplifier(potion);
			
			if(amplifier > Config.getSliders().getMaxPotionAmplifier())
			{
				this.builderPotionItem.setAmplifier(potion, (byte) Config.getSliders().getMaxPotionAmplifier());
			}
		}
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		MenuPageList<Effect> potions = new MenuPageList<Effect>(x, y, new ArrayList<Effect>(ForgeRegistries.POTIONS.getValues()), 114, 20, 3, container, new ILogicPageList<Effect>()
		{
			@Override
			public IFormattableTextComponent translate(Effect item)
			{
				return new TranslationTextComponent(item.getName());
			}
			
			@Override
			public IFormattableTextComponent toTooltip(Effect item)
			{
				return new StringTextComponent(item.getRegistryName().toString());
			}
			
			@Override
			public void onClick(Effect item)
			{
				ContentPotions.this.builderPotion.setEffect(item);
				container.initButtons();
			}
			
			@Override
			public GuiButtonBase onRegister(int x, int y, int width, int height, IFormattableTextComponent text, Effect item, ActionHandler actionHandler)
			{
				return new GuiButtonTooltip(x, y, width, height, text, this.toTooltip(item), actionHandler);
			}
			
			@Override
			public String getId()
			{
				return "potions";
			}
		});
		
		container.add(potions);
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		GuiButtonBase button1;
		GuiButtonBase button2;
		GuiButtonBase button3;
		GuiButtonBase button4;
		GuiButtonBase button5;
		
		container.add(new GuiButtonBase(x, y + 96, 114, 20, new TranslationTextComponent("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, new TranslationTextComponent("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		if(this.potionPage == 0)
		{
			container.add(new GuiButtonBase(x + 118, y + 12, 114, 20, new TranslationTextComponent("gui.worldhandler.potions.effect.give"), () ->
			{
				this.next(container);
			}));
			container.add(new GuiButtonBase(x + 118, y + 36, 114, 20, new TranslationTextComponent("gui.worldhandler.potions.effect.remove"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderPotion.buildRemove());
				container.init();
			}));
			container.add(new GuiButtonBase(x + 118, y + 60, 114, 20, new TranslationTextComponent("gui.worldhandler.potions.effect.remove_all"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderPotion.buildClear());
				container.init();
			}));
		}
		else if(this.potionPage == 1)
		{
			Effect potion = this.builderPotion.getEffectAsPotion();
			
			container.add(new GuiButtonBase(x + 118, y + 24, 114, 20, new TranslationTextComponent("gui.worldhandler.potions.effect.ambient", this.builderPotionItem.getAmbient(potion) ? new TranslationTextComponent("gui.worldhandler.generic.on") : new TranslationTextComponent("gui.worldhandler.generic.off")), () ->
			{
				this.builderPotionItem.setAmbient(potion, !this.builderPotionItem.getAmbient(potion));
				container.init();
			}));
			container.add(new GuiButtonBase(x + 118, y + 48, 114, 20, new TranslationTextComponent("gui.worldhandler.potions.effect.particles", this.builderPotion.getHideParticles() ? new TranslationTextComponent("gui.worldhandler.generic.off") : new TranslationTextComponent("gui.worldhandler.generic.on")), () ->
			{
				this.builderPotion.setHideParticles(!this.builderPotion.getHideParticles());
				this.builderPotionItem.setShowParticles(potion, !this.builderPotionItem.getShowParticles(potion));
				container.init();
			}));
			container.add(new GuiSlider(x + 118, y, 114, 20, 0, Config.getSliders().getMaxPotionAmplifier(), 0, container, new LogicSliderSimple("amplifier" + potion.getRegistryName(), new TranslationTextComponent("gui.worldhandler.potions.effect.amplifier"), value ->
			{
				this.builderPotion.setAmplifier(value.byteValue());
				this.builderPotionItem.setAmplifier(potion, value.byteValue());
			})));
		}
		else if(this.potionPage == 2)
		{
			Effect potion = this.builderPotion.getEffectAsPotion();
			
			container.add(new GuiSlider(x + 118, y, 114, 20, 0, 59, 0, container, new LogicSliderSimple("s" + potion.getRegistryName(), new TranslationTextComponent("gui.worldhandler.potion.time.seconds"), value ->
			{
				this.builderPotion.setSeconds(value.intValue());
				this.builderPotionItem.setSeconds(potion, value.intValue());
			})));
			container.add(new GuiSlider(x + 118, y + 24, 114, 20, 0, 59, 0, container, new LogicSliderSimple("m" + potion.getRegistryName(), new TranslationTextComponent("gui.worldhandler.potion.time.minutes"), value ->
			{
				this.builderPotion.setMinutes(value.intValue());
				this.builderPotionItem.setMinutes(potion, value.intValue());
			})));
			container.add(new GuiSlider(x + 118, y + 48, 114, 20, 0, 99, 0, container, new LogicSliderSimple("h" + potion.getRegistryName(), new TranslationTextComponent("gui.worldhandler.potion.time.hours"), value ->
			{
				this.builderPotion.setHours(value.intValue());
				this.builderPotionItem.setHours(potion, value.intValue());
			})));
		}
		else if(this.potionPage == 3)
		{
			container.add(button1 = new GuiButtonBase(x + 118, y, 114, 20, new TranslationTextComponent("gui.worldhandler.potions.effect"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderPotion.buildGive());
				this.potionPage = 0;
				container.init();
			}));
			container.add(button2 = new GuiButtonBase(x + 118, y + 24, 56, 20, new TranslationTextComponent("gui.worldhandler.potions.effect.tipped_arrow"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderPotionItem.build(Items.TIPPED_ARROW));
				this.potionPage = 0;
				container.init();
			}));
			container.add(button3 = new GuiButtonTooltip(x + 178, y + 24, 55, 20, new TranslationTextComponent("gui.worldhandler.potions.effect.bottle"), new TranslationTextComponent("gui.worldhandler.actions.place_command_block"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderPotionItem.build(Items.POTION));
				this.potionPage = 0;
				container.init();
			}));
			container.add(button4 = new GuiButtonTooltip(x + 118, y + 48, 56, 20, new TranslationTextComponent("gui.worldhandler.potions.effect.splash"), new TranslationTextComponent("gui.worldhandler.actions.place_command_block"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderPotionItem.build(Items.SPLASH_POTION));
				this.potionPage = 0;
				container.init();
			}));
			container.add(button5 = new GuiButtonTooltip(x + 178, y + 48, 55, 20, new TranslationTextComponent("gui.worldhandler.potions.effect.lingering"), new TranslationTextComponent("gui.worldhandler.actions.place_command_block"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderPotionItem.build(Items.LINGERING_POTION));
				this.potionPage = 0;
				container.init();
			}));
			
			boolean enabled = this.builderPotion.getAmplifier() >= 0 && this.builderPotion.getDuration() > 0;
			
			button1.active = enabled;
			button2.active = enabled;
			button3.active = enabled;
			button4.active = enabled;
			button5.active = enabled;
		}
		
		if(this.potionPage > 0)
		{
			container.add(new GuiButtonBase(x + 118, y + 72, 56, 20, TextUtils.ARROW_LEFT, () ->
			{
				this.potionPage--;
				container.init();
			}));
			container.add(button1 = new GuiButtonBase(x + 118 + 60, y + 72, 55, 20, TextUtils.ARROW_RIGHT, () ->
			{
				this.next(container);
			}));
			
			button1.active = this.potionPage < 3;
		}
	}
	
	private void next(Container container)
	{
		this.potionPage++;
		container.init();
	}
	
	@Override
	public IFormattableTextComponent getTitle()
	{
		return new TranslationTextComponent("gui.worldhandler.title.potions");
	}
	
	@Override
	public void onPlayerNameChanged(String username)
	{
		this.builderPotion.setPlayer(username);
		this.builderPotionItem.setPlayer(username);
	}
}
