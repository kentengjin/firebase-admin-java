/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.firebase.remoteconfig;

import com.google.api.client.util.Key;

import java.util.HashMap;
import java.util.Map;

public final class RemoteConfigTemplate {

  private String etag;

  @Key("parameters")
  private Map<String, RemoteConfigParameter> parameters;

  public RemoteConfigTemplate() {
    parameters = new HashMap<>();
  }

  public String getETag() {
    return this.etag;
  }

  public Map<String, RemoteConfigParameter> getParameters() {
    return this.parameters;
  }

  public RemoteConfigTemplate setETag(String etag) {
    this.etag = etag;
    return this;
  }

  public RemoteConfigTemplate setParameters(Map<String, RemoteConfigParameter> parameters) {
    this.parameters = parameters;
    return this;
  }

  RemoteConfigTemplate wrapForTransport() {
    for (Map.Entry<String, RemoteConfigParameter> entry : parameters.entrySet()) {
      entry.getValue().wrapForTransport();
    }
    return this;
  }
}
