package exopandora.worldhandler.gui.content.element.impl;

import java.util.List;

import javax.annotation.Nullable;

import exopandora.worldhandler.gui.button.EnumTooltip;
import exopandora.worldhandler.gui.button.GuiButtonList;
import exopandora.worldhandler.gui.button.GuiButtonWorldHandler;
import exopandora.worldhandler.gui.button.logic.IListButtonLogic;
import exopandora.worldhandler.gui.button.persistence.ButtonValue;
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
	private final int[] buttonIds;
	private final List<Node> list;
	private final ILogicClickList logic;
	private final Content content;
	private final ElementClickList parent;
	private final int depth;
	
	private GuiButtonList button;
	private ElementClickList child;
	
	public ElementClickList(int x, int y, List<Node> list, int[] buttonIds, Content content, ILogicClickList logic)
	{
		this(x, y, list, buttonIds, content, logic, null);
	}
	
	private ElementClickList(int x, int y, List<Node> list, int[] buttonIds, Content content, ILogicClickList logic, ElementClickList parent)
	{
		super(x, y);
		this.list = list;
		this.buttonIds = buttonIds;
		this.logic = logic;
		this.content = content;
		this.parent = parent;
		this.depth = this.parent != null ? this.parent.depth + 1 : 1;
	}
	
	@Override
	public void initGui(Container container)
	{
		
	}
	
	@Override
	public void initButtons(Container container)
	{
		container.add(this.button = new GuiButtonList(this.getButtonId(), this.x, this.y, 114, 20, EnumTooltip.TOP_RIGHT, this.content, new IListButtonLogic<Node>()
		{
			@Override
			public void actionPerformed(Container container, GuiButton button, ButtonValue<Node> values)
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
			public String getDisplayString(ButtonValue<Node> values)
			{
				return logic.translate(getKeys());
			}
			
			@Override
			public String getTooltipString(ButtonValue<Node> values)
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
			this.child = new ElementClickList(this.x, this.y + 24, node.getEntries(), this.buttonIds, this.content, this.logic, this);
			this.child.initButtons(container);
		}
		else if(this.depth < this.buttonIds.length)
		{
			GuiButtonWorldHandler button = new GuiButtonWorldHandler(this.getButtonId(), this.x, this.y + 24, 114, 20, null);
			button.enabled = false;
			container.add(button);
		}
	}
	
	@Nullable
	private ButtonValue<Node> getValues()
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
		if(button.id == this.getButtonId())
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
	
	@Override
	public void draw()
	{
		
	}
	
	private int getButtonId()
	{
		return this.buttonIds[this.depth - 1];
	}
	
	private String[] getKeys()
	{
		return this.getKeys(new String[this.depth]);
	}
	
	private String[] getKeys(String[] keys)
	{
		if(keys != null && this.depth <= keys.length)
		{
			keys[this.depth - 1] = this.getValues().getObject().getKey();
		}
		
		return this.parent != null ? this.parent.getKeys(keys) : keys;
	}
}
