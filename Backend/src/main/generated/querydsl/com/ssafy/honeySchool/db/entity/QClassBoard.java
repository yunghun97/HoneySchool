package com.ssafy.honeySchool.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QClassBoard is a Querydsl query type for ClassBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClassBoard extends EntityPathBase<ClassBoard> {

    private static final long serialVersionUID = -532401905L;

    public static final QClassBoard classBoard = new QClassBoard("classBoard");

    public final StringPath category = createString("category");

    public final NumberPath<Integer> classes = createNumber("classes", Integer.class);

    public final StringPath content = createString("content");

    public final DateTimePath<java.util.Date> date = createDateTime("date", java.util.Date.class);

    public final StringPath file_link = createString("file_link");

    public final NumberPath<Integer> grade = createNumber("grade", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath school = createString("school");

    public final StringPath title = createString("title");

    public final NumberPath<Integer> viewcount = createNumber("viewcount", Integer.class);

    public final StringPath writer = createString("writer");

    public QClassBoard(String variable) {
        super(ClassBoard.class, forVariable(variable));
    }

    public QClassBoard(Path<? extends ClassBoard> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClassBoard(PathMetadata metadata) {
        super(ClassBoard.class, metadata);
    }

}

