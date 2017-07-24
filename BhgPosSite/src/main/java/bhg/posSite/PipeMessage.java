package bhg.posSite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

public class PipeMessage<T> {
	private static final String PATH = "/tmp/bhgMessage/";
	private String key;
	private String schema;
	private String sender;
	private String receiver;
	private IMessageBody msg;

	public void save() throws PipeException {
		File filePath = new File(PATH);
		if (!filePath.exists()) {
			filePath.mkdir();
		} else if (!filePath.isDirectory()) {
			filePath.delete();
			filePath.mkdir();
		}

		try (FileOutputStream out = new FileOutputStream(new File(PATH + this.getKey()));) {
			out.write((this.toString()).getBytes());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new PipeException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new PipeException();
		}
	}

	public PipeMessage(String schema, String sender, String receiver, IMessageBody msg) {
		this.schema = schema;
		this.key = new Date().getTime() + "_" + schema;
		this.sender = sender;
		this.receiver = receiver;
		this.msg = msg;
	}

	public static <T> PipeMessage<T> load(String msgKey) throws PipeException {
		// 从硬盘恢复消息
		try (FileReader input = new FileReader(PATH + msgKey); BufferedReader fr = new BufferedReader(input);) {
			String json = fr.readLine();
			Gson gson = new Gson();
			String schemaName = msgKey.split("_")[1];
			final Class<?> msgClass = Class.forName("bhg.posSite." + schemaName);
			Type resultType = new ParameterizedType() {
				@Override
				public Type[] getActualTypeArguments() {
					return new Type[] { msgClass };
				}

				@Override
				public Type getOwnerType() {
					return null;
				}

				@Override
				public Type getRawType() {
					return PipeMessage.class;
				}
			};
			@SuppressWarnings("unchecked")
			PipeMessage<T> newMessage = (PipeMessage<T>) gson.fromJson(json, resultType);

			return newMessage;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new PipeException();
		}
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public IMessageBody getMsg() {
		return msg;
	}

	public void setMsg(IMessageBody msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	public void delete() {
		new File(PATH + this.getKey()).delete();
	}

	public static List<PipeMessage<IMessageBody>> loadMessages() {
		File f = new File(PATH);
		File fa[] = f.listFiles();
		List<PipeMessage<IMessageBody>> list = new ArrayList<PipeMessage<IMessageBody>>();
		if (null == fa || fa.length == 0) {
			return list;
		}
		List<String> msgKeys = new ArrayList<String>();
		for (int i = 0; i < fa.length; i++) {
			msgKeys.add(fa[i].getName());
		}
		// 排序,按照生成的时间顺序
		Collections.sort(msgKeys);

		for (String s : msgKeys) {
			System.out.println(s);
			list.add(PipeMessage.load(s));
		}
		return list;
	}

	public static void main(String[] args) {
		// SchemaItem item = new SchemaItem();
		// item.setName("name");
		// item.setContext(2);
		// PipeMessage<SchemaItem> message = new
		// PipeMessage<SchemaItem>("SchemaItem", "abc001", "abc002", item);
		// message.save();
		// PipeMessage<SchemaItem> newMessage = PipeMessage.load(message.key);
		// System.out.println(newMessage);
		// System.out.println(newMessage.getMsg().getName());
		// System.out.println(newMessage.getMsg().getContext());
		// message.delete();

		List<PipeMessage<IMessageBody>> messages = loadMessages();
		for (PipeMessage<IMessageBody> m : messages) {
			if (m.getSchema().equals("SchemaItem")) {
				System.out.println(((SchemaItem) m.getMsg()).getContext());
				System.out.println(m.toString());
			}
		}
	}
}
