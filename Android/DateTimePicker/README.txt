This plugin has been updated to be used with more recent version of Phonegap: Cordova
Added feature: you can now chose the default value to be shown in the date / time picker

This plugin allows you to leave the Cordova webview and enter into the native android date and time picker.
Once the user has selected time or date, they will be sent back into the PhoneGap webview with selected value available.

How to use:

Usage:

window.plugins.datePickerPlugin.showDateOrTime(dataType,successCallback,errorCallback,timestamp);

dataType argument takes two value: 
	'date' : if you need the datePicker
	'time' : if you need the timePicker

For the date picker : Success returns an object with the parameters {day,month,year} filled with selected values.
For the time picker : Success returns an object with the parameters {hour,min} filled with selected values.

Example:

	document.querySelector("#mypickdatebutton").addEventListener("tap", function() {
	    window.plugins.datePickerPlugin.showDateOrTime(
			'date',
			function(r){
				document.getElementById("mydatetargetfield").value = r.day + "/" + r.month + "/" + r.year;
			},
			function(e){console.log(e);},
			(new Date()).getTime()
		);
    }, false);

	document.querySelector("#mypickdatebutton").addEventListener("tap", function() {
	    window.plugins.datePickerPlugin.showDateOrTime(
			'time',
			function(r){
				document.getElementById("mytimetargetfield").value = r.hour + "h" + r.min;
			},
			function(e){
				console.log(e);
			},
			(new Date()).getTime()
		);
    }, false);
    
   

For the current files to work, you'll need to create a package (folders) called com.ngapplication.plugin. 
You can change this to whatever you like, just update the datePickerPlugin.js and datePickerPlugin.java.

datePickerPlugin.js should go in the asset folder and should be referenced in your index.html file.


