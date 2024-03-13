package src.data.scripts;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI.SurveyLevel;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.ids.*;
import com.fs.starfarer.api.impl.campaign.procgen.StarAge;
import com.fs.starfarer.api.impl.campaign.procgen.StarSystemGenerator;
import src.data.campaign.econ.bluemoon_industries;

import static com.fs.starfarer.api.campaign.PersonImportance.*;
import static com.fs.starfarer.api.characters.FullName.Gender.FEMALE;
import static com.fs.starfarer.api.characters.FullName.Gender.MALE;
import static com.fs.starfarer.api.impl.campaign.ids.Ranks.*;

public class BlueMoonPlugin extends BaseModPlugin {
	@Override
	public void onNewGame() {
		SectorAPI sector = Global.getSector();
		StarSystemAPI system = sector.createStarSystem("Condilar");

		PlanetAPI Sens = system.initStar(
				"Condilar",
				"star_yellow",
				700,
				25427,
				17750,
				500);

		SectorEntityToken buoy = system.addCustomEntity("condilar_buoy", // unique id
				"Condilar Buoy", // name - if null, defaultName from custom_entities.json will be used
				"nav_buoy", // type of object, defined in custom_entities.json
				"blue_moon"); // faction
		buoy.setCircularOrbitPointingDown(Sens, 40, 2000, 70);

		SectorEntityToken relay = system.addCustomEntity("condilar_relay", // unique id
				"Condilar Relay", // name - if null, defaultName from custom_entities.json will be used
				"comm_relay", // type of object, defined in custom_entities.json
				"blue_moon"); // faction
		relay.setCircularOrbitPointingDown(Sens, 190, 3000, 70);

		SectorEntityToken array = system.addCustomEntity("condilar_array", // unique id
				"Condilar Array", // name - if null, defaultName from custom_entities.json will be used
				"sensor_array", // type of object, defined in custom_entities.json
				"blue_moon"); // faction
		array.setCircularOrbitPointingDown(Sens, 220, 2000, 70);

		SectorEntityToken condilar_gate = system.addCustomEntity("condilar_gate", // unique id
				"Condilar Gate", // name - if null, defaultName from custom_entities.json will be used
				"inactive_gate", // type of object, defined in custom_entities.json
				null); // faction
		condilar_gate.setCircularOrbit(Sens, 120, 2500, 70);

		PlanetAPI Bluemoon_Phantu_planet = system.addPlanet("Phantu", Sens, "Phantu", "gas_giant", 230, 350, 5000, 150);
		Bluemoon_Phantu_planet.setFaction("blue_moon");
		MarketAPI phantu_market = Global.getFactory().createMarket("phantu_market", Bluemoon_Phantu_planet.getName(), 4);

		Bluemoon_Phantu_planet.setCustomDescriptionId("Bluemoon_Phantu_planet");
		phantu_market.setPlanetConditionMarketOnly(false); //This "market" only represents planet conditions.
		phantu_market.addCondition(Conditions.VERY_COLD); //It's a cold planet so let's make it hot!
		phantu_market.addCondition(Conditions.DENSE_ATMOSPHERE); //It's a gas giant, so let's make it gassy!
		phantu_market.addCondition(Conditions.EXTREME_WEATHER);
		phantu_market.addCondition(Conditions.HIGH_GRAVITY);
		phantu_market.addCondition(Conditions.VOLATILES_PLENTIFUL);
		phantu_market.addCondition(Conditions.ORE_RICH);
		phantu_market.addCondition(Conditions.RARE_ORE_RICH);
		phantu_market.addCondition(Conditions.ORGANICS_ABUNDANT);
		phantu_market.setPrimaryEntity(Bluemoon_Phantu_planet); //Tell the "market" that it's on our planet.
		Bluemoon_Phantu_planet.setMarket(phantu_market); //Likewise, tell our planet that it has a "market".
		phantu_market.setSurveyLevel(SurveyLevel.FULL);
		phantu_market.getTariff().modifyFlat("generator", 0.3f);
		phantu_market.setPlanetConditionMarketOnly(false); //This "market" only represents planet conditions.
		phantu_market.addCondition(Conditions.POPULATION_4);
		phantu_market.setFactionId("blue_moon");
		phantu_market.addIndustry(Industries.POPULATION);
		phantu_market.addIndustry(Industries.SPACEPORT);
		phantu_market.addIndustry(Industries.MINING);
		phantu_market.addIndustry(Industries.REFINING);

		phantu_market.addSubmarket(Submarkets.SUBMARKET_STORAGE);
		phantu_market.addSubmarket(Submarkets.SUBMARKET_BLACK);
		phantu_market.addSubmarket(Submarkets.SUBMARKET_OPEN);


		PlanetAPI Bluemoon_Estoul_planet = system.addPlanet("Estoul", Bluemoon_Phantu_planet, "Estoul", "jungle", 10, 80, 700, 250);
		Bluemoon_Estoul_planet.setFaction("blue_moon");
		MarketAPI Estoul_market = Global.getFactory().createMarket("Estoul_market", Bluemoon_Estoul_planet.getName(), 5);

		Bluemoon_Estoul_planet.setCustomDescriptionId("Bluemoon_Estoul_planet");
		Estoul_market.addCondition(Conditions.HABITABLE);
		Estoul_market.addCondition(Conditions.COLD);
		Estoul_market.addCondition(Conditions.ORE_RICH);
		Estoul_market.addCondition(Conditions.RARE_ORE_MODERATE);
		Estoul_market.addCondition(Conditions.ORGANICS_TRACE);
		Estoul_market.addCondition(Conditions.VOLATILES_DIFFUSE);
		Estoul_market.addCondition(Conditions.FARMLAND_BOUNTIFUL);
		Estoul_market.setPrimaryEntity(Bluemoon_Estoul_planet); //Tell the "market" that it's on our planet.
		Bluemoon_Estoul_planet.setMarket(Estoul_market); //Likewise, tell our planet that it has a "market".
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

		PlanetAPI Bluemoon_Moonial_planet = system.addPlanet("Moonial", Sens, "Moonial", "tundra", 570, 175, 7000, 9000);
		Bluemoon_Moonial_planet.setFaction("blue_moon");
		MarketAPI Moonial_market = Global.getFactory().createMarket("Moonial_market", Bluemoon_Moonial_planet.getName(), 7);

		Bluemoon_Moonial_planet.setCustomDescriptionId("Bluemoon_Moonial_planet");
		Moonial_market.setPlanetConditionMarketOnly(false); //This "market" only represents planet conditions.
		Moonial_market.addCondition(Conditions.COLD);
		Moonial_market.addCondition(Conditions.THIN_ATMOSPHERE); //It's a gas giant, so let's make it gassy!
		Moonial_market.addCondition(Conditions.MILD_CLIMATE);
		Moonial_market.addCondition(Conditions.LOW_GRAVITY);
		Moonial_market.addCondition(Conditions.RUINS_EXTENSIVE);
		Moonial_market.setPrimaryEntity(Bluemoon_Moonial_planet); //Tell the "market" that it's on our planet.
		Bluemoon_Moonial_planet.setMarket(Moonial_market); //Likewise, tell our planet that it has a "market".
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
			Ast.setImportance(HIGH);
			Ast.addTag("military");
			Ast.setId("BMS_Ast");

			Ast.setRankId(FACTION_LEADER);
			Ast.setPostId(FACTION_LEADER);

			Ast.getStats().setSkillLevel(Skills.INDUSTRIAL_PLANNING, 1);



			Moonial_market.getCommDirectory().addPerson(Ast, 1);
			Moonial_market.addPerson(Ast);


		}




		PlanetAPI Bluemoon_Ethoi_planet = system.addPlanet("Ethoi", Sens, "Ethoi", "arid", 570, 175, 7500, 9700);
		Bluemoon_Ethoi_planet.setFaction("blue_moon");
		MarketAPI Ethoi_market = Global.getFactory().createMarket("Ethoi_market", Bluemoon_Ethoi_planet.getName(), 5);

		Bluemoon_Ethoi_planet.setCustomDescriptionId("Bluemoon_Ethoi_planet");
		Ethoi_market.setPlanetConditionMarketOnly(false); //This "market" only represents planet conditions.
		Ethoi_market.addCondition(Conditions.HOT);
		Ethoi_market.addCondition(Conditions.TOXIC_ATMOSPHERE); //It's a gas giant, so let's make it gassy!
		Ethoi_market.addCondition(Conditions.MILD_CLIMATE);
		Ethoi_market.addCondition(Conditions.LOW_GRAVITY);
		Ethoi_market.addCondition(Conditions.HABITABLE);
		Ethoi_market.setPrimaryEntity(Bluemoon_Ethoi_planet); //Tell the "market" that it's on our planet.
		Bluemoon_Ethoi_planet.setMarket(Ethoi_market); //Likewise, tell our planet that it has a "market".
		Ethoi_market.setSurveyLevel(SurveyLevel.FULL);
		Ethoi_market.getTariff().modifyFlat("generator", 0.3f);
		Ethoi_market.setPlanetConditionMarketOnly(false); //This "market" only represents planet conditions.
		Ethoi_market.addCondition(Conditions.POPULATION_5);
		Ethoi_market.setFactionId("blue_moon");
		Ethoi_market.addIndustry(Industries.POPULATION);
		Ethoi_market.addIndustry(Industries.MEGAPORT);
		Ethoi_market.addIndustry(Industries.REFINING);
		Ethoi_market.addIndustry(Industries.MINING);
		Ethoi_market.addIndustry(bluemoon_industries.BLMMILK);
		Ethoi_market.addIndustry(bluemoon_industries.BLMPRO);

		Ethoi_market.addSubmarket(Submarkets.SUBMARKET_STORAGE);
		Ethoi_market.addSubmarket(Submarkets.SUBMARKET_BLACK);
		Ethoi_market.addSubmarket(Submarkets.SUBMARKET_OPEN);

		PlanetAPI Bluemoon_Seduveren_planet = system.addPlanet("Seduveren", Sens, "Seduveren", "jungle", 30, 50, 6300, 1200);
		Bluemoon_Seduveren_planet.setFaction("oda_clan");
		MarketAPI seduveren_market = Global.getFactory().createMarket("seduveren_market", Bluemoon_Seduveren_planet.getName(), 5);

		Bluemoon_Seduveren_planet.setCustomDescriptionId("Bluemoon_Seduveren_planet");
		seduveren_market.setPlanetConditionMarketOnly(false); //This "market" only represents planet conditions.
		seduveren_market.addCondition(Conditions.MILD_CLIMATE); //It's a hot Jupiter so let's make it hot!
		seduveren_market.addCondition(Conditions.THIN_ATMOSPHERE); //It's a gas giant, so let's make it gassy!
		seduveren_market.addCondition(Conditions.WATER);
		seduveren_market.addCondition(Conditions.FARMLAND_BOUNTIFUL);
		seduveren_market.setPrimaryEntity(Bluemoon_Seduveren_planet); //Tell the "market" that it's on our planet.
		Bluemoon_Seduveren_planet.setMarket(seduveren_market); //Likewise, tell our planet that it has a "market".
		seduveren_market.setSurveyLevel(SurveyLevel.FULL);
		seduveren_market.getTariff().modifyFlat("generator", 0.0f);
		seduveren_market.addCondition(Conditions.POPULATION_5);
		seduveren_market.setFactionId("oda_clan");
		seduveren_market.addIndustry(Industries.POPULATION);
		seduveren_market.addIndustry(Industries.MEGAPORT);
		seduveren_market.addIndustry(Industries.FARMING);
		seduveren_market.addIndustry(bluemoon_industries.BLMWARMACHINE);
		seduveren_market.addIndustry(Industries.MILITARYBASE);
		seduveren_market.addIndustry(Industries.STARFORTRESS_HIGH);
		seduveren_market.addIndustry(Industries.GROUNDDEFENSES);

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


			seduveren_market.getCommDirectory().addPerson(Ocp, 9);
			seduveren_market.addPerson(Ocp);

			PersonAPI Oda = Global.getFactory().createPerson();
			Oda.getName().setFirst("Oda");
			Oda.getName().setLast("Nobunaga");
			Oda.setPortraitSprite(Global.getSettings().getSpriteName("characters", "bluemoon_oda"));
			Oda.setGender(FEMALE);
			Oda.setFaction("oda_clan");
			Oda.setImportance(VERY_HIGH);
			Oda.setId("BMS_Oda");
			Oda.addTag("military");

			Oda.setRankId(FACTION_LEADER);
			Oda.setPostId(FACTION_LEADER);

			Oda.getStats().setSkillLevel(Skills.INDUSTRIAL_PLANNING, 1);


			seduveren_market.getCommDirectory().addPerson(Oda, 1);
			seduveren_market.addPerson(Oda);

			PersonAPI Nobukatsu = Global.getFactory().createPerson();
			Nobukatsu.getName().setFirst("Oda");
			Nobukatsu.getName().setLast("Nobukatsu");
			Nobukatsu.setPortraitSprite(Global.getSettings().getSpriteName("characters", "bluemoon_nobukatsu"));
			Nobukatsu.setGender(MALE);
			Nobukatsu.setFaction("oda_clan");
			Nobukatsu.setImportance(MEDIUM);
			Nobukatsu.setId("BMS_Nobukatsu");
			Nobukatsu.addTag("trade");
			Nobukatsu.addTag("underworld");

			Nobukatsu.setRankId(GROUND_GENERAL);
			Nobukatsu.setPostId(POST_ARISTOCRAT);


			seduveren_market.getCommDirectory().addPerson(Nobukatsu, 2);
			seduveren_market.addPerson(Nobukatsu);
		}

		EconomyAPI globalEconomy = Global.getSector().getEconomy();
		globalEconomy.addMarket(Estoul_market, true);
		globalEconomy.addMarket(phantu_market, true);
		globalEconomy.addMarket(Moonial_market, true);
		globalEconomy.addMarket(Ethoi_market, true);
		globalEconomy.addMarket(seduveren_market, true);


		StarSystemGenerator.addOrbitingEntities(
				system,
				Sens,
				StarAge.AVERAGE, //Let's try old system entities this time.
				6, 9, //Let's allow for the possibility of no new entities after Aaarg.
				Bluemoon_Ethoi_planet.getCircularOrbitRadius() + 600, //Here we grab Steam's orbit radius to figure out where to start adding from.
				system.getPlanets().size(), //Let's start naming planets based off the number of planets already in this location.
				false); // Again, let's use generic planet names.

		system.autogenerateHyperspaceJumpPoints(true, true);

		FactionAPI oda_clan = sector.getFaction("oda_clan");

		oda_clan.setRelationship("hegemony", -0.7f);
		oda_clan.setRelationship("tritachyon", -0.7f);
		oda_clan.setRelationship("pirates", -0.7f);
		oda_clan.setRelationship("independent", 0.1f);
		oda_clan.setRelationship("player", -0.2f);

		FactionAPI blue_moon = sector.getFaction("blue_moon");

		blue_moon.setRelationship("hegemony", -0.7f);
		blue_moon.setRelationship("tritachyon", -0.7f);
		blue_moon.setRelationship("pirates", 0.1f);
		blue_moon.setRelationship("independent", 0.4f);
		blue_moon.setRelationship("oda_clan", 0.8f);
		blue_moon.setRelationship("player", 0.1f);
		}

}

