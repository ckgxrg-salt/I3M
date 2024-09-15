package io.ckgxrg.i3m.mqtt;

import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;
import org.eclipse.paho.mqttv5.client.MqttCallback;
import org.eclipse.paho.mqttv5.client.MqttDisconnectResponse;
import org.eclipse.paho.mqttv5.client.IMqttToken;

import io.ckgxrg.i3m.I3M;

public class NewsPressCallback implements MqttCallback {

	public MQTTHandler host;

	public NewsPressCallback(MQTTHandler host) {
		this.host = host;
	}

	public void disconnected(MqttDisconnectResponse rep) {
		I3M.LOGGER.warn("<I3M - MQTTHandler " + host.getId() +  ">: Disconnected. Paho returned: " + rep.getReasonString());
	}
	public void mqttErrorOccurred(MqttException exc) {
		I3M.LOGGER.error("<I3M - MQTTHandler " + host.getId() + ">: Paho returned an Error: " + exc.getMessage());
	}
	public void connectComplete(boolean reconnect, String serverURL) {
		if(serverURL != host.serveraddr()) {
			try {
				host.reconnect();
			} catch(MqttException e) {
				e.printStackTrace();
			}
			I3M.LOGGER.warn("<I3M - MQTTHandler " + host.getId() +  ">: Server Address Mismatch! Attempting reconnection...");
		}
	}
	public void authPacketArrived(int reason, MqttProperties prop) {
	}	
    	public void messageArrived(String topic, MqttMessage message) throws Exception {
		host.gotcha(topic, message);
	}
    	public void deliveryComplete(IMqttToken token) {
		if(token.isComplete()) I3M.LOGGER.info("<I3M - MQTTHandler " + host.getId() + ">: Recieved an MQTT Message.");
	}

}
