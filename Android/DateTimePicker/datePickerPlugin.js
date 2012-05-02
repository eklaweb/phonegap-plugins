/**
 *  
 * @return Object literal singleton instance of DatePicker
 */
var DatePicker = function() {
	
};

DatePicker.prototype.showDateOrTime = function(action,successCallback, failureCallback, args) {
	// If a timestamp is passed, we use it as a default value for the date / time picker. Else, we use current date.
	var args = typeof arguments[3] !== null ? arguments[3] : (new Date()).getTime();

	return cordova.exec(
	successCallback,     // Success callback from the plugin
	failureCallback,     // Error callback from the plugin
	'DatePickerPlugin',  // Tell PhoneGap to run "DatePickerPlugin" Plugin
	action,              // Tell plugin, which action we want to perform
	[args]);        		 // Passing list of args to the plugin: ajout du args
};

cordova.addConstructor(function() {
  //Register the javascript plugin with Cordova
  cordova.addPlugin('datePickerPlugin', new DatePicker());
});
