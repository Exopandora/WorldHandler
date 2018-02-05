package exopandora.worldhandler.gui.content.impl;

import java.util.Arrays;

import com.google.common.base.Predicates;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderScoreboardObjectives;
import exopandora.worldhandler.format.EnumColor;
import exopandora.worldhandler.gui.button.GuiButtonWorldHandler;
import exopandora.worldhandler.gui.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.content.element.impl.ElementClickList;
import exopandora.worldhandler.gui.content.element.logic.ILogicClickList;
import exopandora.worldhandler.gui.content.impl.abstr.ContentScoreboard;
import exopandora.worldhandler.helper.EntityHelper;
import exopandora.worldhandler.main.WorldHandler;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
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
		this.objectField.setText(this.objective);
		
		if(this.selectedObjective.equals("create"))
		{
			ElementClickList objectives = new ElementClickList(x + 118, y + 24, HELPER.getObjectives(), 7, 8, this, new ILogicClickList()
			{
				@Override
				public void consumeKey1(String key)
				{
					builderObjectives.setCriteria(key);
				}
				
				@Override
				public String translate1(String key)
				{
					String format = "gui.worldhandler.scoreboard.objectives.criteria." + key;
					String result = I18n.format(format);
					
					if(result.equals(format))
					{
						ResourceLocation location = new ResourceLocation(key);
						
						if(Item.REGISTRY.containsKey(location))
						{
							result = I18n.format(Item.REGISTRY.getObject(location).getUnlocalizedName() + ".name");
						}
						else if(Block.REGISTRY.containsKey(location))
						{
							result = Block.REGISTRY.getObject(location).getLocalizedName();
						}
						else if(EntityHelper.doesExist(key))
						{
							result = I18n.format("entity." + key + ".name");
						}
						else if(Arrays.stream(EnumColor.values()).map(EnumColor::getFormat).anyMatch(Predicates.equalTo(key)))
						{
							result = I18n.format("gui.worldhandler.color." + key);
						}
						else
						{
							result = I18n.format(key);
						}
					}
					
					return result;
				}
				
				@Override
				public String getId()
				{
					return "objectives";
				}
			});
			
			container.add(objectives);
		}
		else if(this.selectedObjective.equals("display") || this.selectedObjective.equals("undisplay"))
		{
			ElementClickList slots = new ElementClickList(x + 118, y + 24 + (this.selectedObjective.equals("undisplay") ? -12 : 0), HELPER.getSlots(), 9, 10, this, new ILogicClickList()
			{
				@Override
				public String translate1(String key)
				{
					return I18n.format("gui.worldhandler.scoreboard.slot." + key);
				}
				
				@Override
				public String translate2(String key1, String key2)
				{
					return I18n.format("gui.worldhandler.color." + key2);
				}
				
				@Override
				public void consumeKey1(String key)
				{
					builderObjectives.setSlot(key);
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
		GuiButtonWorldHandler button3;
		GuiButtonWorldHandler button4;
		GuiButtonWorldHandler button5;
		GuiButtonWorldHandler button6;
		
		container.add(new GuiButtonWorldHandler(0, x, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.back")));
		container.add(new GuiButtonWorldHandler(1, x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame")));
		
		container.add(button3 = new GuiButtonWorldHandler(2, x, y, 114, 20, I18n.format("gui.worldhandler.scoreboard.objectives.create")));
		container.add(button4 = new GuiButtonWorldHandler(3, x, y + 24, 114, 20, I18n.format("gui.worldhandler.scoreboard.objectives.display")));
		container.add(button5 = new GuiButtonWorldHandler(4, x, y + 48, 114, 20, I18n.format("gui.worldhandler.scoreboard.objectives.undisplay")));
		container.add(button6 = new GuiButtonWorldHandler(5, x, y + 72, 114, 20, I18n.format("gui.worldhandler.scoreboard.objectives.remove")));
		
		button3.enabled = !this.selectedObjective.equals("create");
		button4.enabled = !this.selectedObjective.equals("display");
		button5.enabled = !this.selectedObjective.equals("undisplay");
		button6.enabled = !this.selectedObjective.equals("remove");
		
		boolean enabled = this.builderObjectives.getObjective() != null && this.builderObjectives.getObjective().length() > 0;
		int yOffset = this.selectedObjective.equals("undisplay") ? -12 : (this.selectedObjective.equals("remove") ? -24 : 0);
		
		if(this.selectedObjective.equals("undisplay"))
		{
			this.builderObjectives.setObjective(null);
			enabled = true;
		}
		else if(this.selectedObjective.equals("remove"))
		{
			this.builderObjectives.setMode("remove");
		}
		
		if(!this.selectedObjective.equals("undisplay"))
		{
			this.builderObjectives.setObjective(this.objective);
		}
		
		container.add(button3 = new GuiButtonWorldHandler(6, x + 118, y + 72 + yOffset, 114, 20, I18n.format("gui.worldhandler.actions.perform")));
		button3.enabled = enabled;
	}
	
	@Override
	public void actionPerformed(Container container, GuiButton button)
	{
		switch(button.id)
		{
			case 2:
				this.selectedObjective = "create";
				container.initGui();
				break;
			case 3:
				this.selectedObjective = "display";
				container.initGui();
				break;
			case 4:
				this.selectedObjective = "undisplay";
				container.initGui();
				break;
			case 5:
				this.selectedObjective = "remove";
				container.initGui();
				break;
			case 6:
				WorldHandler.sendCommand(this.builderObjectives);
				container.initGui();
				break;
			default:
				break;
		}
	}
	
	@Override
	public void drawScreen(Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		if(!this.selectedObjective.equals("undisplay"))
		{
			this.objectField.drawTextBox();
		}
	}
	
	@Override
	public void keyTyped(Container container, char typedChar, int keyCode)
	{
		if(this.objectField.textboxKeyTyped(typedChar, keyCode))
		{
			this.objective = this.objectField.getText();
			this.builderObjectives.setObjective(this.objective);
			container.initButtons();
		}
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton)
	{
		if(!this.selectedObjective.equals("undisplay"))
		{
			this.objectField.mouseClicked(mouseX, mouseY, mouseButton);
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