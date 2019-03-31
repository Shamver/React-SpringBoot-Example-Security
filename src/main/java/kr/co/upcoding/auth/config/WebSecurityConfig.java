package kr.co.upcoding.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserAuthenticationProvider authenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**", "/js/**","/img/**").permitAll() // 순차적으로 필터링해나아간다. public 소스들은 오픈해준다.
                .antMatchers("/auth/**").hasAnyRole("ROLE_USER") // /auth의 경로는 ROLE_USER 의 권한세션을 지녀야 가능.
                .anyRequest().permitAll(); // 이외의 경로는 모두 오픈

        http.formLogin()
                .loginPage("/login") // defualt, 로그인 UI를 제공하는 페이지의 경로. Controller에 GET요청으로 전달
                .loginProcessingUrl("/authenticate") // form태그에서 action 속성에 해당하는 부분, Controller와 무관하며 Spring Security에서 요청을 가로채서 인증 진행.
                .failureUrl("/login?error") // default, 인증 실패시 돌아갈 경로.
                .defaultSuccessUrl("/home") // 성공시 돌아갈 경로.
                .usernameParameter("email") // 유저네임파라미터
                .passwordParameter("password") // 패스워드파라미터
                .permitAll(); // 로그인페이지의 접근을 완전 허용

        http.logout()
                .logoutUrl("/logout") // default, 로그아웃 경로 설정
                .logoutSuccessUrl("/login") // 로그아웃 후에 이동하게 될 경로
                .permitAll(); // 로그아웃 경로의 접근을 완전 허용
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }
}
