package com.bjit.tms.config;

import com.bjit.tms.repository.user_repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserRepository userRepository;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/**")
                //.requestMatchers("/user/login", "/user/register/admin", "/user/photo/{role}/{id}")
                .permitAll()
//                .requestMatchers("/user/register/trainer", "/user/register/trainee",
//                        "/batch/create", "/batch/classroom/create/{batchId}",
//                        "/batch/all", "/batch/assign/trainee/{batchId}",
//                        "/batch/notice/getNotices/{batchId}",
//                        "/course/create", "/course/assign/batch", "/batch/notice/getNotices/{batchId}",
//                        "/course/schedule","/user/unassigned/trainees", "/batch/details/${batchNumber}").hasAnyAuthority("ADMIN")
//                .requestMatchers("/assignment/create/{trainerId}", "/assignment/list/{batchId}",
//                        "/assignment/submission/{assignmentId}",
//                        "/classroom/post/{trainerId}", "/classroom/allPosts/{classroomId}",
//                        "/classroom/allComments/{postId}", "/classroom/comment/{traineeId}",
//                        "/batch/notice/create/{trainerId}", "/batch/all",
//                        "/batch/notice/getNotices/{batchId}").hasAnyAuthority("TRAINER")
//                .requestMatchers("/assignment/list/{batchId}", "/classroom/allPosts/{classroomId}",
//                         "/assignment/submit/{traineeId}", "/classroom/comment/{traineeId}",
//                        "/classroom/allComments/{postId}", "/allPosts/{classroomId}",
//                        "/batch/notice/getNotices/{batchId}").hasAnyAuthority("TRAINEES")
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }
}
