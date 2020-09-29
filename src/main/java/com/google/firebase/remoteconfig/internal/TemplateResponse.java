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

package com.google.firebase.remoteconfig.internal;

import com.google.api.client.util.Key;
import com.google.firebase.remoteconfig.ExplicitParameterValue;
import com.google.firebase.remoteconfig.InAppDefaultValue;
import com.google.firebase.remoteconfig.RemoteConfigParameter;
import com.google.firebase.remoteconfig.RemoteConfigParameterValue;
import com.google.firebase.remoteconfig.RemoteConfigTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class TemplateResponse {

  @Key("parameters")
  private Map<String, ParameterResponse> parameters;

  public TemplateResponse() {
    parameters = Collections.emptyMap();
  }

  public TemplateResponse(Map<String, ParameterResponse> parameters) {
    this.parameters = parameters;
  }

  public RemoteConfigTemplate toPublicType() {
    Map<String, RemoteConfigParameter> parameterPublicTypes = new HashMap<>();
    for (Map.Entry<String, ParameterResponse> entry : parameters.entrySet()) {
      parameterPublicTypes.put(entry.getKey(), entry.getValue().toPublicType());
    }
    return new RemoteConfigTemplate().setParameters(parameterPublicTypes);
  }

  public static final class ParameterResponse {

    @Key("defaultValue")
    private ParameterValueResponse defaultValue;

    @Key("description")
    private String description;

    @Key("conditionalValues")
    private Map<String, ParameterValueResponse> conditionalValues;

    public ParameterResponse() {
      conditionalValues = Collections.emptyMap();
    }

    public ParameterResponse(ParameterValueResponse defaultValue, String description,
                             Map<String, ParameterValueResponse> conditionalValues) {
      this.defaultValue = defaultValue;
      this.description = description;
      this.conditionalValues = conditionalValues;
    }

    public RemoteConfigParameter toPublicType() {
      Map<String, RemoteConfigParameterValue> conditionalPublicValues = new HashMap<>();
      for (Map.Entry<String, ParameterValueResponse> entry : conditionalValues.entrySet()) {
        conditionalPublicValues.put(entry.getKey(), entry.getValue().toPublicType());
      }
      return new RemoteConfigParameter().setDefaultValue(defaultValue.toPublicType())
              .setDescription(description).setConditionalValues(conditionalPublicValues);
    }
  }

  public static final class ParameterValueResponse {

    @Key("value")
    private String value;

    @Key("useInAppDefault")
    private Boolean inAppDefaultValue;

    public ParameterValueResponse() {
    }

    private ParameterValueResponse(String value, Boolean inAppDefaultValue) {
      this.value = value;
      this.inAppDefaultValue = inAppDefaultValue;
    }

    public static ParameterValueResponse ofValue(String value) {
      return new ParameterValueResponse(value, null);
    }

    public static ParameterValueResponse ofInAppDefaultValue() {
      return new ParameterValueResponse(null, true);
    }

    public RemoteConfigParameterValue toPublicType() {
      if (this.inAppDefaultValue != null && this.inAppDefaultValue) {
        return InAppDefaultValue.getInstance();
      }
      return ExplicitParameterValue.of(this.value);
    }
  }
}
