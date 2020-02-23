/*
 * Copyright (c) 2020-2030, Dreamlu 卢春梦 (596392912@qq.com & www.dreamlu.net).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.dreamlu.mica.java11.rest.config;


import lombok.RequiredArgsConstructor;
import net.dreamlu.mica.java11.rest.core.HttpClientHttpRequestFactory;
import net.dreamlu.mica.java11.rest.logger.RestTemplateLoggingInterceptor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;

/**
 * Http RestTemplateHeaderInterceptor 配置
 *
 * @author L.cm
 */
@Configuration
@EnableConfigurationProperties(HttpClientRestProperties.class)
@RequiredArgsConstructor
public class RestTemplateConfiguration {

	/**
	 * 普通的 RestTemplate，不透传请求头，一般只做外部 http 调用
	 *
	 * @param properties      HttpClientProperties
	 * @param builderProvider RestTemplateBuilder
	 * @return RestTemplate
	 */
	@Bean
	public RestTemplate restTemplate(HttpClientRestProperties properties,
									 ObjectProvider<RestTemplateBuilder> builderProvider) {
		HttpClient httpClient = HttpClient.newBuilder()
			.connectTimeout(properties.getConnectionTimeout())
			.followRedirects(properties.getRedirect())
			.version(properties.getVersion())
			.build();
		return builderProvider.getIfAvailable(RestTemplateBuilder::new)
			.requestFactory(() -> new HttpClientHttpRequestFactory(httpClient, properties.getReadTimeout()))
			.interceptors(new RestTemplateLoggingInterceptor(properties.getLevel()))
			.build();
	}

}
