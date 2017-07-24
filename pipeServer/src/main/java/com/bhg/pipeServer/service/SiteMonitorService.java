package com.bhg.pipeServer.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.stereotype.Service;

import com.bhg.pipeServer.exception.PipeException;
import com.google.gson.Gson;

/**
 * 站点监控程序,监测各个站点状态
 * 
 * @author llq
 *
 */
@Service
public class SiteMonitorService {
	private static final String brokeList = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
	private static final String groupNode = "siteListGroup";
	private static final int sessionTimeOut = 5000;
	private ZooKeeper zk = null;
	private Stat stat = new Stat();
	private volatile List<String> siteList = null;

	public List<String> getOnLineSites() {
		return siteList;
	}

	@PostConstruct
	private void init() {
		connectZookeeper();
	}

	/**
	 * 连接zookeeper
	 * 
	 * @throws IOException
	 */
	private void connectZookeeper() throws PipeException {
		try {
			zk = new ZooKeeper(brokeList, sessionTimeOut, new Watcher() {
				public void process(WatchedEvent event) {
					// 如果发生了"/groupNode"节点下子节点变化事件，更新server列表，并重新注册监听
					if (event.getType() == Event.EventType.NodeChildrenChanged
							&& ("/" + groupNode).equals(event.getPath())) {
						updateServerList();
					}
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
			throw new PipeException(-1);
		}
	}

	/**
	 * 更新server列表
	 */
	private void updateServerList() throws PipeException {
		List<String> newServerList = new ArrayList<String>();

		// 获取并监听groupNode子节点变化
		// watch参数为true，表示监听子节点变化事件
		// 每次都需要重新注册监听，因为一次注册，只能监听一次事件，如果还想继续保持监听，必须重新注册
		List<String> subList;
		try {
			subList = zk.getChildren("/" + groupNode, true);
			for (String subNode : subList) {
				byte[] data = zk.getData("/" + groupNode + "/" + subNode, false, stat);
				newServerList.add(new String(data, "utf-8"));
			}
			siteList = newServerList;
			System.out.println(new Gson().toJson(siteList));
		} catch (KeeperException | InterruptedException e) {
			e.printStackTrace();
			throw new PipeException(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new PipeException(-1);
		}
	}

	public void handle() throws InterruptedException {
		Thread.sleep(Long.MAX_VALUE);
	}

	public static void main(String[] args) throws InterruptedException {
		SiteMonitorService service = new SiteMonitorService();
		service.init();
		service.handle();
	}
}
