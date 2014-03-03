package lugen.fastcar.service.task;

import java.util.List;

import android.location.Address;
import android.location.Geocoder;

import lugen.fastcar.FastCarAplication;
import lugen.fastcar.Utils.Log;
import lugen.fastcar.inteface.IDelegate;
import lugen.fastcar.service.task.abstracttask.AbstractTask;

public class GetLocationFromAddress extends AbstractTask{
	String address;
	public Address location;
	public GetLocationFromAddress(IDelegate<AbstractTask> delegate, String address) {
		mDelegate = delegate;
		this.address = address;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		try {
			List<Address> addresses;
			addresses = new Geocoder(FastCarAplication.getContext()).getFromLocationName(address, 5);
			for (Address address : addresses) {
				if (address != null) {
					location = address;
					return null;
				} else {
					Log.d(Log.TAG, "null pos");
				}
			}
			
		} catch (Exception e) {
			this.e = e;	
		}
		return super.doInBackground(params);
	}
}
