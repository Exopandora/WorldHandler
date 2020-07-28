package exopandora.worldhandler.gui.content.impl;

import java.util.stream.Collectors;

import com.google.common.base.Predicates;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.button.GuiButtonBase;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.container.impl.GuiWorldHandler;
import exopandora.worldhandler.util.ActionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

@OnlyIn(Dist.CLIENT)
public class ContentButcherPresets extends ContentChild
{
	private ICommandBuilder builder;
	private int radius;
	
	public ContentButcherPresets withBuilder(ICommandBuilder builder)
	{
		this.builder = builder;
		return this;
	}
	
	public ContentButcherPresets withRadius(int radius)
	{
		this.radius = radius;
		return this;
	}
	
	@Override
	public ICommandBuilder getCommandBuilder()
	{
		return this.builder;
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.add(new GuiButtonBase(x, y + 96, 114, 20, new TranslationTextComponent("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, new TranslationTextComponent("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.add(new GuiButtonBase(x + 58, y, 114, 20, new TranslationTextComponent("gui.worldhandler.butcher.presets.passive_mobs"), () ->
		{
			ContentButcher.slaughter(Config.getButcher().getEntities().stream().map(ForgeRegistries.ENTITIES::getValue).filter(Predicates.notNull()).collect(Collectors.toList()), this.radius);
			Minecraft.getInstance().displayGuiScreen(new GuiWorldHandler(this.getParentContent()));
		}));
		container.add(new GuiButtonBase(x + 58, y + 24, 114, 20, new TranslationTextComponent("gui.worldhandler.butcher.presets.hostile_mobs"), () ->
		{
			ContentButcher.slaughter(ForgeRegistries.ENTITIES.getValues().stream().filter(entity -> !EntityClassification.MISC.equals(entity.getClassification()) && !EntityType.PLAYER.equals(entity)).collect(Collectors.toList()), this.radius);
			Minecraft.getInstance().displayGuiScreen(new GuiWorldHandler(this.getParentContent()));
		}));
		container.add(new GuiButtonBase(x + 58, y + 72, 114, 20, new TranslationTextComponent("gui.worldhandler.butcher.presets.entities"), () ->
		{
			ContentButcher.slaughter(ForgeRegistries.ENTITIES.getValues().stream().filter(entity -> EntityClassification.MISC.equals(entity.getClassification()) && !EntityType.PLAYER.equals(entity)).collect(Collectors.toList()), this.radius);
			Minecraft.getInstance().displayGuiScreen(new GuiWorldHandler(this.getParentContent()));
		}));
		container.add(new GuiButtonBase(x + 58, y + 48, 114, 20, new TranslationTextComponent("gui.worldhandler.butcher.presets.players"), () ->
		{
			ContentButcher.slaughter(ForgeRegistries.ENTITIES.getValues().stream().filter(entity -> EntityType.PLAYER.equals(entity)).collect(Collectors.toList()), this.radius);
			Minecraft.getInstance().displayGuiScreen(new GuiWorldHandler(this.getParentContent()));
		}));
	}
}
