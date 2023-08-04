package tyfyter.stealth.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tyfyter.stealth.StealthMod;

@Mixin(PlayerEntity.class)
public abstract class StealthPlayerMixin extends LivingEntity {
	protected StealthPlayerMixin(EntityType<? extends LivingEntity> entityType, World world){
		super(entityType, world);
	}
	@Inject(at = @At("INVOKE"), method = "attack")
	public void attack(Entity targ, CallbackInfo info){
		if(targ instanceof LivingEntity target){
			if(!StealthMod.inViewRange(target, this.getVisibilityBoundingBox().getCenter(), target.getAttributeValue(StealthMod.GENERIC_FOLLOW_ANGLE))){
				fallDistance = 1f;
				onGround = false;
			}
		}
	}
}
