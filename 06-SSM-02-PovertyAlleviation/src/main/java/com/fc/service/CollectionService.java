package com.fc.service;

import com.fc.entity.Collection;
import com.fc.vo.ResultVO;

public interface CollectionService {
    ResultVO getList(Integer pageNum, Integer pageSize, Collection collection);

    ResultVO add(Collection collection);

    ResultVO update(Collection collection);

    ResultVO delete(Long id);
}
