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

package net.dreamlu.mica.java11.rest.core;

import lombok.RequiredArgsConstructor;
import net.dreamlu.mica.java11.rest.util.HttpClientUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.List;

/**
 * java11 HttpClient
 *
 * @author L.cm
 */
@RequiredArgsConstructor
public class HttpClientHttpRequestFactory implements ClientHttpRequestFactory {
	private final HttpClient client;
	private final Duration readTimeout;

	@Override
	public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) {
		return new HttpClientClientHttpRequest(this.client, uri, httpMethod, readTimeout);
	}

	public static HttpRequest buildRequest(HttpHeaders headers, byte[] content, URI uri, String method, Duration readTimeout) {
		HttpRequest.Builder builder = HttpRequest.newBuilder(uri);
		// 处理 headers
		headers.forEach((name, list) -> {
			if (HttpClientUtil.allowed(name)) {
				list.forEach(value -> builder.header(name, value));
			}
		});
		headers.computeIfAbsent("Accept", key -> List.of("*/*"));
		// method and content
		if (content.length > 0) {
			builder.method(method, HttpRequest.BodyPublishers.ofByteArray(content));
		} else {
			builder.method(method, HttpRequest.BodyPublishers.noBody());
		}
		// request
		return builder.timeout(readTimeout).build();
	}

}
