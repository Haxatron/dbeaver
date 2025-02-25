/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2021 DBeaver Corp and others
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
package org.jkiss.dbeaver.ui.navigator.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.menus.UIElement;
import org.jkiss.dbeaver.ui.internal.UINavigatorMessages;
import org.jkiss.dbeaver.ui.navigator.INavigatorModelView;
import org.jkiss.dbeaver.utils.GeneralUtils;

import java.util.Iterator;
import java.util.Map;

public class NavigatorHandlerExpandAll extends AbstractHandler implements IElementUpdater {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        IWorkbenchPart activePart = HandlerUtil.getActivePart(event);
        INavigatorModelView navigatorModelView = GeneralUtils.adapt(activePart, INavigatorModelView.class);
        if (navigatorModelView != null) {
            Viewer navigatorViewer = navigatorModelView.getNavigatorViewer();
            if (navigatorViewer instanceof TreeViewer) {
                ISelection selection = navigatorViewer.getSelection();
                if (selection.isEmpty()) {
                    ((TreeViewer) navigatorViewer).expandAll();
                } else if (selection instanceof IStructuredSelection) {
                    for (Iterator iter = ((IStructuredSelection) selection).iterator(); iter.hasNext(); ) {
                        ((TreeViewer) navigatorViewer).expandToLevel(iter.next(), TreeViewer.ALL_LEVELS);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void updateElement(UIElement element, Map parameters) {
        element.setText(UINavigatorMessages.navigator_expand_all_text);
        element.setTooltip(UINavigatorMessages.navigator_expand_all_tip);
    }
}