package com.ssafy.honeySchool.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClassComment is a Querydsl query type for ClassComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClassComment extends EntityPathBase<ClassComment> {

    private static final long serialVersionUID = 361318984L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClassComment classComment = new QClassComment("classComment");

    public final QClassBoard classBoard;

    public final StringPath content = createString("content");

    public final DatePath<java.sql.Date> date = createDate("date", java.sql.Date.class);

    public final StringPath file_link = createString("file_link");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath writer = createString("writer");

    public QClassComment(String variable) {
        this(ClassComment.class, forVariable(variable), INITS);
    }

    public QClassComment(Path<? extends ClassComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClassComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClassComment(PathMetadata metadata, PathInits inits) {
        this(ClassComment.class, metadata, inits);
    }

    public QClassComment(Class<? extends ClassComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.classBoard = inits.isInitialized("classBoard") ? new QClassBoard(forProperty("classBoard")) : null;
    }

}

