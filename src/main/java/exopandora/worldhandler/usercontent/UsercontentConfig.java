package exopandora.worldhandler.usercontent;

import java.nio.file.Path;

import javax.script.ScriptEngine;

import exopandora.worldhandler.usercontent.model.JsonUsercontent;

public class UsercontentConfig
{
	private final String id;
	private final JsonUsercontent content;
	private final String js;
	private final ScriptEngine engine;
	
	private UsercontentConfig(Builder builder)
	{
		this.id = builder.id;
		this.content = builder.content;
		this.js = builder.js;
		this.engine = builder.engine;
	}
	
	public String getId()
	{
		return this.id;
	}
	
	public JsonUsercontent getContent()
	{
		return this.content;
	}
	
	public String getJs()
	{
		return this.js;
	}
	
	public ScriptEngine getScriptEngine()
	{
		return this.engine;
	}
	
	public static class Builder
	{
		private final String id;
		private JsonUsercontent content;
		private String js;
		private ScriptEngine engine;
		
		public Builder(Path path)
		{
			String fileName = path.getFileName().toString();
			this.id = fileName.substring(0, fileName.length() - 3);
		}
		
		public Builder setContent(JsonUsercontent content)
		{
			this.content = content;
			return this;
		}
		
		public Builder setJs(String js)
		{
			this.js = js;
			return this;
		}
		
		public Builder setScriptEngine(ScriptEngine engine)
		{
			this.engine = engine;
			return this;
		}
		
		public UsercontentConfig build()
		{
			return new UsercontentConfig(this);
		}
	}
}
