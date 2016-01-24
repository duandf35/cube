package cube.services.factories;

import com.google.common.base.Preconditions;
import cube.stages.ContainerStage;
import cube.stages.ScoreRecordStage;
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

    private ContainerStage containerContainerStage;
    private SubStage[] subStages;

    private ScoreRecordStageManager scoreRecordStageManager;
    private ScoreRecordStage[] scoreRecordStages;

    private ComponentManager() {

    }

    public static ComponentManager getInstance() {
        return MANAGER;
    }

    /**
     * Register container stage.
     * @param  containerContainerStage the container stage
     * @return the manager itself for method chaining
     */
    public ComponentManager register(final ContainerStage containerContainerStage) {
        this.containerContainerStage = Preconditions.checkNotNull(containerContainerStage, "containerContainerStage must not be null.");

        return this;
    }

    /**
     * Register sub stages.
     * @param  subStages the sub stages.
     * @return the manager itself for method chaining
     */
    public ComponentManager register(final SubStage[] subStages) {
        this.subStages = null == subStages ? new SubStage[] {} : subStages;

        return this;
    }

    /**
     * Register score record stage manager.
     * @param  scoreRecordStageManager the score record stage manager
     * @return the manager itself for method chaining
     */
    public ComponentManager register(final ScoreRecordStageManager scoreRecordStageManager) {
        this.scoreRecordStageManager = scoreRecordStageManager;

        return this;
    }

    /**
     * Add all sub stages to container stage.
     */
    public void addAllSubStages() {
        Preconditions.checkNotNull(containerContainerStage, "containerContainerStage has not been registered.");

        for (SubStage subStage : subStages) {
            containerContainerStage.add(subStage);
        }

        validateEDT();
    }

    /**
     * Remove all sub stages from container stage.
     */
    public void removeAllSubStages() {
        Preconditions.checkNotNull(containerContainerStage, "containerContainerStage has not been registered.");

        for (SubStage subStage : subStages) {
            containerContainerStage.remove(subStage);
        }

        validateEDT();
    }

    /**
     * Add all score record stages to container stage.
     */
    public void addRecords() {
        scoreRecordStages = scoreRecordStageManager.buildStages().getStages();
        for (ScoreRecordStage stage : scoreRecordStages) {
            containerContainerStage.add(stage);
        }

        validateEDT();
    }

    /**
     * Remove all score record stages to container stage.
     */
    public void removeRecords() {
        if (null != scoreRecordStages) {
            for (ScoreRecordStage stage: scoreRecordStages) {
                containerContainerStage.remove(stage);
            }

            validateEDT();
        }
    }

    /**
     * Pops out final score dialog.
     */
    public void showFinalScore() {
        containerContainerStage.showFinalScore();
    }

    /**
     * Hide final score dialog.
     */
    public void hideFinalScore() {
        containerContainerStage.hideFinalScore();
    }

    /**
     * Rest container stage.
     */
    public void reset() {
        containerContainerStage.reset();
    }

    /**
     * Notify Swing EDT to validate container changes.
     */
    private void validateEDT() {
        SwingUtilities.getWindowAncestor(containerContainerStage).revalidate();
        SwingUtilities.getWindowAncestor(containerContainerStage).repaint();
    }
}
