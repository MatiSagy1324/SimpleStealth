package tyfyter.stealth.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import tyfyter.stealth.StealthMod;

@Mixin(PlayerEntity.class)
public abstract class StealthPlayerMixin extends LivingEntity {
	protected StealthPlayerMixin(EntityType<? extends LivingEntity> entityType, World world){
		super(entityType, world);
	}
	//@Inject(at = @At("InjectionPointHere"), method = "attack")
	public void attack(Entity targ, CallbackInfo info){
		LivingEntity target = (LivingEntity)targ;
		float dmg = 1;
		float mag = 1;
		float h;
		boolean a;
		boolean b;
		int c;
		int d;
		boolean crit = false;
		if(!StealthMod.inViewRange(target, this.getVisibilityBoundingBox().getCenter(), target.getAttributeValue(StealthMod.GENERIC_FOLLOW_ANGLE))){
			if(crit){
				dmg *= 1.5f;
				mag *= 1.75f;
			}else{
				dmg *= 1.75f;
				mag *= 1.75f;
			}
			crit = true;
			changebl3(crit);
		}
	}
	@ModifyVariable(at = @At("LOAD"), method = "attack", ordinal = 0)
	public float changef(float f){
		return f;
	}
	@ModifyVariable(at = @At("LOAD"), method = "attack", ordinal = 1)
	public float changeg(float g){
		return g;
	}
	@ModifyVariable(at = @At("LOAD"), method = "attack", ordinal = 0)
	public boolean changebl3(boolean bl3){
		return bl3;
	}
}
