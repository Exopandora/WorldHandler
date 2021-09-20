package exopandora.worldhandler.usercontent;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;

import org.apache.commons.io.IOUtils;
import org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import exopandora.worldhandler.WorldHandler;
import exopandora.worldhandler.builder.types.ArgumentType;
import exopandora.worldhandler.gui.widget.button.EnumIcon;
import exopandora.worldhandler.usercontent.model.Action;
import exopandora.worldhandler.usercontent.model.BooleanExpression;
import exopandora.worldhandler.usercontent.model.JsonMenu;
import exopandora.worldhandler.usercontent.model.JsonUsercontent;
import exopandora.worldhandler.usercontent.model.JsonWidget;
import net.minecraft.resources.ResourceLocation;

public class UsercontentLoader
{
	public static final List<UsercontentConfig> CONFIGS = new ArrayList<UsercontentConfig>();
	
	public static void load(Path path)
	{
		try
		{
			UsercontentLoader.load0(path);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void load0(Path path) throws IOException
	{
		if(Files.notExists(path) || !Files.isReadable(path))
		{
			throw new IOException("Path is not accessible");
		}
		
		final Gson gson = new GsonBuilder()
				.registerTypeAdapter(ArgumentType.class, new EnumTypeAdapter<ArgumentType>(ArgumentType.class))
				.registerTypeAdapter(EnumIcon.class, new EnumTypeAdapter<EnumIcon>(EnumIcon.class))
				.registerTypeAdapter(BooleanExpression.Type.class, new EnumTypeAdapter<BooleanExpression.Type>(BooleanExpression.Type.class))
				.registerTypeAdapter(JsonWidget.Type.class, new EnumTypeAdapter<JsonWidget.Type>(JsonWidget.Type.class))
				.registerTypeAdapter(Action.Type.class, new EnumTypeAdapter<Action.Type>(Action.Type.class))
				.registerTypeAdapter(JsonMenu.Type.class, new EnumTypeAdapter<JsonMenu.Type>(JsonMenu.Type.class))
				.create();
		final List<Path> folders = Files.list(path)
				.filter(Files::isDirectory)
				.filter(Files::isReadable)
				.filter(UsercontentLoader::isValidPathName)
				.collect(Collectors.toList());
		
		for(Path folder : folders)
		{
			Optional<Path> json = UsercontentLoader.locateFile(folder, "json");
			Optional<Path> js = UsercontentLoader.locateFile(folder, "js");
			
			if(json.isPresent())
			{
				UsercontentConfig.Builder builder = new UsercontentConfig.Builder(js.get());
				String usercontent = UsercontentLoader.readFile(json.get());
				
				try
				{
					JsonUsercontent content = gson.fromJson(usercontent, JsonUsercontent.class);
					content.validate();
					builder.setContent(content);
					
					if(js.isPresent())
					{
						builder.setScriptEngine(UsercontentLoader.buildScriptEngine());
						builder.setJs(UsercontentLoader.readFile(js.get()));
					}
					
					UsercontentLoader.CONFIGS.add(builder.build());
				}
				catch(JsonSyntaxException | IllegalStateException e)
				{
					WorldHandler.LOGGER.error("Error loading usercontent " + json.get().toAbsolutePath());
					WorldHandler.LOGGER.throwing(e);
				}
			}
		}
	}
	
	private static final List<String> ALLOWED_CLASSES = Arrays.asList
	(
		"exopandora.worldhandler.util.ActionHelper"
	);
	
	private static ScriptEngine buildScriptEngine()
	{
		final NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
		final ScriptEngine engine = factory.getScriptEngine(s -> ALLOWED_CLASSES.stream().anyMatch(s::equals));
		final ScriptContext context = engine.getContext();
		
		context.removeAttribute("load", context.getAttributesScope("load"));
		context.removeAttribute("quit", context.getAttributesScope("quit"));
		context.removeAttribute("loadWithNewGlobal", context.getAttributesScope("loadWithNewGlobal"));
		context.removeAttribute("exit", context.getAttributesScope("exit"));
		
		return engine;
	}
	
	private static Optional<Path> locateFile(Path path, String fileExtension)
	{
		Path json = path.resolve(path.getFileName().toString() + "." + fileExtension);
		
		if(Files.exists(json) && Files.isRegularFile(json) && Files.isReadable(json))
		{
			return Optional.of(json);
		}
		
		return Optional.empty();
	}
	
	private static String readFile(Path path)
	{
		try
		{
			return IOUtils.toString(path.toUri(), Charset.defaultCharset());
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static boolean isValidPathName(Path path)
	{
		String name = path.getFileName().toString();
		boolean valid = ResourceLocation.isValidResourceLocation(name);
		
		if(!valid)
		{
			WorldHandler.LOGGER.error("Invalid usercontent folder name: " + name);
		}
		
		return valid;
	}
	
	public static class EnumTypeAdapter<T extends Enum<T>> extends TypeAdapter<T>
	{
		private final Class<T> klass;
		
		public EnumTypeAdapter(Class<T> klass)
		{
			this.klass = klass;
		}
		
		@Override
		public void write(JsonWriter writer, T value) throws IOException
		{
			writer.value(value.name().toLowerCase());
		}
		
		@Override
		public T read(JsonReader reader) throws IOException
		{
			return Enum.valueOf(this.klass, reader.nextString().toUpperCase());
		}
	}
}
