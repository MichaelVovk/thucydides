package net.thucydides.core.steps;


import net.thucydides.core.model.Story;
import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.model.TestResult;
import static org.apache.commons.lang.StringUtils.leftPad;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class SampleStepListener implements StepListener {

    private final StringBuffer buffer = new StringBuffer();

    int currentIndent = 0;

    @Override
    public String toString() {
        return buffer.toString();
    }

    public void testRunStartedFor(Class<?> storyClass) {
        push();
    }

    private void push() {
        currentIndent++;
    }

    private void pop() {
        currentIndent--;
    }

    public void testRunStartedFor(Story story) {
    }

    public void testStarted(String description) {
        buffer.append("TEST: " + description).append("\n");
        push();
    }

    public void testFinished(TestStepResult result) {
        pop();
        buffer.append("TEST DONE").append("\n");
    }

    public void stepStarted(ExecutedStepDescription description) {
        buffer.append(leftPad("STEP STARTED:" + description.getName(),currentIndent)).append("\n");
        push();
    }

    public void stepFinished(ExecutedStepDescription description) {
        pop();
        buffer.append(leftPad("STEP DONE:" + description.getName(),currentIndent)).append("\n");
    }

    public void stepGroupStarted(String description) {
    }

    public void stepGroupStarted(ExecutedStepDescription description) {
    }

    public void stepGroupFinished() {
    }

    public void stepGroupFinished(TestResult result) {
    }

    public void stepSucceeded() {
    }

    public void stepFailed(StepFailure failure) {
    }

    public void stepIgnored(ExecutedStepDescription description) {
    }

    public List<TestOutcome> getTestOutcomes() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateCurrentStepStatus(TestResult result) {
    }

    public void setDriver(WebDriver driver) {
    }

    public WebDriver getDriver() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean aStepHasFailed() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void noStepsHaveFailed() {
    }

    public Throwable getStepError() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isDataDriven() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
