package com.ecommerce.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(
        title = "E-commerce API",
        version = "1.0.0",
        description = "<b>API tài liệu cho hệ thống E-commerce</b><br/>\n\n" +
                "<ul>\n" +
                "<li>Đăng ký/Đăng nhập, quản lý sản phẩm, giỏ hàng, đặt hàng</li>\n" +
                "<li>Phân quyền: <b>Admin</b> & <b>Customer</b></li>\n" +
                "<li>Truy cập Swagger UI tại <code>/swagger-ui.html</code> hoặc <code>/swagger-ui/index.html</code></li>\n" +
                "</ul>",
        contact = @Contact(name = "Ecommerce Team", email = "support@ecommerce.com"),
        license = @License(name = "MIT License", url = "https://opensource.org/licenses/MIT")
    ),
    servers = {
        @Server(url = "http://localhost:8081", description = "Local server")
    }
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    in = SecuritySchemeIn.HEADER
)
@Configuration
public class SwaggerConfig {
    // Đã cấu hình hoàn chỉnh bằng annotation, không cần method @Bean nữa.
}