package cube.services;

import com.google.common.base.Preconditions;
import cube.stages.Stage;
import cube.stages.SubStage;

import javax.swing.*;

/**
 * Singleton class for managing components.
 *
 * @author Wenyu
 * @since 1/5/16
 */
public class ComponentManager {

    private static final ComponentManager MANAGER = new ComponentManager();

    private Stage containerStage;
    private SubStage[] subStages;

    private ComponentManager() {

    }

    public static ComponentManager getInstance() {
        return MANAGER;
    }

    public ComponentManager register(final Stage containerStage) {
        this.containerStage = Preconditions.checkNotNull(containerStage, "containerStage must not be null.");

        return this;
    }

    public ComponentManager register(final SubStage[] subStages) {
        this.subStages = null == subStages ? new SubStage[] {} : subStages;

        return this;
    }

    /**
     * Add all sub stages to container stage.
     */
    public void add() {
        Preconditions.checkNotNull(containerStage, "containerStage has not been registered.");

        for (SubStage subStage : subStages) {
            containerStage.add(subStage);
        }

        SwingUtilities.getWindowAncestor(containerStage).revalidate();
        SwingUtilities.getWindowAncestor(containerStage).repaint();
    }

    /**
     * Remove all sub stages from container stage.
     */
    public void remove() {
        Preconditions.checkNotNull(containerStage, "containerStage has not been registered.");

        for (SubStage subStage : subStages) {
            containerStage.remove(subStage);
        }

        SwingUtilities.getWindowAncestor(containerStage).revalidate();
        SwingUtilities.getWindowAncestor(containerStage).repaint();
    }

    /**
     * Rest container stage.
     */
    public void reset() {
        containerStage.reset();
    }
}
