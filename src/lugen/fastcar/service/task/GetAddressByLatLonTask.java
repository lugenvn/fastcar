package lugen.fastcar.service.task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import lugen.fastcar.Utils.Log;
import lugen.fastcar.inteface.IDelegate;
import lugen.fastcar.service.task.abstracttask.AbstractTask;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

public class GetAddressByLatLonTask extends AbstractTask {
	private Context mContext;
	private double lat, lon;

	public GetAddressByLatLonTask(IDelegate<AbstractTask> delegate, Context context, double lat,
			double lon) {
		mDelegate = delegate;
		mContext = context;
		this.lat = lat;
		this.lon = lon;
	}
	@Override
	protected Void doInBackground(Void... params) {
		try {

			Log.d(Log.TAG, "task lat lon : " + lat + ", " + lon);
			Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
			List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);
			StringBuilder sb = new StringBuilder();
			if (addresses.size() > 1) {
				Address address = addresses.get(1);
				
				sb.append(address.getAddressLine(0));
			} else {
				if (addresses.size() > 0) {
					Address address = addresses.get(0);

					sb.append(address.getAddressLine(0));
				}
			}

			strResult = sb.toString();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doInBackground(params);
	}
	
	
}
