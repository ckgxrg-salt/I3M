package io.ckgxrg.i3m.mqtt;

import io.ckgxrg.i3m.block.blockentity.MQTTBlockEntity;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.function.Consumer;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraft.world.World;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

import io.ckgxrg.i3m.block.MQTTBlock;

import org.eclipse.paho.mqttv5.common.MqttMessage;

/* This class records subscribers, translates MQTT messages into Java data types, and then publish events to MQTTBlocks.
 * 本类记录订阅消息的方块, 将MQTT消息转换为Java数据类型, 并向MQTT方块发布更新指令。*/
public class MQTTNewsPress {

	/* The map will be used to register MQTTBlocks that will be notified when an MQTT message arrived at the handler.
	 * 此表用于记录当MQTT报文到达处理器时，会被通知的MQTT方块。*/
	static HashMap<String, ArrayList<MQTTBlockEntity>> subscribers = new HashMap<String, ArrayList<MQTTBlockEntity>>();

	/* Registers a MQTTBlock in the table.
	 * 将一个方块实体记录在表中。*/
	private static MQTTBlockEntity register(String topic, MQTTBlockEntity block) {
		if(subscribers.get(topic) == null) {
			ArrayList<MQTTBlockEntity> newList = new ArrayList<MQTTBlockEntity>();
			newList.add(block);
			subscribers.put(topic, newList);
		} else {
			ArrayList<MQTTBlockEntity> list = subscribers.get(topic);
			list.add(block);
		}
		return block;
	}
	/* By invoking this method, a block will subscribe to a topic.
	 * 将某个实际的方块注册在表中。*/
	public static void subscribe(World world, BlockPos pos, String topic, String archiveName) {
		BlockState state = world.getBlockState(pos);
		world.setBlockState(pos, state.with(MQTTBlock.CONNECTED, true));
		BlockEntity ent = world.getBlockEntity(pos);
		if(ent instanceof MQTTBlockEntity)
			register(topic, (MQTTBlockEntity)ent);
		else
			throw new UnsupportedOperationException("<I3M - MQTTNewsPress>: Unable to register a BlockEntity that is not a MQTTBlockEntity.");
	}

	/* Traverse all subscribers on a given topic.
	 * 遍历指定话题的所有订阅者。*/
	public static void forEach(String topic, Consumer<MQTTBlockEntity> func) {
		subscribers.get(topic).forEach(func);
	}

	/* We use raw JSON string as MQTT message, so the data will have to be extracted.
	 * 我们使用JSON字符串作为MQTT报文, 因此需要对原始数据进行提取。*/
	public static String unpackMsg(MqttMessage message, String archiveName) {
		String json = new String(message.getPayload());
		JsonObject jo = JsonParser.parseString(json).getAsJsonObject();
		return jo.get(archiveName).getAsString();
	}

	/* Broadcast the messages to the subscribers.
	 * 将消息广播到所有订阅者。*/
	public static void sendNews(String topic, String archiveName, MqttMessage message) {
		String msg = unpackMsg(message, archiveName);
		forEach(topic, blk -> {
			blk.update(msg);
		});
	}
}
