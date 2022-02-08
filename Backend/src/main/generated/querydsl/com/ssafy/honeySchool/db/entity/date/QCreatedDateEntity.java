package com.ssafy.honeySchool.db.entity.date;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCreatedDateEntity is a Querydsl query type for CreatedDateEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QCreatedDateEntity extends EntityPathBase<CreatedDateEntity> {

    private static final long serialVersionUID = 1631193850L;

    public static final QCreatedDateEntity createdDateEntity = new QCreatedDateEntity("createdDateEntity");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public QCreatedDateEntity(String variable) {
        super(CreatedDateEntity.class, forVariable(variable));
    }

    public QCreatedDateEntity(Path<? extends CreatedDateEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCreatedDateEntity(PathMetadata metadata) {
        super(CreatedDateEntity.class, metadata);
    }

}

