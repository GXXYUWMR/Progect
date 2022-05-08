package com.fc.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginVo {
    private Long id;
    private String username;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GWT+8")
    private Date lastAccessTime;
}
