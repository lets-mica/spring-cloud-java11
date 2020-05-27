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

package net.dreamlu.mica.java11.feign.config;

import feign.Client;
import feign.Feign;
import feign.http2client.Http2Client;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.cloud.openfeign.ribbon.CachingSpringLoadBalancerFactory;
import org.springframework.cloud.openfeign.ribbon.LoadBalancerFeignClient;
import org.springframework.context.annotation.*;

import java.net.http.HttpClient;


/**
 * feign 增强配置
 *
 * @author L.cm
 */
@ConditionalOnClass(Feign.class)
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(FeignJava11AutoConfiguration.class)
@EnableConfigurationProperties(FeignHttpClientProperties.class)
public class FeignJava11AutoConfiguration {

	@Bean
	public HttpClient httpClient(FeignHttpClientProperties properties) {
		return HttpClient.newBuilder()
			.version(properties.getVersion())
			.followRedirects(properties.getRedirect())
			.connectTimeout(properties.getConnectionTimeout())
			.build();
	}

	@Bean
	public Client feignClient(HttpClient client,
							  CachingSpringLoadBalancerFactory cachingFactory,
							  SpringClientFactory clientFactory) {
		Http2Client http2Client = new Http2Client(client);
		return new LoadBalancerFeignClient(http2Client, cachingFactory, clientFactory);
	}

}
