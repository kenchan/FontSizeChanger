package net.shu_cream.eclipse.font.test;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public abstract class TestUIUtils {
	
	private final static IProgressMonitor MONITOR = new NullProgressMonitor();

	protected static void createTestProject() throws CoreException {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("test");
		if(project.exists()){
			System.out.println("project is already existed.");
			return;
		}
		project.create(MONITOR);
		project.open(MONITOR);
	}
	
	protected static void showNavigator() throws PartInitException {
		IWorkbenchPage page = getWorkbenchPage();
		if(page == null){
			System.out.println("page is null.");
			return;
		}
		page.showView("org.eclipse.ui.views.ResourceNavigator");
	}

	protected static void hideWelcomeView() {
		IWorkbenchPage page = getWorkbenchPage();
		if(page == null){
			System.out.println("page is null.");
			return;
		}
		IWorkbenchPart part = page.getActivePart();
		if( -1 < part.getClass().getName().indexOf("ViewIntroAdapterPart")){
			page.hideView((IViewPart) part);
		}
	}

	protected static IWorkbenchPage getWorkbenchPage() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if(window == null){
			System.out.println("window is null");
			return null;
		}
		IWorkbenchPage page = window.getActivePage();
		if(page == null){
			System.out.println("page is null");
			return null;
		}
		return page;
	}

}
