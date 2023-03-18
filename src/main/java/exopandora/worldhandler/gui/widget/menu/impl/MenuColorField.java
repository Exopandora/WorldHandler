package exopandora.worldhandler.gui.widget.menu.impl;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiButtonList;
import exopandora.worldhandler.gui.widget.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.widget.menu.Menu;
import exopandora.worldhandler.util.UserStylableComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;

public class MenuColorField extends Menu
{
	private static final List<ChatFormatting> COLORS = new ArrayList<ChatFormatting>();
	
	static
	{
		COLORS.add(ChatFormatting.RESET);
		COLORS.add(ChatFormatting.YELLOW);
		COLORS.add(ChatFormatting.GOLD);
		COLORS.add(ChatFormatting.DARK_RED);
		COLORS.add(ChatFormatting.RED);
		COLORS.add(ChatFormatting.LIGHT_PURPLE);
		COLORS.add(ChatFormatting.DARK_PURPLE);
		COLORS.add(ChatFormatting.BLUE);
		COLORS.add(ChatFormatting.DARK_BLUE);
		COLORS.add(ChatFormatting.DARK_AQUA);
		COLORS.add(ChatFormatting.AQUA);
		COLORS.add(ChatFormatting.GREEN);
		COLORS.add(ChatFormatting.DARK_GREEN);
		COLORS.add(ChatFormatting.BLACK);
		COLORS.add(ChatFormatting.DARK_GRAY);
		COLORS.add(ChatFormatting.GRAY);
		COLORS.add(ChatFormatting.WHITE);
	}
	
	private GuiTextFieldTooltip textField;
	private final UserStylableComponent component;
	private final ILogicColorMenu logic;
	private final String translationKey;
	
	public MenuColorField(int x, int y, String translationKey, UserStylableComponent wrappedComponent)
	{
		this(x, y, translationKey, wrappedComponent, new ILogicColorMenu(){});
	}
	
	public MenuColorField(int x, int y, String translationKey, UserStylableComponent wrappedComponent, ILogicColorMenu logic)
	{
		super(x, y);
		this.translationKey = translationKey;
		this.component = wrappedComponent;
		this.logic = logic;
	}
	
	@Override
	public void initGui(Container container)
	{
		this.textField = new GuiTextFieldTooltip(this.x + 118, this.y, 114, 20, Component.translatable(this.translationKey));
		this.textField.setFilter(this.logic::validate);
		this.textField.setFormatter((string, index) -> FormattedCharSequence.forward(string, this.component.getStyle()));
		this.textField.setValue(this.component.getText());
		this.textField.setResponder(this.component::setText);
	}
	
	@Override
	public void initButtons(Container container)
	{
		if(this.logic.doDrawTextField())
		{
			container.addRenderableWidget(this.textField);
		}
		
		if(this.logic.doDrawButtons())
		{
			container.addRenderableWidget(new GuiButtonList<ChatFormatting>(this.x + 118, this.y + 24, COLORS, 114, 20, container, new ILogicMapped<ChatFormatting>()
			{
				@Override
				public MutableComponent translate(ChatFormatting item)
				{
					return Component.translatable("gui.worldhandler.color").withStyle(item).append(": ").append(Component.translatable("gui.worldhandler.color." + item.getName()));
				}
				
				@Override
				public MutableComponent toTooltip(ChatFormatting item)
				{
					return null;
				}
				
				@Override
				public MutableComponent formatTooltip(ChatFormatting item, int index, int max)
				{
					return null;
				}
				
				@Override
				public void onClick(ChatFormatting item)
				{
					MenuColorField.this.component.withStyle(item);
				}
				
				@Override
				public String getId()
				{
					return MenuColorField.this.logic.getId();
				}
			}));
			
			container.addRenderableWidget(new GuiButtonBase(this.x + 118, this.y + 48, 20, 20, Component.literal("I").setStyle(Style.EMPTY.withItalic(this.component.getStyle().isItalic())), () ->
			{
				this.component.setStyle(this.component.getStyle().withItalic(!this.component.getStyle().isItalic()));
				container.init();
			}));
			container.addRenderableWidget(new GuiButtonBase(this.x + 118 + 24 - 1, this.y + 48, 20, 20, Component.literal("B").setStyle(Style.EMPTY.withBold(this.component.getStyle().isBold())), () ->
			{
				this.component.setStyle(this.component.getStyle().withBold(!this.component.getStyle().isBold()));
				container.init();
			}));
			container.addRenderableWidget(new GuiButtonBase(this.x + 118 + 24 * 2 - 1, this.y + 48, 20, 20, Component.literal("U").setStyle(Style.EMPTY.withUnderlined(this.component.getStyle().isUnderlined())), () ->
			{
				this.component.setStyle(this.component.getStyle().withUnderlined(!this.component.getStyle().isUnderlined()));
				container.init();
			}));
			container.addRenderableWidget(new GuiButtonBase(this.x + 118 + 24 * 3 - 1, this.y + 48, 20, 20, Component.literal("S").setStyle(Style.EMPTY.withStrikethrough(this.component.getStyle().isStrikethrough())), () ->
			{
				this.component.setStyle(this.component.getStyle().withStrikethrough(!this.component.getStyle().isStrikethrough()));
				container.init();
			}));
			container.addRenderableWidget(new GuiButtonBase(this.x + 118 + 24 * 4 - 2, this.y + 48, 20, 20, Component.literal("O").setStyle(Style.EMPTY.withObfuscated(this.component.getStyle().isObfuscated())), () ->
			{
				this.component.setStyle(this.component.getStyle().withObfuscated(!this.component.getStyle().isObfuscated()));
				container.init();
			}));
		}
	}
	
	@Override
	public void tick()
	{
		if(this.logic.doDrawTextField())
		{
			this.textField.tick();
		}
	}
	
	@Override
	public void draw(PoseStack matrix, int mouseX, int mouseY, float partialTicks)
	{
		
	}
}
