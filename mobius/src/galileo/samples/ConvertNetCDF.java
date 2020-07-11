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

import galileo.dataset.Block;
import galileo.dataset.Coordinates;
import galileo.dataset.Metadata;
import galileo.dataset.SpatialProperties;
import galileo.dataset.TemporalProperties;
import galileo.dataset.feature.Feature;
import galileo.dht.hash.TemporalHash;
import galileo.serialization.Serializer;
import galileo.util.FileNames;
import galileo.util.GeoHash;
import galileo.util.Pair;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import ucar.ma2.*;
import ucar.nc2.*;
import ucar.nc2.dataset.NetcdfDataset;
import ucar.nc2.dt.GridDatatype;
import ucar.nc2.dt.grid.GridDataset;
import ucar.nc2.util.DiskCache;
import ucar.unidata.geoloc.LatLonPoint;

/**
 * Converts NetCDF files from the NOAA NAM project to Galileo Metadata.  A file
 * containing a single grid is converted to many files resprenting single
 * points.  This process can be fairly memory-intensive.
 * <p>
 * Files from the NAM can be downloaded from:
 *      http://nomads.ncdc.noaa.gov/data.php?name=access
 * <p>
 * The idea of this conversion process is to read all 2D variables from the GRIB
 * files with the NetCDF library and convert them to the Galileo format ---
 * namely, a {@link Block} instance that contains {@link Metadata} for its
 * "data" field, along with a separate {@link Metadata} instance for indexing
 * purposes.  So in other words, the Blocks produced by this class contain
 * Metadata, with Metadata about Metadata (or is that MetaMetadata?).
 *
 * @author malensek
 */
public class ConvertNetCDF {
    public static void main(String[] args)
    throws Exception {
        DiskCache.setCachePolicy(true);

        File dir = new File(args[0]);
        for (File f : dir.listFiles()) {
            Pair<String, String> nameParts = FileNames.splitExtension(f);
            String ext = nameParts.b;
            if (ext.equals("grb") || ext.equals("bz2") || ext.equals("gz")) {
                Map<String, Metadata> metaMap
                        = ConvertNetCDF.readFile(f.getAbsolutePath());

                /* Don't cache more than 1 GB: */
                DiskCache.cleanCache(1073741824, null);

                /* Now that we have geographically-partitioned files, let's pick
                 * some attributes to store as indexable metadata. */

                /* Write converted files to disk */
                System.out.print("Writing converted files");
                int processed = 0;
                int increment = metaMap.keySet().size() / 50;
                for (String g : metaMap.keySet()) {
                    Metadata meta = metaMap.get(g);

                    /* Create the directory for this file */
                    String storageDir = getStorageDir(args[1], meta);
                    File destDir = new File(storageDir);
                    if (!destDir.exists()) {
                        if (!destDir.mkdirs()) {
                            throw new IOException(
                                    "Failed to create directory " + destDir);
                        }
                    }

                    /* Create a file Block to store all the metadata in, and
                     * generate a subset for indexing purposes. */
                    Block block = createBlock(nameParts.a, meta);

                    /* Write out the file */
                    String outputName = nameParts.a + ".gblock";
                    FileOutputStream fOut = new FileOutputStream(storageDir
                            + "/" + outputName);
                    fOut.write(Serializer.serialize(block));
                    fOut.close();

                    if (++processed % increment == 0) {
                        System.out.print('.');
                    }
                }
                System.out.println();
            }
        }
    }

    /**
     * Creates a block/metadata pair subset for indexing.
     */
    public static Block createBlock(String name, Metadata meta)
    throws IOException {
        Metadata m = new Metadata(name);
        addIndexField("visibility", meta, m);
        addIndexField("pressure", meta, m);
        addIndexField("total_precipitation", meta, m);
        addIndexField("precipitable_water", meta, m);
        addIndexField("temperature_surface", meta, m);
        addIndexField("total_cloud_cover", meta, m);
        addIndexField("snow_depth", meta, m);
        m.setTemporalProperties(meta.getTemporalProperties());
        m.setSpatialProperties(meta.getSpatialProperties());
        Block block = new Block("samples", m, Serializer.serialize(meta));
        return block;
    }

    /**
     * Takes a Metadata instance being used as Block content, and extracts the
     * given Feature for indexing purposes.
     */
    private static void addIndexField(String featureName, Metadata baseMeta,
            Metadata indexMeta) {
        Feature f = baseMeta.getAttribute(featureName);
        if (f != null) {
            indexMeta.putAttribute(f);
        }
    }

    /**
     * Determines the storage directory for a file, using its spatial
     * properties run through the Geohash algorithm.
     *
     * The resulting format is xx/xxx, which is specific to the NOAA NAM
     * dataset, because this will place each unique grid point in its own
     * directory.
     */
    private static String getStorageDir(String outputDir, Metadata meta) {
        Coordinates coords
            = meta.getSpatialProperties().getCoordinates();
        String location = GeoHash.encode(
                coords.getLatitude(), coords.getLongitude(), 10);

        String subDir = location.substring(0, 2) + "/"
            + location.substring(2, 5);
        return outputDir + "/" + subDir;
    }

    /**
     * Converts a 3D variable to a {@link Metadata} representation
     */
    private static void convert3DVariable(GridDatatype g, Date date,
            Map<String, Metadata> metaMap) throws IOException {

        Variable v = g.getVariable();
        System.out.println("Reading: " + v.getFullName());
        Array values = v.read();

        int h = v.getShape(1);
        int w = v.getShape(2);

        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                LatLonPoint pt = g.getCoordinateSystem().getLatLon(j, i);
                String hash = GeoHash.encode(
                        (float) pt.getLatitude(),
                        (float) pt.getLongitude(), 10).toLowerCase();

                Metadata meta = metaMap.get(hash);
                if (meta == null) {
                    /* We need to create Metadata for this location */
                    meta = new Metadata();

                    UUID metaUUID = UUID.nameUUIDFromBytes(hash.getBytes());
                    meta.setName(metaUUID.toString());

                    SpatialProperties location = new SpatialProperties(
                            (float) pt.getLatitude(),
                            (float) pt.getLongitude());
                    meta.setSpatialProperties(location);

                    TemporalProperties time
                        = new TemporalProperties(date.getTime());
                    meta.setTemporalProperties(time);

                    metaMap.put(hash, meta);
                }

                String featureName = v.getFullName().toLowerCase();
                float featureValue = values.getFloat(i * w + j);
                Feature feature = new Feature(featureName, featureValue);
                meta.putAttribute(feature);
            }
        }
    }

    public static Map<String, Metadata> readFile(String file)
    throws Exception {
        NetcdfFile n = NetcdfFile.open(file);
        System.out.println("Opened: " + file);

        /* Determine the size of our grid */
        int xLen = n.findDimension("x").getLength();
        int yLen = n.findDimension("y").getLength();
        System.out.println("Grid size: " + xLen + "x" + yLen);

        /* What time is this set of readings for? */
        Variable timeVar = n.findVariable("time");
        String timeStr = timeVar.getUnitsString().toUpperCase();
        timeStr = timeStr.replace("HOURS SINCE ", "");
        timeStr = timeStr.replace("HOUR SINCE ", "");

        /* Find the base date (the day) the reading was taken */
        Date baseDate
            = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX").parse(timeStr);

        /* Get the number of hours since the base date this reading was taken */
        int offset = timeVar.read().getInt(0);

        /* Generate the actual date for this reading */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TemporalHash.TIMEZONE);
        calendar.setTime(baseDate);
        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + offset);
        System.out.println("Time of collection: " + calendar.getTime());

        /* We'll keep a mapping of geolocations -> Galileo Metadata */
        Map<String, Metadata> metaMap = new HashMap<>();

        /* Determine the lat, lon coordinates for the grid points, and get each
         * reading at each grid point. */
        NetcdfDataset dataset = new NetcdfDataset(n);
        @SuppressWarnings("resource")
		GridDataset gridData = new GridDataset(dataset);
        for (GridDatatype g : gridData.getGrids()) {
            /* Let's look at 3D variables: these have WxH dimensions, plus a
             * single plane.  A 4D variable would contain elevation
             * and multiple planes as a result */
            if (g.getShape().length == 3) {
                convert3DVariable(g, calendar.getTime(), metaMap);
            }
        }

        return metaMap;

    }
}
