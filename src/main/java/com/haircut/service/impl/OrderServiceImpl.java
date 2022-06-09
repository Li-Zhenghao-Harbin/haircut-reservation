package com.haircut.service.impl;

import com.haircut.dao.OrderDOMapper;
import com.haircut.dao.SequenceDOMapper;
import com.haircut.dataobject.OrderDO;
import com.haircut.dataobject.SequenceDO;
import com.haircut.dataobject.StaffDO;
import com.haircut.dataobject.StaffStockDO;
import com.haircut.error.BusinessException;
import com.haircut.error.EmBusinessError;
import com.haircut.service.OrderService;
import com.haircut.service.StaffService;
import com.haircut.service.UserService;
import com.haircut.service.model.OrderModel;
import com.haircut.service.model.StaffModel;
import com.haircut.service.model.UserModel;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private StaffService staffService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDOMapper orderDOMapper;

    @Autowired
    private SequenceDOMapper sequenceDOMapper;

    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer staffId) throws BusinessException {
        //校验
        StaffModel staffModel = staffService.getStaffById(staffId);
        if (staffModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "理发师信息不存在");
        }
        UserModel userModel = userService.getUserById(userId);
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "用户信息不存在");
        }
        //落单减库存
        boolean result = staffService.decreaseStock(staffId);
        if (!result) {
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }

        //订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setStaffId(staffId);

        //生成交易流水号
        orderModel.setId(generateOrderNo());
        OrderDO orderDO = convertFromOrderModel(orderModel);
        orderDOMapper.insertSelective(orderDO);

        //返回前端
        return orderModel;
    }

    @Override
    public List<OrderModel> listOrder() {
//        List<StaffDO> staffDOList = staffDOMapper.listStaff();
//        List<StaffModel> staffModelList = staffDOList.stream().map(staffDO -> {
//            StaffStockDO staffStockDO = staffStockDOMapper.selectByStaffId(staffDO.getId());
//            StaffModel staffModel = this.convertModelFromDataObject(staffDO, staffStockDO);
//            return staffModel;
//        }).collect(Collectors.toList());
//        return staffModelList;
        List<OrderDO> orderDOList = orderDOMapper.listOrder();
        List<OrderModel> orderModelList = orderDOList.stream().map(orderDO -> {
            OrderModel orderModel = this.convertModelFromDataObject(orderDO);
            return orderModel;
        }).collect(Collectors.toList());
        return orderModelList;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private String generateOrderNo() {
        StringBuilder stringBuilder = new StringBuilder();
        //前8位 时间信息
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-","");
        stringBuilder.append(nowDate);
        //后6位 自增序列
        int sequence = 0;
        SequenceDO sequenceDO = sequenceDOMapper.getSequenceByName("order_info");
        sequence = sequenceDO.getCurrentValue();
        sequenceDO.setCurrentValue(sequenceDO.getCurrentValue() + sequenceDO.getStep());
        sequenceDOMapper.updateByPrimaryKeySelective(sequenceDO);
        String sequenceStr = String.valueOf(sequence);
        for (int i = 0; i < 6 - sequenceStr.length(); i++) {
            stringBuilder.append("0");
        }
        stringBuilder.append(sequenceStr);
        return stringBuilder.toString();
    }

    private OrderDO convertFromOrderModel(OrderModel orderModel) {
        if (orderModel == null) {
            return null;
        }
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel, orderDO);
        return orderDO;
    }

    private OrderModel convertModelFromDataObject(OrderDO orderDO) {
        OrderModel orderModel = new OrderModel();
        BeanUtils.copyProperties(orderDO, orderModel);
        return orderModel;
    }
}
