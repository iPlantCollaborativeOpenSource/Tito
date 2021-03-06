package org.iplantc.core.tito.client.events;

import com.google.gwt.event.shared.GwtEvent;
/**
 * an event that is fired when a new project is created
 * 
 * @author sriram
 *
 */
public class NewProjectEvent extends GwtEvent<NewProjectEventHandler> {
    /**
     * Defines the GWT Event Type.
     * 
     * @see org.iplantc.core.tito.client.events.NewProjectEventHandler
     */
    public static final GwtEvent.Type<NewProjectEventHandler> TYPE = new GwtEvent.Type<NewProjectEventHandler>();

    public enum ProjectType {
        Tool
    }

    private ProjectType typeProject;

    /**
     * Instantiate from a project type.
     * 
     * @param typeProject type of project.
     */
    public NewProjectEvent(ProjectType typeProject) {
        this.typeProject = typeProject;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void dispatch(NewProjectEventHandler handler) {
        switch (typeProject) {
            case Tool:
                handler.newTool();
                break;

            default:
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<NewProjectEventHandler> getAssociatedType() {
        return TYPE;
    }
}
