package tyfyter.stealth;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import tyfyter.stealth.mixin.EntityATInvoker;

public class StealthMod implements ModInitializer {
	public static final EntityAttribute GENERIC_FOLLOW_ANGLE = new ClampedEntityAttribute("attribute.name.generic.target_angle_range", 0.25D, -1D, 1D);
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		Registry.register(Registry.ATTRIBUTE, new Identifier("stealthmod", "dot"), GENERIC_FOLLOW_ANGLE);
	}
	public static Vec3d getEyePos(Entity entity){
		return new Vec3d(entity.getX(), entity.getEyeY(), entity.getZ());
	}
	public static boolean inViewRange(Entity viewer, Vec3d target, double range){
		Vec3d eyes = ((EntityATInvoker)viewer).stealthInvokeGetRotationVector(viewer.getPitch(), viewer.getHeadYaw());
		Vec3d eyepos = StealthMod.getEyePos(viewer);
		Vec3d diff = target.subtract(eyepos);
		double d = diff.x * diff.x + diff.y * diff.y + diff.z * diff.z;
		//2 (minimum invisibility detection range) squared
		if(d < 4){
			return true;
		}
		d = Math.sqrt(d);
		//use length to normalize
		diff =  d < 1.0E-4D ? Vec3d.ZERO : new Vec3d(diff.x / d, diff.y / d, diff.z / d);
		double dot = diff.dotProduct(eyes);
		return dot>=range;
	}
}
