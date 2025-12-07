package infrastructure.rules

import org.kie.api.KieServices
import org.kie.api.runtime.KieSession

class DroolsConfig {
    fun newSession(): KieSession {
        val kieServices = KieServices.Factory.get()

        val kieContainer = kieServices.newKieClasspathContainer()

        return kieContainer.newKieSession("CarTroubleshootSession")
    }
}