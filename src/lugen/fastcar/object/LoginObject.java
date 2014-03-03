package lugen.fastcar.object;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.annotations.SerializedName;

import lugen.fastcar.FastCarAplication;
import lugen.fastcar.Utils.Utils;
import android.content.Context;
import android.text.TextUtils;

public class LoginObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8000100522009258372L;

	@SerializedName("deviceID")
	private String deviceId;
	@SerializedName("password")
	private String password;
	@SerializedName("latitude")
	private double lat;
	@SerializedName("longitude")
	private double lon;
	@SerializedName("name")
	private String userName;
	@SerializedName("id")
	private int userId;

	private static LoginObject _instance;

	public static LoginObject get_instance() {
		if (_instance == null) {
			_instance = new LoginObject();
		}
		return _instance;
	}

	public static void set_instance(LoginObject _instance) {
		LoginObject._instance = _instance;
	}

	public LoginObject() {
		deviceId = "";
		password = "";
		lat = 100;
		lon = 200;
		userName = "";
		userId = -1;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void saveToCache() {
		Utils.writeObject2File(FastCarAplication.getContext(), this, "data.dat");
	}

	public void loadFromCache(Context context) {
		Object obj = Utils.loadObjectFromFile(context, "data.dat");
		if (obj != null && obj instanceof LoginObject) {
			_instance = (LoginObject) obj;
		}
	}

	public String toJSON() {
		JSONObject object = new JSONObject();
		try {
			if (!TextUtils.isEmpty(userName))
				object.put("name", userName);
			if (userId > -1)
				object.put("id", userId);
			if (!TextUtils.isEmpty(password))
				object.put("password", password);
			if (lat >= -90 && lat <= 90)
				object.put("latitude", lat);
			if (lon >= -180 && lon <= 180)
				object.put("longitude", lon);
			if (!TextUtils.isEmpty(deviceId))
				object.put("deviceID", deviceId);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return object.toString();
	}
}
