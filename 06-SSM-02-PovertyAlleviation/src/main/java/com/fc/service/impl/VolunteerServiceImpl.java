package com.fc.service.impl;

import com.fc.dao.VolunteerRecruitmentMapper;
import com.fc.entity.User;
import com.fc.entity.VolunteerRecruitment;
import com.fc.entity.VolunteerRecruitmentExample;
import com.fc.service.VolunteerService;
import com.fc.vo.DataVO;
import com.fc.vo.ResultVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VolunteerServiceImpl implements VolunteerService {
    @Autowired
    private VolunteerRecruitmentMapper volunteerMapper;

    @Override
    public ResultVO getList(Integer pageNum, Integer pageSize, VolunteerRecruitment volunteer) {
//        List<VolunteerRecruitment> volunteerRecruitments;
//
//        ResultVO resultVO;
//
//        try{
//            if (id == null){
//                PageHelper.startPage(pageNum,pageSize);
//
//                volunteerRecruitments = volunteerRecruitmentMapper.selectByExample(null);
//            }else {
//                VolunteerRecruitment volunteerRecruitment = volunteerRecruitmentMapper.selectByPrimaryKey(id);
//                volunteerRecruitments = new ArrayList<>();
//                volunteerRecruitments.add(volunteerRecruitment);
//            }
//
//            PageInfo<VolunteerRecruitment> pageInfo = new PageInfo<>(volunteerRecruitments);
//
//            DataVO<VolunteerRecruitment> dataVO = new DataVO<>(pageInfo.getTotal(),volunteerRecruitments,pageNum,pageSize);
//
//            resultVO = new ResultVO(200,"OK",true,dataVO);
//        }catch (Exception e){
//            resultVO = new ResultVO(-1000,"fail",false,null);
//        }
//        return resultVO;
//    }
        try {
            VolunteerRecruitmentExample example = null;
            if (volunteer != null) {
                example = new VolunteerRecruitmentExample();
                VolunteerRecruitmentExample.Criteria criteria = example.createCriteria();
                if (volunteer.getId() != null) {
                    criteria.andIdEqualTo(volunteer.getId());
                    click(volunteer.getId(), volunteer.getLastClickTime());
                }
                if (volunteer.getTotal() != null) {
                    criteria.andTotalLike("%" + volunteer.getTotal() + "%");
                }
                if (volunteer.getPosition() != null) {
                    criteria.andTotalLike("%" + volunteer.getPosition() + "%");
                }
                if (volunteer.getWorkRequire() != null) {
                    criteria.andWorkPlaceLike("%" + volunteer.getWorkRequire() + "%");
                }
                if (volunteer.getWorkSalary() != null) {
                    criteria.andWorkSalaryLike("%" + volunteer.getWorkSalary() + "%");
                }
                if (volunteer.getWorkPlace() != null) {
                    criteria.andWorkPlaceLike("%" + volunteer.getWorkPlace() + "%");
                }
                if (volunteer.getRecruitsNum() != null) {
                    criteria.andRecruitsNumEqualTo(volunteer.getRecruitsNum());
                }
                if (volunteer.getLead() != null) {
                    criteria.andLeadLike("%" + volunteer.getLead() + "%");
                }
            }
            pageNum = Math.max(pageNum, 1);
            pageSize = Math.max(pageSize, 1);
            PageHelper.startPage(pageNum, pageSize);
            List<VolunteerRecruitment> list = volunteerMapper.selectByExampleWithBLOBs(example);
            PageInfo<VolunteerRecruitment> pageInfo = new PageInfo<>(list);
            DataVO<VolunteerRecruitment> dataVo = new DataVO<>(pageInfo.getTotal(), list, pageNum, pageSize);
            return new ResultVO(200, "OK", true, dataVo);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultVO(-400, "fail", false, null);
        }
    }

    @Override
    public ResultVO add(VolunteerRecruitment volunteerRecruitment) {
        if (volunteerRecruitment.getCreateTime() == null){
            volunteerRecruitment.setCreateTime(new Date());
        }

        int affectedRows = volunteerMapper.insertSelective(volunteerRecruitment);

        ResultVO resultVO;

        if (affectedRows > 0){
            resultVO = new ResultVO(200,"OK",true,volunteerRecruitment);
        }else {
            resultVO = new ResultVO(-1000,"fail",false,null);
        }
        return resultVO;
    }

    @Override
    public ResultVO update(VolunteerRecruitment volunteerRecruitment) {
        int affectedRows = volunteerMapper.updateByPrimaryKeySelective(volunteerRecruitment);

        ResultVO resultVO;

        if (affectedRows > 0){
            VolunteerRecruitment result = volunteerMapper.selectByPrimaryKey(volunteerRecruitment.getId());

            resultVO = new ResultVO(200,"Ok",true,result);
        }else {
            resultVO = new ResultVO(-1000,"fail", true,null);
        }
        return resultVO;
    }

    @Override
    public ResultVO delete(Long id) {
        int affectedRows = volunteerMapper.deleteByPrimaryKey(id);

        ResultVO resultVO;

        if (affectedRows > 0){
            resultVO = new ResultVO(200,"OK",true,null);
        }else {
            resultVO = new ResultVO(-1000,"fail",false,null);
        }
        return resultVO;
    }

    @Override
    public ResultVO click(Long id, Date lastClickTime) {
        if (lastClickTime == null) {
            lastClickTime = new Date();
        }
        if (volunteerMapper.click(id, lastClickTime) < 1) {
            return new ResultVO(-100,"数据库中无此id", false,  null);
        }
        VolunteerRecruitment data = volunteerMapper.selectByPrimaryKey(id);
        return new ResultVO(200, "OK",true,  data);
    }
}
