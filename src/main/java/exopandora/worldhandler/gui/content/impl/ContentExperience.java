package exopandora.worldhandler.gui.content.impl;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderExperience;
import exopandora.worldhandler.config.ConfigSliders;
import exopandora.worldhandler.gui.button.EnumTooltip;
import exopandora.worldhandler.gui.button.GuiButtonWorldHandler;
import exopandora.worldhandler.gui.button.GuiSlider;
import exopandora.worldhandler.gui.button.responder.SimpleResponder;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.main.WorldHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ContentExperience extends Content
{
	private final BuilderExperience builderExperience = new BuilderExperience();
	private GuiButtonWorldHandler addButton;
	private GuiButtonWorldHandler removeButton;
	
	@Override
	public ICommandBuilder getCommandBuilder()
	{
		return this.builderExperience;
	}
	
	@Override
	public void init(Container container)
	{
		if(this.builderExperience.getLevel() > ConfigSliders.getMaxExperience())
		{
			this.builderExperience.setLevel((int) ConfigSliders.getMaxExperience());
		}
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.add(new GuiButtonWorldHandler(0, x, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.back")));
		container.add(new GuiButtonWorldHandler(1, x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame")));
		
		container.add(new GuiSlider<String>(this, container, "experience", x + 116 / 2, y, 114, 20, I18n.format("gui.worldhandler.title.player.experience"), 0, ConfigSliders.getMaxExperience(), 0, new SimpleResponder<String>(value -> 
		{
			this.builderExperience.setLevel(value);
		})));
		
		container.add(this.addButton = new GuiButtonWorldHandler(3, x + 116 / 2, y + 24, 114, 20, I18n.format("gui.worldhandler.actions.add")));
		container.add(this.removeButton = new GuiButtonWorldHandler(4, x + 116 / 2, y + 48, 114, 20, I18n.format("gui.worldhandler.actions.remove")));
		container.add(new GuiButtonWorldHandler(5, x + 116 / 2, y + 72, 114, 20, I18n.format("gui.worldhandler.actions.reset"), I18n.format("gui.worldhandler.actions.set_to_0"), EnumTooltip.TOP_RIGHT));
		
		boolean enabled = this.builderExperience.getLevel() > 0;
		
		this.addButton.enabled = enabled;
		this.removeButton.enabled = enabled;
	}
	
	@Override
	public void updateScreen(Container container)
	{
		boolean enabled = this.builderExperience.getLevel() > 0;
		
		this.addButton.enabled = enabled;
		this.removeButton.enabled = enabled;
	}
	
	@Override
	public void actionPerformed(Container container, GuiButton button)
	{
		switch(button.id)
		{
			case 3:
				WorldHandler.sendCommand(this.builderExperience);
				container.initGui();
				break;
			case 4:
				if(Minecraft.getMinecraft().player.experienceLevel >= this.builderExperience.getLevel())
				{
					WorldHandler.sendCommand(new BuilderExperience(-this.builderExperience.getLevel(), this.builderExperience.getPlayer()));
					break;
				}
			case 5:
				WorldHandler.sendCommand(new BuilderExperience(-Minecraft.getMinecraft().player.experienceLevel, this.builderExperience.getPlayer()));
				container.initGui();
				break;
			default:
				break;
		}
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.PLAYER;
	}
	
	@Override
	public String getTitle()
	{
		return I18n.format("gui.worldhandler.title.player.experience");
	}
	
	@Override
	public String getTabTitle()
	{
		return I18n.format("gui.worldhandler.tab.player.experience");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.EXPERIENCE;
	}
	
	@Override
	public void onPlayerNameChanged(String username)
	{
		this.builderExperience.setPlayer(username);
	}
}
