package com.gwh.multipledatasource.mapper;

import com.gwh.multipledatasource.pojo.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 客户迁移
 */
@Mapper
public interface CustomerMapper {

    List<Customer> test11_queryCustomer(@Param("startDate") String startDate,@Param("endDate") String endDate);

    int test13_insertCustomer(List<Customer> list);

}
