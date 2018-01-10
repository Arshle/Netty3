/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: Serializer.java
 * Author:   zhangdanji
 * Date:     2017年10月11日
 * Description: 自定义序列化类  
 */
package com.chezhibao.core.serializer;

import org.jboss.netty.buffer.ChannelBuffer;
import java.nio.charset.Charset;
import java.util.*;

/**
 * 自定义序列化类
 *
 * @author zhangdanji
 */
public abstract class Serializer {

    //字符编码
    public static final Charset CHARSET = Charset.forName("UTF-8");
    //读字节流
    protected ChannelBuffer readBuffer;
    //写字节流
    protected ChannelBuffer writeBuffer;

    /**
     * 反序列化读取操作
     *
     * **/
    protected abstract void read();

    /**
     * 序列化写操作
     *
     * **/
    protected abstract void write();

    /**
     * 从字节数组中读取数据
     * @param bytes 存储数据的字节数组
     * @return 反序列化后的对象
     *
     * **/
    public Serializer readFromBytes(byte[] bytes){
        readBuffer = ChannelBufferFactory.getBuffer(bytes);
        read();
        readBuffer.clear();
        return this;
    }

    /**
     * 从channelBuffer中反序列化
     * @param buffer channelBuffer对象
     *
     * **/
    public void readFromBuffer(ChannelBuffer buffer){
        readBuffer = buffer;
        read();
    }

    /**
     * 写入本地序列化
     * @return 写入的channelBuffer
     *
     * **/
    public ChannelBuffer writeToLocalBuffer(){
        writeBuffer = ChannelBufferFactory.getBuffer();
        write();
        return writeBuffer;
    }

    /**
     * 写入目标buffer对象
     * @param target 目标channelBuffer对象
     * @return 写入后的channelBuffer
     *
     * **/
    public ChannelBuffer writeToTargetBuffer(ChannelBuffer target){
        writeBuffer = target;
        write();
        return writeBuffer;
    }

    /**
     * 获取字节数组
     * @return 字节数组
     *
     * **/
    public byte[] getBytes() {
        writeToLocalBuffer();
        byte[] bytes = null;
        if (writeBuffer.writerIndex() == 0) {
            bytes = new byte[0];
        } else {
            bytes = new byte[writeBuffer.writerIndex()];
            writeBuffer.readBytes(bytes);
        }
        writeBuffer.clear();
        return bytes;
    }

    /**
     * 反序列化列表类型
     * @param clazz 列表类型
     * @return 反序列化后的列表
     *
     * **/
    public <T> List<T> readList(Class<T> clazz) {
        List<T> list = new ArrayList<>();
        int size = readBuffer.readShort();
        for (int i = 0; i < size; i++) {
            list.add(read(clazz));
        }
        return list;
    }

    /**
     * 反序列化map类型
     * @param keyClazz 键类型
     * @param valueClazz 值类型
     * @return 读取后的Map对象
     *
     * **/
    public <K,V> Map<K,V> readMap(Class<K> keyClazz, Class<V> valueClazz) {
        Map<K,V> map = new HashMap<>();
        int size = readBuffer.readShort();
        for (int i = 0; i < size; i++) {
            K key = read(keyClazz);
            V value = read(valueClazz);
            map.put(key, value);
        }
        return map;
    }

    /**
     * 读取类型
     * @param clazz 读取的类型
     * @return 读取后的类型
     *
     * **/
    @SuppressWarnings("unchecked")
    public <I> I read(Class<I> clazz) {
        Object t = null;
        if ( clazz == int.class || clazz == Integer.class) {
            t = this.readInt();
        } else if (clazz == byte.class || clazz == Byte.class){
            t = this.readByte();
        } else if (clazz == short.class || clazz == Short.class){
            t = this.readShort();
        } else if (clazz == long.class || clazz == Long.class){
            t = this.readLong();
        } else if (clazz == float.class || clazz == Float.class){
            t = this.readFloat();
        } else if (clazz == double.class || clazz == Double.class){
            t = this.readDouble();
        } else if (clazz == String.class ){
            t = this.readString();
        } else if (Serializer.class.isAssignableFrom(clazz)){
            try {
                byte hasObject = this.readBuffer.readByte();
                if(hasObject == 1){
                    Serializer temp = (Serializer)clazz.newInstance();
                    temp.readFromBuffer(this.readBuffer);
                    t = temp;
                }else{
                    t = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            throw new RuntimeException(String.format("不支持类型:[%s]", clazz));
        }
        return (I) t;
    }

    /**
     * 写入list类型
     * @param list list需要写入的list
     * @return 序列化后的对象
     *
     * **/
    public <T> Serializer writeList(List<T> list) {
        if (isEmpty(list)) {
            writeBuffer.writeShort((short) 0);
            return this;
        }
        writeBuffer.writeShort((short) list.size());
        for (T item : list) {
            writeObject(item);
        }
        return this;
    }

    /**
     * 写入map类型
     * @param map map对象
     * @return 序列化后的map对象
     *
     * **/
    public <K,V> Serializer writeMap(Map<K, V> map) {
        if (isEmpty(map)) {
            writeBuffer.writeShort((short) 0);
            return this;
        }
        writeBuffer.writeShort((short) map.size());
        for (Map.Entry<K, V> entry : map.entrySet()) {
            writeObject(entry.getKey());
            writeObject(entry.getValue());
        }
        return this;
    }

    /**
     * 写入对象
     * @param object 对象
     * @return 序列化对象
     *
     * **/
    public Serializer writeObject(Object object) {

        if(object == null){
            writeByte((byte)0);
        }else{
            if (object instanceof Integer) {
                writeInt((int) object);
                return this;
            }

            if (object instanceof Long) {
                writeLong((long) object);
                return this;
            }

            if (object instanceof Short) {
                writeShort((short) object);
                return this;
            }

            if (object instanceof Byte) {
                writeByte((byte) object);
                return this;
            }

            if (object instanceof String) {
                String value = (String) object;
                writeString(value);
                return this;
            }
            if (object instanceof Serializer) {
                writeByte((byte)1);
                Serializer value = (Serializer) object;
                value.writeToTargetBuffer(writeBuffer);
                return this;
            }

            throw new RuntimeException("不可序列化的类型:" + object.getClass());
        }

        return this;
    }

    private <T> boolean isEmpty(Collection<T> c) {
        return c == null || c.size() == 0;
    }
    public <K,V> boolean isEmpty(Map<K,V> c) {
        return c == null || c.size() == 0;
    }

    /**
     * 基本数据类型的读写操作
     *
     * **/
    public byte readByte() {
        return readBuffer.readByte();
    }

    public short readShort() {
        return readBuffer.readShort();
    }

    public int readInt() {
        return readBuffer.readInt();
    }

    public long readLong() {
        return readBuffer.readLong();
    }

    public float readFloat() {
        return readBuffer.readFloat();
    }

    public double readDouble() {
        return readBuffer.readDouble();
    }

    public String readString() {
        int size = readBuffer.readShort();
        if (size <= 0) {
            return "";
        }

        byte[] bytes = new byte[size];
        readBuffer.readBytes(bytes);

        return new String(bytes, CHARSET);
    }

    public Serializer writeByte(Byte value) {
        writeBuffer.writeByte(value);
        return this;
    }

    public Serializer writeShort(Short value) {
        writeBuffer.writeShort(value);
        return this;
    }

    public Serializer writeInt(Integer value) {
        writeBuffer.writeInt(value);
        return this;
    }

    public Serializer writeLong(Long value) {
        writeBuffer.writeLong(value);
        return this;
    }

    public Serializer writeFloat(Float value) {
        writeBuffer.writeFloat(value);
        return this;
    }

    public Serializer writeDouble(Double value) {
        writeBuffer.writeDouble(value);
        return this;
    }

    public Serializer writeString(String value) {
        if (value == null || value.isEmpty()) {
            writeShort((short) 0);
            return this;
        }

        byte data[] = value.getBytes(CHARSET);
        short len = (short) data.length;
        writeBuffer.writeShort(len);
        writeBuffer.writeBytes(data);
        return this;
    }
}
