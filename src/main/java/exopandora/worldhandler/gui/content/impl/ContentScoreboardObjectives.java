package exopandora.worldhandler.gui.content.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicates;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderScoreboardObjectives;
import exopandora.worldhandler.builder.impl.BuilderScoreboardObjectives.EnumMode;
import exopandora.worldhandler.format.EnumColor;
import exopandora.worldhandler.gui.button.GuiButtonBase;
import exopandora.worldhandler.gui.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.content.element.impl.ElementMultiButtonList;
import exopandora.worldhandler.gui.content.impl.abstr.ContentScoreboard;
import exopandora.worldhandler.gui.logic.ILogicClickList;
import exopandora.worldhandler.helper.ActionHelper;
import exopandora.worldhandler.helper.CommandHelper;
import exopandora.worldhandler.helper.RegistryTranslator;
import net.minecraft.client.resources.I18n;
import net.minecraft.stats.StatType;
import net.minecraft.stats.Stats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

@OnlyIn(Dist.CLIENT)
public class ContentScoreboardObjectives extends ContentScoreboard
{
	private GuiTextFieldTooltip objectField;
	private String selectedObjective = "create";
	
	private final BuilderScoreboardObjectives builderObjectives = new BuilderScoreboardObjectives();
	
	@Override
	public ICommandBuilder getCommandBuilder()
	{
		return this.builderObjectives;
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.objectField = new GuiTextFieldTooltip(x + 118, y + (this.selectedObjective.equals("remove") ? 24 : 0), 114, 20, I18n.format("gui.worldhandler.scoreboard.objectives.objective"));
		this.objectField.setValidator(Predicates.notNull());
		this.objectField.setText(ContentScoreboard.getObjective());
		this.objectField.func_212954_a(text ->
		{
			ContentScoreboard.setObjective(text);
			this.builderObjectives.setObjective(ContentScoreboard.getObjective());
			container.initButtons();
		});
		
		if(this.selectedObjective.equals("create"))
		{
			ElementMultiButtonList objectives = new ElementMultiButtonList(x + 118, y + 24, HELPER.getObjectives(), 2, new ILogicClickList()
			{
				@Override
				public String translate(String key, int depth)
				{
					ResourceLocation resource = this.makeResourceLocation(key);
					
					if(resource != null)
					{
						StatType<?> type = ForgeRegistries.STAT_TYPES.getValue(resource);
						
						if(type != null)
						{
							if(type.equals(Stats.CUSTOM))
							{
								return I18n.format("gui.worldhandler.scoreboard.objectives.stat.custom");
							}
							else if(type.equals(Stats.ENTITY_KILLED))
							{
								return I18n.format("gui.worldhandler.scoreboard.objectives.stat.killed");
							}
							else if(type.equals(Stats.ENTITY_KILLED_BY))
							{
								return I18n.format("gui.worldhandler.scoreboard.objectives.stat.killed_by");
							}
							
							return I18n.format(type.getTranslationKey());
						}
						
						String translation = RegistryTranslator.translate(resource);
						
						if(translation != null)
						{
							return I18n.format(translation);
						}
					}
					
					if(Arrays.stream(EnumColor.values()).map(EnumColor::getName).anyMatch(Predicates.equalTo(key)))
					{
						return I18n.format("gui.worldhandler.color." + key);
					}
					
					return I18n.format("gui.worldhandler.scoreboard.objectives.stat." + key);
				}
				
				@Override
				public void onClick(String key, int depth)
				{
					ContentScoreboardObjectives.this.builderObjectives.setCriteria(key);
				}
				
				@Override
				public String buildEventKey(List<String> keys, int depth)
				{
					if(this.isRegistryItem(keys.get(keys.size() - 1)))
					{
						return String.join(":", keys);
					}
					
					return ILogicClickList.super.buildEventKey(keys, depth);
				}
				
				@Override
				public String getId()
				{
					return "objectives";
				}
				
				@Nullable
				private ResourceLocation makeResourceLocation(String key)
				{
					return ResourceLocation.tryCreate(key.replace(".", ":"));
				}
				
				@Nullable
				private boolean isRegistryItem(String key)
				{
					return this.isRegistryItem(this.makeResourceLocation(key));
				}
				
				@Nullable
				private boolean isRegistryItem(ResourceLocation resource)
				{
					IForgeRegistry<?>[] registries = new IForgeRegistry<?>[] {ForgeRegistries.BLOCKS, ForgeRegistries.ITEMS, ForgeRegistries.ENTITIES};
					
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
		else if(this.selectedObjective.equals("display") || this.selectedObjective.equals("undisplay"))
		{
			ElementMultiButtonList slots = new ElementMultiButtonList(x + 118, y + 24 + (this.selectedObjective.equals("undisplay") ? -12 : 0), HELPER.getSlots(), 2, new ILogicClickList()
			{
				@Override
				public String translate(String key, int depth)
				{
					if(depth == 0)
					{
						return I18n.format("gui.worldhandler.scoreboard.slot." + key);
					}
					else if(depth == 1)
					{
						return I18n.format("gui.worldhandler.color." + key); 
					}
					
					return key;
				}
				
				@Override
				public void onClick(String key, int depth)
				{
					ContentScoreboardObjectives.this.builderObjectives.setSlot(key);
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
		
		container.add(new GuiButtonBase(x, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.add(button1 = new GuiButtonBase(x, y, 114, 20, I18n.format("gui.worldhandler.scoreboard.objectives.create"), () ->
		{
			this.selectedObjective = "create";
			container.init();
		}));
		container.add(button2 = new GuiButtonBase(x, y + 24, 114, 20, I18n.format("gui.worldhandler.scoreboard.objectives.display"), () ->
		{
			this.selectedObjective = "display";
			container.init();
		}));
		container.add(button3 = new GuiButtonBase(x, y + 48, 114, 20, I18n.format("gui.worldhandler.scoreboard.objectives.undisplay"), () ->
		{
			this.selectedObjective = "undisplay";
			container.init();
		}));
		container.add(button4 = new GuiButtonBase(x, y + 72, 114, 20, I18n.format("gui.worldhandler.scoreboard.objectives.remove"), () ->
		{
			this.selectedObjective = "remove";
			container.init();
		}));
		
		button1.active = !this.selectedObjective.equals("create");
		button2.active = !this.selectedObjective.equals("display");
		button3.active = !this.selectedObjective.equals("undisplay");
		button4.active = !this.selectedObjective.equals("remove");
		
		int yOffset = this.selectedObjective.equals("undisplay") ? -12 : (this.selectedObjective.equals("remove") ? -24 : 0);
		
		if(this.selectedObjective.equals("undisplay"))
		{
			this.builderObjectives.setObjective(null);
		}
		else if(this.selectedObjective.equals("remove"))
		{
			this.builderObjectives.setMode(EnumMode.REMOVE);
		}
		
		if(!this.selectedObjective.equals("undisplay"))
		{
			container.add(this.objectField);
			this.builderObjectives.setObjective(ContentScoreboard.getObjective());
		}
		
		container.add(button1 = new GuiButtonBase(x + 118, y + 72 + yOffset, 114, 20, I18n.format("gui.worldhandler.actions.perform"), () ->
		{
			CommandHelper.sendCommand(this.builderObjectives);
			container.init();
		}));
		button1.active = this.selectedObjective.equals("undisplay") || ContentScoreboard.isObjectiveValid();
	}
	
	@Override
	public void tick(Container container)
	{
		if(!this.selectedObjective.equals("undisplay"))
		{
			this.objectField.tick();
		}
	}
	
	@Override
	public void drawScreen(Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		if(!this.selectedObjective.equals("undisplay"))
		{
			this.objectField.renderButton(mouseX, mouseY, partialTicks);
		}
	}
	
	@Override
	public String getTabTitle()
	{
		return I18n.format("gui.worldhandler.tab.scoreboard.objectives");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.SCOREBOARD_OBJECTIVES;
	}
}