package bhg.posSite;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import com.bhg.pipeServer.vo.SchemaVo;
import com.bhg.pipeServer.vo.SiteVo;

public abstract class AbstractPipeSite implements IPipeSite {
	private Map<String, ISiteReceiveListener> receiveSchemas;
	private Map<String, ISiteSendListener> sendSchemas;
	private String siteKey;
	private URI pipeServerUri = URI.create("http://localhost:8080/pipeServer/");
	private List<SchemaVo> schemas;
	private List<SiteVo> sites;

	// 数据发送队列
	LinkedBlockingQueue<PipeMessage<IMessageBody>> sendQueue;

	/**
	 * 发送消息
	 * 
	 * @param msg
	 *            消息
	 * @throws PipeException
	 * 
	 * @param site
	 *            站点
	 * @throws PipeException
	 */
	abstract void sendMsg(PipeMessage<IMessageBody> msg, String site) throws PipeException;

	/**
	 * 消息的消费确认
	 * 
	 * @param msg消息
	 * @throws PipeException
	 */
	abstract void ackMsg(PipeMessage<IMessageBody> msg) throws PipeException;

	/**
	 * 同步接收消息
	 * 
	 * @return 接收到的消息
	 * @throws PipeException
	 */
	abstract PipeMessage<IMessageBody> receiveMsg() throws PipeException;

	/**
	 * 连接消息接收器
	 */
	protected void connectReceiver() throws PipeException {
		if (null != this.receiveSchemas) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						PipeMessage<IMessageBody> msg = receiveMsg();
						String schema = msg.getSchema();
						IMessageBody msgBody = msg.getMsg();
						// 消息校验(如果schema不匹配,那么从服务器更新最新的元数据)
						ISiteReceiveListener listener = receiveSchemas.get(schema);
						// 交给客户处理接收到的数据
						listener.onDataReceived(new ArrayList<IMessageBody>() {
							private static final long serialVersionUID = 1L;
							{
								add(msgBody);
							}
						});
						// 确认消息
						ackMsg(msg);
					}
				}
			});
			t.start();
		}
	}

	private List<String> routing(String receiver, String schema) {
		List<String> sites = new ArrayList<String>();
		// TODO: 根据接收者和表信息计算接收信息的站点

		return sites;
	}

	/**
	 * 链接消息发送器
	 */
	protected void connectSender() throws PipeException {
		if (null != this.sendSchemas && !this.sendSchemas.isEmpty()) {
			if (this.sendQueue == null) {
				this.sendQueue = new LinkedBlockingQueue<PipeMessage<IMessageBody>>();
				// TODO:从日志中恢复未成功发送的文件．
				List<PipeMessage<IMessageBody>> messagesStored = PipeMessage.loadMessages();
				this.sendQueue.addAll(messagesStored);
				System.out.println("begin start ...");
				Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						while (true) {
							// TODO:连接服务器
							if (getStatus() == SiteStatus.ONLINE) {
								break;
							}
							try {
								Thread.sleep(1000);
								System.out.println("retry ...");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						while (true) {
							try {
								System.out.println("send msg ...");

								PipeMessage<IMessageBody> msg = sendQueue.take();
								String receiver = msg.getReceiver();
								String schema = msg.getSchema();
								List<String> sites = routing(receiver, schema);
								for (String site : sites) {
									sendMsg(msg, site);
								}
								// TODO: 从日志中删除
								msg.delete();
							} catch (PipeException e) {
								// TODO: 失败时退出Sender
								sendQueue.clear();
								sendQueue = null;
								break;
							} catch (InterruptedException e) {
							}
						}
					}
				});
				t.start();
				System.out.println("started ...");
			}
		}
	}

	@Override
	public boolean connect(Map<String, ISiteReceiveListener> receiveSchemas, Map<String, ISiteSendListener> sendSchemas)
			throws PipeException {
		this.receiveSchemas = receiveSchemas;
		this.sendSchemas = sendSchemas;
		connectSender();
		connectReceiver();
		return true;
	}

	@Override
	public boolean disconnect() throws PipeException {
		return false;
	}

	@Override
	public boolean init(String siteKey, URI pipeServerUri) throws PipeException {
		this.siteKey = siteKey;
		this.pipeServerUri = pipeServerUri;
		PipeServerCenter server = PipeServerCenter.connect(this.pipeServerUri.toString());
		this.schemas = server.getSchemas();
		this.sites = server.getSites();
		return true;
	}

	@Override
	public List<SchemaInfo> getSchemas() throws PipeException {
		System.out.println(this.schemas);
		return null;
	}

	@Override
	public String sendData(String schema, String siteDest, IMessageBody data) throws PipeException {
		// 生成message
		PipeMessage<IMessageBody> message = new PipeMessage<IMessageBody>(schema, this.siteKey, siteDest, data);
		// TODO:追加到日志中
		message.save();
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new PipeException();
		}
		sendQueue.add(message);
		return message.getKey();
	}
}
