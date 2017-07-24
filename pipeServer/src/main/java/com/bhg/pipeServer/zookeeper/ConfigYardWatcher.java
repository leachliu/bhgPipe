package com.bhg.pipeServer.zookeeper;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bhg.pipeServer.zookeeper.ConfigYard.ConfigYardCallBack;

/**
 * @author llq
 * 
 */
public class ConfigYardWatcher {
	private final static Logger logger = LoggerFactory.getLogger(ConfigYardWatcher.class);

	private ZkClient client;

	private ConfigYardListener configYardListener;

	private ConfigYard configYard;
	ConfigYardCallBack callback;

	public ConfigYardWatcher(ZkClient client, ConfigYard configYard, ConfigYardCallBack callback) {
		this.client = client;
		this.configYard = configYard;
		this.initConfigYard();
	}

	private void initConfigYard() {
		configYardListener = new ConfigYardListener(callback);
	}

	public void watcher(String key) {
		client.subscribeDataChanges(key, configYardListener);
		client.subscribeChildChanges(key, configYardListener);
	}

	/**
	 * 配置监听器
	 * 
	 * @author llq
	 *
	 */
	private class ConfigYardListener implements IZkDataListener, IZkChildListener {
		ConfigYardCallBack callback;

		ConfigYardListener(ConfigYardCallBack callback) {
			this.callback = callback;
		}

		private void callback() {
			if (callback != null) {
				callback.onChanged();
			}
		}

		public void handleDataChange(String dataPath, Object data) throws Exception {
			System.out.println("data {} change,start reload configProperties:" + dataPath);
			configYard.reload();
			callback();
		}

		public void handleDataDeleted(String dataPath) throws Exception {
			System.out.println("data {} delete,start reload configProperties:" + dataPath);
			configYard.reload();
			callback();
		}

		public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
			System.out.println("data {} ChildChange,start reload configProperties:" + parentPath);
			configYard.reload();
			callback();
		}
	}
}
