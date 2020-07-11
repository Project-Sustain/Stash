/*
Software in the Sustain Ecosystem are Released Under Terms of Apache Software License 

This research has been supported by funding from the US National Science Foundation's CSSI program through awards 1931363, 1931324, 1931335, and 1931283. The project is a joint effort involving Colorado State University, Arizona State University, the University of California-Irvine, and the University of Maryland - Baltimore County. All redistributions of the software must also include this information. 

TERMS AND CONDITIONS FOR USE, REPRODUCTION, AND DISTRIBUTION


1. Definitions.

"License" shall mean the terms and conditions for use, reproduction, and distribution as defined by Sections 1 through 9 of this document.

"Licensor" shall mean the copyright owner or entity authorized by the copyright owner that is granting the License.

"Legal Entity" shall mean the union of the acting entity and all other entities that control, are controlled by, or are under common control with that entity. For the purposes of this definition, "control" means (i) the power, direct or indirect, to cause the direction or management of such entity, whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or (iii) beneficial ownership of such entity.

"You" (or "Your") shall mean an individual or Legal Entity exercising permissions granted by this License.

"Source" form shall mean the preferred form for making modifications, including but not limited to software source code, documentation source, and configuration files.

"Object" form shall mean any form resulting from mechanical transformation or translation of a Source form, including but not limited to compiled object code, generated documentation, and conversions to other media types.

"Work" shall mean the work of authorship, whether in Source or Object form, made available under the License, as indicated by a copyright notice that is included in or attached to the work (an example is provided in the Appendix below).

"Derivative Works" shall mean any work, whether in Source or Object form, that is based on (or derived from) the Work and for which the editorial revisions, annotations, elaborations, or other modifications represent, as a whole, an original work of authorship. For the purposes of this License, Derivative Works shall not include works that remain separable from, or merely link (or bind by name) to the interfaces of, the Work and Derivative Works thereof.

"Contribution" shall mean any work of authorship, including the original version of the Work and any modifications or additions to that Work or Derivative Works thereof, that is intentionally submitted to Licensor for inclusion in the Work by the copyright owner or by an individual or Legal Entity authorized to submit on behalf of the copyright owner. For the purposes of this definition, "submitted" means any form of electronic, verbal, or written communication sent to the Licensor or its representatives, including but not limited to communication on electronic mailing lists, source code control systems, and issue tracking systems that are managed by, or on behalf of, the Licensor for the purpose of discussing and improving the Work, but excluding communication that is conspicuously marked or otherwise designated in writing by the copyright owner as "Not a Contribution."

"Contributor" shall mean Licensor and any individual or Legal Entity on behalf of whom a Contribution has been received by Licensor and subsequently incorporated within the Work.

2. Grant of Copyright License. Subject to the terms and conditions of this License, each Contributor hereby grants to You a perpetual, worldwide, non-exclusive, no-charge, royalty-free, irrevocable copyright license to reproduce, prepare Derivative Works of, publicly display, publicly perform, sublicense, and distribute the Work and such Derivative Works in Source or Object form.

3. Grant of Patent License. Subject to the terms and conditions of this License, each Contributor hereby grants to You a perpetual, worldwide, non-exclusive, no-charge, royalty-free, irrevocable (except as stated in this section) patent license to make, have made, use, offer to sell, sell, import, and otherwise transfer the Work, where such license applies only to those patent claims licensable by such Contributor that are necessarily infringed by their Contribution(s) alone or by combination of their Contribution(s) with the Work to which such Contribution(s) was submitted. If You institute patent litigation against any entity (including a cross-claim or counterclaim in a lawsuit) alleging that the Work or a Contribution incorporated within the Work constitutes direct or contributory patent infringement, then any patent licenses granted to You under this License for that Work shall terminate as of the date such litigation is filed.

4. Redistribution. You may reproduce and distribute copies of the Work or Derivative Works thereof in any medium, with or without modifications, and in Source or Object form, provided that You meet the following conditions:

You must give any other recipients of the Work or Derivative Works a copy of this License; and
You must cause any modified files to carry prominent notices stating that You changed the files; and
You must retain, in the Source form of any Derivative Works that You distribute, all copyright, patent, trademark, and attribution notices from the Source form of the Work, excluding those notices that do not pertain to any part of the Derivative Works; and
If the Work includes a "NOTICE" text file as part of its distribution, then any Derivative Works that You distribute must include a readable copy of the attribution notices contained within such NOTICE file, excluding those notices that do not pertain to any part of the Derivative Works, in at least one of the following places: within a NOTICE text file distributed as part of the Derivative Works; within the Source form or documentation, if provided along with the Derivative Works; or, within a display generated by the Derivative Works, if and wherever such third-party notices normally appear. The contents of the NOTICE file are for informational purposes only and do not modify the License. You may add Your own attribution notices within Derivative Works that You distribute, alongside or as an addendum to the NOTICE text from the Work, provided that such additional attribution notices cannot be construed as modifying the License. 

You may add Your own copyright statement to Your modifications and may provide additional or different license terms and conditions for use, reproduction, or distribution of Your modifications, or for any such Derivative Works as a whole, provided Your use, reproduction, and distribution of the Work otherwise complies with the conditions stated in this License.
5. Submission of Contributions. Unless You explicitly state otherwise, any Contribution intentionally submitted for inclusion in the Work by You to the Licensor shall be under the terms and conditions of this License, without any additional terms or conditions. Notwithstanding the above, nothing herein shall supersede or modify the terms of any separate license agreement you may have executed with Licensor regarding such Contributions.

6. Trademarks. This License does not grant permission to use the trade names, trademarks, service marks, or product names of the Licensor, except as required for reasonable and customary use in describing the origin of the Work and reproducing the content of the NOTICE file.

7. Disclaimer of Warranty. Unless required by applicable law or agreed to in writing, Licensor provides the Work (and each Contributor provides its Contributions) on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied, including, without limitation, any warranties or conditions of TITLE, NON-INFRINGEMENT, MERCHANTABILITY, or FITNESS FOR A PARTICULAR PURPOSE. You are solely responsible for determining the appropriateness of using or redistributing the Work and assume any risks associated with Your exercise of permissions under this License.

8. Limitation of Liability. In no event and under no legal theory, whether in tort (including negligence), contract, or otherwise, unless required by applicable law (such as deliberate and grossly negligent acts) or agreed to in writing, shall any Contributor be liable to You for damages, including any direct, indirect, special, incidental, or consequential damages of any character arising as a result of this License or out of the use or inability to use the Work (including but not limited to damages for loss of goodwill, work stoppage, computer failure or malfunction, or any and all other commercial damages or losses), even if such Contributor has been advised of the possibility of such damages.

9. Accepting Warranty or Additional Liability. While redistributing the Work or Derivative Works thereof, You may choose to offer, and charge a fee for, acceptance of support, warranty, indemnity, or other liability obligations and/or rights consistent with this License. However, in accepting such obligations, You may act only on Your own behalf and on Your sole responsibility, not on behalf of any other Contributor, and only if You agree to indemnify, defend, and hold each Contributor harmless for any liability incurred by, or claims asserted against, such Contributor by reason of your accepting any such warranty or additional liability. 

END OF TERMS AND CONDITIONS
*/

package galileo.samples;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import galileo.bmp.Bitmap;
import galileo.bmp.BitmapVisualization;
import galileo.bmp.GeoavailabilityGrid;
import galileo.bmp.GeoavailabilityMap;
import galileo.bmp.GeoavailabilityQuery;
import galileo.bmp.QueryTransform;
import galileo.comm.TemporalType;
import galileo.config.SystemConfig;
import galileo.dataset.Coordinates;
import galileo.dataset.Metadata;
import galileo.dataset.feature.Feature;
import galileo.dht.NetworkConfig;
import galileo.dht.NetworkInfo;
import galileo.dht.StorageNode;
import galileo.fs.GeospatialFileSystem;
import galileo.graph.Path;
import galileo.query.Expression;
import galileo.query.Operation;
import galileo.query.PayloadFilter;
import galileo.query.Query;

/**
 * Demonstrates the use of a {@link GeoavailabilityGrid} in determining whether
 * or not information is available in a particular region.
 *
 * @author malensek
 */
public class GeoavailabilityDemo {
	public static void main(String[] args) throws Exception {
		if (args.length < 1) {
			System.out.println("Usage: GeoavailabilityDemo <NetCDF-File>");
			return;
		}

		String file = args[0];
		System.out.println("Reading NetCDF file: " + file + "...");
		Map<String, Metadata> metaMap = ConvertNetCDF.readFile(file);

		/*
		 * Let's construct a Geo Grid for the 9x Geohash region with a precision
		 * of 20. That's 2^20 total grid points.
		 */
		GeoavailabilityGrid gg = new GeoavailabilityGrid("9x", 20);

		System.out.println("Adding points...");
		for (String str : metaMap.keySet()) {
			if (str.toLowerCase().substring(0, 2).equals("9x")) {
				/* We found a sample for our particular region */
				Coordinates coords = metaMap.get(str).getSpatialProperties().getCoordinates();
				gg.addPoint(coords);
			}
		}

		/* What does this grid look like? */
		BufferedImage b = BitmapVisualization.drawGeoavailabilityGrid(gg, Color.BLACK);
		BitmapVisualization.imageToFile(b, "NetCDF-GeoavailabilityGrid.gif");

		List<Coordinates> poly = new ArrayList<>();
		poly.add(new Coordinates(43.79f, -105.00f));
		poly.add(new Coordinates(40.96f, -103.50f));
		poly.add(new Coordinates(39.98f, -108.47f));
		GeoavailabilityQuery gq = new GeoavailabilityQuery(poly);

		/* Let's see what the polygon looks like too... */
		Bitmap queryBitamp = QueryTransform.queryToGridBitmap(gq, gg);
		BufferedImage polyImage = BitmapVisualization.drawBitmap(queryBitamp, gg.getWidth(), gg.getHeight(), Color.RED);
		BitmapVisualization.imageToFile(polyImage, "NetCDF-GeoavailabilityQuery.gif");

		/* Does this polygon overlap the grid? */
		boolean intersects = gg.intersects(gq);
		System.out.println("Polygon intersects the grid: " + intersects);

		/* Alternatively, a little 'polygon' that won't intersect: */
		poly = new ArrayList<>();
		poly.add(new Coordinates(43.79f, -105.39f));
		gq = new GeoavailabilityQuery(poly);

		intersects = gg.intersects(gq);
		System.out.println("Polygon intersects the grid: " + intersects);

		/* GeoavailabilityMap tests */
		GeoavailabilityMap<String> gm = new GeoavailabilityMap<>("9x", 20);
		NetworkInfo network = NetworkConfig.readNetworkDescription(SystemConfig.getNetworkConfDir());
		GeospatialFileSystem gfs = new GeospatialFileSystem(new StorageNode(), "/tmp/galileo", "samples", 4, 0,
				TemporalType.DAY_OF_MONTH.getType(), network, null, null, false);

		System.out.println("Adding points to Map");
		for (String str : metaMap.keySet()) {
			if (str.toLowerCase().substring(0, 2).equals("9x")) {
				/* We found a sample for our particular region */
				Metadata meta = metaMap.get(str);
				Coordinates coords = meta.getSpatialProperties().getCoordinates();
				gm.addPoint(coords, meta.getName());

				gfs.storeMetadata(meta, meta.getName());
			}
		}

		poly = new ArrayList<>();
		poly.add(new Coordinates(43.79f, -105.00f));
		poly.add(new Coordinates(40.96f, -103.50f));
		poly.add(new Coordinates(39.98f, -108.47f));
		gq = new GeoavailabilityQuery(poly);

		System.out.println("Intersecting points:");
		Map<Integer, List<String>> results = gm.query(gq);
		Set<String> files = new HashSet<>();
		for (int i : results.keySet()) {
			System.out.print(i + ", ");
			for (String s : results.get(i)) {
				System.out.print(s + " ");
				files.add(s);
			}
			System.out.println();
		}

		/*
		 * Homework: see if the total points in the result set matches the
		 * number of points in the outputted gifs
		 */
		System.out.println();
		System.out.println("Total points: " + results.keySet().size());

		Query q = new Query();
		q.addOperation(new Operation(new Expression(">", new Feature("temperature_surface", 270.0f))));
		System.out.println("Query: " + q);

		PayloadFilter<String> pf = new PayloadFilter<>(false, files);

		List<Path<Feature, String>> result = gfs.getMetadataGraph().evaluateQuery(q, pf);
		/* Alternatively, get a Metadata graph back: */
		// MetadataGraph mdg = gfs.getMetadataGraph().evaluateQuery(q, pf);
		/* See the graph: */
		// System.out.println(mdg);

		System.out.println("Number of filtered results: " + result.size());
	}
}
