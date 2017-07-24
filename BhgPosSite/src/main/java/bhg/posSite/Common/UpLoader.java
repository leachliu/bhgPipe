package bhg.posSite.Common;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import kafka.producer.KafkaLog4jAppender;

public class UpLoader {
	Logger logger;

	public void init(String title, String topic, String brokerList, int requiredNumAcks) {
		logger = Logger.getLogger(title);
		PatternLayout layout = new PatternLayout("[%d] %p %t %c - %m%n");

		KafkaLog4jAppender appender = new KafkaLog4jAppender();
		appender.setTopic(topic);
		appender.setBrokerList(brokerList);
		appender.setCompressionType("none");
		appender.setSyncSend(true);
		appender.setLayout(layout);
		appender.setRequiredNumAcks(requiredNumAcks);
		appender.setName("upload");
		logger.addAppender(appender);
		logger.setLevel(Level.INFO);
	}

	public void sendMessage(String message) {
		logger.info(message);
	}

	public static void main(String[] args) {
		UpLoader uploader = new UpLoader();
		uploader.init("title", "a", "b", 2);
		uploader.sendMessage("abc");
	}
}
