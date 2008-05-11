package net.shu_cream.eclipse.font;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class ToLargeHandler extends AbstractHandler {

	public Object execute(ExecutionEvent arg0) throws ExecutionException {
        FontPlugin.getDefault().toLarge();
		return this;
	}


}
