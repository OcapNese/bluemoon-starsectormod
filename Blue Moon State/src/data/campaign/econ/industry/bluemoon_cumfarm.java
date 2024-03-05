package src.data.campaign.econ.industry;

import com.fs.starfarer.api.util.Pair;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.CommodityOnMarketAPI;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;

public class bluemoon_cumfarm extends BaseIndustry {

    @Override
    public void apply() {
        super.apply(true);
        int size = this.market.getSize();
        this.demand("organics", size - 2);
        this.demand("heavy_machinery", size - 2);
        this.supply("bluemoon_milk", size - 1);
        Pair<String, Integer> deficit = this.getMaxDeficit(new String[]{"organics"});
        this.applyDeficitToProduction(1, deficit, new String[]{"food"});
        if (!this.isFunctional()) {
            this.supply.clear();
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
}
