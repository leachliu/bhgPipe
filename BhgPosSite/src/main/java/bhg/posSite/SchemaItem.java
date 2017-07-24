package bhg.posSite;

public class SchemaItem implements IMessageBody{
	String name;
	int context;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getContext() {
		return context;
	}

	public void setContext(int context) {
		this.context = context;
	}

}
