package w4engineping;

import java.security.Principal;

import eu.w4.engine.client.bpmn.w4.infrastructure.DefinitionsIdentifier;
import eu.w4.engine.client.bpmn.w4.process.ProcessIdentifier;
import eu.w4.engine.client.bpmn.w4.runtime.InstanceState;
import eu.w4.engine.client.bpmn.w4.runtime.ProcessInstance;
import eu.w4.engine.client.bpmn.w4.runtime.ProcessInstanceAttachment;
import eu.w4.engine.client.bpmn.w4.runtime.ProcessInstanceIdentifier;
import eu.w4.engine.client.service.AuthenticationService;
import eu.w4.engine.client.service.DefinitionsService;
import eu.w4.engine.client.service.EngineService;
import eu.w4.engine.client.service.EngineServiceFactory;
import eu.w4.engine.client.service.ObjectFactory;
import eu.w4.engine.client.service.ProcessService;

public class Ping {
	public Ping() {
	}

	public static void main(String[] args) throws Exception {
		if (args.length != 3) {
			System.err
					.println("useage: java w4engineping.Ping login password maxTestLoop\n where login is a user login, password is the related user password and maximum number of test loop ");
			System.exit(-1);
		}
		System.out.println(new Ping().getResult(args[0], args[1], new Integer(
				args[2])));
	}

	public String getResult(String login, String password, int maxStateTestLoop)
			throws Exception {
		EngineService engineService = EngineServiceFactory
				.getEngineService(null);
		ProcessService processService = engineService.getProcessService();
		DefinitionsService defService = engineService.getDefinitionsService();
		AuthenticationService authService = engineService
				.getAuthenticationService();
		Principal usrPrincipal = authService.login(login, password);
		try {
			ObjectFactory objFactory = engineService.getObjectFactory();
			DefinitionsIdentifier defId = objFactory.newDefinitionsIdentifier();
			defId.setId("pingDef");
			if (defService.definitionsExists(usrPrincipal, defId)) {
				ProcessIdentifier procId = objFactory.newProcessIdentifier();
				procId.setDefinitionsIdentifier(defId);
				procId.setId("ping");
				ProcessInstanceIdentifier processInstanceId = processService
						.instantiateProcess(usrPrincipal, null, procId, null,
								null, null, null);

				ProcessInstanceAttachment procInstAtt = null;
				ProcessInstance procInst = processService.getProcessInstance(
						usrPrincipal, processInstanceId, procInstAtt);
				InstanceState procInstState = procInst.getState();
				while (procInstState == InstanceState.READY
						|| procInstState == InstanceState.ACTIVE) {
					procInst = processService.getProcessInstance(usrPrincipal,
							processInstanceId, procInstAtt);
					procInstState = procInst.getState();
					Thread.sleep(500);
				}
				String result = null;
				if (procInstState == InstanceState.COMPLETED) {
					result = "OK";
				} else {
					result = "test State is : " + procInstState;
				}
				processService.deleteProcessInstances(usrPrincipal,
						processInstanceId);
				return result;

			} else {
				return "Error : you must deploy pingDef.bpmn file on your server to use W4 Engine Ping";
			}
		} finally {
			if (usrPrincipal != null) {
				authService.logout(usrPrincipal);
			}
		}
	}
}
