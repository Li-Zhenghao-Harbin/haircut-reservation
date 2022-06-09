package com.haircut.service;

import com.haircut.error.BusinessException;
import com.haircut.service.model.StaffModel;

import java.util.List;

public interface StaffService {
    //添加理发师
    StaffModel addStaff(StaffModel staffModel);
    //理发师列表浏览
    List<StaffModel> listStaff();
    //理发师详情获取
    StaffModel getStaffById(Integer id);
    //库存扣减
    boolean decreaseStock(Integer staffId);
}
