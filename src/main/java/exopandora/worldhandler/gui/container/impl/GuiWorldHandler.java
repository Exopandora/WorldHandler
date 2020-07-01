package exopandora.worldhandler.gui.container.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.function.BiConsumer;

import javax.annotation.Nullable;

import com.google.common.base.Predicates;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

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
import exopandora.worldhandler.util.RenderUtils;
import exopandora.worldhandler.util.ResourceHelper;
import exopandora.worldhandler.util.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.gui.GuiUtils;

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
		super(content.getTitle());
		this.content = content;
		this.tabSize = this.content.getCategory().getSize();
		this.tabDistanceTotal = Math.max(this.tabSize - 1, 1) * this.tabDistance;
		this.tabWidth = (this.bgTextureWidth - this.tabDistanceTotal) / Math.max(this.tabSize, 2);
		this.tabHalf = this.tabWidth / 2D;
		this.tabEpsilon = this.bgTextureWidth - (this.tabDistanceTotal + this.tabHalf * Math.max(this.tabSize, 2) * 2D);
		this.content.init(this);
	}
	
	@Override
	public void func_231160_c_()
	{
		super.func_231160_c_();
		
		ActionHelper.tryRun(() ->
		{
			this.finalButtons.clear();
			this.menus.clear();
			this.field_230710_m_.clear();
			this.field_230705_e_.clear();
			
			//INIT
			this.content.onPlayerNameChanged(this.getPlayer());
			this.content.initGui(this, this.getContentX(), this.getContentY());
			
			//MENUS
			
			for(IMenu menu : this.menus)
			{
				menu.initGui(this);
			}
			
			//SHORTCUTS
			
			final int x = this.field_230708_k_ / 2 - 10;
			final int delta = 21;
			
			if(Config.getSettings().shortcuts())
			{
				this.finalButtons.add(new GuiButtonIcon(x - delta * 7, 0, 20, 20, EnumIcon.TIME_DAWN, new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.time", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.time.dawn", Config.getSettings().getDawn())), ActionHelper::timeDawn));
				this.finalButtons.add(new GuiButtonIcon(x - delta * 6, 0, 20, 20, EnumIcon.TIME_NOON, new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.time", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.time.noon", Config.getSettings().getNoon())), ActionHelper::timeNoon));
				this.finalButtons.add(new GuiButtonIcon(x - delta * 5, 0, 20, 20, EnumIcon.TIME_SUNSET, new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.time", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.time.sunset", Config.getSettings().getSunset())), ActionHelper::timeSunset));
				this.finalButtons.add(new GuiButtonIcon(x - delta * 4, 0, 20, 20, EnumIcon.TIME_MIDNIGHT, new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.time", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.time.midnight", Config.getSettings().getMidnight())), ActionHelper::timeMidnight));
				this.finalButtons.add(new GuiButtonIcon(x - delta * 3, 0, 20, 20, EnumIcon.WEATHER_SUN, new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.weather", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.weather.clear")), ActionHelper::weatherClear));
				this.finalButtons.add(new GuiButtonIcon(x - delta * 2, 0, 20, 20, EnumIcon.WEATHER_RAIN, new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.weather", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.weather.rainy")), ActionHelper::weatherRain));
				this.finalButtons.add(new GuiButtonIcon(x - delta * 1, 0, 20, 20, EnumIcon.WEATHER_STORM, new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.weather", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.weather.thunder")), ActionHelper::weatherThunder));
				this.finalButtons.add(new GuiButtonIcon(x - delta * 0, 0, 20, 20, EnumIcon.DIFFICULTY_PEACEFUL, new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.difficulty", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.difficulty.peaceful")), ActionHelper::difficultyPeaceful));
				this.finalButtons.add(new GuiButtonIcon(x + delta * 1, 0, 20, 20, EnumIcon.DIFFICULTY_EASY, new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.difficulty", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.difficulty.easy")), ActionHelper::difficultyEasy));
				this.finalButtons.add(new GuiButtonIcon(x + delta * 2, 0, 20, 20, EnumIcon.DIFFICULTY_NORMAL, new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.difficulty", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.difficulty.normal")), ActionHelper::difficultyNormal));
				this.finalButtons.add(new GuiButtonIcon(x + delta * 3, 0, 20, 20, EnumIcon.DIFFICULTY_HARD, new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.difficulty", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.difficulty.hard")), ActionHelper::difficultyHard));
				this.finalButtons.add(new GuiButtonIcon(x + delta * 4, 0, 20, 20, EnumIcon.GAMEMODE_SURVIVAL, new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.gamemode", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.gamemode.survival")), ActionHelper::gamemodeSurvival));
				this.finalButtons.add(new GuiButtonIcon(x + delta * 5, 0, 20, 20, EnumIcon.GAMEMODE_CREATIVE, new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.gamemode", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.gamemode.creative")), ActionHelper::gamemodeCreative));
				this.finalButtons.add(new GuiButtonIcon(x + delta * 6, 0, 20, 20, EnumIcon.GAMEMODE_ADVENTURE, new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.gamemode", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.gamemode.adventure")), ActionHelper::gamemodeAdventure));
				this.finalButtons.add(new GuiButtonIcon(x + delta * 7, 0, 20, 20, EnumIcon.GAMEMODE_SPECTATOR, new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.gamemode", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.gamemode.spectator")), ActionHelper::gamemodeSpectator));
			}
			
			//SYNTAX
			
			if(Config.getSettings().commandSyntax())
			{
				this.syntaxField = new GuiTextFieldTooltip(x - delta * 7 + 1, this.field_230709_l_ - 22, delta * 15 - 3, 20);
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
				public void func_230930_b_() //onPress
				{
					ActionHelper.changeTab(GuiWorldHandler.this.content, index);
				}
			});
		}
	}
	
	public void initButtons()
	{
		this.field_230710_m_.clear();
		this.field_230705_e_.clear();
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
	
	@Override
	public void func_231023_e_() //tick
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
		return (this.field_230708_k_ - this.bgTextureWidth) / 2 + this.getXOffset();
	}
	
	private int getBackgroundY()
	{
		return (this.field_230709_l_ - this.bgTextureHeight) / 2 + this.getYOffset();
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
			if(!this.syntaxField.func_230999_j_()) //isFocused
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
			int width = this.field_230712_o_.getStringWidth(I18n.format("gui.worldhandler.generic.edit_username")) + 2;
			this.nameField.func_230991_b_(width); //setWidth
			this.nameField.setPosition(backgroundX  + this.bgTextureWidth - this.getWatchOffset() - 7 - (this.field_230712_o_.func_238414_a_(this.content.getTitle()) + 2), backgroundY + 6);
		}
		else
		{
			int width = this.field_230712_o_.getStringWidth(GuiWorldHandler.player) + 2;
			this.nameField.func_230991_b_(width); //setWidth
			this.nameField.setPosition(backgroundX + this.bgTextureWidth - this.getWatchOffset() - 7 - width, backgroundY + 6);
		}
		
		this.content.onPlayerNameChanged(GuiWorldHandler.player);
	}
	
	@Override
	public void func_230430_a_(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) //render
	{
		ActionHelper.tryRun(() ->
		{
			final int backgroundX = this.getBackgroundX();
			final int backgroundY = this.getBackgroundY();
			
			//DEFAULT BACKGROUND
			
			if(Config.getSkin().drawBackground())
			{
				this.func_230926_e_(-1); //setBlitOffset
				super.func_230446_a_(matrix); //renderBackground
			}
			
			//COLOR
			
			this.defaultColor();
			
			//BACKGROUND
			
			this.bindBackground();
			this.func_238474_b_(matrix, backgroundX, backgroundY, 0, 0, this.bgTextureWidth, this.bgTextureHeight); //blit
			
			//TABS
			
			this.func_230926_e_(0); //setBlitOffset
			this.forEachTab((index, xOffset) -> this.drawTab(matrix, index, xOffset));
			this.defaultColor();
			
			//VERSION LABEL
			
			final String label = Main.MC_VERSION + "-" + Main.MOD_VERSION;
			final int hexAlpha = (int) (0xFF * 0.2) << 24;
			final int color = Config.getSkin().getLabelColor() + hexAlpha;
			final int versionWidth = this.field_230708_k_ - this.field_230712_o_.getStringWidth(label) - 2;
			final int versionHeight = this.field_230709_l_ - 10;
			
			this.field_230712_o_.func_238421_b_(matrix, label, versionWidth, versionHeight, color);
			
			//TITLE
			
			final int maxWidth = this.bgTextureWidth - 7 - 2 - this.field_230712_o_.getStringWidth(GuiWorldHandler.player) - 2 - this.getWatchOffset() - 7;
			this.field_230712_o_.func_238422_b_(matrix, TextUtils.stripText(this.content.getTitle(), maxWidth, this.field_230712_o_), backgroundX + 7, backgroundY + 7, Config.getSkin().getLabelColor());
			
			//NAME FIELD
			
			final String username = GuiWorldHandler.player.isEmpty() && !this.nameField.func_230999_j_() ? I18n.format("gui.worldhandler.generic.edit_username") : GuiWorldHandler.player; //isFocused
			this.field_230712_o_.func_238421_b_(matrix, username, backgroundX + this.bgTextureWidth - this.getWatchOffset() - 7 - this.field_230712_o_.getStringWidth(username), backgroundY + 7, Config.getSkin().getLabelColor());
			
			//WATCH
			
			if(Config.getSettings().watch())
			{
				final int watchX = backgroundX + 233;
				final int watchY = backgroundY + 5;
				
				RenderUtils.drawWatchIntoGui(matrix, this, watchX, watchY, Minecraft.getInstance().world.getWorldInfo().getDayTime(), Config.getSettings().smoothWatch());
				
				if(Config.getSettings().tooltips())
				{
					if(mouseX >= watchX && mouseX <= watchX + 9 && mouseY >= watchY && mouseY <= watchY + 9)
					{
						GuiUtils.drawHoveringText(matrix, Arrays.asList(new StringTextComponent(TextUtils.formatWorldTime(Minecraft.getInstance().world.getDayTime()))), mouseX, mouseY + 9, this.field_230708_k_, this.field_230709_l_, this.field_230708_k_, this.field_230712_o_);
						RenderUtils.disableLighting();
					}
				}
			}
			
			//BUTTONS
			
			for(int x = 0; x < this.field_230710_m_.size(); x++)
			{
				this.field_230710_m_.get(x).func_230430_a_(matrix, mouseX, mouseY, partialTicks); //render
			}
			
			//CONTAINER
			
			this.content.drawScreen(matrix, this, this.getContentX(), this.getContentY(), mouseX, mouseY, partialTicks);
			
			//MENUS
			
			for(IMenu menu : this.menus)
			{
				menu.draw(matrix, mouseX, mouseY, partialTicks);
			}
			
			//SYNTAX
			
			if(Config.getSettings().commandSyntax() && this.syntaxField != null)
			{
				this.syntaxField.func_230431_b_(matrix, mouseX, mouseY, partialTicks); //renderButton
			}
			
			//SPLASHTEXT
			
			if(this.splash != null)
			{
				RenderHelper.enableStandardItemLighting();
				RenderUtils.disableLighting();
				
				matrix.push();
				matrix.translate((float) (backgroundX + 212), backgroundY + 15, 0.0F);
				matrix.rotate(new Quaternion(17.0F, 0.0F, 0.0F, 1.0F));
				
				float scale = 1.1F - MathHelper.abs(MathHelper.sin((float) (System.currentTimeMillis() % 1000L) / 1000.0F * (float) Math.PI * 2.0F) * 0.1F);
				scale = scale * 100.0F / this.field_230712_o_.getStringWidth(this.splash);
				matrix.scale(scale, scale, scale);
				
				this.func_238471_a_(matrix, this.field_230712_o_, this.splash, 0, (int) scale, 0xFFFF00);
				
				matrix.pop();
			}
			
			//TOOLTIPS
			
			if(Config.getSettings().tooltips())
			{
				for(Widget button : this.field_230710_m_)
				{
					if(button instanceof GuiButtonTooltip)
					{
						((GuiButtonTooltip) button).renderTooltip(matrix, mouseX, mouseY);
					}
				}
			}
			
			//VERSION LABEL TOOLTIP
			
			if(mouseX >= versionWidth && mouseY >= versionHeight)
			{
				GuiUtils.drawHoveringText(matrix, Arrays.asList(new StringTextComponent(label)), versionWidth - 12, versionHeight + 12, this.field_230708_k_ + this.field_230712_o_.getStringWidth(label), this.field_230709_l_ + 10, this.field_230708_k_, this.field_230712_o_);
			}
		});
	}
	
	private void drawTab(MatrixStack matrix, int index, double xOffset)
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
		this.func_230926_e_(-1); //setBlitOffset
		this.func_238474_b_(matrix, (int) (backgroundX + xOffset), (int) (backgroundY + yOffset), 0, 0, (int) Math.ceil(this.tabHalf), fHeight);
		this.func_238474_b_(matrix, (int) (backgroundX + this.tabHalf + xOffset), (int) (backgroundY + yOffset), this.bgTextureWidth - (int) Math.floor(this.tabHalf + 1), 0, (int) Math.floor(this.tabHalf + 1), fHeight);
		
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
						this.func_238474_b_(matrix, (int) (backgroundX + xOffset - x - 1 + Math.floor(this.tabHalf + 1) + this.tabHalf), (int) (backgroundY + x + 1), (int) (this.tabWidth - x - 1), x + 1, x + 1, 1);
					}
				}
				
				//LEFT TAB CURVATURE
				
				if(index > 0)
				{
					int factor = 2;
					
					for(int x = 0; x < factor; x++)
					{
						this.func_238474_b_(matrix, (int) (backgroundX + xOffset), (int) (backgroundY + x + 1), (int) xOffset, x + 1, x + 1, 1);
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
						this.func_238474_b_(matrix, (int) (backgroundX + xOffset), (int) (backgroundY + yOffset + fHeight + x / interval), 0, fHeight, width - x, 1);
					}
				}
				
				//RIGHT GRADIENT
				
				if(index == this.tabSize - 1 && this.tabSize > 1)
				{
					int offset = 3;
					
					for(int x = 0; x < width; x += interval)
					{
						this.defaultColor(1.0F - (x / (width + 5.0F * interval)));
						this.func_238474_b_(matrix, (int) (backgroundX + Math.ceil(xOffset) + x + offset), (int) (backgroundY + yOffset + fHeight + x / interval), this.bgTextureWidth - width + x, fHeight, width - x, 1);
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
						this.func_238474_b_(matrix, backgroundX, backgroundY + x, 0, fHeight, factor - x, 1);
					}
				}
				
				//RIGHT CORNER FILLER
				
				if(index == this.tabSize - 1)
				{
					int factor = 3;
					
					for(int x = 0; x < factor + 1; x++)
					{
						this.func_238474_b_(matrix, backgroundX + this.bgTextureWidth - x, backgroundY + factor - x, this.bgTextureWidth - x, fHeight, x, 1);
					}
				}
			}
		}
		
		this.func_230926_e_(0); //setBlitOffset
		this.func_238472_a_(matrix, this.field_230712_o_, TextUtils.stripText(tab.getTabTitle().func_240699_a_(TextFormatting.UNDERLINE), (int) this.tabWidth, this.field_230712_o_), (int) (backgroundX + this.tabHalf + xOffset), (int) (backgroundY - 13), color); //drawCenteredString
	}
	
	@Override
	public void mouseMoved(double xPos, double mouseY)
	{
		this.content.mouseMoved(xPos, mouseY);
	}
	
	@Override
	public boolean func_231044_a_(double mouseX, double mouseY, int keyCode) //mouseClicked
	{
		if(this.nameField.func_230999_j_()) //isFocused
		{
			this.nameField.setCursorPositionEnd();
		}
		
		if(this.content.func_231044_a_(mouseX, mouseY, keyCode))
		{
			return true;
		}
		
		return super.func_231044_a_(mouseX, mouseY, keyCode);
	}
	
	@Override
	public boolean func_231048_c_(double mouseX, double mouseY, int keyCode) //mouseReleased
	{
		if(this.content.func_231048_c_(mouseX, mouseY, keyCode))
		{
			return true;
		}
		
		return super.func_231048_c_(mouseX, mouseY, keyCode);
	}
	
	@Override
	public boolean func_231045_a_(double mouseX, double mouseY, int keyCode, double deltaX, double deltaY) //mouseDragged
	{
		if(this.content.func_231045_a_(mouseX, mouseY, keyCode, deltaX, deltaY))
		{
			return true;
		}
		
		return super.func_231045_a_(mouseX, mouseY, keyCode, deltaX, deltaY);
	}
	
	@Override
	public boolean func_231043_a_(double mouseX, double mouseY, double distance) //mouseScrolled
	{
		if(this.content.func_231043_a_(mouseX, mouseY, distance))
		{
			return true;
		}
		
		return super.func_231043_a_(mouseX, mouseY, distance);
	}
	
	@Override
	public boolean func_231046_a_(int keyCode, int scanCode, int modifiers) //keyPressed
	{
		boolean focused = this.func_241217_q_() != null;
		
		if(focused && this.func_241217_q_() instanceof Widget)
		{
			focused = ((Widget) this.func_241217_q_()).func_230999_j_(); //getFocused().isFocused()
		}
		
		if(!focused && KeyHandler.isPressed(KeyHandler.KEY_WORLD_HANDLER, keyCode))
		{
			this.func_231175_as__();
			return true;
		}
		
		if(this.content.func_231046_a_(keyCode, scanCode, modifiers))
		{
			return true;
		}
		
		return super.func_231046_a_(keyCode, scanCode, modifiers);
	}
	
	@Override
	public boolean keyReleased(int keyCode, int scanCode, int modifiers)
	{
		if(this.content.keyReleased(keyCode, scanCode, modifiers))
		{
			return true;
		}
		
		return super.keyReleased(keyCode, scanCode, modifiers);
	}
	
	@Override
	public boolean func_231042_a_(char charTyped, int keyCode) //charTyped
	{
		if(this.nameField.func_230999_j_()) //isFocused
		{
			this.nameField.setCursorPositionEnd();
		}
		
		if(this.content.func_231042_a_(charTyped, keyCode))
		{
			return true;
		}
		
		return super.func_231042_a_(charTyped, keyCode);
	}
	
	@Override
	public boolean func_231049_c__(boolean focus) //changeFocus
	{
		if(this.content.func_231049_c__(focus))
		{
			return true;
		}
		
		return super.func_231049_c__(focus);
	}
	
	@Override
	public boolean func_231047_b_(double mouseX, double mouseY) //isMouseOver
	{
		if(this.content.func_231047_b_(mouseX, mouseY))
		{
			return true;
		}
		
		return super.func_231047_b_(mouseX, mouseY);
	}
	
	private void defaultColor()
	{
		this.defaultColor(1.0F);
	}
	
	private void defaultColor(float alpha)
	{
		RenderSystem.enableBlend();
		RenderUtils.color(Config.getSkin().getBackgroundRedF(), Config.getSkin().getBackgroundGreenF(), Config.getSkin().getBackgroundBlueF(), alpha * Config.getSkin().getBackgroundAlphaF());
	}
	
	private void darkColor()
	{
		RenderSystem.enableBlend();
		RenderUtils.color(Config.getSkin().getBackgroundRedF() - 0.3F, Config.getSkin().getBackgroundGreenF() - 0.3F, Config.getSkin().getBackgroundBlueF() - 0.3F, Config.getSkin().getBackgroundAlphaF());
	}
	
	private void bindBackground()
	{
		Minecraft.getInstance().getTextureManager().bindTexture(ResourceHelper.getBackgroundTexture());
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
	public void func_231175_as__() //onClose
	{
		ActionHelper.tryRun(this.content::onGuiClosed);
		super.func_231175_as__();
	}
	
	@Override
	public boolean func_231177_au__() //isPauseScreen
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
	public boolean func_231178_ax__() //shouldCloseOnEsc
	{
		return true;
	}
}