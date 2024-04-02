package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.Stats;

public class Bluemoon_catcrew extends BaseHullMod {
	public static float CORONA_EFFECT_MULT = 0.25f;
	public static float ENERGY_DAMAGE_MULT = 0.9f;

	public static float SMOD_CORONA_EFFECT_MULT = 0f;

	public static float REPAIR_RATE_BONUS = 50f;
	public static float CR_RECOVERY_BONUS = 50f;
	public static float REPAIR_BONUS = 50f;

	public static float SMOD_REPAIR_BONUS = 25f;
	public static float SMOD_OVERLOAD_BONUS = 33f;

	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		stats.getEnergyDamageTakenMult().modifyMult(id, ENERGY_DAMAGE_MULT);
		stats.getEnergyShieldDamageTakenMult().modifyMult(id, ENERGY_DAMAGE_MULT);

		boolean sMod = isSMod(stats);
		float mult = CORONA_EFFECT_MULT;
		if (sMod) mult = SMOD_CORONA_EFFECT_MULT;
		stats.getDynamic().getStat(Stats.CORONA_EFFECT_MULT).modifyMult(id, mult);

		float bonus = REPAIR_BONUS;
		if (sMod) bonus += SMOD_REPAIR_BONUS;
		stats.getCombatEngineRepairTimeMult().modifyMult(id, 1f - bonus * 0.01f);
		stats.getCombatWeaponRepairTimeMult().modifyMult(id, 1f - bonus * 0.01f);

		if (sMod) {
			stats.getOverloadTimeMod().modifyMult(id, 1f - SMOD_OVERLOAD_BONUS * 0.01f);
		}
	}

	public String getSModDescriptionParam(int index, HullSize hullSize) {
		if (index == 0) return "" + (int) Math.round((1f - SMOD_CORONA_EFFECT_MULT) * 100f) + "%";
		return null;
	}
	public String getDescriptionParam(int index, HullSize hullSize) {
		if (index == 0) return "" + (int) Math.round((1f - CORONA_EFFECT_MULT) * 100f) + "%";
		return null;
	}


}