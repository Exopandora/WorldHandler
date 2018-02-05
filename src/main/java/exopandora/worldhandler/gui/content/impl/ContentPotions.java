package exopandora.worldhandler.gui.content.impl;

import java.util.ArrayList;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderMultiCommand;
import exopandora.worldhandler.builder.impl.BuilderPotionEffect;
import exopandora.worldhandler.builder.impl.BuilderPotionItem;
import exopandora.worldhandler.gui.button.EnumTooltip;
import exopandora.worldhandler.gui.button.GuiButtonWorldHandler;
import exopandora.worldhandler.gui.button.GuiSlider;
import exopandora.worldhandler.gui.button.responder.SimpleResponder;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.element.impl.ElementPageList;
import exopandora.worldhandler.gui.content.element.logic.ILogicPageList;
import exopandora.worldhandler.gui.content.impl.abstr.ContentChild;
import exopandora.worldhandler.main.WorldHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
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
	public void initGui(Container container, int x, int y)
	{
		ElementPageList<ResourceLocation, Potion> potions = new ElementPageList<ResourceLocation, Potion>(x, y, new ArrayList(Potion.REGISTRY.getKeys()), null, 114, 20, 3, this, new int[] {15, 16, 17}, new ILogicPageList<ResourceLocation, Potion>()
		{
			@Override
			public String translate(ResourceLocation key)
			{
				return I18n.format(Potion.REGISTRY.getObject(key).getName());
			}
			
			@Override
			public void onClick(ResourceLocation clicked)
			{
				builderPotion.setEffect(clicked);
			}
			
			@Override
			public String getRegistryName(ResourceLocation key)
			{
				return key.toString();
			}
			
			@Override
			public void onRegister(int id, int x, int y, int width, int height, String display, String registryKey, boolean enabled, ResourceLocation value, Container container)
			{
				GuiButtonWorldHandler button = new GuiButtonWorldHandler(id, x, y, width, height, display, registryKey, EnumTooltip.TOP_RIGHT);
				button.enabled = enabled;
				container.add(button);
			}
			
			@Override
			public ResourceLocation convert(Potion object)
			{
				if(object != null)
				{
					return object.getRegistryName();
				}
				
				return null;
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
		GuiButtonWorldHandler button6;
		GuiButtonWorldHandler button7;
		GuiButtonWorldHandler button8;
		GuiButtonWorldHandler button9;
		GuiButtonWorldHandler button10;
		
		container.add(new GuiButtonWorldHandler(1, x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame")));
		container.add(new GuiButtonWorldHandler(0, x, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.back")));
		
		if(this.potionPage == 0)
		{
			container.add(new GuiButtonWorldHandler(5, x + 118, y + 12, 114, 20, I18n.format("gui.worldhandler.potions.effect.give")));
			container.add(new GuiButtonWorldHandler(6, x + 118, y + 36, 114, 20, I18n.format("gui.worldhandler.potions.effect.remove")));
			container.add(new GuiButtonWorldHandler(7, x + 118, y + 60, 114, 20, I18n.format("gui.worldhandler.potions.effect.remove_all")));
		}
		else if(this.potionPage == 1)
		{
			Potion potion = this.builderPotion.getEffectAsPotion();
			
			container.add(new GuiButtonWorldHandler(8, x + 118, y + 24, 114, 20, I18n.format("gui.worldhandler.potions.effect.ambient", (this.builderPotionItem.getAmbient(potion) ? I18n.format("gui.worldhandler.generic.on") : I18n.format("gui.worldhandler.generic.off")))));
			container.add(new GuiButtonWorldHandler(9, x + 118, y + 48, 114, 20, I18n.format("gui.worldhandler.potions.effect.particles", (this.builderPotion.getHideParticles() ? I18n.format("gui.worldhandler.generic.off") : I18n.format("gui.worldhandler.generic.on")))));
			container.add(new GuiSlider<Potion>(this, container, "potions_amplifier" + potion, x + 118, y, 114, 20, I18n.format("gui.worldhandler.potions.effect.amplifier"), 0, 100, 0, new SimpleResponder<Potion>(value ->
			{
				this.builderPotion.setAmplifier(value.byteValue());
				this.builderPotionItem.setAmplifier(potion, value.byteValue());
			})));
		}
		else if(this.potionPage == 2)
		{
			Potion potion = this.builderPotion.getEffectAsPotion();
			
			container.add(new GuiSlider<Potion>(this, container, "seconds" + potion, x + 118, y, 114, 20, I18n.format("gui.worldhandler.potion.time.seconds"), 0, 59, 0, new SimpleResponder<Potion>(value ->
			{
				this.builderPotion.setSeconds(value.intValue());
				this.builderPotionItem.setSeconds(potion, value.intValue());
			})));
			container.add(new GuiSlider<Potion>(this, container, "minutes" + potion, x + 118, y + 24, 114, 20, I18n.format("gui.worldhandler.potion.time.minutes"), 0, 59, 0, new SimpleResponder<Potion>(value ->
			{
				this.builderPotion.setMinutes(value.intValue());
				this.builderPotionItem.setMinutes(potion, value.intValue());
			})));
			container.add(new GuiSlider<Potion>(this, container, "hours" + potion, x + 118, y + 48, 114, 20, I18n.format("gui.worldhandler.potion.time.hours"), 0, 99, 0, new SimpleResponder<Potion>(value ->
			{
				this.builderPotion.setHours(value.intValue());
				this.builderPotionItem.setHours(potion, value.intValue());
			})));
		}
		else if(this.potionPage == 3)
		{
			container.add(button6 = new GuiButtonWorldHandler(10, x + 118, y, 114, 20, I18n.format("gui.worldhandler.potions.effect")));
			container.add(button7 = new GuiButtonWorldHandler(14, x + 118, y + 24, 56, 20, I18n.format("gui.worldhandler.potions.effect.tipped_arrow")));
			container.add(button8 = new GuiButtonWorldHandler(11, x + 178, y + 24, 55, 20, I18n.format("gui.worldhandler.potions.effect.bottle"), I18n.format("gui.worldhandler.actions.place_command_block"), EnumTooltip.TOP_RIGHT));
			container.add(button9 = new GuiButtonWorldHandler(13, x + 118, y + 48, 56, 20, I18n.format("gui.worldhandler.potions.effect.splash"), I18n.format("gui.worldhandler.actions.place_command_block"), EnumTooltip.TOP_RIGHT));
			container.add(button10 = new GuiButtonWorldHandler(12, x + 178, y + 48, 55, 20, I18n.format("gui.worldhandler.potions.effect.lingering"), I18n.format("gui.worldhandler.actions.place_command_block"), EnumTooltip.TOP_RIGHT));
			
			boolean enabled = this.builderPotion.getAmplifier() >= 0;
			
			button6.enabled = enabled;
			button7.enabled = enabled;
			button8.enabled = enabled;
			button9.enabled = enabled;
			button10.enabled = enabled;
		}
		
		if(this.potionPage > 0)
		{
			container.add(new GuiButtonWorldHandler(4, x + 118, y + 72, 56, 20, "<"));
			container.add(button6 = new GuiButtonWorldHandler(5, x + 118 + 60, y + 72, 55, 20, ">"));
			
			button6.enabled = this.potionPage < 3;
		}
	}
	
	@Override
	public void actionPerformed(Container container, GuiButton button)
	{
		Potion potion = this.builderPotion.getEffectAsPotion();
		
		switch(button.id)
		{
			case 4:
				this.potionPage--;
				container.initGui();
				break;
			case 5:
				this.potionPage++;
				container.initGui();
				break;
			case 6:
				WorldHandler.sendCommand(this.builderPotion.getRemoveCommand());
				container.initGui();
				break;
			case 7:
				WorldHandler.sendCommand(this.builderPotion.getClearCommand());
				container.initGui();
				break;
			case 8:
				this.builderPotionItem.setAmbient(potion, !this.builderPotionItem.getAmbient(potion));
				container.initGui();
				break;
			case 9:
				this.builderPotion.setHideParticles(!this.builderPotion.getHideParticles());
				this.builderPotionItem.setShowParticles(potion, !this.builderPotionItem.getShowParticles(potion));
				container.initGui();
				break;
			case 10:
				WorldHandler.sendCommand(this.builderPotion);
				this.potionPage = 0;
				container.initGui();
				break;
			case 11:
				WorldHandler.sendCommand(this.builderPotionItem.getBuilderForPotion(Items.POTIONITEM));
				this.potionPage = 0;
				container.initGui();
				break;
			case 12:
				WorldHandler.sendCommand(this.builderPotionItem.getBuilderForPotion(Items.LINGERING_POTION));
				this.potionPage = 0;
				container.initGui();
				break;
			case 13:
				WorldHandler.sendCommand(this.builderPotionItem.getBuilderForPotion(Items.SPLASH_POTION));
				this.potionPage = 0;
				container.initGui();
				break;
			case 14:
				WorldHandler.sendCommand(this.builderPotionItem.getBuilderForPotion(Items.TIPPED_ARROW));
				this.potionPage = 0;
				container.initGui();
				break;
			default:
				break;
		}
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
