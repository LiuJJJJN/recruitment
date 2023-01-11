package com.ibm.rms.config;

import com.ibm.rms.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SpringSecurity 的配置类
 *
 * @author 刘嘉宁
 * @date 2022-11-29 11:42:13
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 注入过滤器
    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    // 注入认证和授权的异常处理切入点、handler
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    /**
     * 配置密码加密方式
     *
     * @return 密码加密工具类
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 设置密码加密方式为 SpringSecurity 提供的 BCryptPasswordEncoder
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置方法
     * @param http http
     * @throws Exception 异常
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 允许匿名访问
                .antMatchers("/user/login").anonymous()
                // 除上面以外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();

        // 把 token 校验过滤器添加到过滤器链中
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        // 注册认证和授权异常的切入点
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).
                accessDeniedHandler(accessDeniedHandler);

        // 允许 SpringSecurity 跨域
        http.cors();
    }

    /**
     * 暴露 AuthenticationManager 到 Spring 容器中以便 Service 层使用
     *
     * @return AuthenticationManager 的 bean 实例
     * @throws Exception 异常
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
