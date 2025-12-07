package infrastructure.rules

import org.kie.api.KieServices
import org.kie.api.runtime.KieContainer
import org.kie.api.runtime.KieSession

class DroolsConfig {
    private val kieServices: KieServices = KieServices.Factory.get()
    private val kieContainer: KieContainer = kieServices.newKieClasspathContainer()

    fun newSession(): KieSession {
        return kieContainer.newKieSession("CarTroubleshootSession")
    }
}