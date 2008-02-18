package net.shu_cream.eclipse.font.test;

import static org.junit.Assert.assertNotNull;

import org.eclipse.core.commands.Command;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.junit.Before;
import org.junit.Test;

public class TestToLargeHandler {
	
	@Before
	public void テスト実行のその前に(){
		Command.DEBUG_COMMAND_EXECUTION = true;
	}

	@Test
	public void このクラスがハンドラサービスに登録されていますか() throws Exception{
		ICommandService commandService = (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class);
		assertNotNull(commandService);
		Command command = commandService.getCommand("net.shu_cream.eclipse.font.large");
		assertNotNull(command);
		command.executeWithChecks(null);
	}
	
}
