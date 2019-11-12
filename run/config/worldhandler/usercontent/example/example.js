var ActionHelper = Java.type('exopandora.worldhandler.util.ActionHelper');

var visible = true;
var enabled = true;

function toggleVisibility() {
	visible = !visible;
}

function toggleEnabled() {
	enabled = !enabled;
}

function isVisible() {
	return visible;
}

function isEnabled() {
	return enabled;
}

function selected(arg) {
	api.addChatMessage(arg);
}

function updateText(arg) {
	api.setCommandArgument(0, 4, arg);
}

function openContent(arg) {
	ActionHelper.open(arg);
}

function addChatMessage(arg) {
	api.addChatMessage(arg);
}