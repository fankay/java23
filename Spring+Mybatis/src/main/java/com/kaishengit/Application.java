package com.kaishengit;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.github.pagehelper.PageInterceptor;

@Configuration
@ComponentScan
@EnableTransactionManagement
@PropertySource("classpath:config.properties")
@MapperScan("com.kaishengit.mapper") //将包中的接口自动扫描并加入Spring容器中
public class Application {
	
	@Autowired
	private Environment environment;

	/**
	 * 数据库连接池
	 * @return
	 */
	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(environment.getProperty("jdbc.driver"));
		dataSource.setUrl(environment.getProperty("jdbc.url"));
		dataSource.setUsername(environment.getProperty("jdbc.username"));
		dataSource.setPassword(environment.getProperty("jdbc.password"));
		return dataSource;
	}
	
	/**
	 * 事务管理器
	 * @return
	 */
	@Bean
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}
	
	/**
	 * SqlSessionFactory
	 * @return
	 * @throws IOException
	 */
	@Bean
	public SqlSessionFactoryBean sqlSessionFactory() throws IOException {
		SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
		factory.setDataSource(dataSource()); //设置数据库连接池
		factory.setTypeAliasesPackage("com.kaishengit.entity"); //设置别名包
		
		//加载mapper文件夹中的mapper配置文件
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = resolver.getResources("classpath:mapper/*.xml");
		factory.setMapperLocations(resources);
		
		//设置下划线转驼峰命名法
		org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
		config.setMapUnderscoreToCamelCase(true);
		factory.setConfiguration(config);
		
		//设置分页插件
		PageInterceptor pageInterceptor = new PageInterceptor();
		Properties prop = new Properties();
		prop.setProperty("helperDialect", "mysql");
		pageInterceptor.setProperties(prop);
		
		factory.setPlugins(new Interceptor[]{pageInterceptor});
		
		return factory;
	}
	

	
	
}
