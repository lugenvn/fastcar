package lugen.fastcar.service.task;

import lugen.fastcar.Utils.Const;
import lugen.fastcar.inteface.IDelegate;
import lugen.fastcar.service.task.abstracttask.AbstractPostMethodTask;
import lugen.fastcar.service.task.abstracttask.AbstractTask;

public class SignUpTask extends AbstractPostMethodTask{
	
	public SignUpTask(IDelegate<AbstractTask> delegate, String username, String password){
		this.mDelegate = delegate;
		url = Const.URL_CREATE + "name=" + username + "&password=" + password;
	}
}
