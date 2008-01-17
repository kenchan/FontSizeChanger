package net.shu_cream.eclipse.font;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.PartSite;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class FontPlugin extends AbstractUIPlugin {

    // The shared instance.
    private static FontPlugin plugin;

    private Map<String, Integer> diffSizeMap = new HashMap<String, Integer>();
    
    /**
     * The constructor.
     */
    public FontPlugin() {
        plugin = this;
    }

    /**
     * This method is called upon plug-in activation
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
    }

    /**
     * This method is called when the plug-in is stopped
     */
    public void stop(BundleContext context) throws Exception {
        super.stop(context);
        plugin = null;
    }

    /**
     * Returns the shared instance.
     */
    public static FontPlugin getDefault() {
        return plugin;
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in
     * relative path.
     * 
     * @param path the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path) {
        return AbstractUIPlugin.imageDescriptorFromPlugin(
                "net.shu_cream.eclipse.font", path);
    }
    
    void changeSize(int diff) {
    	IWorkbenchPartSite site = getActiveSite();
		if (!(site instanceof PartSite))return;
		PartSite partSite = (PartSite) site;
		String id = partSite.getId();
		Integer value = this.diffSizeMap.get(id);
		if(value == null){
			this.diffSizeMap.put(id,diff);
		}else{
			this.diffSizeMap.put(id,value + diff);
		}
		Control control = partSite.getPane().getControl();
		changeFont(diff, control);
    }

	private IWorkbenchPartSite getActiveSite() {
		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
		IWorkbenchPart activePart = activePage.getActivePart();
		IWorkbenchPartSite site = activePart.getSite();
		return site;
	}

	private void changeFont(int diff, Control control) {
		if (control instanceof Composite) {
			Composite comp = (Composite) control;
			for(Control cControl : comp.getChildren()){
				changeFont(diff, cControl);
			}
		}
		FontData fontData = control.getFont().getFontData()[0];
        int after = fontData.getHeight() + diff;
        if (after == 0) {
            return;
        }
        fontData.setHeight(after);
        Font font = new Font(PlatformUI.getWorkbench().getDisplay(),fontData);
		control.setFont(font);
	}

    void toLarge() {
        this.changeSize(1);
    }

    void toSmall() {
        this.changeSize(-1);
    }

    void reset() {
    	String id = getActiveSite().getId();
    	Integer value = this.diffSizeMap.get(id);
    	if(value == null){
    		value = 0;
    	}
    	this.changeSize(-value);
    }
}
