package lugen.fastcar.Utils;

public class Log{
	public static final String TAG = "fastcar";
	private static boolean showLog = true;
	private String tag;
	
	public Log(String tag) {
		this.tag = tag;
	}
	
	private static String getTag(Object clazz) {
		String tag = null;
		if (clazz != null) {
			tag = clazz.getClass().getSimpleName();
		} else {
			tag = "besafe";
		}
		return tag;
	}

	public static void i(Object clazz, String msg) {
		if (showLog) {
			android.util.Log.i(getTag(clazz), msg != null ? msg : "Null");
		}
	}

	public static void d(Object clazz, String msg) {
		if (showLog) {
			android.util.Log.d(getTag(clazz), msg != null ? msg : "Null");
		}
	}

	public static void w(Object clazz, String msg) {
		if (showLog) {
			android.util.Log.w(getTag(clazz), msg != null ? msg : "Null");
		}
	}

	public static void e(Object clazz, String msg) {
		if (showLog) {
			android.util.Log.e(getTag(clazz), msg != null ? msg : "Null");
		}
	}

	public static void v(Object clazz, String msg) {
		if (showLog) {
			android.util.Log.v(getTag(clazz), msg != null ? msg : "Null");
		}
	}

	public static boolean isShowLog() {
		return showLog;
	}

	public static void setShowLog(boolean showLog) {
		Log.showLog = showLog;
	}

	public static void i(Object clazz, String msg, boolean newShowLog) {
		if (showLog) {
			android.util.Log.i(getTag(clazz), msg != null ? msg : "Null");
		}
		showLog = newShowLog;
	}

	public static void d(Object clazz, String msg, boolean newShowLog) {
		if (showLog) {
			android.util.Log.d(getTag(clazz), msg != null ? msg : "Null");
		}
		showLog = newShowLog;
	}

	public static void w(Object clazz, String msg, boolean newShowLog) {
		if (showLog) {
			android.util.Log.w(getTag(clazz), msg != null ? msg : "Null");
		}
		showLog = newShowLog;
	}

	public static void e(Object clazz, String msg, boolean newShowLog) {
		if (showLog) {
			android.util.Log.e(getTag(clazz), msg != null ? msg : "Null");
		}
		showLog = newShowLog;
	}

	public static void v(Object clazz, String msg, boolean newShowLog) {
		if (showLog) {
			Log.v(getTag(clazz), msg != null ? msg : "Null");
		}
		showLog = newShowLog;
	}
	
	public void i(String msg) {
		if (showLog) {
			android.util.Log.i(tag, msg != null ? msg : "Null");
		}
	}

	public void d(String msg) {
		if (showLog) {
			android.util.Log.d(tag, msg != null ? msg : "Null");
		}
	}

	public void w(String msg) {
		if (showLog) {
			android.util.Log.w(tag, msg != null ? msg : "Null");
		}
	}

	public void e(String msg) {
		if (showLog) {
			android.util.Log.e(tag, msg != null ? msg : "Null");
		}
	}

	public void v(String msg) {
		if (showLog) {
			Log.v(tag, msg != null ? msg : "Null");
		}
	}
	
	public  static void i(String tag,String msg) {
		if (showLog) {
			android.util.Log.i(tag, msg != null ? msg : "Null");
		}
	}

	public static void d(String tag,String msg) {
		if (showLog) {
			android.util.Log.d(tag, msg != null ? msg : "Null");
		}
	}

	public static void w(String tag,String msg) {
		if (showLog) {
			android.util.Log.w(tag, msg != null ? msg : "Null");
		}
	}

	public static void e(String tag,String msg) {
		if (showLog) {
			android.util.Log.e(tag, msg != null ? msg : "Null");
		}
	}
	public static void e(String tag,String msg,Exception e) {
		if (showLog) {
			android.util.Log.e(tag, msg != null ? msg : "Null" + (e!=null?e.toString():""));
		}
	}
	public static void e(String tag,String msg,Error e) {
		if (showLog) {
			android.util.Log.e(tag, msg != null ? msg : "Null" + (e!=null?e.toString():""));
		}
	}
	public static void e(String tag ,Exception e) {
		if (showLog) {
			android.util.Log.e(tag,   (e!=null?e.toString():"NULL exeption"));
		}
	}
}
