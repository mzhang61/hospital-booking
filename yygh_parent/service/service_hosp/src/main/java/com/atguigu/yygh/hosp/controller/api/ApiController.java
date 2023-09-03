package com.atguigu.yygh.hosp.controller.api;

import com.atguigu.yygh.common.exception.YyghException;
import com.atguigu.yygh.common.helper.HttpRequestHelper;
import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.common.result.ResultCodeEnum;
import com.atguigu.yygh.common.utils.MD5;
import com.atguigu.yygh.hosp.service.DepartmentService;
import com.atguigu.yygh.hosp.service.HospitalService;
import com.atguigu.yygh.hosp.service.HospitalSetService;
import com.atguigu.yygh.model.hosp.Department;
import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.vo.hosp.DepartmentQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/hosp")
public class ApiController {

    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private HospitalSetService hospitalSetService;
    @Autowired
    private DepartmentService departmentService;

    //delete department
    @PostMapping("department/remove")
    public Result removeDepartment(HttpServletRequest request) {
        //get department info
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        String hoscode = (String)paramMap.get("hoscode");
        String depcode = (String)paramMap.get("depcode");

        departmentService.remove(hoscode,depcode);
        return Result.ok();
    }

    //check department
    @PostMapping("department/list")
    public Result findDepartment(HttpServletRequest request) {
        //get department info
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //get hoscode
        String hoscode = (String)paramMap.get("hoscode");
        int page = StringUtils.isEmpty(paramMap.get("hoscode")) ?1:Integer.parseInt((String)paramMap.get("hoscode"));
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ?1:Integer.parseInt((String)paramMap.get("limit"));
        //signKey check
        DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        departmentQueryVo.setHoscode(hoscode);
        //invoke service method
        Page<Department> pageModel = departmentService.findPageDepartment(page, limit, departmentQueryVo);
        return Result.ok(pageModel);

    }

    //upload department api
    @PostMapping("saveDepartment")
    public Result saveDepartment(HttpServletRequest request) {
        //get department info
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //get hospital hoscode
        String hoscode = (String)paramMap.get("hoscode");
        //1 get hospital signkey
        String hospSign = (String)paramMap.get("sign");
        //2 check signkey by hoscode
        String signKey = hospitalSetService.getSignKey(hoscode);
        //3 encrypt signKey
        String signKeyMd5 = MD5.encrypt(signKey);
        //4 check signKey is the same
        if(!hospSign.equals(signKeyMd5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        //invoke service method
        departmentService.save(paramMap);
        return Result.ok();
    }

    //check hospital
    @PostMapping("hospital/show")
    public Result getHospital(HttpServletRequest request) {
        //get hospital info
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //get hospital hoscode
        String hoscode = (String)paramMap.get("hoscode");
        //1 get hospital signkey
        String hospSign = (String)paramMap.get("sign");
        //2 check signkey by hoscode
        String signKey = hospitalSetService.getSignKey(hoscode);
        //3 encrypt signKey
        String signKeyMd5 = MD5.encrypt(signKey);
        //4 check signKey is the same
        if(!hospSign.equals(signKeyMd5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        //invoke service method to check by hoscode
        Hospital hospital = hospitalService.getByHoscode(hoscode);
        return Result.ok(hospital);
    }
    
    //upload hospital api
    @PostMapping("saveHospital")
    public Result saveHosp(HttpServletRequest request) {
        //get hospital data
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //1 get hospital signkey
        String hospSign = (String)paramMap.get("sign");
        //2 check signkey by hoscode
        String hoscode = (String)paramMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hoscode);
        //3 encrypt signKey
        String signKeyMd5 = MD5.encrypt(signKey);
        //4 check signKey is the same
        if(!hospSign.equals(signKeyMd5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        //
        String logoData = (String)paramMap.get("logoData");
        logoData = logoData.replaceAll(" ", "+");
        paramMap.put("logoData", logoData);
        //invoke service method
        hospitalService.save(paramMap);
        return Result.ok();

    }

}
