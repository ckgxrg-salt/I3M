package io.ckgxrg.i3m.block.blockentity;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.network.listener.ClientPlayPacketListener;

import org.jetbrains.annotations.Nullable;

public abstract class MQTTBlockEntity extends BlockEntity {

	/*
	 * All MQTT blocks will have to listen on a given topic. This field indicates it.
	 * Since we use JSON format, the archiveName is the key.
	 * Considering efficiency, we cannot allow MQTT clients to be run by any block.
	 *
	 * 所有MQTT方块需要订阅指定主题, topic字段即为该主题。
	 * 鉴于我们使用JSON格式, archiveName字段即为字段名。
	 * 考虑到效率, 并不能由所有MQTT方块分别运行MQTT客户端。*/
	String topic;
	String archiveName;
	/* The value of the topic.
	 * 主题的内容 */
	String value;

	public MQTTBlockEntity(BlockEntityType<? extends BlockEntity> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
		topic = "";
		archiveName = "";
		value = "";
	}

	// Getters / Setters
	public String getTopic() {
		return topic;
	}
	public String getArchive() {
		return archiveName;
	}
	public String getValue() {
		return value;
	}
	public void newTopic(String topic, String archiveName) {
		this.topic = topic;
		this.archiveName = archiveName;
		markDirty();
	}
	public void update(String value) {
		this.value = value;
		markDirty();
	}
	public abstract void sync();

	/* Basic NBT read / write.
	 * 基本NBT数据读写。*/
	@Override
	public void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapper) {
		nbt.putString("mqtt_topic", topic);
		nbt.putString("mqtt_archive", archiveName);
		nbt.putString("mqtt_value", value);
		super.writeNbt(nbt, wrapper);
	}
	@Override
	public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapper) {
		super.readNbt(nbt, wrapper);
		topic = nbt.getString("mqtt_topic");
		archiveName = nbt.getString("mqtt_archive");
		value = nbt.getString("mqtt_value");
	}
	@Nullable
  	@Override
  	public Packet<ClientPlayPacketListener> toUpdatePacket() {
    		return BlockEntityUpdateS2CPacket.create(this);
  	}
  	@Override
  	public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup wrapper) {
    		return createNbt(wrapper);
  	}

}
