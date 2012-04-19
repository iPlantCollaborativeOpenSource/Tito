package org.iplantc.core.tito.client;

import org.iplantc.core.jsonutil.JsonUtil;
import org.iplantc.core.tito.client.panels.TemplateTabPanel;
import org.iplantc.core.tito.client.services.EnumerationServices;
import org.iplantc.core.uiapplications.client.events.AnalysisSelectEvent;
import org.iplantc.core.uicommons.client.ErrorHandler;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.event.BorderLayoutEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Defines the overall layout for the root panel of the web application.
 * 
 * @author sriram
 */
public class TitoPanel extends LayoutContainer {
    private TemplateTabPanel pnlAppTemplate;
    private Component content;
    private final FlowLayout layout;
    public static String tag;

    /**
     * Default constructor.
     * 
     * @param tag a window tag for this panel
     */
    public TitoPanel(String winTag) {
        tag = winTag;
        // build top level layout
        layout = new FlowLayout(0);

        // make sure we re-draw when a panel expands
        layout.addListener(Events.Expand, new Listener<BorderLayoutEvent>() {
            @Override
            public void handleEvent(BorderLayoutEvent be) {
                layout();
            }
        });
        setLayout(layout);
    }
    
    public void cleanup() {
        cleanupTemplateTabPanel();
    }

    private void cleanupTemplateTabPanel() {
        if (pnlAppTemplate != null) {
            pnlAppTemplate.cleanup();
        }
    }

    /**
     * Replace the contents of the center panel.
     * 
     * @param view a new component to set in the center of the BorderLayout.
     */
    public void replaceContent(Component view) {
        if (content != null) {
            remove(content);
        }

        content = view;

        BorderLayoutData data = new BorderLayoutData(LayoutRegion.CENTER);
        data.setMargins(new Margins(0));

        if (content != null) {
            add(content, data);
        }

        if (isRendered()) {
            layout();
        }
    }

    public void reset() {
        // clear our center
        if (content != null) {
            remove(content);
        }

        content = null;
    }

    public void newTool() {
    	pnlAppTemplate = new TemplateTabPanel();
    	replaceContent(pnlAppTemplate);
    }
    
    public void loadFromJson(JSONObject obj) {
    	 pnlAppTemplate = new TemplateTabPanel(obj, true);
    	 replaceContent(pnlAppTemplate);
    }

    public boolean isDirty() {
    	return pnlAppTemplate.templateChanged();
    }

    public void load(final String id) {
        EnumerationServices services = new EnumerationServices();
        services.getIntegrationById(id, new AsyncCallback<String>() {

            @Override
            public void onSuccess(String result) {
                JSONArray jsonObjects = JsonUtil.getArray(JsonUtil.getObject(result), "objects"); //$NON-NLS-1$
                if (jsonObjects != null && jsonObjects.size() > 0) {
                    pnlAppTemplate = new TemplateTabPanel(jsonObjects.get(0).isObject(), false);
                    replaceContent(pnlAppTemplate);
                    AnalysisSelectEvent event = new AnalysisSelectEvent(tag, null, id);
                    org.iplantc.core.uicommons.client.events.EventBus.getInstance().fireEvent(event);
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                ErrorHandler.post(I18N.DISPLAY.cantLoadTemplate(), caught);
            }
        });
    }


    /**
     * 
     * get current tito config for state management
     * 
     * @return
     */
    public JSONObject getTitoConfig() {
    	if(pnlAppTemplate != null) {
    		return pnlAppTemplate.toJson();
    	}
    	return null;
    }

    public String getId() {
        if (pnlAppTemplate != null) {
            return pnlAppTemplate.getTitoId();
        }

        return null;
    }

}