# Usercontent Reference # 

The World Handler provides the ability to be extended with custom tabs configured with json files.

# Setup #

The World Handler only loads usercontent files from `../.minecraft/config/worldhandler/usercontent/`.  
To add a custom GUI a new folder is required with a json file inside it named exactly the same as the folder.  
That name indicates the id of the usercontent and may only consist of the characters `0-9` `a-z` `-_:`.  
Additionally a javascript file can be created inside the folder with exactly the same name as the folder.  

Example setup for a custom GUI with id `example`:

`../.minecraft/config/worldhandler/usercontent/example/example.json`  
`../.minecraft/config/worldhandler/usercontent/example/example.js`  

An example GUI can be found [here](../run/config/worldhandler/usercontent/)

# Index #

Content: A single GUI of the World Handler i.e. the main page  
Category: A collection of contents that are grouped together and are displayed in tabs next to each other  
Elements: A structure of buttons simplifying the creation of larger menus

## General Structure ##

```
{
	"model": {
		"commands": [
			#See commands
		]
	},
	"gui": {
		"title": "", #The title of the gui
		"tab": {
			"title": "", #The title of the tab
			"category": "", #The category where the tab should be clickable in (See reference/category-ids)
			"category_index": 0, #The index of the tab (left: 0, right: last tab index)
			"active_content": "" #The id of the active content (See reference/content-ids)
		},
		"buttons": [
			#See buttons
		],
		"elements": [
			#See elements
		],
		"texts": [
			#See texts
		]
	}
}
```

## Commands ##

All commands that should be modifiable, executable or visible by the command syntax need to be modeled

```
{
	"name": "", #The root of the command
	"syntax": [
		{
			"name": "", #Placeholder text for the argument
			"type": "", #Type of the argument (See table below for available types)
			"required": true #Whethter or not this argument is required in order to complete the command
		}
	],
	"visible": {
		#See widget-states
	}
}
```

Argument types          | Comment
----------------------- | -----------------------------------------------------------------------------------------------------------
byte                    | Default java byte
short                   | Default java short
int                     | Default java integer
float                   | Default java float
double                  | Default java double
long                    | Default java long
boolean                 | Default java boolean
string                  | Default java string
greedy_string           | String that can be read across whitespaces. (usually in quotes)
resource_location       | Minecraft resource location. Example:` namespace:resource`
item_resource_location  | Resource location with attached nbt. Example: `namespace:resource{"nbt":"example"}`
block_resource_location | Item resource location with attached block tag. Example: `namespace:resource[state=example]`
nbt                     | Minecraft data structure. Example: `{"nbt":"example"}`
coordinate_int          | An integer that can either be absolute or relative in a global (prefix: `~`) or local (prefix: `^`) context
coordinate_double       | A double that can either be absolute or relative in a global (prefix: `~`) or local (prefix: `^`) context
target_selector         | Filter for entities. This implementation only supports `@e` with arguments. Example: `@e[type=player]`
player                  | Username of the player that will be automatically set by the system

## Buttons ##

```
{
	"text": "", #Optional
	"type": "", #See table below for available types
	"dimensions": {
		"x": 0,
		"y": 0,
		"width": 114,
		"height": 20
	},
	"action": {
		#See action
	},
	"attributes": {
		"id": "",
		"item": "", #Resource location
		"icon": "", #See reference/icon-ids for available ids
		"visible": {
			#See widget-states
		},
		"enabled": {
			#See widget-states
		}
	}
}
```

Button type | Description                                   | Actions                                | Required attributes | Optional Attributes
------------|---------------------------------------------- | -------------------------------------- | ------------------- | -------------------
button      | Simple button                                 | open, set, run, back, back_to_game, js | -                   | tooltip
textfield   | Simple textfield                              | set, js                                | id                  | -
item_button | Button with an item instead of text           | open, set, run, back, back_to_game, js | item                | -
icon_button | Button with an icon instead of text           | open, set, run, back, back_to_game, js | icon                | tooltip
list_button | Button with the option to cycle through items | set, js                                | items               | -
slider      | Simple slider                                 | set, js                                | id, min, max        | start

## Elements ##

```
"type": "", #See table below for available types
"dimensions": {
	"x": 0,
	"y": 0,
	"width": 114,
	"height": 20
},
"attributes": {
	"id": "",
	"length": 1,
	"items": [
		{
			"id": "",
			"translation": "" #Translation key or string value
		}
	]
}
```

Element type | Description                                                                                                  | Required attributes | Optional Attributes
------------ | ------------------------------------------------------------------------------------------------------------ | ------------------- | -------------------
page_list    | Creates column of buttons with `length` options to choose from and two buttons for navigating left and right | id, length, items   | -

## Text ##

```
{
	"text": "",
	"x": 0,
	"y": 0,
	"color": 2039583 #Color of the text (red << 16 + green << 8 + blue)
}
```

## Action ##

```
"action": {
	"type": "", #See table below for available types
	"attributes": {
		"function": "", #Name of the js function
		"command": 0, #Index of the command inside the json file
		"index": 0, #Argument index
		"value": ""
	}
}
```

Action type  | Description                                                                                            | Required attributes | Optional Attributes
------------ | ------------------------------------------------------------------------------------------------------ | ------------------- | -------------------
open         | Opens the content associated with `value`                                                              | value               | -
set          | Sets the argument `index` of command `command` to `value` or the value provided by the system          | index, command      | value
run          | Runs the command from `value`                                                                          | value               | -
back         | Goes back to the previous content                                                                      | -                   | -
back_to_game | Closes the GUI                                                                                         | -                   | -
js           | Invokes the javascript function `function` (with argument `value`)                                     | function            | value

## Widget states ##

There are two widget states: `visible` and `enabled`  
This structure is optional.

```
"visible": {
	"type": "",
	"bool": true
	"function": "" #Name of the js function
}
```

State type | Description                                                       | Required attributes | Optional Attributes
---------- | ----------------------------------------------------------------- | ------------------- | -------------------
bool       | The state will be determined by the value of the `bool` attribute | bool                | -
js         | Invokes the javascript function `function` and uses the result    | function            | -

## JavaScript API ##

The JavaScript interpreter comes with an instance of `UsercontentAPI` available for reference with `api`

Example: `api.addChatMessage("Hello World")`

Function                                                         | Description
---------------------------------------------------------------- | -------------------------------------------------------------------
String getValue(String id)                                       | returns the value stored by a button or element with id `id`
String getCommandArgument(int command, int index)                | returns the argument at index `index` of command `command`
void setCommandArgument(int command, int index, String object)   | sets the argument at index `index` of command `command` to `object`
void addChatMessage(Object object)                               | prints the object to the ingame chat

### ActionHelper ###

Example:
```
var ActionHelper = Java.type('exopandora.worldhandler.helper.ActionHelper');
ActionHelper.displayGui();
```

Function                  | Description
------------------------- | ----------------------------------------------------------------------------
void backToGame()         | Closes the GUI
void back(String id)      | Tries to open the back content of content with id `id`
void open(String id)      | Tries to open the content with id `id`
void timeDawn()           | Sets the time to dawn
void timeNoon()           | Sets the time to noon
void timeSunset()         | Sets the time to sunset
void timeMidnight()       | Sets the time to midnight
void weatherClear()       | Sets the weather to clear
void weatherRain()        | Sets the weather to rain
void weatherThunder()     | Sets the weather to thunder
void difficultyPeaceful() | Sets the difficulty to peaceful
void difficultyEasy()     | Sets the difficulty to easy
void difficultyNormal()   | Sets the difficulty to normal
void difficultyHard()     | Sets the difficulty to hard
void gamemodeSurvival()   | Sets the gamemode to survival
void gamemodeCreative()   | Sets the gamemode to creative
void gamemodeAdventure()  | Sets the gamemode to adventure
void gamemodeSpectator()  | Sets the gamemode to spectator
void displayGui()         | Tries to open the World Handler GUI (same as pressing the activation button)

## Reference ##

Content ids           | Category ids | Icon ids
--------------------- | ------------ | -------------------
main                  | main         | weather_sun
containers            | entities     | weather_rain
multiplayer           | items        | weather_storm
summon                | blocks       | difficulty_peaceful
custom_item           | world        | difficulty_easy
enchantment           | player       | difficulty_normal
edit_blocks           | scoreboard   | difficulty_hard
sign_editor           |              | time_dawn
note_editor           |              | time_noon
world                 |              | time_sunset
gamerules             |              | time_midnight
recipes               |              | gamemode_survival
player                |              | gamemode_creative
experience            |              | gamemode_adventure
advancements          |              | gamemode_spectator
scoreboard_objectives |              | butcher
scoreboard_teams      |              | potion
scoreboard_players    |              | achievements
change_world          |              | home
continue              |              | settings
potions               |              | reload
butcher               |              |
butcher_settings      |              |
settings              |              |

