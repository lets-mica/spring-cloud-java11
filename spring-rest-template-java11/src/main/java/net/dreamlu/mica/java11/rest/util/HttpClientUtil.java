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

package net.dreamlu.mica.java11.rest.util;

import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

/**
 * java11 HttpClient 的一些方法
 *
 * @author L.cm
 */
@UtilityClass
public class HttpClientUtil {
	private static final Set<String> DISALLOWED_HEADERS_SET;

	static {
		TreeSet<String> treeSet = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
		treeSet.addAll(Set.of("connection", "content-length",
			"date", "expect", "from", "host", "upgrade", "via", "warning"));
		DISALLOWED_HEADERS_SET = Collections.unmodifiableSet(treeSet);
	}

	private static final Predicate<String> ALLOWED_HEADERS = (header) -> !DISALLOWED_HEADERS_SET.contains(header);

	/**
	 * 判断是否允许传递的 header， java11 下直接会校验报错
	 *
	 * @param headerName headerName
	 * @return 是否允许
	 */
	public static boolean allowed(String headerName) {
		return ALLOWED_HEADERS.test(headerName);
	}
}
