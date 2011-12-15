package net.thucydides.junit.runners.integration;

import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.steps.StepEventBus;
import net.thucydides.junit.runners.AbstractTestStepRunnerTest;
import net.thucydides.junit.runners.ThucydidesRunner;
import net.thucydides.samples.SamplePassingScenarioUsingHtmlUnit;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WhenRunningTestScenariosInParallel extends AbstractTestStepRunnerTest {

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void clearEventBus() throws Exception {
        StepEventBus.getEventBus().clear();

    }


    public class ScenarioThread extends Thread {

        ThucydidesRunner runner;

        public ScenarioThread()  throws InitializationError {
            runner = new ThucydidesRunner(SamplePassingScenarioUsingHtmlUnit.class);
        }

        public void run() {
            runner.run(new RunNotifier());
        }
        
        public List<TestOutcome> getTestOutcomes() {
            return runner.getTestOutcomes();    
        }

    }
    
    @Test
    public void the_test_runner_records_the_steps_as_they_are_executed() throws InitializationError, InterruptedException {

        List<ScenarioThread> threads = new ArrayList<ScenarioThread>();

        for(int i = 0; i < 10; i++) {
            threads.add(new ScenarioThread());
        }

        for(ScenarioThread thread : threads) {
            thread.start();
        }

        for(ScenarioThread thread : threads) {
            thread.join();
        }

        for(ScenarioThread thread : threads)  {
            List<TestOutcome> executedSteps = thread.getTestOutcomes();

            assertThat(executedSteps.size(), is(3));
            TestOutcome testOutcome1 = executedSteps.get(0);
            TestOutcome testOutcome2 = executedSteps.get(1);
            TestOutcome testOutcome3 = executedSteps.get(2);

            assertThat(testOutcome1.getTitle(), is("Happy day scenario"));
            assertThat(testOutcome1.getMethodName(), is("happy_day_scenario"));
            assertThat(testOutcome1.getTestSteps().size(), is(4));

            assertThat(testOutcome2.getTitle(), is("Edge case 1"));
            assertThat(testOutcome2.getMethodName(), is("edge_case_1"));
            assertThat(testOutcome2.getTestSteps().size(), is(3));

            assertThat(testOutcome3.getTitle(), is("Edge case 2"));
            assertThat(testOutcome3.getMethodName(), is("edge_case_2"));
            assertThat(testOutcome3.getTestSteps().size(), is(2));
        }
    }

}