package lugen.fastcar.service.task;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import lugen.fastcar.Utils.Const;
import lugen.fastcar.inteface.IDelegate;
import lugen.fastcar.object.LoginObject;
import lugen.fastcar.object.TaskResultObject;
import lugen.fastcar.service.task.abstracttask.AbstractPostMethodTask;
import lugen.fastcar.service.task.abstracttask.AbstractTask;

public class SignInTask extends AbstractPostMethodTask{
	public SignInTaskObject taskObject;
	public SignInTask(IDelegate<AbstractTask> delegate, String username, String password) {
		this.mDelegate = delegate;
		url = Const.URL_LOGIN + "?name=" + username + "&password=" + password;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		super.doInBackground(params);
		
		try {
			taskObject = new Gson().fromJson(strResult, SignInTaskObject.class);
		} catch (Exception e) {
			this.e = e;
		}
		return null;
	}
	
	public class SignInTaskObject extends TaskResultObject{
		@SerializedName("payload")
		public LoginObject payload;
	}
}
