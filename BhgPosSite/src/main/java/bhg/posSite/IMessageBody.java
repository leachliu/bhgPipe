package bhg.posSite;

import com.google.gson.Gson;

public interface IMessageBody {
	default String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

}
