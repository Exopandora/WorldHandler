package exopandora.worldhandler.gui.content.impl;

import java.util.Arrays;

import com.google.common.base.Predicates;

import exopandora.worldhandler.WorldHandler;
import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderGamerule;
import exopandora.worldhandler.gui.button.EnumTooltip;
import exopandora.worldhandler.gui.button.GuiButtonWorldHandler;
import exopandora.worldhandler.gui.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.content.element.impl.ElementPageList;
import exopandora.worldhandler.gui.content.element.logic.ILogicPageList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.GameRules.ValueType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ContentGamerules extends Content
{
	private GuiTextFieldTooltip valueField;
	
	private boolean booleanValue;
	private String value;
	
	private final BuilderGamerule builderGamerule = new BuilderGamerule();
	
	@Override
	public ICommandBuilder getCommandBuilder()
	{
		return this.builderGamerule;
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.valueField = new GuiTextFieldTooltip(x + 118, y + 24, 114, 20, I18n.format("gui.worldhandler.generic.value"));
		this.valueField.setValidator(Predicates.notNull());
		this.valueField.setText(this.value);
		this.valueField.setCursorPositionEnd();
		
		ElementPageList<String, String> rules = new ElementPageList<String, String>(x, y, Arrays.asList(Minecraft.getMinecraft().world.getGameRules().getRules()), null, 114, 20, 3, this, new int[] {5, 6, 7}, new ILogicPageList<String, String>()
		{
			@Override
			public String translate(String key)
			{
				String translated = I18n.format(key);
				
				if(!translated.equals(key))
				{
					return translated;
				}
				
				return I18n.format("gui.worldhandler.gamerules.rule." + key);
			}
			
			@Override
			public void onClick(String clicked)
			{
				builderGamerule.setRule(clicked);
				booleanValue = Minecraft.getMinecraft().world.getGameRules().areSameType(clicked, ValueType.BOOLEAN_VALUE);
				
				if(booleanValue)
				{
					builderGamerule.setValue(null);
				}
				else
				{
					builderGamerule.setValue(value);
				}
			}
			
			@Override
			public String getRegistryName(String key)
			{
				return key;
			}
			
			@Override
			public void onRegister(int id, int x, int y, int width, int height, String display, String registryKey, boolean enabled, String value, Container container)
			{
				GuiButtonWorldHandler button = new GuiButtonWorldHandler(id, x, y, width, height, display, registryKey, EnumTooltip.TOP_RIGHT);
				button.enabled = enabled;
				container.add(button);
			}
			
			@Override
			public String convert(String object)
			{
				return object;
			}
			
			@Override
			public String getId()
			{
				return "gamerules";
			}
		});
		
		container.add(rules);
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.add(new GuiButtonWorldHandler(0, x, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.back")));
		container.add(new GuiButtonWorldHandler(1, x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame")));
		
		if(this.booleanValue)
		{
			container.add(new GuiButtonWorldHandler(2, x + 118, y + 24, 114, 20, I18n.format("gui.worldhandler.generic.enable")));
			container.add(new GuiButtonWorldHandler(3, x + 118, y + 48, 114, 20, I18n.format("gui.worldhandler.generic.disable")));
		}
		else
		{
			container.add(new GuiButtonWorldHandler(4, x + 118, y + 48, 114, 20, I18n.format("gui.worldhandler.actions.perform")));
		}
	}
	
	@Override
	public void actionPerformed(Container container, GuiButton button)
	{
		switch(button.id)
		{
			case 2:
				WorldHandler.sendCommand(this.builderGamerule.getBuilderForValue(String.valueOf(true)));
				break;
			case 3:
				WorldHandler.sendCommand(this.builderGamerule.getBuilderForValue(String.valueOf(false)));
				break;
			case 4:
				WorldHandler.sendCommand(this.builderGamerule);
				break;
			default:
				break;
		}
	}
	
	@Override
	public void drawScreen(Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		if(!this.booleanValue)
		{
			this.valueField.drawTextBox();
		}
	}
	
	@Override
	public void keyTyped(Container container, char typedChar, int keyCode)
	{
		if(this.valueField.textboxKeyTyped(typedChar, keyCode))
		{
			this.value = this.valueField.getText();
			this.builderGamerule.setValue(this.value);
		}
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton)
	{
		if(!this.booleanValue)
		{
			this.valueField.mouseClicked(mouseX, mouseY, mouseButton);
		}
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.WORLD;
	}
	
	@Override
	public String getTitle()
	{
		return I18n.format("gui.worldhandler.title.world.gamerules");
	}
	
	@Override
	public String getTabTitle()
	{
		return I18n.format("gui.worldhandler.tab.world.gamerules");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.GAMERULES;
	}
}
