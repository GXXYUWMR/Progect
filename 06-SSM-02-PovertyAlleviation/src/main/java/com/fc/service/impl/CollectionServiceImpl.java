package com.fc.service.impl;

import com.fc.dao.CollectionMapper;
import com.fc.entity.Collection;
import com.fc.entity.CollectionExample;
import com.fc.service.CollectionService;
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
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    private CollectionMapper collectionMapper;

    @Override
    public ResultVO getList(Integer pageNum, Integer pageSize, Collection collection) {
//        List<Collection> collections;
//
//        ResultVO resultVO;
//        try {
//            if (id == null) {
//                PageHelper.startPage(pageNum, pageSize);
//
//                collections = collectionMapper.selectByExample(null);
//            } else {
//                Collection collection = collectionMapper.selectByPrimaryKey(id);
//                collections = new ArrayList<>();
//                collections.add(collection);
//            }
//
//            PageInfo<Collection> pageInfo = new PageInfo<>(collections);
//
//            DataVO<Collection> dataVO = new DataVO<>(pageInfo.getTotal(), collections, pageNum, pageSize);
//
//            resultVO = new ResultVO(200, "收藏表获取成功", true, dataVO);
//        } catch (Exception e) {
//            e.printStackTrace();
//            resultVO = new ResultVO(-1000, "收藏表获取失败", false, null);
//        }
//        return resultVO;
//    }

        try {
            CollectionExample example = null;
            if (collection != null) {
                example = new CollectionExample();
                CollectionExample.Criteria criteria = example.createCriteria();
                if (collection.getId() != null) {
                    criteria.andIdEqualTo(collection.getId());
                }
                if (collection.getUserId() != null) {
                    criteria.andUserIdEqualTo(collection.getUserId());
                }
                if (collection.getTableName() != null) {
                    criteria.andTableNameLike("%" + collection.getTableName() + "%");
                }
                if (collection.getName() != null) {
                    criteria.andNameLike("%" + collection.getName() + "%");
                }
                if (collection.getType() != null) {
                    criteria.andTypeEqualTo(collection.getType());
                }
                if (collection.getRecommendType() != null) {
                    criteria.andRecommendTypeEqualTo(collection.getRecommendType());
                }
            }
            pageNum = Math.max(pageNum, 1);
            pageSize = Math.max(pageSize, 1);
            PageHelper.startPage(pageNum, pageSize);
            List<Collection> list = collectionMapper.selectByExample(example);
            PageInfo<Collection> pageInfo = new PageInfo<>(list);
            DataVO<Collection> data = new DataVO<>(pageInfo.getTotal(), list, pageNum, pageSize);
            return new ResultVO(200, "OK", true, data);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultVO(-400, "fail", true, null);
        }
    }

    @Override
    public ResultVO add(Collection collection) {
        if (collection.getCreateTime() == null) {
            collection.setCreateTime(new Date());
        }

        int affectedRows = collectionMapper.insertSelective(collection);

        ResultVO resultVO;

        if (affectedRows > 0) {
            resultVO = new ResultVO(200, "收藏表添加成功", true, collection);
        } else {
            resultVO = new ResultVO(-1000, "收藏表添加失败", false, null);
        }
        return resultVO;
    }

    @Override
    public ResultVO update(Collection collection) {
        int affectedRows = collectionMapper.updateByPrimaryKeySelective(collection);

        ResultVO resultVO;

        if (affectedRows > 0) {
            Collection result = collectionMapper.selectByPrimaryKey(collection.getId());

            resultVO = new ResultVO(200, "收藏表修改成功", true, result);
        } else {
            resultVO = new ResultVO(-1000, "收藏表修改失败", true, null);
        }
        return resultVO;
    }

    @Override
    public ResultVO delete(Long id) {
        int affectedRows = collectionMapper.deleteByPrimaryKey(id);

        ResultVO resultVO;

        if (affectedRows > 0) {
            resultVO = new ResultVO(200, "收藏表删除成功", true, null);
        } else {
            resultVO = new ResultVO(-1000, "收藏表删除成功", false, null);
        }
        return resultVO;
    }
}