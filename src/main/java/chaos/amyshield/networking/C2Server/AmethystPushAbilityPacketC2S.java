package chaos.amyshield.networking.C2Server;

import chaos.amyshield.AmethystShield;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class AmethystPushAbilityPacketC2S {
    public static void receiver(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        server.execute(() -> {
            List<Entity> entityList = new ArrayList<>(getEntitiesAroundPlayer(AmethystShield.AMETHYST_PUSH_RADIUS, player));
            for (Entity entity : entityList) {
                if (entity instanceof LivingEntity && !((LivingEntity) entity).isDead() && !entity.isRemoved()) {
                    pushEntityAwayFromPlayer(entity, AmethystShield.AMETHYST_PUSH_STRENGTH_X, player);
                    entity.damage(player.getDamageSources().indirectMagic(player, player), AmethystShield.AMETHYST_PUSH_DAMAGE);
                }
            }
        });
    }

    private static List<Entity> getEntitiesAroundPlayer(double radius, PlayerEntity player) {
        World world = player.getWorld();
        Box box = new Box(
                player.getX() - radius, player.getY() - radius, player.getZ() - radius,
                player.getX() + radius, player.getY() + radius, player.getZ() + radius
        );
        return world.getOtherEntities(player, box);
    }

    private static void pushEntityAwayFromPlayer(Entity entity, double speed, PlayerEntity player) {
        Vec2f playerPos = new Vec2f((float) -player.getPos().getX(), (float) -player.getPos().getZ());
        Vec2f entityPos = new Vec2f((float) entity.getPos().getX(), (float) entity.getPos().getZ());
        Vec2f direction = entityPos.add(playerPos);

        Vec2f normalizedDirection = direction.normalize();

        Vec2f velocity = normalizedDirection.multiply((float) (speed + (player.distanceTo(entity) * 0.8)));

        entity.addVelocity(new Vec3d(velocity.x, AmethystShield.AMETHYST_PUSH_STRENGTH_Y, velocity.y));
    }
}