package com.back.global.batch;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.EnableJdbcJobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
@EnableBatchProcessing
@EnableJdbcJobRepository
public class BatchConfig {

	/**
	 * 프로덕션 환경이 아닌 경우(예: 로컬, 개발), Spring Batch 실행에 필요한 H2 데이터베이스 스키마를 자동으로 생성
	 *
	 * @param dataSource 초기화할 대상 데이터 소스
	 * @return 설정된 DataSourceInitializer 객체
	 */
	@Bean
	@Profile("!prod")
	public DataSourceInitializer notProdDataSourceInitializer(DataSource dataSource) {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("/org/springframework/batch/core/schema-h2.sql"));
		populator.setContinueOnError(true);

		DataSourceInitializer initializer = new DataSourceInitializer();
		initializer.setDataSource(dataSource);
		initializer.setDatabasePopulator(populator);
		return initializer;
	}
}
