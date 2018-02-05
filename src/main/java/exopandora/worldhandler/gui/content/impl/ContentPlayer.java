package exopandora.worldhandler.gui.content.impl;

import com.mojang.realmsclient.gui.ChatFormatting;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderGeneric;
import exopandora.worldhandler.builder.impl.BuilderMultiCommand;
import exopandora.worldhandler.builder.impl.BuilderSpawnpoint;
import exopandora.worldhandler.gui.button.GuiButtonWorldHandler;
import exopandora.worldhandler.gui.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.container.impl.GuiWorldHandlerContainer;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.main.WorldHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ContentPlayer extends Content
{
	private String selectedMain = "start";
	
	private GuiTextFieldTooltip posXField;
	private GuiTextFieldTooltip posYField;
	private GuiTextFieldTooltip posZField;
	
	private GuiTextFieldTooltip scoreField;
	private GuiTextFieldTooltip coinsField;
	private GuiTextFieldTooltip xpField;
	
	private final BuilderGeneric builderSetworldspawn = new BuilderGeneric("setworldspawn");
	private final BuilderSpawnpoint builderSpawnpoint = new BuilderSpawnpoint(WorldHandler.USERNAME);
	private final BuilderGeneric builderKill = new BuilderGeneric("kill");
	private final BuilderGeneric builderClear = new BuilderGeneric("clear");
	
	private final BuilderMultiCommand builderMiscellaneous = new BuilderMultiCommand(this.builderSetworldspawn, this.builderSpawnpoint, this.builderKill, this.builderClear);
	
	@Override
	public ICommandBuilder getCommandBuilder()
	{
		if(this.selectedMain.equals("miscellaneous"))
		{
			return this.builderMiscellaneous;
		}
		
		return null;
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.posXField = new GuiTextFieldTooltip(x + 118, y, 114, 20);
		this.posYField = new GuiTextFieldTooltip(x + 118, y + 24, 114, 20);
		this.posZField = new GuiTextFieldTooltip(x + 118, y + 48, 114, 20);
		this.scoreField = new GuiTextFieldTooltip(x + 118, y + 12, 114, 20);
		this.coinsField = new GuiTextFieldTooltip(x + 118, y + 36, 114, 20);
		this.xpField = new GuiTextFieldTooltip(x + 118, y + 60, 114, 20);
		
		this.updateScreen(container);
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
				
		container.add(button3 = new GuiButtonWorldHandler(4, x, y, 114, 20, I18n.format("gui.worldhandler.entities.player.start")));
		container.add(button4 = new GuiButtonWorldHandler(5, x, y + 24, 114, 20, I18n.format("gui.worldhandler.entities.player.score")));
		container.add(button5 = new GuiButtonWorldHandler(6, x, y + 48, 114, 20, I18n.format("gui.worldhandler.entities.player.position")));
		container.add(button6 = new GuiButtonWorldHandler(7, x, y + 72, 114, 20, I18n.format("gui.worldhandler.entities.player.miscellaneous")));
		
		if(this.selectedMain.equals("start"))
		{
			button3.enabled = false;
		}
		else if(this.selectedMain.equals("score"))
		{
			button4.enabled = false;
		}
		else if(this.selectedMain.equals("position"))
		{
			button5.enabled = false;
			
			container.add(new GuiButtonWorldHandler(8, x + 118, y + 72, 114, 20, I18n.format("gui.worldhandler.entities.player.position.copy_position")));
		}
		else if(this.selectedMain.equals("miscellaneous"))
		{
			button6.enabled = false;
			
			container.add(new GuiButtonWorldHandler(9, x + 118, y, 114, 20, ChatFormatting.RED + I18n.format("gui.worldhandler.entities.player.miscellaneous.set_spawn")));
			container.add(new GuiButtonWorldHandler(10, x + 118, y + 24, 114, 20, ChatFormatting.RED + I18n.format("gui.worldhandler.entities.player.miscellaneous.set_global_spawn")));
			container.add(new GuiButtonWorldHandler(11, x + 118, y + 48, 114, 20, ChatFormatting.RED + I18n.format("gui.worldhandler.entities.player.miscellaneous.kill")));
			container.add(new GuiButtonWorldHandler(12, x + 118, y + 72, 114, 20, ChatFormatting.RED + I18n.format("gui.worldhandler.entities.player.miscellaneous.clear_inventory")));
		}
	}
	
	@Override
	public void updateScreen(Container container)
	{
		this.posXField.setText("X: " + MathHelper.floor(Minecraft.getMinecraft().player.posX));
		this.posYField.setText("Y: " + MathHelper.floor(Minecraft.getMinecraft().player.posY));
		this.posZField.setText("Z: " + MathHelper.floor(Minecraft.getMinecraft().player.posZ));
		this.scoreField.setText(I18n.format("gui.worldhandler.entities.player.score") + ": " + Minecraft.getMinecraft().player.getScore());
		this.coinsField.setText(I18n.format("gui.worldhandler.entities.player.score.experience") + ": " + Minecraft.getMinecraft().player.experienceLevel + "L");
		this.xpField.setText(I18n.format("gui.worldhandler.entities.player.score.experience_coins") + ": " + Minecraft.getMinecraft().player.experienceTotal);
	}
	
	@Override
	public void actionPerformed(Container container, GuiButton button)
	{
		switch(button.id)
		{
			case 4:
				this.selectedMain = "start";
				container.initGui();
				break;
			case 5:
				this.selectedMain = "score";
				container.initGui();
				break;
			case 6:
				this.selectedMain = "position";
				container.initGui();
				break;
			case 7:
				this.selectedMain = "miscellaneous";
				container.initGui();
				break;
			case 8:
				int posX = MathHelper.floor(Minecraft.getMinecraft().player.posX);
				int posY = MathHelper.floor(Minecraft.getMinecraft().player.posY);
				int posZ = MathHelper.floor(Minecraft.getMinecraft().player.posZ);
				
				container.setClipboardString(posX + " " + posY + " " + posZ);
				break;
			case 9:
				Minecraft.getMinecraft().displayGuiScreen(new GuiWorldHandlerContainer(Contents.CONTINUE.withBuilder(this.builderSpawnpoint).withParent(Contents.PLAYER)));
				break;
			case 10:
				Minecraft.getMinecraft().displayGuiScreen(new GuiWorldHandlerContainer(Contents.CONTINUE.withBuilder(this.builderSetworldspawn).withParent(Contents.PLAYER)));
				break;
			case 11:
				Minecraft.getMinecraft().displayGuiScreen(new GuiWorldHandlerContainer(Contents.CONTINUE.withBuilder(this.builderKill).withParent(Contents.PLAYER)));
				break;
			case 12:
				Minecraft.getMinecraft().displayGuiScreen(new GuiWorldHandlerContainer(Contents.CONTINUE.withBuilder(this.builderClear).withParent(Contents.PLAYER)));
				break;
			default:
				break;
		}
	}
	
	@Override
	public void drawScreen(Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		if(this.selectedMain.equals("start"))
		{
			int xPos = x + 175;
			int yPos = y + 82;
			int playerNameWidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(Minecraft.getMinecraft().player.getName()) / 2;
			
			container.drawRect(container.width / 2 - playerNameWidth - 1 + 59, yPos - 74, container.width / 2 + playerNameWidth + 1 + 59, yPos - 65, 0x3F000000);
			Minecraft.getMinecraft().fontRenderer.drawString(Minecraft.getMinecraft().player.getName(), container.width / 2 - playerNameWidth + 59, yPos - 73, 0xE0E0E0);
			
			GuiInventory.drawEntityOnScreen(xPos, yPos, 30, xPos - mouseX, yPos - mouseY - 44, Minecraft.getMinecraft().player);
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		}
		else if(this.selectedMain.equals("score"))
		{
			this.scoreField.drawTextBox();
			this.xpField.drawTextBox();
			this.coinsField.drawTextBox();
		}
		else if(this.selectedMain.equals("position"))
		{
			this.posXField.drawTextBox();
			this.posYField.drawTextBox();
			this.posZField.drawTextBox();
		}
	}
	
	@Override
	public void onPlayerNameChanged(String username)
	{
		this.builderSpawnpoint.setPlayer(username);
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.PLAYER;
	}
	
	@Override
	public String getTitle()
	{
		return I18n.format("gui.worldhandler.title.player.player");
	}
	
	@Override
	public String getTabTitle()
	{
		return I18n.format("gui.worldhandler.tab.player.player");
	}
	
	@Override
	public String[] getHeadline()
	{
		return new String[]{I18n.format("gui.worldhandler.generic.browse")};
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.PLAYER;
	}
}
