package src.data.campaign.econ.industry;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.econ.CommodityOnMarketAPI;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.util.Pair;

public class bluemoon_cumfactory extends BaseIndustry {

    @Override
    public void apply() {
        super.apply(true);
        int size = this.market.getSize();
        this.demand("bluemoon_milk", size-1);
        this.supply("food", size+1);

        Pair<String, Integer> deficit = this.getMaxDeficit(new String[]{"bluemoon_milk"});
        this.applyDeficitToProduction(1, deficit, new String[]{"food"});
        if (!this.isFunctional()) {
            this.supply.clear();
        }

    }

    public void unapply() {
        super.unapply();
    }

    protected boolean canImproveToIncreaseProduction() {
        return true;
    }
}
