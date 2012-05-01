package org.apache.cordova.plugin;

import java.util.Calendar;
 
import org.apache.cordova.api.CordovaInterface;
import org.apache.cordova.api.PluginResult.Status;
import org.apache.cordova.api.Plugin;
import org.apache.cordova.api.PluginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;

/**
 * @author ng4e 
 * 
 */
public class DatePickerPlugin extends Plugin {

	private static final String ACTION_DATE = "date";
	private static final String ACTION_TIME = "time";

	@Override
	public PluginResult execute(final String action, final JSONArray data,
			final String callBackId) {

		// Generating a calendar based on the timestamp sent through javascript
		Calendar c = Calendar.getInstance();
		try {
			Long ts = data.getLong(0);
			c.setTimeInMillis(ts);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		Log.d("DatePickerPlugin", "Plugin Called");
		PluginResult result = null;

		if (ACTION_DATE.equalsIgnoreCase(action)) {
			Log.d("DatePickerPluginListener execute", ACTION_DATE);
			// Sending the generated calendar as parameter to get a default value in the date picker
			this.showDatePicker(callBackId, c);
			final PluginResult r = new PluginResult(
					PluginResult.Status.NO_RESULT);
			r.setKeepCallback(true);
			return r;

		} else if (ACTION_TIME.equalsIgnoreCase(action)) {
			Log.d("DatePickerPluginListener execute", ACTION_TIME);
			// Sending the generated calendar as parameter to get a default value in the time picker
			this.showTimePicker(callBackId, c);
			final PluginResult r = new PluginResult(
					PluginResult.Status.NO_RESULT);
			r.setKeepCallback(true);
			return r;

		} else {
			result = new PluginResult(Status.INVALID_ACTION);
			Log.d("DatePickerPlugin", "Invalid action : " + action + " passed");
		}

		return result;
	}

	public synchronized void showTimePicker(final String callBackId, final Calendar c) {
		final DatePickerPlugin datePickerPlugin = this;
		final CordovaInterface currentCtx = ctx;

		// Getting default values
		final int h = c.get(Calendar.HOUR_OF_DAY);
		final int m = c.get(Calendar.MINUTE);
		final Runnable runnable = new Runnable() {

			public void run() {
				final TimePickerDialog tpd = new TimePickerDialog((Context) currentCtx,
						new OnTimeSetListener() {

							public void onTimeSet(final TimePicker view,
									final int hourOfDay, final int minute) {
								final JSONObject userChoice = new JSONObject();
								try {
									userChoice.put("hour", hourOfDay);
									userChoice.put("min", minute);
								} catch (final JSONException jsonEx) {
									Log.e("showDatePicker",
											"Got JSON Exception "
													+ jsonEx.getMessage());
									datePickerPlugin.error(new PluginResult(
											Status.JSON_EXCEPTION), callBackId);
								}
								datePickerPlugin.success(new PluginResult(
										PluginResult.Status.OK, userChoice),
										callBackId);

							}
						}, h, m, true);

				tpd.show();
			}
		};
		ctx.runOnUiThread(runnable);

	}

	public synchronized void showDatePicker(final String callBackId, final Calendar c) {

		final DatePickerPlugin datePickerPlugin = this;
		final CordovaInterface currentCtx = ctx;
		// Getting default values
		final int mYear = c.get(Calendar.YEAR);
		final int mMonth = c.get(Calendar.MONTH);
		final int mDay = c.get(Calendar.DAY_OF_MONTH);

		final Runnable runnable = new Runnable() {

			public void run() {
				final DatePickerDialog dpd = new DatePickerDialog((Context) currentCtx,
						new OnDateSetListener() {

							public void onDateSet(final DatePicker view,
									final int year, final int monthOfYear,
									final int dayOfMonth) {

								final JSONObject userChoice = new JSONObject();

								try {
									userChoice.put("year", year);
									userChoice.put("month", monthOfYear);
									userChoice.put("day", dayOfMonth);
								} catch (final JSONException jsonEx) {
									Log.e("showDatePicker",
											"Got JSON Exception "
													+ jsonEx.getMessage());
									datePickerPlugin.error(new PluginResult(
											Status.JSON_EXCEPTION), callBackId);
								}

								datePickerPlugin.success(new PluginResult(
										PluginResult.Status.OK, userChoice),
										callBackId);

							}
						}, mYear, mMonth, mDay);

				dpd.show();
			}
		};
		ctx.runOnUiThread(runnable);
	}

}
