package chaos.amyshield;

import chaos.amyshield.Item.ModItems;
import chaos.amyshield.util.BlockingListener;
import chaos.amyshield.util.DoubleJumpListener;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AmethystShield implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("amyshield");

	public static String MOD_ID = "amyshield";

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		BlockingListener.init();
		DoubleJumpListener.init();

		LOGGER.info("Hello Fabric world!");
	}
}