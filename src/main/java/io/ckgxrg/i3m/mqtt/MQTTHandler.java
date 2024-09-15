package io.ckgxrg.i3m.mqtt;

import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;

import java.util.HashMap;

import io.ckgxrg.i3m.I3M;

public class MQTTHandler implements Runnable {

	private MqttClient paho;
	private MqttConnectionOptions opt;
	private Thread thread;
	private String serveraddr;
	private String clientid;

	private boolean connected;

	private HashMap<String, String> topics;

	public MQTTHandler(String clientid, String serveraddr) {
		this.serveraddr = serveraddr;
		this.clientid = clientid;
		connected = false;
	}

	public String getId() {
		return clientid;
	}
	public String serveraddr() {
		return serveraddr;
	}

	public void start() {
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		start();
	}

	public void subscribe(String topic, String archiveName) throws MqttException {
		if(!connected) return;
		topics.put(topic, archiveName);
		paho.subscribe(topic, 0);
	}

	public void connect(String username, String password) throws MqttException {
		opt = new MqttConnectionOptions();
		opt.setCleanStart(false);
		opt.setUserName(username);
		opt.setPassword(password.getBytes());
		opt.setConnectionTimeout(10);
		opt.setKeepAliveInterval(20);
		paho = new MqttClient(serveraddr, clientid);
		paho.connect(opt);
		connected = true;
	}
	public void reconnect() throws MqttException {
		if(opt == null || paho == null) {
			I3M.LOGGER.error("<I3M - MQTTHandler " + this.clientid + ">: No credentials stored, impossible to reconnect.");
			connected = false;
		}
		paho.connect(opt);
		connected = true;
	}

	public void gotcha(String topic, MqttMessage msg) {
		if(topics.keySet().contains(topic)) {
			MQTTNewsPress.sendNews(topic, topics.get(topic), msg);
		}	
	}

}
