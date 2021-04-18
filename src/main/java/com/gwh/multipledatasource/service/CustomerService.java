package com.gwh.multipledatasource.service;

import com.gwh.multipledatasource.mapper.CustomerMapper;
import com.gwh.multipledatasource.pojo.Customer;
import com.gwh.multipledatasource.utils.AesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 客户迁移
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 迁移客户数据
     * @param startDate
     * @param endDate
     * @param pageSize
     * @return
     */
    public HashMap<String ,String> customerData(String startDate, String endDate, int pageSize){
        HashMap<String,String> resultMap = new HashMap<String,String>();
        long startGet = System.currentTimeMillis();
        System.out.println("开始迁移数据库");
        List<Customer> customerList = this.customerMapper.test11_queryCustomer(startDate,endDate);
        if(customerList.isEmpty()){
            System.out.println("迁移数据为空，执行结束");
            resultMap.put("code","E");
            resultMap.put("msg","迁移数据为空");
        }else{
            //对客户信息进行加密处理
            customerList.stream().forEach(p ->{
                p.setCustomerTel(AesUtil.encryptBase64(p.getCustomerTel()==null?"":p.getCustomerTel()));
                p.setIdNumber(AesUtil.encryptBase64(p.getIdNumber()==null?"":p.getIdNumber()));
            });
            System.out.println("查询耗时"+(new SimpleDateFormat("ss:SSS")).format(new Date((System.currentTimeMillis() - startGet)))+" s");
            int index = 0;
            if (customerList.size() % pageSize == 0) {
                index = customerList.size() / pageSize;
            }else {
                index = customerList.size() / pageSize + 1;
            }
            int successRows = 0;
            long start = System.currentTimeMillis();
            for (int i=0;i< index;i++){
                long start_i = System.currentTimeMillis();
                //stream流表达式，skip表示跳过前i*10000条记录，limit表示读取当前流的前10000条记录 经测试使用skip 数据迁移查询不会重复
                int rows = this.customerMapper.test13_insertCustomer(customerList.stream().skip(i * pageSize).limit(pageSize).collect(Collectors.toList()));
                successRows += rows;
                System.out.println("第"+i+"次插入数据行数： " + rows+", 耗时： " + (new SimpleDateFormat("ss:SSS")).format(new Date((System.currentTimeMillis() - start_i))));
            }
            System.out.println("插入数据总行数： " + successRows+", 耗时： " + (new SimpleDateFormat("ss:SSS")).format(new Date((System.currentTimeMillis() - start))));
            resultMap.put("code", "S");
            resultMap.put("msg","数据迁移成功");
        }

        return resultMap;
    }

}
