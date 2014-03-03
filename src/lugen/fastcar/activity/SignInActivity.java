package lugen.fastcar.activity;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import lugen.fastcar.R;
import lugen.fastcar.Utils.Log;
import lugen.fastcar.inteface.IDelegate;
import lugen.fastcar.object.LoginObject;
import lugen.fastcar.service.task.SignInTask;
import lugen.fastcar.service.task.UpdateInfoTask;
import lugen.fastcar.service.task.SignInTask.SignInTaskObject;
import lugen.fastcar.service.task.abstracttask.AbstractTask;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignInActivity extends Activity implements OnClickListener, IDelegate<AbstractTask> {
	public static final String EXTRA_MESSAGE = "message";
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	/**
	 * Substitute you own sender ID here. This is the project number you got
	 * from the API Console, as described in "Getting Started."
	 */
	String SENDER_ID = "489510355548";

	GoogleCloudMessaging gcm;
	AtomicInteger msgId = new AtomicInteger();
	SharedPreferences prefs;
	Context context;

	String regid;

	
	private EditText edtUsername, edtPassword;
	private Button btnSignin;
	private TextView tvSignup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin_screen);
		initView();
		

		context = getApplicationContext();

		// Check device for Play Services APK. If check succeeds, proceed with
		// GCM registration.
		if (checkPlayServices()) {

			Log.i(Log.TAG, "0");
			gcm = GoogleCloudMessaging.getInstance(this);
			regid = getRegistrationId(context);

			if (regid.isEmpty()) {
				Log.i(Log.TAG, "1");
				registerInBackground();
			} else {
				Log.i(Log.TAG, "2");
				LoginObject.get_instance().setDeviceId(regid);
				LoginObject.get_instance().saveToCache();
				new UpdateInfoTask(this, LoginObject.get_instance()).execute();
			}
		} else {
			Log.i(Log.TAG, "No valid Google Play Services APK found.");
		}
	}

	private void initView() {
		edtUsername = (EditText) findViewById(R.id.edt_username);
		edtPassword = (EditText) findViewById(R.id.edt_password);
		btnSignin = (Button) findViewById(R.id.btn_signin);
		btnSignin.setOnClickListener(this);
		tvSignup = (TextView) findViewById(R.id.txt_signup);
		tvSignup.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btn_signin:
			String username = edtUsername.getText().toString();
			String password = edtPassword.getText().toString();

			if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
				Toast.makeText(getApplicationContext(),
						getApplicationContext().getString(R.string.not_fill_enought), Toast.LENGTH_LONG)
						.show();
				return;
			} else {
				new SignInTask(this, username, password).execute();
			}
			break;

		case R.id.txt_signup:
			Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
			startActivity(intent);
			break;
		}

	}

	ProgressDialog progressDialog;

	@Override
	public void onGetStart(AbstractTask task) {
		progressDialog = ProgressDialog.show(this, "", getApplicationContext()
				.getString(R.string.loading));
	}

	@Override
	public void onGetSuccess(AbstractTask task) {
		if (task instanceof SignInTask) {
			SignInTaskObject taskObject = ((SignInTask)task).taskObject;
			if (taskObject != null && taskObject.success) {
				LoginObject object = taskObject.payload;
				LoginObject.set_instance(object);
				object.saveToCache();
				if (!TextUtils.isEmpty(object.getDeviceId())) {
					object.setDeviceId(getRegistrationId(getApplicationContext()));
					new UpdateInfoTask(this, object);
				} else {
					dismissDialog();
					goToMain();
				}
			} else {
				dismissDialog();
				if (taskObject != null)
					Log.e(Log.TAG, taskObject.message);
				else 
					Log.e(Log.TAG, "null task object");
			}
		} else if (task instanceof UpdateInfoTask) {
			dismissDialog();
			goToMain();
		}
	}

	private void goToMain(){
		Intent intent = new Intent(SignInActivity.this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	@Override
	public void onGetFailure(AbstractTask task, Exception exception) {
		exception.printStackTrace();
		dismissDialog();

	}
	
	private void dismissDialog(){
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}
	

	/**
	 * Check the device to make sure it has the Google Play Services APK. If it
	 * doesn't, display a dialog that allows users to download the APK from the
	 * Google Play Store or enable it in the device's system settings.
	 */
	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
						PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Log.i(Log.TAG, "This device is not supported.");
				finish();
			}
			return false;
		}
		return true;
	}

	/**
	 * Gets the current registration ID for application on GCM service.
	 * <p>
	 * If result is empty, the app needs to register.
	 * 
	 * @return registration ID, or empty string if there is no existing
	 *         registration ID.
	 */
	private String getRegistrationId(Context context) {
		final SharedPreferences prefs = getGCMPreferences(context);
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if (registrationId.isEmpty()) {
			Log.i(Log.TAG, "Registration not found.");
			return "";
		}
		// Check if app was updated; if so, it must clear the registration ID
		// since the existing regID is not guaranteed to work with the new
		// app version.
		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			Log.i(Log.TAG, "App version changed.");
			return "";
		}
		return registrationId;
	}

	// ...
	/**
	 * @return Application's {@code SharedPreferences}.
	 */
	private SharedPreferences getGCMPreferences(Context context) {
		// This sample app persists the registration ID in shared preferences,
		// but
		// how you store the regID in your app is up to you.
		return getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
	}

	/**
	 * @return Application's version code from the {@code PackageManager}.
	 */
	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	/**
	 * Registers the application with GCM servers asynchronously.
	 * <p>
	 * Stores the registration ID and app versionCode in the application's
	 * shared preferences.
	 */
	private void registerInBackground() {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(context);
					}
					regid = gcm.register(SENDER_ID);
					msg = "Device registered, registration ID=" + regid;

					// You should send the registration ID to your server over
					// HTTP,
					// so it can use GCM/HTTP or CCS to send messages to your
					// app.
					// The request to your server should be authenticated if
					// your app
					// is using accounts.
					
					
//					sendRegistrationIdToBackend();

					// For this demo: we don't need to send it because the
					// device
					// will send upstream messages to a server that echo back
					// the
					// message using the 'from' address in the message.

					// Persist the regID - no need to register again.
					storeRegistrationId(context, regid);
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
					// If there is an error, don't just keep trying to register.
					// Require the user to click a button again, or perform
					// exponential back-off.
				}
				return msg;
			}

			@Override
			protected void onPostExecute(String msg) {
				Log.d(Log.TAG, msg);
			}
		}.execute(null, null, null);
	}

	/**
	 * Sends the registration ID to your server over HTTP, so it can use
	 * GCM/HTTP or CCS to send messages to your app. Not needed for this demo
	 * since the device sends upstream messages to a server that echoes back the
	 * message using the 'from' address in the message.
	 */
	@SuppressWarnings("unused")
	private void sendRegistrationIdToBackend() {

		LoginObject.get_instance().setDeviceId(regid);
		LoginObject.get_instance().saveToCache();
		new UpdateInfoTask(this, LoginObject.get_instance()).execute();
	}

	/**
	 * Stores the registration ID and app versionCode in the application's
	 * {@code SharedPreferences}.
	 * 
	 * @param context
	 *            application's context.
	 * @param regId
	 *            registration ID
	 */
	private void storeRegistrationId(Context context, String regId) {
		final SharedPreferences prefs = getGCMPreferences(context);
		int appVersion = getAppVersion(context);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(PROPERTY_REG_ID, regId);
		editor.putInt(PROPERTY_APP_VERSION, appVersion);
		editor.commit();
	}
}
