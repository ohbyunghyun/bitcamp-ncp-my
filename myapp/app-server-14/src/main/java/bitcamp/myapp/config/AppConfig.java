package bitcamp.myapp.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import bitcamp.myapp.controller.StudentController;
import bitcamp.myapp.controller.TeacherController;

//@Configuration

// Spring IoC 컨테이너가 자동 생성할 클래스를 찾을 수 있도록 패키지를 지정한다.
@ComponentScan(
    value = "bitcamp.myapp.controller",
    excludeFilters = {
        @Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = {StudentController.class, TeacherController.class})
    })

// JDBC 설정 정보를 담고 있는 .properties 파일을 로딩한다.
@PropertySource("classpath:/bitcamp/myapp/config/jdbc.properties")

// Mybatis-Spring 라이브러리에 있는 클래스를 사용하여 DAO 인터페이스의 구현체를 자동 생성하기
@MapperScan("bitcamp.myapp.dao")

// @Transactional 애노테이션으로 트랜잭션을 제어하려면 다음 애노테이션을 이용하여 설정해야 한다.
@EnableTransactionManagement

public class AppConfig {

  {
    System.out.println("AppConfig 생성됨!");
  }
  // 시스템 property 값 가져오기
  //  @Autowired Environment env;

  // DB 커넥셕풀 객체 준비
  //  @Bean
  //  public DataSource dataSource() {
  //    DriverManagerDataSource ds = new DriverManagerDataSource();
  //    ds.setDriverClassName(env.getProperty("jdbc.driver"));
  //    ds.setUrl(env.getProperty("jdbc.url"));
  //    ds.setUsername(env.getProperty("jdbc.username"));
  //    ds.setPassword(env.getProperty("jdbc.password"));
  //    return ds;
  //  }

  // .properties 파일에 있는 값을 낱개로 주입받기
  //  @Value("${jdbc.driver}") String jdbcDriver;
  //  @Value("${jdbc.url}") String url;
  //  @Value("${jdbc.username}") String username;
  //  @Value("${jdbc.password}") String password;
  //
  //  @Bean
  //  public DataSource dataSource() {
  //    DriverManagerDataSource ds = new DriverManagerDataSource();
  //    ds.setDriverClassName(jdbcDriver);
  //    ds.setUrl(url);
  //    ds.setUsername(username);
  //    ds.setPassword(password);
  //    return ds;
  //  }

  // Servlet3.0의 멀티파트 요청 데이터를 처리하려면
  // 1) 서블릿에 멀티파트 파일 처리에 관한 설정 정보를 담은 MultipartConfigElement를 등록해야 한다.
  // 2) 스프링 WebMVC에는 Servlet3.0 API를 이용해 멀티파트 데이터를 처리한 후에
  //    페이지 컨트롤러에게 그 값들을 전달해주는 StandardServletMultipartResolver를 등록해야 한다.
  // 주의!
  //    이때 MultipartResolver 구현체 bean 이름은 "multipartResolver" 여야 한다.
  @Bean
  public MultipartResolver multipartResolver() {
    return new StandardServletMultipartResolver();
  }

  // Apache commons-fileupload 라이브러리로 멀티파트 요청 데이터를 처리하려면
  // 1) 프로젝트에 commons-fileupload 라이브러리를 별도로 추가해야 한다.
  // 2) 스프링 WebMVC에는 이 라이브러리를 이용해 멀티파트 데이터를 처리한 후에
  //    페이지 컨트롤러에게 그 값들을 전달해주는 CommonsMultipartResolver를 등록해야 한다.
  // 주의!
  //    이때 MultipartResolver 구현체 bean 이름은 "multipartResolver" 여야 한다.
  //  @Bean
  //  public MultipartResolver multipartResolver() {
  //    return new CommonsMultipartResolver();
  //  }

  @Bean
  public ViewResolver viewResolver() {
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setViewClass(JstlView.class);
    viewResolver.setPrefix("/WEB-INF/jsp/");
    viewResolver.setSuffix(".jsp");
    return viewResolver;
  }
}








