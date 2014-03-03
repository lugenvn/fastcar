package lugen.fastcar.service.task.abstracttask;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public abstract class AbstractGetMethodTask extends AbstractTask {
	private final int DEFAULT_TIMEOUT_CONECTION = 60000;
	private final int DEFAULT_TIMEOUT_SOCKET = 60000;
	protected String url;
	
	@Override
	protected Void doInBackground(Void... params) {
		try {
			downloadUrl(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doInBackground(params);
	}
	
	private String downloadUrl(String myurl) throws IOException {
	    InputStream is = null;
	    // Only display the first 500 characters of the retrieved
	    // web page content.
	        
	    try {
	        URL url = new URL(myurl);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setReadTimeout(DEFAULT_TIMEOUT_CONECTION);
	        conn.setConnectTimeout(DEFAULT_TIMEOUT_SOCKET);
	        conn.setRequestMethod("GET");
	        conn.setDoInput(true);
	        // Starts the query
	        conn.connect();
	        is = conn.getInputStream();

	        // Convert the InputStream into a string
	        String contentAsString = readIt(is);

	        Log.d("paktor", "The response is: " + contentAsString);
	        return contentAsString;
	        
	    // Makes sure that the InputStream is closed after the app is
	    // finished using it.
	    } finally {
	        if (is != null) {
	            is.close();
	        } 
	    }
	}
	
	public String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {
	    Reader reader = null;
	    reader = new InputStreamReader(stream, "UTF-8");        
	    char[] buffer = new char[1];
	    StringBuilder builder = new StringBuilder();
	    while (reader.read(buffer) != -1) {
	    	builder.append(buffer);
	    } 
	    return builder.toString();
	}
}
