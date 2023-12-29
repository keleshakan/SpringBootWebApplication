package com.example.MSSQLConnection.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class AppCorsConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppCorsConfig.class);

    @Autowired
    private AllowedHost csatAllowedHosts;

    @Bean
    public CorsFilter corsFilter() {
        this.csatAllowedHosts.getAllowedHosts()
                .forEach(h -> LOGGER.info("app::security::cors::allowed-host: {}", h));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(this.csatAllowedHosts.getAllowedHosts());
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setExposedHeaders(Arrays.asList("Authorization", "Link", "X-Total-Count"));
        config.setAllowCredentials(true);
        config.setMaxAge(1800L);
        List<String> allowedOrigins = config.getAllowedOrigins();
        if (Objects.nonNull(allowedOrigins) && !allowedOrigins.isEmpty()) {
            source.registerCorsConfiguration("/**", config);
        }
        return new CorsFilter(source);
    }

    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler();
    }
}
