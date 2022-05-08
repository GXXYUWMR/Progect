package com.fc.service;


import com.fc.entity.MessageBoardWithBLOBs;
import com.fc.vo.ResultVO;

public interface MessageBoardService {
    ResultVO getList(Integer pageNum, Integer pageSize, MessageBoardWithBLOBs message);

    ResultVO add(MessageBoardWithBLOBs messageBoard);

    ResultVO update(MessageBoardWithBLOBs messageBoard);

    ResultVO delete(Long id);
}