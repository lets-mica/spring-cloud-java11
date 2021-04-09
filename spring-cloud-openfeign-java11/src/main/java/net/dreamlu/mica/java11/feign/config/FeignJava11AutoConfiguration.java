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
import net.dreamlu.mica.java11.feign.config.httpclient.Http2Client;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalancedRetryFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerProperties;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.cloud.openfeign.loadbalancer.FeignBlockingLoadBalancerClient;
import org.springframework.cloud.openfeign.loadbalancer.OnRetryNotEnabledCondition;
import org.springframework.cloud.openfeign.loadbalancer.RetryableFeignBlockingLoadBalancerClient;
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
	@ConditionalOnMissingBean
	@Conditional(OnRetryNotEnabledCondition.class)
	public Client feignClient(HttpClient httpClient,
							  LoadBalancerClient loadBalancerClient,
							  LoadBalancerProperties properties,
							  LoadBalancerClientFactory loadBalancerClientFactory) {
		Http2Client delegate = new Http2Client(httpClient);
		return new FeignBlockingLoadBalancerClient(delegate, loadBalancerClient, properties, loadBalancerClientFactory);
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnClass(name = "org.springframework.retry.support.RetryTemplate")
	@ConditionalOnBean(LoadBalancedRetryFactory.class)
	@ConditionalOnProperty(value = "spring.cloud.loadbalancer.retry.enabled", havingValue = "true", matchIfMissing = true)
	public Client feignRetryClient(LoadBalancerClient loadBalancerClient,
								   HttpClient httpClient,
								   LoadBalancedRetryFactory loadBalancedRetryFactory,
								   LoadBalancerProperties properties,
								   LoadBalancerClientFactory loadBalancerClientFactory) {
		Http2Client delegate = new Http2Client(httpClient);
		return new RetryableFeignBlockingLoadBalancerClient(delegate, loadBalancerClient, loadBalancedRetryFactory,
			properties, loadBalancerClientFactory);
	}

}
