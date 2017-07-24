package bhg.posSite.Receiver;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bhg.posSite.IMessageBody;
import bhg.posSite.IPipeSite;
import bhg.posSite.IPipeSite.ISiteReceiveListener;
import bhg.posSite.PipeException;

public class DataReceiver {
	IPipeSite site;

	DataReceiver() {
		try {
			site.init("1234", new URI("localhost://8080/siteManger"));
		} catch (PipeException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		Map<String, ISiteReceiveListener> callers = new HashMap<String, ISiteReceiveListener>();
		// 商品表
		callers.put("ITEM", new ISiteReceiveListener() {
			@Override
			public void onDataReceived(List<IMessageBody> datas) {
				// TODO Auto-generated method stub
				
			}
		});

		// 订单表
		callers.put("ORDER", new ISiteReceiveListener() {

			@Override
			public void onDataReceived(List<IMessageBody> datas) {

				
			}
		});
		site.connect(callers, null);

	}
}
