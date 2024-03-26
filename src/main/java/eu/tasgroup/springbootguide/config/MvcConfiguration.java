package eu.tasgroup.springbootguide.config;

import eu.tasgroup.springbootguide.service.AccessLogService;
import eu.tasgroup.springbootguide.util.log.AppServerLogging;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@EnableWebMvc
@Configuration
@RequiredArgsConstructor
public class MvcConfiguration implements WebMvcConfigurer {

  private final AccessLogService accessLogService;

  List<Locale> LOCALES = Arrays.asList(Locale.ENGLISH, Locale.ITALIAN);

  @Value("${log.server.request.include-headers}")
  private boolean serverRequestIncludeHeaders;
  @Value("${log.server.request.include-client-info}")
  private boolean serverRequestIncludeClientInfo;
  @Value("${log.server.request.include-payload}")
  private boolean serverRequestIncludePayload;
  @Value("${log.server.request.max-payload-length}")
  private int serverRequestMaxLength;
  @Value("${log.server.response.include-headers}")
  private boolean serverResponseIncludeHeaders;
  @Value("${log.server.response.include-payload}")
  private boolean serverResponseIncludePayload;
  @Value("${log.server.response.max-payload-length}")
  private int serverResponseMaxLength;


  @Value("${cors.configuration}")
  private String corsConfiguration;


  @Bean
  public LocaleResolver localeResolver() {
    AcceptHeaderLocaleResolver acceptHeaderLocaleResolver = new AcceptHeaderLocaleResolver();
    acceptHeaderLocaleResolver.setDefaultLocale(Locale.ENGLISH);
    acceptHeaderLocaleResolver.setSupportedLocales(LOCALES);
    return acceptHeaderLocaleResolver;
  }

  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource =
        new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("classpath:messages");
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.setUseCodeAsDefaultMessage(true);
    return messageSource;
  }
  @Primary
  @Bean
  @Override
  public LocalValidatorFactoryBean getValidator() {
    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
    bean.setValidationMessageSource(messageSource());
    return bean;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    AppServerLogging interceptor = new AppServerLogging(accessLogService);

    interceptor.setRequestIncludeHeaders(serverRequestIncludeHeaders);
    interceptor.setRequestIncludeClientInfo(serverRequestIncludeClientInfo);
    interceptor.setRequestIncludePayload(serverRequestIncludePayload);
    interceptor.setRequestMaxPayloadLength(serverRequestMaxLength);

    interceptor.setResponseIncludeHeaders(serverResponseIncludeHeaders);
    interceptor.setResponseIncludePayload(serverResponseIncludePayload);
    interceptor.setResponseMaxPayloadLength(serverResponseMaxLength);

    registry.addInterceptor(interceptor).excludePathPatterns("/swagger-ui/**", "/v3/api-docs/**");
  }

}
