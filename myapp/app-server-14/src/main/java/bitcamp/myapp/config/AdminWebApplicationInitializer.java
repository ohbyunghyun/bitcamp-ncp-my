package bitcamp.myapp.config;

import javax.servlet.Filter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AdminWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

  @Override
  protected String getServletName() {
    return "admin";
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class<?>[] {AdminConfig.class};
  }

  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class<?>[] {RootConfig.class};
  }

  @Override
  protected String[] getServletMappings() {
    return new String[] {"/admin/*"};
  }

  @Override
  protected Filter[] getServletFilters() {
    return new Filter[] {new CharacterEncodingFilter("UTF-8")};
  }
}









