package lugen.fastcar.service.task.abstracttask;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import lugen.fastcar.Utils.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

public class AbstractPutMethodTask extends AbstractTask {
	protected String url;
	protected JSONObject json = new JSONObject();
	
	protected void addParam(String name, String value) {
		try {
			json.put(name, value);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private String exec() {

		try {
			Log.d(Log.TAG, "load url : "+ this.url);
			BufferedReader br = null;
			String output = null;
			StringBuilder builder = new StringBuilder();
			// --This code works for updating a record from the feed--
			HttpPut httpPut = new HttpPut(url);

			StringEntity entity = new StringEntity(json.toString());
			entity.setContentType("application/json;charset=UTF-8");// text/plain;charset=UTF-8
			entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json;charset=UTF-8"));
			httpPut.setEntity(entity);

			// Send request to WCF service
			DefaultHttpClient httpClient = new DefaultHttpClient();

			HttpResponse response = httpClient.execute(httpPut);
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

		return null;
	}

	
	@Override
	protected Void doInBackground(Void... params) {
		exec();
		return null;
	}
}
