package com.fc.service;

import com.fc.entity.VolunteerRecruitment;
import com.fc.vo.ResultVO;

import java.util.Date;

public interface VolunteerService {
    ResultVO getList(Integer pageNum, Integer pageSize, VolunteerRecruitment volunteer);

    ResultVO add(VolunteerRecruitment volunteerRecruitment);

    ResultVO update(VolunteerRecruitment volunteerRecruitment);

    ResultVO delete(Long id);

    ResultVO click(Long id, Date lastClickTime);
}
