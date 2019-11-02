package exopandora.worldhandler.gui.element.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

import javax.annotation.Nullable;

import exopandora.worldhandler.gui.button.GuiButtonBase;
import exopandora.worldhandler.gui.button.GuiButtonList;
import exopandora.worldhandler.gui.button.GuiButtonList.Persistence;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.element.Element;
import exopandora.worldhandler.gui.logic.ILogicClickList;
import exopandora.worldhandler.gui.logic.ILogicMapped;
import exopandora.worldhandler.helper.Node;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ElementMultiButtonList extends Element
{
	private final List<Node> items;
	private final ILogicClickList logic;
	private final ElementMultiButtonList parent;
	private final int depth;
	private final int maxDepth;
	
	public ElementMultiButtonList(int x, int y, List<Node> list, int maxDepth, ILogicClickList logic)
	{
		this(x, y, list, maxDepth, logic, null);
	}
	
	private ElementMultiButtonList(int x, int y, List<Node> list, int maxDepth, ILogicClickList logic, ElementMultiButtonList parent)
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
			public String translate(Node item)
			{
				return ElementMultiButtonList.this.logic.translate(ElementMultiButtonList.this.buildKey(container, ElementMultiButtonList.this.logic::buildTranslationKey), ElementMultiButtonList.this.getDepth());
			}
			
			@Override
			public String toTooltip(Node item)
			{
				return item.getKey();
			}
			
			@Override
			public String formatTooltip(Node item, int index, int max)
			{
				return ILogicMapped.super.formatTooltip(item, index, max);
			}
			
			@Override
			public void onClick(Node item)
			{
				ElementMultiButtonList.this.getPersistence(container, 1).setIndex(0);
				container.init();
			}
			
			@Override
			public String getId()
			{
				return ElementMultiButtonList.this.getId();
			}
		}));
		
		Node node = this.getNode(container);
		this.logic.onClick(this.buildKey(container, this.logic::buildEventKey), this.getDepth());
		
		if(node.getEntries() != null)
		{
			ElementMultiButtonList child = new ElementMultiButtonList(this.x, this.y + 24, node.getEntries(), this.maxDepth, this.logic, this);
			child.initButtons(container);
		}
		else
		{
			for(int x = this.getDepth() + 1; x < this.maxDepth; x++)
			{
				GuiButtonBase button = new GuiButtonBase(this.x, this.y + 24 * x, 114, 20, null, null);
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
	public void draw(int mouseX, int mouseY, float partialTicks)
	{
		
	}
	
	protected int getDepth()
	{
		return this.depth;
	}
	
	@Nullable
	protected ElementMultiButtonList getParent()
	{
		return this.parent;
	}
	
	protected String getId()
	{
		return this.getId(0);
	}
	
	protected String getId(int offset)
	{
		return String.format("%s%d", ElementMultiButtonList.this.logic.getId(), ElementMultiButtonList.this.depth + offset);
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
		List<String> nodes = new ArrayList<String>(ElementMultiButtonList.this.depth + 1);
		ElementMultiButtonList element = ElementMultiButtonList.this;
		
		while(element != null)
		{
			nodes.add(element.getNode(container).getKey());
			element = element.getParent();
		}
		
		Collections.reverse(nodes);
		return factory.apply(nodes, ElementMultiButtonList.this.depth);
	}
}
