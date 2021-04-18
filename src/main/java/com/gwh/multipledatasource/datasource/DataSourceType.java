package com.gwh.multipledatasource.datasource;

public class DataSourceType {

	// 如果要添加新的数据源 再枚举类型里面新增即可
	public enum DataBaseType {
		TEST11, TEST13
	}

	// 使用ThreadLocal保证线程安全
	private static final ThreadLocal<DataBaseType> TYPE = new ThreadLocal<DataBaseType>();

	// 往当前线程里设置数据源类型
	public static void setDataBaseType(DataBaseType dataBaseType) {
		if (dataBaseType == null) {
			throw new NullPointerException();
		}
		System.err.println("[将当前数据源改为]：" + dataBaseType);
		TYPE.set(dataBaseType);
	}

	// 获取数据源类型
	public static DataBaseType getDataBaseType() {
		// 如果获取的数据源为空 则默认为 测试系统
		DataBaseType dataBaseType = TYPE.get() == null ? DataBaseType.TEST11 : TYPE.get();
		System.err.println("[获取当前数据源的类型为]：" + dataBaseType);
		return dataBaseType;
	}

	// 清空数据类型
	public static void clearDataBaseType() {
		TYPE.remove();
	}

}
