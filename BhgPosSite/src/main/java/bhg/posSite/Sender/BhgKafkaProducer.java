package bhg.posSite.Sender;

import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import bhg.posSite.PipeException;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

/**
 * 消息发送器,发送参数从服务端获取,进行同意配置.
 * 
 * @author llq
 *
 */
public class BhgKafkaProducer {
	// 线程池负
	private final ExecutorService pool;
	// kafka生产者
	private final Producer<String, String> producer;

	/**
	 * 消息生产者
	 * 
	 * @param props
	 *            kafka配置参数
	 */
	public BhgKafkaProducer(Properties props) {
		// 构建Kafka Producer Configuration上下文
		ProducerConfig config = new ProducerConfig(props);

		// 构建Producer对象
		producer = new Producer<String, String>(config);
		pool = Executors.newSingleThreadExecutor();
	}

	/**
	 * 发送消息
	 * 
	 * @param topic
	 *            目的主题
	 * @param msg
	 *            消息体
	 * @return
	 */
	public Future<Integer> sendMessage(String topic, String msg) {
		// 异步发送
		Future<Integer> future = pool.submit(new Callable<Integer>() {
			public Integer call() throws PipeException {
				// 发送数据
				KeyedMessage<String, String> message = new KeyedMessage<String, String>(topic, msg);
				producer.send(message);
				return 1;
			}
		});
		return future;
	}

	/**
	 * 关闭
	 */
	public void close() {
		pool.shutdown();
		try {
			pool.awaitTermination(6, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
		} finally {
			producer.close();
		}
	}
}