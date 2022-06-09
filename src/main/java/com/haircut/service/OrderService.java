package com.haircut.service;

import com.haircut.error.BusinessException;
import com.haircut.service.model.OrderModel;
import com.haircut.service.model.StaffModel;

import java.util.List;

public interface OrderService {
    OrderModel createOrder(Integer userId, Integer staffId) throws BusinessException;
    List<OrderModel> listOrder();
}
