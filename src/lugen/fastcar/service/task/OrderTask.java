package lugen.fastcar.service.task;

import lugen.fastcar.Utils.Const;
import lugen.fastcar.inteface.IDelegate;
import lugen.fastcar.object.Order;
import lugen.fastcar.service.task.abstracttask.AbstractPostMethodTask;
import lugen.fastcar.service.task.abstracttask.AbstractTask;

public class OrderTask extends AbstractPostMethodTask {
	
	
	public OrderTask(IDelegate<AbstractTask> delegate, Order order) {
		mDelegate = delegate;
		url = Const.URL_ORDER + order.id + "/orders?price=" + order.price + "&allocation=" + order.allocation
				+ "&type=" + order.type + "&user_latitude=" + order.lat + "&user_longitude" + order.lon;
	}

}
