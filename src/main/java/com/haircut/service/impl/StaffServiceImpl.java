package com.haircut.service.impl;

import com.haircut.dao.StaffDOMapper;
import com.haircut.dao.StaffStockDOMapper;
import com.haircut.dataobject.StaffDO;
import com.haircut.dataobject.StaffStockDO;
import com.haircut.service.StaffService;
import com.haircut.service.model.StaffModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffDOMapper staffDOMapper;

    @Autowired
    private StaffStockDOMapper staffStockDOMapper;

    private StaffDO convertStaffDOFromModel(StaffModel staffModel) {
        if (staffModel == null) {
            return null;
        }
        StaffDO staffDO = new StaffDO();
        BeanUtils.copyProperties(staffModel, staffDO);
        return staffDO;
    }

    private StaffStockDO convertStaffStockDOFromStaffModel(StaffModel staffModel) {
        if (staffModel == null) {
            return null;
        }
        StaffStockDO staffStockDO = new StaffStockDO();
        staffStockDO.setStaffId(staffModel.getId());
        staffStockDO.setStock(staffModel.getStock());
        return staffStockDO;
    }

    @Override
    @Transactional
    public StaffModel addStaff(StaffModel staffModel) {
        StaffDO staffDO = this.convertStaffDOFromModel(staffModel);
        staffDOMapper.insertSelective(staffDO);
        staffModel.setId(staffDO.getId());
        StaffStockDO staffStockDO = this.convertStaffStockDOFromStaffModel(staffModel);
        staffStockDOMapper.insertSelective(staffStockDO);
        return this.getStaffById(staffModel.getId());
    }

    @Override
    public List<StaffModel> listStaff() {
        List<StaffDO> staffDOList = staffDOMapper.listStaff();
        List<StaffModel> staffModelList = staffDOList.stream().map(staffDO -> {
            StaffStockDO staffStockDO = staffStockDOMapper.selectByStaffId(staffDO.getId());
            StaffModel staffModel = this.convertModelFromDataObject(staffDO, staffStockDO);
            return staffModel;
        }).collect(Collectors.toList());
        return staffModelList;
    }

    @Override
    public StaffModel getStaffById(Integer id) {
        StaffDO staffDO = staffDOMapper.selectByPrimaryKey(id);
        if (staffDO == null) {
            return null;
        }
        StaffStockDO staffStockDO = staffStockDOMapper.selectByStaffId(staffDO.getId());
        StaffModel staffModel = convertModelFromDataObject(staffDO, staffStockDO);
        return staffModel;
    }

    @Override
    @Transactional
    public boolean decreaseStock(Integer staffId) {
        int affectedRow = staffStockDOMapper.decreaseStock(staffId);
        return affectedRow > 0;
    }

    private StaffModel convertModelFromDataObject(StaffDO staffDO, StaffStockDO staffStockDO) {
        StaffModel staffModel = new StaffModel();
        BeanUtils.copyProperties(staffDO, staffModel);
        staffModel.setStock(staffStockDO.getStock());
        return staffModel;
    }
}
