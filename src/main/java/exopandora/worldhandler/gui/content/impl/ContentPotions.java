package exopandora.worldhandler.gui.content.impl;

import java.util.ArrayList;

import exopandora.worldhandler.builder.argument.tag.CustomPotionEffectsTag;
import exopandora.worldhandler.builder.argument.tag.EffectInstance;
import exopandora.worldhandler.builder.impl.EffectCommandBuilder;
import exopandora.worldhandler.builder.impl.GiveCommandBuilder;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiButtonTooltip;
import exopandora.worldhandler.gui.widget.button.GuiSlider;
import exopandora.worldhandler.gui.widget.button.LogicSliderSimple;
import exopandora.worldhandler.gui.widget.menu.impl.ILogicPageList;
import exopandora.worldhandler.gui.widget.menu.impl.MenuPageList;
import exopandora.worldhandler.util.ActionHandler;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.CommandHelper;
import exopandora.worldhandler.util.TextUtils;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

public class ContentPotions extends ContentChild
{
	private final EffectCommandBuilder builderPotion = new EffectCommandBuilder();
	private final CustomPotionEffectsTag effects = new CustomPotionEffectsTag();
	private final GiveCommandBuilder builderPotionItem = new GiveCommandBuilder();
	private final CommandPreview preview = new CommandPreview()
			.add(this.builderPotion, EffectCommandBuilder.Label.GIVE_SECONDS_AMPLIFIER_HIDEPARTICLES)
			.add(this.builderPotionItem, GiveCommandBuilder.Label.GIVE_COUNT);
	
	private int potionPage;
	
	public ContentPotions()
	{
		this.builderPotionItem.item().addTagProvider(this.effects);
		this.builderPotion.amplifier().set((byte) 0);
		this.builderPotion.hideParticles().set(false);
	}
	
	@Override
	public CommandPreview getCommandPreview()
	{
		return this.preview;
	}
	
	@Override
	public void init(Container container)
	{
		if(this.builderPotion.amplifier().get() > Config.getSliders().getMaxPotionAmplifier())
		{
			this.builderPotion.amplifier().set((byte) Config.getSliders().getMaxPotionAmplifier());
		}
		
		for(MobEffect effect : this.effects.getMobEffects())
		{
			EffectInstance tag = this.effects.getOrCreate(effect);
			
			if(tag.getAmplifier() > Config.getSliders().getMaxPotionAmplifier())
			{
				tag.setAmplifier((byte) Config.getSliders().getMaxPotionAmplifier());
			}
		}
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		MenuPageList<MobEffect> potions = new MenuPageList<MobEffect>(x, y, new ArrayList<MobEffect>(ForgeRegistries.MOB_EFFECTS.getValues()), 114, 20, 3, container, new ILogicPageList<MobEffect>()
		{
			@Override
			public MutableComponent translate(MobEffect effect)
			{
				return new TranslatableComponent(effect.getDescriptionId());
			}
			
			@Override
			public MutableComponent toTooltip(MobEffect effect)
			{
				return new TextComponent(effect.getRegistryName().toString());
			}
			
			@Override
			public void onClick(MobEffect effect)
			{
				ContentPotions.this.builderPotion.effect().set(effect);
				
				for(MobEffect entry : ContentPotions.this.effects.getMobEffects())
				{
					if(!entry.equals(effect))
					{
						ContentPotions.this.effects.remove(entry);
					}
				}
				
				container.initButtons();
			}
			
			@Override
			public GuiButtonBase onRegister(int x, int y, int width, int height, MutableComponent text, MobEffect effect, ActionHandler actionHandler)
			{
				return new GuiButtonTooltip(x, y, width, height, text, this.toTooltip(effect), actionHandler);
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
		
		container.add(new GuiButtonBase(x, y + 96, 114, 20, new TranslatableComponent("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, new TranslatableComponent("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		if(this.potionPage == 0)
		{
			container.add(new GuiButtonBase(x + 118, y + 12, 114, 20, new TranslatableComponent("gui.worldhandler.potions.effect.give"), () ->
			{
				this.next(container);
			}));
			container.add(new GuiButtonBase(x + 118, y + 36, 114, 20, new TranslatableComponent("gui.worldhandler.potions.effect.remove"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderPotion, EffectCommandBuilder.Label.CLEAR_TARGETS_EFFECT);
				container.init();
			}));
			container.add(new GuiButtonBase(x + 118, y + 60, 114, 20, new TranslatableComponent("gui.worldhandler.potions.effect.remove_all"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderPotion, EffectCommandBuilder.Label.CLEAR);
				container.init();
			}));
		}
		else if(this.potionPage == 1)
		{
			MobEffect effect = this.builderPotion.effect().getEffect();
			EffectInstance tag = this.effects.getOrCreate(effect);
			
			container.add(new GuiButtonBase(x + 118, y + 24, 114, 20, new TranslatableComponent("gui.worldhandler.potions.effect.ambient", tag.isAmbient() ? new TranslatableComponent("gui.worldhandler.generic.on") : new TranslatableComponent("gui.worldhandler.generic.off")), () ->
			{
				tag.setAmbient(!tag.isAmbient());
				container.init();
			}));
			container.add(new GuiButtonBase(x + 118, y + 48, 114, 20, new TranslatableComponent("gui.worldhandler.potions.effect.particles", this.builderPotion.hideParticles().get() ? new TranslatableComponent("gui.worldhandler.generic.off") : new TranslatableComponent("gui.worldhandler.generic.on")), () ->
			{
				this.builderPotion.hideParticles().set(!this.builderPotion.hideParticles().get());
				tag.setShowParticles(!tag.doShowParticles());
				container.init();
			}));
			container.add(new GuiSlider(x + 118, y, 114, 20, 1, Config.getSliders().getMaxPotionAmplifier(), 1, container, new LogicSliderSimple("amplifier" + effect.getRegistryName(), new TranslatableComponent("gui.worldhandler.potions.effect.amplifier"), value ->
			{
				this.builderPotion.amplifier().set((byte) (value.byteValue() - 1));
				tag.setAmplifier(value.byteValue());
			})));
		}
		else if(this.potionPage == 2)
		{
			MobEffect effect = this.builderPotion.effect().getEffect();
			EffectInstance tag = this.effects.getOrCreate(effect);
			
			container.add(new GuiSlider(x + 118, y, 114, 20, 0, 59, 0, container, new LogicSliderSimple("s" + effect.getRegistryName(), new TranslatableComponent("gui.worldhandler.potion.time.seconds"), value ->
			{
				tag.setSeconds(value.intValue());
				this.builderPotion.seconds().set(tag.toSeconds());
			})));
			container.add(new GuiSlider(x + 118, y + 24, 114, 20, 0, 59, 0, container, new LogicSliderSimple("m" + effect.getRegistryName(), new TranslatableComponent("gui.worldhandler.potion.time.minutes"), value ->
			{
				tag.setMinutes(value.intValue());
				this.builderPotion.seconds().set(tag.toSeconds());
			})));
			container.add(new GuiSlider(x + 118, y + 48, 114, 20, 0, 99, 0, container, new LogicSliderSimple("h" + effect.getRegistryName(), new TranslatableComponent("gui.worldhandler.potion.time.hours"), value ->
			{
				tag.setHours(value.intValue());
				this.builderPotion.seconds().set(tag.toSeconds());
			})));
		}
		else if(this.potionPage == 3)
		{
			container.add(button1 = new GuiButtonBase(x + 118, y, 114, 20, new TranslatableComponent("gui.worldhandler.potions.effect"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderPotion, EffectCommandBuilder.Label.GIVE_SECONDS_AMPLIFIER_HIDEPARTICLES);
				this.potionPage = 0;
				container.init();
			}));
			container.add(button2 = new GuiButtonBase(x + 118, y + 24, 56, 20, new TranslatableComponent("gui.worldhandler.potions.effect.tipped_arrow"), () ->
			{
				this.giveItem(container.getPlayer(), Items.TIPPED_ARROW, 0.125F);
				this.potionPage = 0;
				container.init();
			}));
			container.add(button3 = new GuiButtonTooltip(x + 178, y + 24, 55, 20, new TranslatableComponent("gui.worldhandler.potions.effect.bottle"), new TranslatableComponent("gui.worldhandler.actions.place_command_block"), () ->
			{
				this.giveItem(container.getPlayer(), Items.POTION, 1.0F);
				this.potionPage = 0;
				container.init();
			}));
			container.add(button4 = new GuiButtonTooltip(x + 118, y + 48, 56, 20, new TranslatableComponent("gui.worldhandler.potions.effect.splash"), new TranslatableComponent("gui.worldhandler.actions.place_command_block"), () ->
			{
				this.giveItem(container.getPlayer(), Items.SPLASH_POTION, 1.0F);
				this.potionPage = 0;
				container.init();
			}));
			container.add(button5 = new GuiButtonTooltip(x + 178, y + 48, 55, 20, new TranslatableComponent("gui.worldhandler.potions.effect.lingering"), new TranslatableComponent("gui.worldhandler.actions.place_command_block"), () ->
			{
				this.giveItem(container.getPlayer(), Items.LINGERING_POTION, 0.25F);
				this.potionPage = 0;
				container.init();
			}));
			
			boolean enabled = this.builderPotion.amplifier().get() >= 0 && this.builderPotion.seconds().get() > 0;
			
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
	
	private void giveItem(String player, Item item, float modifier)
	{
		CustomPotionEffectsTag effects = new CustomPotionEffectsTag();
		MobEffect effect = this.builderPotion.effect().getEffect();
		EffectInstance tag = effects.getOrCreate(effect);
		EffectInstance original = this.effects.getOrCreate(effect);
		
		tag.setAmbient(original.isAmbient());
		tag.setAmplifier(original.getAmplifier());
		tag.setHours((int) (original.getHours() / modifier));
		tag.setMinutes((int) (original.getMinutes() / modifier));
		tag.setSeconds((int) (original.getSeconds() / modifier));
		tag.setShowParticles(original.doShowParticles());
		
		GiveCommandBuilder builder = new GiveCommandBuilder();
		builder.targets().setTarget(this.builderPotionItem.targets().getTarget());
		builder.item().set(item);
		builder.item().addTagProvider(effects);
		
		CommandHelper.sendCommand(player, builder, GiveCommandBuilder.Label.GIVE);
	}
	
	@Override
	public MutableComponent getTitle()
	{
		return new TranslatableComponent("gui.worldhandler.title.potions");
	}
	
	@Override
	public void onPlayerNameChanged(String username)
	{
		this.builderPotion.targets().setTarget(username);
		this.builderPotionItem.targets().setTarget(username);
	}
}
