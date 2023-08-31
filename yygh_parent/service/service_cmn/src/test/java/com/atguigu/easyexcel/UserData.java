package com.atguigu.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class UserData {

    @ExcelProperty(value = "user id", index = 0)
    private int uid;

    @ExcelProperty(value = "user name", index = 1)
    private String username;
}
