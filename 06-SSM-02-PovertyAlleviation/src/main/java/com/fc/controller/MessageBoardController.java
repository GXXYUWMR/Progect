package com.fc.controller;

import com.fc.entity.MessageBoard;
import com.fc.entity.MessageBoardWithBLOBs;
import com.fc.service.MessageBoardService;
import com.fc.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//留言板
@RestController
@RequestMapping("msgboard")

public class MessageBoardController {
    @Autowired
    private MessageBoardService messageBoardService;

    @GetMapping("getlist")
    public ResultVO getList(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                            @RequestParam(value = "pageSize",defaultValue = "3") Integer pageSize,
                            MessageBoardWithBLOBs message){
        return messageBoardService.getList(pageNum,pageSize,message);
    }

    @PostMapping("add")
    public ResultVO add(@RequestBody MessageBoardWithBLOBs messageBoard){
        return messageBoardService.add(messageBoard);
    }

    @PostMapping("update")
    public ResultVO update(@RequestBody MessageBoardWithBLOBs messageBoard){
        return messageBoardService.update(messageBoard);
    }

    @GetMapping("delete")
    public ResultVO delete(Long id){
        return messageBoardService.delete(id);
    }
}
