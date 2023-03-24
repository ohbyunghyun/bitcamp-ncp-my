package bitcamp.myapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import bitcamp.myapp.controller.StudentController;
import bitcamp.myapp.controller.TeacherController;
import bitcamp.myapp.web.interceptor.AuthInterceptor;

//@Configuration

@ComponentScan(
    value = "bitcamp.myapp.controller",
    excludeFilters = {
        @Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = {StudentController.class, TeacherController.class})
    })
@EnableWebMvc
public class AppConfig implements WebMvcConfigurer{

  {
    System.out.println("AppConfig 생성됨!");
  }

  @Bean
  public MultipartResolver multipartResolver() {
    return new StandardServletMultipartResolver();
  }

  @Bean
  public ViewResolver viewResolver() {
    // 페이지 컨트롤러가 jsp 경로를 리턴하면
    // viewResolver가 그 경로를 가지고 최종 jsp 경로를 계산한 다음에
    // JstlView를 통해 실행한다.
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setViewClass(JstlView.class);
    viewResolver.setPrefix("/WEB-INF/jsp/");
    viewResolver.setSuffix(".jsp");
    return viewResolver;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    System.out.println("AppConfig.addInterceptors 호출됨!");
    registry.addInterceptor(new AuthInterceptor()).addPathPatterns("/board/form");

  }
}








