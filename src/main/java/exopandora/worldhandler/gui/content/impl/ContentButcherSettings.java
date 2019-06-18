package exopandora.worldhandler.gui.content.impl;

import java.util.List;
import java.util.stream.Collectors;

import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.button.GuiButtonBase;
import exopandora.worldhandler.gui.button.GuiButtonTooltip;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.element.impl.ElementPageList;
import exopandora.worldhandler.gui.content.impl.abstr.ContentChild;
import exopandora.worldhandler.gui.logic.ILogicPageList;
import exopandora.worldhandler.helper.ActionHelper;
import exopandora.worldhandler.util.ActionHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

@OnlyIn(Dist.CLIENT)
public class ContentButcherSettings extends ContentChild
{
	private ResourceLocation entity;
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		List<EntityType<?>> list = ForgeRegistries.ENTITIES.getValues().stream().filter(EntityType::isSummonable).collect(Collectors.toList());
		
		ElementPageList<EntityType<?>> entities = new ElementPageList<EntityType<?>>(x, y, list, 114, 20, 3, container, new ILogicPageList<EntityType<?>>()
		{
			@Override
			public String translate(EntityType<?> item)
			{
				return I18n.format(item.getTranslationKey());
			}
			
			@Override
			public String toTooltip(EntityType<?> item)
			{
				return item.getRegistryName().toString();
			}
			
			@Override
			public void onClick(EntityType<?> item)
			{
				ContentButcherSettings.this.entity = item.getRegistryName();
				container.initButtons();
			}
			
			@Override
			public GuiButtonBase onRegister(int x, int y, int width, int height, String text, EntityType<?> item, ActionHandler actionHandler)
			{
				return new GuiButtonTooltip(x, y, width, height, text, this.toTooltip(item), actionHandler);
			}
			
			@Override
			public String getId()
			{
				return "entities";
			}
		});
		
		container.add(entities);
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		GuiButtonBase button1;
		GuiButtonBase button2;
		
		container.add(new GuiButtonBase(x, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.add(button1 = new GuiButtonBase(x + 118, y + 24, 114, 20, I18n.format("gui.worldhandler.generic.enable"), () ->
		{
			Config.CLIENT.getButcher().addEntity(this.entity);
			container.initButtons();
		}));
		container.add(button2 = new GuiButtonBase(x + 118, y + 48, 114, 20, I18n.format("gui.worldhandler.generic.disable"), () ->
		{
			Config.CLIENT.getButcher().removeEntity(this.entity);
			container.initButtons();
		}));
		
		boolean contains = Config.CLIENT.getButcher().containsEntity(this.entity);
		
		button1.active = !contains;
		button2.active = contains;
	}
	
	@Override
	public String getTitle()
	{
		return this.parent.getTitle();
	}
}
