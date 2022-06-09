package com.haircut.controller;

import com.haircut.controller.viewobject.StaffVO;
import com.haircut.error.BusinessException;
import com.haircut.responce.CommonReturnType;
import com.haircut.service.StaffService;
import com.haircut.service.model.StaffModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller("staff")
@RequestMapping("/staff")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*", originPatterns = "*")
public class StaffController extends BaseController {

    @Autowired
    private StaffService staffService;

    @RequestMapping(value = "/add", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType addStaff(@RequestParam(name = "name") String name) {
        StaffModel staffModel = new StaffModel();
        staffModel.setName(name);
        staffModel.setStock(INITIAL_STOCK);
        StaffModel staffModelForReturn = staffService.addStaff(staffModel);
        StaffVO staffVO = convertVOFromModel(staffModelForReturn);
        return CommonReturnType.create(staffVO);
    }

    @RequestMapping(value = "/get", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getStaff(@RequestParam(name = "id") Integer id) {
        StaffModel staffModel = staffService.getStaffById(id);
        StaffVO staffVO = convertVOFromModel(staffModel);
        return CommonReturnType.create(staffVO);
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType listStaff() {
        List<StaffModel> staffModelList = staffService.listStaff();
        List<StaffVO> staffVOList = staffModelList.stream().map(staffModel -> {
            StaffVO staffVO = this.convertVOFromModel(staffModel);
            return staffVO;
        }).collect(Collectors.toList());
        return CommonReturnType.create(staffVOList);
    }

    private StaffVO convertVOFromModel(StaffModel staffModel) {
        if (staffModel == null) {
            return null;
        }
        StaffVO staffVO = new StaffVO();
        BeanUtils.copyProperties(staffModel, staffVO);
        return staffVO;
    }
}
