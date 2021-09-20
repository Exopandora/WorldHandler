package exopandora.worldhandler.usercontent;

import javax.annotation.Nullable;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import exopandora.worldhandler.WorldHandler;

public class ScriptEngineAdapter
{
	private final ScriptEngine engine;
	
	public ScriptEngineAdapter(ScriptEngine engine)
	{
		this.engine = engine;
	}
	
	@Nullable
	public Object invokeFunction(String function)
	{
		return this.invokeFunction(function, null);
	}
	
	@Nullable
	public Object invokeFunction(String function, Object value)
	{
		if(function != null && !function.isEmpty())
		{
			try
			{
				if(value != null)
				{
					return ((Invocable) this.engine).invokeFunction(function, value);
				}
				else
				{
					return ((Invocable) this.engine).invokeFunction(function);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			WorldHandler.LOGGER.warn("No function specified");
		}
		
		return null;
	}
	
	public void addObject(String key, Object instance)
	{
		this.engine.put(key, instance);
	}
	
	public void eval(String js) throws ScriptException
	{
		if(js != null)
		{
			this.engine.eval(js);
		}
	}
}