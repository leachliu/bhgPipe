package bhg.posSite;

public interface IMessagePipe {
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
	void sendMsg(PipeMessage<IMessageBody> msg, String site) throws PipeException;

	/**
	 * 消息的消费确认
	 * 
	 * @param msg消息
	 * @throws PipeException
	 */
	void ackMsg(PipeMessage<IMessageBody> msg) throws PipeException;

	/**
	 * 同步接收消息
	 * 
	 * @return 接收到的消息
	 * @throws PipeException
	 */
	PipeMessage<IMessageBody> receiveMsg() throws PipeException;
}
