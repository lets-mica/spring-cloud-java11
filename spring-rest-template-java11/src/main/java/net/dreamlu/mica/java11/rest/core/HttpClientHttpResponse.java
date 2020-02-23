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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.AbstractClientHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

/**
 * java11 HttpResponse
 *
 * @author L.cm
 */
@RequiredArgsConstructor
public class HttpClientHttpResponse extends AbstractClientHttpResponse {
	private final HttpResponse<InputStream> response;
	@Nullable
	private volatile HttpHeaders headers;

	@Override
	public int getRawStatusCode() {
		return this.response.statusCode();
	}

	@Override
	public String getStatusText() {
		HttpStatus httpStatus = HttpStatus.resolve(getRawStatusCode());
		return httpStatus.getReasonPhrase();
	}

	@Override
	public InputStream getBody() {
		InputStream body = this.response.body();
		return body == null ? StreamUtils.emptyInput() : body;
	}

	@Override
	public HttpHeaders getHeaders() {
		HttpHeaders headers = this.headers;
		if (headers == null) {
			headers = new HttpHeaders();
			Map<String, List<String>> headerMap = this.response.headers().map();
			for (Map.Entry<String, List<String>> entry : headerMap.entrySet()) {
				headers.addAll(entry.getKey(), entry.getValue());
			}
			this.headers = headers;
		}
		return headers;
	}

	@Override
	public void close() {
		InputStream body = this.response.body();
		if (body != null) {
			try {
				body.close();
			} catch (IOException e) {
				// ignore
			}
		}
	}
}
