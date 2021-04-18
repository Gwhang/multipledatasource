package com.gwh.multipledatasource.datasource;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 配置切点表达式 如果有添加新的数据源，往后面新增即可
 */
@Aspect
@Component
public class DataSourceAop {

	private static final Logger logger = LoggerFactory.getLogger(DataSourceAop.class);

	@Before("execution(* com.gwh.multipledatasource.mapper..*.test11*(..))")
	public void setDataSource2test11() {
		logger.info("测试系统数据源");
		DataSourceType.setDataBaseType(DataSourceType.DataBaseType.TEST11);
	}

	@Before("execution(* com.gwh.multipledatasource.mapper..*.test13*(..))")
	public void setDataSource2test13() {
		logger.info("生产系统数据源");
		DataSourceType.setDataBaseType(DataSourceType.DataBaseType.TEST13);
	}

}
