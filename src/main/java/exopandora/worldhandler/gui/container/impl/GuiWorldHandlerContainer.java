package exopandora.worldhandler.gui.container.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.function.BiConsumer;

import javax.annotation.Nullable;

import com.google.common.base.Predicates;
import com.mojang.realmsclient.gui.ChatFormatting;

import exopandora.worldhandler.builder.impl.BuilderDifficulty;
import exopandora.worldhandler.builder.impl.BuilderDifficulty.EnumDifficulty;
import exopandora.worldhandler.builder.impl.BuilderGamemode;
import exopandora.worldhandler.builder.impl.BuilderGamemode.EnumGamemode;
import exopandora.worldhandler.builder.impl.BuilderTime;
import exopandora.worldhandler.builder.impl.BuilderTime.EnumMode;
import exopandora.worldhandler.builder.impl.BuilderWeather;
import exopandora.worldhandler.builder.impl.BuilderWeather.EnumWeather;
import exopandora.worldhandler.builder.impl.BuilderWorldHandler;
import exopandora.worldhandler.config.ConfigSettings;
import exopandora.worldhandler.config.ConfigSkin;
import exopandora.worldhandler.format.TextFormatting;
import exopandora.worldhandler.gui.button.EnumIcon;
import exopandora.worldhandler.gui.button.EnumTooltip;
import exopandora.worldhandler.gui.button.GuiButtonTab;
import exopandora.worldhandler.gui.button.GuiButtonWorldHandler;
import exopandora.worldhandler.gui.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.IContent;
import exopandora.worldhandler.gui.content.element.IElement;
import exopandora.worldhandler.helper.ResourceHelper;
import exopandora.worldhandler.main.Main;
import exopandora.worldhandler.main.WorldHandler;
import exopandora.worldhandler.util.UtilRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiWorldHandlerContainer extends Container
{
	private final Content content;
	private final int tabSize;
	private final int bgTextureWidth = 248;
	private final int bgTextureHeight = 166;
	private final int tabDistance = 2;
	private final int tabDistanceTotal;
	private final double tabWidth;
	private final double tabHalf;
	private final double tabEpsilon;
	private final String splash = this.getSplash();
	private final List<GuiButton> finalButtons = new ArrayList<GuiButton>();
	
	private GuiTextFieldTooltip syntaxField;
	private GuiTextFieldTooltip nameField;
	
	private static final BuilderWorldHandler BUILDER_WORLD_HANDLER = new BuilderWorldHandler();
	
	public GuiWorldHandlerContainer(Content content)
	{
		this.content = content;
		this.tabSize = this.content.getCategory().getSize();
		this.tabDistanceTotal = Math.max(this.tabSize - 1, 1) * this.tabDistance;
		this.tabWidth = (this.bgTextureWidth - this.tabDistanceTotal) / Math.max(this.tabSize, 2);
		this.tabHalf = this.tabWidth / 2D;
		this.tabEpsilon = this.bgTextureWidth - (this.tabDistanceTotal + this.tabHalf * Math.max(this.tabSize, 2) * 2D);
	}
	
	@Override
	public void initGui()
	{
		this.finalButtons.clear();
		this.elements.clear();
		
		//INIT
		this.content.onPlayerNameChanged(this.getPlayer());
		this.content.initGui(this, this.getContentX(), this.getContentY());
		
		//ELEMENTS
		
		for(IElement element : this.elements)
		{
			element.initGui(this);
		}
		
		//SHORTCUTS
		
		final int x = this.width / 2 - 10;
		final int delta = 21;
		
		if(ConfigSettings.areShortcutsEnabled())
		{
			this.finalButtons.add(new GuiButtonWorldHandler(-1, x - delta * 7, 0, 20, 20, null, I18n.format("gui.worldhandler.shortcuts.tooltip.time", I18n.format("gui.worldhandler.shortcuts.tooltip.time.dawn", ConfigSettings.getDawn())), EnumTooltip.RIGHT, EnumIcon.TIME_DAWN));
			this.finalButtons.add(new GuiButtonWorldHandler(-2, x - delta * 6, 0, 20, 20, null, I18n.format("gui.worldhandler.shortcuts.tooltip.time", I18n.format("gui.worldhandler.shortcuts.tooltip.time.noon", ConfigSettings.getNoon())), EnumTooltip.RIGHT, EnumIcon.TIME_NOON));
			this.finalButtons.add(new GuiButtonWorldHandler(-3, x - delta * 5, 0, 20, 20, null, I18n.format("gui.worldhandler.shortcuts.tooltip.time", I18n.format("gui.worldhandler.shortcuts.tooltip.time.sunset", ConfigSettings.getSunset())), EnumTooltip.RIGHT, EnumIcon.TIME_SUNSET));
			this.finalButtons.add(new GuiButtonWorldHandler(-4, x - delta * 4, 0, 20, 20, null, I18n.format("gui.worldhandler.shortcuts.tooltip.time", I18n.format("gui.worldhandler.shortcuts.tooltip.time.midnight", ConfigSettings.getMidnight())), EnumTooltip.RIGHT, EnumIcon.TIME_MIDNIGHT));
			this.finalButtons.add(new GuiButtonWorldHandler(-5, x - delta * 3, 0, 20, 20, null, I18n.format("gui.worldhandler.shortcuts.tooltip.weather", I18n.format("gui.worldhandler.shortcuts.tooltip.weather.clear")), EnumTooltip.RIGHT, EnumIcon.WEATHER_SUN));
			this.finalButtons.add(new GuiButtonWorldHandler(-6, x - delta * 2, 0, 20, 20, null, I18n.format("gui.worldhandler.shortcuts.tooltip.weather", I18n.format("gui.worldhandler.shortcuts.tooltip.weather.rainy")), EnumTooltip.RIGHT, EnumIcon.WEATHER_RAIN));
			this.finalButtons.add(new GuiButtonWorldHandler(-7, x - delta * 1, 0, 20, 20, null, I18n.format("gui.worldhandler.shortcuts.tooltip.weather", I18n.format("gui.worldhandler.shortcuts.tooltip.weather.thunder")), EnumTooltip.RIGHT, EnumIcon.WEATHER_STORM));
			this.finalButtons.add(new GuiButtonWorldHandler(-8, x - delta * 0, 0, 20, 20, null, I18n.format("gui.worldhandler.shortcuts.tooltip.difficulty", I18n.format("gui.worldhandler.shortcuts.tooltip.difficulty.peaceful")), EnumTooltip.RIGHT, EnumIcon.DIFFICULTY_PEACEFUL));
			this.finalButtons.add(new GuiButtonWorldHandler(-9, x + delta * 1, 0, 20, 20, null, I18n.format("gui.worldhandler.shortcuts.tooltip.difficulty", I18n.format("gui.worldhandler.shortcuts.tooltip.difficulty.easy")), EnumTooltip.RIGHT, EnumIcon.DIFFICULTY_EASY));
			this.finalButtons.add(new GuiButtonWorldHandler(-10, x + delta * 2, 0, 20, 20, null, I18n.format("gui.worldhandler.shortcuts.tooltip.difficulty", I18n.format("gui.worldhandler.shortcuts.tooltip.difficulty.normal")), EnumTooltip.RIGHT, EnumIcon.DIFFICULTY_NORMAL));
			this.finalButtons.add(new GuiButtonWorldHandler(-11, x + delta * 3, 0, 20, 20, null, I18n.format("gui.worldhandler.shortcuts.tooltip.difficulty", I18n.format("gui.worldhandler.shortcuts.tooltip.difficulty.hard")), EnumTooltip.RIGHT, EnumIcon.DIFFICULTY_HARD));
			this.finalButtons.add(new GuiButtonWorldHandler(-12, x + delta * 4, 0, 20, 20, null, I18n.format("gui.worldhandler.shortcuts.tooltip.gamemode", I18n.format("gui.worldhandler.shortcuts.tooltip.gamemode.survival")), EnumTooltip.RIGHT, EnumIcon.GAMEMODE_SURVIVAL));
			this.finalButtons.add(new GuiButtonWorldHandler(-13, x + delta * 5, 0, 20, 20, null, I18n.format("gui.worldhandler.shortcuts.tooltip.gamemode", I18n.format("gui.worldhandler.shortcuts.tooltip.gamemode.creative")), EnumTooltip.RIGHT, EnumIcon.GAMEMODE_CREATIVE));
			this.finalButtons.add(new GuiButtonWorldHandler(-14, x + delta * 6, 0, 20, 20, null, I18n.format("gui.worldhandler.shortcuts.tooltip.gamemode", I18n.format("gui.worldhandler.shortcuts.tooltip.gamemode.adventure")), EnumTooltip.RIGHT, EnumIcon.GAMEMODE_ADVENTURE));
			this.finalButtons.add(new GuiButtonWorldHandler(-15, x + delta * 7, 0, 20, 20, null, I18n.format("gui.worldhandler.shortcuts.tooltip.gamemode", I18n.format("gui.worldhandler.shortcuts.tooltip.gamemode.spectator")), EnumTooltip.RIGHT, EnumIcon.GAMEMODE_SPECTATOR));
		}
		
		//SYNTAX
		
		if(ConfigSettings.isCommandSyntaxEnabled())
		{
			this.syntaxField = new GuiTextFieldTooltip(x - delta * 7 + 1, this.height - 22, delta * 15 - 3, 20);
			this.updateSyntax();
		}
		
		//NAME
		
		this.nameField = new GuiTextFieldTooltip(0, 0, 0, 11);
		this.nameField.setMaxStringLength(16);
		this.nameField.setText(this.getPlayer());
		this.updateNameField();
		
		final int backgroundX = this.getBackgroundX();
		final int backgroundY = this.getBackgroundY();
		
		this.forEachTab((index, xOffset) ->
		{
			IContent tab = this.content.getCategory().getContent(index);
			
			if(!this.content.getActiveContent().equals(tab))
			{
				this.finalButtons.add(new GuiButtonTab(-16, (int)(backgroundX + xOffset), backgroundY - 20, (int)this.tabWidth + (int)Math.ceil(this.tabEpsilon / this.tabSize), 21, index));
			}
		});
		
		//BUTTONS
		
		this.initButtons();
	}
	
	public void initButtons()
	{
		this.buttonList.clear();
		
		this.content.initButtons(this, this.getContentX(), this.getContentY());
		
		if(this.finalButtons != null && !this.finalButtons.isEmpty())
		{
			this.buttonList.addAll(this.finalButtons);
		}
		
		for(IElement element : this.elements)
		{
			element.initButtons(this);
		}
	}
	
	private int getContentX()
	{
		return (this.width - this.bgTextureWidth) / 2 + 8 + this.getXOffset();
	}
	
	private int getContentY()
	{
		return this.height / 2 - 50 + this.getYOffset();
	}
	
	private int getXOffset()
	{
		return 0;
	}
	
	private int getYOffset()
	{
		return ConfigSettings.areShortcutsEnabled() ? 11 : 8;
	}
	
	@Override
	public void updateScreen()
	{
		this.content.updateScreen(this);
		this.updateSyntax();
	}
	
	private int getBackgroundX()
	{
		return (this.width - this.bgTextureWidth) / 2 + this.getXOffset();
	}
	
	private int getBackgroundY()
	{
		return (this.height - this.bgTextureHeight) / 2 + this.getYOffset();
	}
	
	private int getWatchOffset()
	{
		return ConfigSettings.isWatchEnabled() ? 9 : 0;
	}
	
	private void forEachTab(BiConsumer<Integer, Double> consumer)
	{
		double xOffset = 0D;
		
		for(int index = 0; index < this.tabSize; index++)
		{
			consumer.accept(index, xOffset);
			xOffset += this.tabWidth + this.tabDistance + this.tabEpsilon / this.tabSize;
		}
	}
	
	private void updateSyntax()
	{
		if(ConfigSettings.isCommandSyntaxEnabled() && this.syntaxField != null)
		{
			if(!this.syntaxField.isFocused())
			{
				this.syntaxField.setValidator(Predicates.alwaysTrue());
				
				if(this.content.getCommandBuilder() != null)
				{
					this.syntaxField.setText(this.content.getCommandBuilder().toCommand());
				}
				else
				{
					this.syntaxField.setText(BUILDER_WORLD_HANDLER.toCommand());
				}
				
				this.syntaxField.setValidator(string -> string.equals(this.syntaxField.getText()));
				this.syntaxField.setCursorPositionZero();
			}
		}
	}
	
	private void updateNameField()
	{
		final int backgroundX = this.getBackgroundX();
		final int backgroundY = this.getBackgroundY();
		
		if(WorldHandler.USERNAME.isEmpty())
		{
			int width = this.fontRenderer.getStringWidth(I18n.format("gui.worldhandler.generic.edit_username")) + 2;
			
			this.nameField.setWidth(width);
			this.nameField.setPosition(backgroundX  + this.bgTextureWidth - this.getWatchOffset() - 7 - (this.fontRenderer.getStringWidth(this.content.getTitle()) + 2), backgroundY + 6);
		}
		else
		{
			int width = this.fontRenderer.getStringWidth(WorldHandler.USERNAME) + 2;
			
			this.nameField.setWidth(width);
			this.nameField.setPosition(backgroundX + this.bgTextureWidth - this.getWatchOffset() - 7 - width, backgroundY + 6);
			this.content.onPlayerNameChanged(WorldHandler.USERNAME);
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		switch(button.id)
		{
			case 1:
				Minecraft.getMinecraft().displayGuiScreen((GuiScreen) null);
				Minecraft.getMinecraft().setIngameFocus();
				break;
			case 0:
				if(this.content.getBackContent() != null)
				{
					Minecraft.getMinecraft().displayGuiScreen(new GuiWorldHandlerContainer(this.content.getBackContent()));
				}
				break;
			case -1:
				WorldHandler.sendCommand(new BuilderTime(EnumMode.SET, ConfigSettings.getDawn()));
				break;
			case -2:
				WorldHandler.sendCommand(new BuilderTime(EnumMode.SET, ConfigSettings.getNoon()));
				break;
			case -3:
				WorldHandler.sendCommand(new BuilderTime(EnumMode.SET, ConfigSettings.getSunset()));
				break;
			case -4:
				WorldHandler.sendCommand(new BuilderTime(EnumMode.SET, ConfigSettings.getMidnight()));
				break;
			case -5:
				WorldHandler.sendCommand(new BuilderWeather(EnumWeather.CLEAR));
				break;
			case -6:
				WorldHandler.sendCommand(new BuilderWeather(EnumWeather.RAIN));
				break;
			case -7:
				WorldHandler.sendCommand(new BuilderWeather(EnumWeather.THUNDER));
				break;
			case -8:
				WorldHandler.sendCommand(new BuilderDifficulty(EnumDifficulty.PEACEFUL));
				break;
			case -9:
				WorldHandler.sendCommand(new BuilderDifficulty(EnumDifficulty.EASY));
				break;
			case -10:
				WorldHandler.sendCommand(new BuilderDifficulty(EnumDifficulty.NORMAL));
				break;
			case -11:
				WorldHandler.sendCommand(new BuilderDifficulty(EnumDifficulty.HARD));
				break;
			case -12:
				WorldHandler.sendCommand(new BuilderGamemode(EnumGamemode.SURVIVAL));
				break;
			case -13:
				WorldHandler.sendCommand(new BuilderGamemode(EnumGamemode.CREATIVE));
				break;
			case -14:
				WorldHandler.sendCommand(new BuilderGamemode(EnumGamemode.ADVENTURE));
				break;
			case -15:
				WorldHandler.sendCommand(new BuilderGamemode(EnumGamemode.SPECTATOR));
				break;
			case -16:
				if(button instanceof GuiButtonTab)
				{
					Minecraft.getMinecraft().displayGuiScreen(new GuiWorldHandlerContainer(this.content.getCategory().getContent(((GuiButtonTab)button).getIndex())));
				}
				break;
			default:
				elements:
					for(IElement element : this.elements)
					{
						if(element.actionPerformed(this, button))
						{
							break elements;
						}
					}
				
				this.content.actionPerformed(this, button);
				break;
		}
	}
	
	private void defaultColor()
	{
		this.defaultColor(1);
	}
	
	private void defaultColor(float alpha)
	{
		GlStateManager.enableBlend();
		GlStateManager.color((float) ConfigSkin.getBackgroundRed() / 255, (float) ConfigSkin.getBackgroundGreen() / 255, (float) ConfigSkin.getBackgroundBlue() / 255, alpha * (float) ConfigSkin.getBackgroundAlpha() / 255);
	}
	
	private void darkColor()
	{
		GlStateManager.enableBlend();
		GlStateManager.color((float) ConfigSkin.getBackgroundRed() / 255 - 0.3F, (float) ConfigSkin.getBackgroundGreen() / 255 - 0.3F, (float) ConfigSkin.getBackgroundBlue() / 255 - 0.3F, (float) ConfigSkin.getBackgroundAlpha() / 255);
	}
	
	private void bindBackground()
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceHelper.getBackgroundTexture());
	}
	
	@Nullable
	protected String getSplash()
	{
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH) + 1;
		
		if(day == 12 && month == 24)
		{
			return "Merry X-mas!";
		}
		else if(day == 1 && month == 1)
		{
			return "Happy new year!";
		}
		else if(day == 10 && month == 31)
		{
			return "OOoooOOOoooo! Spooky!";
		}
		else if(day == 3 && month == 28)
		{
			return (calendar.get(Calendar.YEAR) - 2013) + " Years of World Handler!";
		}
		
		return null;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		final int backgroundX = this.getBackgroundX();
		final int backgroundY = this.getBackgroundY();
		
		//DEFAULT BACKGROUND
		
		if(ConfigSkin.isBackgroundDrawingEnabled())
		{
			super.drawDefaultBackground();
		}
		
		//COLOR
		
		this.defaultColor();
		
		//BACKGROUND
		
		this.bindBackground();
		this.drawTexturedModalRect(backgroundX, backgroundY, 0, 0, this.bgTextureWidth, this.bgTextureHeight);
		
		//TABS
		
		this.forEachTab((index, xOffset) ->
		{
			IContent tab = this.content.getCategory().getContent(index);
			int yOffset;
			int fHeight;
			int color;
			
			if(this.content.getActiveContent().equals(tab))
			{
				yOffset = -22;
				fHeight = 25;
				color = 0xFFFFFF;
				this.defaultColor();
			}
			else
			{
				yOffset = -20;
				fHeight = 20;
				color = 0xE0E0E0;
				this.darkColor();
			}
			
			this.bindBackground();
			this.drawTexturedModalRect((int)(backgroundX + xOffset), (int)(backgroundY + yOffset), 0, 0, (int) Math.ceil(this.tabHalf), fHeight);
			this.drawTexturedModalRect((int)(backgroundX + this.tabHalf + xOffset), (int)(backgroundY + yOffset), this.bgTextureWidth - (int) Math.ceil(this.tabHalf), 0, (int) Math.ceil(this.tabHalf), fHeight);
			
			if(!ConfigSkin.areSharpEdgesEnabled())
			{
				if(this.content.getActiveContent().equals(tab))
				{
					//RIGHT TAB CURVATURE
					
					if(index < this.tabSize - 1 || this.tabSize == 1)
					{
						int factor = 2;
						
						for(int x = 0; x < factor; x++)
						{
							this.drawTexturedModalRect((int)(backgroundX + this.tabWidth + xOffset - x - 1), (int)(backgroundY + x + 1), (int)(this.tabWidth - x - 1), x + 1, x + 1, 1);
						}
					}
					
					//LEFT TAB CURVATURE
					
					if(index > 0)
					{
						int factor = 2;
						
						for(int x = 0; x < factor; x++)
						{
							this.drawTexturedModalRect((int)(backgroundX + xOffset), (int)(backgroundY + x + 1), xOffset.intValue(), x + 1, x + 1, 1);
						}
					}
					
					int width = (int)(this.tabWidth - 3);
					int interval = 5;
					
					//LEFT GRADIENT
					
					if(index == 0)
					{
						for(int x = 0; x < width; x += interval)
						{
							this.defaultColor(1.0F - (x / (width + 5.0F * interval)));
							this.drawTexturedModalRect((int)(backgroundX + xOffset), (int)(backgroundY + yOffset + fHeight + x / interval), 0, fHeight, width - x, 1);
						}
					}
					
					//RIGHT GRADIENT
					
					if(index == this.tabSize - 1 && this.tabSize > 1)
					{
						int offset = 3;
						
						for(int x = 0; x < width; x += interval)
						{
							this.defaultColor(1.0F - (x / (width + 5.0F * interval)));
							this.drawTexturedModalRect((int)(backgroundX + Math.ceil(xOffset) + x + offset), (int)(backgroundY + yOffset + fHeight + x / interval), this.bgTextureWidth - width + x, fHeight, width - x, 1);
						}
					}
				}
				else
				{
					//LEFT CORNER FILLER
					
					if(index == 0)
					{
						int factor = 2;
						
						for(int x = 0; x < factor; x++)
						{
							this.drawTexturedModalRect(backgroundX, backgroundY + x, 0, fHeight, factor - x, 1);
						}
					}
					
					//RIGHT CORNER FILLER
					
					if(index == this.tabSize - 1)
					{
						int factor = 3;
						
						for(int x = 0; x < factor + 1; x++)
						{
							this.drawTexturedModalRect(backgroundX + this.bgTextureWidth - x, backgroundY + factor - x, this.bgTextureWidth - x, fHeight, x, 1);
						}
					}
				}
			}
			
			this.drawCenteredString(this.fontRenderer, ChatFormatting.UNDERLINE + tab.getTabTitle(), (int)(backgroundX + this.tabHalf + xOffset), (int)(backgroundY - 13), color);
		});
		
		this.defaultColor();
		
		//VERSION LABEL
		
		final int hexAlpha = (int) (0xFF * 0.2) << 24;
		final int color = ConfigSkin.getLabelColor() + hexAlpha;
		
		this.fontRenderer.drawString(Main.MC_VERSION + "-" + Main.VERSION, this.width - this.fontRenderer.getStringWidth(Main.MC_VERSION + "-" + Main.VERSION) - 2, this.height - 10, color);
		
		//TITLE
		
		final int maxWidth = this.bgTextureWidth - 7 - 2 - this.fontRenderer.getStringWidth(WorldHandler.USERNAME) - 2 - this.getWatchOffset() - 7;
		this.fontRenderer.drawString(TextFormatting.shortenString(this.content.getTitle(), maxWidth, this.fontRenderer), backgroundX + 7, backgroundY + 7, ConfigSkin.getLabelColor());
		
		//HEADLINE
		
		if(this.content.getHeadline() != null)
		{
			if(this.content.getHeadline().length > 0)
			{
				this.fontRenderer.drawString(this.content.getHeadline()[0], backgroundX + 8, backgroundY + 22, ConfigSkin.getHeadlineColor());
			}
			
			if(this.content.getHeadline().length > 1)
			{
				this.fontRenderer.drawString(this.content.getHeadline()[1], backgroundX + 126, backgroundY + 22, ConfigSkin.getHeadlineColor());
			}
		}
		
		//NAME FIELD
		
		final String username = WorldHandler.USERNAME.isEmpty() && !this.nameField.isFocused() ? I18n.format("gui.worldhandler.generic.edit_username") : WorldHandler.USERNAME;
		this.fontRenderer.drawString(username, backgroundX + 232 - this.fontRenderer.getStringWidth(username), backgroundY + 7, ConfigSkin.getLabelColor());
		
		//WATCH
		
		if(ConfigSettings.isWatchEnabled())
		{
			final int watchX = backgroundX + 233;
			final int watchY = backgroundY + 5;
			
			UtilRender.drawWatchIntoGui(this, watchX, watchY, Minecraft.getMinecraft().world.getWorldInfo().getWorldTime(), ConfigSettings.isSmoothWatchEnabled());
			
			if(ConfigSettings.areTooltipsEnabled())
			{
				if(mouseX >= watchX && mouseX <= watchX + 9 && mouseY >= watchY && mouseY <= watchY + 9)
				{
					GuiUtils.drawHoveringText(Arrays.asList(TextFormatting.getWorldTime(Minecraft.getMinecraft().world.getWorldTime())), mouseX, mouseY + 9, this.width, this.height, this.width, this.fontRenderer);
					GlStateManager.disableLighting();
				}
			}
		}
		
		//BUTTONS
		
		for(int x = 0; x < this.buttonList.size(); x++)
		{
			this.buttonList.get(x).drawButton(this.mc, mouseX, mouseY, partialTicks);
		}
		
		for(int x = 0; x < this.labelList.size(); x++)
		{
			this.labelList.get(x).drawLabel(this.mc, mouseX, mouseY);
		}
		
		//CONTAINER
		
		this.content.drawScreen(this, this.getContentX(), this.getContentY(), mouseX, mouseY, partialTicks);
		
		//CONTAINER ELEMENTS
		
		for(IElement element : this.elements)
		{
			element.draw();
		}
		
		//SYNTAX
		
		if(ConfigSettings.isCommandSyntaxEnabled() && this.syntaxField != null)
		{
			this.syntaxField.drawTextBox();
		}
		
		//SPLASHTEXT
		
		if(this.splash != null)
		{
			GlStateManager.pushMatrix();
			RenderHelper.enableGUIStandardItemLighting();
			GlStateManager.disableLighting();
			GlStateManager.translate((float) (backgroundX + 212), backgroundY + 15, 0.0F);
			GlStateManager.rotate(17.0F, 0.0F, 0.0F, 1.0F);
			
			float scale = 1.1F - MathHelper.abs(MathHelper.sin((float) (Minecraft.getSystemTime() % 1000L) / 1000.0F * (float) Math.PI * 2.0F) * 0.1F);
			scale = scale * 100.0F / this.fontRenderer.getStringWidth(this.splash);
			GlStateManager.scale(scale, scale, scale);
			
			this.drawCenteredString(this.fontRenderer, this.splash, 0, (int) scale, 0xFFFF00);
			
			GlStateManager.popMatrix();
		}
		
		//TOOLTIPS
		
		if(ConfigSettings.areTooltipsEnabled())
		{
			for(int x = 0; x < this.buttonList.size(); x++)
			{
				if(this.buttonList.get(x) instanceof GuiButtonWorldHandler)
				{
					((GuiButtonWorldHandler) this.buttonList.get(x)).drawTooltip(mouseX, mouseY, this.width, this.height);
				}
			}
		}
	}
	
	@Override
	protected void keyTyped(char charTyped, int keyCode) throws IOException
	{
		super.keyTyped(charTyped, keyCode);
		
		this.content.keyTyped(this, charTyped, keyCode);
		
		if(this.nameField.isFocused())
		{
			this.nameField.textboxKeyTyped(charTyped, keyCode);
			WorldHandler.USERNAME = this.nameField.getText();
			this.updateNameField();
		}
		
		if(ConfigSettings.isCommandSyntaxEnabled() && this.syntaxField != null)
		{
			this.syntaxField.textboxKeyTyped(charTyped, keyCode);
		}
		
		for(IElement element : this.elements)
		{
			element.keyTyped(this, charTyped, keyCode);
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
		this.content.mouseClicked(mouseX, mouseY, mouseButton);
		this.nameField.mouseClicked(mouseX, mouseY, mouseButton);
		
		if(this.nameField.isFocused())
		{
			this.nameField.setCursorPositionEnd();
		}
		
		if(ConfigSettings.isCommandSyntaxEnabled() && this.syntaxField != null)
		{
			this.syntaxField.mouseClicked(mouseX, mouseY, mouseButton);
		}
		
		for(IElement element : this.elements)
		{
			element.mouseClicked(mouseX, mouseY, mouseButton);
		}
	}
	
	@Override
	public void onGuiClosed()
	{
		this.content.onGuiClosed();
	}
	
	@Override
	public boolean doesGuiPauseGame()
	{
		return ConfigSettings.isPauseEnabled();
	}
	
	@Override
	public String getPlayer()
	{
		return WorldHandler.USERNAME;
	}
}