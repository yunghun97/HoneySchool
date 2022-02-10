package com.ssafy.honeySchool.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTimetable is a Querydsl query type for Timetable
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTimetable extends EntityPathBase<Timetable> {

    private static final long serialVersionUID = -782740416L;

    public static final QTimetable timetable = new QTimetable("timetable");

    public final NumberPath<Integer> classes = createNumber("classes", Integer.class);

    public final StringPath day = createString("day");

    public final DateTimePath<java.util.Date> endTime = createDateTime("endTime", java.util.Date.class);

    public final NumberPath<Integer> grade = createNumber("grade", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath school = createString("school");

    public final DateTimePath<java.util.Date> startTime = createDateTime("startTime", java.util.Date.class);

    public final StringPath subject = createString("subject");

    public QTimetable(String variable) {
        super(Timetable.class, forVariable(variable));
    }

    public QTimetable(Path<? extends Timetable> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTimetable(PathMetadata metadata) {
        super(Timetable.class, metadata);
    }

}

