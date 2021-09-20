package exopandora.worldhandler.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

import javax.annotation.Nullable;

public class Node
{
	private final String key;
	private List<Node> entries;
	
	public Node()
	{
		this("root", null);
	}
	
	public Node(String key)
	{
		this(key, null);
	}
	
	public Node(String key, List<Node> entries)
	{
		this.key = key;
		this.entries = entries;
	}
	
	public String getKey()
	{
		return this.key;
	}
	
	@Nullable
	public List<Node> getEntries()
	{
		return this.entries;
	}
	
	public void addEntries(List<Node> entries)
	{
		this.validateEntries();
		this.entries.addAll(entries);
	}
	
	public Node addNode(Node node)
	{
		this.validateEntries();
		this.entries.add(node);
		
		return node;
	}
	
	private void validateEntries()
	{
		if(this.entries == null)
		{
			this.entries = new ArrayList<Node>();
		}
	}
	
	public Node addNode(String key)
	{
		return this.addNode(new Node(key));
	}
	
	public Node addNode(String key, List<Node> entries)
	{
		return this.addNode(new Node(key, entries));
	}
	
	public Node getNode(String key)
	{
		if(this.entries != null)
		{
			for(int x = 0; x < this.entries.size(); x++)
			{
				Node node = this.entries.get(x);
				
				if(node.getKey().equals(key))
				{
					return node;
				}
			}
		}
		
		return null;
	}
	
	public void sort()
	{
		this.sort(this, (a, b) -> a.getKey().compareTo(b.getKey()));
	}
	
	public void sort(Comparator<Node> comparator)
	{
		this.sort(this, comparator);
	}
	
	private void sort(Node root, Comparator<Node> comparator)
	{
		if(root.getEntries() != null)
		{
			for(Node node : root.getEntries())
			{
				this.sort(node, comparator);
			}
			
			root.getEntries().sort(comparator);
		}
	}
	
	public Node insertNode(String[] path)
	{
		return this.insertNode(path, this);
	}
	
	private Node insertNode(String[] path, Node root)
	{
		return this.insertNode(0, path, root);
	}
	
	private Node insertNode(int index, String[] path, Node root)
	{
		if(index == path.length)
		{
			return root;
		}
		
		Node node = new Node(path[index]);
		
		if(root.getEntries() != null && root.getEntries().contains(node))
		{
			for(Node element : root.getEntries())
			{
				if(element.equals(node))
				{
					return this.insertNode(index + 1, path, element);
				}
			}
		}
		
		root.addNode(node);
		return this.insertNode(index + 1, path, node);
	}
	
	public void mergeItems()
	{
		this.mergeItems(null, this, null);
	}
	
	public void mergeItems(BiPredicate<String, String> predicate)
	{
		this.mergeItems(null, this, predicate);
	}
	
	private void mergeItems(Node root, Node child, BiPredicate<String, String> predicate)
	{
		if(child != null && child.getEntries() != null)
		{
			if(root == null)
			{
				for(Node node : child.getEntries())
				{
					this.mergeItems(child, node, predicate);
				}
			}
			else
			{
				if(child.getEntries() != null && !child.getEntries().isEmpty())
				{
					boolean flag = true;
					
					for(Node node : child.getEntries())
					{
						if(node.getEntries() != null && !node.getEntries().isEmpty())
						{
							this.mergeItems(child, node, predicate);
							flag = false;
							break;
						}
						else if(predicate != null)
						{
							flag = flag && predicate.test(child.getKey(), node.getKey());
						}
					}
					
					if(flag)
					{
						this.merge(root, child.getKey(), (parent, kid) -> parent + ":" + kid);
					}
				}
			}
		}
	}
	
	public void merge(String key, BiFunction<String, String, String> merger)
	{
		this.merge(this, key, merger);
	}
	
	private void merge(Node root, String key, BiFunction<String, String, String> merger)
	{
		if(root.getEntries() != null)
		{
			Node node = root.getNode(key);
			
			if(node != null)
			{
				root.getEntries().remove(node);
				
				for(Node entry : node.getEntries())
				{
					root.addNode(new Node(merger.apply(node.getKey(), entry.getKey()), entry.getEntries()));
				}
			}
			
			for(Node entry : root.getEntries())
			{
				this.merge(entry, key, merger);
			}
		}
	}
	
	public void print()
	{
		this.print("", this);
	}
	
	private void print(String offset, Node node)
	{
		System.out.println(offset + node.getKey());
		
		if(node.getEntries() != null)
		{
			for(Node entry : node.getEntries())
			{
				this.print(offset + "  ", entry);
			}
		}
	}
	
	public void writeFile(FileOutputStream out) throws IOException
	{
		this.writeFile("", this, out);
	}
	
	protected void writeFile(String offset, Node node, FileOutputStream out) throws IOException
	{
		out.write((offset + node.getKey() + "\n").getBytes());
		
		if(node.getEntries() != null)
		{
			for(Node entry : node.getEntries())
			{
				this.writeFile(offset + "  ", entry, out);
			}
		}
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof Node)
		{
			Node node = (Node) obj;
			
			return this.key.equals(node.getKey());
		}
		
		return false;
	}
	
	@Override
	public String toString()
	{
		return this.getKey();
	}
}
