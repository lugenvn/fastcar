package lugen.fastcar.service.task;

import android.text.TextUtils;
import lugen.fastcar.Utils.Const;
import lugen.fastcar.inteface.IDelegate;
import lugen.fastcar.object.LoginObject;
import lugen.fastcar.service.task.abstracttask.AbstractPutMethodTask;
import lugen.fastcar.service.task.abstracttask.AbstractTask;

public class UpdateInfoTask extends AbstractPutMethodTask{
	public UpdateInfoTask(IDelegate<AbstractTask> delegate, LoginObject object) {
		mDelegate = delegate;
		url = Const.URL_UPDATE_INFOR; 
		initParam(object);
	}

	private void initParam(LoginObject object) {
		if (object.getUserId() > 0) {
			addParam("id", String.valueOf(object.getUserId()));
			if (!TextUtils.isEmpty(object.getDeviceId())) {
				addParam("deviceID", object.getDeviceId());
			}
			if (!TextUtils.isEmpty(object.getUserName())) {
				addParam("name", object.getUserName());
			}
			if (!TextUtils.isEmpty(object.getPassword())) {
				addParam("password", object.getPassword());
			}
			if (object.getLat() >= -180 && object.getLat() <= 180) {
				addParam("latitude", String.valueOf(object.getLat()));
			}
			if (object.getLon() >= 0 && object.getLon() <= 180) {
				addParam("longitude", String.valueOf(object.getLon()));
			}
		}
	}
	
	
}
