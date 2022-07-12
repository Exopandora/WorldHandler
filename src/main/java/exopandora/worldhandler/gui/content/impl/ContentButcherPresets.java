package exopandora.worldhandler.gui.content.impl;

import java.util.stream.Collectors;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.util.ActionHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.ForgeRegistries;

public class ContentButcherPresets extends ContentChild
{
	private CommandPreview preview;
	private double radius;
	
	public ContentButcherPresets withBuilder(ICommandBuilder builder, Object label)
	{
		this.preview = new CommandPreview(builder, label);
		return this;
	}
	
	public ContentButcherPresets withRadius(double radius)
	{
		this.radius = radius;
		return this;
	}
	
	@Override
	public CommandPreview getCommandPreview()
	{
		return this.preview;
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.add(new GuiButtonBase(x, y + 96, 114, 20, Component.translatable("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, Component.translatable("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.add(new GuiButtonBase(x + 58, y, 114, 20, Component.translatable("gui.worldhandler.butcher.presets.passive_mobs"), () ->
		{
			ContentButcher.slaughter(container.getPlayer(), ForgeRegistries.ENTITY_TYPES.getValues().stream().filter(entity -> !MobCategory.MONSTER.equals(entity.getCategory()) && !MobCategory.MISC.equals(entity.getCategory())).collect(Collectors.toList()), this.radius);
			ActionHelper.open(this.getParentContent());
		}));
		container.add(new GuiButtonBase(x + 58, y + 24, 114, 20, Component.translatable("gui.worldhandler.butcher.presets.hostile_mobs"), () ->
		{
			ContentButcher.slaughter(container.getPlayer(), ForgeRegistries.ENTITY_TYPES.getValues().stream().filter(entity -> MobCategory.MONSTER.equals(entity.getCategory())).collect(Collectors.toList()), this.radius);
			ActionHelper.open(this.getParentContent());
		}));
		container.add(new GuiButtonBase(x + 58, y + 48, 114, 20, Component.translatable("gui.worldhandler.butcher.presets.players"), () ->
		{
			ContentButcher.slaughter(container.getPlayer(), ForgeRegistries.ENTITY_TYPES.getValues().stream().filter(entity -> EntityType.PLAYER.equals(entity)).collect(Collectors.toList()), this.radius);
			ActionHelper.open(this.getParentContent());
		}));
		container.add(new GuiButtonBase(x + 58, y + 72, 114, 20, Component.translatable("gui.worldhandler.butcher.presets.entities"), () ->
		{
			ContentButcher.slaughter(container.getPlayer(), ForgeRegistries.ENTITY_TYPES.getValues().stream().filter(entity -> MobCategory.MISC.equals(entity.getCategory()) && !EntityType.PLAYER.equals(entity)).collect(Collectors.toList()), this.radius);
			ActionHelper.open(this.getParentContent());
		}));
	}
}
