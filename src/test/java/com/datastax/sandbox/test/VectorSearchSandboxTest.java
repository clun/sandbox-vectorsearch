package com.datastax.sandbox.test;

import com.datastax.sandbox.ChatEntity;
import com.datastax.sandbox.VectorSearchCodec;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.CodecRegistry;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VectorSearchSandboxTest {

    Logger LOGGER = LoggerFactory.getLogger(VectorSearchSandboxTest.class);

    private static final String CASSANDRA_HOST = "<change_me>";
    private static final int    CASSANDRA_PORT = 9042;
    private static final String USERNAME       = "cassandra";
    private static final String PASSWORD       = "<change_me>";
    private static final String KEYSPACE       = "demo";

    @Test
    public void readCassandra() {
        try(Cluster cluster = Cluster.builder()
                .addContactPoint(CASSANDRA_HOST)
                .withPort(9042)
                .withCredentials(USERNAME, PASSWORD)
                .build()) {
            LOGGER.info("Connected to cluster: {}", cluster.getMetadata().getClusterName());
            CodecRegistry myCodecRegistry = cluster.getConfiguration().getCodecRegistry();
            myCodecRegistry.register(new VectorSearchCodec());
            try(Session session = cluster.connect(KEYSPACE)) {
                LOGGER.info("Connected to Keyspace: {}", KEYSPACE);
                MappingManager manager = new MappingManager(session);
                Mapper<ChatEntity> mapper = manager.mapper(ChatEntity.class);

                // Execution of query
                ResultSet rs = session.execute("select * from chat where conversation_id='161643600678082'");
                Result<ChatEntity> chat = mapper.map(rs);
                System.out.println(chat.one().getVectorSearch().toString());
            }
        }
    }
}
