package lugen.fastcar.service.task.abstracttask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import lugen.fastcar.Utils.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public abstract class AbstractPostMethodTask extends AbstractTask {
	private final int DEFAULT_TIMEOUT_CONECTION = 60000;
	private final int DEFAULT_TIMEOUT_SOCKET = 60000;
	private int timeoutConnection = DEFAULT_TIMEOUT_CONECTION;
	private int timeoutSocket = DEFAULT_TIMEOUT_SOCKET;

	protected String url;
	protected List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	
	public AbstractPostMethodTask() {
		super();
	}


	protected void addParam(String name, String value) {
		NameValuePair object = new BasicNameValuePair(name, value);
		nameValuePairs.add(object);
	}

	private void exec(){
		HttpPost httppost = new HttpPost(url);
		Log.d(Log.TAG, "send request : " + this.url);
		StringBuilder builder = new StringBuilder();
		try {
			BufferedReader br = null;
			String output = null;
			HttpParams httpParameters = new BasicHttpParams();
			// Set the timeout in milliseconds until a connection is
			// established.
			// The default value is zero, that means the timeout is not used.
			HttpConnectionParams.setConnectionTimeout(httpParameters,
					timeoutConnection);
			// Set the default socket timeout (SO_TIMEOUT)
			// in milliseconds which is the timeout for waiting for data.
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
			DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

			HttpResponse response = httpClient.execute(httppost);
			br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));
			while ((output = br.readLine()) != null) {
				builder.append(output);
			}
			httpClient.getConnectionManager().shutdown();
			strResult = builder.toString();
			Log.d(Log.TAG, "return from "+ this.url + " : " + strResult);
		} catch (Exception e) {
			this.e = e;
		}
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		exec();
		return null;
	}
}
