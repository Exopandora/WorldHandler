package exopandora.worldhandler.gui.container.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.function.BiConsumer;

import javax.annotation.Nullable;

import com.google.common.base.Predicates;
import com.mojang.blaze3d.platform.GlStateManager;

import exopandora.worldhandler.Main;
import exopandora.worldhandler.builder.impl.BuilderWorldHandler;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.event.KeyHandler;
import exopandora.worldhandler.gui.button.EnumIcon;
import exopandora.worldhandler.gui.button.GuiButtonIcon;
import exopandora.worldhandler.gui.button.GuiButtonTab;
import exopandora.worldhandler.gui.button.GuiButtonTooltip;
import exopandora.worldhandler.gui.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.IContent;
import exopandora.worldhandler.gui.menu.IMenu;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.ResourceHelper;
import exopandora.worldhandler.util.TextFormatting;
import exopandora.worldhandler.util.UtilRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.config.GuiUtils;

@OnlyIn(Dist.CLIENT)
public class GuiWorldHandler extends Container
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
	private final List<Widget> finalButtons = new ArrayList<Widget>();
	
	private GuiTextFieldTooltip syntaxField;
	private GuiTextFieldTooltip nameField;
	
	private static String player = Minecraft.getInstance().getSession().getUsername();
	
	private static final BuilderWorldHandler BUILDER_WORLD_HANDLER = new BuilderWorldHandler();
	
	public GuiWorldHandler(Content content) throws Exception
	{
		super(new StringTextComponent(content.getTitle()));
		this.content = content;
		this.tabSize = this.content.getCategory().getSize();
		this.tabDistanceTotal = Math.max(this.tabSize - 1, 1) * this.tabDistance;
		this.tabWidth = (this.bgTextureWidth - this.tabDistanceTotal) / Math.max(this.tabSize, 2);
		this.tabHalf = this.tabWidth / 2D;
		this.tabEpsilon = this.bgTextureWidth - (this.tabDistanceTotal + this.tabHalf * Math.max(this.tabSize, 2) * 2D);
		this.content.init(this);
	}
	
	@Override
	public void init()
	{
		super.init();
		
		ActionHelper.tryRun(() ->
		{
			this.finalButtons.clear();
			this.menus.clear();
			this.buttons.clear();
			this.children.clear();
			
			//INIT
			this.content.onPlayerNameChanged(this.getPlayer());
			this.content.initGui(this, this.getContentX(), this.getContentY());
			
			//MENUS
			
			for(IMenu menu : this.menus)
			{
				menu.initGui(this);
			}
			
			//SHORTCUTS
			
			final int x = this.width / 2 - 10;
			final int delta = 21;
			
			if(Config.getSettings().shortcuts())
			{
				this.finalButtons.add(new GuiButtonIcon(x - delta * 7, 0, 20, 20, EnumIcon.TIME_DAWN, I18n.format("gui.worldhandler.shortcuts.tooltip.time", I18n.format("gui.worldhandler.shortcuts.tooltip.time.dawn", Config.getSettings().getDawn())), ActionHelper::timeDawn));
				this.finalButtons.add(new GuiButtonIcon(x - delta * 6, 0, 20, 20, EnumIcon.TIME_NOON, I18n.format("gui.worldhandler.shortcuts.tooltip.time", I18n.format("gui.worldhandler.shortcuts.tooltip.time.noon", Config.getSettings().getNoon())), ActionHelper::timeNoon));
				this.finalButtons.add(new GuiButtonIcon(x - delta * 5, 0, 20, 20, EnumIcon.TIME_SUNSET, I18n.format("gui.worldhandler.shortcuts.tooltip.time", I18n.format("gui.worldhandler.shortcuts.tooltip.time.sunset", Config.getSettings().getSunset())), ActionHelper::timeSunset));
				this.finalButtons.add(new GuiButtonIcon(x - delta * 4, 0, 20, 20, EnumIcon.TIME_MIDNIGHT, I18n.format("gui.worldhandler.shortcuts.tooltip.time", I18n.format("gui.worldhandler.shortcuts.tooltip.time.midnight", Config.getSettings().getMidnight())), ActionHelper::timeMidnight));
				this.finalButtons.add(new GuiButtonIcon(x - delta * 3, 0, 20, 20, EnumIcon.WEATHER_SUN, I18n.format("gui.worldhandler.shortcuts.tooltip.weather", I18n.format("gui.worldhandler.shortcuts.tooltip.weather.clear")), ActionHelper::weatherClear));
				this.finalButtons.add(new GuiButtonIcon(x - delta * 2, 0, 20, 20, EnumIcon.WEATHER_RAIN, I18n.format("gui.worldhandler.shortcuts.tooltip.weather", I18n.format("gui.worldhandler.shortcuts.tooltip.weather.rainy")), ActionHelper::weatherRain));
				this.finalButtons.add(new GuiButtonIcon(x - delta * 1, 0, 20, 20, EnumIcon.WEATHER_STORM, I18n.format("gui.worldhandler.shortcuts.tooltip.weather", I18n.format("gui.worldhandler.shortcuts.tooltip.weather.thunder")), ActionHelper::weatherThunder));
				this.finalButtons.add(new GuiButtonIcon(x - delta * 0, 0, 20, 20, EnumIcon.DIFFICULTY_PEACEFUL, I18n.format("gui.worldhandler.shortcuts.tooltip.difficulty", I18n.format("gui.worldhandler.shortcuts.tooltip.difficulty.peaceful")), ActionHelper::difficultyPeaceful));
				this.finalButtons.add(new GuiButtonIcon(x + delta * 1, 0, 20, 20, EnumIcon.DIFFICULTY_EASY, I18n.format("gui.worldhandler.shortcuts.tooltip.difficulty", I18n.format("gui.worldhandler.shortcuts.tooltip.difficulty.easy")), ActionHelper::difficultyEasy));
				this.finalButtons.add(new GuiButtonIcon(x + delta * 2, 0, 20, 20, EnumIcon.DIFFICULTY_NORMAL, I18n.format("gui.worldhandler.shortcuts.tooltip.difficulty", I18n.format("gui.worldhandler.shortcuts.tooltip.difficulty.normal")), ActionHelper::difficultyNormal));
				this.finalButtons.add(new GuiButtonIcon(x + delta * 3, 0, 20, 20, EnumIcon.DIFFICULTY_HARD, I18n.format("gui.worldhandler.shortcuts.tooltip.difficulty", I18n.format("gui.worldhandler.shortcuts.tooltip.difficulty.hard")), ActionHelper::difficultyHard));
				this.finalButtons.add(new GuiButtonIcon(x + delta * 4, 0, 20, 20, EnumIcon.GAMEMODE_SURVIVAL, I18n.format("gui.worldhandler.shortcuts.tooltip.gamemode", I18n.format("gui.worldhandler.shortcuts.tooltip.gamemode.survival")), ActionHelper::gamemodeSurvival));
				this.finalButtons.add(new GuiButtonIcon(x + delta * 5, 0, 20, 20, EnumIcon.GAMEMODE_CREATIVE, I18n.format("gui.worldhandler.shortcuts.tooltip.gamemode", I18n.format("gui.worldhandler.shortcuts.tooltip.gamemode.creative")), ActionHelper::gamemodeCreative));
				this.finalButtons.add(new GuiButtonIcon(x + delta * 6, 0, 20, 20, EnumIcon.GAMEMODE_ADVENTURE, I18n.format("gui.worldhandler.shortcuts.tooltip.gamemode", I18n.format("gui.worldhandler.shortcuts.tooltip.gamemode.adventure")), ActionHelper::gamemodeAdventure));
				this.finalButtons.add(new GuiButtonIcon(x + delta * 7, 0, 20, 20, EnumIcon.GAMEMODE_SPECTATOR, I18n.format("gui.worldhandler.shortcuts.tooltip.gamemode", I18n.format("gui.worldhandler.shortcuts.tooltip.gamemode.spectator")), ActionHelper::gamemodeSpectator));
			}
			
			//SYNTAX
			
			if(Config.getSettings().commandSyntax())
			{
				this.syntaxField = new GuiTextFieldTooltip(x - delta * 7 + 1, this.height - 22, delta * 15 - 3, 20);
				this.updateSyntax();
			}
			
			//NAME
			
			this.nameField = new GuiTextFieldTooltip(0, 0, 0, 11);
			this.nameField.setMaxStringLength(16);
			this.nameField.setText(this.getPlayer());
			this.nameField.setResponder(text -> 
			{
				GuiWorldHandler.player = text;
				this.updateNameField();
			});
			this.updateNameField();
			
			//BUTTONS
			
			this.forEachTab(this::addTabButtons);
			this.initButtons();
		});
	}
	
	private void addTabButtons(int index, double xOffset)
	{
		final int backgroundX = this.getBackgroundX();
		final int backgroundY = this.getBackgroundY();
		
		IContent tab = this.content.getCategory().getContent(index);
		
		if(!tab.equals(this.content.getActiveContent()))
		{
			this.finalButtons.add(new GuiButtonTab((int) (backgroundX + xOffset), backgroundY - 20, (int) this.tabWidth + (int) Math.ceil(this.tabEpsilon / this.tabSize), 21, tab.getTabTitle())
			{
				@Override
				public void onPress()
				{
					ActionHelper.changeTab(GuiWorldHandler.this.content, index);
				}
			});
		}
	}
	
	public void initButtons()
	{
		this.buttons.clear();
		this.children.clear();
		this.content.initButtons(this, this.getContentX(), this.getContentY());
		
		if(this.finalButtons != null && !this.finalButtons.isEmpty())
		{
			this.finalButtons.forEach(this::add);
		}
		
		if(Config.getSettings().commandSyntax())
		{
			this.add(this.syntaxField);
		}
		
		this.add(this.nameField);
		
		for(IMenu menu : this.menus)
		{
			menu.initButtons(this);
		}
	}
	
	private int getContentX()
	{
		return this.getBackgroundX() + 8;
	}
	
	private int getContentY()
	{
		return this.getBackgroundY() + 33;
	}
	
	private int getXOffset()
	{
		return 0;
	}
	
	private int getYOffset()
	{
		return Config.getSettings().shortcuts() ? 11 : 8;
	}
	
	@Override
	public void tick()
	{
		ActionHelper.tryRun(this::update);
	}
	
	private void update()
	{
		this.content.tick(this);
		
		for(IMenu menu : this.menus)
		{
			menu.tick();
		}
		
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
		return Config.getSettings().watch() ? 9 : 0;
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
		if(Config.getSettings().commandSyntax() && this.syntaxField != null)
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
			
			this.syntaxField.tick();
		}
	}
	
	private void updateNameField()
	{
		final int backgroundX = this.getBackgroundX();
		final int backgroundY = this.getBackgroundY();
		
		if(GuiWorldHandler.player.isEmpty())
		{
			int width = this.font.getStringWidth(I18n.format("gui.worldhandler.generic.edit_username")) + 2;
			this.nameField.setWidth(width);
			this.nameField.setPosition(backgroundX  + this.bgTextureWidth - this.getWatchOffset() - 7 - (this.font.getStringWidth(this.content.getTitle()) + 2), backgroundY + 6);
		}
		else
		{
			int width = this.font.getStringWidth(GuiWorldHandler.player) + 2;
			this.nameField.setWidth(width);
			this.nameField.setPosition(backgroundX + this.bgTextureWidth - this.getWatchOffset() - 7 - width, backgroundY + 6);
		}
		
		this.content.onPlayerNameChanged(GuiWorldHandler.player);
	}
	
	private void defaultColor()
	{
		this.defaultColor(1.0F);
	}
	
	private void defaultColor(float alpha)
	{
		GlStateManager.enableBlend();
		GlStateManager.color4f(Config.getSkin().getBackgroundRedF(), Config.getSkin().getBackgroundGreenF(), Config.getSkin().getBackgroundBlueF(), alpha * Config.getSkin().getBackgroundAlphaF());
	}
	
	private void darkColor()
	{
		GlStateManager.enableBlend();
		GlStateManager.color4f(Config.getSkin().getBackgroundRedF() - 0.3F, Config.getSkin().getBackgroundGreenF() - 0.3F, Config.getSkin().getBackgroundBlueF() - 0.3F, Config.getSkin().getBackgroundAlphaF());
	}
	
	private void bindBackground()
	{
		Minecraft.getInstance().getTextureManager().bindTexture(ResourceHelper.getBackgroundTexture());
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
	public void render(int mouseX, int mouseY, float partialTicks)
	{
		ActionHelper.tryRun(() ->
		{
			final int backgroundX = this.getBackgroundX();
			final int backgroundY = this.getBackgroundY();
			
			//DEFAULT BACKGROUND
			
			if(Config.getSkin().drawBackground())
			{
				super.renderBackground();
			}
			
			//COLOR
			
			this.defaultColor();
			
			//BACKGROUND
			
			this.bindBackground();
			this.blit(backgroundX, backgroundY, 0, 0, this.bgTextureWidth, this.bgTextureHeight);
			
			//TABS
			
			this.forEachTab(this::drawTab);
			this.defaultColor();
			
			//VERSION LABEL
			
			final String label = Main.MC_VERSION + "-" + Main.MOD_VERSION;
			final int hexAlpha = (int) (0xFF * 0.2) << 24;
			final int color = Config.getSkin().getLabelColor() + hexAlpha;
			final int versionWidth = this.width - this.font.getStringWidth(label) - 2;
			final int versionHeight = this.height - 10;
			
			this.font.drawString(label, versionWidth, versionHeight, color);
			
			//TITLE
			
			final int maxWidth = this.bgTextureWidth - 7 - 2 - this.font.getStringWidth(GuiWorldHandler.player) - 2 - this.getWatchOffset() - 7;
			this.font.drawString(TextFormatting.shortenString(this.content.getTitle(), maxWidth, this.font), backgroundX + 7, backgroundY + 7, Config.getSkin().getLabelColor());
			
			//NAME FIELD
			
			final String username = GuiWorldHandler.player.isEmpty() && !this.nameField.isFocused() ? I18n.format("gui.worldhandler.generic.edit_username") : GuiWorldHandler.player;
			this.font.drawString(username, backgroundX + 232 - this.font.getStringWidth(username), backgroundY + 7, Config.getSkin().getLabelColor());
			
			//WATCH
			
			if(Config.getSettings().watch())
			{
				final int watchX = backgroundX + 233;
				final int watchY = backgroundY + 5;
				
				UtilRender.drawWatchIntoGui(this, watchX, watchY, Minecraft.getInstance().world.getWorldInfo().getDayTime(), Config.getSettings().smoothWatch());
				
				if(Config.getSettings().tooltips())
				{
					if(mouseX >= watchX && mouseX <= watchX + 9 && mouseY >= watchY && mouseY <= watchY + 9)
					{
						GuiUtils.drawHoveringText(Arrays.asList(TextFormatting.formatWorldTime(Minecraft.getInstance().world.getDayTime())), mouseX, mouseY + 9, this.width, this.height, this.width, this.font);
						GlStateManager.disableLighting();
					}
				}
			}
			
			//BUTTONS
			
			for(int x = 0; x < this.buttons.size(); x++)
			{
				this.buttons.get(x).render(mouseX, mouseY, partialTicks);
			}
			
			//CONTAINER
			
			this.content.drawScreen(this, this.getContentX(), this.getContentY(), mouseX, mouseY, partialTicks);
			
			//MENUS
			
			for(IMenu menu : this.menus)
			{
				menu.draw(mouseX, mouseY, partialTicks);
			}
			
			//SYNTAX
			
			if(Config.getSettings().commandSyntax() && this.syntaxField != null)
			{
				this.syntaxField.renderButton(mouseX, mouseY, partialTicks);
			}
			
			//SPLASHTEXT
			
			if(this.splash != null)
			{
				GlStateManager.pushMatrix();
				RenderHelper.enableGUIStandardItemLighting();
				GlStateManager.disableLighting();
				GlStateManager.translatef((float) (backgroundX + 212), backgroundY + 15, 0.0F);
				GlStateManager.rotatef(17.0F, 0.0F, 0.0F, 1.0F);
				
				float scale = 1.1F - MathHelper.abs(MathHelper.sin((float) (System.currentTimeMillis() % 1000L) / 1000.0F * (float) Math.PI * 2.0F) * 0.1F);
				scale = scale * 100.0F / this.font.getStringWidth(this.splash);
				GlStateManager.scalef(scale, scale, scale);
				
				this.drawCenteredString(this.font, this.splash, 0, (int) scale, 0xFFFF00);
				
				GlStateManager.popMatrix();
			}
			
			//TOOLTIPS
			
			if(Config.getSettings().tooltips())
			{
				for(Widget button : this.buttons)
				{
					if(button instanceof GuiButtonTooltip)
					{
						((GuiButtonTooltip) button).renderTooltip(mouseX, mouseY);
					}
				}
			}
			
			//VERSION LABEL TOOLTIP
			
			if(mouseX >= versionWidth && mouseY >= versionHeight)
			{
				GuiUtils.drawHoveringText(Arrays.asList(label), versionWidth - 12, versionHeight + 12, this.width + this.font.getStringWidth(label), this.height + 10, this.width, this.font);
			}
		});
	}
	
	private void drawTab(int index, Double xOffset)
	{
		final IContent tab = this.content.getCategory().getContent(index);
		
		final int backgroundX = this.getBackgroundX();
		final int backgroundY = this.getBackgroundY();
		
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
		this.blit((int) (backgroundX + xOffset), (int) (backgroundY + yOffset), 0, 0, (int) Math.ceil(this.tabHalf), fHeight);
		this.blit((int) (backgroundX + this.tabHalf + xOffset), (int) (backgroundY + yOffset), this.bgTextureWidth - (int) Math.floor(this.tabHalf + 1), 0, (int) Math.floor(this.tabHalf + 1), fHeight);
		
		if(!Config.getSkin().sharpEdges())
		{
			if(this.content.getActiveContent().equals(tab))
			{
				//RIGHT TAB CURVATURE
				
				if(index < this.tabSize - 1 || this.tabSize == 1)
				{
					int factor = 2;
					
					for(int x = 0; x < factor; x++)
					{
						this.blit((int) (backgroundX + xOffset - x - 1 + Math.floor(this.tabHalf + 1) + this.tabHalf), (int) (backgroundY + x + 1), (int) (this.tabWidth - x - 1), x + 1, x + 1, 1);
					}
				}
				
				//LEFT TAB CURVATURE
				
				if(index > 0)
				{
					int factor = 2;
					
					for(int x = 0; x < factor; x++)
					{
						this.blit((int) (backgroundX + xOffset), (int) (backgroundY + x + 1), xOffset.intValue(), x + 1, x + 1, 1);
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
						this.blit((int) (backgroundX + xOffset), (int) (backgroundY + yOffset + fHeight + x / interval), 0, fHeight, width - x, 1);
					}
				}
				
				//RIGHT GRADIENT
				
				if(index == this.tabSize - 1 && this.tabSize > 1)
				{
					int offset = 3;
					
					for(int x = 0; x < width; x += interval)
					{
						this.defaultColor(1.0F - (x / (width + 5.0F * interval)));
						this.blit((int) (backgroundX + Math.ceil(xOffset) + x + offset), (int) (backgroundY + yOffset + fHeight + x / interval), this.bgTextureWidth - width + x, fHeight, width - x, 1);
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
						this.blit(backgroundX, backgroundY + x, 0, fHeight, factor - x, 1);
					}
				}
				
				//RIGHT CORNER FILLER
				
				if(index == this.tabSize - 1)
				{
					int factor = 3;
					
					for(int x = 0; x < factor + 1; x++)
					{
						this.blit(backgroundX + this.bgTextureWidth - x, backgroundY + factor - x, this.bgTextureWidth - x, fHeight, x, 1);
					}
				}
			}
		}
		
		this.drawCenteredString(this.font, TextFormatting.shortenString(net.minecraft.util.text.TextFormatting.UNDERLINE + tab.getTabTitle(), (int) this.tabWidth, this.font), (int) (backgroundX + this.tabHalf + xOffset), (int) (backgroundY - 13), color);
	}
	
	@Override
	public boolean charTyped(char charTyped, int keyCode)
	{
		if(this.nameField.isFocused())
		{
			this.nameField.setCursorPositionEnd();
		}
		
		return super.charTyped(charTyped, keyCode);
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int keyCode)
	{
		if(this.nameField.isFocused())
		{
			this.nameField.setCursorPositionEnd();
		}
		
		return super.mouseClicked(mouseX, mouseY, keyCode);
	}
	
	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers)
	{
		boolean focused = this.getFocused() != null;
		
		if(focused && this.getFocused() instanceof Widget)
		{
			focused = ((Widget) this.getFocused()).isFocused();
		}
		
		if(!focused && KeyHandler.isPressed(KeyHandler.KEY_WORLD_HANDLER, keyCode))
		{
			this.onClose();
			return true;
		}
		
		return super.keyPressed(keyCode, scanCode, modifiers);
	}
	
	@Override
	public void onClose()
	{
		ActionHelper.tryRun(this.content::onGuiClosed);
		super.onClose();
	}
	
	@Override
	public boolean isPauseScreen()
	{
		return Config.getSettings().pause();
	}
	
	@Override
	public String getPlayer()
	{
		return GuiWorldHandler.player;
	}
	
	@Override
	public Content getContent()
	{
		return this.content;
	}
	
	@Override
	public boolean shouldCloseOnEsc()
	{
		return true;
	}
}