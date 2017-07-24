package bhg.posSite;

public class Field {
	public static final String INT = "int";
	public static final String LONG = "long";
	public static final String FLOAT = "float";
	public static final String DOUBLE = "double";
	public static final String DATETIME = "dateTime";
	public static final String DATE = "date";
	public static final String STRING = "string";

	String name;
	String type;

	/**
	 * 名称
	 * @return
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**数据类型
	 * @return
	 */
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
