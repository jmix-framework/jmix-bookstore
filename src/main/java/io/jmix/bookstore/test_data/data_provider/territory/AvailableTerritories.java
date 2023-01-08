package io.jmix.bookstore.test_data.data_provider.territory;

import io.jmix.bookstore.employee.Territory;
import io.jmix.bookstore.test_data.data_provider.region.AvailableRegions;
import org.locationtech.jts.geom.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record AvailableTerritories(List<Territory> territories) {
    public static Stream<Entry> all() {
        return Arrays.stream(AvailableTerritories.Entry.values());
    }

    private static Coordinate coordinate(double lat, double lng) {
        return new Coordinate(lng, lat);
    }

    private static Polygon geographicalArea(Coordinate... coordinates) {
        return new GeometryFactory().createPolygon(
                coordinates
        );
    }

    public int size() {
        return territories().size();
    }

    public Territory find(Entry entry) {
        return territories.stream()
                .filter(region -> entry.getName().equals(region.getName()))
                .findFirst()
                .orElseThrow();
    }

    public Set<Territory> findTerritoriesFromRegion(AvailableRegions.Entry... regions) {
        return territories().stream()
                .filter(territory -> Arrays.stream(regions).anyMatch(region -> territory.getRegion().getName().equals(region.getName())))
                .collect(Collectors.toSet());
    }

    public Optional<Territory> findTerritoryForPosition(Point position) {
        return territories.stream()
                .filter(territory -> territory.getGeographicalArea() != null)
                .filter(territory -> territory.getGeographicalArea().contains(position))
                .findFirst();
    }

    public enum Entry {

        KY_TN_WEST_NC_MEMPHIS(
                "KY, TN, West NC, Memphis",
                AvailableRegions.Entry.US_EAST,
                geographicalArea(
                        coordinate(36.437166, -88.107262),
                        coordinate(37.512526, -88.144287),
                        coordinate(37.801519, -87.048948),
                        coordinate(37.738557, -86.546323),
                        coordinate(38.208385, -86.345823),
                        coordinate(38.005327, -85.959649),
                        coordinate(38.026357, -85.472732),
                        coordinate(38.453303, -85.414093),
                        coordinate(38.901541, -84.573773),
                        coordinate(38.589001, -84.218166),
                        coordinate(38.522988, -83.651104),
                        coordinate(38.55781, -82.988909),
                        coordinate(38.276942, -82.455654),
                        coordinate(36.555982, -80.470047),
                        coordinate(34.764179, -80.871735),
                        coordinate(34.96925, -83.040848),
                        coordinate(34.96925, -89.456863),
                        coordinate(34.481656, -90.116043),
                        coordinate(35.005253, -90.863113),
                        coordinate(36.476377, -90.194664),
                        coordinate(36.437166, -88.107262)
                )
        ),


        AZ_NM(
                "AZ, NM",
                AvailableRegions.Entry.US_SOUTH,
                geographicalArea(
                        coordinate(32.720617, -114.71805),
                        coordinate(32.491891, -114.817112),
                        coordinate(31.332933, -111.072991),
                        coordinate(31.334069, -108.2064),
                        coordinate(31.783608, -108.20676),
                        coordinate(31.783685, -106.530816),
                        coordinate(31.742987, -106.487956),
                        coordinate(31.75087, -106.46461),
                        coordinate(31.761379, -106.452594),
                        coordinate(31.82011, -106.009349),
                        coordinate(32.003854, -105.854484),
                        coordinate(32.001621, -103.05819),
                        coordinate(36.513797, -102.980577),
                        coordinate(36.983572, -103.000538),
                        coordinate(36.985479, -104.023656),
                        coordinate(36.998414, -110.963679),
                        coordinate(37.000753, -112.138642),
                        coordinate(36.981807, -112.134046),
                        coordinate(36.956824, -112.564197),
                        coordinate(36.869452, -114.042565),
                        coordinate(36.152532, -114.049796),
                        coordinate(34.811859, -114.587758),
                        coordinate(34.471392, -114.108907),
                        coordinate(34.24579, -114.033376),
                        coordinate(34.061184, -114.286399),
                        coordinate(33.780584, -114.428621),
                        coordinate(33.515644, -114.476367),
                        coordinate(33.397327, -114.598902),
                        coordinate(33.109524, -114.651311),
                        coordinate(32.99557, -114.428152),
                        coordinate(32.743422, -114.440854),
                        coordinate(32.720617, -114.71805)
                )
        ),


        PA_VA_WV_NC(
                "PA, VA, WV, NC",
                AvailableRegions.Entry.US_EAST,
                geographicalArea(
                        coordinate(33.834597, -78.444185),
                        coordinate(33.752426, -77.774019),
                        coordinate(34.362249, -77.422457),
                        coordinate(34.516281, -76.488619),
                        coordinate(35.138546, -75.510836),
                        coordinate(35.309067, -75.247164),
                        coordinate(36.094158, -75.499849),
                        coordinate(37.060725, -75.747814),
                        coordinate(38.630349, -74.756813),
                        coordinate(39.194547, -75.350075),
                        coordinate(39.686648, -75.679665),
                        coordinate(40.275924, -75.987282),
                        coordinate(40.74368, -75.899391),
                        coordinate(41.323781, -75.789528),
                        coordinate(41.931465, -75.877419),
                        coordinate(42.029468, -79.722633),
                        coordinate(42.208749, -80.337868),
                        coordinate(42.045787, -80.447731),
                        coordinate(39.697578, -80.517399),
                        coordinate(39.921886, -80.794702),
                        coordinate(39.825187, -80.814421),
                        coordinate(39.776654, -80.861113),
                        coordinate(39.701677, -80.828154),
                        coordinate(39.652, -80.856993),
                        coordinate(39.608635, -80.884459),
                        coordinate(39.606519, -80.920165),
                        coordinate(39.51017, -81.05612),
                        coordinate(39.424297, -81.137145),
                        coordinate(39.424297, -81.17697),
                        coordinate(39.375482, -81.249754),
                        coordinate(39.34082, -81.375357),
                        coordinate(39.353564, -81.39321),
                        coordinate(39.38329, -81.406943),
                        coordinate(39.39921, -81.441275),
                        coordinate(38.283591, -82.464361),
                        coordinate(38.273889, -82.447881),
                        coordinate(36.559515, -80.472794),
                        coordinate(34.766039, -80.87079),
                        coordinate(34.818872, -80.80873),
                        coordinate(34.820801, -79.697844),
                        coordinate(33.834597, -78.444185)
                )
        ),

        TX_NORTH_OK_NOT_DALLAS(
                "TX North, OK",
                AvailableRegions.Entry.US_SOUTH,
                geographicalArea(
                        coordinate(36.508752, -102.981768),
                        coordinate(36.987647, -102.99836),
                        coordinate(36.982588, -102.306656),
                        coordinate(36.994106, -102.307),
                        coordinate(36.993531, -101.924295),
                        coordinate(37.000393, -94.612058),
                        coordinate(36.494751, -94.617965),
                        coordinate(35.345375, -94.420806),
                        coordinate(33.635944, -94.489417),
                        coordinate(33.641134, -94.46811),
                        coordinate(33.633988, -94.45472),
                        coordinate(33.610259, -94.448884),
                        coordinate(33.58967, -94.44442),
                        coordinate(33.593388, -94.425538),
                        coordinate(33.587096, -94.420388),
                        coordinate(33.576799, -94.421418),
                        coordinate(33.567931, -94.406312),
                        coordinate(33.572222, -94.388802),
                        coordinate(33.58252, -94.387772),
                        coordinate(33.587954, -94.380906),
                        coordinate(33.587096, -94.374039),
                        coordinate(33.581662, -94.367516),
                        coordinate(33.574225, -94.370949),
                        coordinate(33.57165, -94.377472),
                        coordinate(33.564499, -94.381936),
                        coordinate(33.559349, -94.391549),
                        coordinate(33.554199, -94.396698),
                        coordinate(33.548191, -94.390175),
                        coordinate(33.547904, -94.367173),
                        coordinate(33.555057, -94.356186),
                        coordinate(33.567359, -94.351037),
                        coordinate(33.574225, -94.336274),
                        coordinate(33.570506, -94.329407),
                        coordinate(33.551052, -94.329064),
                        coordinate(33.548184, -94.313118),
                        coordinate(33.556195, -94.304878),
                        coordinate(33.56535, -94.301789),
                        coordinate(33.576506, -94.304535),
                        coordinate(33.581655, -94.301102),
                        coordinate(33.582227, -94.284966),
                        coordinate(33.581369, -94.271576),
                        coordinate(33.581924, -94.044464),
                        coordinate(31.986148, -94.03651),
                        coordinate(32.34121, -96.510451),
                        coordinate(32.010987, -103.056071),
                        coordinate(36.508752, -102.981768)
                )
        ),


        CA(
                "CA",
                AvailableRegions.Entry.US_WEST,
                geographicalArea(
                        coordinate(41.976912, -124.230942),
                        coordinate(41.729369, -124.28862),
                        coordinate(41.694514, -124.154037),
                        coordinate(40.933252, -124.166332),
                        coordinate(40.434029, -124.433502),
                        coordinate(40.169979, -124.375352),
                        coordinate(39.514131, -123.815049),
                        coordinate(39.345143, -123.836657),
                        coordinate(39.059941, -123.724047),
                        coordinate(38.921181, -123.765246),
                        coordinate(38.354479, -123.108898),
                        coordinate(38.093904, -123.036704),
                        coordinate(37.773293, -123.003745),
                        coordinate(36.785504, -122.168784),
                        coordinate(35.695643, -121.487632),
                        coordinate(34.214322, -120.518303),
                        coordinate(33.795376, -120.507317),
                        coordinate(32.785218, -119.474602),
                        coordinate(32.468386, -117.990385),
                        coordinate(32.743465, -114.4412),
                        coordinate(32.99541, -114.428422),
                        coordinate(33.109472, -114.652301),
                        coordinate(33.27496, -114.619342),
                        coordinate(33.396578, -114.600116),
                        coordinate(33.515735, -114.47652),
                        coordinate(33.614968, -114.45444),
                        coordinate(33.780538, -114.429088),
                        coordinate(33.890047, -114.374156),
                        coordinate(34.063148, -114.286265),
                        coordinate(34.244978, -114.03358),
                        coordinate(34.471715, -114.110484),
                        coordinate(34.809637, -114.583529),
                        coordinate(41.976912, -124.230942)
                )
        ),

        NYC_PHILADELPHIA_BOSTON(
                "NYC, Philadelphia, Boston",
                AvailableRegions.Entry.US_EAST,
                geographicalArea(
                        coordinate(38.702157, -74.780931),
                        coordinate(39.622119, -73.869066),
                        coordinate(40.446457, -73.704271),
                        coordinate(40.979412, -71.539965),
                        coordinate(41.302088, -71.188402),
                        coordinate(41.120261, -70.606127),
                        coordinate(41.095427, -69.826097),
                        coordinate(41.754442, -69.76018),
                        coordinate(42.260573, -70.067797),
                        coordinate(42.293088, -70.518236),
                        coordinate(42.609232, -70.683031),
                        coordinate(42.625402, -71.561937),
                        coordinate(41.499867, -73.363695),
                        coordinate(41.343341, -74.154711),
                        coordinate(40.730121, -74.671068),
                        coordinate(39.9956, -75.833387),
                        coordinate(39.503354, -75.572526),
                        coordinate(38.702157, -74.780931)
                )
        ),

        KS_NE_NOT_KANSAS_CITY(
                "KS, NE, not Kansas City",
                AvailableRegions.Entry.US_SOUTH,
                geographicalArea(
                        coordinate(37.020098, -94.680176),
                        coordinate(37.002553, -102.282715),
                        coordinate(40.212441, -102.480469),
                        coordinate(40.979898, -104.084473),
                        coordinate(42.536892, -104.0625),
                        coordinate(42.956423, -104.040527),
                        coordinate(42.988576, -98.371582),
                        coordinate(42.956423, -95.075684),
                        coordinate(39.300299, -94.790039),
                        coordinate(39.044786, -95.427246),
                        coordinate(38.496594, -94.96582),
                        coordinate(37.020098, -94.680176)
                )
        ),


        MN_IA_KANSAS_CITY_IS_MO_WI(
                "MN, IA, Kansas City, IS, MO, WI",
                AvailableRegions.Entry.US_NORTH,
                geographicalArea(
                        coordinate(49.001225, -97.199135),
                        coordinate(48.999887, -95.123148),
                        coordinate(49.373278, -95.150614),
                        coordinate(49.372461, -94.934621),
                        coordinate(49.313408, -94.797292),
                        coordinate(48.856506, -94.668202),
                        coordinate(48.80769, -94.690175),
                        coordinate(48.729849, -94.624257),
                        coordinate(48.693602, -94.500661),
                        coordinate(48.713541, -94.385304),
                        coordinate(48.715353, -94.286427),
                        coordinate(48.68091, -94.228749),
                        coordinate(48.650072, -94.239736),
                        coordinate(48.644628, -94.063954),
                        coordinate(48.640998, -93.893666),
                        coordinate(48.635553, -93.814015),
                        coordinate(48.559263, -93.800282),
                        coordinate(48.517436, -93.811269),
                        coordinate(48.526532, -93.698659),
                        coordinate(48.521074, -93.632741),
                        coordinate(48.535626, -93.602529),
                        coordinate(48.544718, -93.511891),
                        coordinate(48.601055, -93.451467),
                        coordinate(48.610374, -93.340992),
                        coordinate(48.638673, -93.246009),
                        coordinate(48.633228, -93.10868),
                        coordinate(48.635043, -92.924659),
                        coordinate(48.593279, -92.847755),
                        coordinate(48.533296, -92.704933),
                        coordinate(48.546025, -92.644508),
                        coordinate(48.496908, -92.617042),
                        coordinate(48.487806, -92.704933),
                        coordinate(48.458672, -92.693946),
                        coordinate(48.433166, -92.619789),
                        coordinate(48.44592, -92.504432),
                        coordinate(48.414939, -92.45774),
                        coordinate(48.349269, -92.454994),
                        coordinate(48.287169, -92.413795),
                        coordinate(48.232313, -92.375343),
                        coordinate(48.246947, -92.279212),
                        coordinate(48.267062, -92.290199),
                        coordinate(48.309096, -92.298439),
                        coordinate(48.354745, -92.281959),
                        coordinate(48.36022, -92.238014),
                        coordinate(48.35657, -92.185829),
                        coordinate(48.369344, -92.15287),
                        coordinate(48.365694, -92.086952),
                        coordinate(48.340141, -92.051246),
                        coordinate(48.301788, -92.001808),
                        coordinate(48.259749, -91.988075),
                        coordinate(48.24146, -91.971595),
                        coordinate(48.226824, -91.922157),
                        coordinate(48.232313, -91.878211),
                        coordinate(48.204598, -91.728641),
                        coordinate(48.19754, -91.696937),
                        coordinate(48.159079, -91.683204),
                        coordinate(48.115089, -91.669471),
                        coordinate(48.109588, -91.6063),
                        coordinate(48.113255, -91.523902),
                        coordinate(48.050869, -91.545875),
                        coordinate(48.072896, -91.479957),
                        coordinate(48.061884, -91.408546),
                        coordinate(48.071061, -91.315162),
                        coordinate(48.155415, -91.133888),
                        coordinate(48.254263, -90.892189),
                        coordinate(48.243289, -90.818031),
                        coordinate(48.166508, -90.804083),
                        coordinate(48.100517, -90.738165),
                        coordinate(48.129857, -90.548651),
                        coordinate(48.102703, -90.559697),
                        coordinate(48.109688, -90.455267),
                        coordinate(48.103713, -90.427576),
                        coordinate(48.112883, -90.402857),
                        coordinate(48.093624, -90.358912),
                        coordinate(48.111049, -90.313593),
                        coordinate(48.100962, -90.280634),
                        coordinate(48.109215, -90.227076),
                        coordinate(48.112883, -90.161158),
                        coordinate(48.111966, -90.121332),
                        coordinate(48.09821, -90.041682),
                        coordinate(48.078029, -90.001856),
                        coordinate(48.055086, -89.981257),
                        coordinate(48.033969, -89.979883),
                        coordinate(48.014681, -89.962031),
                        coordinate(48.018355, -89.934565),
                        coordinate(48.001818, -89.905726),
                        coordinate(47.993548, -89.88238),
                        coordinate(47.994467, -89.860407),
                        coordinate(48.009169, -89.837061),
                        coordinate(48.021111, -89.804102),
                        coordinate(48.025704, -89.76565),
                        coordinate(48.025704, -89.740931),
                        coordinate(48.024785, -89.717585),
                        coordinate(48.013594, -89.709499),
                        coordinate(48.010609, -89.69817),
                        coordinate(48.012905, -89.677227),
                        coordinate(48.00992, -89.665897),
                        coordinate(48.012905, -89.658001),
                        coordinate(48.005556, -89.649761),
                        coordinate(48.013824, -89.623325),
                        coordinate(48.018647, -89.608562),
                        coordinate(48.008082, -89.603413),
                        coordinate(48.002799, -89.58144),
                        coordinate(47.999583, -89.558437),
                        coordinate(48.008682, -89.48708),
                        coordinate(47.943386, -89.249067),
                        coordinate(48.249198, -88.413978),
                        coordinate(47.653189, -87.040687),
                        coordinate(46.743831, -84.837027),
                        coordinate(46.527749, -83.722773),
                        coordinate(45.196615, -82.382441),
                        coordinate(43.340224, -82.030878),
                        coordinate(42.963992, -82.367978),
                        coordinate(42.520225, -82.499814),
                        coordinate(42.252441, -83.027158),
                        coordinate(42.01324, -83.094878),
                        coordinate(41.759692, -83.006988),
                        coordinate(41.690516, -84.7938),
                        coordinate(41.760212, -84.807533),
                        coordinate(41.760212, -87.526649),
                        coordinate(38.68417, -87.476921),
                        coordinate(37.50067, -88.138762),
                        coordinate(36.462987, -88.134384),
                        coordinate(36.53364, -94.418564),
                        coordinate(36.956202, -94.63829),
                        coordinate(38.485576, -94.945908),
                        coordinate(39.033853, -95.429306),
                        coordinate(39.310328, -94.785061),
                        coordinate(42.959061, -95.091434),
                        coordinate(42.9671, -96.497684),
                        coordinate(43.135692, -96.38782),
                        coordinate(43.238513, -96.506288),
                        coordinate(43.490105, -96.533754),
                        coordinate(43.509748, -96.403055),
                        coordinate(45.315973, -96.404428),
                        coordinate(49.001225, -97.199135)
                )
        ),


        NY_VT_ME(
                "NY, VT, ME",
                AvailableRegions.Entry.US_EAST,
                geographicalArea(
                        coordinate(42.183043, -80.295382),
                        coordinate(42.835696, -78.865356),
                        coordinate(43.04882, -79.096069),
                        coordinate(43.448931, -79.16748),
                        coordinate(43.640051, -78.684082),
                        coordinate(43.616194, -76.717529),
                        coordinate(44.424708, -76.440811),
                        coordinate(45.173082, -74.639053),
                        coordinate(45.049027, -71.3871),
                        coordinate(45.343218, -71.29921),
                        coordinate(45.589777, -70.618057),
                        coordinate(45.957594, -70.31044),
                        coordinate(46.277445, -70.288467),
                        coordinate(46.504773, -70.112686),
                        coordinate(47.136257, -69.673233),
                        coordinate(47.533139, -69.183054),
                        coordinate(47.354815, -68.831491),
                        coordinate(47.280335, -68.304148),
                        coordinate(47.444053, -68.479929),
                        coordinate(47.339928, -67.666941),
                        coordinate(46.695786, -67.622995),
                        coordinate(45.553667, -67.688913),
                        coordinate(45.584431, -67.24946),
                        coordinate(45.198671, -67.293406),
                        coordinate(45.198097, -67.056127),
                        coordinate(44.949826, -66.891332),
                        coordinate(44.567572, -66.825414),
                        coordinate(44.41867, -66.935277),
                        coordinate(44.30871, -67.04514),
                        coordinate(44.025008, -67.660375),
                        coordinate(43.565062, -69.97849),
                        coordinate(42.571303, -70.545906),
                        coordinate(42.576237, -70.664478),
                        coordinate(42.608589, -70.685078),
                        coordinate(42.625826, -71.563008),
                        coordinate(41.494283, -73.372563),
                        coordinate(41.342342, -74.150333),
                        coordinate(40.729113, -74.672184),
                        coordinate(39.992443, -75.825748),
                        coordinate(40.273829, -75.996037),
                        coordinate(41.334094, -75.792789),
                        coordinate(41.925334, -75.886173),
                        coordinate(42.017305, -79.721196),
                        coordinate(42.183043, -80.295382)
                )
        ),


        TX_SOUTH(
                "TX South",
                AvailableRegions.Entry.US_SOUTH,
                geographicalArea(
                        coordinate(31.761747, -106.444733),
                        coordinate(31.820204, -106.009226),
                        coordinate(32.004415, -105.854763),
                        coordinate(32.00162, -103.05814),
                        coordinate(32.010848, -103.05809),
                        coordinate(32.080291, -101.711809),
                        coordinate(32.341826, -96.513535),
                        coordinate(31.986134, -94.036562),
                        coordinate(31.993378, -94.036562),
                        coordinate(31.995292, -94.03331),
                        coordinate(31.994455, -94.031036),
                        coordinate(31.996312, -94.02962),
                        coordinate(31.996166, -94.027989),
                        coordinate(31.992126, -94.025156),
                        coordinate(31.990088, -94.023096),
                        coordinate(31.990379, -94.021294),
                        coordinate(31.992344, -94.019449),
                        coordinate(31.992635, -94.018462),
                        coordinate(31.99158, -94.017603),
                        coordinate(31.98936, -94.018762),
                        coordinate(31.985356, -94.016917),
                        coordinate(31.979804, -94.016423),
                        coordinate(31.977637, -94.01187),
                        coordinate(31.973305, -94.00672),
                        coordinate(31.960519, -94.000351),
                        coordinate(31.953329, -93.995043),
                        coordinate(31.948813, -93.990408),
                        coordinate(31.941676, -93.98749),
                        coordinate(31.938908, -93.981997),
                        coordinate(31.923028, -93.975989),
                        coordinate(31.919769, -93.969777),
                        coordinate(31.922975, -93.965056),
                        coordinate(31.924177, -93.963297),
                        coordinate(31.923229, -93.95922),
                        coordinate(31.916491, -93.955872),
                        coordinate(31.911172, -93.955229),
                        coordinate(31.910316, -93.939379),
                        coordinate(31.908968, -93.936461),
                        coordinate(31.912465, -93.929509),
                        coordinate(31.912393, -93.927835),
                        coordinate(31.90671, -93.923629),
                        coordinate(31.90405, -93.921526),
                        coordinate(31.903066, -93.922642),
                        coordinate(31.902265, -93.925346),
                        coordinate(31.690106, -93.770295),
                        coordinate(31.132222, -93.567048),
                        coordinate(30.096388, -93.683617),
                        coordinate(29.658185, -93.834679),
                        coordinate(28.575458, -95.90277),
                        coordinate(27.414214, -97.26634),
                        coordinate(25.879844, -97.086611),
                        coordinate(25.835374, -97.298763),
                        coordinate(25.840318, -97.556942),
                        coordinate(25.978667, -97.71075),
                        coordinate(25.988543, -97.892025),
                        coordinate(25.983605, -98.276546),
                        coordinate(26.13658, -98.435848),
                        coordinate(26.412415, -99.149959),
                        coordinate(27.582185, -99.578426),
                        coordinate(28.252263, -100.319746),
                        coordinate(29.305393, -100.958691),
                        coordinate(29.740364, -101.486034),
                        coordinate(29.768978, -102.331982),
                        coordinate(29.592397, -102.721996),
                        coordinate(29.128003, -102.853832),
                        coordinate(28.950307, -103.106518),
                        coordinate(28.974338, -103.496532),
                        coordinate(29.17118, -103.69978),
                        coordinate(29.276648, -104.095287),
                        coordinate(29.563734, -104.512768),
                        coordinate(30.268167, -104.827144),
                        coordinate(30.628071, -105.035884),
                        coordinate(30.986642, -105.61816),
                        coordinate(31.456398, -106.222408),
                        coordinate(31.629615, -106.359737),
                        coordinate(31.761747, -106.444733)
                )
        ),


        FL_GA_SC(
                "FL, GA, SC",
                AvailableRegions.Entry.US_EAST,
                geographicalArea(
                        coordinate(30.258604, -86.394596),
                        coordinate(29.736769, -85.531611),
                        coordinate(29.373611, -85.289912),
                        coordinate(29.603126, -84.290156),
                        coordinate(29.87976, -83.938594),
                        coordinate(28.893793, -82.99377),
                        coordinate(28.20863, -82.785029),
                        coordinate(27.852737, -82.918582),
                        coordinate(27.072883, -82.643924),
                        coordinate(26.119993, -82.149539),
                        coordinate(25.322266, -81.297283),
                        coordinate(24.685042, -82.165203),
                        coordinate(25.033933, -82.912273),
                        coordinate(24.395218, -83.505535),
                        coordinate(24.03451, -81.681805),
                        coordinate(24.575187, -80.19865),
                        coordinate(25.441376, -79.715252),
                        coordinate(27.428423, -79.671307),
                        coordinate(29.09274, -80.572186),
                        coordinate(30.735823, -81.184201),
                        coordinate(31.936904, -80.59094),
                        coordinate(33.243031, -78.684125),
                        coordinate(33.828002, -78.455601),
                        coordinate(34.820445, -79.694846),
                        coordinate(34.820445, -80.809958),
                        coordinate(34.762753, -80.871737),
                        coordinate(34.97019, -83.055246),
                        coordinate(34.968265, -85.224295),
                        coordinate(32.466821, -84.572507),
                        coordinate(31.0004, -84.947169),
                        coordinate(30.258604, -86.394596)
                )
        ),


        ND_SH_EAST_WY(
                "ND, SH, East WY",
                AvailableRegions.Entry.US_NORTH,
                geographicalArea(
                        coordinate(42.979441, -96.502233),
                        coordinate(43.139982, -96.403356),
                        coordinate(43.252111, -96.502233),
                        coordinate(43.483722, -96.535192),
                        coordinate(43.515599, -96.403356),
                        coordinate(45.35109, -96.414342),
                        coordinate(45.896609, -96.524205),
                        coordinate(48.98692, -97.174759),
                        coordinate(49.001337, -107.040482),
                        coordinate(48.070222, -106.029739),
                        coordinate(45.888395, -106.016951),
                        coordinate(44.965496, -106.717651),
                        coordinate(43.588193, -109.662051),
                        coordinate(42.528638, -104.037051),
                        coordinate(42.948224, -104.048038),
                        coordinate(42.979441, -96.502233)
                )
        ),


        AR_AL_MIS_WEST_FL(
                "AR, AL, MIS, West FL",
                AvailableRegions.Entry.US_EAST,
                geographicalArea(
                        coordinate(36.897194, -94.608765),
                        coordinate(36.494182, -94.617004),
                        coordinate(35.35249, -94.4188),
                        coordinate(33.636689, -94.489141),
                        coordinate(33.640753, -94.470406),
                        coordinate(33.641365, -94.467976),
                        coordinate(33.633188, -94.453413),
                        coordinate(33.596163, -94.446718),
                        coordinate(33.589688, -94.444098),
                        coordinate(33.59312, -94.426589),
                        coordinate(33.585684, -94.419379),
                        coordinate(33.579677, -94.420924),
                        coordinate(33.574529, -94.417491),
                        coordinate(33.568235, -94.405475),
                        coordinate(33.571954, -94.388824),
                        coordinate(33.585255, -94.387794),
                        coordinate(33.588401, -94.377837),
                        coordinate(33.585684, -94.369941),
                        coordinate(33.578819, -94.365821),
                        coordinate(33.575959, -94.373202),
                        coordinate(33.572097, -94.378524),
                        coordinate(33.567234, -94.380927),
                        coordinate(33.561799, -94.390025),
                        coordinate(33.554074, -94.39775),
                        coordinate(33.548076, -94.387342),
                        coordinate(33.548791, -94.366056),
                        coordinate(33.556087, -94.355928),
                        coordinate(33.568961, -94.350778),
                        coordinate(33.574253, -94.335843),
                        coordinate(33.570678, -94.330007),
                        coordinate(33.552082, -94.329663),
                        coordinate(33.549077, -94.320222),
                        coordinate(33.549936, -94.311124),
                        coordinate(33.552225, -94.307691),
                        coordinate(33.564098, -94.302713),
                        coordinate(33.573824, -94.304601),
                        coordinate(33.579402, -94.305459),
                        coordinate(33.581976, -94.298764),
                        coordinate(33.581358, -94.286226),
                        coordinate(33.581501, -94.273179),
                        coordinate(33.581582, -94.045849),
                        coordinate(31.993095, -94.036476),
                        coordinate(31.995332, -94.033362),
                        coordinate(31.994531, -94.031088),
                        coordinate(31.996314, -94.029629),
                        coordinate(31.996205, -94.027998),
                        coordinate(31.991255, -94.024565),
                        coordinate(31.990127, -94.023063),
                        coordinate(31.990564, -94.021131),
                        coordinate(31.992566, -94.018685),
                        coordinate(31.992056, -94.017741),
                        coordinate(31.989217, -94.018599),
                        coordinate(31.984813, -94.016668),
                        coordinate(31.979504, -94.016105),
                        coordinate(31.977975, -94.012114),
                        coordinate(31.97408, -94.007307),
                        coordinate(31.965103, -94.002594),
                        coordinate(31.954855, -93.996298),
                        coordinate(31.948373, -93.989947),
                        coordinate(31.941705, -93.987451),
                        coordinate(31.938828, -93.982129),
                        coordinate(31.923091, -93.975987),
                        coordinate(31.91974, -93.969679),
                        coordinate(31.924184, -93.963327),
                        coordinate(31.923301, -93.959226),
                        coordinate(31.917109, -93.956179),
                        coordinate(31.911353, -93.955321),
                        coordinate(31.91026, -93.939374),
                        coordinate(31.908912, -93.936498),
                        coordinate(31.912555, -93.929503),
                        coordinate(31.912445, -93.92783),
                        coordinate(31.903629, -93.921306),
                        coordinate(31.140781, -93.5653),
                        coordinate(30.073727, -93.6844),
                        coordinate(29.627087, -93.83352),
                        coordinate(28.610219, -89.955883),
                        coordinate(28.725894, -88.307934),
                        coordinate(29.703934, -88.76936),
                        coordinate(30.008835, -88.198071),
                        coordinate(29.970773, -86.945629),
                        coordinate(31.004281, -84.946203),
                        coordinate(32.471138, -84.572668),
                        coordinate(34.951805, -85.221634),
                        coordinate(34.969812, -89.440384),
                        coordinate(34.482222, -90.077591),
                        coordinate(34.969812, -90.846634),
                        coordinate(36.492258, -90.187132),
                        coordinate(36.529717, -94.414852),
                        coordinate(36.897194, -94.608765)
                )
        ),


        WA_OR(
                "WA, OR",
                AvailableRegions.Entry.US_NORTH,
                geographicalArea(
                        coordinate(42.016293, -117.026491),
                        coordinate(42.015966, -116.960527),
                        coordinate(41.934324, -116.966798),
                        coordinate(41.951871, -118.68806),
                        coordinate(41.974447, -120.616783),
                        coordinate(42.016652, -124.321289),
                        coordinate(42.714732, -124.628906),
                        coordinate(46.012224, -123.947754),
                        coordinate(48.414619, -124.936523),
                        coordinate(48.166085, -122.783203),
                        coordinate(48.57479, -123.156738),
                        coordinate(48.951366, -123.112793),
                        coordinate(49.023461, -117.04834),
                        coordinate(42.016293, -117.026491)
                )
        ),


        MT_ID_NORTH_WY(
                "MT, ID, North WY",
                AvailableRegions.Entry.US_NORTH,
                geographicalArea(
                        coordinate(48.980217, -117.04834),
                        coordinate(48.994636, -107.006836),
                        coordinate(48.063397, -106.062012),
                        coordinate(45.859412, -106.040039),
                        coordinate(44.964798, -106.721191),
                        coordinate(43.598233, -109.650936),
                        coordinate(42.000325, -111.071777),
                        coordinate(42.016652, -117.026367),
                        coordinate(48.980217, -117.04834)
                )
        ),


        WY_UT_CO(
                "WY, UT, CO",
                AvailableRegions.Entry.US_NORTH,
                geographicalArea(
                        coordinate(41.999942, -114.059973),
                        coordinate(41.991777, -111.049719),
                        coordinate(43.603889, -109.632483),
                        coordinate(42.528417, -104.007483),
                        coordinate(40.981745, -104.036708),
                        coordinate(40.193397, -102.407041),
                        coordinate(36.982741, -102.307434),
                        coordinate(37.000291, -112.140198),
                        coordinate(37.993931, -112.370911),
                        coordinate(38.760442, -114.040833),
                        coordinate(41.999942, -114.059973)
                )
        ),


        IN_OH(
                "IN, OH",
                AvailableRegions.Entry.US_EAST,
                geographicalArea(
                        coordinate(41.753289, -82.990229),
                        coordinate(41.847674, -83.037291),
                        coordinate(41.674555, -82.680236),
                        coordinate(41.679226, -82.401323),
                        coordinate(41.921108, -81.881291),
                        coordinate(41.96237, -81.770157),
                        coordinate(42.27515, -80.491247),
                        coordinate(39.699758, -80.516438),
                        coordinate(39.922886, -80.795023),
                        coordinate(39.86599, -80.786784),
                        coordinate(39.840688, -80.811503),
                        coordinate(39.817486, -80.800517),
                        coordinate(39.768948, -80.852702),
                        coordinate(39.720375, -80.819743),
                        coordinate(39.690792, -80.841715),
                        coordinate(39.612549, -80.849955),
                        coordinate(39.606201, -80.913126),
                        coordinate(39.561749, -80.984538),
                        coordinate(39.515149, -81.053202),
                        coordinate(39.430343, -81.132853),
                        coordinate(39.4261, -81.176798),
                        coordinate(39.404881, -81.193278),
                        coordinate(39.37304, -81.275675),
                        coordinate(39.339061, -81.355326),
                        coordinate(39.336936, -81.388285),
                        coordinate(39.385778, -81.410258),
                        coordinate(39.398514, -81.44047),
                        coordinate(38.281044, -82.466469),
                        coordinate(38.556489, -82.993813),
                        coordinate(38.522115, -83.652992),
                        coordinate(38.590845, -84.224281),
                        coordinate(38.899316, -84.575844),
                        coordinate(38.45332, -85.410805),
                        coordinate(38.024228, -85.479898),
                        coordinate(38.006916, -85.963297),
                        coordinate(38.205746, -86.347818),
                        coordinate(37.738073, -86.545572),
                        coordinate(37.798866, -87.06193),
                        coordinate(37.712004, -87.336588),
                        coordinate(37.511326, -88.143332),
                        coordinate(38.676984, -87.488551),
                        coordinate(41.760813, -87.525802),
                        coordinate(41.753289, -82.990229)
                )
        ),


        NV_NORTH_CA(
                "NV, North CA",
                AvailableRegions.Entry.US_WEST,
                geographicalArea(
                        coordinate(42.014707, -124.276121),
                        coordinate(41.949373, -118.25012),
                        coordinate(41.933699, -116.965706),
                        coordinate(42.015376, -116.960213),
                        coordinate(42.003132, -114.054329),
                        coordinate(39.519668, -114.054329),
                        coordinate(38.761094, -114.049137),
                        coordinate(37.997685, -112.383056),
                        coordinate(36.981884, -112.134104),
                        coordinate(36.869545, -114.042742),
                        coordinate(36.152066, -114.050102),
                        coordinate(34.810192, -114.588432),
                        coordinate(42.014707, -124.276121)
                )
        );

        private final String name;
        private final AvailableRegions.Entry region;
        private final Polygon geographicalArea;

        Entry(String name, AvailableRegions.Entry region, Polygon geographicalArea) {
            this.name = name;
            this.region = region;
            this.geographicalArea = geographicalArea;
        }

        public String getName() {
            return name;
        }

        public Polygon getGeographicalArea() {
            return geographicalArea;
        }

        public AvailableRegions.Entry getRegion() {
            return region;
        }
    }

}
