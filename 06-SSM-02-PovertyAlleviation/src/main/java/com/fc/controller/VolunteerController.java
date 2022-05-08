package com.fc.controller;


import com.fc.entity.VolunteerRecruitment;
import com.fc.service.VolunteerService;
import com.fc.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//志愿者
@RestController
@RequestMapping("volunteer")

public class VolunteerController {
    @Autowired
    private VolunteerService volunteerService;

    @GetMapping("getlist")
    public ResultVO getList(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                            @RequestParam(value = "pageSize",defaultValue = "3") Integer pageSize,
                            VolunteerRecruitment volunteer){
        return volunteerService.getList(pageNum,pageSize,volunteer);
    }

    @PostMapping("add")
    public ResultVO add(@RequestBody VolunteerRecruitment volunteerRecruitment){
        return volunteerService.add(volunteerRecruitment);
    }

    @PostMapping("update")
    public ResultVO update(@RequestBody VolunteerRecruitment volunteerRecruitment){
        return volunteerService.update(volunteerRecruitment);
    }

    @GetMapping("delete")
    public ResultVO delete(Long id){
        return volunteerService.delete(id);
    }

    @PostMapping("click")
    public ResultVO click(@RequestBody VolunteerRecruitment volunteer){
        return volunteerService.click(volunteer.getId(),volunteer.getLastClickTime());
    }
}
