/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.server.dao;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Field;
import com.datastax.driver.mapping.annotations.Frozen;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.datastax.driver.mapping.annotations.UDT;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;

/**
 *
 *
 * @author billschwanitz
 */
@Table(name = "paging_state")
public class PagingStateDao {
    @PartitionKey
    @Column(name = "user_id")
    private UUID userId;
    @Column(name = "paging_state")
    @Frozen
    private PagingStateUdt pagingState;

    /**
     * @return the userId
     */
    public UUID getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    /**
     * @return the pagingState
     */
    public PagingStateUdt getPagingState() {
        return pagingState;
    }

    /**
     * @param pagingState the pagingState to set
     */
    public void setPagingState(PagingStateUdt pagingState) {
        this.pagingState = pagingState;
    }

    @UDT(name = "paging_state")
    public static class PagingStateUdt {
        @Field(name = "page_size")
        private int pageSize;
        @Field(name = "content_type")
        private String contentType;
        private String query;
        @Field(name = "page_state")
        private ByteBuffer pageState;
        private boolean end;
        private List<String> buffer;

        /**
         * @return the pageSize
         */
        public int getPageSize() {
            return pageSize;
        }

        /**
         * @param pageSize the pageSize to set
         */
        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        /**
         * @return the contentType
         */
        public String getContentType() {
            return contentType;
        }

        /**
         * @param contentType the contentType to set
         */
        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        /**
         * @return the query
         */
        public String getQuery() {
            return query;
        }

        /**
         * @param query the query to set
         */
        public void setQuery(String query) {
            this.query = query;
        }

        /**
         * @return the pageState
         */
        public ByteBuffer getPageState() {
            return pageState;
        }

        /**
         * Get the blob data out of the ByteBuffer as an byte[]
         * 
         * @return a byte[] containing the BLOB, or null if pageState is null
         */
        public byte[] getPageStateBlob() {
            if ( null == pageState ) {
                return null;
            }

            byte[] blob = new byte[pageState.remaining()];
            pageState.get(blob);

            return blob;
        }

        /**
         * @param pageState the pageState to set
         */
        public void setPageState(ByteBuffer pageState) {
            this.pageState = pageState;
        }

        /**
         * @return the end
         */
        public boolean isEnd() {
            return end;
        }

        /**
         * @param end the end to set
         */
        public void setEnd(boolean end) {
            this.end = end;
        }

        /**
         * @return the buffer
         */
        public List<String> getBuffer() {
            return buffer;
        }

        /**
         * @param buffer the buffer to set
         */
        public void setBuffer(List<String> buffer) {
            this.buffer = buffer;
        }
    }
}
