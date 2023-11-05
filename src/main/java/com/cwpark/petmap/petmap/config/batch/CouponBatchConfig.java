package com.cwpark.petmap.petmap.config.batch;

import com.cwpark.petmap.petmap.service.CouponService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class CouponBatchConfig {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    @Autowired
    private CouponService couponService;

    @Bean
    public Job job() {

        Job job = jobBuilderFactory.get("openAi")
                .start(step())
                .build();

        return job;
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("step")
                .tasklet((contribution, chunkContext) -> {

                    // 아래는 하고 싶은 일
                    couponService.procedureExpiredCoupon();

                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
