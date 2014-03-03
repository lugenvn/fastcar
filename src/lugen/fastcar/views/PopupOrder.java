package lugen.fastcar.views;

import lugen.fastcar.R;
import lugen.fastcar.Utils.Log;
import lugen.fastcar.Utils.Utils;
import lugen.fastcar.inteface.IDelegate;
import lugen.fastcar.object.LoginObject;
import lugen.fastcar.object.Order;
import lugen.fastcar.service.task.GetAddressByLatLonTask;
import lugen.fastcar.service.task.GetLocationFromAddress;
import lugen.fastcar.service.task.OrderTask;
import lugen.fastcar.service.task.abstracttask.AbstractTask;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Location;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class PopupOrder extends PopupWindow implements OnClickListener, IDelegate<AbstractTask> {
	Context mContext;
	View layout;
	Location from, to;
	TextView orderButton;
	EditText edtFrom, edtTo;
	private ProgressDialog dialog;

	public PopupOrder(Context context) {
		super(context);
		setFocusable(true);
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout = inflater.inflate(R.layout.popup_order, null);
		setContentView(layout);
		init();
		setWindowLayoutMode(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		setAnimationStyle(android.R.anim.fade_in);
		showAtLocation(layout, Gravity.CENTER, 0, 0);

		new GetAddressByLatLonTask(this, mContext, LoginObject.get_instance().getLat(), LoginObject
				.get_instance().getLon()).execute();
	}

	private void init() {
		edtFrom = (EditText) layout.findViewById(R.id.edit_from);
		edtTo = (EditText) layout.findViewById(R.id.edit_to);

		orderButton = (TextView) layout.findViewById(R.id.btn_order);
		orderButton.setOnClickListener(this);
	}

	String fromString;
	String toString;

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_order:
			fromString = edtFrom.getText().toString();
			toString = edtTo.getText().toString();
			if (TextUtils.isEmpty(fromString)) {
				Toast.makeText(mContext, R.string.from_string_null, Toast.LENGTH_SHORT).show();
			} else if (TextUtils.isEmpty(toString)) {
				Toast.makeText(mContext, R.string.to_string_null, Toast.LENGTH_SHORT).show();
			} else {
				new GetLocationFromAddress(this, toString).execute();
			}
			break;
		}
	}

	@Override
	public void onGetStart(AbstractTask task) {
		dialog = ProgressDialog.show(mContext, "", "loading...");

	}

	@Override
	public void onGetSuccess(AbstractTask task) {
		if (task instanceof GetAddressByLatLonTask) {
			String address = task.getResult();
			edtFrom.setText(address);
		} else if (task instanceof GetLocationFromAddress) {
			Address to = ((GetLocationFromAddress) task).location;

			double dis = Utils.calculateDistance(LoginObject.get_instance().getLat(), LoginObject
					.get_instance().getLon(), to.getLatitude(), to.getLongitude());
			Log.d(Log.TAG, "distance : " + dis);

			Order order = new Order();
			order.lat = LoginObject.get_instance().getLat();
			order.lon = LoginObject.get_instance().getLon();
			order.id = LoginObject.get_instance().getUserId();
			order.type = "5";
			order.allocation = "4";
			order.price = String.valueOf(dis*10000);
			new OrderTask(this, order).execute();
		} else if (task instanceof OrderTask) {
			String s = task.getResult();
			Log.d(Log.TAG, "order return : " + s);
		}
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	@Override
	public void onGetFailure(AbstractTask task, Exception exception) {
		exception.printStackTrace();
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

}
