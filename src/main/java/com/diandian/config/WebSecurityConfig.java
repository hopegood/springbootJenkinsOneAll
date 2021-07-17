package com.diandian.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.diandian.component.JwtAuthenticationProvider;
import com.diandian.filter.JwtAuthenticationFilter;



@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
    private UserDetailsService userDetailsService;

    // 无权访问返回的 JSON 格式数据给前端（否则为 403 html 页面）
   // @Autowired
   // AjaxAccessDeniedHandler accessDeniedHandler;

   // @Autowired
   // private MyLogoutSuccessHandler logoutSuccessHandler;
    
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 使用自定义身份验证组件
        auth.authenticationProvider(new JwtAuthenticationProvider(userDetailsService));
    }	
	/**
     * 配置请求拦截
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	// 禁用 csrf, 由于使用的是JWT，我们这里不需要csrf
        /*http.cors().and().csrf().disable()
                .authorizeRequests()
                // 跨域预检请求
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // web jars
                .antMatchers("/webjars/**").permitAll()
                // 查看SQL监控（druid）
                .antMatchers("/druid/**").permitAll()
                .antMatchers("/online").permitAll()
                .antMatchers("/global/**","/static/**").permitAll()
                // 首页和登录页面
                //.antMatchers("/").permitAll()
                .antMatchers("/postTest").permitAll()
                .antMatchers("/login1/getTest").permitAll()
                //.antMatchers("/login").permitAll()
                // swagger
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/webjars/springfox-swagger-ui/**").permitAll()
                // 验证码
                // .antMatchers("/captcha.jpg**").permitAll()

                // 服务监控
                .antMatchers("/actuator/**").permitAll()
                //在线预览
                .antMatchers("/sysSchedule/onlinePreview").permitAll()
                // 其他所有请求需要身份认证
                .anyRequest().authenticated();*/
    	
    	 http.cors().and().csrf().disable().authorizeRequests()
    	 .antMatchers("/loginTest").permitAll()
    	 .antMatchers("/toLogin").permitAll()
    	 .and()
    	 //.httpBasic()
    	.formLogin()
    	 .defaultSuccessUrl("/toLogin")
    	 .and()
    	 .authorizeRequests()
         .anyRequest()
         .authenticated();

        // 退出登录处理器
       // http.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);//new HttpStatusReturningLogoutSuccessHandler());
                          
        // token验证过滤器
        http.addFilterBefore(new JwtAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);

        // security session 管理
       // http.sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry());

        // 无权访问 JSON 格式的数据
        //http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);

    }
    
    /**
     * 需要放行的URL
     */
   public static final String[] AUTH_WHITELIST = {
           "/user/login"
           // other public endpoints of your API may be appended to this array
   };
   @Bean
   @Override
   public AuthenticationManager authenticationManager() throws Exception {
       return super.authenticationManager();
   }

/*   @Override
   public void configure(WebSecurity web) {
	   //这段代码会生成多个过滤器链
       web.ignoring().antMatchers("/captcha.jpg**").antMatchers("/login")
               .antMatchers("/online").antMatchers("/homeDownload").antMatchers("/tdSubject/greadUpload'");

   }
*/
   /**
    * 解决session失效后 sessionRegistry中session没有同步失效的问题，启用并发session控制，首先需要在配置中增加下面监听器
    * @return
    */
   @Bean
   public HttpSessionEventPublisher httpSessionEventPublisher() {
       return new HttpSessionEventPublisher();
   }

   /**
    * 注册bean sessionRegistry
    */
   @Bean
   public SessionRegistry sessionRegistry() {
       return new SessionRegistryImpl();
   }
}
