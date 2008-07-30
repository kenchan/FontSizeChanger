package net.shu_cream.eclipse.font.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import net.shu_cream.eclipse.font.ToLargeHandler;

import org.eclipse.core.commands.Command;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.bindings.TriggerSequence;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.keys.IBindingService;
import org.eclipse.ui.menus.CommandContributionItem;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestToLargeHandler {
		
	@BeforeClass
	public void テスト実行のその前に() throws Exception{
		Command.DEBUG_COMMAND_EXECUTION = true;
		TestUIUtils.hideWelcomeView();
		TestUIUtils.showNavigator();
		TestUIUtils.createTestProject();
	}

	@Test
	public void このクラスがハンドラサービスに登録されていますか() throws Exception{
		ICommandService commandService = (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class);
		assertNotNull(commandService);
		Command command = commandService.getCommand("net.shu_cream.eclipse.font.large");
		assertNotNull(command);
		
		Object obj = command.executeWithChecks(null);
		assertEquals(ToLargeHandler.class,obj.getClass());
		command.executeWithChecks(null);
		command.executeWithChecks(null);
		command.executeWithChecks(null);
	}
	
	@Test
	public void このクラスがショートカットに登録されているか() throws Exception {
		IBindingService service = (IBindingService) PlatformUI.getWorkbench().getService(IBindingService.class);
		TriggerSequence[] sequences = service.getActiveBindingsFor("net.shu_cream.eclipse.font.large");
		assertEquals(1, sequences.length);
		KeyStroke s = (KeyStroke)sequences[0].getTriggers()[0];
		System.out.println(s.format());
		assertEquals("Ctrl+Alt+;",sequences[0].format());
	}
	
	@Test
	public void このクラスがツールバーに登録されているか() throws Exception {
		IWorkbenchWindow[] windows = PlatformUI.getWorkbench().getWorkbenchWindows();
		for(IWorkbenchWindow w : windows){
			ApplicationWindow window = (ApplicationWindow) w;
			ICoolBarManager coolbar = window.getCoolBarManager2();
			ToolBarContributionItem contributionItem = (ToolBarContributionItem) coolbar.find("net.shu_cream.eclipse.font");
			assertNotNull(contributionItem);
			
			ToolBarManager barManager = (ToolBarManager) contributionItem.getToolBarManager();
			IContributionItem item = barManager.find("large");
			assertNotNull(item);
			assertTrue(item instanceof CommandContributionItem);
			
			CommandContributionItem commandItem = (CommandContributionItem) item;
			assertTrue(commandItem.isEnabled());

			doCommand(barManager);
		}
		
	}

	private void doCommand(ToolBarManager barManager) {
		ToolBar toolbar = barManager.getControl();
		ToolItem[] toolItems = toolbar.getItems();
		for(ToolItem toolItem : toolItems){
			if("Large".equals(toolItem.getToolTipText())){
				toolItem.notifyListeners(SWT.Selection, null);
			}
		}
	}
}
