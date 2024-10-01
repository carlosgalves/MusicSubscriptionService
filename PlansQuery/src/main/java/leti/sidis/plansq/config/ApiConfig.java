/*
 * Copyright (c) 2022-2022 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package leti.sidis.plansq.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;


/**
 *
 * @author pgsousa
 *
 */
@Configuration
@EnableConfigurationProperties
public class ApiConfig {

	/*
	 * Etags
	 */

	/*
	 * @Bean public FilterRegistrationBean<ShallowEtagHeaderFilter>
	 * shallowEtagHeaderFilter() { FilterRegistrationBean<ShallowEtagHeaderFilter>
	 * filterRegistrationBean = new FilterRegistrationBean<>( new
	 * ShallowEtagHeaderFilter()); filterRegistrationBean.addUrlPatterns("/foos/*");
	 * filterRegistrationBean.setName("etagFilter"); return filterRegistrationBean;
	 * }
	 */

	// We can also just declare the filter directly
	@Bean
	public ShallowEtagHeaderFilter shallowEtagHeaderFilter() {
		return new ShallowEtagHeaderFilter();
	}

	/*
	 * OpenAPI
	 */
	@Bean
	public OpenAPI openApi() {
		return new OpenAPI().info(new Info().title("Plan API").description("Sample demo API").version("v1.0")
				.contact(new Contact().name("PSOFT_G7").email("1211508@isep.ipp.pt")).termsOfService("TOC")
				//.contact(new Contact().name("Barbara Santos").email("1211508@isep.ipp.pt")).termsOfService("TOC")
				//.contact(new Contact().name("Clara Coelho").email("1210926@isep.ipp.pt")).termsOfService("TOC")
				//.contact(new Contact().name("Carlos Alves").email("1211604@isep.ipp.pt")).termsOfService("TOC")
				//.contact(new Contact().name("Luis Passos").email("1211091@isep.ipp.pt")).termsOfService("TOC")
				.license(new License().name("MIT").url("#")));
	}
}
//TODO SO A DAR PARA POR UM NOME
