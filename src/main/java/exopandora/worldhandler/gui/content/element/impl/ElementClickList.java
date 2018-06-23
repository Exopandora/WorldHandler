package exopandora.worldhandler.gui.content.element.impl;

import java.util.List;

import javax.annotation.Nullable;

import exopandora.worldhandler.gui.button.EnumTooltip;
import exopandora.worldhandler.gui.button.GuiButtonList;
import exopandora.worldhandler.gui.button.GuiButtonWorldHandler;
import exopandora.worldhandler.gui.button.logic.IListButtonLogic;
import exopandora.worldhandler.gui.button.persistence.ButtonValues;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.element.Element;
import exopandora.worldhandler.gui.content.element.logic.ILogicClickList;
import exopandora.worldhandler.helper.Node;
import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ElementClickList extends Element
{
	private final int buttonId;
	private final List<Node> list;
	private final ILogicClickList logic;
	private final Content content;
	private final ElementClickList parent;
	private final int depth;
	private final int maxDepth;
	
	private GuiButtonList button;
	private ElementClickList child;
	
	public ElementClickList(int x, int y, List<Node> list, int buttonId, int maxDepth, Content content, ILogicClickList logic)
	{
		this(x, y, list, buttonId, maxDepth, content, logic, null);
	}
	
	private ElementClickList(int x, int y, List<Node> list, int buttonId, int maxDepth, Content content, ILogicClickList logic, ElementClickList parent)
	{
		super(x, y);
		this.list = list;
		this.buttonId = buttonId;
		this.logic = logic;
		this.content = content;
		this.parent = parent;
		this.maxDepth = maxDepth;
		this.depth = this.parent != null ? this.parent.depth + 1 : 1;
	}
	
	@Override
	public void initGui(Container container)
	{
		
	}
	
	@Override
	public void initButtons(Container container)
	{
		container.add(this.button = new GuiButtonList(this.buttonId, this.x, this.y, 114, 20, EnumTooltip.TOP_RIGHT, this.content, new IListButtonLogic<Node>()
		{
			@Override
			public void actionPerformed(Container container, GuiButton button, ButtonValues<Node> values)
			{
				content.getPersistence(logic.getId() + (depth + 1)).setIndex(0);
				container.initButtons();
			}
			
			@Override
			public int getMax()
			{
				return list.size();
			}
			
			@Override
			public Node getObject(int index)
			{
				return list.get(index);
			}
			
			@Override
			public String getDisplayString(ButtonValues<Node> values)
			{
				return logic.translate(getKeys());
			}
			
			@Override
			public String getTooltipString(ButtonValues<Node> values)
			{
				if(values != null && values.getObject() != null)
				{
					return values.getObject().getKey() + " (" + (values.getIndex() + 1) + "/" + this.getMax() + ")";
				}
				
				return null;
			}
			
			@Override
			public String getId()
			{
				return logic.getId() + depth;
			}
		}));
		
		Node node = this.getValues().getObject();
		this.logic.consumeKey(this.getKeys());
		
		if(node.getEntries() != null)
		{
			this.child = new ElementClickList(this.x, this.y + 24, node.getEntries(), this.buttonId + 1, this.maxDepth, this.content, this.logic, this);
			this.child.initButtons(container);
		}
		else if(this.depth < this.maxDepth)
		{
			GuiButtonWorldHandler button = new GuiButtonWorldHandler(this.buttonId + 1, this.x, this.y + 24, 114, 20, null);
			button.enabled = false;
			container.add(button);
		}
	}
	
	private String[] getKeys()
	{
		return this.getKeys(new String[this.depth]);
	}
	
	private String[] getKeys(String[] keys)
	{
		keys[this.depth - 1] = this.getValues().getObject().getKey();
		return this.parent != null ? this.parent.getKeys(keys) : keys;
	}
	
	@Nullable
	private ButtonValues<Node> getValues()
	{
		if(this.button != null)
		{
			return this.content.<Node>getPersistence(this.button.getLogic().getId());
		}
		
		return null;
	}
	
	@Override
	public boolean actionPerformed(Container container, GuiButton button)
	{
		if(button.id == this.buttonId)
		{
			this.button.actionPerformed(container, button);
			return true;
		}
		else if(this.child != null)
		{
			return this.child.actionPerformed(container, button);
		}
		
		return false;
	}
}
