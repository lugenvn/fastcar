package lugen.fastcar.service.task;

import lugen.fastcar.Utils.Const;
import lugen.fastcar.inteface.IDelegate;
import lugen.fastcar.service.task.abstracttask.AbstractGetMethodTask;
import lugen.fastcar.service.task.abstracttask.AbstractTask;

public class GetUserInforTask extends AbstractGetMethodTask{
	public GetUserInforTask(IDelegate<AbstractTask> delegate, int id){
		mDelegate = delegate;
		url = Const.URL_GET_USER_INFOR + id;
	}
}
