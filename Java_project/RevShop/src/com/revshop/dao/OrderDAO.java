package com.revshop.dao;

import com.revshop.model.Order;
import com.revshop.model.OrderItem;
import java.sql.Connection;
import java.util.List;

public interface OrderDAO {

    int createOrder(Connection conn, Order order);
    void addOrderItem(Connection conn, OrderItem item);
    List<Order> getOrdersByUser(int userId);
    List<OrderItem> getOrderItemsByOrderId(int orderId);
}

