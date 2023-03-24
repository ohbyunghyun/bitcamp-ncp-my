package bitcamp.myapp.config;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class MyWebApplicationInitailzer implements WebApplicationInitializer {

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    System.out.println("MyWebApplicationInitailzer.onStartup() 호출됨");
    // Spring IoC 컨테이너 준비
    AnnotationConfigWebApplicationContext iocContainer = new AnnotationConfigWebApplicationContext();
    iocContainer.register(AppConfig.class);

    // DispatcherServlet 프론트 컨트롤러 준비
    DispatcherServlet dispatcherServlet = new DispatcherServlet(iocContainer);
    Dynamic registration = servletContext.addServlet("app", dispatcherServlet);
    registration.addMapping("/app/*");
    registration.setLoadOnStartup(1);
    registration.setMultipartConfig(new MultipartConfigElement(
        System.getProperty("java.io.tmpdir"), // 클라이언트가 보낸 파일을 임시 보관할 폴더
        1024 * 1024 * 20, // 한 파일의 최대 크기
        1024 * 1024 * 20 * 10, // 한 요청 당 최대 총 파일 크기
        1024 * 1024 * 1 // 클라이언트가 보낸 파일을 메모리에 임시 보관하는 최대 크기.
        // 최대 크기를 초과하면 파일에 내보낸다.
        ));
  }
}









