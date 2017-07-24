package bhg.posSite;

import java.util.HashMap;
import java.util.Map;

public class KafkaSitePipe extends AbstractPipeSite {

	@Override
	public SiteStatus getStatus() {
		return SiteStatus.ONLINE;
	}

	@Override
	void sendMsg(PipeMessage<IMessageBody> msg, String site) throws PipeException {
		System.out.println(msg.toString());
	}

	@Override
	PipeMessage<IMessageBody> receiveMsg() throws PipeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	void ackMsg(PipeMessage<IMessageBody> msg) throws PipeException {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		IPipeSite site = new KafkaSitePipe();
		site.getSchemas();
		if(true){
			return;
		}
		Map<String, ISiteSendListener> sendCallbacks = new HashMap<String, ISiteSendListener>();
		// 商品表
		sendCallbacks.put("ITEM", new ISiteSendListener() {

			@Override
			public void onDataSendSucess(String msgId) {
				System.out.println(msgId + "send success");
			}

			@Override
			public void onDataSendFailure(String msgId, String errorMessage) {
				System.out.println(msgId + "send failure");
			}
		});
		site.connect(null, sendCallbacks);
		System.out.println("begin send");
		for (int i = 0; i < 100; i++) {
			SchemaItem item = new SchemaItem();
			item.setName("item" + i);
			item.setContext(i);
			System.out.println(site.sendData("SchemaItem", "mom_statistic", item));
		}
	}
}
