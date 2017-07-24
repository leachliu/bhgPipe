package com.bhg.pipeServer.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.bhg.pipeServer.utils.SshRemoteParams;

@Configuration
@PropertySource("classpath:constant.properties")
public class AppConfig {
	/**
	 * 数据库存储的工作空间，用于存储和管理中间数据文件
	 */
	@Value("${WORK_SPACE}")
	private String WORK_SPACE;
	/**
	 * 文件读取时的BOM头设置信息
	 */
	@Value("${BOM_SIZE}")
	private int BOM_SIZE;

	/**
	 * 数据库所在服务器的数据导入临时存放文件目录
	 */
	@Value("${DATABASE_IMPORT_PATH_CH}")
	private String DATABASE_IMPORT_PATH_CH;
	@Value("${DATABASE_IMPORT_PATH_GP}")
	private String DATABASE_IMPORT_PATH_GP;

	@Value("${redis.host}")
	public String REDIS_HOST;

	@Value("${redis.port}")
	public int REDIS_PORT;

	@Value("${redis.password}")
	public String REDIS_PASSWORD;

	@Value("${redis.timeout}")
	public int REDIS_TIMEOUT;

	@Value("${redis.default.db}")
	public int REDIS_DEFAULT_DATABASE;

	@Value("${redis.pool.maxActive}")
	public int REDIS_POOL_MAX_ACTIVE;

	@Value("${redis.pool.maxIdle}")
	public int REDIS_POOL_MAX_IDLE;

	@Value("${redis.pool.maxWait}")
	public int REDIS_POOL_MAX_WAIT;

	@Value("${redis.pool.testOnBorrow}")
	public boolean REDIS_POOL_TEST_ON_BORROW;

	/**
	 * 日期下钻
	 */
	@Value("${DATE_RESOLVE}")
	private boolean DATE_RESOLVE;
	@Value("${dataView.sshport}")
	private int dataView_sshport;
	@Value("${dataView.sshuser}")
	private String dataView_sshuser;
	@Value("${dataView.sshpassword}")
	private String dataView_sshpassword;
	@Value("${dataView.sship}")
	private String dataView_sship;

	public SshRemoteParams SSH_DATA_VIEW = getDataViewSsh();

	private SshRemoteParams getDataViewSsh() {
		SshRemoteParams dataView = new SshRemoteParams();
		dataView.setSshPort(dataView_sshport);
		dataView.setSshUser(dataView_sshuser);
		dataView.setSshPassword(dataView_sshpassword);
		dataView.setSship(dataView_sship);

		return dataView;
	}

	/**
	 * 必须加上static
	 */
	@Bean
	private static PropertySourcesPlaceholderConfigurer loadProperties() {
		PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
		return configurer;
	}
}