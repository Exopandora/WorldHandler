package exopandora.worldhandler.gui.content.impl;

import java.util.ArrayList;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderMultiCommand;
import exopandora.worldhandler.builder.impl.BuilderPotionEffect;
import exopandora.worldhandler.builder.impl.BuilderPotionItem;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.button.GuiButtonBase;
import exopandora.worldhandler.gui.button.GuiButtonTooltip;
import exopandora.worldhandler.gui.button.GuiSlider;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.logic.ILogicPageList;
import exopandora.worldhandler.gui.logic.LogicSliderSimple;
import exopandora.worldhandler.gui.menu.impl.MenuPageList;
import exopandora.worldhandler.helper.ActionHelper;
import exopandora.worldhandler.helper.CommandHelper;
import exopandora.worldhandler.util.ActionHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Items;
import net.minecraft.potion.Effect;
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
			public String translate(Effect item)
			{
				return I18n.format(item.getName());
			}
			
			@Override
			public String toTooltip(Effect item)
			{
				return item.getRegistryName().toString();
			}
			
			@Override
			public void onClick(Effect item)
			{
				ContentPotions.this.builderPotion.setEffect(item);
				container.initButtons();
			}
			
			@Override
			public GuiButtonBase onRegister(int x, int y, int width, int height, String text, Effect item, ActionHandler actionHandler)
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
		
		container.add(new GuiButtonBase(x, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		if(this.potionPage == 0)
		{
			container.add(new GuiButtonBase(x + 118, y + 12, 114, 20, I18n.format("gui.worldhandler.potions.effect.give"), () ->
			{
				this.next(container);
			}));
			container.add(new GuiButtonBase(x + 118, y + 36, 114, 20, I18n.format("gui.worldhandler.potions.effect.remove"), () ->
			{
				CommandHelper.sendCommand(this.builderPotion.getRemoveCommand());
				container.init();
			}));
			container.add(new GuiButtonBase(x + 118, y + 60, 114, 20, I18n.format("gui.worldhandler.potions.effect.remove_all"), () ->
			{
				CommandHelper.sendCommand(this.builderPotion.getClearCommand());
				container.init();
			}));
		}
		else if(this.potionPage == 1)
		{
			Effect potion = this.builderPotion.getEffectAsPotion();
			
			container.add(new GuiButtonBase(x + 118, y + 24, 114, 20, I18n.format("gui.worldhandler.potions.effect.ambient", this.builderPotionItem.getAmbient(potion) ? I18n.format("gui.worldhandler.generic.on") : I18n.format("gui.worldhandler.generic.off")), () ->
			{
				this.builderPotionItem.setAmbient(potion, !this.builderPotionItem.getAmbient(potion));
				container.init();
			}));
			container.add(new GuiButtonBase(x + 118, y + 48, 114, 20, I18n.format("gui.worldhandler.potions.effect.particles", this.builderPotion.getHideParticles() ? I18n.format("gui.worldhandler.generic.off") : I18n.format("gui.worldhandler.generic.on")), () ->
			{
				this.builderPotion.setHideParticles(!this.builderPotion.getHideParticles());
				this.builderPotionItem.setShowParticles(potion, !this.builderPotionItem.getShowParticles(potion));
				container.init();
			}));
			container.add(new GuiSlider(x + 118, y, 114, 20, 0, Config.getSliders().getMaxPotionAmplifier(), 0, container, new LogicSliderSimple("amplifier" + potion.getRegistryName(), I18n.format("gui.worldhandler.potions.effect.amplifier"), value ->
			{
				this.builderPotion.setAmplifier(value.byteValue());
				this.builderPotionItem.setAmplifier(potion, value.byteValue());
			})));
		}
		else if(this.potionPage == 2)
		{
			Effect potion = this.builderPotion.getEffectAsPotion();
			
			container.add(new GuiSlider(x + 118, y, 114, 20, 0, 59, 0, container, new LogicSliderSimple("s" + potion.getRegistryName(), I18n.format("gui.worldhandler.potion.time.seconds"), value ->
			{
				this.builderPotion.setSeconds(value.intValue());
				this.builderPotionItem.setSeconds(potion, value.intValue());
			})));
			container.add(new GuiSlider(x + 118, y + 24, 114, 20, 0, 59, 0, container, new LogicSliderSimple("m" + potion.getRegistryName(), I18n.format("gui.worldhandler.potion.time.minutes"), value ->
			{
				this.builderPotion.setMinutes(value.intValue());
				this.builderPotionItem.setMinutes(potion, value.intValue());
			})));
			container.add(new GuiSlider(x + 118, y + 48, 114, 20, 0, 99, 0, container, new LogicSliderSimple("h" + potion.getRegistryName(), I18n.format("gui.worldhandler.potion.time.hours"), value ->
			{
				this.builderPotion.setHours(value.intValue());
				this.builderPotionItem.setHours(potion, value.intValue());
			})));
		}
		else if(this.potionPage == 3)
		{
			container.add(button1 = new GuiButtonBase(x + 118, y, 114, 20, I18n.format("gui.worldhandler.potions.effect"), () ->
			{
				CommandHelper.sendCommand(this.builderPotion.getGiveCommand());
				this.potionPage = 0;
				container.init();
			}));
			container.add(button2 = new GuiButtonBase(x + 118, y + 24, 56, 20, I18n.format("gui.worldhandler.potions.effect.tipped_arrow"), () ->
			{
				CommandHelper.sendCommand(this.builderPotionItem.getBuilderForPotion(Items.TIPPED_ARROW));
				this.potionPage = 0;
				container.init();
			}));
			container.add(button3 = new GuiButtonTooltip(x + 178, y + 24, 55, 20, I18n.format("gui.worldhandler.potions.effect.bottle"), I18n.format("gui.worldhandler.actions.place_command_block"), () ->
			{
				CommandHelper.sendCommand(this.builderPotionItem.getBuilderForPotion(Items.POTION));
				this.potionPage = 0;
				container.init();
			}));
			container.add(button4 = new GuiButtonTooltip(x + 118, y + 48, 56, 20, I18n.format("gui.worldhandler.potions.effect.splash"), I18n.format("gui.worldhandler.actions.place_command_block"), () ->
			{
				CommandHelper.sendCommand(this.builderPotionItem.getBuilderForPotion(Items.SPLASH_POTION));
				this.potionPage = 0;
				container.init();
			}));
			container.add(button5 = new GuiButtonTooltip(x + 178, y + 48, 55, 20, I18n.format("gui.worldhandler.potions.effect.lingering"), I18n.format("gui.worldhandler.actions.place_command_block"), () ->
			{
				CommandHelper.sendCommand(this.builderPotionItem.getBuilderForPotion(Items.LINGERING_POTION));
				this.potionPage = 0;
				container.init();
			}));
			
			boolean enabled = this.builderPotion.getAmplifier() >= 0;
			
			button1.active = enabled;
			button2.active = enabled;
			button3.active = enabled;
			button4.active = enabled;
			button5.active = enabled;
		}
		
		if(this.potionPage > 0)
		{
			container.add(new GuiButtonBase(x + 118, y + 72, 56, 20, "<", () ->
			{
				this.potionPage--;
				container.init();
			}));
			container.add(button1 = new GuiButtonBase(x + 118 + 60, y + 72, 55, 20, ">", () ->
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
	public String getTitle()
	{
		return I18n.format("gui.worldhandler.title.potions");
	}
	
	@Override
	public void onPlayerNameChanged(String username)
	{
		this.builderPotion.setPlayer(username);
		this.builderPotionItem.setPlayer(username);
	}
}
