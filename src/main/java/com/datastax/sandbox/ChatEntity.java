package com.datastax.sandbox;


import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.io.Serializable;
import java.util.Objects;

/**
 * Mapping of the Table use to Import to BigQuery.
 */
@Table(name = ChatEntity.TABLE_NAME)
public class ChatEntity implements Serializable {

    /** Constants for mapping. */
    public static final String TABLE_NAME             = "chat";
    public static final String COLUMN_CONVERSATION_ID = "conversation_id";
    public static final String COLUMN_RAW_TEXT        = "raw_text";
    public static final String COLUMN_EMBEDDING       = "embedding";

    @PartitionKey
    @Column(name = COLUMN_CONVERSATION_ID)
    private String conversationId;

    @Column(name = COLUMN_RAW_TEXT)
    private String text;

    @Column(name = COLUMN_EMBEDDING)
    private VectorSearch vectorSearch;

    /**
     * Create the table in Cassandra.
     *
     * @return
     *      cql statement
     */
    public static String cqlCreateTable() {
        return "CREATE TABLE IF NOT EXISTS chat (" +
                "conversation_id TEXT PRIMARY KEY, " +
                "raw_text        TEXT, " +
                "embedding       FLOAT VECTOR[768])";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ChatEntity that = (ChatEntity) o;
        return Objects.equals(conversationId, that.conversationId) && Objects.equals(text, that.text) && Objects.equals(vectorSearch, that.vectorSearch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(conversationId, text, vectorSearch);
    }

    /**
     * Gets conversationId
     *
     * @return value of conversationId
     */
    public String getConversationId() {
        return conversationId;
    }

    /**
     * Set value for conversationId
     *
     * @param conversationId
     *         new value for conversationId
     */
    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    /**
     * Gets text
     *
     * @return value of text
     */
    public String getText() {
        return text;
    }

    /**
     * Set value for text
     *
     * @param text
     *         new value for text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets vectorSearch
     *
     * @return value of vectorSearch
     */
    public VectorSearch getVectorSearch() {
        return vectorSearch;
    }

    /**
     * Set value for vectorSearch
     *
     * @param vectorSearch
     *         new value for vectorSearch
     */
    public void setVectorSearch(VectorSearch vectorSearch) {
        this.vectorSearch = vectorSearch;
    }
}
