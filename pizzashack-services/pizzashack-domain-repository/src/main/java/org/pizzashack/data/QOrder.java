package org.pizzashack.data;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QOrder is a Querydsl query type for Order
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QOrder extends EntityPathBase<Order> {

    private static final long serialVersionUID = -5732094;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QOrder order = new QOrder("order1");

    public final QCustomer customer;

    public final StringPath delivered = createString("delivered");

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final DateTimePath<java.util.Date> orderTime = createDateTime("orderTime", java.util.Date.class);

    public final StringPath pizzaType = createString("pizzaType");

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public QOrder(String variable) {
        this(Order.class, forVariable(variable), INITS);
    }

    public QOrder(Path<? extends Order> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QOrder(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QOrder(PathMetadata<?> metadata, PathInits inits) {
        this(Order.class, metadata, inits);
    }

    public QOrder(Class<? extends Order> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customer = inits.isInitialized("customer") ? new QCustomer(forProperty("customer")) : null;
    }

}

