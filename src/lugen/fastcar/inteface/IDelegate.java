package lugen.fastcar.inteface;

import lugen.fastcar.service.task.abstracttask.AbstractTask;

public interface IDelegate<T extends AbstractTask>{
	void onGetStart(T task);

	void onGetSuccess(T task);

	void onGetFailure(T task, Exception exception);
}
