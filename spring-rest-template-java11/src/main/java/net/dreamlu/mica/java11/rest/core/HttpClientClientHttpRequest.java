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
import org.springframework.http.HttpMethod;
import org.springframework.http.client.AbstractClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * java 11 ClientHttpRequest
 *
 * @author L.cm
 */
@RequiredArgsConstructor
public class HttpClientClientHttpRequest extends AbstractClientHttpRequest {
	private final HttpClient client;
	private final URI uri;
	private final HttpMethod method;
	private final Duration readTimeout;
	private ByteArrayOutputStream bufferedOutput = new ByteArrayOutputStream(1024);

	@Override
	protected OutputStream getBodyInternal(HttpHeaders headers) throws IOException {
		return this.bufferedOutput;
	}

	@Override
	protected ClientHttpResponse executeInternal(HttpHeaders headers) throws IOException {
		byte[] content = this.bufferedOutput.toByteArray();
		// request
		HttpRequest request = HttpClientHttpRequestFactory.buildRequest(headers, content, getURI(), getMethodValue(), readTimeout);
		try {
			HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
			return new HttpClientHttpResponse(response);
		} catch (InterruptedException e) {
			throw new IOException(e);
		} finally {
			this.bufferedOutput = new ByteArrayOutputStream(0);
		}
	}

	@Override
	public String getMethodValue() {
		return this.method.name();
	}

	@Override
	public URI getURI() {
		return this.uri;
	}

}
