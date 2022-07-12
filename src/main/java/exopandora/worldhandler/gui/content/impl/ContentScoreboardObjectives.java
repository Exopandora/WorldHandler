package exopandora.worldhandler.gui.content.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicates;
import com.mojang.blaze3d.vertex.PoseStack;

import exopandora.worldhandler.builder.impl.ScoreboardCommandBuilder;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.widget.menu.impl.ILogicButtonList;
import exopandora.worldhandler.gui.widget.menu.impl.MenuButtonList;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.CommandHelper;
import exopandora.worldhandler.util.RegistryHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatType;
import net.minecraft.stats.Stats;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class ContentScoreboardObjectives extends ContentScoreboard
{
	private GuiTextFieldTooltip objectField;
	private Page page = Page.CREATE;
	
	@Override
	public CommandPreview getCommandPreview()
	{
		return new CommandPreview(BUILDER, this.page.getLabel());
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.objectField = new GuiTextFieldTooltip(x + 118, y + this.page.getShift(), 114, 20, Component.translatable("gui.worldhandler.scoreboard.objectives.objective"));
		this.objectField.setFilter(Predicates.notNull());
		this.objectField.setResponder(text ->
		{
			BUILDER.objective().set(text);
			BUILDER.displayName().deserialize(text);
			container.initButtons();
		});
		this.objectField.setValue(BUILDER.objective().get());
		
		if(Page.CREATE.equals(this.page))
		{
			MenuButtonList objectives = new MenuButtonList(x + 118, y + 24, HELPER.getObjectives(), 2, new ILogicButtonList()
			{
				@Override
				public MutableComponent translate(String key, int depth)
				{
					ResourceLocation resource = this.makeResourceLocation(key);
					
					if(resource != null)
					{
						StatType<?> type = ForgeRegistries.STAT_TYPES.getValue(resource);
						
						if(type != null)
						{
							if(type.equals(Stats.CUSTOM))
							{
								return Component.translatable("gui.worldhandler.scoreboard.objectives.stat.custom");
							}
							else if(type.equals(Stats.ENTITY_KILLED))
							{
								return Component.translatable("gui.worldhandler.scoreboard.objectives.stat.killed");
							}
							else if(type.equals(Stats.ENTITY_KILLED_BY))
							{
								return Component.translatable("gui.worldhandler.scoreboard.objectives.stat.killed_by");
							}
							
							return Component.translatable(type.getTranslationKey());
						}
						
						String translation = RegistryHelper.translate(resource);
						
						if(translation != null)
						{
							return Component.translatable(translation);
						}
					}
					
					String translation = "stat." + key;
					
					if(!translation.equals(I18n.get(translation)))
					{
						return Component.translatable(translation);
					}
					
					if(Arrays.stream(ChatFormatting.values()).map(ChatFormatting::getName).anyMatch(Predicates.equalTo(key)))
					{
						return Component.translatable("gui.worldhandler.color." + key);
					}
					
					return Component.translatable("gui.worldhandler.scoreboard.objectives.stat." + key);
				}
				
				@Override
				public void onClick(String key, int depth)
				{
					BUILDER.criteria().set(key);
				}
				
				@Override
				public String buildEventKey(List<String> keys, int depth)
				{
					if(this.isRegistryItem(keys.get(keys.size() - 1)))
					{
						return String.join(":", keys);
					}
					
					return ILogicButtonList.super.buildEventKey(keys, depth);
				}
				
				@Override
				public String getId()
				{
					return "objectives";
				}
				
				@Nullable
				private ResourceLocation makeResourceLocation(String key)
				{
					return ResourceLocation.tryParse(key.replace(".", ":"));
				}
				
				@Nullable
				private boolean isRegistryItem(String key)
				{
					return this.isRegistryItem(this.makeResourceLocation(key));
				}
				
				@Nullable
				private boolean isRegistryItem(ResourceLocation resource)
				{
					IForgeRegistry<?>[] registries = new IForgeRegistry<?>[] {ForgeRegistries.BLOCKS, ForgeRegistries.ITEMS, ForgeRegistries.ENTITY_TYPES};
					
					for(IForgeRegistry<?> registry : registries)
					{
						if(registry.containsKey(resource))
						{
							return true;
						}
					}
					
					return ForgeRegistries.STAT_TYPES.containsKey(resource);
				}
			});
			
			container.add(objectives);
		}
		else if(Page.DISPLAY.equals(this.page) || Page.UNDISPLAY.equals(this.page))
		{
			MenuButtonList slots = new MenuButtonList(x + 118, y + 24 - this.page.getShift(), HELPER.getSlots(), 2, new ILogicButtonList()
			{
				@Override
				public MutableComponent translate(String key, int depth)
				{
					if(depth == 0)
					{
						return Component.translatable("gui.worldhandler.scoreboard.slot." + key);
					}
					else if(depth == 1)
					{
						return Component.translatable("gui.worldhandler.color." + key); 
					}
					
					return Component.literal(key);
				}
				
				@Override
				public void onClick(String key, int depth)
				{
					BUILDER.slot().set(key);
				}
				
				@Override
				public String getId()
				{
					return "slots";
				}
			});
			
			container.add(slots);
		}
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		GuiButtonBase button1;
		GuiButtonBase button2;
		GuiButtonBase button3;
		GuiButtonBase button4;
		
		container.add(new GuiButtonBase(x, y + 96, 114, 20, Component.translatable("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, Component.translatable("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.add(button1 = new GuiButtonBase(x, y, 114, 20, Component.translatable("gui.worldhandler.scoreboard.objectives.create"), () ->
		{
			this.page = Page.CREATE;
			container.init();
		}));
		container.add(button2 = new GuiButtonBase(x, y + 24, 114, 20, Component.translatable("gui.worldhandler.scoreboard.objectives.display"), () ->
		{
			this.page = Page.DISPLAY;
			container.init();
		}));
		container.add(button3 = new GuiButtonBase(x, y + 48, 114, 20, Component.translatable("gui.worldhandler.scoreboard.objectives.undisplay"), () ->
		{
			this.page = Page.UNDISPLAY;
			container.init();
		}));
		container.add(button4 = new GuiButtonBase(x, y + 72, 114, 20, Component.translatable("gui.worldhandler.scoreboard.objectives.remove"), () ->
		{
			this.page = Page.REMOVE;
			container.init();
		}));
		
		button1.active = !Page.CREATE.equals(this.page);
		button2.active = !Page.DISPLAY.equals(this.page);
		button3.active = !Page.UNDISPLAY.equals(this.page);
		button4.active = !Page.REMOVE.equals(this.page);
		
		if(!Page.UNDISPLAY.equals(this.page))
		{
			container.add(this.objectField);
		}
		
		container.add(button1 = new GuiButtonBase(x + 118, y + 72 - this.page.getShift(), 114, 20, Component.translatable("gui.worldhandler.actions.perform"), () ->
		{
			CommandHelper.sendCommand(container.getPlayer(), BUILDER, this.page.getLabel());
			container.init();
		}));
		button1.active = Page.UNDISPLAY.equals(this.page) || BUILDER.objective().get() != null && !BUILDER.objective().get().isEmpty();
	}
	
	@Override
	public void tick(Container container)
	{
		if(!Page.UNDISPLAY.equals(this.page))
		{
			this.objectField.tick();
		}
	}
	
	@Override
	public void drawScreen(PoseStack matrix, Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		if(!Page.UNDISPLAY.equals(this.page))
		{
			this.objectField.renderButton(matrix, mouseX, mouseY, partialTicks);
		}
	}
	
	@Override
	public MutableComponent getTabTitle()
	{
		return Component.translatable("gui.worldhandler.tab.scoreboard.objectives");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.SCOREBOARD_OBJECTIVES;
	}
	
	public static enum Page
	{
		CREATE(0, ScoreboardCommandBuilder.Label.OBJECTIVES_ADD_DISPLAYNAME),
		DISPLAY(0, ScoreboardCommandBuilder.Label.OBJECTIVES_SETDISPLAY_SLOT_OBJECTIVE),
		UNDISPLAY(12, ScoreboardCommandBuilder.Label.OBJECTIVES_SETDISPLAY_SLOT),
		REMOVE(24, ScoreboardCommandBuilder.Label.OBJECTIVES_REMOVE);
		
		private final int shift;
		private final ScoreboardCommandBuilder.Label label;
		
		private Page(int shift, ScoreboardCommandBuilder.Label label)
		{
			this.shift = shift;
			this.label = label;
		}
		
		public int getShift()
		{
			return this.shift;
		}
		
		public ScoreboardCommandBuilder.Label getLabel()
		{
			return this.label;
		}
	}
}