package com.hexaware.techshop.dao;


	import java.util.List;

import com.hexaware.techshop.entity.*;

	public interface OrderDetailDAO {
	    void addOrderDetail(OrderDetail detail) throws Exception;
	    List<OrderDetail> getOrderDetailsByOrderId(int orderId) throws Exception;
	    void updateOrderDetail(OrderDetail detail) throws Exception;
	    void deleteOrderDetailsByOrderId(int orderId) throws Exception;
	}

