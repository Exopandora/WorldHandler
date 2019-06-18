package exopandora.worldhandler.gui.content.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderAdvancement;
import exopandora.worldhandler.builder.impl.BuilderAdvancement.EnumActionType;
import exopandora.worldhandler.builder.impl.BuilderAdvancement.EnumMode;
import exopandora.worldhandler.gui.button.GuiButtonBase;
import exopandora.worldhandler.gui.button.GuiButtonList;
import exopandora.worldhandler.gui.button.GuiButtonTooltip;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.container.impl.GuiWorldHandler;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.content.element.impl.ElementPageList;
import exopandora.worldhandler.gui.logic.ILogicMapped;
import exopandora.worldhandler.gui.logic.ILogicPageList;
import exopandora.worldhandler.helper.ActionHelper;
import exopandora.worldhandler.helper.CommandHelper;
import exopandora.worldhandler.util.ActionHandler;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ContentAdvancements extends Content
{
	private final BuilderAdvancement builderAdvancement = new BuilderAdvancement(EnumMode.values()[0]);
	private final List<EnumMode> modes = Arrays.stream(EnumMode.values()).filter(mode -> !mode.equals(EnumMode.EVERYTHING)).collect(Collectors.toList());
	
	@Override
	public ICommandBuilder getCommandBuilder()
	{
		return this.builderAdvancement;
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		List<Advancement> advancements = new AdvancementManager().getAllAdvancements().stream()
				.filter(advancement -> advancement.getDisplay() != null)
				.collect(Collectors.toList());
		
		ElementPageList<Advancement> list = new ElementPageList<Advancement>(x, y, advancements, 114, 20, 3, container, new ILogicPageList<Advancement>()
		{
			@Override
			public String translate(Advancement item)
			{
				return item.getDisplay().getTitle().getString();
			}
			
			@Override
			public String toTooltip(Advancement item)
			{
				return item.getId().toString();
			}
			
			@Override
			public void onClick(Advancement item)
			{
				ContentAdvancements.this.builderAdvancement.setAdvancement(item.getId());
				container.initButtons();
			}
			
			@Override
			public GuiButtonBase onRegister(int x, int y, int width, int height, String text, Advancement item, ActionHandler actionHandler)
			{
				return new GuiButtonTooltip(x, y, width, height, text, this.toTooltip(item), actionHandler);
			}
			
			@Override
			public String getId()
			{
				return "advancement";
			}
		});
		
		container.add(list);
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.add(new GuiButtonBase(x, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.add(new GuiButtonList<EnumMode>(x + 118, y, this.modes, 114, 20, container, new ILogicMapped<EnumMode>()
		{
			@Override
			public String translate(EnumMode item)
			{
				return I18n.format("gui.worldhandler.advancements." + item.toString());
			}
			
			@Override
			public String toTooltip(EnumMode item)
			{
				return item.toString();
			}
			
			@Override
			public void onClick(EnumMode item)
			{
				ContentAdvancements.this.builderAdvancement.setMode(item);
			}
			
			@Override
			public String getId()
			{
				return "mode";
			}
		}));
		
		container.add(new GuiButtonBase(x + 118, y + 24, 114, 20, I18n.format("gui.worldhandler.advancements.grant"), () ->
		{
			CommandHelper.sendCommand(this.builderAdvancement.getBuilderForAction(EnumActionType.GRANT));
		}));
		container.add(new GuiButtonBase(x + 118, y + 48, 114, 20, I18n.format("gui.worldhandler.advancements.revoke"), () ->
		{
			CommandHelper.sendCommand(this.builderAdvancement.getBuilderForAction(EnumActionType.REVOKE));
		}));
		container.add(new GuiButtonBase(x + 118, y + 72, 114, 20, TextFormatting.RED + I18n.format("gui.worldhandler.actions.reset"), () ->
		{
			Minecraft.getInstance().displayGuiScreen(new GuiWorldHandler(Contents.CONTINUE.withBuilder(this.builderAdvancement.getBuilder(EnumActionType.REVOKE, EnumMode.EVERYTHING)).withParent(Contents.ADVANCEMENTS)));
		}));
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.PLAYER;
	}
	
	@Override
	public String getTitle()
	{
		return I18n.format("gui.worldhandler.title.player.advancements");
	}
	
	@Override
	public String getTabTitle()
	{
		return I18n.format("gui.worldhandler.tab.player.advancements");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.ADVANCEMENTS;
	}
	
	@Override
	public void onPlayerNameChanged(String username)
	{
		this.builderAdvancement.setPlayer(username);
	}
}
