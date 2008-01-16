package net.shu_cream.eclipse.font;

import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class FontPlugin extends AbstractUIPlugin {

    // The shared instance.
    private static FontPlugin plugin;

    private static final String SYMBOLIC_NAME = "org.eclipse.jdt.ui.editors.textfont";

    private int changed = 0;

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
        FontData fontData = this.getFontData();
        int after = fontData.getHeight() + diff;
        if (after == 0) {
            return;
        }
        this.changed += diff;
        fontData.setHeight(after);
        this.setFontData(fontData);
    }

    void toLarge() {
        this.changeSize(1);
    }

    void toSmall() {
        this.changeSize(-1);
    }

    void reset() {
        this.changeSize(-this.changed);
        this.changed = 0;
    }

    private FontData getFontData() {
        FontRegistry registry = JFaceResources.getFontRegistry();
        Font font = registry.get(SYMBOLIC_NAME);
        FontData[] fontData = font.getFontData();
        if (fontData.length < 1) {
            //TODO FontData not found.
        }
        return fontData[0];
    }

    private void setFontData(FontData fontData) {
        JFaceResources.getFontRegistry().put(SYMBOLIC_NAME,
                new FontData[] { fontData });
    }
}
