package com.fc.service.impl;

import com.fc.dao.PoorMapper;
import com.fc.entity.Poor;
import com.fc.entity.PoorExample;
import com.fc.entity.PoorWithBLOBs;
import com.fc.service.PoorService;
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
public class PoorServiceImpl implements PoorService {
    @Autowired
    private PoorMapper poorMapper;

    @Override
    public ResultVO click(Long id, Date lastClickTime) {
        if (lastClickTime == null) {
            lastClickTime = new Date();
        }
        int affectedRows = poorMapper.click(id, lastClickTime);
        if (affectedRows < 1)
            return new ResultVO(-100,"点击失败",  false, null);

        return new ResultVO(200,"点击成功", true,  null);
    }

    @Override
    public ResultVO getList(Integer pageNum, Integer pageSize, PoorWithBLOBs poor) {
        try {
            PoorExample example = null;
            if (poor != null) {
                example = new PoorExample();
                PoorExample.Criteria criteria = example.createCriteria();
                if (poor.getId() != null) {
                    criteria.andIdEqualTo(poor.getId());
                    click(poor.getId(), poor.getLastClickTime());
                }
                if (poor.getSn() != null) {
                    criteria.andSnEqualTo(poor.getSn());
                }
                if (poor.getMember() != null) {
                    criteria.andMemberLike("%" + poor.getMember() + "%");
                }
                if (poor.getAddress() != null) {
                    criteria.andAddressLike("%" + poor.getAddress() + "%");
                }
                if (poor.getUsername() != null) {
                    criteria.andUsernameLike("%" + poor.getUsername() + "%");
                }
                if (poor.getName() != null) {
                    criteria.andNameLike("%" + poor.getName() + "%");
                }
                if (poor.getAudit() != null) {
                    criteria.andAuditEqualTo(poor.getAudit());
                }
            }
            pageNum = Math.max(pageNum, 1);
            pageSize = Math.max(pageSize, 1);
            PageHelper.startPage(pageNum, pageSize);
            List<PoorWithBLOBs> list = poorMapper.selectByExampleWithBLOBs(example);
            PageInfo<PoorWithBLOBs> pageInfo = new PageInfo<>(list);
            DataVO<PoorWithBLOBs> data = new DataVO<>(pageInfo.getTotal(), list, pageNum, pageSize);
            return new ResultVO(200, "OK",true,  data);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultVO(-400, "fail",false,  null);
        }

    }


    @Override
    public ResultVO add(PoorWithBLOBs poor) {
        if (poor.getCreateTime() == null) {
            poor.setCreateTime(new Date());
        }

        // 刚刚创建出来的扶贫政策点击量应该为0
        poor.setClickNum(0);
        poor.setLastClickTime(null);

        int affectedRows = poorMapper.insert(poor);

        ResultVO resultVO;

        if (affectedRows > 0) {
            resultVO = new ResultVO(200, "贫困户添加成功", true, poor);
        } else {
            resultVO = new ResultVO(-1000, "贫困户添加失败", false, null);
        }
        return resultVO;
    }

    @Override
    public ResultVO update(PoorWithBLOBs poor) {
        int affectedRows = poorMapper.updateByPrimaryKeySelective(poor);

        ResultVO resultVO;

        if (affectedRows > 0) {
            Poor result = poorMapper.selectByPrimaryKey(poor.getId());

            resultVO = new ResultVO(200, "Ok", true, result);
        } else {
            resultVO = new ResultVO(-1000, "fail", true, null);
        }
        return resultVO;
    }

    @Override
    public ResultVO delete(Long id) {
        int affectedRows = poorMapper.deleteByPrimaryKey(id);

        ResultVO resultVO;

        if (affectedRows > 0) {
            resultVO = new ResultVO(200, "OK", true, null);
        } else {
            resultVO = new ResultVO(-1000, "fail", false, null);
        }
        return resultVO;
    }


}
