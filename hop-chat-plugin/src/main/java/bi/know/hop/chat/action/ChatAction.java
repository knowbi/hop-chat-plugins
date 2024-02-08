/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package bi.know.hop.chat.action;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import java.util.List;
import org.apache.hop.core.ICheckResult;
import org.apache.hop.core.Result;
import org.apache.hop.core.annotations.Action;
import org.apache.hop.core.variables.IVariables;
import org.apache.hop.metadata.api.HopMetadata;
import org.apache.hop.metadata.api.HopMetadataProperty;
import org.apache.hop.metadata.api.IHopMetadataProvider;
import org.apache.hop.workflow.WorkflowMeta;
import org.apache.hop.workflow.action.ActionBase;
import org.apache.hop.workflow.action.IAction;

@Action(
    id = "ChatAction",
    name = "i18n::ChatAction.Name",
    description = "i18n::ChatAction.Description",
    image = "sample.svg",
    categoryDescription = "Sample.Category",
    documentationUrl = "" /*url to your documentation */)
public class ChatAction extends ActionBase implements Cloneable, IAction {
  private static final Class<?> PKG = ChatAction.class; // Needed by Translator


  @HopMetadataProperty(key="service")
  String service;
  @HopMetadataProperty(key="channel")
  String channel;

  @HopMetadataProperty(key="token")
  String token;

  @HopMetadataProperty(key="message")
  String message;

  private String[] services = {"Slack"};

  public ChatAction(String name) {
    super(name, "");
  }

  public ChatAction() {
    this("");
  }

  public Object clone() {
    ChatAction c = (ChatAction) super.clone();
    return c;
  }

  /**
   * Execute this action and return the result. In this case it means, just set the result boolean
   * in the Result class.
   *
   * @param result The result of the previous execution
   * @return The Result of the execution.
   */
  @Override
  public Result execute(Result result, int nr) {

    Slack slack = Slack.getInstance();
    try {

      String token = "xoxb-213655224325-6378522016774-UsZVvzbRo04p6DvSZFZEHoIE";

      // Initialize an API Methods client with the given token
      MethodsClient methods = slack.methods(resolve(getToken()));

      // Build a request object
      ChatPostMessageRequest request =
          ChatPostMessageRequest.builder()
              .channel(resolve(getChannel()))// Use a channel ID `C1234567` is preferable
              .text(resolve(getMessage()))
              .build();

      // Get a response as a Java object
      ChatPostMessageResponse response = methods.chatPostMessage(request);

      //check response to see if an error occurred
      if(!response.isOk()){
        logError("sending message failed with following error: " + response.getError());
        result.setNrErrors(1);
        result.setResult(false);
      }

    } catch (Exception e) {
      logError(e.toString());
      result.setNrErrors(1);
      result.setResult(false);
    }

    return result;
  }

  /**
   * Add checks to report warnings
   *
   * @param remarks
   * @param workflowMeta
   * @param variables
   * @param metadataProvider
   */
  @Override
  public void check(
      List<ICheckResult> remarks,
      WorkflowMeta workflowMeta,
      IVariables variables,
      IHopMetadataProvider metadataProvider) {}

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getService() {
    return service;
  }

  public void setService(String service) {
    this.service = service;
  }

  public String[] getServices() {
    return services;
  }

  public void setServices(String[] services) {
    this.services = services;
  }
}
