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

package galileo.fs;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.CRC32;

import galileo.dataset.feature.Feature;
import galileo.dataset.feature.FeatureType;
import galileo.graph.FeaturePath;
import galileo.graph.Vertex;
import galileo.serialization.SerializationException;
import galileo.serialization.SerializationInputStream;
import galileo.serialization.SerializationOutputStream;
import galileo.util.Pair;
import galileo.util.PerformanceTimer;

public class PathJournal {

    private static final Logger logger = Logger.getLogger("galileo");

    private String pathFile;
    private String indexFile;

    private DataOutputStream pathStore;
    private DataOutputStream indexStore;

    private Map<String, Integer> featureNames = new HashMap<>();
    private Map<Integer, Pair<String, FeatureType>> featureIndex
        = new HashMap<>();
    private int nextId = 1;

    private boolean running = false;

    public PathJournal(String pathFile) {
        this.pathFile = pathFile;
        this.indexFile = pathFile + ".index";
    }

    /**
     * Recovers the Path Journal from disk.
     *
     * @param paths A list that will be populated with all the recovered paths.
     *
     * @return true if the recovery was completed cleanly; if false, there were
     * issues with the journal files (possible corruption).
     */
    public boolean recover(List<FeaturePath<String>> paths)
    throws IOException {
        PerformanceTimer timer = new PerformanceTimer();
        timer.start();
        boolean clean = true;

        if (new File(pathFile).exists() == false
                || new File(indexFile).exists() == false) {
            erase();
            return false;
        }

        try {
            recoverIndex();
        } catch (EOFException e) {
            logger.info("Reached end of path journal index.");
        } catch (FileNotFoundException e) {
            logger.info("Could not locate journal index.  Journal recovery is "
                    + "not possible.");
            return false;
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error reading path index!", e);
            clean = false;
        }
        logger.log(Level.INFO, "Features read: {0}", featureNames.size());

        try {
            recoverPaths(paths);
        } catch (EOFException e) {
            logger.info("Reached end of path journal.");
        } catch (NullPointerException | SerializationException e) {
            logger.log(Level.WARNING, "Error deserializing path!", e);
            clean = false;
        }
        logger.log(Level.INFO, "Recovered {0} paths.", paths.size());
        timer.stop();
        logger.log(Level.INFO, "Finished PathJournal recovery in "
                + timer.getLastResult() + " ms.");
        return clean;
    }

    /**
     * Recovers the Feature index.  The index contains a mapping from feature
     * identification numbers (used in this class only) to FeatureTypes and
     * names.  After executing this method, the index is populated with Feature
     * mappings read from disk.
     */
    private void recoverIndex()
    throws FileNotFoundException, IOException {
        DataInputStream indexIn = new DataInputStream(
                new BufferedInputStream(
                    new FileInputStream(indexFile)));

        while (true) {
            long check = indexIn.readLong();
            int entryLength = indexIn.readInt();

            byte[] entry = new byte[entryLength];
            int read = indexIn.read(entry);
            if (read != entry.length) {
                logger.info("Reached end of journal index");
                /* Did not read a complete entry, we're done. */
                break;
            }

            CRC32 crc = new CRC32();
            crc.update(entry);
            if (crc.getValue() != check) {
                logger.warning("Detected checksum mismatch in journal index; "
                        + "ignoring entry.");
                continue;
            }

            SerializationInputStream sIn = new SerializationInputStream(
                    new ByteArrayInputStream(entry));

            int featureId = sIn.readInt();
            FeatureType type = FeatureType.fromInt(sIn.readInt());
            String name  = sIn.readString();

            newFeature(featureId, type, name);
            sIn.close();
        }

        indexIn.close();
    }

    /**
     * Recovers Paths stored in the Path Journal.
     */
    private void recoverPaths(List<FeaturePath<String>> paths)
    throws IOException, SerializationException {
        DataInputStream pathIn = new DataInputStream(
                new BufferedInputStream(
                    new FileInputStream(pathFile)));

        while (true) {
            long check = pathIn.readLong();
            int pathSize = pathIn.readInt();

            byte[] pathBytes = new byte[pathSize];
            int read = pathIn.read(pathBytes);
            if (read != pathSize) {
                logger.info("Reached end of path index");
                break;
            }

            CRC32 crc = new CRC32();
            crc.update(pathBytes);
            if (crc.getValue() != check) {
                logger.warning("Detected checksum mismatch; ignoring path.");
                continue;
            }

            FeaturePath<String> fp = deserializePath(pathBytes);
            paths.add(fp);
        }

        pathIn.close();
    }

    /**
     * Prepares the journal files and allows new entries to be written.
     */
    public void start()
    throws IOException {
        OutputStream out = Files.newOutputStream(Paths.get(pathFile),
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.APPEND);
        pathStore = new DataOutputStream(new BufferedOutputStream(out));

        OutputStream indexOut = Files.newOutputStream(Paths.get(indexFile),
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.APPEND,
                StandardOpenOption.DSYNC);
        indexStore = new DataOutputStream(new BufferedOutputStream(indexOut));

        running = true;
    }

    /**
     * Determines whether the specified Feature information is in the index.  If
     * the feature is not present, it is added to the index.
     *
     * @param feature Feature to check for in the index.
     */
    private void checkIndex(Feature feature) {
        String featureName = feature.getName();

        if (featureNames.get(featureName) != null) {
            return;
        } else {
            int featureId = newFeature(feature);
            try {
                writeIndex(featureId, feature);
            } catch (IOException e) {
                logger.log(Level.SEVERE,
                        "Could not write to path journal index!", e);
            }
        }
    }

    /**
     * Adds a particular feature to the index.
     */
    private int newFeature(Feature feature) {
        int featureId = nextId;
        FeatureType type = feature.getType();
        String name = feature.getName();

        return newFeature(featureId, type, name);
    }

    /**
     * Adds a (featureId, FeatureType, FeatureName) tuple to the index directly.
     */
    private int newFeature(int featureId, FeatureType type, String name) {
        logger.log(Level.INFO, "Adding new Feature to path journal index: {0}",
                name);

        featureNames.put(name, featureId);
        featureIndex.put(featureId, new Pair<>(name, type));

        /* Update the nextId counter here */
        nextId = featureId + 1;

        return featureId;
    }

    /**
     * Appends a new {@link Feature} to the on-disk Feature index.
     *
     * @param featureId identifier of the Feature being written
     * @param feature 
     */
    private void writeIndex(int featureId, Feature feature)
    throws IOException {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        SerializationOutputStream sOut = new SerializationOutputStream(bOut);
        sOut.writeInt(featureId);
        sOut.writeInt(feature.getType().toInt());
        sOut.writeString(feature.getName());
        sOut.close();
        byte[] entry = bOut.toByteArray();

        CRC32 crc = new CRC32();
        crc.update(entry);
        long check = crc.getValue();

        indexStore.writeLong(check);
        indexStore.writeInt(entry.length);
        indexStore.write(entry);
        indexStore.flush();
    }

    /**
     * Adds a graph {@link FeaturePath} to the journal.
     *
     * @param path The FeaturePath to add to the journal.
     */
    public void persistPath(FeaturePath<String> path)
    throws FileSystemException, IOException {
        if (running == false) {
            throw new FileSystemException("Path Journal has not been started!");
        }

        byte[] pathBytes = serializePath(path);

        CRC32 crc = new CRC32();
        crc.update(pathBytes);
        long check = crc.getValue();

        pathStore.writeLong(check);
        pathStore.writeInt(pathBytes.length);
        pathStore.write(pathBytes);
        pathStore.flush();
    }

    /**
     * Given a {@link FeaturePath}, this method serializes the path data to a
     * byte array that can be appended to the path journal.
     */
    private byte[] serializePath(FeaturePath<String> path)
    throws IOException {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        SerializationOutputStream sOut = new SerializationOutputStream(bOut);
        sOut.writeInt(path.size());
        for (Vertex<Feature, String> v : path.getVertices()) {
            Feature f = v.getLabel();
            checkIndex(f);
            int featureId = featureNames.get(f.getName());
            sOut.writeInt(featureId);
            sOut.writeSerializable(f.getDataContainer());
        }
        sOut.writeInt(path.getPayload().size());
        for (String s : path.getPayload()) {
            sOut.writeString(s);
        }
        sOut.close();
        return bOut.toByteArray();
    }

    /**
     * Deserializes a {@link FeaturePath} from a byte array.
     */
    private FeaturePath<String> deserializePath(byte[] pathBytes)
    throws IOException, SerializationException {
        SerializationInputStream sIn = new SerializationInputStream(
                new ByteArrayInputStream(pathBytes));

        int vertices = sIn.readInt();
        FeaturePath<String> fp = new FeaturePath<>();
        for (int i = 0; i < vertices; ++i) {
            int featureId = sIn.readInt();
            Pair<String, FeatureType> featureInfo
                = featureIndex.get(featureId);

            Feature f = new Feature(featureInfo.a, featureInfo.b, sIn);
            fp.add(f);
        }

        int payloads = sIn.readInt();
        for (int i = 0; i < payloads; ++i) {
            String payload = sIn.readString();
            fp.addPayload(payload);
        }
        sIn.close();
        return fp;
    }

    /**
     * Removes the Path Journal and its Feature index files.  This method shuts
     * the PathJournal down before deleting the files.
     */
    public void erase()
    throws IOException {
        shutdown();

        new File(indexFile).delete();
        new File(pathFile).delete();
    }

    /**
     * Closes open journal files and stops accepting new FeaturePaths.
     */
    public void shutdown()
    throws IOException {
        if (running == false) {
            return;
        }

        indexStore.close();
        pathStore.close();
    }
}

