package lugen.fastcar.service.task;

import lugen.fastcar.Utils.Const;
import lugen.fastcar.inteface.IDelegate;
import lugen.fastcar.object.Order;
import lugen.fastcar.service.task.abstracttask.AbstractPostMethodTask;
import lugen.fastcar.service.task.abstracttask.AbstractTask;

public class OrderTask extends AbstractPostMethodTask {
	
	
	public OrderTask(IDelegate<AbstractTask> delegate, Order order) {
		mDelegate = delegate;
		url = Const.URL_ORDER + order.id + "/orders"; 
		addParam("price", order.price);
		addParam("allocation", order.allocation);
		addParam("type", order.type);
		addParam("user_latitude", String.valueOf(order.lat));
		addParam("user_longitude", String.valueOf(order.lon));
	}

}
