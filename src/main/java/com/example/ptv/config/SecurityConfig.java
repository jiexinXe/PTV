package com.example.ptv.config;



import com.example.ptv.sec.JwtAuthenticationEntryPoint;
import com.example.ptv.sec.filter.JwtAuthenticationFilter;
import com.example.ptv.sec.handler.JwtAccessDeniedHandler;
import com.example.ptv.sec.handler.LoginFailureHandler;
import com.example.ptv.sec.handler.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    LoginFailureHandler loginFailureHandler;



    @Autowired
    LoginSuccessHandler loginSuccessHandler;

    @Autowired
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public static final String[] URL_WHITELIST = {
            "/webjars/**",
            "/favicon.ico",

            "/captcha",
            "/login",
            "/logout",
            "/register",
            "/kafkaProducer/**",
            "/kafkaConsumer/**",
    };
    @Bean
    public PasswordEncoder passwordEncoder() {
        //开启加密
        return new BCryptPasswordEncoder();
    }
    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager());
        return jwtAuthenticationFilter;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("可惜不可惜忘了爱自己");
        http.cors().and().csrf().disable()
                .formLogin()
                    .loginProcessingUrl("/login")
                    .failureHandler(loginFailureHandler)
                    .successHandler(loginSuccessHandler)

                .and()
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                    .antMatchers("/admin/*").hasRole("ADMIN")

                    .antMatchers(URL_WHITELIST).permitAll() //白名单
                    .anyRequest().authenticated()


                // 不会创建 session
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 异常处理器
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // 配置自定义的过滤器
                .and()


        ;
    }
}

