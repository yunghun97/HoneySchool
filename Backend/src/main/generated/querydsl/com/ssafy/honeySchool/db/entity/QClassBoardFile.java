package com.ssafy.honeySchool.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QClassBoardFile is a Querydsl query type for ClassBoardFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClassBoardFile extends EntityPathBase<ClassBoardFile> {

    private static final long serialVersionUID = -776438997L;

    public static final QClassBoardFile classBoardFile = new QClassBoardFile("classBoardFile");

    public final NumberPath<Integer> board_id = createNumber("board_id", Integer.class);

    public final EnumPath<DeleteYn> delete_yn = createEnum("delete_yn", DeleteYn.class);

    public final NumberPath<Long> file_size = createNumber("file_size", Long.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath original_file_name = createString("original_file_name");

    public final StringPath stored_file_path = createString("stored_file_path");

    public QClassBoardFile(String variable) {
        super(ClassBoardFile.class, forVariable(variable));
    }

    public QClassBoardFile(Path<? extends ClassBoardFile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClassBoardFile(PathMetadata metadata) {
        super(ClassBoardFile.class, metadata);
    }

}

