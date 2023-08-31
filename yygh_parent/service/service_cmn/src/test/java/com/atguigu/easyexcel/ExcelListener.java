package com.atguigu.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<UserData> {
    //read excel content line by line, start from the second line
    @Override
    public void invoke(UserData userData, AnalysisContext analysisContext) {
        System.out.println(userData);
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("table header info" + headMap);
    }
    // executed after loading
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
