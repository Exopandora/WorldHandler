package exopandora.worldhandler.gui.menu.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;

import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.menu.Menu;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiButtonList;
import exopandora.worldhandler.gui.widget.button.GuiButtonList.Persistence;
import exopandora.worldhandler.util.Node;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;

public class MenuButtonList extends Menu
{
	private final List<Node> items;
	private final ILogicButtonList logic;
	private final MenuButtonList parent;
	private final int depth;
	private final int maxDepth;
	
	public MenuButtonList(int x, int y, List<Node> list, int maxDepth, ILogicButtonList logic)
	{
		this(x, y, list, maxDepth, logic, null);
	}
	
	private MenuButtonList(int x, int y, List<Node> list, int maxDepth, ILogicButtonList logic, MenuButtonList parent)
	{
		super(x, y);
		this.items = list;
		this.logic = logic;
		this.parent = parent;
		this.depth = this.parent != null ? this.parent.getDepth() + 1 : 0;
		this.maxDepth = maxDepth;
	}
	
	@Override
	public void initGui(Container container)
	{
		
	}
	
	@Override
	public void initButtons(Container container)
	{
		container.add(new GuiButtonList<Node>(this.x, this.y, this.items, 114, 20, container, new ILogicMapped<Node>()
		{
			@Override
			public MutableComponent translate(Node item)
			{
				return MenuButtonList.this.logic.translate(MenuButtonList.this.buildKey(container, MenuButtonList.this.logic::buildTranslationKey), MenuButtonList.this.getDepth());
			}
			
			@Override
			public MutableComponent toTooltip(Node item)
			{
				return new TextComponent(item.getKey());
			}
			
			@Override
			public MutableComponent formatTooltip(Node item, int index, int max)
			{
				return ILogicMapped.super.formatTooltip(item, index, max);
			}
			
			@Override
			public void onClick(Node item)
			{
				MenuButtonList.this.getPersistence(container, 1).setIndex(0);
				container.init();
			}
			
			@Override
			public String getId()
			{
				return MenuButtonList.this.getId();
			}
		}));
		
		Node node = this.getNode(container);
		this.logic.onClick(this.buildKey(container, this.logic::buildEventKey), this.getDepth());
		
		if(node.getEntries() != null)
		{
			MenuButtonList child = new MenuButtonList(this.x, this.y + 24, node.getEntries(), this.maxDepth, this.logic, this);
			child.initButtons(container);
		}
		else
		{
			for(int x = this.getDepth() + 1; x < this.maxDepth; x++)
			{
				GuiButtonBase button = new GuiButtonBase(this.x, this.y + 24 * x, 114, 20, TextComponent.EMPTY, null);
				button.active = false;
				container.add(button);
			}
		}
	}
	
	@Override
	public void tick()
	{
		
	}
	
	@Override
	public void draw(PoseStack matrix, int mouseX, int mouseY, float partialTicks)
	{
		
	}
	
	protected int getDepth()
	{
		return this.depth;
	}
	
	@Nullable
	protected MenuButtonList getParent()
	{
		return this.parent;
	}
	
	protected String getId()
	{
		return this.getId(0);
	}
	
	protected String getId(int offset)
	{
		return String.format("%s%d", MenuButtonList.this.logic.getId(), MenuButtonList.this.depth + offset);
	}
	
	protected Persistence getPersistence(Container container)
	{
		return this.getPersistence(container, 0);
	}
	
	protected Persistence getPersistence(Container container, int offset)
	{
		return container.getContent().getPersistence(this.getId(offset), Persistence::new);
	}
	
	protected Node getNode(Container container)
	{
		return this.items.get(this.getPersistence(container).getIndex());
	}
	
	protected String buildKey(Container container, BiFunction<List<String>, Integer, String> factory)
	{
		List<String> nodes = new ArrayList<String>(MenuButtonList.this.depth + 1);
		MenuButtonList menu = MenuButtonList.this;
		
		while(menu != null)
		{
			nodes.add(menu.getNode(container).getKey());
			menu = menu.getParent();
		}
		
		Collections.reverse(nodes);
		return factory.apply(nodes, MenuButtonList.this.depth);
	}
}
