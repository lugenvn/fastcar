package lugen.fastcar.object;

import com.google.gson.annotations.SerializedName;

public class TaskResultObject {
	@SerializedName("success")
	public boolean success;
	@SerializedName("message")
	public String message;
}
