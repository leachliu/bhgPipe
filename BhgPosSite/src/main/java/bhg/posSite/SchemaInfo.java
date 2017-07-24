package bhg.posSite;

import java.util.List;

/**
 * 表信息
 * 
 * @author llq
 *
 */
public class SchemaInfo {
	String name;

	List<Field> fields;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

}
