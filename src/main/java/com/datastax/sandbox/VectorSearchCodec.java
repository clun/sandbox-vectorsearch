package com.datastax.sandbox;

import com.datastax.driver.core.DataType;
import com.datastax.driver.core.ProtocolVersion;
import com.datastax.driver.core.TypeCodec;
import com.datastax.driver.core.exceptions.InvalidTypeException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Arrays;

/**
 * Codec to map the Vector type from Cassandra.
 */
public class VectorSearchCodec extends TypeCodec<VectorSearch> {

    /**
     * Constructor with mapping of type.
     */
    public VectorSearchCodec() {
        super(DataType.custom("org.apache.cassandra.db.marshal.VectorType(768)"), VectorSearch.class);
    }

    /**
     * VectorSearch to ByteBuffer.
     */
    @Override
    public ByteBuffer serialize(VectorSearch value, ProtocolVersion protocolVersion) throws InvalidTypeException {
        if (value == null) return null;
        int bufferSize = value.getVector().length * Float.BYTES;
        ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize);
        // Set the byte order to match the system's native orders
        byteBuffer.order(ByteOrder.nativeOrder());
        // Create a FloatBuffer from the ByteBuffer
        FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
        // Put the Float objects into the FloatBuffer
        for (Float floatValue : value.getVector()) {
            floatBuffer.put(floatValue);
        }
        // Rewind the FloatBuffer to prepare for reading
        floatBuffer.rewind();
        return byteBuffer;
    }

    /**
     * ByteBuffer to VectorSearch.
     */
    @Override
    @SuppressWarnings("unchecked")
    public VectorSearch deserialize(ByteBuffer bytes, ProtocolVersion protocolVersion) throws InvalidTypeException {
        if (bytes == null) return null;
        return new VectorSearch(bytes);
    }

    /**
     * Serialize as a list of flaot separated by comma.
     */
    @Override
    public String format(VectorSearch value) throws InvalidTypeException {
        if (value == null) return "NULL";
        return value.toString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public VectorSearch parse(String value) throws InvalidTypeException {
        if (value == null || value.isEmpty() || value.equalsIgnoreCase("NULL"))
            return null;
        return new VectorSearch(Arrays.asList(value.split(",")));
    }
}
