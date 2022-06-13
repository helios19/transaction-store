package com.abn.amro.common.sequence;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import static com.abn.amro.common.utils.ClassUtils.COUNTERS_COLLECTION_NAME;

/**
 * Counter document class holding up the sequence value of a given collection.
 *
 * @see com.abn.amro.common.service.CounterServiceImpl
 */
@Data
@Builder
@Document(collection = COUNTERS_COLLECTION_NAME)
public class Counter {
    @Id
    private String id;

    @Field
    private int seq;
}