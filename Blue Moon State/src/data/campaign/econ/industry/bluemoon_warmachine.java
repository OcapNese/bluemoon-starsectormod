package src.data.campaign.econ.industry;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.SpecialItemData;
import com.fs.starfarer.api.campaign.econ.CommodityOnMarketAPI;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.util.Pair;

import java.awt.*;

import static com.fs.starfarer.api.impl.campaign.econ.impl.GroundDefenses.DEFENSE_BONUS_BASE;
import static com.fs.starfarer.api.impl.campaign.econ.impl.GroundDefenses.DEFENSE_BONUS_BATTERIES;

public class bluemoon_warmachine extends BaseIndustry {

    public static float ORBITAL_WORKS_QUALITY_BONUS = 0.2F;
    public static String POLLUTION_ID = "pollution";
    public static float DAYS_BEFORE_POLLUTION = 0.0F;
    public static float DAYS_BEFORE_POLLUTION_PERMANENT = 90.0F;
    protected boolean permaPollution = false;
    protected boolean addedPollution = false;
    protected float daysWithNanoforge = 0.0F;

    public bluemoon_warmachine() {
    }

    public void apply() {
        super.apply(true);
        int size = this.market.getSize();
        boolean bluemoon_works = "bluemoon_warmachine".equals(this.getId());
        int shipBonus = 0;
        float qualityBonus = 0.0F;
        if (bluemoon_works) {
            qualityBonus = ORBITAL_WORKS_QUALITY_BONUS;
        }
        float mult = this.getDeficitMult(new String[]{"supplies", "marines", "hand_weapons"});

        this.demand("metals", size);
        this.demand("rare_metals", size - 2);
        this.supply("heavy_machinery", size - 2);
        this.supply("supplies", size - 2);
        this.supply("hand_weapons", size - 2);
        this.supply("ships", size - 2);
        this.supply("luxury_goods", size - 2);
        this.supply("drugs", size - 2);
        this.supply("bluemoon_milk",size);
        if (shipBonus > 0) {
            this.supply(1, "ships", shipBonus, "Orbital works");
        }

        Pair<String, Integer> deficit = this.getMaxDeficit(new String[]{"metals", "rare_metals"});
        int maxDeficit = size - 3;
        if ((Integer)deficit.two > maxDeficit) {
            deficit.two = maxDeficit;
        }

        this.applyDeficitToProduction(2, deficit, new String[]{"heavy_machinery", "supplies", "hand_weapons", "ships"});
        if (qualityBonus > 0.0F) {
            this.market.getStats().getDynamic().getMod("production_quality_mod").modifyFlat(this.getModId(1), qualityBonus, "Orbital works");
        }

        float stability = this.market.getPrevStability();
        if (stability < 5.0F) {
            float stabilityMod = (stability - 5.0F) / 5.0F;
            stabilityMod *= 0.5F;
            this.market.getStats().getDynamic().getMod("production_quality_mod").modifyFlat(this.getModId(0), stabilityMod, this.getNameForModifier() + " - low stability");
        }

        if (!this.isFunctional()) {
            this.supply.clear();
            this.unapply();
        }

        float bonus = bluemoon_works ? DEFENSE_BONUS_BATTERIES : DEFENSE_BONUS_BASE;
        this.market.getStats().getDynamic().getMod("ground_defenses_mod").modifyMult(this.getModId(), 1.0F + bonus * mult, this.getNameForModifier());
        if (!this.isFunctional()) {
            this.supply.clear();
            this.unapply();
        }

    }

    public void unapply() {
        super.unapply();
        this.market.getStats().getDynamic().getMod("production_quality_mod").unmodifyFlat(this.getModId(0));
        this.market.getStats().getDynamic().getMod("production_quality_mod").unmodifyFlat(this.getModId(1));
    }

    protected void addPostDemandSection(TooltipMakerAPI tooltip, boolean hasDemand, Industry.IndustryTooltipMode mode) {
        if (mode != IndustryTooltipMode.NORMAL || this.isFunctional()) {
            boolean works = "bluemoon_warmachine".equals(this.getId());
            if (works) {
                float total = ORBITAL_WORKS_QUALITY_BONUS;
                String totalStr = "+" + Math.round(total * 100.0F) + "%";
                Color h = Misc.getHighlightColor();
                if (total < 0.0F) {
                    h = Misc.getNegativeHighlightColor();
                    totalStr = Math.round(total * 100.0F) + "%";
                }

                float opad = 10.0F;
                if (total >= 0.0F) {
                    tooltip.addPara("Ship quality: %s", opad, h, new String[]{totalStr});
                    tooltip.addPara("*Quality bonus only applies for the largest ship producer in the faction.", Misc.getGrayColor(), opad);
                }
            }
        }

    }

    public boolean isDemandLegal(CommodityOnMarketAPI com) {
        return true;
    }

    public boolean isSupplyLegal(CommodityOnMarketAPI com) {
        return true;
    }

    protected boolean canImproveToIncreaseProduction() {
        return true;
    }

    public boolean wantsToUseSpecialItem(SpecialItemData data) {
        return this.special != null && "corrupted_nanoforge".equals(this.special.getId()) && data != null && "pristine_nanoforge".equals(data.getId()) ? true : super.wantsToUseSpecialItem(data);
    }

    public void advance(float amount) {
        super.advance(amount);
        if (this.special != null) {
            float days = Global.getSector().getClock().convertToDays(amount);
            this.daysWithNanoforge += days;
            this.updatePollutionStatus();
        }

    }

    protected void updatePollutionStatus() {
        if (this.market.hasCondition("habitable")) {
            if (this.special != null) {
                if (!this.addedPollution && this.daysWithNanoforge >= DAYS_BEFORE_POLLUTION) {
                    if (this.market.hasCondition(POLLUTION_ID)) {
                        this.permaPollution = true;
                    } else {
                        this.market.addCondition(POLLUTION_ID);
                        this.addedPollution = true;
                    }
                }

                if (this.addedPollution && !this.permaPollution && this.daysWithNanoforge > DAYS_BEFORE_POLLUTION_PERMANENT) {
                    this.permaPollution = true;
                }
            } else if (this.addedPollution && !this.permaPollution) {
                this.market.removeCondition(POLLUTION_ID);
                this.addedPollution = false;
            }

        }
    }

    public boolean isPermaPollution() {
        return this.permaPollution;
    }

    public void setPermaPollution(boolean permaPollution) {
        this.permaPollution = permaPollution;
    }

    public boolean isAddedPollution() {
        return this.addedPollution;
    }

    public void setAddedPollution(boolean addedPollution) {
        this.addedPollution = addedPollution;
    }

    public float getDaysWithNanoforge() {
        return this.daysWithNanoforge;
    }

    public void setDaysWithNanoforge(float daysWithNanoforge) {
        this.daysWithNanoforge = daysWithNanoforge;
    }

    public void setSpecialItem(SpecialItemData special) {
        super.setSpecialItem(special);
        this.updatePollutionStatus();
    }
    public boolean isAvailableToBuild() {
        return false;
    }

    public boolean showWhenUnavailable() {
        return false;
    }
}
