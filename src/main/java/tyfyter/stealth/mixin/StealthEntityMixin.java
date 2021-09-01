package tyfyter.stealth.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tyfyter.stealth.StealthMod;

@Mixin(LivingEntity.class)
public abstract class StealthEntityMixin extends Entity {
	public StealthEntityMixin(EntityType<?> type, World world){
		super(type, world);
	}
	
	@Shadow public abstract double getAttributeValue(EntityAttribute attribute);
	
	@Inject(at = @At("RETURN"), method = "createLivingAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;", require = 1, allow = 1)
	private static void addAt(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir){
		cir.getReturnValue().add(StealthMod.GENERIC_FOLLOW_ANGLE, 0.1D);
	}
	
	@Inject(at = @At("INVOKE"), method = "canSee", cancellable = true)
	private void canSee(Entity entity, CallbackInfoReturnable<Boolean> cir) {
		if(!StealthMod.inViewRange(this, entity.getVisibilityBoundingBox().getCenter(), this.getAttributeValue(StealthMod.GENERIC_FOLLOW_ANGLE))){
			cir.setReturnValue(false);
			cir.cancel();
		}
	}
}
