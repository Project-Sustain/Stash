package galileo.bq;
/* 
 * Copyright (c) 2015, Colorado State University. Written by Duck Keun Yang 2015-08-02
 * 
 * All rights reserved.
 * 
 * CSU EDF Project
 * 
 * This program read a csv-formatted file and send each line to the galileo server
 */

import java.util.ArrayList;
import java.util.List;

import galileo.dataset.SpatialHint;
import galileo.dataset.feature.FeatureType;
import galileo.util.Pair;

public class VisualizationNAMCreateFSTest {

	// [START processFile]
	/**
	 * read each line from the csv file and send it to galileo server
	 * 
	 * @param pathtothefile
	 *            path to the csv file
	 * @param galileoconnector
	 *            GalileoConnector instance
	 * @throws Exception
	 */
	private static boolean FS_CREATED = false;
	
	private static void createFileSystem(GalileoConnector gc) throws Exception {
		
		// CREATING FS1
		if( ! FS_CREATED ) {
			List<Pair<String, FeatureType>> featureList1 = new ArrayList<>();
	  		
			featureList1.add(new Pair<>("gps_abs_lat", FeatureType.FLOAT));
			featureList1.add(new Pair<>("gps_abs_lon", FeatureType.FLOAT));
			featureList1.add(new Pair<>("epoch_time", FeatureType.FLOAT));
			featureList1.add(new Pair<>("geopotential_height_lltw", FeatureType.FLOAT));
			featureList1.add(new Pair<>("water_equiv_of_accum_snow_depth_surface", FeatureType.FLOAT));
			featureList1.add(new Pair<>("drag_coefficient_surface", FeatureType.FLOAT));
			featureList1.add(new Pair<>("sensible_heat_net_flux_surface", FeatureType.FLOAT));
			featureList1.add(new Pair<>("categorical_ice_pellets_yes1_no0_surface", FeatureType.FLOAT));
			featureList1.add(new Pair<>("visibility_surface", FeatureType.FLOAT));
			featureList1.add(new Pair<>("number_of_soil_layers_in_root_zone_surface", FeatureType.FLOAT));
			featureList1.add(new Pair<>("categorical_freezing_rain_yes1_no0_surface", FeatureType.FLOAT));
			featureList1.add(new Pair<>("pressure_reduced_to_msl_msl", FeatureType.FLOAT));
			featureList1.add(new Pair<>("upward_short_wave_rad_flux_surface", FeatureType.FLOAT));
			featureList1.add(new Pair<>("relative_humidity_zerodegc_isotherm", FeatureType.FLOAT));
			featureList1.add(new Pair<>("missing_pblri", FeatureType.FLOAT));
			featureList1.add(new Pair<>("categorical_snow_yes1_no0_surface", FeatureType.FLOAT));
			featureList1.add(new Pair<>("u-component_of_wind_tropopause", FeatureType.FLOAT));
			featureList1.add(new Pair<>("surface_wind_gust_surface", FeatureType.FLOAT));
			featureList1.add(new Pair<>("total_cloud_cover_entire_atmosphere", FeatureType.FLOAT));
			featureList1.add(new Pair<>("upward_long_wave_rad_flux_surface", FeatureType.FLOAT));
			featureList1.add(new Pair<>("land_cover_land1_sea0_surface", FeatureType.FLOAT));
			featureList1.add(new Pair<>("vegitation_type_as_in_sib_surface", FeatureType.FLOAT));
			featureList1.add(new Pair<>("v-component_of_wind_pblri", FeatureType.FLOAT));
			featureList1.add(new Pair<>("convective_precipitation_surface_1_hour_accumulation", FeatureType.FLOAT));
			featureList1.add(new Pair<>("albedo_surface", FeatureType.FLOAT));
			featureList1.add(new Pair<>("lightning_surface", FeatureType.FLOAT));
			featureList1.add(new Pair<>("ice_cover_ice1_no_ice0_surface", FeatureType.FLOAT));
			featureList1.add(new Pair<>("convective_inhibition_surface", FeatureType.FLOAT));
			featureList1.add(new Pair<>("pressure_surface", FeatureType.FLOAT));
			featureList1.add(new Pair<>("transpiration_stress-onset_soil_moisture_surface", FeatureType.FLOAT));
			featureList1.add(new Pair<>("soil_porosity_surface", FeatureType.FLOAT));
			featureList1.add(new Pair<>("vegetation_surface", FeatureType.FLOAT));
			featureList1.add(new Pair<>("categorical_rain_yes1_no0_surface", FeatureType.FLOAT));
			featureList1.add(new Pair<>("downward_long_wave_rad_flux_surface", FeatureType.FLOAT));
			featureList1.add(new Pair<>("planetary_boundary_layer_height_surface", FeatureType.FLOAT));
			featureList1.add(new Pair<>("soil_type_as_in_zobler_surface", FeatureType.FLOAT));
			featureList1.add(new Pair<>("geopotential_height_cloud_base", FeatureType.FLOAT));
			featureList1.add(new Pair<>("friction_velocity_surface", FeatureType.FLOAT));
			featureList1.add(new Pair<>("maximumcomposite_radar_reflectivity_entire_atmosphere", FeatureType.FLOAT));
			featureList1.add(new Pair<>("plant_canopy_surface_water_surface", FeatureType.FLOAT));
			featureList1.add(new Pair<>("v-component_of_wind_maximum_wind", FeatureType.FLOAT));
			featureList1.add(new Pair<>("geopotential_height_zerodegc_isotherm", FeatureType.FLOAT));
			featureList1.add(new Pair<>("mean_sea_level_pressure_nam_model_reduction_msl", FeatureType.FLOAT));
			featureList1.add(new Pair<>("total_precipitation_surface_1_hour_accumulation", FeatureType.FLOAT));
			featureList1.add(new Pair<>("temperature_surface", FeatureType.FLOAT));
			featureList1.add(new Pair<>("snow_cover_surface", FeatureType.FLOAT));
			featureList1.add(new Pair<>("geopotential_height_surface", FeatureType.FLOAT));
			featureList1.add(new Pair<>("convective_available_potential_energy_surface", FeatureType.FLOAT));
			featureList1.add(new Pair<>("latent_heat_net_flux_surface", FeatureType.FLOAT));
			featureList1.add(new Pair<>("surface_roughness_surface", FeatureType.FLOAT));
			featureList1.add(new Pair<>("pressure_maximum_wind", FeatureType.FLOAT));
			featureList1.add(new Pair<>("temperature_tropopause", FeatureType.FLOAT));
			featureList1.add(new Pair<>("geopotential_height_pblri", FeatureType.FLOAT));
			featureList1.add(new Pair<>("pressure_tropopause", FeatureType.FLOAT));
			featureList1.add(new Pair<>("snow_depth_surface", FeatureType.FLOAT));
			featureList1.add(new Pair<>("v-component_of_wind_tropopause", FeatureType.FLOAT));
			featureList1.add(new Pair<>("downward_short_wave_rad_flux_surface", FeatureType.FLOAT));
			featureList1.add(new Pair<>("u-component_of_wind_maximum_wind", FeatureType.FLOAT));
			featureList1.add(new Pair<>("wilting_point_surface", FeatureType.FLOAT));
			featureList1.add(new Pair<>("precipitable_water_entire_atmosphere", FeatureType.FLOAT));
			featureList1.add(new Pair<>("u-component_of_wind_pblri", FeatureType.FLOAT));
			featureList1.add(new Pair<>("direct_evaporation_cease_soil_moisture_surface", FeatureType.FLOAT));
			
			List<String> sumHints = new ArrayList<String>();
			sumHints.add("geopotential_height_lltw");
			sumHints.add("water_equiv_of_accum_snow_depth_surface");
			sumHints.add("drag_coefficient_surface");
			sumHints.add("v-component_of_wind_tropopause");
			sumHints.add("downward_short_wave_rad_flux_surface");
			sumHints.add("u-component_of_wind_maximum_wind");
			
			SpatialHint spHint = new SpatialHint("gps_abs_lat", "gps_abs_lon");
			String temporalHint1 = "epoch_time";
			
			gc.createFSViz("namfs", spHint, featureList1, temporalHint1, sumHints);
			
			
			System.out.println("CREATION INITIATED");
			FS_CREATED = true;
			
			Thread.sleep(2000);
		}
		
		gc.disconnect();
		
	}
	
	
	
	public static void main(String[] args1) {
		String args[] = new String[2];
		args[0] = "lattice-1.cs.colostate.edu";
		args[1] = "5634";
		
		System.out.println(args.length);
		if (args.length != 2) {
			System.out.println("Usage: ConvertCSVFileToGalileo [galileo-hostname] [galileo-port-number]");
			System.exit(0);
		} else {
			try {
				GalileoConnector gc = new GalileoConnector(args[0], 5634);
				System.out.println(args[0] + "," + Integer.parseInt(args[1]));
				
				createFileSystem(gc);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("Data successfully inserted into galileo");
		System.exit(0);
	}
	
	
}
