package com.gwh.multipledatasource.controller;

import com.gwh.multipledatasource.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * 客户信息迁移
 */
@Controller
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     *  迁移客户信息
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param pageSize 每次迁移的条数
     * @return
     */
    @RequestMapping("/execut")
    @ResponseBody
    public HashMap<String,String> customerData(String startDate, String endDate, int pageSize){
        HashMap<String,String> resultMap=new HashMap<String,String>();

        if (pageSize <= 0){
            resultMap.put("code","E");
            resultMap.put("msg","迁移的条数小于等于0，执行结束");
        }else{
             resultMap =  customerService.customerData(startDate,endDate,pageSize);
        }

        return resultMap;
    }

}
