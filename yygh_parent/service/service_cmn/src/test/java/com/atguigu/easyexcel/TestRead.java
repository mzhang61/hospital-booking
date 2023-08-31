package com.atguigu.easyexcel;

import com.alibaba.excel.EasyExcel;

public class TestRead {

    public static void main(String[] args) {
        // read file path
        String fileName = "/Users/mmm/Documents/webDevPrac/hospitalbooking/01.xlsx";

        //invoke method to read operation
        EasyExcel.read(fileName, UserData.class, new ExcelListener()).sheet().doRead();


    }
}
