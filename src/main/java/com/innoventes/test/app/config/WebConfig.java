import com.innoventes.test.app.exception.WebsiteURLValidationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final WebsiteURLValidationInterceptor websiteURLValidationInterceptor;

    public WebConfig(WebsiteURLValidationInterceptor websiteURLValidationInterceptor) {
        this.websiteURLValidationInterceptor = websiteURLValidationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(websiteURLValidationInterceptor);
    }
}