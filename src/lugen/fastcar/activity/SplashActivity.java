package lugen.fastcar.activity;

import lugen.fastcar.R;
import lugen.fastcar.object.LoginObject;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

@SuppressLint("HandlerLeak")
public class SplashActivity  extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);
		
		handler.sendMessageDelayed(new Message(), 2000);
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			LoginObject.get_instance().loadFromCache(getApplicationContext());
			LoginObject object = LoginObject.get_instance();
			Intent intent;
			if (object != null && object.getUserId() >= 0) {
				intent = new Intent(SplashActivity.this, MainActivity.class);
			} else {
				intent = new Intent(SplashActivity.this, SignInActivity.class);
			}
			
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);

			finish();
			
			super.handleMessage(msg);
		}
	};
}
