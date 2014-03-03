package lugen.fastcar.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.content.Context;

public class Utils {
	public static final String TAG = "FastCar";
	
	public static boolean writeObject2File(Context context, Serializable obj,
			String fileName) {
		try {
			File cacheDir = context.getCacheDir();
			File fileSave = new File(cacheDir, fileName);
			FileOutputStream out = new FileOutputStream(fileSave);
			ObjectOutputStream os = new ObjectOutputStream(out);
			os.writeObject(obj);
			os.flush();
			os.close();
			return true;
		} catch (Exception e) {
			Log.e(Log.TAG, e);
			return false;
		}
	}
	public static Object loadObjectFromFile(Context context, String fileName) {

		String loadFile = context.getCacheDir() + "/" + fileName;
		Log.d("besafe", "Load user setting from: " + loadFile);
		File file = new File(loadFile);
		if (file.exists()) {
			try {
				FileInputStream fin = new FileInputStream(file);
				ObjectInputStream os = new ObjectInputStream(fin);
				Object obj = os.readObject();
				os.close();
				return obj;
			} catch (Exception e) {
				Log.e(Log.TAG, e);
				return null;
			}
		} else {
			return null;
		}

	}
	

	private static final int earthRadius = 6371;
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2)
    {
    	double dLat = Math.toRadians(lat2 - lat1);
    	double dLon = Math.toRadians(lon2 - lon1);
    	double a =
                (Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
                        * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2));
    	double c = (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)));
    	double d = earthRadius * c;
        return d;
    }
}
