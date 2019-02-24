package exopandora.worldhandler.gui.content.impl;

import exopandora.worldhandler.WorldHandler;
import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderButcher;
import exopandora.worldhandler.config.ConfigButcher;
import exopandora.worldhandler.gui.button.GuiButtonWorldHandler;
import exopandora.worldhandler.gui.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.config.GuiConfigWorldHandler;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.impl.abstr.ContentChild;
import exopandora.worldhandler.helper.EntityHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ContentButcher extends ContentChild
{
	private GuiTextFieldTooltip radiusField;
	private String radius;
	private final BuilderButcher builderButcher = new BuilderButcher();
	
	@Override
	public ICommandBuilder getCommandBuilder()
	{
		return this.builderButcher;
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.radiusField = new GuiTextFieldTooltip(x + 116 / 2, y + 12, 116, 20, I18n.format("gui.worldhandler.butcher.radius"));
		this.radiusField.setValidator(string -> string != null && string.matches("[0-9]{0,8}"));
		this.radiusField.setText(this.radius);
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		GuiButtonWorldHandler slaughter;
		
		container.add(new GuiButtonWorldHandler(0, x, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.back")));
		container.add(new GuiButtonWorldHandler(1, x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame")));
		
		container.add(new GuiButtonWorldHandler(3, x + 116 / 2, y + 36, 232 / 2, 20, I18n.format("gui.worldhandler.butcher.configure")));
		container.add(slaughter = new GuiButtonWorldHandler(2, x + 116 / 2, y + 60, 232 / 2, 20, I18n.format("gui.worldhandler.butcher.slaughter")));
		
		slaughter.enabled = this.radius != null && !this.radius.isEmpty();
	}
	
	@Override
	public void actionPerformed(Container container, GuiButton button) throws Exception
	{
		switch(button.id)
		{
			case 2:
				for(ResourceLocation entity : EntityList.ENTITY_EGGS.keySet())
				{
					if(ConfigButcher.getEntitiyMap().get(EntityHelper.getEntityName(entity)))
					{
						WorldHandler.sendCommand(new BuilderButcher(entity, Integer.valueOf(this.radius)));
					}
				}
				break;
			case 3:
				Minecraft.getMinecraft().displayGuiScreen(new GuiConfigWorldHandler(container, ConfigButcher.CATEGORY));
				break;
			default:
				break;
		}
	}
	
	@Override
	public void drawScreen(Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		this.radiusField.drawTextBox();
	}
	
	@Override
	public String getTitle()
	{
		return I18n.format("gui.worldhandler.title.butcher");
	}
	
	@Override
	public void keyTyped(Container container, char typedChar, int keyCode)
	{
		if(this.radiusField.textboxKeyTyped(typedChar, keyCode))
		{
			this.radius = this.radiusField.getText();
			
			if(this.radius.length() > 0)
			{
				this.builderButcher.setRadius(Integer.valueOf(this.radius));
			}
			else
			{
				this.builderButcher.setRadius(0);
			}
			
			container.initButtons();
		}
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton)
	{
		this.radiusField.mouseClicked(mouseX, mouseY, mouseButton);
	}
}
