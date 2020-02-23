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

package net.dreamlu.mica.java11.rest.logger;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * RestTemplate 日志打印
 *
 * @author L.cm
 */
@Slf4j
@RequiredArgsConstructor
public class RestTemplateLoggingInterceptor implements ClientHttpRequestInterceptor {
	private final HttpLogLevel logLevel;

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
		if (HttpLogLevel.NONE == logLevel) {
			return execution.execute(request, body);
		}
		traceRequest(request, body, logLevel);
		long startNs = System.nanoTime();
		ClientHttpResponse response;
		if (HttpLogLevel.BODY.lte(logLevel)) {
			response = new ClientHttpResponseLogWrapper(execution.execute(request, body));
		} else {
			response = execution.execute(request, body);
		}
		traceResponse(response, logLevel, startNs);
		return response;
	}

	private static void traceRequest(HttpRequest request, byte[] body, HttpLogLevel logLevel) {
		boolean hasBody = body != null && body.length > 0;
		// POST /greeting (3-byte body)
		if (hasBody) {
			log.info("--> {} {} ({}-byte body)", request.getMethod(), request.getURI(), body.length);
		} else {
			log.info("--> {} {}", request.getMethod(), request.getURI());
		}
		if (HttpLogLevel.HEADERS.lte(logLevel)) {
			HttpHeaders headers = request.getHeaders();
			headers.forEach((name, values) -> log.info("{}: {}", name, StringUtils.collectionToCommaDelimitedString(values)));
		}
		if (hasBody && HttpLogLevel.BODY.lte(logLevel)) {
			log.info("Request body: {}", new String(body, StandardCharsets.UTF_8));
		}
		log.info("--> END {}", request.getMethod());
	}

	private static void traceResponse(ClientHttpResponse response, HttpLogLevel logLevel, long startNs) throws IOException {
		long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
		log.info("<--{} {}({}ms)", response.getRawStatusCode(), response.getStatusText(), tookMs);
		if (HttpLogLevel.HEADERS.lte(logLevel)) {
			HttpHeaders headers = response.getHeaders();
			headers.forEach((name, values) -> log.info("{}: {}", name, StringUtils.collectionToCommaDelimitedString(values)));
		}
		if (HttpLogLevel.BODY.lte(logLevel)) {
			log.info("Response body: {}", StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8));
		}
		log.info("<-- END HTTP");
	}

}
