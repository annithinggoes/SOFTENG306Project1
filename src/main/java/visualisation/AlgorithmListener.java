package visualisation;

import algorithm.Algorithm;
import algorithm.AlgorithmBranchDetails;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.util.Duration;
import scheduler.State;
import visualisation.processor.helpers.GUIUpdater;
import visualisation.processor.listeners.SchedulerListener;

/**
 * A listener which is used to update data when certain events occur
 */
public class AlgorithmListener implements SchedulerListener {
    private int numberOfProcessors;
    private State state;
    private String path;
    private long timeElapsed;
    private int branchCounter;
    private AlgorithmBranchDetails details;

    public AlgorithmListener() {
        details = new AlgorithmBranchDetails();
    }

    @Override
    public void setNumberOfProcessors(int numberOfProcessors) {
        this.numberOfProcessors = numberOfProcessors;
    }

    @Override
    public int getNumberOfProcessors(){
        return numberOfProcessors;
    }

    @Override
    public void setState(State state) {
        this.state = state;
        GUIUpdater.getInstance().updateProcessChart();
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public void setFileName(String path) {
        this.path = path;
    }

    @Override
    public String getFileName() {
        return path;
    }

    @Override
    public void updateTimeElapsed(String time) {
        GUIUpdater.getInstance().updateTimeLabel(time);
    }

    @Override
    public long getTimeElapsed() {
        return timeElapsed;
    }

    @Override
    public void updateBranchCounter() {
        //details.setBranchesSeen(AlgorithmDataStorage);
        GUIUpdater.getInstance().updateBranchLabel(AlgorithmDataStorage.getInstance().getDetails());
//        Timeline timeline = new Timeline(
//                new KeyFrame(
//                        Duration.millis( 1000 ),
//                        event -> {
//
//
//                        }
//                )
//        );
//        timeline.setCycleCount( Animation.INDEFINITE );
//        timeline.play();


//        Task<Void> task = new Task<Void>() {
//            @Override
//            protected Void call() {
//                branchCounter++;
//                details.setBranchesSeen(branchCounter);
//                GUIUpdater.getInstance().updateBranchLabel(details);
//                return null;
//            }
//        };
//        new Thread(task).start();

    }

    @Override
    public int getBranchCounter(){
        return branchCounter;
    }
}
