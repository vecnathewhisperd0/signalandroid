/*
 * Copyright (C) 2015 Open Whisper Systems
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.thoughtcrime.securesms.util;

import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class Rfc5724Uri {

  private final String              schema;
  private final String              path;
  private final Map<String, String> queryParams;

  public Rfc5724Uri(String uri) throws URISyntaxException {
    this.schema      = parseSchema(uri);
    this.path        = parsePath(uri);
    this.queryParams = parseQueryParams(uri, this.path);
  }

  private static String parseSchema(final String uri) throws URISyntaxException {
    String[] parts = uri.split(":");

    if (parts.length < 1 || parts[0].isEmpty()) throw new URISyntaxException(uri, "invalid schema");
    else                                        return parts[0];
  }

  private static String parsePath(final String uri) throws URISyntaxException {
    String[] parts = uri.split("\\?")[0].split(":", 2);

    if (parts.length < 2) throw new URISyntaxException(uri, "invalid path");
    else                  return parts[1];
  }

  private static Map<String, String> parseQueryParams(final String uri, final String path) throws URISyntaxException {
    Map<String, String> queryParams = new HashMap<>();
    if (uri.split("\\?").length > 1) {
      for (String keyValue : uri.split("\\?")[1].split("&")) {
        String[] parts = keyValue.split("=");

        if (parts.length == 1) queryParams.put(parts[0], "");
        else                   queryParams.put(parts[0], URLDecoder.decode(parts[1]));
      }
    }

    if (path.isEmpty() && !queryParams.containsKey("body")) throw new URISyntaxException(uri, "missing recipient(s)");
    else                                                    return queryParams;
  }

  public String getSchema() {
    return schema;
  }

  public String getPath() {
    return path;
  }

  public Map<String, String> getQueryParams() {
    return queryParams;
  }
}
