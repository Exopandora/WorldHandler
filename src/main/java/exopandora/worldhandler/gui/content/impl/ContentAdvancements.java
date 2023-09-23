package exopandora.worldhandler.gui.content.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import exopandora.worldhandler.builder.impl.AdvancementCommandBuilder;
import exopandora.worldhandler.builder.impl.AdvancementCommandBuilder.Label;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiButtonList;
import exopandora.worldhandler.gui.widget.button.GuiButtonTooltip;
import exopandora.worldhandler.gui.widget.menu.impl.ILogicMapped;
import exopandora.worldhandler.gui.widget.menu.impl.ILogicPageList;
import exopandora.worldhandler.gui.widget.menu.impl.MenuPageList;
import exopandora.worldhandler.util.ActionHandler;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.AdvancementHelper;
import exopandora.worldhandler.util.CommandHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class ContentAdvancements extends Content
{
	private final AdvancementCommandBuilder builderAdvancement = new AdvancementCommandBuilder();
	private Mode mode = Mode.ONLY;
	
	@Override
	public CommandPreview getCommandPreview()
	{
		CommandPreview preview = new CommandPreview()
				.add(this.builderAdvancement, this.mode.getGrant())
				.add(this.builderAdvancement, this.mode.getRevoke());
		return preview;
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		List<AdvancementHolder> advancements = AdvancementHelper.getInstance().getAdvancements().stream()
				.filter(advancement -> advancement.value().display().isPresent())
				.collect(Collectors.toList());
		
		MenuPageList<AdvancementHolder> list = new MenuPageList<AdvancementHolder>(x, y, advancements, 114, 20, 3, container, new ILogicPageList<AdvancementHolder>()
		{
			@Override
			public MutableComponent translate(AdvancementHolder item)
			{
				return (MutableComponent) item.value().display().get().getTitle();
			}
			
			@Override
			public MutableComponent toTooltip(AdvancementHolder item)
			{
				return Component.literal(item.id().toString());
			}
			
			@Override
			public void onClick(AdvancementHolder item)
			{
				ContentAdvancements.this.builderAdvancement.advancement().set(item.id());
				container.initButtons();
			}
			
			@Override
			public GuiButtonBase onRegister(int x, int y, int width, int height, MutableComponent text, AdvancementHolder item, ActionHandler actionHandler)
			{
				return new GuiButtonTooltip(x, y, width, height, text, this.toTooltip(item), actionHandler);
			}
			
			@Override
			public String getId()
			{
				return "advancement";
			}
		});
		
		container.addMenu(list);
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.addRenderableWidget(new GuiButtonBase(x, y + 96, 114, 20, Component.translatable("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.addRenderableWidget(new GuiButtonBase(x + 118, y + 96, 114, 20, Component.translatable("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.addRenderableWidget(new GuiButtonList<Mode>(x + 118, y, Arrays.asList(Mode.values()), 114, 20, container, new ILogicMapped<Mode>()
		{
			@Override
			public MutableComponent translate(Mode mode)
			{
				return Component.translatable("gui.worldhandler.advancements." + mode.toString());
			}
			
			@Override
			public MutableComponent toTooltip(Mode mode)
			{
				return Component.literal(mode.toString());
			}
			
			@Override
			public void onClick(Mode mode)
			{
				ContentAdvancements.this.mode = mode;
			}
			
			@Override
			public String getId()
			{
				return "mode";
			}
		}));
		
		container.addRenderableWidget(new GuiButtonBase(x + 118, y + 24, 114, 20, Component.translatable("gui.worldhandler.advancements.grant"), () ->
		{
			CommandHelper.sendCommand(container.getPlayer(), this.builderAdvancement, this.mode.getGrant());
		}));
		container.addRenderableWidget(new GuiButtonBase(x + 118, y + 48, 114, 20, Component.translatable("gui.worldhandler.advancements.revoke"), () ->
		{
			CommandHelper.sendCommand(container.getPlayer(), this.builderAdvancement, this.mode.getRevoke());
		}));
		container.addRenderableWidget(new GuiButtonBase(x + 118, y + 72, 114, 20, Component.translatable("gui.worldhandler.actions.reset").withStyle(ChatFormatting.RED), () ->
		{
			ActionHelper.open(Contents.CONTINUE.withBuilder(this.builderAdvancement, AdvancementCommandBuilder.Label.REVOKE_EVERYTHING));
		}));
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.PLAYER;
	}
	
	@Override
	public MutableComponent getTitle()
	{
		return Component.translatable("gui.worldhandler.title.player.advancements");
	}
	
	@Override
	public MutableComponent getTabTitle()
	{
		return Component.translatable("gui.worldhandler.tab.player.advancements");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.ADVANCEMENTS;
	}
	
	@Override
	public void onPlayerNameChanged(String username)
	{
		this.builderAdvancement.targets().setTarget(username);
	}
	
	private static enum Mode
	{
		ONLY(AdvancementCommandBuilder.Label.GRANT_ONLY, AdvancementCommandBuilder.Label.REVOKE_ONLY),
		UNTIL(AdvancementCommandBuilder.Label.GRANT_UNTIL, AdvancementCommandBuilder.Label.REVOKE_UNTIL),
		FROM(AdvancementCommandBuilder.Label.GRANT_FROM, AdvancementCommandBuilder.Label.REVOKE_FROM),
		THROUGH(AdvancementCommandBuilder.Label.GRANT_THROUGH, AdvancementCommandBuilder.Label.REVOKE_THROUGH);
		
		private final AdvancementCommandBuilder.Label grant;
		private final AdvancementCommandBuilder.Label revoke;
		
		private Mode(Label grant, Label revoke)
		{
			this.grant = grant;
			this.revoke = revoke;
		}
		
		public AdvancementCommandBuilder.Label getGrant()
		{
			return this.grant;
		}
		
		public AdvancementCommandBuilder.Label getRevoke()
		{
			return this.revoke;
		}
		
		@Override
		public String toString()
		{
			return this.name().toLowerCase();
		}
	}
}
