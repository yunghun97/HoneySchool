package com.ssafy.honeySchool.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLectureUserHistory is a Querydsl query type for LectureUserHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLectureUserHistory extends EntityPathBase<LectureUserHistory> {

    private static final long serialVersionUID = -1863252564L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLectureUserHistory lectureUserHistory = new QLectureUserHistory("lectureUserHistory");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final BooleanPath join = createBoolean("join");

    public final StringPath link = createString("link");

    public final DatePath<java.sql.Date> time = createDate("time", java.sql.Date.class);

    public final QUser user;

    public QLectureUserHistory(String variable) {
        this(LectureUserHistory.class, forVariable(variable), INITS);
    }

    public QLectureUserHistory(Path<? extends LectureUserHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLectureUserHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLectureUserHistory(PathMetadata metadata, PathInits inits) {
        this(LectureUserHistory.class, metadata, inits);
    }

    public QLectureUserHistory(Class<? extends LectureUserHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

