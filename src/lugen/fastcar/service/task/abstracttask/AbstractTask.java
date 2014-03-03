package lugen.fastcar.service.task.abstracttask;

import lugen.fastcar.inteface.IDelegate;
import android.os.AsyncTask;

public abstract class AbstractTask extends AsyncTask<Void, Void, Void> {
	protected String strResult = "";
	protected IDelegate<AbstractTask> mDelegate;
	protected Exception e;

	public AbstractTask() {
		super();
	}

	@Override
	protected void onPreExecute() {
		if (mDelegate != null)
			mDelegate.onGetStart(this);
	}

	@Override
	protected Void doInBackground(Void... params) {
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		if (mDelegate != null) {
			if (e != null) {
				mDelegate.onGetFailure(this, e);
			} else {
				mDelegate.onGetSuccess(this);
			}
		}
	}

	public String getResult() {
		return strResult;
	}
}
