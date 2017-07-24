package bhg.posSite;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * １．站点初始化:设置站点的Key,以及服务器地址． ２．服务连接
 * 
 *
 */
public interface IPipeSite {
	enum SiteStatus {
		/**
		 * 在线
		 */
		ONLINE,
		/**
		 * 离线
		 */
		OFFLINE,
	}

	/**
	 * 数据发送回调接口
	 *
	 */
	public interface ISiteSendListener {

		/**
		 * 数据成功发送
		 * 
		 * @param msgId
		 *            消息Id
		 */
		void onDataSendSucess(String msgId);

		/**
		 * 数据发送失败
		 * 
		 * @param msgId
		 *            消息Id
		 * @param errorMessage
		 *            错误信息
		 */
		void onDataSendFailure(String msgId, String errorMessage);
	}

	/**
	 * 有数据到达接口
	 *
	 */
	public interface ISiteReceiveListener {
		/**
		 * 有数据到达
		 * 
		 * @param datas
		 *            数据集合
		 */
		void onDataReceived(final List<IMessageBody> datas);

	}

	/**
	 * 连接站点
	 * 
	 * @param receiveSchemas
	 *            需要接收的数据表,对于未指定的数据表，接收到的数据将被站点抛弃
	 * @param sendSchemas
	 *            需要发送的数据表
	 * @return
	 * @throws PipeException
	 */
	boolean connect(Map<String, ISiteReceiveListener> receiveSchemas, Map<String, ISiteSendListener> sendSchemas)
			throws PipeException;

	/**
	 * 站点断开连接
	 * 
	 * @return
	 * @throws PipeException
	 */
	boolean disconnect() throws PipeException;

	/**
	 * 初始化站点
	 * 
	 * @param siteName
	 *            站点名
	 * @param pipServerUri
	 *            站点服务地址
	 * @return
	 */
	boolean init(String siteKey, URI pipeServerUri) throws PipeException;

	/**
	 * 获取元数据列表
	 * 
	 * @return
	 */
	List<SchemaInfo> getSchemas() throws PipeException;

	/**
	 * 发送数据，Site负责生成消息的唯一Id,规则：至少包含发送方站点ID和接口调用时间，同时保证唯一性．
	 * 
	 * @param schema
	 *            目的数据表
	 * @param siteDest
	 *            目的站点
	 * @param data
	 *            要发送的数据
	 * @return 消息Id
	 * 
	 * @throws PipeException
	 */
	String sendData(String schema, String siteDest, IMessageBody data) throws PipeException;

	/**
	 * 获取站点状态
	 * 
	 * @return
	 */
	SiteStatus getStatus();
}
