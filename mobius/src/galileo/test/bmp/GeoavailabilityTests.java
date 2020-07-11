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

package galileo.test.bmp;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import galileo.bmp.GeoavailabilityGrid;
import galileo.bmp.GeoavailabilityQuery;
import galileo.bmp.QueryTransform;
import galileo.bmp.BitmapVisualization;
import galileo.dataset.Coordinates;
import galileo.dataset.SpatialRange;

import org.junit.Test;

public class GeoavailabilityTests {

    private boolean draw = false;

    public GeoavailabilityTests() {
        this.draw = Boolean.parseBoolean(System.getProperty(
                "galileo.test.bmp.GeoavailabilityTests.draw",
                "false"));
    }

    /**
     * Test converting X, Y grid coordinates to spatial ranges using the
     * calculated corners of the grid.
     */
    @Test
    public void testXYtoSpatialRange() {
        GeoavailabilityGrid gg = new GeoavailabilityGrid("9x", 10);
        gg.addPoint(new Coordinates(44.919f, -112.242f));
        gg.addPoint(new Coordinates(44.919f, -101.514f));
        gg.addPoint(new Coordinates(39.496f, -112.242f));
        gg.addPoint(new Coordinates(39.496f, -101.514f));


        float epsilon = 0.01f;

        SpatialRange s1 = gg.XYtoSpatialRange(0, 0);
        SpatialRange s2 = gg.XYtoSpatialRange(31, 0);
        SpatialRange s3 = gg.XYtoSpatialRange(0, 31);
        SpatialRange s4 = gg.XYtoSpatialRange(31, 31);

        assertEquals(s1.getLowerBoundForLatitude(), 45.0f, epsilon);
        assertEquals(s1.getLowerBoundForLongitude(), -112.5f, epsilon);
        assertEquals(s1.getUpperBoundForLatitude(), 44.82f, epsilon);
        assertEquals(s1.getUpperBoundForLongitude(), -112.14f, epsilon);
        assertEquals(s2.getLowerBoundForLatitude(), 45.0f, epsilon);
        assertEquals(s2.getLowerBoundForLongitude(), -101.60f, epsilon);
        assertEquals(s2.getUpperBoundForLatitude(), 44.82f, epsilon);
        assertEquals(s2.getUpperBoundForLongitude(), -101.25f, epsilon);
        assertEquals(s3.getLowerBoundForLatitude(), 39.55f, epsilon);
        assertEquals(s3.getLowerBoundForLongitude(), -112.5f, epsilon);
        assertEquals(s3.getUpperBoundForLatitude(), 39.37f, epsilon);
        assertEquals(s3.getUpperBoundForLongitude(), -112.14f, epsilon);
        assertEquals(s4.getLowerBoundForLatitude(), 39.55f, epsilon);
        assertEquals(s4.getLowerBoundForLongitude(), -101.60f, epsilon);
        assertEquals(s4.getUpperBoundForLatitude(), 39.37f, epsilon);
        assertEquals(s4.getUpperBoundForLongitude(), -101.25f, epsilon);
    }

    @Test
    public void testQuery() throws Exception {
        GeoavailabilityGrid gg = new GeoavailabilityGrid("9x", 20);
        gg.addPoint(new Coordinates(43.438f, -110.300f));
        List<Coordinates> p1 = new ArrayList<>();
        p1.add(new Coordinates(44.919f, -112.242f));
        p1.add(new Coordinates(43.111f, -105.414f));
        p1.add(new Coordinates(41.271f, -111.421f));
        GeoavailabilityQuery q1 = new GeoavailabilityQuery(p1);
        assertEquals(true, gg.intersects(q1));
        if (draw) {
            BufferedImage b = BitmapVisualization.drawBitmap(
                    QueryTransform.queryToGridBitmap(q1, gg),
                    gg.getWidth(), gg.getHeight(), Color.BLACK);
            BitmapVisualization.imageToFile(b, "Query1.gif");
        }

        List<Coordinates> p2 = new ArrayList<>();
        p2.add(new Coordinates(41.223f, -101.609f));
        p2.add(new Coordinates(39.663f, -101.641f));
        p2.add(new Coordinates(39.745f, -104.701f));
        GeoavailabilityQuery q2 = new GeoavailabilityQuery(p2);
        assertEquals(false, gg.intersects(q2));
        if (draw) {
            BufferedImage b = BitmapVisualization.drawBitmap(
                    QueryTransform.queryToGridBitmap(q2, gg),
                    gg.getWidth(), gg.getHeight(), Color.BLACK);
            BitmapVisualization.imageToFile(b, "Query2.gif");
        }
    }

    @Test
    public void testBitmapCorners() throws IOException {
        GeoavailabilityGrid gg = new GeoavailabilityGrid("9x", 10);
        gg.addPoint(new Coordinates(44.919f, -112.242f));
        gg.addPoint(new Coordinates(44.919f, -101.514f));
        gg.addPoint(new Coordinates(39.496f, -112.242f));
        gg.addPoint(new Coordinates(39.496f, -101.514f));

        if (draw) {
            BufferedImage b = BitmapVisualization.drawGeoavailabilityGrid(
                    gg, Color.BLACK);
            BitmapVisualization.imageToFile(b, "BitmapCorners.gif");
        }
    }

    @Test
    public void testHiResCorners() throws IOException {
        GeoavailabilityGrid gg = new GeoavailabilityGrid("9x", 25);
        gg.addPoint(new Coordinates(44.919f, -112.242f));
        gg.addPoint(new Coordinates(44.919f, -101.514f));
        gg.addPoint(new Coordinates(39.496f, -112.242f));
        gg.addPoint(new Coordinates(39.496f, -101.514f));

        if (draw) {
            BufferedImage b = BitmapVisualization.drawGeoavailabilityGrid(
                    gg, Color.BLACK);
            BitmapVisualization.imageToFile(b, "HiResCorners.gif");
        }
    }

    /**
     * Ensures out-of-bounds X points are not inserted into the
     * GeoavailabilityGrid.
     */
    @Test
    public void testXOutOfBounds() {
        GeoavailabilityGrid gg = new GeoavailabilityGrid("9x", 10);
        assertEquals(gg.addPoint(new Coordinates(44.819f, -113.350f)), false);
        assertEquals(gg.addPoint(new Coordinates(44.819f, -100.684f)), false);
        assertEquals(gg.addPoint(new Coordinates(39.496f, -113.350f)), false);
        assertEquals(gg.addPoint(new Coordinates(39.496f, -100.684f)), false);
        assertEquals(gg.addPoint(new Coordinates(0.0f, 0.0f)), false);
    }

    /**
     * Ensures out-of-bounds Y points are not inserted into the
     * GeoavailabilityGrid.
     */
    @Test
    public void testYOutOfBounds() {
        GeoavailabilityGrid gg = new GeoavailabilityGrid("9x", 10);
        assertEquals(gg.addPoint(new Coordinates(45.333f, -112.242f)), false);
        assertEquals(gg.addPoint(new Coordinates(45.360f, -101.514f)), false);
        assertEquals(gg.addPoint(new Coordinates(38.992f, -112.242f)), false);
        assertEquals(gg.addPoint(new Coordinates(38.992f, -101.514f)), false);
        assertEquals(gg.addPoint(new Coordinates(0.0f, 0.0f)), false);
    }

    /**
     * Tests the update procedure for bits that are inserted out of order.
     */
    @Test
    public void testUpdates() throws Exception {
        /* Insert points in indexed order */
        GeoavailabilityGrid g1 = new GeoavailabilityGrid("9x", 10);
        g1.addPoint(new Coordinates(44.919f, -112.242f));
        g1.addPoint(new Coordinates(44.919f, -101.514f));
        g1.addPoint(new Coordinates(39.496f, -112.242f));
        g1.addPoint(new Coordinates(39.496f, -101.514f));

        /* Insert points out of order */
        GeoavailabilityGrid g2 = new GeoavailabilityGrid("9x", 10);
        g2.addPoint(new Coordinates(39.496f, -101.514f));
        g2.addPoint(new Coordinates(39.496f, -112.242f));
        g2.addPoint(new Coordinates(44.919f, -101.514f));
        g2.addPoint(new Coordinates(44.919f, -112.242f));

        if (draw) {
            BufferedImage b1 = BitmapVisualization.drawGeoavailabilityGrid(g1);
            BufferedImage b2 = BitmapVisualization.drawGeoavailabilityGrid(g2);
            BitmapVisualization.imageToFile(b1, "Updates1.gif");
            BitmapVisualization.imageToFile(b2, "Updates2.gif");
        }
        assertEquals(true, g1.equals(g2));
    }
}
