package com.ssafy.honeySchool.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNoticeFile is a Querydsl query type for NoticeFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNoticeFile extends EntityPathBase<NoticeFile> {

    private static final long serialVersionUID = -1484880587L;

    public static final QNoticeFile noticeFile = new QNoticeFile("noticeFile");

    public final NumberPath<Long> file_size = createNumber("file_size", Long.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final EnumPath<DeleteYn> isDeleted = createEnum("isDeleted", DeleteYn.class);

    public final NumberPath<Integer> noticeId = createNumber("noticeId", Integer.class);

    public final StringPath original_file_name = createString("original_file_name");

    public final StringPath stored_file_path = createString("stored_file_path");

    public QNoticeFile(String variable) {
        super(NoticeFile.class, forVariable(variable));
    }

    public QNoticeFile(Path<? extends NoticeFile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNoticeFile(PathMetadata metadata) {
        super(NoticeFile.class, metadata);
    }

}

