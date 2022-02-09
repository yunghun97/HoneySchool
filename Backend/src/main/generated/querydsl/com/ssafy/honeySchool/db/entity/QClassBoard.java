package com.ssafy.honeySchool.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClassBoard is a Querydsl query type for ClassBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClassBoard extends EntityPathBase<ClassBoard> {

    private static final long serialVersionUID = -532401905L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClassBoard classBoard = new QClassBoard("classBoard");

    public final StringPath category = createString("category");

    public final NumberPath<Integer> classes = createNumber("classes", Integer.class);

    public final StringPath content = createString("content");

    public final DateTimePath<java.util.Date> date = createDateTime("date", java.util.Date.class);

    public final NumberPath<Integer> grade = createNumber("grade", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath school = createString("school");

    public final StringPath title = createString("title");

    public final QUser user;

    public final NumberPath<Integer> viewcount = createNumber("viewcount", Integer.class);

    public QClassBoard(String variable) {
        this(ClassBoard.class, forVariable(variable), INITS);
    }

    public QClassBoard(Path<? extends ClassBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClassBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClassBoard(PathMetadata metadata, PathInits inits) {
        this(ClassBoard.class, metadata, inits);
    }

    public QClassBoard(Class<? extends ClassBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

