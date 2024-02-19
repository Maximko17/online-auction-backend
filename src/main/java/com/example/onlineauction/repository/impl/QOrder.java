package com.example.onlineauction.repository.impl;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;

import java.util.ArrayList;
import java.util.List;

public class QOrder {
    private final List<OrderSpecifier<?>> predicates = new ArrayList<>();

    public static QOrder builder() {
        return new QOrder();
    }

    public <T extends Enum> QOrder add(T orderEnum, Path<?> expression) {
        if (orderEnum != null) {
            predicates.add(new OrderSpecifier(Order.valueOf(orderEnum.name()), expression));
        }
        return this;
    }

    public OrderSpecifier<?>[] build() {
        return predicates.toArray(new OrderSpecifier[0]);
    }
}
