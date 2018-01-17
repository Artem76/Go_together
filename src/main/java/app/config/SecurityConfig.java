package app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)//с помощью какого обьекта спрингу обращатся к учетным записям
                .passwordEncoder(getShaPasswordEncoder());//обьект преобразования паролей
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {//конфигурация работы спрингсекьюрити
        http
                .csrf().disable()//отключение защиты
                .authorizeRequests()//все страницы авторизовать
                .antMatchers("/admin_full**").hasRole("Administrator")
                .antMatchers("/manager_voip**").hasRole("Manager_VoIP")
                .antMatchers("/manager_sms**").hasRole("Manager_SMS")
                .antMatchers("/admin_voip**").hasRole("Administrator_VoIP")
                .antMatchers("/admin_sms**").hasRole("Administrator_SMS")
                .antMatchers("/noc_voip**").hasRole("NOC_VoIP")
                .antMatchers("/noc_sms**").hasRole("NOC_SMS")
                .antMatchers("/report_user**").hasRole("Report_User")
                .antMatchers("/enter").hasAnyRole("Administrator", "Administrator_VoIP", "Administrator_SMS", "Manager_VoIP", "Manager_SMS", "NOC_VoIP", "NOC_SMS", "Report_User", "Blocked")
                .antMatchers("/profile**").hasAnyRole("Administrator", "Administrator_VoIP", "Administrator_SMS", "Manager_VoIP", "Manager_SMS", "NOC_VoIP", "NOC_SMS", "Report_User")
                .and()
                .exceptionHandling().accessDeniedPage("/")//страница в случае вызова недопустимой страницы
                .and()
                .formLogin()
                .loginPage("/login")//страница ввода логина и пароля
                .loginProcessingUrl("/j_spring_security_check")//куда отправлять пост запрос с логином и паролем
                .failureUrl("/login?error")//куда перекинуть если логин или пароль неправильны
                .usernameParameter("j_login")
                .passwordParameter("j_password")
                .permitAll()//доступен всем
                .and()
                .logout()
                .permitAll()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")//выхоод из сесии
                .invalidateHttpSession(true);//прибить сесию
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/js/**")
                .antMatchers("/css/**")
                .antMatchers("/images/**")
                .antMatchers("/fonts/**")
                .antMatchers("/vendor/**")
                .antMatchers("/sections/**")
                .antMatchers("/favicon.ico");
    }

    @Bean
    public ShaPasswordEncoder getShaPasswordEncoder() {
        return new ShaPasswordEncoder();
    }
}
