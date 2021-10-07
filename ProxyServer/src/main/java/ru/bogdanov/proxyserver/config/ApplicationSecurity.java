package ru.bogdanov.proxyserver.config;

//import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ru.bogdanov.proxyserver.entity.User;
import ru.bogdanov.proxyserver.repozitory.UserRepository;


@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/item/**").permitAll()
               // .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and().logout().logoutSuccessUrl("/").permitAll();

        http.csrf().disable();
      /*  http.antMatcher("/**").authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login().tokenEndpoint().accessTokenResponseClient(accessTokenResponseClient());//.defaultSuccessUrl("/");//successHandler(myAuthenticationSuccessHandler());
             //   .authorizationEndpoint().authorizationRequestRepository()




                //.successHandler(myAuthenticationSuccessHandler());
    //.defaultSuccessUrl("/**");
    }*/

    }

   @Bean
    public PrincipalExtractor principalExtractor(UserRepository userRepository) {
        return map -> {
            String id = (String) map.get("sub");
            User user = userRepository.findById(id).orElseGet(() -> {
                User newUser = new User();
                newUser.setId(id);
                newUser.setEmail((String) map.get("email"));
                System.out.println(newUser.toString());
                return newUser;
            });
            return userRepository.save(user);
        };
    }
}
