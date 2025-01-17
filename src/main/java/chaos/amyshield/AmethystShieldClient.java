package chaos.amyshield;

import chaos.amyshield.networking.ModPackets;
import chaos.amyshield.particles.client.ModClientParticles;
import chaos.amyshield.ui.client.ChargeHudOverlay;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class AmethystShieldClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModClientParticles.registerModParticlesClient();
		ModPackets.registerGlobalReceiversS2C();
		HudRenderCallback.EVENT.register(new ChargeHudOverlay());
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
	}
}