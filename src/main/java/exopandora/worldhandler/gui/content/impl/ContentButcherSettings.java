package exopandora.worldhandler.gui.content.impl;

import java.util.List;
import java.util.stream.Collectors;

import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiButtonTooltip;
import exopandora.worldhandler.gui.widget.menu.impl.ILogicPageList;
import exopandora.worldhandler.gui.widget.menu.impl.MenuPageList;
import exopandora.worldhandler.util.ActionHandler;
import exopandora.worldhandler.util.ActionHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

public class ContentButcherSettings extends ContentChild
{
	private ResourceLocation entity;
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		List<EntityType<?>> list = ForgeRegistries.ENTITY_TYPES.getValues().stream().filter(EntityType::canSummon).collect(Collectors.toList());
		
		MenuPageList<EntityType<?>> entities = new MenuPageList<EntityType<?>>(x, y, list, 114, 20, 3, container, new ILogicPageList<EntityType<?>>()
		{
			@Override
			public MutableComponent translate(EntityType<?> item)
			{
				return Component.translatable(item.getDescriptionId());
			}
			
			@Override
			public MutableComponent toTooltip(EntityType<?> item)
			{
				return Component.literal(ForgeRegistries.ENTITY_TYPES.getKey(item).toString());
			}
			
			@Override
			public void onClick(EntityType<?> item)
			{
				ContentButcherSettings.this.entity = ForgeRegistries.ENTITY_TYPES.getKey(item);
				container.initButtons();
			}
			
			@Override
			public GuiButtonBase onRegister(int x, int y, int width, int height, MutableComponent text, EntityType<?> item, ActionHandler actionHandler)
			{
				return new GuiButtonTooltip(x, y, width, height, text, this.toTooltip(item), actionHandler);
			}
			
			@Override
			public String getId()
			{
				return "entities";
			}
		});
		
		container.addMenu(entities);
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		GuiButtonBase button1;
		GuiButtonBase button2;
		
		container.addRenderableWidget(new GuiButtonBase(x, y + 96, 114, 20, Component.translatable("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.addRenderableWidget(new GuiButtonBase(x + 118, y + 96, 114, 20, Component.translatable("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.addRenderableWidget(button1 = new GuiButtonBase(x + 118, y + 24, 114, 20, Component.translatable("gui.worldhandler.generic.enable"), () ->
		{
			Config.CLIENT.getButcher().addEntity(this.entity);
			container.initButtons();
		}));
		container.addRenderableWidget(button2 = new GuiButtonBase(x + 118, y + 48, 114, 20, Component.translatable("gui.worldhandler.generic.disable"), () ->
		{
			Config.CLIENT.getButcher().removeEntity(this.entity);
			container.initButtons();
		}));
		
		boolean contains = Config.CLIENT.getButcher().containsEntity(this.entity);
		
		button1.active = !contains;
		button2.active = contains;
	}
}
