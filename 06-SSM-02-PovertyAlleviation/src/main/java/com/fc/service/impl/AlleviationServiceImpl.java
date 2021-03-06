package com.fc.service.impl;

import com.fc.dao.AlleviationMapper;
import com.fc.entity.Alleviation;
import com.fc.entity.AlleviationExample;
import com.fc.service.AlleviationService;
import com.fc.vo.DataVO;
import com.fc.vo.ResultVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AlleviationServiceImpl implements AlleviationService {
    @Autowired
     private AlleviationMapper alleviationMapper;

//         ResultVO resultVO;

//        try{
//            if (id == null){
//                PageHelper.startPage(pageNum,pageSize);
//
//                alleviations= alleviationMapper.selectByExample(null);
//            }else {
//                Alleviation alleviation = alleviationMapper.selectByPrimaryKey(id);
//                alleviations = new ArrayList<>();
//                alleviations.add(alleviation);
//
//                //如果调用了findById，应该对点击量进行加1操作
//                click(alleviation.getId(),null);
//            }
//
//            PageInfo<Alleviation> pageInfo = new PageInfo<>(alleviations);
//
//            DataVO<Alleviation> dataVO = new DataVO<>(pageInfo.getTotal(),alleviations,pageNum,pageSize);
//
//            resultVO = new ResultVO(200,"扶贫政策获取成功",true,dataVO);
//        }catch (Exception e){
//            e.printStackTrace();
//            resultVO = new ResultVO(-1000,"扶贫政策获取失败",false,null);
//        }
//        return resultVO;
//   }

    @Override
    public ResultVO getlist(Integer pageNum, Integer pageSize, Alleviation alleviation) {
        try {
            List<Alleviation> list;
            AlleviationExample example = null;
            if (alleviation != null) {
                example = new AlleviationExample();
                AlleviationExample.Criteria criteria = example.createCriteria();
                if (alleviation.getId() != null) {
                    click(alleviation.getId(), alleviation.getLastClickTime());
                    criteria.andIdEqualTo(alleviation.getId());
                }
                if (alleviation.getTitle() != null)
                    criteria.andTitleLike("%" + alleviation.getTitle() + "%");

                if (alleviation.getType() != null)
                    criteria.andTypeLike("%" + alleviation.getType() + "%");

            }
            pageNum = Math.max(pageNum, 1);
            pageSize = Math.max(pageSize, 1);
            PageHelper.startPage(pageNum, pageSize);
            list = alleviationMapper.selectByExampleWithBLOBs(example);
            PageInfo<Alleviation> pageInfo = new PageInfo<>(list);
            DataVO<Alleviation> dataVo = new DataVO<>(pageInfo.getTotal(), list, pageNum, pageSize);
            return new ResultVO(200, "OK",true,  dataVo);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultVO(-400, "服务器发生异常,请联系管理员",false,  null);
        }

    }

    @Override
    public ResultVO add(Alleviation alleviation) {
        if (alleviation.getCreateTime() == null){
            alleviation.setCreateTime(new Date());
        }

        if (alleviation.getReleaseTime() == null){
            alleviation.setReleaseTime(null);
        }

        //刚刚创建出来的扶贫政策点击量应该为0
        alleviation.setClickNum(0);
        alleviation.setLastClickTime(null);

        int affectedRows = alleviationMapper.insertSelective(alleviation);

        ResultVO resultVO;

        if (affectedRows > 0){
            resultVO = new ResultVO(200,"扶贫政策添加成功",true,alleviation);
        }else {
            resultVO = new ResultVO(-1000,"扶贫政策添加失败",false,null);
        }
        return resultVO;
    }

    @Override
    public ResultVO update(Alleviation alleviation) {
        int affectedRows = alleviationMapper.updateByPrimaryKeySelective(alleviation);

        ResultVO resultVO;

        if (affectedRows > 0){
            Alleviation result = alleviationMapper.selectByPrimaryKey(alleviation.getId());

            resultVO = new ResultVO(200,"扶贫政策修改成功",true,result);
        }else {
            resultVO = new ResultVO(-1000,"扶贫政策修改失败", true,null);
        }
        return resultVO;
    }

    @Override
    public ResultVO delete(Long id) {
        int affectedRows = alleviationMapper.deleteByPrimaryKey(id);

        ResultVO resultVO;

        if (affectedRows > 0){
            resultVO = new ResultVO(200,"扶贫政策删除成功",true,null);
        }else {
            resultVO = new ResultVO(-1000,"扶贫政策删除失败",false,null);
        }
        return resultVO;
    }

    @Override
    public ResultVO click(Long id, Date lastClickTime) {
        if (lastClickTime == null){
            lastClickTime = new Date();
        }

        Integer affectedRows = alleviationMapper.click(id,lastClickTime);

        ResultVO resultVO;

        if (affectedRows > 0){
            resultVO = new ResultVO(200, "点击量+1", true, null);
        }else {
            resultVO = new ResultVO(-1000, "点击失败", false, null);
        }
        return resultVO;
    }
}
