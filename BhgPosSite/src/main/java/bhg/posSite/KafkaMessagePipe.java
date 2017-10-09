package bhg.posSite;

import java.util.Map;
import java.util.Properties;

import com.bhg.pipeServer.vo.SiteConfigVo;

import bhg.posSite.Sender.BhgKafkaProducer;

/**
 * kafka消息通道
 * 
 * @author llq
 *
 */
public class KafkaMessagePipe implements IMessagePipe {
	SiteConfigVo config;
	// 发送消息的通道
	BhgKafkaProducer producer;

	public KafkaMessagePipe() {

	}

	/**
	 * 根据从服务器获取到的kafka配置参数初始化消息通道
	 * 
	 * @param config
	 */
	public KafkaMessagePipe(SiteConfigVo config) {
		this.config = config;
		Properties properties = new Properties();
		for (Map.Entry<String, String> entry : config.getProducerProperties().entrySet()) {
			properties.put(entry.getKey(), entry.getValue());
		}
		this.producer = new BhgKafkaProducer(properties);
	}

	@Override
	public void sendMsg(PipeMessage<IMessageBody> msg, String site) throws PipeException {
		producer.sendMessage(msg.getSchema(), msg.getMsg().toJson());
	}

	@Override
	public PipeMessage<IMessageBody> receiveMsg() throws PipeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void ackMsg(PipeMessage<IMessageBody> msg) throws PipeException {
		// TODO Auto-generated method stub
	}
}
