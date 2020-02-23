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

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.net.http.HttpClient;
import java.time.Duration;

/**
 * java11 http client 配置
 *
 * @author L.cm
 */
@Getter
@Setter
@RefreshScope
@ConfigurationProperties("http.client.feign")
public class FeignHttpClientProperties {
	/**
	 * 连接超时，默认：2秒
	 */
	private Duration connectionTimeout = Duration.ofSeconds(2);
	/**
	 * 读取超时，默认：2秒
	 */
	private Duration readTimeout = Duration.ofSeconds(2);
	/**
	 * 重定向规则，默认：ALWAYS
	 */
	@NestedConfigurationProperty
	private HttpClient.Redirect redirect = HttpClient.Redirect.ALWAYS;
	/**
	 * http 版本，默认：HTTP_2
	 */
	@NestedConfigurationProperty
	private HttpClient.Version version = HttpClient.Version.HTTP_2;
}
