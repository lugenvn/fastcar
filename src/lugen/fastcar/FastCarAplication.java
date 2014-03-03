package lugen.fastcar;

import android.app.Application;
import android.content.Context;

public class FastCarAplication extends Application {
	private static Context context;

	synchronized public static Context getContext() {
		return context;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		context = getBaseContext();
	}
}
