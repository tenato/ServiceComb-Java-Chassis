/*
 * Copyright 2017 Huawei Technologies Co., Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.servicecomb.common.rest.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.servicecomb.core.Invocation;
import io.servicecomb.swagger.invocation.Response;

public interface HttpServerFilter {
  int getOrder();

  //if check failed, then return failed response
  Response afterReceiveRequest(Invocation invocation, HttpServletRequest request);

  // invocation maybe null
  void beforeSendResponse(Invocation invocation, HttpServletResponse response, byte[] bodyBytes, int length);
}
