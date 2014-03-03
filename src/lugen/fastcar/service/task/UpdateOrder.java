package lugen.fastcar.service.task;

import lugen.fastcar.Utils.Const;
import lugen.fastcar.inteface.IDelegate;
import lugen.fastcar.service.task.abstracttask.AbstractPutMethodTask;
import lugen.fastcar.service.task.abstracttask.AbstractTask;

public class UpdateOrder extends AbstractPutMethodTask{
	public UpdateOrder(IDelegate<AbstractTask> delegate, int id, double latitude, double longitude) {
		mDelegate = delegate;
		url = Const.URL_UPDATE_ORDER + id;
		addParam("id", String.valueOf(id));
		addParam("user_latitude", String.valueOf(latitude));
		addParam("user_longitude", String.valueOf(longitude));
	}
}
