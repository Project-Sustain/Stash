var org;
(function (org) {
    var jsweet;
    (function (jsweet) {
        /**
         * This is a very simple example that just shows an alert.
         * @class
         */
        var GeoHash = (function () {
            function GeoHash() {
            }
            GeoHash.__static_initialize = function () { if (!GeoHash.__static_initialized) {
                GeoHash.__static_initialized = true;
                GeoHash.__static_initializer_0();
            } };
            GeoHash.charMap_$LI$ = function () { GeoHash.__static_initialize(); if (GeoHash.charMap == null)
                GeoHash.charMap = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z']; return GeoHash.charMap; };
            ;
            GeoHash.charLookupTable_$LI$ = function () { GeoHash.__static_initialize(); if (GeoHash.charLookupTable == null)
                GeoHash.charLookupTable = ({}); return GeoHash.charLookupTable; };
            ;
            GeoHash.__static_initializer_0 = function () {
                for (var i = 0; i < GeoHash.charMap_$LI$().length; ++i) {
                    {
                        /* put */ (function (m, k, v) { if (m.entries == null)
                            m.entries = []; for (var i_1 = 0; i_1 < m.entries.length; i_1++)
                            if (m.entries[i_1].key.equals != null && m.entries[i_1].key.equals(k) || m.entries[i_1].key === k) {
                                m.entries[i_1].value = v;
                                return;
                            } m.entries.push({ key: k, value: v, getKey: function () { return this.key; }, getValue: function () { return this.value; } }); })(GeoHash.charLookupTable_$LI$(), GeoHash.charMap_$LI$()[i], i);
                    }
                    ;
                }
            };
            /*private*/ GeoHash.decodeBits = function (bits, latitude) {
                var low;
                var high;
                var middle;
                var offset;
                if (latitude) {
                    offset = 1;
                    low = -90.0;
                    high = 90.0;
                }
                else {
                    offset = 0;
                    low = -180.0;
                    high = 180.0;
                }
                for (var i = offset; i < bits.length; i += 2) {
                    {
                        middle = (high + low) / 2;
                        if (bits[i]) {
                            low = middle;
                        }
                        else {
                            high = middle;
                        }
                    }
                    ;
                }
                if (latitude) {
                    return [low, high];
                }
                else {
                    return [low, high];
                }
            };
            /*private*/ GeoHash.getBits = function (hash) {
                hash = hash.toLowerCase();
                var bits = ([]);
                for (var i = 0; i < hash.length; ++i) {
                    {
                        var charValue = (function (m, k) { if (m.entries == null)
                            m.entries = []; for (var i_2 = 0; i_2 < m.entries.length; i_2++)
                            if (m.entries[i_2].key.equals != null && m.entries[i_2].key.equals(k) || m.entries[i_2].key === k) {
                                return m.entries[i_2].value;
                            } return null; })(GeoHash.charLookupTable_$LI$(), hash.charAt(i));
                        for (var j = 0; j < 5; ++j, charValue <<= 1) {
                            {
                                /* add */ (bits.push((charValue & 16) === 16) > 0);
                            }
                            ;
                        }
                    }
                    ;
                }
                return bits;
            };
            GeoHash.decodeHash = function (geoHash) {
                var bits = GeoHash.getBits(geoHash);
                var longitude = GeoHash.decodeBits(bits, false);
                var latitude = GeoHash.decodeBits(bits, true);
                return [longitude[0] , latitude[0] , longitude[1] , latitude[1]];
            };
            GeoHash.getInternalGeohashes$java_lang_String = function (geohash) {
                var childrenGeohashes = ([]);
                for (var index5300 = 0; index5300 < GeoHash.charMap_$LI$().length; index5300++) {
                    var c = GeoHash.charMap_$LI$()[index5300];
                    {
                        /* add */ (childrenGeohashes.push(geohash + c) > 0);
                    }
                }
                return childrenGeohashes;
            };
            GeoHash.getInternalGeohashes$java_lang_String$int = function (geohash, precision) {
                var allGeoHashes = ([]);
                /* add */ (allGeoHashes.push(geohash) > 0);
                for (var i = geohash.length; i < precision; i++) {
                    {
                        var currentGeohashes = ([]);
                        for (var index5301 = 0; index5301 < allGeoHashes.length; index5301++) {
                            var geoHash = allGeoHashes[index5301];
                            {
                                /* addAll */ (function (l1, l2) { return l1.push.apply(l1, l2); })(currentGeohashes, GeoHash.getInternalGeohashes$java_lang_String(geoHash));
                            }
                        }
                        allGeoHashes = currentGeohashes;
                    }
                    ;
                }
                return allGeoHashes;
            };
            GeoHash.getInternalGeohashes = function (geohash, precision) {
                if (((typeof geohash === 'string') || geohash === null) && ((typeof precision === 'number') || precision === null)) {
                    return org.jsweet.GeoHash.getInternalGeohashes$java_lang_String$int(geohash, precision);
                }
                else if (((typeof geohash === 'string') || geohash === null) && precision === undefined) {
                    return org.jsweet.GeoHash.getInternalGeohashes$java_lang_String(geohash);
                }
                else
                    throw new Error('invalid overload');
            };
            GeoHash.getCoordinateBounds = function (geohashes) {
                var fullStr = [];
                var nameStr = "[";
                var rectangles = ([]);
                var i = 0;
                for (var index5302 = 0; index5302 < geohashes.length; index5302++) {
                    var s = geohashes[index5302];
                    {
                        var range1 = GeoHash.decodeHash(s);
                        fullStr.push(range1)
                        i++;
                    }
                }
                //console.info("COMPLETE...");
                return fullStr;
            };
            
            GeoHash.getBoundsNames = function (geohashes) {
                var fullStr = "[";
                var nameStr = [];
                var rectangles = ([]);
                var i = 0;
                for (var index5302 = 0; index5302 < geohashes.length; index5302++) {
                    var s = geohashes[index5302];
                    {
                    	nameStr.push(s)
                        
                        i++;
                    }
                }
                //console.info("COMPLETE...");
                return nameStr;
            };
            GeoHash.main = function (arg) {
                var allCoords = "[";
                var allNames = [];
                var i = 0;
                {
                    var array5304 = GeoHash.getInternalGeohashes$java_lang_String("9xjqb");
                    for (var index5303 = 0; index5303 < array5304.length; index5303++) {
                        var s = array5304[index5303];
                        {
                        	var geohashNames = GeoHash.getInternalGeohashes$java_lang_String$int(s, 8);
                            var op = GeoHash.getCoordinateBounds(geohashNames);
                            var opNames = GeoHash.getBoundsNames(geohashNames);
                            
                            
                            //console.info(op.length);
                            //console.info(opNames.length)
                            
                            /*
                            var cs = op.split("\\$\\$")[0];
                            var ns = op.split("\\$\\$")[1];
                            if (i === 0) {
                                allCoords += cs;
                                allNames += ns;
                            }
                            else {
                                allCoords += "," + cs;
                                allNames += "," + ns + "";
                            }*/
                            i++;
                        }
                    }
                    //console.info(allNames);
                }
            };
            return GeoHash;
        }());
        GeoHash.__static_initialized = false;
        jsweet.GeoHash = GeoHash;
        GeoHash["__class"] = "org.jsweet.GeoHash";
    })(jsweet = org.jsweet || (org.jsweet = {}));
})(org || (org = {}));
org.jsweet.GeoHash.charLookupTable_$LI$();
org.jsweet.GeoHash.charMap_$LI$();
org.jsweet.GeoHash.__static_initialize();
org.jsweet.GeoHash.main(null);


