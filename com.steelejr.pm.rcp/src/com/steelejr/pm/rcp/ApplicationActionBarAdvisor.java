package com.steelejr.pm.rcp;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ContributionItemFactory;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {
	
	//private IWorkbenchAction showViewAction;
	private IContributionItem fShowViewShortList;
	private IContributionItem fPerspectiveShortList;
	
    public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
    }

    protected void makeActions(IWorkbenchWindow window) {
    	//showViewAction = ActionFactory.ABOUT.create(window);
    	//register(showViewAction);
    	fShowViewShortList = ContributionItemFactory.VIEWS_SHORTLIST.create(window);
    	fPerspectiveShortList = ContributionItemFactory.PERSPECTIVES_SHORTLIST.create(window);
    }

    protected void fillMenuBar(IMenuManager menuBar) {
    	
    	MenuManager windowManager = new MenuManager("&Window", IWorkbenchActionConstants.M_WINDOW);
    	
    	MenuManager viewMenu = new MenuManager("Show View", "showView");
    	MenuManager perspectiveMenu = new MenuManager ("&Open Perspective");
    	windowManager.add(viewMenu);
    	windowManager.add(perspectiveMenu);
    	
    	viewMenu.add(fShowViewShortList);
    	perspectiveMenu.add(fPerspectiveShortList);
    	
    	////ActionContributionItm a = new ActionContributionItem(showViewAction);
    	//windowManager.add(a);
    	
    	//windowManager.add(showViewAction);
    	//windowManager.add(new Separator());
    	//windowManager.add(fShowPerspective);
    	menuBar.add(windowManager);
    }
}
