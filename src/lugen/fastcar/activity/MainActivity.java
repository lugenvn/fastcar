package lugen.fastcar.activity;

import lugen.fastcar.R;
import lugen.fastcar.object.LoginObject;
import lugen.fastcar.views.PopupOrder;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnMyLocationButtonClickListener,
		LocationListener, ConnectionCallbacks, OnConnectionFailedListener {
	
	private GoogleMap map;
	private TextView controlButton;
	private LocationClient mLocationClient;
	ProgressDialog dialog;
	boolean init = false;
	private OnClickListener orderListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// LoginObject.get_instance().setLat(mLocationClient.getLastLocation().getLatitude());
			// LoginObject.get_instance().setLon(mLocationClient.getLastLocation().getLongitude());
			if (controlButton.getText().toString()
					.equals(MainActivity.this.getResources().getString(R.string.order)))
				new PopupOrder(MainActivity.this);
		}
	};
	// These settings are the same as the settings for the map. They will in
	// fact give you updates
	// at the maximal rates currently possible.
	private static final LocationRequest REQUEST = LocationRequest.create().setInterval(5000) // 5
																								// seconds
			.setFastestInterval(16) // 16ms = 60fps
			.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// h.postDelayed(r2, 2000);
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		if (map != null) {
			map.setMyLocationEnabled(true);
			map.setOnMyLocationButtonClickListener(this);
		}
		controlButton = (TextView) findViewById(R.id.control_button);
		controlButton.setOnClickListener(orderListener);

		dialog = ProgressDialog.show(this, "", "loading...");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (mLocationClient == null) {
			mLocationClient = new LocationClient(getApplicationContext(), this, // ConnectionCallbacks
					this); // OnConnectionFailedListener
		}
		mLocationClient.connect();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onMyLocationButtonClick() {
		// Toast.makeText(this, "location = " +
		// mLocationClient.getLastLocation(), Toast.LENGTH_SHORT)
		// .show();
		return false;
	}

	@Override
	public void onLocationChanged(Location location) {
		dismissDialog();
		double lat = location.getLatitude();
		double lon = location.getLongitude();
		CameraUpdate myLocation = CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 15);
		map.animateCamera(myLocation);
		LoginObject.get_instance().setLat(lat);
		LoginObject.get_instance().setLon(lon);

	}
	
	private void dismissDialog(){
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	@Override
	public void onConnected(Bundle arg0) {
		mLocationClient.requestLocationUpdates(REQUEST, this);
	}

	@Override
	public void onDisconnected() {

	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {

	}

}
