package com.fc.service.impl;

import com.fc.dao.MessageBoardMapper;
import com.fc.entity.MessageBoard;
import com.fc.entity.MessageBoardExample;
import com.fc.entity.MessageBoardWithBLOBs;
import com.fc.service.MessageBoardService;
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
public class MessageBoardServiceImpl implements MessageBoardService {
    @Autowired
    private MessageBoardMapper messageBoardMapper;

    @Override
    public ResultVO getList(Integer pageNum, Integer pageSize,MessageBoardWithBLOBs message) {
//        List<MessageBoard> messageBoards;
//
//        ResultVO resultVO;
//
//        try{
//            if (id == null){
//                PageHelper.startPage(pageNum,pageSize);
//
//                messageBoards = messageBoardMapper.selectByExample(null);
//            }else {
//                MessageBoard messageBoard = messageBoardMapper.selectByPrimaryKey(id);
//                messageBoards = new ArrayList<>();
//                messageBoards.add(messageBoard);
//            }
//
//            PageInfo<MessageBoard> pageInfo = new PageInfo<>(messageBoards);
//
//            DataVO<MessageBoard> dataVO = new DataVO<>(pageInfo.getTotal(),messageBoards,pageNum,pageSize);
//
//            resultVO = new ResultVO(200,"OK",true,dataVO);
//        }catch (Exception e){
//            resultVO = new ResultVO(-1000,"fail",false,null);
//        }
//        return resultVO;
//    }

        try {
            MessageBoardExample example = null;
            if (message != null) {
                example = new MessageBoardExample();
                MessageBoardExample.Criteria criteria = example.createCriteria();
                if (message.getId() != null) {
                    criteria.andIdEqualTo(message.getId());
                }
                if (message.getUserId() != null) {
                    criteria.andUserIdEqualTo(message.getUserId());
                }
                if (message.getUsername() != null) {
                    criteria.andUsernameLike("%" + message.getUsername() + "%");
                }
//                if (message.getReply() != null) {
//                    criteria.andReplyLike("%" + message.getReply() + "%");
//                }
            }
            pageNum = Math.max(pageNum, 1);
            pageSize = Math.max(pageSize, 1);
            PageHelper.startPage(pageNum, pageSize);
            List<MessageBoardWithBLOBs> list = messageBoardMapper.selectByExampleWithBLOBs(example);
            PageInfo<MessageBoardWithBLOBs> pageInfo = new PageInfo<>(list);
            DataVO<MessageBoardWithBLOBs> data = new DataVO<>(pageInfo.getTotal(), list, pageNum, pageSize);
            return new ResultVO(200, "OK", true, data);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultVO(-400, "fail", true, null);
        }

    }

    @Override
    public ResultVO add(MessageBoardWithBLOBs messageBoard) {
        if (messageBoard.getCreateTime() == null){
            messageBoard.setCreateTime(new Date());
        }

        int affectedRows = messageBoardMapper.insertSelective(messageBoard);

        ResultVO resultVO;

        if (affectedRows > 0){
            resultVO = new ResultVO(200,"OK",true,messageBoard);
        }else {
            resultVO = new ResultVO(-1000,"fail",false,null);
        }
        return resultVO;
    }

    @Override
    public ResultVO update(MessageBoardWithBLOBs messageBoard) {
        int affectedRows = messageBoardMapper.updateByPrimaryKeySelective(messageBoard);

        ResultVO resultVO;

        if (affectedRows > 0){
            MessageBoard result = messageBoardMapper.selectByPrimaryKey(messageBoard.getId());

            resultVO = new ResultVO(200,"Ok",true,result);
        }else {
            resultVO = new ResultVO(-1000,"fail", true,null);
        }
        return resultVO;
    }


    @Override
    public ResultVO delete(Long id) {
        int affectedRows = messageBoardMapper.deleteByPrimaryKey(id);

        ResultVO resultVO;

        if (affectedRows > 0){
            resultVO = new ResultVO(200,"OK",true,null);
        }else {
            resultVO = new ResultVO(-1000,"fail",false,null);
        }
        return resultVO;
    }
}
