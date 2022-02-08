package com.ssafy.honeySchool.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLecture is a Querydsl query type for Lecture
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLecture extends EntityPathBase<Lecture> {

    private static final long serialVersionUID = -605160355L;

    public static final QLecture lecture = new QLecture("lecture");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final DateTimePath<java.time.LocalDateTime> end_time = createDateTime("end_time", java.time.LocalDateTime.class);

    //inherited
    public final NumberPath<Integer> id = _super.id;

    public final DateTimePath<java.time.LocalDateTime> start_time = createDateTime("start_time", java.time.LocalDateTime.class);

    public final StringPath teacher = createString("teacher");

    public final StringPath thumbnail_url = createString("thumbnail_url");

    public final StringPath title = createString("title");

    public final StringPath user_id = createString("user_id");

    public QLecture(String variable) {
        super(Lecture.class, forVariable(variable));
    }

    public QLecture(Path<? extends Lecture> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLecture(PathMetadata metadata) {
        super(Lecture.class, metadata);
    }

}

