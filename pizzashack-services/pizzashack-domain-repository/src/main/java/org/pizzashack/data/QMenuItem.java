package org.pizzashack.data;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QMenuItem is a Querydsl query type for MenuItem
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QMenuItem extends EntityPathBase<MenuItem> {

    private static final long serialVersionUID = -804426018;

    public static final QMenuItem menuItem = new QMenuItem("menuItem");

    public final StringPath description = createString("description");

    public final StringPath icon = createString("icon");

    public final NumberPath<Long> menuItemId = createNumber("menuItemId", Long.class);

    public final StringPath name = createString("name");

    public final StringPath price = createString("price");

    public QMenuItem(String variable) {
        super(MenuItem.class, forVariable(variable));
    }

    public QMenuItem(Path<? extends MenuItem> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMenuItem(PathMetadata<?> metadata) {
        super(MenuItem.class, metadata);
    }

}

