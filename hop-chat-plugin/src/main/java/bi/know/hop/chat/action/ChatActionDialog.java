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

import java.util.Arrays;
import org.apache.hop.core.Const;
import org.apache.hop.core.util.Utils;
import org.apache.hop.core.variables.IVariables;
import org.apache.hop.i18n.BaseMessages;
import org.apache.hop.ui.core.PropsUi;
import org.apache.hop.ui.core.dialog.BaseDialog;
import org.apache.hop.ui.core.gui.WindowProperty;
import org.apache.hop.ui.core.widget.StyledTextComp;
import org.apache.hop.ui.core.widget.TextVar;
import org.apache.hop.ui.pipeline.transform.BaseTransformDialog;
import org.apache.hop.ui.workflow.action.ActionDialog;
import org.apache.hop.ui.workflow.dialog.WorkflowDialog;
import org.apache.hop.workflow.WorkflowMeta;
import org.apache.hop.workflow.action.IAction;
import org.apache.hop.workflow.action.IActionDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.*;

public class ChatActionDialog extends ActionDialog implements IActionDialog {
  private static final Class<?> PKG = ChatActionDialog.class; // Needed by Translator

  private Shell shell;

  private ChatAction action;

  private boolean changed;

  private Text wName;

  private CCombo wService;

  private TextVar wChannel;

  private TextVar wToken;

  private StyledTextComp wMessage;

  public ChatActionDialog(
      Shell parent, IAction action, WorkflowMeta workflowMeta, IVariables variables) {
    super(parent, workflowMeta, variables);
    this.action = (ChatAction) action;
    if (this.action.getName() == null) {
      this.action.setName(BaseMessages.getString(PKG, "ChatAction.Name"));
    }
  }

  @Override
  public IAction open() {
    Shell parent = getParent();
    Display display = parent.getDisplay();

    shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.RESIZE | SWT.MAX | SWT.MIN );
    props.setLook(shell);
    WorkflowDialog.setShellImage(shell, action);

    ModifyListener lsMod = (ModifyEvent e) -> action.setChanged();
    changed = action.hasChanged();

    FormLayout formLayout = new FormLayout();
    formLayout.marginWidth =  PropsUi.getFormMargin();
    formLayout.marginHeight = PropsUi.getFormMargin();

    shell.setLayout(formLayout);
    shell.setText(BaseMessages.getString(PKG, "ChatAction.Title"));

    int middle = props.getMiddlePct();
    int margin = PropsUi.getMargin();

    // Action name line
    Label wlName = new Label(shell, SWT.RIGHT);
    wlName.setText(BaseMessages.getString(PKG, "System.ActionName.Label"));
    props.setLook(wlName);
    FormData fdlName = new FormData();
    fdlName.left = new FormAttachment(0, 0);
    fdlName.right = new FormAttachment(middle, -margin);
    fdlName.top = new FormAttachment(0, margin);
    wlName.setLayoutData(fdlName);
    wName = new Text(shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
    props.setLook(wName);
    wName.addModifyListener(lsMod);
    FormData fdName = new FormData();
    fdName.left = new FormAttachment(middle, 0);
    fdName.top = new FormAttachment(0, margin);
    fdName.right = new FormAttachment(100, 0);
    wName.setLayoutData(fdName);

    // service
    Label wlService = new Label(shell, SWT.RIGHT);
    wlService.setText(BaseMessages.getString(PKG, "ChatAction.ServiceLabel"));
    props.setLook(wlService);
    FormData fdlService = new FormData();
    fdlService.left = new FormAttachment(0, 0);
    fdlService.right = new FormAttachment(middle, -margin);
    fdlService.top = new FormAttachment(wName, margin);
    wlService.setLayoutData(fdlService);
    wService = new CCombo(shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
    props.setLook(wService);
    wService.setItems(action.getServices());
    wService.addModifyListener(lsMod);
    FormData fdService = new FormData();
    fdService.left = new FormAttachment(middle, 0);
    fdService.top = new FormAttachment(wName, margin);
    fdService.right = new FormAttachment(100, 0);
    wService.setLayoutData(fdService);



    // token
    Label wlToken = new Label(shell, SWT.RIGHT);
    wlToken.setText(BaseMessages.getString(PKG, "ChatAction.TokenLabel"));
    props.setLook(wlToken);
    FormData fdlToken = new FormData();
    fdlToken.left = new FormAttachment(0, 0);
    fdlToken.right = new FormAttachment(middle, -margin);
    fdlToken.top = new FormAttachment(wService, margin);
    wlToken.setLayoutData(fdlToken);
    wToken = new TextVar(variables,shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
    props.setLook(wToken);
    wToken.addModifyListener(lsMod);
    FormData fdToken = new FormData();
    fdToken.left = new FormAttachment(middle, 0);
    fdToken.top = new FormAttachment(wService, margin);
    fdToken.right = new FormAttachment(100, 0);
    wToken.setLayoutData(fdToken);

    // channel
    Label wlChannel = new Label(shell, SWT.RIGHT);
    wlChannel.setText(BaseMessages.getString(PKG, "ChatAction.ChannelLabel"));
    props.setLook(wlChannel);
    FormData fdlChannel = new FormData();
    fdlChannel.left = new FormAttachment(0, 0);
    fdlChannel.right = new FormAttachment(middle, -margin);
    fdlChannel.top = new FormAttachment(wToken, margin);
    wlChannel.setLayoutData(fdlChannel);
    wChannel = new TextVar(variables,shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
    props.setLook(wChannel);
    wChannel.addModifyListener(lsMod);
    FormData fdChannel = new FormData();
    fdChannel.left = new FormAttachment(middle, 0);
    fdChannel.top = new FormAttachment(wToken, margin);
    fdChannel.right = new FormAttachment(100, 0);
    wChannel.setLayoutData(fdChannel);

    // create some buttons

    Button wOk = new Button(shell, SWT.PUSH);
    wOk.setText(BaseMessages.getString(PKG, "System.Button.OK"));
    Button wCancel = new Button(shell, SWT.PUSH);
    wCancel.setText(BaseMessages.getString(PKG, "System.Button.Cancel"));

    // Message
    Label wlMessage = new Label(shell, SWT.RIGHT);
    wlMessage.setText(BaseMessages.getString(PKG, "ChatAction.MessageLabel"));
    props.setLook(wlMessage);
    FormData fdlMessage = new FormData();
    fdlMessage.left = new FormAttachment(0, 0);
    fdlMessage.right = new FormAttachment(middle, -margin);
    fdlMessage.top = new FormAttachment(wChannel, margin);
    wlMessage.setLayoutData(fdlMessage);
    wMessage = new StyledTextComp(variables,shell, SWT.MULTI | SWT.LEFT | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
    props.setLook(wMessage);
    wMessage.addModifyListener(lsMod);
    FormData fdMessage = new FormData();
    fdMessage.left = new FormAttachment(middle, 0);
    fdMessage.top = new FormAttachment(wChannel, margin);
    fdMessage.right = new FormAttachment(100, 0);
    fdMessage.bottom = new FormAttachment(wOk, -margin);
    wMessage.setLayoutData(fdMessage);




    // at the bottom
    BaseTransformDialog.positionBottomButtons(shell, new Button[] {wOk, wCancel}, margin, null);

    // Add listeners
    wCancel.addListener(SWT.Selection, (Event e) -> cancel());
    wOk.addListener(SWT.Selection, (Event e) -> ok());

    SelectionAdapter lsDef =
        new SelectionAdapter() {
          @Override
          public void widgetDefaultSelected(SelectionEvent e) {
            ok();
          }
        };

    wName.addSelectionListener(lsDef);

    // Detect X or ALT-F4 or something that kills this window...
    shell.addShellListener(
        new ShellAdapter() {
          @Override
          public void shellClosed(ShellEvent e) {
            cancel();
          }
        });

    getData();

    BaseTransformDialog.setSize(shell);

    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    return action;
  }

  public void dispose() {
    WindowProperty winprop = new WindowProperty(shell);
    props.setScreen(winprop);
    shell.dispose();
  }

  /** Copy information from the meta-data input to the dialog fields. */
  public void getData() {
    if (action.getName() != null) {
      wName.setText(action.getName());
    }
    if (action.getService() != null) {
      wService.select(Arrays.asList(action.getServices()).indexOf(action.getService()));
    }
    if (action.getToken() != null) {
      wToken.setText(action.getToken());
    }
    if (action.getChannel() != null) {
      wChannel.setText(action.getChannel());
    }
    if (action.getMessage() != null) {
      wMessage.setText(action.getMessage());
    }

    wName.selectAll();
    wName.setFocus();
  }

  private void cancel() {
    action.setChanged(changed);
    action = null;
    dispose();
  }

  private void ok() {
    if (Utils.isEmpty(wName.getText())) {
      MessageBox mb = new MessageBox(shell, SWT.OK | SWT.ICON_ERROR);
      mb.setText(BaseMessages.getString(PKG, "System.TransformActionNameMissing.Title"));
      mb.setMessage(BaseMessages.getString(PKG, "System.ActionNameMissing.Msg"));
      mb.open();
      return;
    }
    action.setName(wName.getText());
    action.setToken(wToken.getText());
    action.setChannel(wChannel.getText());
    action.setService(wService.getText());
    action.setMessage(wMessage.getText());
    dispose();
  }
}
