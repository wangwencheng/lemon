package org.lemon.common.data.mybatis;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import org.lemon.common.data.tenant.GbTenantHandler;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
/**
 * mybatis-plus配置
 * @author wwc
 */
@Configuration
@ConditionalOnClass(DataSource.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@Import(LemonMetaObjectHandler.class)
public class MybatisPlusConfig {

	/**
	 * 创建租户维护处理器对象
	 * @return
	 */
//	@Bean
//	@ConditionalOnMissingBean
//	public GbTenantHandler gbTenantHandler() {
//		return new GbTenantHandler();
//	}

	/**
	 * 分页插件
	 * @param tenantHandler
	 * @return
	 */
//	@Bean
//	@ConditionalOnMissingBean
//	@ConditionalOnProperty(name = "mybatisPlus.tenantEnable", havingValue = "true", matchIfMissing = true)
//	public PaginationInterceptor paginatiosnInterceptor(GbTenantHandler tenantHandler) {
//		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
//		List<ISqlParser> sqlParserList = new ArrayList<>();
//		TenantSqlParser tenantSqlParser = new TenantSqlParser();
//		tenantSqlParser.setTenantHandler(tenantHandler);
//		sqlParserList.add(tenantSqlParser);
//		paginationInterceptor.setSqlParserList(sqlParserList);
//		return paginationInterceptor;
//	}

	/**
	 * 乐观锁
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public OptimisticLockerInterceptor optimisticLockerInterceptor() {
		return new OptimisticLockerInterceptor();
	}
}
