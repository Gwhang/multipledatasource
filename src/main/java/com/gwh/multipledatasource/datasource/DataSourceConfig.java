package com.gwh.multipledatasource.datasource;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * 数据源配置
 */
@Configuration
@MapperScan(basePackages = "com.gwh.multipledatasource.mapper", sqlSessionFactoryRef = "SqlSessionFactory")
public class DataSourceConfig {

	/**
	 * 测试系统数据库
	 * @return
	 */
	@Primary//设置默认数据库
	@Bean(name = "test11DataSource")
	@ConfigurationProperties(prefix = "spring.datasource.test11")
	public DataSource test11DataSource() {
		return DataSourceBuilder.create().build();
	}

	/**
	 * 生产系统数据库
	 * @return
	 */
	@Bean(name = "test13DataSource")
	@ConfigurationProperties(prefix = "spring.datasource.test13")
	public DataSource test13DataSource() {
		return DataSourceBuilder.create().build();
	}

	/**
	 *  设置所有数据源
	 * @param test11DataSource
	 * @param test13DataSource
	 * @return
	 */
	@Bean(name = "dynamicDataSource")
	public DynamicDataSource DataSource(@Qualifier("test11DataSource") DataSource test11DataSource,
			@Qualifier("test13DataSource") DataSource test13DataSource){
		Map<Object, Object> targetDataSource = new HashMap<>();
		targetDataSource.put(DataSourceType.DataBaseType.TEST11, test11DataSource);
		targetDataSource.put(DataSourceType.DataBaseType.TEST13, test13DataSource);
		DynamicDataSource dataSource = new DynamicDataSource();
		dataSource.setTargetDataSources(targetDataSource);
		// 设置默认数据源
		dataSource.setDefaultTargetDataSource(test11DataSource);
		return dataSource;
	}

	/**
	 * 数据源不同，读取不同的SQL文件
	 * @param dynamicDataSource
	 * @return
	 * @throws Exception
	 */
	@Bean(name = "SqlSessionFactory")
	public SqlSessionFactory test1SqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dynamicDataSource)
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dynamicDataSource);
		bean.setMapperLocations(
				new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/*.xml"));
		return bean.getObject();
	}

}
