package com.atguigu.easyexcel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestWrite {

    public static void main(String[] args) {
        // construct data list
        List<UserData> list = new ArrayList();
        for (int i = 0; i < 10; i++) {
            UserData data = new UserData();
            data.setUid(i);
            data.setUsername("lucy" + i);
            list.add(data);
        }
        // excel path and name
        String fileName = "/Users/mmm/Documents/webDevPrac/hospitalbooking/01.xlsx";

        // invoke method
        EasyExcel.write(fileName, UserData.class).sheet("user info")
                .doWrite(list);
    }
}
