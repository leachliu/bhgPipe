package bhg.posSite;

import java.io.IOException;
import java.net.InetAddress;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

public class ServerConnect {
	private static final String brokeList = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
	private static final String groupNode = "siteListGroup";
	private static final int sessionTimeOut = 5000;

	private String subNode;

	public ServerConnect(String siteName) {
		this.subNode = siteName;
	}

	/**
	 * 链接zookeeper
	 * 
	 * @param address
	 * @throws IOException
	 */
	public void connectZookeeper(String address) throws IOException, KeeperException, InterruptedException {
		ZooKeeper zk = new ZooKeeper(brokeList, sessionTimeOut, new Watcher() {
			public void process(WatchedEvent event) {
				// 不做处理
			}
		});
		// 在"/groupNode"下创建子节点
		// 子节点类型设置为EPHEMERAL_SEQUENTIAL，表明这是一个临时节点，且在子节点的名称后面加上一串数字后缀
		// 将server的地址数据关联到新创建的子节点上
		String createPath = zk.create("/" + groupNode + "/" + subNode, address.getBytes("utf-8"),
				ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		System.out.println("create:" + createPath);
	}

	/**
	 * server的工作逻辑写在这个方法中
	 * 
	 * @throws InterruptedException
	 */
	public void handle() throws InterruptedException {
		Thread.sleep(Long.MAX_VALUE);
	}

	private static String getIp() {
		try {
			InetAddress ia = InetAddress.getLocalHost();
			String localip = ia.getHostAddress();
			System.out.println("本机的ip是 ：" + localip);
			return localip;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void main(String[] args) throws InterruptedException, IOException, KeeperException {
		ServerConnect as = new ServerConnect("SiteA");
		as.connectZookeeper(getIp());
		as.handle();
	}
}
