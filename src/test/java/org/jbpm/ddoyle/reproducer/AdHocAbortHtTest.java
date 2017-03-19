package org.jbpm.ddoyle.reproducer;

import org.jbpm.services.task.admin.listener.TaskCleanUpProcessEventListener;
import org.jbpm.test.JbpmJUnitBaseTestCase;
import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.internal.runtime.manager.context.ProcessInstanceIdContext;

public class AdHocAbortHtTest extends JbpmJUnitBaseTestCase{

	public AdHocAbortHtTest() {
		// Setup the datasource and enable persistence.
		super(true, true);
	}
	
	@Test
	public void testCompleteProcess() {
		RuntimeManager runtimeManager = createPpiRuntimeManager("ad-hoc-abort-ht.bpmn");
		RuntimeEngine runtimeEngine = getRuntimeEngine(ProcessInstanceIdContext.get());
		KieSession kieSession = runtimeEngine.getKieSession();
		
		kieSession.addEventListener(new TaskCleanUpProcessEventListener(runtimeEngine.getTaskService()));
		ProcessInstance pInstance = kieSession.startProcess("jbpm-abort-ht-issue.ad-hoc-abort-ht");
		kieSession.signalEvent("Milestone", null, pInstance.getId());
	}
	
    protected RuntimeManager createPpiRuntimeManager(String... process) {
    	return createRuntimeManager(Strategy.PROCESS_INSTANCE, null, process);
    }

}

