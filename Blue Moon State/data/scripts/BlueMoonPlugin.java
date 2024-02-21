package data.scripts;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI.SurveyLevel;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.ids.Conditions;
import com.fs.starfarer.api.impl.campaign.ids.Industries;
import com.fs.starfarer.api.impl.campaign.ids.Skills;
import com.fs.starfarer.api.impl.campaign.ids.Submarkets;
import com.fs.starfarer.api.impl.campaign.procgen.StarAge;
import com.fs.starfarer.api.impl.campaign.procgen.StarSystemGenerator;
import com.fs.starfarer.api.impl.campaign.rulecmd.AddPotentialContact;

import static com.fs.starfarer.api.campaign.PersonImportance.LOW;
import static com.fs.starfarer.api.campaign.PersonImportance.VERY_HIGH;
import static com.fs.starfarer.api.characters.FullName.Gender.FEMALE;
import static com.fs.starfarer.api.characters.FullName.Gender.MALE;
import static com.fs.starfarer.api.impl.campaign.ids.Ranks.CITIZEN;
import static com.fs.starfarer.api.impl.campaign.ids.Ranks.FACTION_LEADER;

public class BlueMoonPlugin extends BaseModPlugin {
	@Override
	public void onNewGame() {
		SectorAPI sector = Global.getSector();
		StarSystemAPI system = sector.createStarSystem("condilar");

		PlanetAPI star = system.initStar(
				"condilar",
				"star_yellow",
				700,
				25427,
				17750,
				500);

		SectorEntityToken buoy = system.addCustomEntity("condilar_buoy", // unique id
				"Condilar Buoy", // name - if null, defaultName from custom_entities.json will be used
				"nav_buoy", // type of object, defined in custom_entities.json
				"blue_moon"); // faction
		buoy.setCircularOrbitPointingDown(star, 40, 2000, 70);

		SectorEntityToken relay = system.addCustomEntity("condilar_relay", // unique id
				"Condilar Relay", // name - if null, defaultName from custom_entities.json will be used
				"comm_relay", // type of object, defined in custom_entities.json
				"blue_moon"); // faction
		relay.setCircularOrbitPointingDown(star, 190, 3000, 70);

		SectorEntityToken array = system.addCustomEntity("condilar_array", // unique id
				"Condilar Array", // name - if null, defaultName from custom_entities.json will be used
				"sensor_array", // type of object, defined in custom_entities.json
				"blue_moon"); // faction
		array.setCircularOrbitPointingDown(star, 220, 2000, 70);

		SectorEntityToken condilar_gate = system.addCustomEntity("condilar_gate", // unique id
				"Condilar Gate", // name - if null, defaultName from custom_entities.json will be used
				"inactive_gate", // type of object, defined in custom_entities.json
				null); // faction
		condilar_gate.setCircularOrbit(star, 120, 2500, 70);

		PlanetAPI Phantu = system.addPlanet("Phantu", star, "Phantu", "gas_giant", 230, 350, 5000, 150);
		Phantu.setFaction("blue_moon");
		MarketAPI phantu_market = Global.getFactory().createMarket("phantu_market", Phantu.getName(), 3);

		phantu_market.setPlanetConditionMarketOnly(false); //This "market" only represents planet conditions.
		phantu_market.addCondition(Conditions.VERY_COLD); //It's a hot Jupiter so let's make it hot!
		phantu_market.addCondition(Conditions.DENSE_ATMOSPHERE); //It's a gas giant, so let's make it gassy!
		phantu_market.addCondition(Conditions.EXTREME_WEATHER);
		phantu_market.addCondition(Conditions.HIGH_GRAVITY);
		phantu_market.addCondition(Conditions.VOLATILES_PLENTIFUL);
		phantu_market.addCondition(Conditions.ORE_RICH);
		phantu_market.addCondition(Conditions.RARE_ORE_RICH);
		phantu_market.addCondition(Conditions.ORGANICS_ABUNDANT);
		phantu_market.setPrimaryEntity(Phantu); //Tell the "market" that it's on our planet.
		Phantu.setMarket(phantu_market); //Likewise, tell our planet that it has a "market".
		phantu_market.setSurveyLevel(SurveyLevel.FULL);
		phantu_market.getTariff().modifyFlat("generator", 0.3f);
		phantu_market.setPlanetConditionMarketOnly(false); //This "market" only represents planet conditions.
		phantu_market.addCondition(Conditions.POPULATION_3);
		phantu_market.setFactionId("blue_moon");
		phantu_market.addIndustry(Industries.POPULATION);
		phantu_market.addIndustry(Industries.SPACEPORT);
		phantu_market.addIndustry(Industries.MINING);
		phantu_market.addIndustry(Industries.REFINING);

		phantu_market.addSubmarket(Submarkets.SUBMARKET_STORAGE);
		phantu_market.addSubmarket(Submarkets.SUBMARKET_BLACK);
		phantu_market.addSubmarket(Submarkets.SUBMARKET_OPEN);


		PlanetAPI estoul = system.addPlanet("estoul", Phantu, "Estoul", "jungle", 10, 80, 700, 250);
		estoul.setFaction("blue_moon");
		MarketAPI Estoul_market = Global.getFactory().createMarket("Estoul_market", estoul.getName(), 5);

		Estoul_market.addCondition(Conditions.HABITABLE);
		Estoul_market.addCondition(Conditions.COLD);
		Estoul_market.addCondition(Conditions.ORE_RICH);
		Estoul_market.addCondition(Conditions.RARE_ORE_MODERATE);
		Estoul_market.addCondition(Conditions.ORGANICS_TRACE);
		Estoul_market.addCondition(Conditions.VOLATILES_DIFFUSE);
		Estoul_market.addCondition(Conditions.FARMLAND_BOUNTIFUL);
		Estoul_market.setPrimaryEntity(estoul); //Tell the "market" that it's on our planet.
		estoul.setMarket(Estoul_market); //Likewise, tell our planet that it has a "market".
		Estoul_market.setSurveyLevel(SurveyLevel.FULL);
		Estoul_market.getTariff().modifyFlat("generator", 0.4f);
		Estoul_market.setPlanetConditionMarketOnly(false); //This "market" only represents planet conditions.
		Estoul_market.addCondition(Conditions.POPULATION_5);
		Estoul_market.setFactionId("blue_moon");
		Estoul_market.addIndustry(Industries.POPULATION);
		Estoul_market.addIndustry(Industries.SPACEPORT);
		Estoul_market.addIndustry(Industries.ORBITALSTATION_MID);
		Estoul_market.addIndustry(Industries.FARMING);
		Estoul_market.addIndustry(Industries.ORBITALWORKS);
		Estoul_market.addIndustry(Industries.PATROLHQ);
		Estoul_market.addIndustry(Industries.LIGHTINDUSTRY);

		Estoul_market.addSubmarket(Submarkets.SUBMARKET_STORAGE);
		Estoul_market.addSubmarket(Submarkets.SUBMARKET_BLACK);
		Estoul_market.addSubmarket(Submarkets.SUBMARKET_OPEN);
		Estoul_market.addSubmarket(Submarkets.GENERIC_MILITARY);

		PlanetAPI Moonial = system.addPlanet("moonial", star, "Moonial", "tundra", 570, 175, 7000, 9000);
		Moonial.setFaction("blue_moon");
		MarketAPI Moonial_market = Global.getFactory().createMarket("Moonial_market", Moonial.getName(), 7);

		Moonial_market.setPlanetConditionMarketOnly(false); //This "market" only represents planet conditions.
		Moonial_market.addCondition(Conditions.COLD);
		Moonial_market.addCondition(Conditions.THIN_ATMOSPHERE); //It's a gas giant, so let's make it gassy!
		Moonial_market.addCondition(Conditions.MILD_CLIMATE);
		Moonial_market.addCondition(Conditions.LOW_GRAVITY);
		Moonial_market.addCondition(Conditions.RUINS_EXTENSIVE);
		Moonial_market.setPrimaryEntity(Moonial); //Tell the "market" that it's on our planet.
		Moonial.setMarket(Moonial_market); //Likewise, tell our planet that it has a "market".
		Moonial_market.setSurveyLevel(SurveyLevel.FULL);
		Moonial_market.getTariff().modifyFlat("generator", 0.3f);
		Moonial_market.setPlanetConditionMarketOnly(false); //This "market" only represents planet conditions.
		Moonial_market.addCondition(Conditions.POPULATION_7);
		Moonial_market.setFactionId("blue_moon");
		Moonial_market.addIndustry(Industries.POPULATION);
		Moonial_market.addIndustry(Industries.MEGAPORT);
		Moonial_market.addIndustry(Industries.FUELPROD);
		Moonial_market.addIndustry(Industries.LIGHTINDUSTRY);
		Moonial_market.addIndustry(Industries.MILITARYBASE);
		Moonial_market.addIndustry(Industries.TECHMINING);
		Moonial_market.addIndustry(Industries.ORBITALSTATION_HIGH);

		Moonial_market.addSubmarket(Submarkets.SUBMARKET_STORAGE);
		Moonial_market.addSubmarket(Submarkets.SUBMARKET_BLACK);
		Moonial_market.addSubmarket(Submarkets.SUBMARKET_OPEN);
		Moonial_market.addSubmarket(Submarkets.GENERIC_MILITARY);


		{


			PersonAPI Ast = Global.getFactory().createPerson();
			Ast.getName().setFirst("Ast");
			Ast.getName().setLast("Iclingas");
			Ast.setPortraitSprite(Global.getSettings().getSpriteName("characters", "bluemoon_ast"));
			Ast.setGender(MALE);
			Ast.setFaction("blue_moon");
			Ast.setImportance(VERY_HIGH);

			Ast.setRankId(FACTION_LEADER);
			Ast.setPostId(FACTION_LEADER);

			Ast.getStats().setSkillLevel(Skills.INDUSTRIAL_PLANNING, 1);
			Ast.getStats().setSkillLevel(Skills.SPACE_OPERATIONS, 2);
			Ast.getStats().setSkillLevel(Skills.PLANETARY_OPERATIONS, 2);


			Moonial_market.getCommDirectory().addPerson(Ast, 1);
			Moonial_market.addPerson(Ast);


		}




		PlanetAPI ethoi = system.addPlanet("Ethoi", star, "Ethoi", "arid", 570, 175, 7500, 9700);
		ethoi.setFaction("blue_moon");
		MarketAPI Ethoi_market = Global.getFactory().createMarket("Ethoi_market", ethoi.getName(), 4);

		Ethoi_market.setPlanetConditionMarketOnly(false); //This "market" only represents planet conditions.
		Ethoi_market.addCondition(Conditions.HOT);
		Ethoi_market.addCondition(Conditions.TOXIC_ATMOSPHERE); //It's a gas giant, so let's make it gassy!
		Ethoi_market.addCondition(Conditions.MILD_CLIMATE);
		Ethoi_market.addCondition(Conditions.LOW_GRAVITY);
		Ethoi_market.addCondition(Conditions.HABITABLE);
		Ethoi_market.setPrimaryEntity(ethoi); //Tell the "market" that it's on our planet.
		ethoi.setMarket(Ethoi_market); //Likewise, tell our planet that it has a "market".
		Ethoi_market.setSurveyLevel(SurveyLevel.FULL);
		Ethoi_market.getTariff().modifyFlat("generator", 0.3f);
		Ethoi_market.setPlanetConditionMarketOnly(false); //This "market" only represents planet conditions.
		Ethoi_market.addCondition(Conditions.POPULATION_4);
		Ethoi_market.setFactionId("blue_moon");
		Ethoi_market.addIndustry(Industries.POPULATION);
		Ethoi_market.addIndustry(Industries.MEGAPORT);
		Ethoi_market.addIndustry(Industries.REFINING);
		Ethoi_market.addIndustry(Industries.MINING);
		Ethoi_market.addIndustry(Industries.FARMING);

		Ethoi_market.addSubmarket(Submarkets.SUBMARKET_STORAGE);
		Ethoi_market.addSubmarket(Submarkets.SUBMARKET_BLACK);
		Ethoi_market.addSubmarket(Submarkets.SUBMARKET_OPEN);

		PlanetAPI Seduveren = system.addPlanet("Seduveren", star, "Seduveren", "jungle", 30, 50, 6300, 1200);
		Seduveren.setFaction("pirates");
		MarketAPI seduveren_market = Global.getFactory().createMarket("seduveren_market", Seduveren.getName(), 1);

		seduveren_market.setPlanetConditionMarketOnly(false); //This "market" only represents planet conditions.
		seduveren_market.addCondition(Conditions.MILD_CLIMATE); //It's a hot Jupiter so let's make it hot!
		seduveren_market.addCondition(Conditions.THIN_ATMOSPHERE); //It's a gas giant, so let's make it gassy!
		seduveren_market.addCondition(Conditions.WATER);
		seduveren_market.addCondition(Conditions.FARMLAND_BOUNTIFUL);
		seduveren_market.setPrimaryEntity(Seduveren); //Tell the "market" that it's on our planet.
		Seduveren.setMarket(seduveren_market); //Likewise, tell our planet that it has a "market".
		seduveren_market.setSurveyLevel(SurveyLevel.FULL);
		seduveren_market.getTariff().modifyFlat("generator", 0.0f);
		seduveren_market.addCondition(Conditions.POPULATION_1);
		seduveren_market.setFactionId("pirates");
		seduveren_market.addIndustry(Industries.POPULATION);
		seduveren_market.addIndustry(Industries.SPACEPORT);
		seduveren_market.addIndustry(Industries.FARMING);

		seduveren_market.addSubmarket(Submarkets.SUBMARKET_STORAGE);
		seduveren_market.addSubmarket(Submarkets.SUBMARKET_BLACK);
		seduveren_market.addSubmarket(Submarkets.SUBMARKET_OPEN);
		{
			PersonAPI Ocp = Global.getFactory().createPerson();
			Ocp.getName().setFirst("Ocp");
			Ocp.getName().setLast("Nize");
			Ocp.setPortraitSprite(Global.getSettings().getSpriteName("characters", "bluemoon_ocp"));
			Ocp.setGender(FEMALE);
			Ocp.setFaction("blue_moon");
			Ocp.setImportance(LOW);
			Ocp.setId("BMS_Ocp");

			Ocp.setRankId(CITIZEN);
			Ocp.setPostId(CITIZEN);

			Ocp.getStats().setSkillLevel(Skills.INDUSTRIAL_PLANNING, 1);
			Ocp.getStats().setSkillLevel(Skills.SPACE_OPERATIONS, 2);
			Ocp.getStats().setSkillLevel(Skills.PLANETARY_OPERATIONS, 2);


			seduveren_market.getCommDirectory().addPerson(Ocp, 1);
			seduveren_market.addPerson(Ocp);
		}

		EconomyAPI globalEconomy = Global.getSector().getEconomy();
		globalEconomy.addMarket(Estoul_market, true);
		globalEconomy.addMarket(phantu_market, true);
		globalEconomy.addMarket(Moonial_market, true);
		globalEconomy.addMarket(Ethoi_market, true);
		globalEconomy.addMarket(seduveren_market, true);


		StarSystemGenerator.addOrbitingEntities(
				system,
				star,
				StarAge.AVERAGE, //Let's try old system entities this time.
				6, 9, //Let's allow for the possibility of no new entities after Aaarg.
				ethoi.getCircularOrbitRadius() + 600, //Here we grab Steam's orbit radius to figure out where to start adding from.
				system.getPlanets().size(), //Let's start naming planets based off the number of planets already in this location.
				false); // Again, let's use generic planet names.

		system.autogenerateHyperspaceJumpPoints(true, true);

	}
}
