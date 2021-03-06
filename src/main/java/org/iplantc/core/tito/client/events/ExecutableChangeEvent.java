package org.iplantc.core.tito.client.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * 
 * An event that is fired when the user selects an executable for a tool
 * 
 * @author sriram
 * 
 */
public class ExecutableChangeEvent extends GwtEvent<ExecutableChangeEventHandler> {

    /**
     * Defines the GWT Event Type.
     * 
     * @see org.iplantc.core.tito.client.events.ExecutableChangeEventHandler
     */
    public static final GwtEvent.Type<ExecutableChangeEventHandler> TYPE = new GwtEvent.Type<ExecutableChangeEventHandler>();

    private String executable;
    private String id;

    /**
     * @param executable the executable whose name changed
     */
    public ExecutableChangeEvent(String id, String executable) {
        this.executable = executable;
        this.id = id;
    }

    /**
     * 
     * 
     * @return get id of DC
     */
    public String getId() {
        return id;
    }

    /**
     * @return the executable whose name changed
     */
    public String getExecutable() {
        return executable;
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<ExecutableChangeEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ExecutableChangeEventHandler handler) {
        handler.onChange(this);
    }

}
