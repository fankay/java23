package com.kaishengit.util;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtil {

	/**
	 * SqlSessionFactory 静态，整个系统中运行时只有一份(单例)
	 */
	private static SqlSessionFactory sessionFactory = builderSessionFactory();

	private static SqlSessionFactory builderSessionFactory() {
		try {
			//1. 加载配置文件
			Reader reader = Resources.getResourceAsReader("mybatis.xml");
			//2. 创建SqlSessionFactory
			SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
			return sqlSessionFactoryBuilder.build(reader);
		} catch (IOException e) {
			throw new RuntimeException("读取MyBaits配置文件异常",e);
		}
	}
	
	/**
	 * 获取SqlSessionFactory
	 * @return
	 */
	public static SqlSessionFactory getSqlSessionFactory() {
		return sessionFactory;
	}
	
	/**
	 * 获取SqlSession
	 * @return
	 */
	public static SqlSession getSqlSession() {
		return getSqlSessionFactory().openSession();
	}
	
}
