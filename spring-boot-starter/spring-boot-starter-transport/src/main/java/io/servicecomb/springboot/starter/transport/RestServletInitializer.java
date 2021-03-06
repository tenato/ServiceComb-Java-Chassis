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

package io.servicecomb.springboot.starter.transport;

import java.io.IOException;
import java.net.ServerSocket;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.AbstractConfigurableEmbeddedServletContainer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.stereotype.Component;

import io.servicecomb.transport.rest.servlet.RestServletInjector;
import io.servicecomb.transport.rest.servlet.ServletUtils;

@Component
// extends from AbstractConfigurableEmbeddedServletContainer, only want to get embed web container's port and address
// when org.springframework.web.context.ServletContextAware invoked, ServletContext already rejected to addServlet
// should implements org.springframework.boot.web.servlet.ServletContextInitializer to inject servlet
public class RestServletInitializer extends AbstractConfigurableEmbeddedServletContainer
    implements ServletContextInitializer {
  private static final Logger LOGGER = LoggerFactory.getLogger(RestServletInitializer.class);

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    if (getPort() == 0) {
      LOGGER.warn(
          "spring boot embed web container listen port is 0, serviceComb will not use container's port to handler RESTful request.");
      return;
    }

    // web container did not did listen now.
    // so mock to listen, and then close.
    try (ServerSocket ss = new ServerSocket(getPort(), 0, getAddress())) {
      RestServletInjector.defaultInject(servletContext);
      ServletUtils.saveUrlPrefix(servletContext);
    } catch (IOException e) {
      throw new ServletException(e);
    }
  }
}
